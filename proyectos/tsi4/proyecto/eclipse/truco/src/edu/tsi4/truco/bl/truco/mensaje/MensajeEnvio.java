package edu.tsi4.truco.bl.truco.mensaje;

import java.util.Hashtable;



public class MensajeEnvio extends MensajeAbstracto {

	public MensajeEnvio(int comando) {
		super();
		this.comando = comando;
		parametros=new Hashtable();
	}
	
	
	public String getIdentMsg() {
		return "env";
	}
	
	public static void main(String[] args) {
		MensajeEnvio msg = new MensajeEnvio(3);
		msg.addParametro("prueba", "123");
		
		System.out.println(msg.toXml());
	}




	
}
