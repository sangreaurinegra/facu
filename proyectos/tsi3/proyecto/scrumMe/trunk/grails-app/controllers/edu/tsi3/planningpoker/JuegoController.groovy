

package edu.tsi3.planningpoker

import java.awt.geom.FlatteningPathIterator;
import java.util.ArrayList;
import java.util.Calendar;
import edu.tsi3.scrumme.Alarma;
import edu.tsi3.scrumme.Proyecto;
import edu.tsi3.scrumme.Usuario;

import java.text.SimpleDateFormat;

class JuegoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        if(session.usuario){
        	//busco juegos activos para el usuario
        	def juegos = Juego.findAllByCreadorAndEstado(session.usuario,Juego.ESTADO_ACTIVO)
        	def invitado = InvitacionJuego.findAll ("from InvitacionJuego as j where j.estado = ? and j.juego.estado = ? and j.usuario = ?",
        			[InvitacionJuego.ESTADO_ACEPTADA,Juego.ESTADO_ACTIVO,session.usuario])
        	juegos += invitado.collect{it.juego}.flatten()
        	if(juegos != null && juegos.size > 0){
        		if(juegos.size == 1 ){
        			params.id = juegos.get (0).id 
        			redirect(action: "juego", params: params)
        			return
        		}
        		else{
        			params.juegos = juegos 
        			forward(action: "resumen", params: params)
        			return
        		}
        	}
        	else{
        		juegos = Juego.findAllByCreadorAndEstado(session.usuario,Juego.ESTADO_POR_INICIAR)
        		if(juegos.size > 0){
        			params.juegos = juegos 
        			forward(action: "resumen", params: params)
        			return
        		}
        		redirect(action: "create", params: params)
    			return
        	}
        }
    	redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [juegoInstanceList: Juego.list(params), juegoInstanceTotal: Juego.count()]
    }

    def create = {
        def juegoInstance = new Juego()
        juegoInstance.properties = params
        def p = null
        if(session.proyecto){
        	p = Proyecto.get(session.proyecto)
        }
        return [juegoInstance: juegoInstance, proy:p]
    }

    def save = {
        def juegoInstance = new Juego(params)
        def user = Usuario.get(session.usuario.id)
        juegoInstance.creador = user
        juegoInstance.estado = Juego.ESTADO_POR_INICIAR
        
        juegoInstance.clearErrors();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm")
		
		def fechaIni = sdf.parse(params.fechaIni)
		juegoInstance.fechaIni=fechaIni
        
        
        if (juegoInstance.save(flush: true)) {
        	
        	// una vez salvado creamos las invitaciones y la alarma.
        	//generamos la alarma
        	def a = new Alarma()
        	a.titulo = "Invitacion Juego:"+juegoInstance.nombre
        	a.mensaje = "Has sido invitado a participar el juego de planificación. <br> "+
			        	"Nombre del juego:"+juegoInstance.nombre+" <br> "+
			        	"fecha de inicio:"+juegoInstance.fechaIni.getDateString()+" <br> "+
        				"Click en el link para aceptar la invitación."
        	a.link = createLink(controller:'invitacionJuego', action:'pendientes')
        	a.avisados = new ArrayList<Usuario>()
        	def cal = new GregorianCalendar()
        	cal.time = new Date()
        	cal.add(Calendar.MINUTE,2)
        	cal.roll( Calendar.SECOND, -(cal.get(Calendar.SECOND)))
        	a.fechaAlarma = cal.time
        	
        	
        	def invitados = params.keySet().findAll { it.startsWith("u_") }
        	invitados.each {
        		def checked = params[it]
        		if(checked == "on"){
        			def inv = new InvitacionJuego()
        			def u = Usuario.get (new Long(it.substring(2)))
        			inv.juego = juegoInstance
        			inv.usuario = u
        			inv.estado = InvitacionJuego.ESTADO_PENDIENTE
        			inv.save(flush:true)
        			a.avisados.add u
        		}
        	}
        	a.save(flush:true)
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'juego.label', default: 'Juego'), juegoInstance.id])}"
            redirect(action: "juego", id: juegoInstance.id)
        }
        else {
            render(view: "create", model: [juegoInstance: juegoInstance])
        }
    }

    def show = {
        def juegoInstance = Juego.get(params.id)
        if (!juegoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'juego.label', default: 'Juego'), params.id])}"
            redirect(action: "list")
        }
        else {
            [juegoInstance: juegoInstance]
        }
    }

    def edit = {
        def juegoInstance = Juego.get(params.id)
        if (!juegoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'juego.label', default: 'Juego'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [juegoInstance: juegoInstance]
        }
    }

    def update = {
        def juegoInstance = Juego.get(params.id)
        if (juegoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (juegoInstance.version > version) {
                    
                    juegoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'juego.label', default: 'Juego')] as Object[], "Another user has updated this Juego while you were editing")
                    render(view: "edit", model: [juegoInstance: juegoInstance])
                    return
                }
            }
            juegoInstance.properties = params
            if (!juegoInstance.hasErrors() && juegoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'juego.label', default: 'Juego'), juegoInstance.id])}"
                redirect(action: "show", id: juegoInstance.id)
            }
            else {
                render(view: "edit", model: [juegoInstance: juegoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'juego.label', default: 'Juego'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def juegoInstance = Juego.get(params.id)
        if (juegoInstance) {
            try {
                juegoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'juego.label', default: 'Juego'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'juego.label', default: 'Juego'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'juego.label', default: 'Juego'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    
    def juego = {
    	//busco la mano activa, si no hay
    	// si soy creador, voy a crear sino, voy a resumen
    	def juego = Juego.get(params.id)
    	def mano = Mano.findByEstadoAndJuego(Mano.ESTADO_EN_JUEGO,juego)
    	if(mano == null){
    		if(session.usuario.id == juego.creador.id)
    			redirect action:'create', controller:'mano', params:[jid:juego.id]
    		else
    			redirect action:'resumen', controller:'mano', params:[jid:juego.id]
    		return
    	}
    	redirect action:'mano', controller:'mano', params:[jid:juego.id, id:mano.id]
    }
    
    //muestra todos los juegos activos de los que estoy invitado a participar.
    def juegos = {}
    
    // muestra todos los juegos de los que soy creador en cualquier estado, los por iniciar primero
    def resumen = {
    	def iniciar = Juego.findAllByCreadorAndEstado(session.usuario,Juego.ESTADO_POR_INICIAR)
    	def activos = Juego.findAllByCreadorAndEstado(session.usuario,Juego.ESTADO_ACTIVO)
    	def finalizados = Juego.findAllByCreadorAndEstado(session.usuario,Juego.ESTADO_FINALIZADO)
        
        [iniciar:iniciar,activos:activos, finalizados:finalizados]
    }
    
    def iniciar = {
    	def juego = Juego.get(params.id)
    	juego.estado = Juego.ESTADO_ACTIVO
    	juego.save(flush:true)
    	redirect action:'index'
    	def d = new Date()
    	def d1 = new Date()-400
    	def j = d-d1
    	println "j:"+j+" ->j class"+j.class
    }
    
    def finalizar = {
    	if(params.mid){
    		//debo finalizar la mano
    		def mano = Mano.get(new Long(params.mid))
        	def est = estimar(mano)
        	mano.estado = Mano.ESTADO_FINALIZADA
        	mano.estimacion = est
        	mano.save(flush:true)
    	}
    	def juego = Juego.get(new Long(params.jid))
    	juego.estado = Juego.ESTADO_FINALIZADO 
    	juego.fechaFin = new Date()
    	juego.save(flush:true)
    	redirect (action:'ver',controller:'proyecto', id:session.proyecto)
    }
    
    def estimar(mano){
    	def jugadas = CartaUsuario.findAllByMano(mano)
    	if(jugadas && jugadas.size() > 0){
	    	double suma = jugadas.sum{it.carta.valor}
	    	return suma / jugadas.size()
    	}
    	else
    		return 0.0
    }
    
}
