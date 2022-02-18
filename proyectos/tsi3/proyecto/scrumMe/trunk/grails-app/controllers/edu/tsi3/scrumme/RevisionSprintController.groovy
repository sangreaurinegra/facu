package edu.tsi3.scrumme

class RevisionSprintController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [revisionSprintInstanceList: RevisionSprint.list(params), revisionSprintInstanceTotal: RevisionSprint.count()]
    }

    def create = {
        def revisionSprintInstance = new RevisionSprint()
        revisionSprintInstance.properties = params
        return [revisionSprintInstance: revisionSprintInstance]
    }

    def save = {
        def revisionSprintInstance = new RevisionSprint(params)
        if (revisionSprintInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'revisionSprint.label', default: 'RevisionSprint'), revisionSprintInstance.id])}"
            redirect(action: "show", id: revisionSprintInstance.id)
        }
        else {
            render(view: "create", model: [revisionSprintInstance: revisionSprintInstance])
        }
    }

    def show = {
        def revisionSprintInstance = RevisionSprint.get(params.id)
        if (!revisionSprintInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'revisionSprint.label', default: 'RevisionSprint'), params.id])}"
            redirect(action: "list")
        }
        else {
            [revisionSprintInstance: revisionSprintInstance]
        }
    }

    def edit = {
        def revisionSprintInstance = RevisionSprint.get(params.id)
        if (!revisionSprintInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'revisionSprint.label', default: 'RevisionSprint'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [revisionSprintInstance: revisionSprintInstance]
        }
    }

    def update = {
        def revisionSprintInstance = RevisionSprint.get(params.id)
        if (revisionSprintInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (revisionSprintInstance.version > version) {
                    
                    revisionSprintInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'revisionSprint.label', default: 'RevisionSprint')] as Object[], "Another user has updated this RevisionSprint while you were editing")
                    render(view: "edit", model: [revisionSprintInstance: revisionSprintInstance])
                    return
                }
            }
            revisionSprintInstance.properties = params
            if (!revisionSprintInstance.hasErrors() && revisionSprintInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'revisionSprint.label', default: 'RevisionSprint'), revisionSprintInstance.id])}"
                redirect(action: "show", id: revisionSprintInstance.id)
            }
            else {
                render(view: "edit", model: [revisionSprintInstance: revisionSprintInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'revisionSprint.label', default: 'RevisionSprint'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def revisionSprintInstance = RevisionSprint.get(params.id)
        if (revisionSprintInstance) {
            try {
                revisionSprintInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'revisionSprint.label', default: 'RevisionSprint'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'revisionSprint.label', default: 'RevisionSprint'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'revisionSprint.label', default: 'RevisionSprint'), params.id])}"
            redirect(action: "list")
        }
    }
}
