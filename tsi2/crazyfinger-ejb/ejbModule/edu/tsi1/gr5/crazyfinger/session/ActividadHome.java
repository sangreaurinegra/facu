package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("actividadHome")
public class ActividadHome extends EntityHome<Actividad> {

	@In(create = true)
	LugarHome lugarHome;
	@In(create = true)
	PuntoturisticoHome puntoturisticoHome;

	public void setActividadIdActividad(Long id) {
		setId(id);
	}

	public Long getActividadIdActividad() {
		return (Long) getId();
	}

	@Override
	protected Actividad createInstance() {
		Actividad actividad = new Actividad();
		return actividad;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Lugar lugar = lugarHome.getDefinedInstance();
		if (lugar != null) {
			getInstance().setLugar(lugar);
		}
		Puntoturistico puntoturistico = puntoturisticoHome.getDefinedInstance();
		if (puntoturistico != null) {
			getInstance().setPuntoturistico(puntoturistico);
		}
	}

	public boolean isWired() {
		if (getInstance().getLugar() == null)
			return false;
		return true;
	}

	public Actividad getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
