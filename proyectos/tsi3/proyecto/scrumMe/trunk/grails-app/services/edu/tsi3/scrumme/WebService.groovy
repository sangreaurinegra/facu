package edu.tsi3.scrumme

import java.util.Arrays;

class WebService {

	static expose = ["xfire"]
	
    boolean transactional = true

    def authenticateService
    
    Long login(String usuario, String pwd) {
		println "------------->>> entra login"
		def u = Usuario.findByUsername(usuario)
		def pass = authenticateService.encodePassword(pwd)
		
		if(u){
			if(pass != u.passwd)
				return new Long(-1)
			return new Long(u.id)
		}
		return new Long(1)
    }
	
	Long[] proyectosAsignado(Long usuario){
		def proys = UsuarioProyecto.findAll("from UsuarioProyecto up where up.usuario.id = ?",[usuario])
		Long[] mapa = new Long[proys.size()] 
		if(proys){
			proys.eachWithIndex {it, i ->
				mapa[i] = new Long(it.proyecto.id)
			}
		}
		println mapa
		return mapa 
	}
	
	String nombreProyecto(Long proyecto){
		def p = Proyecto.get(proyecto)
		return p.nombre
	}
	/*
	 * 
	Map<String, String> proyectosAsignado(Long usuario){
		def proys = UsuarioProyecto.findAll("from UsuarioProyecto up where up.usuario.id = ?",[usuario])
		def mapa = new TreeMap<String, String>() 
		if(proys){
			proys.each {
				mapa.put ""+it.proyecto.id, it.proyecto.nombre
			}
		}
		println mapa
		return mapa 
	}
	
	List<Reunion> reuniones(Long usuario, Long proyecto){
		def proy = Proyecto.get(proyecto)
		def res = Reunion.findAll("from Reunion r where r.proyecto = ?",[proy])
		return res
	}
	*/
}
