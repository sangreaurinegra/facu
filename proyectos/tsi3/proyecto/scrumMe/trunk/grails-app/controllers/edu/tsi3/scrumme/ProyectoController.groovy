package edu.tsi3.scrumme

import edu.tsi3.scrumme.util.EstadoProyecto;
import edu.tsi3.scrumme.util.*;
import java.text.SimpleDateFormat;


class ProyectoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
    	if(session.proyecto){
    		redirect action:'ver', id:session.proyecto
    		return
    	}
        redirect(action: "proyectos", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [proyectoInstanceList: Proyecto.list(params), proyectoInstanceTotal: Proyecto.count()]
    }

    def create = {
        def proyectoInstance = new Proyecto()
        proyectoInstance.properties = params
        return [proyectoInstance: proyectoInstance]
    }

    def save = {
        def proyectoInstance = new Proyecto(params)
        proyectoInstance.estado = EstadoProyecto.NO_INICIADO
        
        
      //limppio errores para setear fecha
        proyectoInstance.clearErrors();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm")
		
		def fechaIni = sdf.parse(params.fechaIni)
		proyectoInstance.fechaIni=fechaIni
		
		def fechaFin = sdf.parse(params.fechaFin)
		proyectoInstance.fechaFin=fechaFin
        
        if (proyectoInstance.save(flush: true)) {
        	//luego que salva correctamente asignamos el usuarios el proyecto
        	def u = Usuario.get(session.usuario.id)
        	def up = new UsuarioProyecto()
        	up.usuario = u
        	up.proyecto = proyectoInstance
        	up.save(flush: true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), proyectoInstance.id])}"
            redirect(uri: "/")
        }
        else {
            render(view: "create", model: [proyectoInstance: proyectoInstance])
        }
    }

    def show = {
        def proyectoInstance = Proyecto.get(params.id)
        if (!proyectoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), params.id])}"
            redirect(action: "list")
        }
        else {
            [proyectoInstance: proyectoInstance]
        }
    }

    def edit = {
        def proyectoInstance = Proyecto.get(params.id)
        if (!proyectoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [proyectoInstance: proyectoInstance]
        }
    }

    def update = {
        def proyectoInstance = Proyecto.get(params.id)
        if (proyectoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (proyectoInstance.version > version) {
                    
                    proyectoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'proyecto.label', default: 'Proyecto')] as Object[], "Another user has updated this Proyecto while you were editing")
                    render(view: "edit", model: [proyectoInstance: proyectoInstance])
                    return
                }
            }
            proyectoInstance.properties = params
            if (!proyectoInstance.hasErrors() && proyectoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), proyectoInstance.id])}"
                redirect(action: "show", id: proyectoInstance.id)
            }
            else {
                render(view: "edit", model: [proyectoInstance: proyectoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def proyectoInstance = Proyecto.get(params.id)
        if (proyectoInstance) {
            try {
                proyectoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def ver = {
    	session.proyecto = new Long(params.id)
    	def proyecto = Proyecto.get(params.id)
    	session.proy_nombre = proyecto.nombre
    	def u = Usuario.get(session.usuario.id)
		def sprint = Sprint.find("from Sprint as s where s.fechaIni <= ? and s.estado = ? and s.proyecto = ?",[new Date(),Sprint.ESTADO_DESA,proyecto])
		if(sprint)
			session.sprint_desa = sprint.id
    	if(u.tipo.id == grailsApplication.config.idTipoUsuarioEquipo){
	    	
	    	if(sprint){
	    		redirect(action:'dashboard',controller:'sprint',id:sprint.id)
	    	}
	    	else{
	    		redirect(action:'resumen',controller:'proyecto',id:params.id)
	    	}
    	}
    	else{
    		redirect(action:'resumen',controller:'proyecto',id:params.id)
    	}
    }
    
    def resumen = {
    	def proyectoInstance = Proyecto.get(params.id)
		def sprint = Sprint.find("from Sprint as s where s.fechaIni <= ? and s.estado = ? and s.proyecto = ?",[new Date(),Sprint.ESTADO_DESA,proyectoInstance])
		if(sprint)
			session.sprint_desa = sprint.id
        if (!proyectoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), params.id])}"
            redirect(action: "proyectos")
        }
        else {
            [proyecto: proyectoInstance]
        }
    }
    
    def asignar = {
    	def proyecto = Proyecto.get(params.id)
    	def usuarios = Usuario.findAllByEnabled(true)
    	[usuarios:usuarios, proyecto:proyecto]
    }
    
    def guardarAsignacion = {
    	println params
    	def proyecto = Proyecto.get(new Long( params.pid))
    	def usuarios = Usuario.findAllByEnabled(true)
    	usuarios.each {
    			
			def up = UsuarioProyecto.findByUsuarioAndProyecto(it,proyecto)
			def asignado = params["u_"+it.id]=='on'?true:false
			if(up){
				if(!asignado)
					up.delete(flush:true)
			}
			else{
				if(asignado){
					up= new UsuarioProyecto()
					up.usuario = it
					up.proyecto = proyecto
					up.save(flush:true)
				}
			}
    		
    	}
    	redirect action:'proyectos'
    }
    
    def proyectos = {
        [proyectos: Proyecto.list()]
    }
    
    def ini = {
        def proyecto = Proyecto.get(params.id)
        if (!proyecto) {         
            redirect(uri: "/")
        }
        else {
        	proyecto.estado= EstadoProyecto.EN_DESA
        	proyecto.save(flush:true)
            redirect(action: "ver",id:params.id)
        }
    }
    
    def fin = {
        def proyecto = Proyecto.get(params.id)
        if (!proyecto) {         
            redirect(uri: "/")
        }
        else {
        	proyecto.estado= EstadoProyecto.FINALIZADO
        	proyecto.save(flush:true)
            redirect(controller:'proyecto', action: "resumen",id:proyecto.id)
        }
    }
    
}
