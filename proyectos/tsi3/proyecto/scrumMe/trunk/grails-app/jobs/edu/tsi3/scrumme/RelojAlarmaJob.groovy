package edu.tsi3.scrumme


class RelojAlarmaJob {
	static triggers = {
	    cron name: 'alarmaTrigger', cronExpression: "0 0/1 * * * ?"
	  }

	def group = "alarma"

    def execute() {
        def fecha = new Date()
		println "job----->"+fecha
		//obtengo las alarmas
        def alarmas = Alarma.findAllByFechaAlarma(fecha)
        alarmas.each{
        	//recorro las alarmas y genero los mensajes.
        	it.avisados.each{ u->
        		def msg = new Mensaje()
            	msg.titulo = it.titulo
            	msg.mensaje = it.mensaje
            	msg.link = it.link
            	msg.destino = u
            	msg.fechaEnviado = fecha
            	msg.estado = Mensaje.ESTADO_PENDIENTE
            	msg.tipo = Mensaje.TIPO_ALARMA
            	msg.save (flush:true)
        	}
        	
        }
        //una vez generados los mensajes, borramos las alarmas.
        //alarmas*.delete(flush:true)
    }
}
