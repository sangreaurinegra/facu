package edu.tsi3.planningpoker

import edu.tsi3.scrumme.Tarea;

class Mano {

	public static int ESTADO_EN_JUEGO = 0
	public static int ESTADO_FINALIZADA = 1
	
	
	Tarea tarea
	Juego juego
	int estado
	double estimacion
    static constraints = {
    }
	
	
	String textoEstado(){
		if (estado == ESTADO_EN_JUEGO)
			return "En juego"
		else
			return "Finalizada"
	}
}
