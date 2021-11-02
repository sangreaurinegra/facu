package edu.tsi1.gr5.crazyfinger.rss.session;

import java.util.Locale;

import org.jboss.seam.annotations.Name;

@Name("linkNoticia")
public class LinkNoticia {

	private String href;
	private String base;
	private String hrefLang;
	private Locale hrefLocale;
	private String lang;
	private Locale locale;
	private String rel;
	private String title;
	private String type;
	
	public LinkNoticia() {
		
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getHrefLang() {
		return hrefLang;
	}

	public void setHrefLang(String hrefLang) {
		this.hrefLang = hrefLang;
	}

	public Locale getHrefLocale() {
		return hrefLocale;
	}

	public void setHrefLocale(Locale hrefLocale) {
		this.hrefLocale = hrefLocale;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

	
}
