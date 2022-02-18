
package edu.tsi3.scrumme

class InicioController {

    def index = { 
    		
    	if(session.usuario){
    		def proyectos = UsuarioProyecto.findAllByUsuario(session.usuario).collect{it.proyecto}
    		
    		render view:'/index', model:[proyectos:proyectos]
    	}
    	else{
    		redirect uri:'/login/auth'
    	}
    	
    }
}
