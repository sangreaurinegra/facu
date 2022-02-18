package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("nodoptHome")
public class NodoptHome extends EntityHome<Nodopt> {

	@In(create = true)
	PaqueteturisticoHome paqueteturisticoHome;

	public void setNodoptIdNodo(Long id) {
		setId(id);
	}

	public Long getNodoptIdNodo() {
		return (Long) getId();
	}

	@Override
	protected Nodopt createInstance() {
		Nodopt nodopt = new Nodopt();
		return nodopt;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Paqueteturistico paqueteturistico = paqueteturisticoHome
				.getDefinedInstance();
		if (paqueteturistico != null) {
			getInstance().setPaqueteturistico(paqueteturistico);
		}
	}

	public boolean isWired() {
		if (getInstance().getPaqueteturistico() == null)
			return false;
		return true;
	}

	public Nodopt getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
