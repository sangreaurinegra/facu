package edu.tsi3.planningpoker

class InvitacionJuegoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [invitacionJuegoInstanceList: InvitacionJuego.list(params), invitacionJuegoInstanceTotal: InvitacionJuego.count()]
    }

    def create = {
        def invitacionJuegoInstance = new InvitacionJuego()
        invitacionJuegoInstance.properties = params
        return [invitacionJuegoInstance: invitacionJuegoInstance]
    }

    def save = {
        def invitacionJuegoInstance = new InvitacionJuego(params)
        if (invitacionJuegoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego'), invitacionJuegoInstance.id])}"
            redirect(action: "show", id: invitacionJuegoInstance.id)
        }
        else {
            render(view: "create", model: [invitacionJuegoInstance: invitacionJuegoInstance])
        }
    }

    def show = {
        def invitacionJuegoInstance = InvitacionJuego.get(params.id)
        if (!invitacionJuegoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego'), params.id])}"
            redirect(action: "list")
        }
        else {
            [invitacionJuegoInstance: invitacionJuegoInstance]
        }
    }

    def edit = {
        def invitacionJuegoInstance = InvitacionJuego.get(params.id)
        if (!invitacionJuegoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [invitacionJuegoInstance: invitacionJuegoInstance]
        }
    }

    def update = {
        def invitacionJuegoInstance = InvitacionJuego.get(params.id)
        if (invitacionJuegoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (invitacionJuegoInstance.version > version) {
                    
                    invitacionJuegoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'invitacionJuego.label', default: 'InvitacionJuego')] as Object[], "Another user has updated this InvitacionJuego while you were editing")
                    render(view: "edit", model: [invitacionJuegoInstance: invitacionJuegoInstance])
                    return
                }
            }
            invitacionJuegoInstance.properties = params
            if (!invitacionJuegoInstance.hasErrors() && invitacionJuegoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego'), invitacionJuegoInstance.id])}"
                redirect(action: "show", id: invitacionJuegoInstance.id)
            }
            else {
                render(view: "edit", model: [invitacionJuegoInstance: invitacionJuegoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def invitacionJuegoInstance = InvitacionJuego.get(params.id)
        if (invitacionJuegoInstance) {
            try {
                invitacionJuegoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def pendientes = {
        
        [invitacionJuegoInstanceList: InvitacionJuego.findAllByUsuarioAndEstado(session.usuario,InvitacionJuego.ESTADO_PENDIENTE), 
         invitacionJuegoInstanceTotal: InvitacionJuego.count()]
    }
    
    def aceptar = {
    	def invitacionJuegoInstance = InvitacionJuego.get(params.id)
    	invitacionJuegoInstance.estado = InvitacionJuego.ESTADO_ACEPTADA
    	invitacionJuegoInstance.save(flush:true)
    	redirect(action:'index', controller:'juego')
    }
    
}
