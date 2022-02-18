package edu.tsi3.scrumme

import edu.tsi3.scrumme.util.*;
import java.text.SimpleDateFormat;

class SprintController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [sprintInstanceList: Sprint.list(params), sprintInstanceTotal: Sprint.count()]
    }

    def create = {
        def sprintInstance = new Sprint()
        sprintInstance.properties = params
        return [sprintInstance: sprintInstance, pid:params.pid]
    }

    def save = {
        def sprintInstance = new Sprint(params)
        
        sprintInstance.clearErrors();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm")
		
		def fechaIni = sdf.parse(params.fechaIni)
		sprintInstance.fechaIni=fechaIni
		
		def fechaFin = sdf.parse(params.fechaFin)
		sprintInstance.fechaFin=fechaFin
        
        
        if (sprintInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'sprint.label', default: 'Sprint'), sprintInstance.id])}"
            if(params.pid)
            	redirect(controller:'proyecto', action: "proyectos")
            else
            	redirect(action: "show", id: sprintInstance.id)
        }
        else {
            render(view: "create", model: [sprintInstance: sprintInstance])
        }
    }

    def show = {
        def sprintInstance = Sprint.get(params.id)
        if (!sprintInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sprint.label', default: 'Sprint'), params.id])}"
            redirect(action: "list")
        }
        else {
            [sprintInstance: sprintInstance]
        }
    }

    def edit = {
        def sprintInstance = Sprint.get(params.id)
        if (!sprintInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sprint.label', default: 'Sprint'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [sprintInstance: sprintInstance]
        }
    }

    def update = {
        def sprintInstance = Sprint.get(params.id)
        if (sprintInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (sprintInstance.version > version) {
                    
                    sprintInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'sprint.label', default: 'Sprint')] as Object[], "Another user has updated this Sprint while you were editing")
                    render(view: "edit", model: [sprintInstance: sprintInstance])
                    return
                }
            }
            sprintInstance.properties = params
            if (!sprintInstance.hasErrors() && sprintInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'sprint.label', default: 'Sprint'), sprintInstance.id])}"
                redirect(action: "show", id: sprintInstance.id)
            }
            else {
                render(view: "edit", model: [sprintInstance: sprintInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sprint.label', default: 'Sprint'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def sprintInstance = Sprint.get(params.id)
        if (sprintInstance) {
            try {
                sprintInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'sprint.label', default: 'Sprint'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'sprint.label', default: 'Sprint'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sprint.label', default: 'Sprint'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def dashboard = {
    	def sprintInstance = Sprint.get(params.id)
        if (!sprintInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sprint.label', default: 'Sprint'), params.id])}"
            redirect(action: "list")
        }
        else {
			if(sprintInstance.estado == Sprint.ESTADO_DESA)
				session.sprint_desa = sprintInstance.id
			def usuarios = []
			def usu = new Usuario(userRealName:'TODOS',id:-1)
			//usuarios.add usu
			usuarios.addAll UsuarioProyecto.findAllByProyecto(sprintInstance.proyecto).collect{it.usuario}
			if(params.u){
				usu = Usuario.get(new Long(params.u))
	        	def todo = Tarea.findAll("from Tarea as s where s.sprint = ? and s.estado = ? and s.asignado = ?",[sprintInstance, EstadoTarea.NO_INICIADA,usu])
	        	def working = Tarea.findAll("from Tarea as s where s.sprint = ? and s.estado = ? and s.asignado = ?",[sprintInstance, EstadoTarea.EN_PROCESO,usu])
	        	def ready = Tarea.findAll("from Tarea as s where s.sprint = ? and s.estado = ? and s.asignado = ?",[sprintInstance, EstadoTarea.FINALIZADA,usu])
	            return [sprint: sprintInstance, todo:todo, working:working, ready:ready, usuarios:usuarios]
			}
			else {
				def todo = Tarea.findAllBySprintAndEstado(sprintInstance, EstadoTarea.NO_INICIADA)
				def working = Tarea.findAllBySprintAndEstado(sprintInstance, EstadoTarea.EN_PROCESO)
				def ready = Tarea.findAllBySprintAndEstado(sprintInstance, EstadoTarea.FINALIZADA)
				return [sprint: sprintInstance, todo:todo, working:working, ready:ready, usuarios:usuarios]
			}
        }
    }
    
    def ini = {
        def sprintInstance = Sprint.get(params.id)
        if (!sprintInstance) {         
            redirect(uri: "/")
        }
        else {
        	sprintInstance.estado= Sprint.ESTADO_DESA
        	sprintInstance.save(flush:true)
            redirect(action: "dashboard",id:params.id)
        }
    }
    
    def fin = {
        def sprintInstance = Sprint.get(params.id)
        if (!sprintInstance) {         
            redirect(uri: "/")
        }
        else {
        	sprintInstance.estado= Sprint.ESTADO_FINALIZADO
        	sprintInstance.save(flush:true)
            redirect(controller:'proyecto', action: "resumen",id:sprintInstance.proyecto.id)
        }
    }
}
