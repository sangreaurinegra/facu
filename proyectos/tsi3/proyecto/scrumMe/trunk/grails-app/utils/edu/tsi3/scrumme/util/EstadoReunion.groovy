package edu.tsi3.scrumme.util

public enum EstadoReunion {
	ARELIZARCE('A Realizarse'),
	ENCURSO('En Curso'),
	CANCELADA('Cancelada'),
	FINALIZADA('Finalizada')
	
	String nombreEstado
	
	
	EstadoReunion(String nombreEstado) {
		this.nombreEstado = nombreEstado
	}
	
	
	static list() {
		[ARELIZARCE, ENCURSO, CANCELADA, FINALIZADA]
	}
	
}
