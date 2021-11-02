package edu.tsi1.gr5.crazyfinger.rss.session;

import java.security.NoSuchAlgorithmException;

import org.jboss.beans.metadata.api.annotations.Create;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.web.ServletContexts;

@Name("verFeedEntry")
@Scope(ScopeType.PAGE)
public class VerFeedEntryBean {

	@In
	CacheFeeds cacheFeeds;
	
	private Noticia noticia = null;
	
	@Create
	public void init() throws NoSuchAlgorithmException {
		System.out.println("VerFeedEntryBean.init()");
		// Parametros 
//		Object l = ServletContexts.getInstance().getRequest().getParameter("link");
//		String link = (String) l;
//		Object t = ServletContexts.getInstance().getRequest().getParameter("titulo");
//		String titulo = (String) t;
//		Object f = ServletContexts.getInstance().getRequest().getParameter("fecha");
//		String fecha = (String) f;
//
//		System.out.println("link " + link);
//		System.out.println("titulo " + titulo);
//		System.out.println("fecha " + fecha);

		Object l = ServletContexts.getInstance().getRequest().getParameter("key");
		String key = (String) l;
		
		
//		noticia = cacheFeeds.obtenerNoticia(link, titulo, fecha);
		noticia = cacheFeeds.obtenerNoticia(key);
		
//		cacheFeeds.show();
		System.out.println("noticia " + noticia);
		if (noticia != null) {
			System.out.println("getLinkFeed " + noticia.getLinkFeed());
			System.out.println("getTitulo " + noticia.getTitulo());
			System.out.println("getDescriptionOrSummaryText " + noticia.getDescriptionOrSummaryText());
			System.out.println("getPubDate " + noticia.getPubDate());
			System.out.println("getBase " + noticia.getBase());
			
		}
		
	}

	public CacheFeeds getCacheFeeds() {
		return cacheFeeds;
	}

	public void setCacheFeeds(CacheFeeds cacheFeeds) {
		this.cacheFeeds = cacheFeeds;
	}

	public Noticia getNoticia() {
		if (noticia == null) {
			try {
				init();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}
	
	
	
}
