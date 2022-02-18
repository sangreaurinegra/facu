package edu.tsi3.scrumme

class Sprint {

	public static int ESTADO_NO_INICIADO = 0
	public static int ESTADO_DESA = 1
	public static int ESTADO_FINALIZADO = 2
	
	String nombre
	int numero
	String fraseObjetivo
	int estado
	Date fechaIni
	Date fechaFin
	Proyecto proyecto
	static hasMany = [tareas: Tarea]
	                  
    static constraints = {
		nombre(nullable:true)
		fraseObjetivo(nullable:true,size:0..255)
		fechaIni(nullable:true)
		fechaFin(nullable:true)
    }
	
	String textoEstado(){
		if(estado == ESTADO_NO_INICIADO)
			return "No iniciado"
		if(estado == ESTADO_DESA)
			return "Desarrollando"
		if(estado == ESTADO_FINALIZADO)
			return "Finalizado"
	}
	
	String toString(){
		"Sprint "+numero+": "+nombre
	}
}
