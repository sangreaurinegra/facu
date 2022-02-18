package edu.tsi3.planningpoker

import edu.tsi3.scrumme.Usuario;

class Juego {

	public static int ESTADO_POR_INICIAR = 0
	public static int ESTADO_ACTIVO = 1
	public static int ESTADO_FINALIZADO = 2
	
	Usuario creador
	Date fechaIni
	Date fechaFin
	int estado
	String nombre
	static hasMany = [invitados: InvitacionJuego, manos: Mano]
    static constraints = {
		fechaFin(nullable:true)
		fechaIni(nullable:true)
		manos(nullable:true)
    }
	
	String toString(){
		nombre
	}
}
