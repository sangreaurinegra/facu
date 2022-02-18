package edu.tsi3.prototipo

import java.util.Date;

class Tarea {
	
	String nombre
	String descripcion
	Date dateCreated
	Date Updated
	
	static belongsTo = [ user : Usuario ]
	
	static mapping = {
		sort dateCreated:"desc"
	}
	
	static constraints = {
	}
}
