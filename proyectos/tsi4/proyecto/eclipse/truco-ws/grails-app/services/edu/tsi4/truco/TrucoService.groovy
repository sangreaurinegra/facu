package edu.tsi4.truco

class TrucoService {

    boolean transactional = true

	static expose = ["xfire"]
	
	static mensajes = []
	
    def void enviar(String mensaje, String para, String de) {
		def men = new Mensaje()
		men.origen = de
		men.destino = para
		men.mensaje = mensaje
		men.fechaHoraEnviado = new Date()
		men.estado = Mensaje.ESTADO_NO_LEIDO
		println "E-->mensaje para: "+para+" por:"+de
		println "mensaje recibido: "+mensaje
		salvarMensaje(men)
		println "mensaje salvado, id: "+men.id
    }
	
	def String recibir(String para, String de){
		//println "R-->mensaje para: "+para+" por:"+de
		Mensaje men = buscar(de, para)
		if(men){
			println "-->"
			println "mensaje enviado: "+men.mensaje
			men.estado = Mensaje.ESTADO_LEIDO
			men.fechaHoraRecibido = new Date()	
			
			return men.mensaje
		}	
		return null
	}
	
	void salvarMensaje(men){
		//men.save(flush:true)
		mensajes << men
	}
	
	Mensaje buscar(de, para){
		//Mensaje.find ("from Mensaje a where a.origen = ? and a.destino = ? and a.estado = ?",
		//	[de,para,Mensaje.ESTADO_NO_LEIDO])
		def res = null
		mensajes.each {
			if(it.origen == de && it.destino == para && it.estado == Mensaje.ESTADO_NO_LEIDO){
				res = it
				
			}	
		}
		return res
	}
	
	def borrarMensajes(){
		mensajes = []	
	}
}
