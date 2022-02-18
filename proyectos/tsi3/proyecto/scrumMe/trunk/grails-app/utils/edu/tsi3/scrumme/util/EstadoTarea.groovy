package edu.tsi3.scrumme.util

public enum EstadoTarea {
	NO_INICIADA('No iniciada'),
	EN_PROCESO('En Proceso'),
	FINALIZADA('Finalizada')
	
	String nombreEstado
	
	EstadoTarea(String nombreEstado) {
		this.nombreEstado = nombreEstado
	}
	
	static list() {
		[NO_INICIADA, EN_PROCESO, FINALIZADA]
	}
	
	
}
