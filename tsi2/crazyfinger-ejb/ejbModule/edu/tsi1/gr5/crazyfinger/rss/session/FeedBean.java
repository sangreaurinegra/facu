package edu.tsi1.gr5.crazyfinger.rss.session;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.URIException;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.web.ServletContexts;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.ItemEntry;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedReader;

import static edu.tsi1.gr5.crazyfinger.rss.helper.FeedHelper.*;


//http://localhost:8080/crazyfinger/rss.seam
//http://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml
@Name("feedBean")
@Scope(ScopeType.PAGE)
public class FeedBean implements Serializable {


	private List<Noticia> noticias;
	
	private String link;
	
	@In 
	CacheFeeds cacheFeeds;
	
	@Create
	public void cargar() {
		Object l = ServletContexts.getInstance().getRequest().getParameter("link");
		System.out.println(" l = " + l);
		if (l!=null) 
			link = (String)l;
		
		Object cd = ServletContexts.getInstance().getRequest().getSession().getAttribute("crazydomain");
		String dominio = null;
		if (cd!=null)
			dominio = (String) cd;
		
		Object o = Component.getInstance("crazydomain");

		
//		System.out.println("ServletContexts.getInstance().getRequest().getPathTranslated() " + ServletContexts.getInstance().getRequest().getPathTranslated());
		
		
//		if (link == null) 
//			link = "http://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml";
//		if (link.equals("")) 
//			link = "http://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml";
		
		if (link == null) 
			link = getUrlRSSPorDefecto();
		if (link.equals("")) 
			link = getUrlRSSPorDefecto();
		
		System.out.println(" feedbean cargar " + link);
		
//		noticias = new ArrayList<Noticia>();
		noticias = getNoticias();
		FeedReader reader2;
		try {
			System.out.println(" antes de instanciar feedreader ");		
			reader2 = new FeedReader(new HttpURL(link));
			System.out.println(" antes de readChannel ");
			ChannelFeed c = reader2.readChannel();
			System.out.println(" c==null " + c==null);
//			System.out.println(" feedbean cantidad de noticias " + c.getItems().size());

			
			for (ItemEntry item : c.getItems()) {
				Noticia noticia = toNoticia(item);
				noticia.setLinkFeed(link);
				this.noticias.add(noticia);
				cacheFeeds.addNoticia(noticia);
			}
		} catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (YarfrawException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Noticia> getNoticias() {
		if (noticias == null) {
			noticias = new ArrayList<Noticia>();
		}
		return noticias;
	}


	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}

	private String getUrlRSSPorDefecto() { //http://localhost:8080//crazyfinger/rss
		int puerto = ServletContexts.getInstance().getRequest().getServerPort();
		String dominio = ServletContexts.getInstance().getRequest().getServerName();
		String aplicacion = ServletContexts.getInstance().getRequest().getContextPath();
		String res  = "http://" + dominio + ":" + puerto + ""+aplicacion+"/rss.seam";
		return res;
	}
	
	
}
