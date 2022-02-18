package edu.tsi3.planningpoker

import edu.tsi3.scrumme.Usuario;

class ManoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [manoInstanceList: Mano.list(params), manoInstanceTotal: Mano.count()]
    }

    def create = {
        def manoInstance = new Mano()
        manoInstance.properties = params
        return [manoInstance: manoInstance]
    }

    def save = {
        def manoInstance = new Mano(params)
        if (manoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'mano.label', default: 'Mano'), manoInstance.id])}"
            redirect(action: "index",controller:'juego')
        }
        else {
            render(view: "create", model: [manoInstance: manoInstance])
        }
    }

    def show = {
        def manoInstance = Mano.get(params.id)
        if (!manoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mano.label', default: 'Mano'), params.id])}"
            redirect(action: "list")
        }
        else {
            [manoInstance: manoInstance]
        }
    }

    def edit = {
        def manoInstance = Mano.get(params.id)
        if (!manoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mano.label', default: 'Mano'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [manoInstance: manoInstance]
        }
    }

    def update = {
        def manoInstance = Mano.get(params.id)
        if (manoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (manoInstance.version > version) {
                    
                    manoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'mano.label', default: 'Mano')] as Object[], "Another user has updated this Mano while you were editing")
                    render(view: "edit", model: [manoInstance: manoInstance])
                    return
                }
            }
            manoInstance.properties = params
            if (!manoInstance.hasErrors() && manoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'mano.label', default: 'Mano'), manoInstance.id])}"
                redirect(action: "show", id: manoInstance.id)
            }
            else {
                render(view: "edit", model: [manoInstance: manoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mano.label', default: 'Mano'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def manoInstance = Mano.get(params.id)
        if (manoInstance) {
            try {
                manoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'mano.label', default: 'Mano'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'mano.label', default: 'Mano'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'mano.label', default: 'Mano'), params.id])}"
            redirect(action: "list")
        }
    }
    
    
    def mano = {
    	def cartasArriba = Carta.findAll("from Carta as c where c.valor >= ? and c.valor <= ? and c.comodin = false",[new Double(0),new Double(8)])
    	def cartasAbajo = Carta.findAll("from Carta as c where c.valor > ? or c.comodin = true",[new Double(8)])
    	def mano = Mano.get(params.id)
    	def juego = Juego.get(new Long(params.jid))
    	def jugadas = CartaUsuario.findAllByManoAndUsuarioNotEqual(mano,juego.creador, [sort:"usuario", order:"asc"])
    	def jugadacreador = CartaUsuario.findByManoAndUsuario(mano,juego.creador)
    	def jugadores = InvitacionJuego.findAllByJuegoAndEstado(juego,InvitacionJuego.ESTADO_ACEPTADA, [sort:"usuario", order:"asc"])
    	
    	[mano:mano, cartasArriba:cartasArriba, 
    	 cartasAbajo:cartasAbajo, jugadas:jugadas,
    	 juego:juego, jugadores:jugadores,
    	 jugadacreador:jugadacreador,
    	 estanTodos: (jugadas.size() == jugadores.size() && jugadacreador != null)]
    }
    
    def elegirCarta = {
    	def usuario = Usuario.get(new Long(params.uid))
    	def mano = Mano.get(new Long(params.mid))
    	def carta = Carta.get(new Long(params.cid))
    	def cu = CartaUsuario.findByManoAndUsuario(mano,usuario)
    	if(cu == null){
	    	cu = new CartaUsuario()
	    	cu.usuario = usuario
	    	cu.carta = carta
	    	cu.mano = mano
	    	cu.save(flush:true)
    	}
    	render "ok"
    }
    
    def estimar = {
    	def mano = Mano.get(new Long(params.mid))
    	render ""+estimar(mano)
    }
    
    def finalizar = {
    	def mano = Mano.get(new Long(params.mid))
    	def est = estimar(mano)
    	if(params.estimacion != ''){
    		try{
    			est = new Double(params.estimacion)
    		}
    		catch(Exception e){}
    	}
    	mano.estado = Mano.ESTADO_FINALIZADA
    	mano.estimacion = est
    	mano.tarea.estimacion = est
    	mano.tarea.save(flush:true)
    	mano.save(flush:true)
    	if(session.usuario.id == mano.juego.creador.id)
    		redirect (action:'create', params:[jid:mano.juego.id])
    	else
    		redirect (action:'resumen',controller:'mano', params:[jid:mano.juego.id])
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
    
    def resumen = {
    	def juego = Juego.get(new Long(params.jid))
    	[juego:juego]
    }
    
    def refrescar = {
    	def mano = Mano.get(new Long(params.mid))
    	if(mano.estado == Mano.ESTADO_EN_JUEGO){
	    	def juego = mano.juego
	    	def jugadas = CartaUsuario.findAllByManoAndUsuarioNotEqual(mano,juego.creador, [sort:"usuario", order:"asc"])
	    	def jugadacreador = CartaUsuario.findByManoAndUsuario(mano,juego.creador)
	    	def jugadores = InvitacionJuego.findAllByJuegoAndEstado(juego,InvitacionJuego.ESTADO_ACEPTADA, [sort:"usuario", order:"asc"])
	    	
	    	render template:'refrescar',
	    	model:[mano:mano, jugadas:jugadas,
	    	 juego:juego, jugadores:jugadores,
	    	 jugadacreador:jugadacreador,
	    	 estanTodos: (jugadas.size() == jugadores.size() && jugadacreador != null)]
    	}
    	else
    		render "fin"
    }
}
