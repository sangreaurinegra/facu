package edu.tsi4.truco.data.comm;

import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;

public interface ICommunicationConnection {

	public void conectar(boolean soyServidor, String destino, ICommunicationListener listener);
	
	public void enviar(MensajeAbstracto mensaje, String destino) throws CommException;
	
}
