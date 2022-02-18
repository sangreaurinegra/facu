package edu.tsi3.scrumme

class ProyectoTagLib {

	static namespace = "scrumme"
		
	// recibe el nombre del proyecto
	// el id y estado
	def caratula = { attrs->
		//def texto = Proyecto.textoEstado(new Integer(attrs.estado))
		out << render (template:'../proyecto/caratula',
				model:[
				       nombre:attrs.nombre,
				       idproy:attrs.idproy,
				       estado:attrs.estado //texto
				       ])
	}
}
