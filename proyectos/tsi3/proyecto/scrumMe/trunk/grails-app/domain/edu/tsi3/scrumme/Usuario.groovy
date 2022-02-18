package edu.tsi3.scrumme

import edu.tsi3.scrumme.Role

/**
 * User domain class.
 */
class Usuario {
	static transients = ['pass']
	static hasMany = [authorities: Role, proyectos: UsuarioProyecto, tareas: Tarea, mensajes : Mensaje]
	static belongsTo = Role

	/** Username */
	String username
	/** User Real Name*/
	String userRealName
	/** MD5 Password */
	String passwd
	/** enabled */
	boolean enabled

	String email
	boolean emailShow

	/** description */
	String description = ''

	/** plain password to create a MD5 password */
	String pass = '[secret]'

	/*agregados maxi*/
	String nick
	TipoUsuario tipo	
	static constraints = {
		username(blank: false, unique: true)
		userRealName(blank: false)
		passwd(blank: false)
		enabled()
		nick(size:0..4)
	}
}
