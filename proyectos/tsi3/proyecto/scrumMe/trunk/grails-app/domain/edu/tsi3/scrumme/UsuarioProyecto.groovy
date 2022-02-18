package edu.tsi3.scrumme

class UsuarioProyecto {
	/*
	 * asociacion many to many entre usuario y proyecto, como ninguno pertenece al otro
	 * se debe crear esta clase asociacion
	 */
	Usuario usuario
	Proyecto proyecto
    static constraints = {
    }
}
