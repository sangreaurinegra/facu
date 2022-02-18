package edu.tsi3.scrumme

import java.util.Date;

class Mensaje {

	public static int ESTADO_PENDIENTE = 0
	public static int ESTADO_LEIDO = 1
	public static int ESTADO_ANULADO = 2
	
	public static int TIPO_MENSAJE = 1
	public static int TIPO_ALARMA = 2
	
	Date fechaEnviado
	Date fechaLeido
	String titulo
	String mensaje
	String link
	int estado
	Usuario destino
	int tipo
    static constraints = {
		titulo(nullable:true)
		mensaje(nullable:true,size:0..512)
		link(nullable:true)
		fechaLeido(nullable:true)
    }
}
