package edu.tsi4.truco

class TrucoController {

	def trucoService
	
    def limpiar = { 
		println "borrando mensajes..."
		trucoService.borrarMensajes()
	}
}
