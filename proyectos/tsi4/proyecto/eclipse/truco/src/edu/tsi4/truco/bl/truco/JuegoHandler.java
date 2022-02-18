package edu.tsi4.truco.bl.truco;

import java.util.Date;

import edu.tsi4.truco.bl.AccionBLGUI;
import edu.tsi4.truco.bl.ComandoBLGUI;
import edu.tsi4.truco.bl.IJuegoHandler;
import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.bl.truco.mensaje.MensajeEnvio;
import edu.tsi4.truco.bl.truco.mensaje.MensajeRespuesta;

import edu.tsi4.truco.config.ConfigManager;
import edu.tsi4.truco.data.comm.ConnectionFactory;
import edu.tsi4.truco.data.comm.ICommunicationConnection;
import edu.tsi4.truco.util.Logger;

public class JuegoHandler implements IJuegoHandler {

	Juego juego;
	
	String idJuego; // cuando no soy el creador
	
	//crea un juego
	public void crearJuego(String invitado, String celular){
		
		try {
			juego = new Juego();
			juego.idJuego = ""+(new Date()).getTime();
			juego.creador = new Usuario();
			juego.creador.nombre = ConfigManager.usuario();
			juego.invitado = new Usuario();
			juego.invitado.nombre = invitado;
			juego.invitado.celular = celular;
			juego.estado = Juego.ESTADO_PARA_INICIAR;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	// inicia un juego
	public void initJuego(String invitado, String celular){
		
		juego.estado = Juego.ESTADO_EN_JUEGO;
		
	}
	
	// pausa un juego
	public void pausarJuego(){
		juego.estado = Juego.ESTADO_PAUSADO;
	}
	
	// finaliza un juego
	public void finalizarJuego(){
		juego.estado = Juego.ESTADO_FINALIZADO;
	}

	public AccionBLGUI ejecutarComando(ComandoBLGUI comando, boolean creador) {
		AccionTruco accion = new AccionTruco();
		try {
			//if (creador){ // si soy creador resuelvo el tema sino envio el mensaje
				if(comando.getComando() == ComandoTruco.CMD_HABILITADOS)
					accion = comandosHabilitados();
				else if(comando.getComando() == ComandoTruco.CMD_CONTRA_FLOR){
					accion.setEnviarMensaje();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					accion.setMensaje(msg);
				}
				else if(comando.getComando() == ComandoTruco.CMD_INVITAR_USUARIO){ // viene de mensaje, es el invitado
					setIdJuego(comando.getParametro("juego"));
					accion.setEnviarMensaje();
					MensajeRespuesta msg = new MensajeRespuesta(ComandoTruco.CMD_ACEPTO_INVITACION);					
					msg.addParametro("invitado", ConfigManager.usuario());					
					accion.setMensaje(msg);
				}
				else if(comando.getComando() == ComandoTruco.CMD_ACEPTO_INVITACION){// viene de mensaje, es el servidor
					initJuego(comando.getParametro("invitado"), null);
					initMano();
					accion.setEnviarMensaje();
					accion.setAccion(AccionTruco.ACCION_MOSTRAR_CARTAS);
					MensajeRespuesta msg = new MensajeRespuesta(ComandoTruco.CMD_CARTAS_USUARIO);					
					msg.addParametro("1", juego.invitado.cartas[0].toSprite());
					msg.addParametro("2", juego.invitado.cartas[1].toSprite());
					msg.addParametro("3", juego.invitado.cartas[2].toSprite());
					msg.addParametro("muestra", juego.manoActiva.muestra.toSprite());
					msg.addParametro("turno", juego.creadorEsMano?"1":"2");
					msg.addParametro("tanteador", juego.invitado.nombre+":"+juego.manoActiva.totalTantosInvitado()+" "+juego.creador.nombre+":"+juego.manoActiva.totalTantosCreador());
					accion.setMensaje(msg);
					accion.cartas = new Carta[4];
					accion.cartas[0] = juego.creador.cartas[0];
					accion.cartas[1] = juego.creador.cartas[1];
					accion.cartas[2] = juego.creador.cartas[2];
					accion.cartas[3] = juego.manoActiva.muestra;
					
				}
				else if(comando.getComando() == ComandoTruco.CMD_CARTAS_USUARIO){ // funciona en ambos sentidos para mostrar o recibir
					MensajeRespuesta msg = new MensajeRespuesta(ComandoTruco.CMD_CARTAS_USUARIO);
					accion.setAccion(AccionTruco.ACCION_MOSTRAR_CARTAS);
					accion.setMensaje(msg);
					boolean gui = comando.parametros().isEmpty();
					Logger.Log(Logger.INFO, this.getClass(), "<-->cartas usuario: "+creador+" vengo de gui:"+gui);
					if(gui){
						accion.setEnviarMensaje();
						msg.addParametro("dummy", "quiero mis cartas");
						if(creador){//envio las cartas del usuario																		
							msg.addParametro("1", juego.invitado.cartas[0].toSprite());
							msg.addParametro("2", juego.invitado.cartas[1].toSprite());
							msg.addParametro("3", juego.invitado.cartas[2].toSprite());
							msg.addParametro("muestra", juego.manoActiva.muestra.toSprite());
							msg.addParametro("turno", juego.creadorEsMano?"1":"2");
							msg.addParametro("tanteador", juego.invitado.nombre+":"+juego.manoActiva.totalTantosInvitado()+" "+juego.creador.nombre+":"+juego.manoActiva.totalTantosCreador());
							//seteo las cartas del creador
							accion.cartas = new Carta[4];
							accion.cartas[0] = juego.creador.cartas[0];
							accion.cartas[1] = juego.creador.cartas[1];
							accion.cartas[2] = juego.creador.cartas[2];
							accion.cartas[3] = juego.manoActiva.muestra;
						}
					}
					else{ // llega de mensaje
						if(creador){ //debo enviar las cartas
							accion.setEnviarMensaje();Logger.Log(Logger.INFO, this.getClass(), "<-->cartas usuario: "+creador+" envio al invitado");
							accion.setAccion(0);
							msg.addParametro("1", juego.invitado.cartas[0].toSprite());
							msg.addParametro("2", juego.invitado.cartas[1].toSprite());
							msg.addParametro("3", juego.invitado.cartas[2].toSprite());
							msg.addParametro("muestra", juego.manoActiva.muestra.toSprite());
							msg.addParametro("turno", juego.creadorEsMano?"1":"2");
							msg.addParametro("tanteador", juego.invitado.nombre+":"+juego.manoActiva.totalTantosInvitado()+" "+juego.creador.nombre+":"+juego.manoActiva.totalTantosCreador());
						}
						else{// recibi las cartas
							accion.cartas = new Carta[4];Logger.Log(Logger.INFO, this.getClass(), "<-->cartas usuario: "+creador+" recibi cartas");
							accion.cartas[0] = Carta.toCarta(comando.getParametro("1"));
							accion.cartas[1] = Carta.toCarta(comando.getParametro("2"));
							accion.cartas[2] = Carta.toCarta(comando.getParametro("3"));
							accion.cartas[3] = Carta.toCarta(comando.getParametro("muestra"));						
							msg.addParametro("turno", comando.getParametro("turno"));
							msg.addParametro("tanteador", comando.getParametro("tanteador"));
						}
					}
				}
				else if(comando.getComando() == ComandoTruco.CMD_JUGAR_CARTA){ // comando viene del gui
					Carta jugada = Carta.toCarta(comando.getParametro("jugada"));
					MensajeEnvio msg = new MensajeEnvio(ComandoTruco.CMD_CARTA_JUGADA);	
					msg.addParametro("jugada", comando.getParametro("jugada"));
					accion.setEnviarMensaje();
					if(creador){
						int res = juego.manoActiva.jugarCarta(jugada, juego.creador, creador);
						Logger.Log(Logger.INFO, this.getClass(), "-->res de la mano"+res+"<--");
						if(res != 0){ // se termino la baza, y probablemente la mano
							if(juego.manoActiva.estado == Mano.ESTADO_FIN){
								accion.finMano = true;
								msg.addParametro("finMano", ""+accion.finMano);
								initMano();
							}	
							//envio al otro jugador de quien es el turno
							msg.addParametro("turno", juego.manoActiva.turnoCreador?"1":"2");
						}
					}				
					accion.setMensaje(msg);					
				}
				else if(comando.getComando() == ComandoTruco.CMD_CARTA_JUGADA){ // comando viene con la carta que jugo el contrario
					Carta jugada = Carta.toCarta(comando.getParametro("jugada"));
					MensajeEnvio msg = new MensajeEnvio(ComandoTruco.CMD_TURNO);
					accion.setAccion(AccionTruco.ACCION_CARTA_JUGADA);
					if(creador){							
						int res = juego.manoActiva.jugarCarta(jugada, juego.invitado, false);
						
						if(res != 0){ // se termino la baza, y probablemente la mano
							if(juego.manoActiva.estado == Mano.ESTADO_FIN){
								accion.finMano = true;
								msg.addParametro("finMano", ""+accion.finMano);
								initMano();
							}							
							accion.setEnviarMensaje();	
							//envio al otro jugador de quien es el turno
							msg.addParametro("turno", juego.manoActiva.turnoCreador?"1":"2");
						}
						
					}	
					else{
						String val = comando.getParametro("turno");
						if(val != null) msg.addParametro("turno", val);
						val = comando.getParametro("finMano");
						if(val != null) accion.finMano = val.trim().equalsIgnoreCase("true");
						Logger.Log(Logger.INFO, this.getClass(), "<---------->carta jugada, fin mano:"+accion.finMano);
					}
					accion.setMensaje(msg);
					// accion para que muestre la carta jugada por el contrario					
					accion.jugada = jugada;
				}
				else if(comando.getComando() == ComandoTruco.CMD_TURNO){ // comando viene de mensaje y lo mando a la gui
					accion.setAccion(AccionTruco.ACCION_TURNO);
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					msg.addParametro("turno", comando.getParametro("turno"));
					String val = comando.getParametro("finMano");
					if(val != null) accion.finMano = val.trim().equalsIgnoreCase("true");
					Logger.Log(Logger.INFO, this.getClass(), "<---------->cmd turno, fin mano:"+accion.finMano);
					accion.setMensaje(msg);						
				}
				else if(comando.getComando() == ComandoTruco.CMD_ENVIDO || comando.getComando() == ComandoTruco.CMD_3_ENVIDO || comando.getComando() == ComandoTruco.CMD_FALTA_ENVIDO){ // comando viene del gui, o de mensaje
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					if(gui){ // viene del gui
						accion.setEnviarMensaje();		
					}
					else{ // viene de mensaje del contrario
						accion.setAccion(AccionTruco.ACCION_QUIERO_TRUCO);					
						msg.addParametro("resultado", comando.getParametro("resultado"));
						accion.setMensaje(msg);	
					}	
					if(creador){
						juego.manoActiva.envido(gui);
						int vc = juego.calcularEnvido(juego.creador, juego.manoActiva.muestra);
						int vi = juego.calcularEnvido(juego.invitado, juego.manoActiva.muestra);
						if(vc>vi)
							msg.addParametro("resultado", "Gané con "+vc);
						else
							msg.addParametro("resultado", "Son buenas");
					}
					//seteo param por defecto para que diferneciar la gui del mensaje					
					msg.addParametro("toque", valorToque(comando.getComando()));
					accion.setMensaje(msg);
				}
				else if(comando.getComando() == ComandoTruco.CMD_QUIERO_ENVIDO){ // comando viene del gui
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());					
					if(gui){ // viene del gui						
						accion.setEnviarMensaje();
						if(creador)
							accion.setAccion(AccionTruco.ACCION_MOSTRAR_MENSAJE);
					}
					else{	
						accion.setAccion(AccionTruco.ACCION_MOSTRAR_MENSAJE);
						msg.addParametro("resultado", comando.getParametro("resultado"));
					}	
					if(creador){
						msg.addParametro("resultado", juego.manoActiva.quieroEnvido( Integer.valueOf(comando.getParametro("toque")).intValue() ));						
					}
					accion.setMensaje(msg);	
				}
				else if(comando.getComando() == ComandoTruco.CMD_NO_QUIERO_ENVIDO){ // comando viene del gui
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());					
					if(gui){ // viene del gui						
						accion.setEnviarMensaje();
						msg.addParametro("dummy", "No quiero envido");
					}
					else{	
						accion.setAccion(AccionTruco.ACCION_MOSTRAR_MENSAJE);
					}	
					accion.setMensaje(msg);	
				}
				else if(comando.getComando() == ComandoTruco.CMD_TRUCO){ // comando viene con la carta que jugo el contrario
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					if(gui){ // viene del gui						
						accion.setEnviarMensaje();
						msg.addParametro("dummy", "truco");
					}
					else{	
						accion.setAccion(AccionTruco.ACCION_GRITE_TRUCO);
						msg.addParametro("resultado", "Truco");
					}
					accion.setMensaje(msg);
					if(creador){
						juego.manoActiva.rabonCreador = gui;
						juego.manoActiva.truco = true; // seteo que se echo el rabon
					}
				}
				else if(comando.getComando() == ComandoTruco.CMD_RE_TRUCO){ // comando viene con la carta que jugo el contrario
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					if(gui){ // viene del gui						
						accion.setEnviarMensaje();
						msg.addParametro("dummy", "retruco");
					}
					else{	
						accion.setAccion(AccionTruco.ACCION_GRITE_RE_TRUCO);
						msg.addParametro("resultado", "Re Truco");
					}
					accion.setMensaje(msg);
					if(creador)
						juego.manoActiva.retruco = true; // seteo que se echo el rabon
				}
				else if(comando.getComando() == ComandoTruco.CMD_VALE_4){ // comando viene con la carta que jugo el contrario
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					if(gui){ // viene del gui						
						accion.setEnviarMensaje();
						msg.addParametro("dummy", "vale 4");
					}
					else{	
						accion.setAccion(AccionTruco.ACCION_GRITE_VALE_4);
						msg.addParametro("resultado", "Vale 4");
					}
					accion.setMensaje(msg);
					if(creador)
						juego.manoActiva.vale4 = true; // seteo que se echo el rabon
				}
				else if(comando.getComando() == ComandoTruco.CMD_QUIERO){ // no quiero para el truco, retruco y vale 4
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					if(gui){ // viene del gui						
						accion.setEnviarMensaje();
						msg.addParametro("dummy", "quiero truco");
					}
					else{	
						accion.setAccion(AccionTruco.ACCION_QUIERO_TRUCO);
						msg.addParametro("resultado", "Quiero");
					}
					accion.setMensaje(msg);
					if(creador){
//						juego.manoActiva.noQuieroTruco();
//						initMano();
					}
				}
				else if(comando.getComando() == ComandoTruco.CMD_NO_QUIERO){ // no quiero para el truco, retruco y vale 4
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					if(gui){ // viene del gui						
						accion.setEnviarMensaje();
						msg.addParametro("dummy", "no quiero");
					}
					else{							
						msg.addParametro("resultado", "No quiero");
					}
					accion.finMano = true;
					accion.setAccion(AccionTruco.ACCION_NO_QUIERO_TRUCO);
					accion.setMensaje(msg);
					if(creador){
						juego.manoActiva.rabonCreador = !gui;
						juego.manoActiva.noQuieroTruco();
						initMano();
					}
				}
				else if(comando.getComando() == ComandoTruco.CMD_TANTOS){ // para refrescar el tanteador
					boolean gui = comando.parametros().isEmpty();
					MensajeEnvio msg = new MensajeEnvio(comando.getComando());
					Logger.Log(Logger.INFO, this.getClass(), "<---------->usuario servidor: "+creador+" vengo de gui:"+gui);
					
					if(gui){ // viene del gui	
						if(creador){
							accion.setAccion(AccionTruco.ACCION_MOSTRAR_TANTOS);
							msg.addParametro("tanteador", juego.invitado.nombre+":"+juego.manoActiva.totalTantosInvitado()+" "+juego.creador.nombre+":"+juego.manoActiva.totalTantosCreador());
						}
						else{
							accion.setEnviarMensaje();
							msg.addParametro("dummy", "tantos");
						}						
					}
					else{// viene de mensaje
						if(creador){
							accion.setEnviarMensaje();
							msg.addParametro("tanteador", juego.invitado.nombre+":"+juego.manoActiva.totalTantosInvitado()+" "+juego.creador.nombre+":"+juego.manoActiva.totalTantosCreador());
						}
						else{								
							accion.setAccion(AccionTruco.ACCION_MOSTRAR_TANTOS);
							msg.addParametro("tanteador", comando.getParametro("tanteador"));
						}
					}
					accion.setMensaje(msg);
				}
			/*}
			else{
				accion = getMensaje(comando);
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accion;
	}

	

	private String valorToque(int comando) {
		if(comando == ComandoTruco.CMD_ENVIDO || comando == ComandoTruco.CMD_3_ENVIDO)
			return ""+comando;
		return "0"; //significa la falta envido
	}

	private AccionTruco getMensaje(ComandoBLGUI comando) {
		AccionTruco accion = new AccionTruco();
		accion.setAccion(AccionTruco.ACCION_ENVIAR_MENSAJE_MOSTRAR);
		MensajeEnvio msg = new MensajeEnvio(comando.getComando());
		accion.setMensaje(msg);
		return accion;
	}

	private AccionTruco comandosHabilitados() {
		// dependiendo del estado del juego retornamos los camndos habilitados
		return null;
	}

	public AccionBLGUI mensajeRecibido(MensajeAbstracto mensaje) {

		AccionTruco accion = new AccionTruco();
		accion.setMensaje(mensaje);
		
		//proceso mensaje de invitacion
		if(mensaje instanceof MensajeEnvio){
			
		}
		if(mensaje instanceof MensajeRespuesta){
			
		}
		
		if(mensaje.getComando()==ComandoTruco.CMD_CONTRA_FLOR){
			//accion.setAccion(AccionBLGUI.ACCION_MOSTRAR_MENSAJE);
		}
		
		return accion;
	}

	public void initMano() {
		juego.iniciarMano();
		juego.repartir();
		juego.creadorEsMano = !juego.creadorEsMano;
		juego.manoActiva.turnoCreador = juego.creadorEsMano;
				
	}

	public String idJuego() {
		if(juego != null)
			return juego.idJuego;
		return idJuego;
	}

	public String invitado() {
		// TODO Auto-generated method stub
		return juego.invitado.nombre;
	}

	public void setIdJuego(String id) {
		// TODO Auto-generated method stub
		idJuego = id;
	}
	
}
