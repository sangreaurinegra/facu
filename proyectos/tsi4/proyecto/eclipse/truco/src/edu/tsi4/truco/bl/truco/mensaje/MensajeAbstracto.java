package edu.tsi4.truco.bl.truco.mensaje;

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class MensajeAbstracto {

	protected String identMsg="";
	
	int comando;
	
	Hashtable parametros = null;

	public void addParametro(String nombre, String valor){
		parametros.put(nombre, valor);
	}
	
	public String getParametro(String nombre){
		return (String) parametros.get(nombre);
	}
	
	public Hashtable parametros(){
		return parametros;
	}
	
	public String toXml(){
		if(parametros==null) parametros = new Hashtable();
		String ret = "<"+getIdentMsg()+" ";
		ret += "cmd=\"" +comando+"\">";
		ret +="<params";
		Enumeration en = parametros.keys();
		while (en.hasMoreElements()) {
			String clave = (String) en.nextElement();
			ret +=" ";
			ret += clave+"="+"\""+parametros.get(clave)+"\"";

		}
		ret +="/>";
		ret +="</"+getIdentMsg()+">";
		return ret;
	}
	
	public abstract String getIdentMsg();
	
	
	public String paraPruebas(){
		if(parametros==null) parametros = new Hashtable();
		String ret = "Comando " + comando;
		ret +="\n";
		
		Enumeration en = parametros.keys();
		
		while (en.hasMoreElements()) {
			String clave = (String) en.nextElement();
			
			ret += "Clave " + clave +" Valor "+ parametros.get(clave);
			ret +="\n";
		}
		
		return ret;
	}

	public int getComando() {
		return comando;
	}

	public void setComando(int comando) {
		this.comando = comando;
	}
	
	
	
}
