package uy.edu.fing.inco.hpc;


public class Nodo implements Comparable{
	
	private String ruta;
	private int largo;
	private int cantidad;
	
	
	public Nodo() {
		super();
	}
	
	public Nodo(String ruta, int largo) {
		super();
		this.ruta = ruta;
		this.largo = largo;
	}
	
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	public int getLargo() {
		return largo;
	}
	public void setLargo(int largo) {
		this.largo = largo;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int compareTo(Object o) {
		if (this.largo < ((Nodo)o).getLargo())
			return 1;
		else if (this.largo > ((Nodo)o).getLargo())
			return -1;
		else 
			return 0;
		
	}
}
