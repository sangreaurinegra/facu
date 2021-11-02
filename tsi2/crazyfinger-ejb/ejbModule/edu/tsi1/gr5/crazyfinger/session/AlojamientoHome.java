package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("alojamientoHome")
public class AlojamientoHome extends EntityHome<Alojamiento> {

	public void setAlojamientoIdServicio(Long id) {
		setId(id);
	}

	public Long getAlojamientoIdServicio() {
		return (Long) getId();
	}

	@Override
	protected Alojamiento createInstance() {
		Alojamiento alojamiento = new Alojamiento();
		return alojamiento;
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

	public Alojamiento getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Estadia> getEstadias() {
		return getInstance() == null ? null : new ArrayList<Estadia>(
				getInstance().getEstadias());
	}

}
