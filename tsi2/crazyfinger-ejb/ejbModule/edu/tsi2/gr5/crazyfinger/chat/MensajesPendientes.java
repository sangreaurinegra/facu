package edu.tsi2.gr5.crazyfinger.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.Destination;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Tabla con los mensajes pendientes de cada contacto.
 * @author ALEX101
 *
 */
@Name("mensajesPendientes")
@Scope(ScopeType.SESSION)
public class MensajesPendientes implements Serializable{

	private HashMap<Long, List<String>> mensajes = new HashMap<Long, List<String>>();
	
//	private static MensajesPendientes instance = null;
	
	public MensajesPendientes(){
		
	}
	
	
	public List<String> getMensajesDe(long idUsuario) {
		List<String> msgs = mensajes.get(idUsuario);
		return msgs;
	}

	public List<String> consumirMensajesDe(long idUsuario) {
		List<String> msgs = mensajes.get(idUsuario);
		if (msgs==null)
			msgs = new ArrayList<String>();
		mensajes.put(idUsuario, new ArrayList<String>());
		return msgs;
	}
	
	public boolean hayMensajesPendientesDe(long idUsuario) {
		if (mensajes.containsKey(idUsuario)) {
			List<String> pendientes = mensajes.get(idUsuario);
			return (pendientes.size()>0);
		}
		else {
			return false;
		}
	}
	
	public void addMensajePendiente(long idUsuario, String mensajePendiente) {
		List<String> pendientes;
		if (mensajes.containsKey(idUsuario)) {
			pendientes = mensajes.get(idUsuario);
		}
		else {
			pendientes = new ArrayList<String>();
		}
		pendientes.add(mensajePendiente);
		mensajes.put(idUsuario, pendientes);
	}
		
	public int mensajesDe(Long idUsuario) {
		List<String> pendientes = mensajes.get(idUsuario);
		if (pendientes == null) {
			return 0;
		}
		else {
			return pendientes.size();
		}
	}
	
	public int cantidadDeMensajes() {
		int cantidad = 0;
		for (Long idUsuario : mensajes.keySet()) {
			cantidad += mensajesDe(idUsuario);
		}
		return cantidad;
	}
	
}
