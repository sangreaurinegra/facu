package edu.tsi3.scrumme

class Archivo {

	String nombre
	Date fecha
	Proyecto proyecto
	
    static constraints = {
		nombre(nullable:false)
		fecha(nullable:false)
		proyecto(nullable:true)
    }
}
