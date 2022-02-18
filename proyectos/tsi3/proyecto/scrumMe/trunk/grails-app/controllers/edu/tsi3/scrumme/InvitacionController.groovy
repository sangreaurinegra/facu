package edu.tsi3.scrumme

import edu.tsi3.scrumme.util.*;

class InvitacionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [invitacionInstanceList: Invitacion.list(params), invitacionInstanceTotal: Invitacion.count()]
    }

    def create = {
        def invitacionInstance = new Invitacion()
        invitacionInstance.properties = params
        return [invitacionInstance: invitacionInstance]
    }

    def save = {
        def invitacionInstance = new Invitacion(params)
        if (invitacionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'invitacion.label', default: 'Invitacion'), invitacionInstance.id])}"
            redirect(action: "show", id: invitacionInstance.id)
        }
        else {
            render(view: "create", model: [invitacionInstance: invitacionInstance])
        }
    }

    def show = {
        def invitacionInstance = Invitacion.get(params.id)
        if (!invitacionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'invitacion.label', default: 'Invitacion'), params.id])}"
            redirect(action: "list")
        }
        else {
            [invitacionInstance: invitacionInstance]
        }
    }

    def edit = {
        def invitacionInstance = Invitacion.get(params.id)
        if (!invitacionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'invitacion.label', default: 'Invitacion'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [invitacionInstance: invitacionInstance]
        }
    }

    def update = {
        def invitacionInstance = Invitacion.get(params.id)
        if (invitacionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (invitacionInstance.version > version) {
                    
                    invitacionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'invitacion.label', default: 'Invitacion')] as Object[], "Another user has updated this Invitacion while you were editing")
                    render(view: "edit", model: [invitacionInstance: invitacionInstance])
                    return
                }
            }
            invitacionInstance.properties = params
            if (!invitacionInstance.hasErrors() && invitacionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'invitacion.label', default: 'Invitacion'), invitacionInstance.id])}"
                redirect(action: "show", id: invitacionInstance.id)
            }
            else {
                render(view: "edit", model: [invitacionInstance: invitacionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'invitacion.label', default: 'Invitacion'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def invitacionInstance = Invitacion.get(params.id)
        if (invitacionInstance) {
            try {
                invitacionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'invitacion.label', default: 'Invitacion'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'invitacion.label', default: 'Invitacion'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'invitacion.label', default: 'Invitacion'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def feed = {
		render(feedType:"rss", feedVersion:"2.0") {
			title = "Feed Invitaciones"
			link = "http://localhost:8080/scrumme/invitacion/feed"
			description = "Feed de Mensajes"
			Invitacion.list().each() { 
				Mensaje -> entry(Invitacion.reunion.titulo) { link = "http://localhost:8080/scrumMe/invitacion/show/${Mensaje.id}" 
					Invitacion.reunion.lugar // return the content 
				}
			}
		}
	} 
    
    def aceptar = {
        def invitacionInstance = Invitacion.get(params.id)
        if (!invitacionInstance) {
            log.error "invitacion:"+params.id+" no existe!!!"
            render "error"
        }
        else {
            invitacionInstance.estado = EstadoInvitacion.ACEPTADA
            invitacionInstance.save(flush:true)
            render "ok"
        }
    }
    
    def rechazar = {
        def invitacionInstance = Invitacion.get(params.id)
        if (!invitacionInstance) {
            log.error "invitacion:"+params.id+" no existe!!!"
            render "error"
        }
        else {
            invitacionInstance.estado = EstadoInvitacion.RECHAZADA
            invitacionInstance.save(flush:true)
            render "ok"
        }
    }
    
    
    
}
