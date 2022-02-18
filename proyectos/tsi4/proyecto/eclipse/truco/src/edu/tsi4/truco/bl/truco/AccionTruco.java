package edu.tsi4.truco.bl.truco;

import edu.tsi4.truco.bl.AccionBLGUI;

public class AccionTruco extends AccionBLGUI {

	public static final int ACCION_ENVIAR_MENSAJE = 1;
	public static final int ACCION_ENVIAR_MENSAJE_MOSTRAR = 2;
	public static final int ACCION_MOSTRAR_MENSAJE = 3;
	public static final int ACCION_COMANDOS = 11;
	public static final int ACCION_NO_QUIERO_TRUCO = 13;
	public static final int ACCION_MOSTRAR_CARTAS = 14;
	public static final int ACCION_CARTA_JUGADA = 15;
	public static final int ACCION_FIN_MANO = 16;
	public static final int ACCION_TURNO = 17;
	public static final int ACCION_MOSTRAR_TANTOS = 18;
	public static final int ACCION_GRITE_TRUCO = 19;
	public static final int ACCION_QUIERO_TRUCO = 20;
	public static final int ACCION_GRITE_RE_TRUCO = 21;
	public static final int ACCION_GRITE_VALE_4 = 22;
	
	//RESPUESTA CARTAS USUARIO
	public Carta cartas[];
	
	//RESPUESTA JUGAR CARTA
	public Carta jugada;
	
	public boolean finMano = false;
	
}
