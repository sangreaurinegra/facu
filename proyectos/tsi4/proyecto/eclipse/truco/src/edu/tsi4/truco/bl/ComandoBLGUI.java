package edu.tsi4.truco.bl;

import java.util.Hashtable;

public abstract class ComandoBLGUI {
	//clase abstracta que se usa para el pasaje de datos entre la logica del juego y la gui
	//extender esta clase para cada juego en particular
	
	private int comando;

	public int getComando() {
		return comando;
	}
	
	public void setComando(int comando) {
		this.comando = comando;
	}
	
	Hashtable parametros = null;
	
	public String getParametro(String nombre){
		return (String) parametros.get(nombre);
	}
	
	public Hashtable parametros(){
		return parametros;
	}
	
	public void parametros(Hashtable p){
		parametros = p;
	}
	
	public void addParametro(String nombre, String valor){
		parametros.put(nombre, valor);
	}
}
