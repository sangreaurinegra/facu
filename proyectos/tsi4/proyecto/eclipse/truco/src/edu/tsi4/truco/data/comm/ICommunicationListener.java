package edu.tsi4.truco.data.comm;

import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;

public interface ICommunicationListener {

	public void recibir(MensajeAbstracto mensajeAbstracto);
}
