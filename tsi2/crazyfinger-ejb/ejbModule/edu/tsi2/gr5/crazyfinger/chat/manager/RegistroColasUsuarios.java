package edu.tsi2.gr5.crazyfinger.chat.manager;

import java.util.HashMap;

import javax.jms.Destination;

public class RegistroColasUsuarios {

	private static RegistroColasUsuarios instance = null;
	private HashMap<Long, Destination> tabla;
	
	private RegistroColasUsuarios() {
		tabla = new HashMap<Long, Destination>();
	}
	
	public static RegistroColasUsuarios getInstance() {
		if (instance == null) {
			instance = new RegistroColasUsuarios();
		}
		return instance;
 	}
	
	public void add(Long idUsuario, Destination dest) {
		tabla.put(idUsuario, dest);
	}
	
	public Destination getDestination(Long idUsuario) {
		return tabla.get(idUsuario);
	}
	
	public boolean exists(Long idUsuario) {
		return tabla.containsKey(idUsuario);
	}
	
	public Destination removeDestination(Long idUsuario) {
		Destination destination = tabla.remove(idUsuario);
		return destination;
	}
	
}
