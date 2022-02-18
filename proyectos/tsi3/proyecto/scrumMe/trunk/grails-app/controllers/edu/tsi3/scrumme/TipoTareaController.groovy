package edu.tsi3.scrumme

class TipoTareaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tipoTareaInstanceList: TipoTarea.list(params), tipoTareaInstanceTotal: TipoTarea.count()]
    }

    def create = {
        def tipoTareaInstance = new TipoTarea()
        tipoTareaInstance.properties = params
        return [tipoTareaInstance: tipoTareaInstance]
    }

    def save = {
        def tipoTareaInstance = new TipoTarea(params)
        if (tipoTareaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'tipoTarea.label', default: 'TipoTarea'), tipoTareaInstance.id])}"
            redirect(action: "show", id: tipoTareaInstance.id)
        }
        else {
            render(view: "create", model: [tipoTareaInstance: tipoTareaInstance])
        }
    }

    def show = {
        def tipoTareaInstance = TipoTarea.get(params.id)
        if (!tipoTareaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipoTarea.label', default: 'TipoTarea'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tipoTareaInstance: tipoTareaInstance]
        }
    }

    def edit = {
        def tipoTareaInstance = TipoTarea.get(params.id)
        if (!tipoTareaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipoTarea.label', default: 'TipoTarea'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [tipoTareaInstance: tipoTareaInstance]
        }
    }

    def update = {
        def tipoTareaInstance = TipoTarea.get(params.id)
        if (tipoTareaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (tipoTareaInstance.version > version) {
                    
                    tipoTareaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'tipoTarea.label', default: 'TipoTarea')] as Object[], "Another user has updated this TipoTarea while you were editing")
                    render(view: "edit", model: [tipoTareaInstance: tipoTareaInstance])
                    return
                }
            }
            tipoTareaInstance.properties = params
            if (!tipoTareaInstance.hasErrors() && tipoTareaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tipoTarea.label', default: 'TipoTarea'), tipoTareaInstance.id])}"
                redirect(action: "show", id: tipoTareaInstance.id)
            }
            else {
                render(view: "edit", model: [tipoTareaInstance: tipoTareaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipoTarea.label', default: 'TipoTarea'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def tipoTareaInstance = TipoTarea.get(params.id)
        if (tipoTareaInstance) {
            try {
                tipoTareaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tipoTarea.label', default: 'TipoTarea'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tipoTarea.label', default: 'TipoTarea'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipoTarea.label', default: 'TipoTarea'), params.id])}"
            redirect(action: "list")
        }
    }
}
