package edu.tsi2.gr5.crazyfinger.chat;

import java.io.Serializable;
import java.util.HashMap;

import javax.jms.Destination;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;


@Name("registroConexiones")
@Scope(ScopeType.SESSION)
public class RegistroConexiones  implements Serializable{

	private static final long serialVersionUID = 9022460034880513722L;
	private HashMap<Long, Destination> registro = new HashMap<Long, Destination>();
	
	public RegistroConexiones(){
		
	}
	
	public Destination getDestination(long idUsuario) {
		Destination d = registro.get(idUsuario);
		return d;
	}
	
	public boolean estaOnline(long idUsuario) {
		return registro.containsKey(idUsuario);
	}
	
	public void putDestination(long idUsuario, Destination dest) {
//		log("putDestination idUsuario " + idUsuario + " dest==null " + (dest==null) );
		registro.put(idUsuario, dest);
	}
	
	public void removeInvitacion(long idUsuario) {
		registro.remove(idUsuario);
	}
	
	public int cantidadDeInvitaciones() {
		return registro.size();
	}

	public void log(String s) {
		System.out.println("[RegistroConexiones] " + s);
	}
	
}
