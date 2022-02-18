package edu.tsi4.truco.bl.truco;

import java.util.Hashtable;

import edu.tsi4.truco.bl.ComandoBLGUI;

public class ComandoTruco extends ComandoBLGUI {

	// definimos los comandos del juego
	public static final int CMD_REPARTIR = 0;
	public static final int CMD_ENVIDO = 2;
	public static final int CMD_REAL_ENVIDO = 5;
	public static final int CMD_3_ENVIDO = 3;
	public static final int CMD_FALTA_ENVIDO = 4;
	public static final int CMD_TRUCO = 1;
	public static final int CMD_RE_TRUCO = 6;
	public static final int CMD_VALE_4 = 7;
	public static final int CMD_FLOR = 8;
	public static final int CMD_CONTRA_FLOR = 9;
	public static final int CMD_2_FLOR = 10;
	public static final int CMD_3_FLOR = 11;
	public static final int CMD_5_FLOR = 12;
	public static final int CMD_JUGAR_CARTA = 13;
	public static final int CMD_QUIERO_ENVIDO = 14;
	public static final int CMD_NO_QUIERO_ENVIDO = 15;
	public static final int CMD_IR_AL_MASO = 16;
	
	
	//comandos internos del sistema
	public static final int CMD_HABILITADOS = 17;
	public static final int CMD_CARTAS_USUARIO = 18;
	public static final int CMD_INVITAR_USUARIO = 19;
	public static final int CMD_ACEPTO_INVITACION = 20;
	public static final int CMD_CARTA_JUGADA = 21;
	public static final int CMD_SET_LISTENER = 22;
	public static final int CMD_NO_QUIERO = 23; // no quiero para el truco, retruco y vale 4
	public static final int CMD_QUIERO = 24;
	public static final int CMD_TURNO = 25;
	public static final int CMD_TANTOS = 26;
	
	Carta jugada;
	
	public ComandoTruco(){
		this.parametros(new Hashtable());
	}
	
}
