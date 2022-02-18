package edu.tsi4.truco.bl;

import edu.tsi4.truco.bl.truco.Juego;
import edu.tsi4.truco.bl.truco.Usuario;
import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.config.ConfigManager;

public interface IJuegoHandler {

	//crea un juego
	public void crearJuego(String invitado, String celular);
	
	// inicia un juego
	public void initJuego(String invitado, String celular);
	
	// inicia un juego
	public void initMano();
	
	// pausa un juego
	public void pausarJuego();
	
	// finaliza un juego
	public void finalizarJuego();
	
	public AccionBLGUI ejecutarComando(ComandoBLGUI comando, boolean creador);
	
	public AccionBLGUI mensajeRecibido(MensajeAbstracto mensaje);
	
	public String idJuego();
	
	public void setIdJuego(String id);
	
	public String invitado();
	
}
