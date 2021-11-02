package edu.tsi1.gr5.crazyfinger.session;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessages;
import org.hibernate.validator.Length;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import edu.tsi1.gr5.crazyfinger.entity.Alojamiento;
import edu.tsi1.gr5.crazyfinger.entity.Lugar;
import edu.tsi1.gr5.crazyfinger.entity.Servicio;

@Stateless
@Name("AlojamientoParser")
public class AlojamientoParserBean implements AlojamientoParser
{
    @Logger private Log log;

    @In StatusMessages statusMessages;

    @PersistenceContext
    private EntityManager em;
        
    @In(create = true)
	AlojamientoHome alojamientoHome;
    
    @Length(max = 102)
    private String value;
    

//    @Out(scope=ScopeType.SESSION,required=false)
//    private List<Alojamiento> hoteles;
    
    public String alojamientoParser() throws FailingHttpStatusCodeException, MalformedURLException, IOException
    {    	
    	int hotelesActualizados=0,hotelesIngresados=0;
    	StringTokenizer listaTokens = new StringTokenizer(value,",",false);
    	String nombreLugar="",paisLugar="";
    	if (listaTokens.countTokens()==2)
    	{
    		nombreLugar=listaTokens.nextToken();
    		paisLugar=listaTokens.nextToken().trim();
    	}
    	else
    	{
    		FacesMessages.instance().add("El nombre de lugar debe ser de la forma \"<ciudad>, <pais>\"");
    		return null;
    	}
    	
		Query q_lugar =em.createQuery(
				"select l from edu.tsi1.gr5.crazyfinger.entity.Lugar l where nombre =:nombreLugar" +
				" and pais=:paisLugar ");
		q_lugar.setParameter("nombreLugar", nombreLugar);
		q_lugar.setParameter("paisLugar", paisLugar);
		List existing = q_lugar.getResultList();
    	
    	//int j=0;
    	if (existing.size()==0)
    	{
    		FacesMessages.instance().add("NO existe el lugar #{AlojamientoParser.value}");
    		return null;
    	}       
    	
    	Lugar lugar=(Lugar)existing.get(0);

    	final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_2);
	    final HtmlPage page = webClient.getPage("http://www.hoteles.com/");
	    //System.out.println(page.getTitleText());
	    HtmlForm form=page.getFormByName("myform");
	    //System.out.println(form.getNameAttribute());
	    HtmlTextInput paramEntrada= form.getInputByName("usertypedcity");
        paramEntrada.setValueAttribute(value);
        //HtmlButton boton=form.getElementById("submitSearch");
        //final HtmlPage page2 = boton.click();
         HtmlPage page2 = (HtmlPage) form.submit(null);
        //System.out.println(page2==page);
/*        boolean hasNext;
        do
        {
*/
        	//System.out.println(page2.getTitleText());
            HtmlForm form2=page2.getFormByName("myform");
            //System.out.println(form2);
            HtmlDivision divPropertyList = (HtmlDivision) form2.getElementById("propertyList");
            //System.out.println(divPropertyList);
            
            //form2.getByXPath(xpathExpr);
            List<HtmlDivision> listaDivHoteles = (List<HtmlDivision>) divPropertyList.getByXPath("//div[@class='info clearFix']");
            //System.out.println(listaDivHoteles.size());
        if (listaDivHoteles.size()==0){
        	FacesMessages.instance().add("NO hay hoteles en la página web para el lugar #{AlojamientoParser.value}");
        	return null;
        }
        else
        {
            String urlFoto="",nombreHotel="",costo="";
            Double costoDouble=null;
            for (HtmlDivision h:listaDivHoteles)
	        {
	        	//Obtiene los datos del hotel
            	for (HtmlElement e: h.getChildElements() )
            	{
            		
            		if (e.getAttribute("class").equals("thumbnail"))
            		{
            			//el dato a obtener es una foto
            			List<HtmlElement> listaImg = e.getHtmlElementsByTagName("IMG");
            			if (listaImg.size()>0)
            			{
            				//System.out.println("urlFoto: "+listaImg.get(0).getAttribute("src"));
            				urlFoto="http://www.hoteles.com"+listaImg.get(0).getAttribute("src");
        	        		if (urlFoto.equals("http://www.hoteles.com"))
        	        			urlFoto="No tiene URL";
        	        		else if (urlFoto.length()>250)
        	        			urlFoto="Error en URL, mayor 250";
            			}
            		}
            		else if (e.getAttribute("class").equals("property-info"))
            		{
            			//el dato a obtener es el nombre del hotel
            			List<HtmlElement> listaSpan = e.getHtmlElementsByTagName("SPAN");
            			if (listaSpan.size()>0)
            			{
            				for (HtmlElement s: listaSpan )
            	        	{
            					if (s.getAttribute("class").equals("property-name"))
            					{
            						List<HtmlElement> listaA= s.getHtmlElementsByTagName("A");
            						if (listaA.size()>0)
            						{
            							//System.out.println("Nombre: "+listaA.get(0).asText() );
            							nombreHotel=listaA.get(0).asText();
            							if (nombreHotel.length()>50)
            			        			//servicio.setNombre("Error en nombre, mayor 50");
            			        			nombreHotel=nombreHotel.substring(0, 49);
            						}
            					}
            	        	}
            			}
            		}
            		else if (e.getAttribute("class").equals("rate-info"))
            		{
            			List<HtmlElement> listaSpan = e.getHtmlElementsByTagName("SPAN");
            			if (listaSpan.size()>0)
            			{
            				for (HtmlElement s: listaSpan )
            	        	{
            					if (s.getAttribute("class").equals("from"))
            					{
            						//System.out.println("Costo: "+s.asText() );
            						costo=s.asText();
            						if (costo.trim().equals(""))
            							costoDouble=null;
            						else
            						{
            							//el str es de la forma "desde USD <precio>"
            							costo=costo.substring(10);
            							//elimino del str los separadores de mil
            							costo=costo.replaceAll("[.]", "");
            							//en el str viene como separador decimal la , en ves del .
            							costo=costo.replace(',', '.');
            							costoDouble=Double.valueOf(costo);
            						}
            					}
            	        	}
            			}
            		}
            	}
            	
            	//Ya se tienen los datos del hotel
            	//Ahora hay que insertar o actualizar estos en la BD
	        	Alojamiento alojamiento;
	        		
        		//hotel = buscarAlojamientoConNombre(nombreHotel, tipo="hotel");
        		Query q =em.createQuery(
        					"select a from edu.tsi1.gr5.crazyfinger.entity.Alojamiento a, IN(a.lugar) l " +
        					"where a.nombre = :nombreServicio and l.nombre = :nombreLugar and l.pais= :paisLugar ");
        		q.setParameter("nombreServicio", nombreHotel);
        		q.setParameter("nombreLugar", nombreLugar);
        		q.setParameter("paisLugar", paisLugar);
        		
        		List existingAlojamiento = q.getResultList();
	        		
        		if (existingAlojamiento.size()==0)
            	{
        			//si hotel<>null ingresar el alojamiento
        			alojamiento=alojamientoHome.createInstance();
        			alojamiento.setNombre(nombreHotel);
        			alojamiento.setLugar(lugar);
        			alojamiento.setTipo("hotel");
        			alojamiento.setCosto(costoDouble);
        			alojamiento.setUrl(urlFoto);
        			alojamientoHome.setInstance(alojamiento);
        			alojamientoHome.persist();
        			hotelesIngresados++;
            	}
        		else
        		{
        			//actualizar los datos urlFoto del hotel
        			alojamiento=(Alojamiento)existingAlojamiento.get(0);
        			alojamiento.setCosto(costoDouble);
        			alojamiento.setUrl(urlFoto);
        			alojamientoHome.setInstance(alojamiento);
        			alojamientoHome.update();
        			hotelesActualizados++;
        		}
        				        			        		        			        			        			        			        			        			        
        		urlFoto="";
        		nombreHotel="";
        		costo="";
        		costoDouble=null;
	        }
        }   
/* Esta parte es la encargada de ir a la página siguiente
 *             
	        //persiste en la BD los hoteles leidos en la pág leida
	        em.flush();
	        
	        HtmlDivision divListaPaginas = (HtmlDivision) page2.getElementById("sortPaginate");
	        //System.out.println(divListaHoteles);
	        List<HtmlAnchor> listaPaginas = divListaPaginas.getHtmlElementsByTagName("A");
	        HtmlPage page3=null;
	        for (HtmlAnchor a:listaPaginas)
	        {
	        	DomNode d=a.getFirstChild();
	        	String s=d.asText();
	        	if(s.compareTo("Página siguiente")==0)
	        	{
	        		//page3=(HtmlPage) a.openLinkInNewWindow();
	        		page3=(HtmlPage) a.click();
	        		break;
	        	}
	        	
	        }
	        if (page3==null)
	        	hasNext=false;
	        else{
	        	hasNext=true;
	        	page2=page3;
	        }
	        
	        
	        	
        }
        while(hasNext);
*/    	
        
        statusMessages.clear();
        //FacesMessages.instance().add("Listo el pollo");
        FacesMessages.instance().add("Se actualizaron #0 y se ingresaron #1 hoteles",hotelesActualizados,hotelesIngresados); 
        return null;
        //return "/alojamientoParserLista.xhtml";

    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    /*@Remove
    public void destroy() {}*/

}
