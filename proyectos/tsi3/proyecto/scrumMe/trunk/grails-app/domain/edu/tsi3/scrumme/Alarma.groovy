package edu.tsi3.scrumme

class Alarma {

	Date fechaAlarma
	String titulo
	String mensaje
	String link
	int estado
	static hasMany = [avisados: Usuario]
    static constraints = {
		titulo(nullable:true)
		mensaje(nullable:true,size:0..512)
		link(nullable:true)
    }
}
