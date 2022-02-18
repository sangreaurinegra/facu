package edu.tsi3.scrumme

import edu.tsi3.scrumme.util.*;

class MensajeController {
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[mensajeInstanceList: Mensaje.list(params), mensajeInstanceTotal: Mensaje.count()]
	}
	
	def create = {
		def mensajeInstance = new Mensaje()
		mensajeInstance.properties = params
		return [mensajeInstance: mensajeInstance]
	}
	
	def save = {
		def mensajeInstance = new Mensaje(params)
		if (mensajeInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), mensajeInstance.id])}"
			redirect(action: "show", id: mensajeInstance.id)
		}
		else {
			render(view: "create", model: [mensajeInstance: mensajeInstance])
		}
	}
	
	def show = {
		def mensajeInstance = Mensaje.get(params.id)
		if (!mensajeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), params.id])}"
			redirect(action: "list")
		}
		else {
			[mensajeInstance: mensajeInstance]
		}
	}
	
	def edit = {
		def mensajeInstance = Mensaje.get(params.id)
		if (!mensajeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [mensajeInstance: mensajeInstance]
		}
	}
	
	def update = {
		def mensajeInstance = Mensaje.get(params.id)
		if (mensajeInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (mensajeInstance.version > version) {
					
					mensajeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'mensaje.label', default: 'Mensaje')] as Object[], "Another user has updated this Mensaje while you were editing")
					render(view: "edit", model: [mensajeInstance: mensajeInstance])
					return
				}
			}
			mensajeInstance.properties = params
			if (!mensajeInstance.hasErrors() && mensajeInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), mensajeInstance.id])}"
				redirect(action: "show", id: mensajeInstance.id)
			}
			else {
				render(view: "edit", model: [mensajeInstance: mensajeInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def delete = {
		def mensajeInstance = Mensaje.get(params.id)
		if (mensajeInstance) {
			try {
				mensajeInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def checkAlarmas = {
		//chequeamos los mensajes de tipo alarma para mostrar un dialogo con estos
		def mensajes = Mensaje.findAll ("from Mensaje as m where m.destino = ? and m.tipo = ? and m.estado = ?",[session.usuario,Mensaje.TIPO_ALARMA,Mensaje.ESTADO_PENDIENTE])
		def invs = Invitacion.findAll ("from Invitacion as m where m.usuario = ? and m.estado = ?",[session.usuario,EstadoInvitacion.PENDIENTE])
		if(mensajes || invs){
			println mensajes
			render template:'alarma', model:[mensajes:mensajes, invs:invs]
		}
		else{
			render "no"
		}
		
		
	}
	
	def marcarLeido = {
		def msg = Mensaje.get(params.id)
		if(msg){
			msg.estado = Mensaje.ESTADO_LEIDO
			msg.fechaLeido = new Date()
			msg.save (flush:true)
		}
		render "ok"
	}
	
	def ver = {
		def mensajeInstance = Mensaje.get(params.id)
		if (!mensajeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mensaje.label', default: 'Mensaje'), params.id])}"
			redirect(action: "list")
		}
		else {
			mensajeInstance.estado = Mensaje.ESTADO_LEIDO
			mensajeInstance.fechaLeido = new Date()
			[mensajeInstance: mensajeInstance]
		}
	}
	
	def feed = {
		render(feedType:"rss", feedVersion:"2.0") {
			title = "Feed Mensajes"
			link = "http://localhost:8080/scrumme/mensaje/feed"
			description = "Feed de Mensajes"
			Mensaje.list().each() { 
				Mensaje -> entry(Mensaje.titulo) { 
					link = "http://localhost:8080/scrumMe/mensaje/show/${Mensaje.id}" 
					Mensaje.mensaje // return the content 
				}
			}
		}
	} 
}

