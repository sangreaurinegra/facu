package edu.tsi3.planningpoker

class CartaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [cartaInstanceList: Carta.list(params), cartaInstanceTotal: Carta.count()]
    }

    def create = {
        def cartaInstance = new Carta()
        cartaInstance.properties = params
        return [cartaInstance: cartaInstance]
    }

    def save = {
        def cartaInstance = new Carta(params)
        if (cartaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'carta.label', default: 'Carta'), cartaInstance.id])}"
            redirect(action: "show", id: cartaInstance.id)
        }
        else {
            render(view: "create", model: [cartaInstance: cartaInstance])
        }
    }

    def show = {
        def cartaInstance = Carta.get(params.id)
        if (!cartaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'carta.label', default: 'Carta'), params.id])}"
            redirect(action: "list")
        }
        else {
            [cartaInstance: cartaInstance]
        }
    }

    def edit = {
        def cartaInstance = Carta.get(params.id)
        if (!cartaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'carta.label', default: 'Carta'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [cartaInstance: cartaInstance]
        }
    }

    def update = {
        def cartaInstance = Carta.get(params.id)
        if (cartaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (cartaInstance.version > version) {
                    
                    cartaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'carta.label', default: 'Carta')] as Object[], "Another user has updated this Carta while you were editing")
                    render(view: "edit", model: [cartaInstance: cartaInstance])
                    return
                }
            }
            cartaInstance.properties = params
            if (!cartaInstance.hasErrors() && cartaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'carta.label', default: 'Carta'), cartaInstance.id])}"
                redirect(action: "show", id: cartaInstance.id)
            }
            else {
                render(view: "edit", model: [cartaInstance: cartaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'carta.label', default: 'Carta'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def cartaInstance = Carta.get(params.id)
        if (cartaInstance) {
            try {
                cartaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'carta.label', default: 'Carta'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'carta.label', default: 'Carta'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'carta.label', default: 'Carta'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def imagen = {
    	def carta = Carta.get(params.id)
        
        response.contentType = 'image/jpeg'
        response.outputStream << carta.imagen
        response.outputStream.flush()
       

    }
}
