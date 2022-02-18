package edu.tsi3.scrumme.util

public enum EstadoProyecto {
	NO_INICIADO('No iniciado'),
	EN_DESA('En desarrollo'),
	FINALIZADO('Finalizado')
	
	String nombreEstado
	
	EstadoProyecto(String nombreEstado) {
		this.nombreEstado = nombreEstado
	}
	
	static list() {
		[NO_INICIADO, EN_DESA, FINALIZADO]
	}
	
	
}
