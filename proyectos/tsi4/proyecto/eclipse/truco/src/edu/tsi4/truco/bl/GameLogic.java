package edu.tsi4.truco.bl;

import edu.tsi4.truco.bl.truco.AccionTruco;
import edu.tsi4.truco.bl.truco.ComandoTruco;
import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.bl.truco.mensaje.MensajeEnvio;
import edu.tsi4.truco.config.ConfigManager;
import edu.tsi4.truco.data.comm.ConnectionFactory;
import edu.tsi4.truco.data.comm.ICommunicationConnection;
import edu.tsi4.truco.data.comm.sms.SmsHelper;
import edu.tsi4.truco.lwuit.listener.IGUIListener;

public class GameLogic {

	private IJuegoHandler h;
	private Listener listener;
	
	private static GameLogic instance;
	
	private boolean soyCreador;
	private String invitado;
	private String creador;
	
	private GameLogic(){
		h = HandlerFactory.handler();
		listener = new Listener(h);
		soyCreador = false;
	}
	
	public static GameLogic instance(){
		if(instance == null)
			instance = new GameLogic();
		return instance;
	}
	
	public void crearJuego(boolean creador, String invitado, boolean sms, String cel, IGUIListener gui) {
		try{
			h.crearJuego(invitado,cel);
			soyCreador = creador;
			this.invitado = invitado;
			listener.setGuiListener(gui);
			//si hay que mandar sms lo envio y me duermo
			if(sms){
				(new SmsHelper()).enviar(getMensajeInvitacion(), cel);
			}
			//concecto y envio mensaje de invitacion
			ICommunicationConnection con = ConnectionFactory.getConnection();
			con.conectar(soyCreador,invitado, listener);
			con.enviar(getMensajeInvitacion(), this.invitado);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private MensajeAbstracto getMensajeInvitacion() throws Exception {
		MensajeEnvio msg = new MensajeEnvio(ComandoTruco.CMD_INVITAR_USUARIO);
		msg.addParametro("creador", ConfigManager.usuario());
		msg.addParametro("juego", h.idJuego());
		return msg;
	}

	public AccionBLGUI ejecutarComando(ComandoBLGUI comando, IGUIListener gui) {
		if(comando.getComando() == ComandoTruco.CMD_SET_LISTENER){
			listener.setGuiListener(gui);
			return new AccionTruco(); // retorno una accion nula
		}
		AccionBLGUI accion = h.ejecutarComando(comando, soyCreador);
		listener.setGuiListener(gui);
		//hacemos todo lo que tengamos que hacer
		// luego retornamos la accion a la gui.
		if(accion.enviarMensaje()){
			try{
				//listener.setAccion(accion); // seteo la accion al listener por si tengo que modificarla, al recibir el mensaje.
				ICommunicationConnection con = ConnectionFactory.getConnection();
				if(soyCreador)
					con.enviar(accion.getMensaje(), this.invitado);
				else
					con.enviar(accion.getMensaje(), this.creador);
				//accion = listener.getAccion();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		return accion;
	}

	public void finalizarJuego() {
		h.finalizarJuego();

	}

	public void initJuego(String invitado, String celular) {
		h.initJuego(invitado, celular);

	}

	public void pausarJuego() {
		h.pausarJuego();

	}
	
	public void unirseJuego(String creador, String idJuego, IGUIListener gui) {

		//si tengo el id del juego implica que me llego el sms
		// sino voy a buscar el mensaje de invitacion
		try{
			soyCreador = false;
			listener.setGuiListener(gui);
			this.creador= creador;
			//concecto y envio mensaje de invitacion
			ICommunicationConnection con = ConnectionFactory.getConnection();
			con.conectar(soyCreador,creador, listener);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
