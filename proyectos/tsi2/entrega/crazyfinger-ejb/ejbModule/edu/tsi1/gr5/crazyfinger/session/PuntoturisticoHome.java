package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("puntoturisticoHome")
public class PuntoturisticoHome extends EntityHome<Puntoturistico> {

	@In(create = true)
	LugarHome lugarHome;

	public void setPuntoturisticoIdPuntoturistico(Long id) {
		setId(id);
	}

	public Long getPuntoturisticoIdPuntoturistico() {
		return (Long) getId();
	}

	@Override
	protected Puntoturistico createInstance() {
		Puntoturistico puntoturistico = new Puntoturistico();
		return puntoturistico;
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
	}

	public boolean isWired() {
		return true;
	}

	public Puntoturistico getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Actividad> getActividades() {
		return getInstance() == null ? null : new ArrayList<Actividad>(
				getInstance().getActividades());
	}

}
