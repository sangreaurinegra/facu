package edu.tsi3.scrumme

class UsuarioProyectoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [usuarioProyectoInstanceList: UsuarioProyecto.list(params), usuarioProyectoInstanceTotal: UsuarioProyecto.count()]
    }

    def create = {
        def usuarioProyectoInstance = new UsuarioProyecto()
        usuarioProyectoInstance.properties = params
        return [usuarioProyectoInstance: usuarioProyectoInstance]
    }

    def save = {
        def usuarioProyectoInstance = new UsuarioProyecto(params)
        if (usuarioProyectoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto'), usuarioProyectoInstance.id])}"
            redirect(action: "show", id: usuarioProyectoInstance.id)
        }
        else {
            render(view: "create", model: [usuarioProyectoInstance: usuarioProyectoInstance])
        }
    }

    def show = {
        def usuarioProyectoInstance = UsuarioProyecto.get(params.id)
        if (!usuarioProyectoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto'), params.id])}"
            redirect(action: "list")
        }
        else {
            [usuarioProyectoInstance: usuarioProyectoInstance]
        }
    }

    def edit = {
        def usuarioProyectoInstance = UsuarioProyecto.get(params.id)
        if (!usuarioProyectoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [usuarioProyectoInstance: usuarioProyectoInstance]
        }
    }

    def update = {
        def usuarioProyectoInstance = UsuarioProyecto.get(params.id)
        if (usuarioProyectoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (usuarioProyectoInstance.version > version) {
                    
                    usuarioProyectoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto')] as Object[], "Another user has updated this UsuarioProyecto while you were editing")
                    render(view: "edit", model: [usuarioProyectoInstance: usuarioProyectoInstance])
                    return
                }
            }
            usuarioProyectoInstance.properties = params
            if (!usuarioProyectoInstance.hasErrors() && usuarioProyectoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto'), usuarioProyectoInstance.id])}"
                redirect(action: "show", id: usuarioProyectoInstance.id)
            }
            else {
                render(view: "edit", model: [usuarioProyectoInstance: usuarioProyectoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def usuarioProyectoInstance = UsuarioProyecto.get(params.id)
        if (usuarioProyectoInstance) {
            try {
                usuarioProyectoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto'), params.id])}"
            redirect(action: "list")
        }
    }
}
