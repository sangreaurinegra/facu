package edu.tsi4.truco.bl.truco;

public class Jugada {

	int estado;
	Carta cartaCreador;
	Carta cartaInvitado;
	
	public static final int ESTADO_GANA_CREADOR = 1;
	public static final int ESTADO_GANA_INVITADO = 2;
	public static final int ESTADO_PARDA = 3;
	
	
	public int ganador(Carta muestra){
		int res = cartaCreador.comparar(cartaInvitado, muestra);
		if(res > 0)
			estado = ESTADO_GANA_CREADOR;
		else if(res < 0)
			estado = ESTADO_GANA_INVITADO;
		else 
			estado = ESTADO_PARDA;
		return estado;
	}
}
