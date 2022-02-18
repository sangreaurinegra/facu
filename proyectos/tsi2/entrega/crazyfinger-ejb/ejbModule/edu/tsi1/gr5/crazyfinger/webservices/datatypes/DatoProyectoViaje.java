package edu.tsi1.gr5.crazyfinger.webservices.datatypes;

import java.io.Serializable;
import java.util.List;

public class DatoProyectoViaje implements Serializable{

	private String usuario;
	private String openid;
	private String descripcion;
	private int estado;
	

	private List<DatoNodo> nodos;
	
	public DatoProyectoViaje() {
		super();
		
	}


	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public List<DatoNodo> getNodos() {
		return nodos;
	}

	public void setNodos(List<DatoNodo> nodos) {
		this.nodos = nodos;
	}
	
	
	
	
}
