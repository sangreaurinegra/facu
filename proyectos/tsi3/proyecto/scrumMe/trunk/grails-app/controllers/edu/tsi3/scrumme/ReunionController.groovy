package edu.tsi3.scrumme

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.tsi3.scrumme.util.EstadoInvitacion;
import edu.tsi3.scrumme.util.EstadoProyecto;

import java.text.SimpleDateFormat;

class ReunionController {
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[reunionInstanceList: Reunion.list(params), reunionInstanceTotal: Reunion.count()]
	}
	
	def create = {
		def reunionInstance = new Reunion()
		reunionInstance.properties = params
		return [reunionInstance: reunionInstance]
	}
	
	def save = {
		
		
		
		def reunionInstance = new Reunion(params)
		reunionInstance.acta = new Texto()
		// reunionInstance.acta.reunion = reunionInstance
		reunionInstance.acta.texto = "Acta de "+reunionInstance.titulo
		
		//limppio errores para setear fecha
		reunionInstance.clearErrors();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm")
		
		def fechaIni = sdf.parse(params.fechaInicio)
		reunionInstance.fechaInicio=fechaIni
		
		
		
		def fechaFin = sdf.parse(params.fechaFin)
		reunionInstance.fechaFin=fechaFin
		
		Alarma alarma = new Alarma()
		
		if(session.proyecto!=null){
			reunionInstance.proyecto = Proyecto.get(session.proyecto)
			if(reunionInstance.proyecto.estado == EstadoProyecto.FINALIZADO){
				flash.message = "${message(code: 'scrumme.reunion.proy.fin', default:'No se puede crear reuniones en proyecto finalizados.')}"
				redirect(action: "create", model: [reunionInstance: reunionInstance])
				return
			}
			Calendar cal = new GregorianCalendar()
			cal.time = fechaIni
			cal.add(Calendar.MINUTE, -15)
			alarma.fechaAlarma = cal.time
			alarma.titulo = "Reunion"
			alarma.mensaje = reunionInstance.titulo+" inicia a las "+ sdf.format(fechaIni)
			alarma.estado = 0
			alarma.avisados = new ArrayList<Usuario>()
			reunionInstance.invitaciones = new ArrayList<Usuario>()
			
			reunionInstance.proyecto.usuarios.each {
				Invitacion invitacion = new Invitacion()
				invitacion.reunion=reunionInstance
				invitacion.fecha= new Date()
				invitacion.estado = EstadoInvitacion.PENDIENTE
				invitacion.usuario = it.usuario
				
				reunionInstance.invitaciones.add(invitacion)
				
				alarma.avisados+=it.usuario
				
			}
		
			
		}
		
		// re valido
		reunionInstance.validate()
		
		
		if (reunionInstance.save(flush: true)) {
			if(session.proyecto!=null){
				alarma.link = g.createLink(action:'show',controller:'reunion',id:reunionInstance.id)
				alarma.save(flush: true)
			}
			
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'reunion.label', default: 'Reunion'), reunionInstance.id])}"
			redirect(action: "show", id: reunionInstance.id)
		}
		else {
			render(view: "create", model: [reunionInstance: reunionInstance])
		}
		
	}
	
	def show = {
		def reunionInstance = Reunion.get(params.id)
		if (!reunionInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'reunion.label', default: 'Reunion'), params.id])}"
			redirect(action: "list")
		}
		else {
			[reunionInstance: reunionInstance]
		}
	}
	
	def edit = {
		def reunionInstance = Reunion.get(params.id)
		if (!reunionInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'reunion.label', default: 'Reunion'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [reunionInstance: reunionInstance]
		}
	}
	
	def update = {
		def reunionInstance = Reunion.get(params.id)
		if (reunionInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (reunionInstance.version > version) {
					
					reunionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'reunion.label', default: 'Reunion')] as Object[], "Another user has updated this Reunion while you were editing")
					render(view: "edit", model: [reunionInstance: reunionInstance])
					return
				}
			}
			reunionInstance.properties = params
			
			//limppio errores para setear fecha
			reunionInstance.clearErrors();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm")
			
			def fechaIni = sdf.parse(params.fechaInicio)
			reunionInstance.fechaInicio=fechaIni
			
			def fechaFin = sdf.parse(params.fechaFin)
			reunionInstance.fechaFin=fechaFin
			
			// re valido
			reunionInstance.validate()
			
			if (!reunionInstance.hasErrors() && reunionInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'reunion.label', default: 'Reunion'), reunionInstance.id])}"
				redirect(action: "show", id: reunionInstance.id)
			}
			else {
				render(view: "edit", model: [reunionInstance: reunionInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'reunion.label', default: 'Reunion'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def delete = {
		def reunionInstance = Reunion.get(params.id)
		if (reunionInstance) {
			try {
				reunionInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'reunion.label', default: 'Reunion'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'reunion.label', default: 'Reunion'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'reunion.label', default: 'Reunion'), params.id])}"
			redirect(action: "list")
		}
	}
	
	
	def reuniones = {
		if(session.proyecto){
			def proy = Proyecto.get(session.proyecto)
			def res = Reunion.findAll ("from Reunion r where r.proyecto = ? order by r.fechaInicio desc",[proy])
			[reuniones: res, proyecto:proy]
		}
		else{
			flash.message = "Debe seleccionar un proyecto para poder acceder a esta opción."
			redirect action:'proyectos', controller:'proyecto'
		}
		
	}
	
}
