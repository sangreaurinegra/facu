package edu.tsi3.planningpoker

class Carta {

	double valor
	boolean comodin
	byte[] imagen
    static constraints = {
		imagen(size:0..512000)
		valor(nullable:false)
    }
	/*
	 * <img src="${createLink(action:'imagen', controller:'carta',id:cartaInstance.id) }" />
	 * 
	 * def imagen = {
    	def carta = Carta.get(params.id)
        
        response.contentType = 'image/jpeg'
        response.outputStream << carta.imagen
        response.outputStream.flush()
       

    }
	 * 
	 * */
}
