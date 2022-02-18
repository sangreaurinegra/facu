

package edu.tsi3.scrumme
import edu.tsi3.scrumme.util.EstadoProyecto;

class Proyecto {

	String nombre
	Date fechaIni
	Date fechaFin
	EstadoProyecto estado
	
	static hasMany = [sprints : Sprint, backlog: Tarea, usuarios: UsuarioProyecto]
	static constraints = {
		nombre(nullable:false)
		fechaIni(nullable:false)
		fechaFin(nullable:false)
	}
	
	String toString(){
		nombre+":"+estado.nombreEstado
	}
}
