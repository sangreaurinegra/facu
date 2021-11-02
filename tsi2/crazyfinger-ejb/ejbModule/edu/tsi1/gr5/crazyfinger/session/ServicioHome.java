package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("servicioHome")
public class ServicioHome extends EntityHome<Servicio> {

	@In(create = true)
	LugarHome lugarHome;

	public void setServicioIdServicio(Long id) {
		setId(id);
	}

	public Long getServicioIdServicio() {
		return (Long) getId();
	}

	@Override
	protected Servicio createInstance() {
		Servicio servicio = new Servicio();
		return servicio;
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
		if (getInstance().getLugar() == null)
			return false;
		return true;
	}

	public Servicio getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Calificacion> getCalificacions() {
		return getInstance() == null ? null : new ArrayList<Calificacion>(
				getInstance().getCalificacions());
	}

}
