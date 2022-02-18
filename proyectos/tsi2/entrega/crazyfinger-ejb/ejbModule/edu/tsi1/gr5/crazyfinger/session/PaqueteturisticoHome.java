package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("paqueteturisticoHome")
public class PaqueteturisticoHome extends EntityHome<Paqueteturistico> {

	public void setPaqueteturisticoIdPaqueteturistico(Long id) {
		setId(id);
	}

	public Long getPaqueteturisticoIdPaqueteturistico() {
		return (Long) getId();
	}

	@Override
	protected Paqueteturistico createInstance() {
		Paqueteturistico paqueteturistico = new Paqueteturistico();
		return paqueteturistico;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Paqueteturistico getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Nodopt> getNodopts() {
		return getInstance() == null ? null : new ArrayList<Nodopt>(
				getInstance().getNodopts());
	}
	

}
