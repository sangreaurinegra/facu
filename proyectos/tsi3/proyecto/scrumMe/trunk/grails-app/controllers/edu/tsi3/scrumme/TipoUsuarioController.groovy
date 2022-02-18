package edu.tsi3.scrumme

class TipoUsuarioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tipoUsuarioInstanceList: TipoUsuario.list(params), tipoUsuarioInstanceTotal: TipoUsuario.count()]
    }

    def create = {
        def tipoUsuarioInstance = new TipoUsuario()
        tipoUsuarioInstance.properties = params
        return [tipoUsuarioInstance: tipoUsuarioInstance]
    }

    def save = {
        def tipoUsuarioInstance = new TipoUsuario(params)
        if (tipoUsuarioInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tipoUsuario.label', default: 'TipoUsuario'), tipoUsuarioInstance.id])}"
            redirect(action: "show", id: tipoUsuarioInstance.id)
        }
        else {
            render(view: "create", model: [tipoUsuarioInstance: tipoUsuarioInstance])
        }
    }

    def show = {
        def tipoUsuarioInstance = TipoUsuario.get(params.id)
        if (!tipoUsuarioInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipoUsuario.label', default: 'TipoUsuario'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tipoUsuarioInstance: tipoUsuarioInstance]
        }
    }

    def edit = {
        def tipoUsuarioInstance = TipoUsuario.get(params.id)
        if (!tipoUsuarioInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipoUsuario.label', default: 'TipoUsuario'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tipoUsuarioInstance: tipoUsuarioInstance]
        }
    }

    def update = {
        def tipoUsuarioInstance = TipoUsuario.get(params.id)
        if (tipoUsuarioInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tipoUsuarioInstance.version > version) {
                    
                    tipoUsuarioInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tipoUsuario.label', default: 'TipoUsuario')] as Object[], "Another user has updated this TipoUsuario while you were editing")
                    render(view: "edit", model: [tipoUsuarioInstance: tipoUsuarioInstance])
                    return
                }
            }
            tipoUsuarioInstance.properties = params
            if (!tipoUsuarioInstance.hasErrors() && tipoUsuarioInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tipoUsuario.label', default: 'TipoUsuario'), tipoUsuarioInstance.id])}"
                redirect(action: "show", id: tipoUsuarioInstance.id)
            }
            else {
                render(view: "edit", model: [tipoUsuarioInstance: tipoUsuarioInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipoUsuario.label', default: 'TipoUsuario'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tipoUsuarioInstance = TipoUsuario.get(params.id)
        if (tipoUsuarioInstance) {
            try {
                tipoUsuarioInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tipoUsuario.label', default: 'TipoUsuario'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tipoUsuario.label', default: 'TipoUsuario'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipoUsuario.label', default: 'TipoUsuario'), params.id])}"
            redirect(action: "list")
        }
    }
}
