package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("nodoHome")
public class NodoHome extends EntityHome<Nodo> {

	@In(create = true)
	EstadiaHome estadiaHome;
	@In(create = true)
	LugarHome lugarHome;
	@In(create = true)
	PasajeHome pasajeHome;

	public void setNodoIdNodo(Long id) {
		setId(id);
	}

	public Long getNodoIdNodo() {
		return (Long) getId();
	}

	@Override
	protected Nodo createInstance() {
		Nodo nodo = new Nodo();
		return nodo;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Estadia estadia = estadiaHome.getDefinedInstance();
		if (estadia != null) {
			getInstance().setEstadia(estadia);
		}
		Lugar lugar = lugarHome.getDefinedInstance();
		if (lugar != null) {
			getInstance().setLugar(lugar);
		}
		Pasaje pasajeLlegada = pasajeHome.getDefinedInstance();
		if (pasajeLlegada != null) {
			getInstance().setPasajeLlegada(pasajeLlegada);
		}
		Pasaje pasajeSalida = pasajeHome.getDefinedInstance();
		if (pasajeSalida != null) {
			getInstance().setPasajeSalida(pasajeSalida);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Nodo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
