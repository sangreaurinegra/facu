package edu.tsi1.gr5.crazyfinger.rss.session;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.annotations.Name;

@Name("noticiaFeed")
public class Noticia implements Serializable {

	private String about;
	private String titulo;
	private String base;
	private String comments;
	private String descriptionOrSummaryText;
	private String pubDate;
	private String lang;
	private String source;
	private String contributors;
	private String autores;
	private String categoria;
	private String enclousureUrl;
	private String updatedDate;
	
	private ContenidoNoticia contenido;
	private List<LinkNoticia> links;
	private TextoNoticia tituloNoticia;
	
	private String linkFeed;
	private String clave;
	
	public Noticia() {
		
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDescriptionOrSummaryText() {
		return descriptionOrSummaryText;
	}

	public void setDescriptionOrSummaryText(String descriptionOrSummaryText) {
		this.descriptionOrSummaryText = descriptionOrSummaryText;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public ContenidoNoticia getContenido() {
		return contenido;
	}

	public void setContenido(ContenidoNoticia contenido) {
		this.contenido = contenido;
	}

	public List<LinkNoticia> getLinks() {
		return links;
	}

	public void setLinks(List<LinkNoticia> links) {
		this.links = links;
	}

	public TextoNoticia getTituloNoticia() {
		return tituloNoticia;
	}

	public void setTituloNoticia(TextoNoticia tituloNoticia) {
		this.tituloNoticia = tituloNoticia;
	}
	
	public String links2html() {
		String links = "";
		for (LinkNoticia link : this.getLinks()) {
			String s = "<a href=\"" + link.getHref() + "\"" +">" + link.getTitle() + "</a>,";
		}
		return links;
	}

	public String getLinkFeed() {
		return linkFeed;
	}

	public void setLinkFeed(String linkFeed) {
		this.linkFeed = linkFeed;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getContributors() {
		return contributors;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEnclousureUrl() {
		return enclousureUrl;
	}

	public void setEnclousureUrl(String enclousureUrl) {
		this.enclousureUrl = enclousureUrl;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	
}
