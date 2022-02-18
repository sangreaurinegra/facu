package edu.tsi1.gr5.crazyfinger.rss.session;

import java.io.Serializable;

public class ClaveNoticia implements Serializable{

	private String link;
	private String titulo;
	private String fecha;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof ClaveNoticia)) {
			return false;
		}
		else {
			ClaveNoticia clave = (ClaveNoticia) obj;
			return (link.equalsIgnoreCase(clave.getLink())
					&& fecha.equals(clave.getFecha())
					&& titulo.equals(clave.getTitulo()));
		}
	}
	
}
