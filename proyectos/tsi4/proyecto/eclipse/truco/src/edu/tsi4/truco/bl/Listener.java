package edu.tsi4.truco.bl;

import edu.tsi4.truco.bl.truco.ComandoTruco;
import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.bl.truco.mensaje.MensajeEnvio;
import edu.tsi4.truco.bl.truco.mensaje.MensajeRespuesta;
import edu.tsi4.truco.config.ConfigManager;
import edu.tsi4.truco.data.comm.CommException;
import edu.tsi4.truco.data.comm.ConnectionFactory;
import edu.tsi4.truco.data.comm.ICommunicationConnection;
import edu.tsi4.truco.data.comm.ICommunicationListener;
import edu.tsi4.truco.data.comm.sms.SmsHelper;
import edu.tsi4.truco.lwuit.listener.IGUIListener;

public class Listener implements ICommunicationListener {

	private IJuegoHandler h;
	private AccionBLGUI accion;
	private IGUIListener guiListener;
	
	public Listener(IJuegoHandler ijh){
		accion = null;
		h = ijh;
	}
	
	public void recibir(MensajeAbstracto mensaje) {
		// recibi mensaje, vuelve la actividad al juego
		
		// TODO chekeos de mensajes de error
		ComandoTruco cmd = new ComandoTruco();
		cmd.setComando(mensaje.getComando());
		cmd.parametros(mensaje.parametros());
		accion = GameLogic.instance().ejecutarComando(cmd, guiListener);
		if(guiListener != null)
			guiListener.recibir(accion);
	}
	
	public AccionBLGUI getAccion() {
		return accion;
	}

	public void setAccion(AccionBLGUI accion) {
		this.accion = accion;
	}

	public IGUIListener getGuiListener() {
		return guiListener;
	}

	public void setGuiListener(IGUIListener guiListener) {
		this.guiListener = guiListener;
	}

}
