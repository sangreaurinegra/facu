package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("pasajeHome")
public class PasajeHome extends EntityHome<Pasaje> {

	@In(create = true)
	TrasladoHome trasladoHome;

	public void setPasajeIdPasaje(Long id) {
		setId(id);
	}

	public Long getPasajeIdPasaje() {
		return (Long) getId();
	}

	@Override
	protected Pasaje createInstance() {
		Pasaje pasaje = new Pasaje();
		return pasaje;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Traslado traslado = trasladoHome.getDefinedInstance();
		if (traslado != null) {
			getInstance().setTraslado(traslado);
		}
	}

	public boolean isWired() {
		if (getInstance().getTraslado() == null)
			return false;
		return true;
	}

	public Pasaje getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
