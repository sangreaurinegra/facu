package edu.tsi3.scrumme.util

public enum EstadoInvitacion {
	PENDIENTE('Favor confirmar'),
	ACEPTADA('Voy'),
	RECHAZADA('No Voy'),
	ENDUDA('mmm tal vez')
	
	
	String nombreEstado
	
	EstadoInvitacion(String nombreEstado) {
		this.nombreEstado = nombreEstado
	}
	
	static list() {
		[PENDIENTE, ACEPTADA, RECHAZADA, ENDUDA]
	}
	
	
}
