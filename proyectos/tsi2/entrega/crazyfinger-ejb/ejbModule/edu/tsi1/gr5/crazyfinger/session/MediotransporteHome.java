package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("mediotransporteHome")
public class MediotransporteHome extends EntityHome<Mediotransporte> {

	public void setMediotransporteIdMediotransporte(Long id) {
		setId(id);
	}

	public Long getMediotransporteIdMediotransporte() {
		return (Long) getId();
	}

	@Override
	protected Mediotransporte createInstance() {
		Mediotransporte mediotransporte = new Mediotransporte();
		return mediotransporte;
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

	public Mediotransporte getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
