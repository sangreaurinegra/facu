package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("proyectoviajeHome")
public class ProyectoviajeHome extends EntityHome<Proyectoviaje> {

	@In(create = true)
	UsuarioHome usuarioHome;

	public void setProyectoviajeIdProyectoviaje(Long id) {
		setId(id);
	}

	public Long getProyectoviajeIdProyectoviaje() {
		return (Long) getId();
	}

	@Override
	protected Proyectoviaje createInstance() {
		Proyectoviaje proyectoviaje = new Proyectoviaje();
		return proyectoviaje;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
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

	public Proyectoviaje getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Nodopv> getNodopvs() {
		return getInstance() == null ? null : new ArrayList<Nodopv>(
				getInstance().getNodopvs());
	}

	public List<InvitacionProyecto> getInvitaciones() {
		return getInstance() == null ? null : new ArrayList<InvitacionProyecto>(
				getInstance().getInvitaciones());
	}
	
}
