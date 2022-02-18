package edu.tsi3.planningpoker

import edu.tsi3.scrumme.Usuario;

class InvitacionJuego {

	public static int ESTADO_PENDIENTE = 0
	public static int ESTADO_ACEPTADA = 1
	public static int ESTADO_CONECTADO = 2
	
	Usuario usuario
	Juego juego
	int estado
    static constraints = {
    }
}
