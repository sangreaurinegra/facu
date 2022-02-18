package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("estadiaHome")
public class EstadiaHome extends EntityHome<Estadia> {

	@In(create = true)
	AlojamientoHome alojamientoHome;
	@In(create = true)
	NodoHome nodoHome;

	public void setEstadiaIdEstadia(Long id) {
		setId(id);
	}

	public Long getEstadiaIdEstadia() {
		return (Long) getId();
	}

	@Override
	protected Estadia createInstance() {
		Estadia estadia = new Estadia();
		return estadia;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Alojamiento alojamiento = alojamientoHome.getDefinedInstance();
		if (alojamiento != null) {
			getInstance().setAlojamiento(alojamiento);
		}
		Nodo nodo = nodoHome.getDefinedInstance();
		if (nodo != null) {
			getInstance().setNodo(nodo);
		}
	}

	public boolean isWired() {
		if (getInstance().getAlojamiento() == null)
			return false;
		return true;
	}

	public Estadia getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Nodo> getNodos() {
		return getInstance() == null ? null : new ArrayList<Nodo>(getInstance()
				.getNodos());
	}

}
