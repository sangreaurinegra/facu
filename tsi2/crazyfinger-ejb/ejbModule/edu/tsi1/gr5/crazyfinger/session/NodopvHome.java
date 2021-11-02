package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("nodopvHome")
public class NodopvHome extends EntityHome<Nodopv> {

	@In(create = true)
	ProyectoviajeHome proyectoviajeHome;

	public void setNodopvIdNodo(Long id) {
		setId(id);
	}

	public Long getNodopvIdNodo() {
		return (Long) getId();
	}

	@Override
	protected Nodopv createInstance() {
		Nodopv nodopv = new Nodopv();
		return nodopv;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Proyectoviaje proyectoviaje = proyectoviajeHome.getDefinedInstance();
		if (proyectoviaje != null) {
			getInstance().setProyectoviaje(proyectoviaje);
		}
	}

	public boolean isWired() {
		if (getInstance().getProyectoviaje() == null)
			return false;
		return true;
	}

	public Nodopv getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
