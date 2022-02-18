package edu.tsi3.scrumme

import edu.tsi3.scrumme.util.*;

class TareaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tareaInstanceList: Tarea.list(params), tareaInstanceTotal: Tarea.count()]
    }

    def create = {
        def tareaInstance = new Tarea()
        tareaInstance.properties = params
        def proy = null
        if(params.sprintid){
        	def sp = Sprint.get(new Long(params.sprintid))
        	proy = sp.proyecto
        }
        else if(session.proyecto)
        	proy = Proyecto.get(session.proyecto)
        return [tareaInstance: tareaInstance, sprint:params.sprintid, proyecto:proy]
    }

    def save = {
        def tareaInstance = new Tarea(params)
        tareaInstance.estado = EstadoTarea.NO_INICIADA
        //def sp = Sprint.get(new Long)
        tareaInstance.proyecto = tareaInstance.sprint.proyecto
        tareaInstance.fechaCreacion = new Date()
        double est = new Double(params.estimacion)
        tareaInstance.estimacion  = est
        if (tareaInstance.save(flush: true)) {
        	
            if(params.sprintid){
            	redirect(action: "dashboard", controller:'sprint', id: params.sprintid)
            }
            else{
	        	flash.message = "${message(code: 'default.created.message', args: [message(code: 'tarea.label', default: 'Tarea'), tareaInstance.id])}"
	            redirect(action: "show", id: tareaInstance.id)
            }
        }
        else {
            render(view: "create", model: [tareaInstance: tareaInstance, sprint:params.sprintid])
        }
    }

    def show = {
        def tareaInstance = Tarea.get(params.id)
        if (!tareaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tareaInstance: tareaInstance]
        }
    }

    def edit = {
        def tareaInstance = Tarea.get(params.id)
        if (!tareaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tareaInstance: tareaInstance]
        }
    }

    def update = {
        def tareaInstance = Tarea.get(params.id)
        if (tareaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tareaInstance.version > version) {
                    
                    tareaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tarea.label', default: 'Tarea')] as Object[], "Another user has updated this Tarea while you were editing")
                    render(view: "edit", model: [tareaInstance: tareaInstance])
                    return
                }
            }
            tareaInstance.properties = params
            double est = new Double(params.estimacion)
            tareaInstance.estimacion  = est
            if (!tareaInstance.hasErrors() && tareaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tarea.label', default: 'Tarea'), tareaInstance.id])}"
                redirect(action: "show", id: tareaInstance.id)
            }
            else {
                render(view: "edit", model: [tareaInstance: tareaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tareaInstance = Tarea.get(params.id)
        if (tareaInstance) {
            try {
                tareaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def cambioEstado = {
		try{
	    	def tarea = Tarea.get(new Long(params.tarea))
	    	def newestado = EstadoTarea.NO_INICIADA 
	    	def salvar = false
			//le quito el 'px'
	    	def x = new Integer(params.x.substring (0, params.x.length()-2))
	    	def y = new Integer(params.y.substring (0, params.y.length()-2))
	    	
	    	if(params.estado == 'pready')
	    		newestado = EstadoTarea.FINALIZADA
			if(params.estado == 'pworking')
	    		newestado = EstadoTarea.EN_PROCESO
	    	// seteamos el usuario	
			if(tarea.estado == EstadoTarea.NO_INICIADA && newestado == EstadoTarea.EN_PROCESO){
	    		tarea.asignado = session.usuario
	    		tarea.fechaIni = new Date()
	    	}
	    	if(newestado == EstadoTarea.FINALIZADA && tarea.estado != EstadoTarea.FINALIZADA){
	    		tarea.fechaFin = new Date()
	    	}
			if(newestado == EstadoTarea.FINALIZADA && tarea.estado == EstadoTarea.NO_INICIADA){
				throw new Exception("No puede pasar una tarea no iniciada a finalizada.")
			}
	    	//seteamos el nuevo estado
	    	if(newestado != tarea.estado){
	    		tarea.estado = newestado
				//si cambio de estado ajusto el x al nuevo estado
				x -= grailsApplication.config.anchoDashboard
	    		salvar = true
	    	}
	    	//seteamos la posicion x
	    	if(tarea.x != x){
	    		tarea.x = x
	    		salvar = true
	    	}
	    	//seteamos la posicion y
	    	if(tarea.y != y){
	    		tarea.y = y
	    		salvar = true
	    	}	
	    	if(salvar)
	    		tarea.save (flush:true)
	    		
	    	render "ok"
		}
		catch(Exception e){
			log.error e
			render "error"
		}
    }
    
    def detalles ={
    	def tarea = Tarea.get(new Long(params.tarea))
    	render template:'detalles',model:[tarea:tarea]
    }
    
    def dialogoedit = {
        def tareaInstance = Tarea.get(params.id)
        if (!tareaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tarea.label', default: 'Tarea'), params.id])}"
            render("No se encontro la tarea")
        }
        else {
            render template:'dialogoedit',model:[tareaInstance: tareaInstance]
        }
    }
}
