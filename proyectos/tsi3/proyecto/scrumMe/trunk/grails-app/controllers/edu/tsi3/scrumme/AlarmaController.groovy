package edu.tsi3.scrumme

class AlarmaController {
//alarma!!!!
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [alarmaInstanceList: Alarma.list(params), alarmaInstanceTotal: Alarma.count()]
    }

    def create = {
        def alarmaInstance = new Alarma()
        alarmaInstance.properties = params
        return [alarmaInstance: alarmaInstance]
    }

    def save = {
        def alarmaInstance = new Alarma(params)
        if (alarmaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'alarma.label', default: 'Alarma'), alarmaInstance.id])}"
            redirect(action: "show", id: alarmaInstance.id)
        }
        else {
            render(view: "create", model: [alarmaInstance: alarmaInstance])
        }
    }

    def show = {
        def alarmaInstance = Alarma.get(params.id)
        if (!alarmaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'alarma.label', default: 'Alarma'), params.id])}"
            redirect(action: "list")
        }
        else {
            [alarmaInstance: alarmaInstance]
        }
    }

    def edit = {
        def alarmaInstance = Alarma.get(params.id)
        if (!alarmaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'alarma.label', default: 'Alarma'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [alarmaInstance: alarmaInstance]
        }
    }

    def update = {
        def alarmaInstance = Alarma.get(params.id)
        if (alarmaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (alarmaInstance.version > version) {
                    
                    alarmaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'alarma.label', default: 'Alarma')] as Object[], "Another user has updated this Alarma while you were editing")
                    render(view: "edit", model: [alarmaInstance: alarmaInstance])
                    return
                }
            }
            alarmaInstance.properties = params
            if (!alarmaInstance.hasErrors() && alarmaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'alarma.label', default: 'Alarma'), alarmaInstance.id])}"
                redirect(action: "show", id: alarmaInstance.id)
            }
            else {
                render(view: "edit", model: [alarmaInstance: alarmaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'alarma.label', default: 'Alarma'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def alarmaInstance = Alarma.get(params.id)
        if (alarmaInstance) {
            try {
                alarmaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'alarma.label', default: 'Alarma'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'alarma.label', default: 'Alarma'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'alarma.label', default: 'Alarma'), params.id])}"
            redirect(action: "list")
        }
    }
}
