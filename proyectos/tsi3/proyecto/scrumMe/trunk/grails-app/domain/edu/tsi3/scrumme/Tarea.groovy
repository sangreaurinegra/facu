package edu.tsi3.scrumme

import java.util.Date;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH;
import edu.tsi3.scrumme.util.EstadoTarea;

class Tarea {

	String nombre
	Usuario asignado
	String descripcion
	EstadoTarea estado
	Date fechaIni
	Date fechaFin
	TipoTarea tipo
	Proyecto proyecto
	Sprint sprint
	int x = 0
	int y = 0
	double estimacion
	double duracion
	
	Date fechaCreacion
	
    static constraints = {
		asignado(nullable:true)
		descripcion(nullable:true,size:0..512)
		fechaIni(nullable:true)
		fechaFin(nullable:true)
		x(nullable:false, blank:false, min:0)
		y(nullable:false, blank:false, min:0)
		estimacion(nullable:false)
		duracion(nullable:false)
    }
	
	int calcularX(int offset){
		def x = 0
		if(this.estado == EstadoTarea.NO_INICIADA )
			x = CH.config.inibacklog
		if(this.estado == EstadoTarea.EN_PROCESO)
			x = CH.config.iniworking
		if(this.estado == EstadoTarea.FINALIZADA)
			x = CH.config.iniready
		return x+offset
	}
	
	int calcularY(int offset){
		def x = CH.config.inivertical
		return x+offset
	}
	
	String toString(){
		nombre
	}
}
