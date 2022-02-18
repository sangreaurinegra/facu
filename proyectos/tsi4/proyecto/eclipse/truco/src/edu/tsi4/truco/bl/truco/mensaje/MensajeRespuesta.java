package edu.tsi4.truco.bl.truco.mensaje;

import java.util.Hashtable;



public class MensajeRespuesta extends MensajeAbstracto {

	
	public MensajeRespuesta(int comando) {
		super();
		this.comando = comando;
		parametros=new Hashtable();
	}

	public String getIdentMsg() {
		return "res";
	}
	
	public static void main(String[] args) {
		MensajeRespuesta msg = new MensajeRespuesta(3);
		msg.addParametro("prueba", "123");
		
		System.out.println(msg.toXml());
	}
	
}
