package edu.tsi1.gr5.crazyfinger.webservices.datatypes;

import java.util.List;

public class PaquetesTuristicosWS {
	
	private String nombre;
	private String descripcion;
	private List<NodoWS> nodosws;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<NodoWS> getNodosws() {
		return nodosws;
	}
	public void setNodosws(List<NodoWS> nodosws) {
		this.nodosws = nodosws;
	}
	
	
}
