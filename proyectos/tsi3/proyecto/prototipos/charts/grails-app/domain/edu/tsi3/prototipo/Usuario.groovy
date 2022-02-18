package edu.tsi3.prototipo

class Usuario {

	String userId
	String password
	String homepage
	Date dateCreated
	byte[] foto
	
	static hasMany = [ tareas : Tarea ]
	
	
    static constraints = {
		password(size: 6..8, validator: { passwd, user ->
		passwd != user.userId
		})
		foto(size: 0..102400)
    }
}
