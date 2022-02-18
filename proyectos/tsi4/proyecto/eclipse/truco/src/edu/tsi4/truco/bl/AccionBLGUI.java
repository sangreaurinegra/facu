package edu.tsi4.truco.bl;

import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;

public abstract class AccionBLGUI {

	// accion que es retornada por la logica del juego particular
	// contiene la accion a tomar por la logica general, como ser mandar mensaje, etc.
	// extender para agregar la accion para la gui.
	
	
	private int accion;
	private MensajeAbstracto mensaje;
	private boolean enviarMensaje = false;
	
	public int getAccion() {
		return accion;
	}
	public void setAccion(int accion) {
		this.accion = accion;
	}
	public MensajeAbstracto getMensaje() {
		return mensaje;
	}
	public void setMensaje(MensajeAbstracto mensaje) {
		this.mensaje = mensaje;
	}
	public boolean enviarMensaje() {
		return enviarMensaje;
	}
	public void setEnviarMensaje() {
		this.enviarMensaje = true;
	}
}
