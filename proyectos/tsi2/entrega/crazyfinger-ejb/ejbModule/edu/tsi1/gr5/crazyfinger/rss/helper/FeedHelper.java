package edu.tsi1.gr5.crazyfinger.rss.helper;

import static edu.tsi1.gr5.crazyfinger.rss.helper.FeedHelper.toSHA1;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.tsi1.gr5.crazyfinger.rss.session.ContenidoNoticia;
import edu.tsi1.gr5.crazyfinger.rss.session.LinkNoticia;
import edu.tsi1.gr5.crazyfinger.rss.session.Noticia;
import edu.tsi1.gr5.crazyfinger.rss.session.TextoNoticia;
import yarfraw.core.datamodel.CategorySubject;
import yarfraw.core.datamodel.Content;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.Link;
import yarfraw.core.datamodel.Person;
import yarfraw.core.datamodel.Source;
import yarfraw.core.datamodel.Text;

public class FeedHelper {

	public static Noticia toNoticia(ItemEntry item) throws NoSuchAlgorithmException {
		Noticia noticia = new Noticia();
		noticia.setAbout(item.getAbout());
		noticia.setTitulo(item.getTitle().getText());
		noticia.setBase(item.getBase());
		noticia.setComments(item.getComments());
		noticia.setDescriptionOrSummaryText(item.getDescriptionOrSummaryText());
		noticia.setPubDate(item.getPubDate());
		noticia.setLang(item.getLang());
		if (item.getSource()!=null) {
			noticia.setSource(item.getSource().getSource());
		}
		noticia.setContributors(listaPersonas2String(item.getContributors()));
		noticia.setAutores(listaPersonas2String(item.getAuthorOrCreator()));
		noticia.setCategoria(categoria2String(item.getCategorySubjects()));

		
		if (item.getEnclosure()!=null) {
			noticia.setEnclousureUrl(item.getEnclosure().getUrl());
		}
		noticia.setUpdatedDate(item.getUpdatedDate());
		
		// Mapear contenido;
		Content content = item.getContent();
		ContenidoNoticia contenido = toContenido(content);
		noticia.setContenido(contenido);
		
		// mapear links
		List<LinkNoticia> lns = toListLinkNoticia(item.getLinks());
		noticia.setLinks(lns);
		
		Text title = item.getTitle();
		noticia.setTituloNoticia(toTextoNoticia(title));
		
		
		String clave = toSHA1(noticia.getLinkFeed(),noticia.getTitulo(),noticia.getPubDate());
		noticia.setClave(clave);

		
		return noticia;
	}
	
	
	public static ContenidoNoticia toContenido(Content content) {
		ContenidoNoticia contenido = new ContenidoNoticia();
		System.out.println("FEEDHELPER!! content==null " + content==null);
		
		if (content!=null) {
			System.out.println("FEEDHELPER!! content lineas " + content.getContentText().size());
			
			contenido.setAbout(content.getAbout());
			contenido.setBase(content.getBase());
			contenido.setLineas(content.getContentText());
			contenido.setLang(content.getLang());
			contenido.setResource(content.getResource());
			contenido.setSrc(content.getSrc());
			contenido.setType(content.getType());
			contenido.setLocale(content.getLangAsLocale());
		}
		return contenido;
	}
	
	public static List<LinkNoticia> toListLinkNoticia(List<Link> ls) {
		ArrayList<LinkNoticia> al= new ArrayList<LinkNoticia>();
		if (ls!=null) {
			for (Link link : ls) {
				al.add(toLinkNoticia(link));
			}
		}
		return al;
	}
	
	public static LinkNoticia toLinkNoticia(Link link) {
		LinkNoticia ln = new LinkNoticia();
		ln.setBase(link.getBase());
		ln.setHref(link.getHref());
		ln.setHrefLang(link.getHreflang());
		ln.setHrefLocale(ln.getHrefLocale());
		ln.setLang(link.getLang());
		ln.setLocale(link.getLangAsLocale());
		ln.setRel(link.getRel());
		ln.setTitle(link.getTitle());
		ln.setType(link.getType());
		return ln;
	}
	
	public static TextoNoticia toTextoNoticia(Text text) {
		TextoNoticia t = new TextoNoticia();
		if (text!=null) {
			t.setBase(text.getBase());
			t.setLang(text.getLang());
			t.setLocale(text.getLangAsLocale());
			if (text.getXhtmlDiv()!=null) {
				t.setXhtmlDiv(text.getXhtmlDiv().toString());
			}
		}
		return t;
	}
	
    public static String toSHA1(String link, String titulo, String fecha) throws NoSuchAlgorithmException  {
    	return toSHA1(link + titulo + fecha);
    }
	
    public static String listaPersonas2String(List<Person> conts) {
    	String s = "";
    	if (conts!=null) {
    		for (Person p : conts) {
    			s += p.getName() +"(" + p.getEmailOrText() + ") <br/>";
    		}
    	}
    	return s;
    }
    
    public static String categoria2String(Set<CategorySubject> conts) {
    	String s = "";
    	if (conts!=null) {
    		for (CategorySubject c : conts) {
    			
//    			s += c.getLabel() ;
    			s+=c.getCategoryOrSubjectOrTerm();

    			if (c.getDomainOrScheme()!=null) {
    				if (!c.getDomainOrScheme().equals("")) {
    					s +=" (" + c.getDomainOrScheme() + ") ";
    				}
    			}
    			s += "<br/>";
    		}
    	}
    	return s;
    }
    
    private static String toSHA1(String message) throws NoSuchAlgorithmException  {
    	byte[] buffer = message.getBytes();
    	MessageDigest  md = MessageDigest.getInstance("SHA1");
        md.update(buffer);
        byte[] digest = md.digest();
        String hash = "";

        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }

        return hash;
    }

}
