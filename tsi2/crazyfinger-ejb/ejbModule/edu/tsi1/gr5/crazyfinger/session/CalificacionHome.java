package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("calificacionHome")
public class CalificacionHome extends EntityHome<Calificacion> {

	@In(create = true)
	ServicioHome servicioHome;
	@In(create = true)
	UsuarioHome usuarioHome;

	public void setCalificacionIdCalificacion(Long id) {
		setId(id);
	}

	public Long getCalificacionIdCalificacion() {
		return (Long) getId();
	}

	@Override
	protected Calificacion createInstance() {
		Calificacion calificacion = new Calificacion();
		return calificacion;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Servicio servicio = servicioHome.getDefinedInstance();
		if (servicio != null) {
			getInstance().setServicio(servicio);
		}
		Usuario usuario = usuarioHome.getDefinedInstance();
		if (usuario != null) {
			getInstance().setUsuario(usuario);
		}
	}

	public boolean isWired() {
		if (getInstance().getUsuario() == null)
			return false;
		return true;
	}

	public Calificacion getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
