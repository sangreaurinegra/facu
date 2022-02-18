package edu.tsi3.scrumme

class PlanSprintController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [planSprintInstanceList: PlanSprint.list(params), planSprintInstanceTotal: PlanSprint.count()]
    }

    def create = {
        def planSprintInstance = new PlanSprint()
        planSprintInstance.properties = params
        return [planSprintInstance: planSprintInstance]
    }

    def save = {
        def planSprintInstance = new PlanSprint(params)
        if (planSprintInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'planSprint.label', default: 'PlanSprint'), planSprintInstance.id])}"
            redirect(action: "show", id: planSprintInstance.id)
        }
        else {
            render(view: "create", model: [planSprintInstance: planSprintInstance])
        }
    }

    def show = {
        def planSprintInstance = PlanSprint.get(params.id)
        if (!planSprintInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'planSprint.label', default: 'PlanSprint'), params.id])}"
            redirect(action: "list")
        }
        else {
            [planSprintInstance: planSprintInstance]
        }
    }

    def edit = {
        def planSprintInstance = PlanSprint.get(params.id)
        if (!planSprintInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'planSprint.label', default: 'PlanSprint'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [planSprintInstance: planSprintInstance]
        }
    }

    def update = {
        def planSprintInstance = PlanSprint.get(params.id)
        if (planSprintInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (planSprintInstance.version > version) {
                    
                    planSprintInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'planSprint.label', default: 'PlanSprint')] as Object[], "Another user has updated this PlanSprint while you were editing")
                    render(view: "edit", model: [planSprintInstance: planSprintInstance])
                    return
                }
            }
            planSprintInstance.properties = params
            if (!planSprintInstance.hasErrors() && planSprintInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'planSprint.label', default: 'PlanSprint'), planSprintInstance.id])}"
                redirect(action: "show", id: planSprintInstance.id)
            }
            else {
                render(view: "edit", model: [planSprintInstance: planSprintInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'planSprint.label', default: 'PlanSprint'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def planSprintInstance = PlanSprint.get(params.id)
        if (planSprintInstance) {
            try {
                planSprintInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'planSprint.label', default: 'PlanSprint'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'planSprint.label', default: 'PlanSprint'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'planSprint.label', default: 'PlanSprint'), params.id])}"
            redirect(action: "list")
        }
    }
}
