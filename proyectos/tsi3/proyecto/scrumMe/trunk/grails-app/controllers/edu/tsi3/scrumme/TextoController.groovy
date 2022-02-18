package edu.tsi3.scrumme

class TextoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [textoInstanceList: Texto.list(params), textoInstanceTotal: Texto.count()]
    }

    def create = {
        def textoInstance = new Texto()
        textoInstance.properties = params
        return [textoInstance: textoInstance]
    }

    def save = {
        def textoInstance = new Texto(params)
        if (textoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'texto.label', default: 'Texto'), textoInstance.id])}"
            redirect(action: "show", id: textoInstance.id)
        }
        else {
            render(view: "create", model: [textoInstance: textoInstance])
        }
    }

    def show = {
        def textoInstance = Texto.get(params.id)
        if (!textoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'texto.label', default: 'Texto'), params.id])}"
            redirect(action: "list")
        }
        else {
            [textoInstance: textoInstance]
        }
    }

    def edit = {
        def textoInstance = Texto.get(params.id)
        if (!textoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'texto.label', default: 'Texto'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [textoInstance: textoInstance]
        }
    }

    def update = {
        def textoInstance = Texto.get(params.id)
        if (textoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (textoInstance.version > version) {
                    
                    textoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'texto.label', default: 'Texto')] as Object[], "Another user has updated this Texto while you were editing")
                    render(view: "edit", model: [textoInstance: textoInstance])
                    return
                }
            }
            textoInstance.properties = params
            if (!textoInstance.hasErrors() && textoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'texto.label', default: 'Texto'), textoInstance.id])}"
                redirect(action: "show", id: textoInstance.id)
            }
            else {
                render(view: "edit", model: [textoInstance: textoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'texto.label', default: 'Texto'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def textoInstance = Texto.get(params.id)
        if (textoInstance) {
            try {
                textoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'texto.label', default: 'Texto'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'texto.label', default: 'Texto'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'texto.label', default: 'Texto'), params.id])}"
            redirect(action: "list")
        }
    }
}
