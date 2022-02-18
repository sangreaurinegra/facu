package edu.tsi4.truco

import java.util.Date;

class Mensaje {

	public static int ESTADO_NO_LEIDO = 1
	public static int ESTADO_LEIDO = 2
	
	String origen
	String destino
	int estado
	String mensaje
	Date fechaHoraEnviado
	Date fechaHoraRecibido
	
    static constraints = {
		origen(nullable:false)
		destino(nullable:false)
    }
}
