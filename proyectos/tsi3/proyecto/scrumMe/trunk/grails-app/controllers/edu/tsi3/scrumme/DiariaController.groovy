package edu.tsi3.scrumme

class DiariaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [diariaInstanceList: Diaria.list(params), diariaInstanceTotal: Diaria.count()]
    }

    def create = {
        def diariaInstance = new Diaria()
        diariaInstance.properties = params
        return [diariaInstance: diariaInstance]
    }

    def save = {
        def diariaInstance = new Diaria(params)
        if (diariaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'diaria.label', default: 'Diaria'), diariaInstance.id])}"
            redirect(action: "show", id: diariaInstance.id)
        }
        else {
            render(view: "create", model: [diariaInstance: diariaInstance])
        }
    }

    def show = {
        def diariaInstance = Diaria.get(params.id)
        if (!diariaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'diaria.label', default: 'Diaria'), params.id])}"
            redirect(action: "list")
        }
        else {
            [diariaInstance: diariaInstance]
        }
    }

    def edit = {
        def diariaInstance = Diaria.get(params.id)
        if (!diariaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'diaria.label', default: 'Diaria'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [diariaInstance: diariaInstance]
        }
    }

    def update = {
        def diariaInstance = Diaria.get(params.id)
        if (diariaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (diariaInstance.version > version) {
                    
                    diariaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'diaria.label', default: 'Diaria')] as Object[], "Another user has updated this Diaria while you were editing")
                    render(view: "edit", model: [diariaInstance: diariaInstance])
                    return
                }
            }
            diariaInstance.properties = params
            if (!diariaInstance.hasErrors() && diariaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'diaria.label', default: 'Diaria'), diariaInstance.id])}"
                redirect(action: "show", id: diariaInstance.id)
            }
            else {
                render(view: "edit", model: [diariaInstance: diariaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'diaria.label', default: 'Diaria'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def diariaInstance = Diaria.get(params.id)
        if (diariaInstance) {
            try {
                diariaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'diaria.label', default: 'Diaria'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'diaria.label', default: 'Diaria'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'diaria.label', default: 'Diaria'), params.id])}"
            redirect(action: "list")
        }
    }
}
