package edu.tsi3.scrumme

class PlanSprint extends Reunion {

	Sprint sprint
	
    static constraints = {
		sprint(nullable:false)
    }
}
