package edu.tsi3.scrumme

import edu.tsi3.scrumme.Usuario

/**
 * Authority domain class.
 */
class Role {

	static hasMany = [people: Usuario]

	/** description */
	String description
	/** ROLE String */
	String authority

	static constraints = {
		authority(blank: false, unique: true)
		description()
	}
}
