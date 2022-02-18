package edu.tsi3.scrumme.util

class Utilidades {

	
	static String esActivo(ctrl,link){
		if(link == 'proyectos'){
			if(ctrl == 'proyecto' || ctrl == 'sprint' || ctrl == 'tarea' || ctrl == 'alarma' || ctrl == 'reunion')
				return 'active'
		}
		else if(link == 'juego'){
			if(ctrl == 'carta' || ctrl == 'juego' || ctrl == 'mano' || ctrl == 'invitacionJuego')
				return 'active'
		}
		else if(link == 'inicio'){
			if(ctrl == 'inicio')
				return 'active'
		}
		else if(link == 'ayuda'){
			if(ctrl == 'ayuda')
				return 'active'
		}		
		else if(link == 'admin'){
			if(!(ctrl == 'proyecto' || ctrl == 'sprint' || ctrl == 'tarea' || ctrl == 'alarma' || ctrl == 'reunion'
				|| ctrl == 'carta' || ctrl == 'juego' || ctrl == 'mano' || ctrl == 'invitacionJuego'
				|| ctrl == 'inicio' || ctrl == 'ayuda'
			))
				return 'active'
		}
		
		return ''
	}
}
