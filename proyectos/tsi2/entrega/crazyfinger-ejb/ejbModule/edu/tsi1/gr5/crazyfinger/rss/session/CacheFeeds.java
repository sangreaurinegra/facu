package edu.tsi1.gr5.crazyfinger.rss.session;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.tsi1.gr5.crazyfinger.rss.helper.FeedHelper.*;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import org.jboss.seam.annotations.AutoCreate;

@Name("cacheFeeds")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class CacheFeeds {

	private Map<String, Noticia> noticias = new HashMap<String, Noticia>();
	private Map<String,List<Noticia>> links = new HashMap<String, List<Noticia>>();
	
	public CacheFeeds() {
		
	}
	
	public void addNoticia(Noticia noticia) {
		noticias.put(noticia.getClave(), noticia);
		addLink(noticia);
	}

	public boolean contieneNoticia(String clave) {
		return noticias.containsKey(clave);
	}
	
	public boolean contieneNoticia(String link, String titulo, String fecha) throws NoSuchAlgorithmException {
		String clave = toSHA1(link, titulo, fecha);
		return contieneNoticia(clave);
	}
	
	public boolean linkYaFueProcesado(String link) {
		return links.containsKey(link);
	}
	
	public Noticia obtenerNoticia(String clave) {
		return noticias.get(clave);
	}
	
	public Noticia obtenerNoticia(String link, String titulo, String fecha) throws NoSuchAlgorithmException {
		String clave = toSHA1(link, titulo, fecha);
		return obtenerNoticia(clave);
	}
	
	public void addLink(Noticia noticia) {
		List<Noticia> lista = null;
		lista = links.get(noticia.getLinkFeed());
		if (lista == null) {
			lista = new ArrayList<Noticia>();
		}
		lista.add(noticia);
	}
	
	public List<Noticia> getNoticiasRelacionadas(Noticia noticia) {
		List<Noticia> lista = null;
		lista = links.get(noticia.getLinkFeed());
		if (lista == null) {
			lista = new ArrayList<Noticia>();
		}
		else {
			List<Noticia> lista2 = new ArrayList<Noticia>();
			for (Noticia n : lista) {
				if (!n.equals(noticia)) {
					lista2.add(n);
				}
				return lista2;
			}
		}
		return lista;
	}
	
}
