package edu.tsi3.scrumme

import edu.tsi3.scrumme.util.EstadoInvitacion;

class Invitacion {

	Date fecha
	EstadoInvitacion estado
	Date fechaRespuesta
	Usuario usuario
	static belongsTo = [reunion:Reunion]
	
    static constraints = {
		fecha(nullable:false)
		estado(nullable:false)
		fechaRespuesta(nullable:true)
		usuario(nullable:false)
    }
}
