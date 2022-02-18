package edu.tsi1.gr5.crazyfinger.rss.session;

import java.util.Locale;

import org.jboss.seam.annotations.Name;

@Name("textoNoticia")
public class TextoNoticia {

	private String base;
	private String lang;
	private Locale locale;
	private String xhtmlDiv;
	
	public TextoNoticia() {
		
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
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

	public String getXhtmlDiv() {
		return xhtmlDiv;
	}

	public void setXhtmlDiv(String xhtmlDiv) {
		this.xhtmlDiv = xhtmlDiv;
	}

	
	
	
}
