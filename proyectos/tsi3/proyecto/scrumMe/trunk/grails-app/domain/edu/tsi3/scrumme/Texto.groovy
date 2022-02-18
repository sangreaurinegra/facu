package edu.tsi3.scrumme

class Texto {

	String texto
	static belongsTo = [reunion:Reunion] 
	
    static constraints = {
		texto(nullable:true)
    }
}
