package edu.tsi3.scrumme

import edu.tsi3.scrumme.util.EstadoReunion;

class Reunion {

	String titulo
	String lugar
	Date fechaInicio
	Date fechaFin
	EstadoReunion estado
	Proyecto proyecto
	Texto acta
	static hasMany = [ invitaciones : Invitacion, documentos : Documento ]
	
    static constraints = {
		titulo(blank:false)
		lugar(blank:false)
		fechaInicio(nullable:false)
		fechaFin(nullable:false)
		estado(nullable:false)
		proyecto(nullable:true)
		acta(nullable:true)
		invitaciones(nullable:true,size:2..999)
    }
}
 