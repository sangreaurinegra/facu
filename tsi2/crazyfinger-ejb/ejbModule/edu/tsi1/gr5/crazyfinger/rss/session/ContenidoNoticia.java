package edu.tsi1.gr5.crazyfinger.rss.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jboss.seam.annotations.Name;

@Name("contenidoNoticia")
public class ContenidoNoticia {
	
	private String about;
	private String base;
	private List<String> lineas;
	private String lang;
	private String resource;
	private String src;
	private String type;
	private Locale locale;
	
	public ContenidoNoticia() {
		
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public List<String> getLineas() {
		if (lineas == null) {
			lineas = new ArrayList<String>();
		}
		return lineas;
	}

	public void setLineas(List<String> lineas) {
		this.lineas = lineas;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public String toHtml() {
		String h = "";
		for (String linea : getLineas()) {
			h+= linea + "<br/>";
		}
		return h;
	}
	
}
