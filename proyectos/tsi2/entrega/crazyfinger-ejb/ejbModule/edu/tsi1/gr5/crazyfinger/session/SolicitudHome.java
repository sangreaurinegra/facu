package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("solicitudHome")
public class SolicitudHome extends EntityHome<Solicitud> {

	@In(create = true)
	UsuarioHome usuarioHome;

	public void setSolicitudIdsolicitud(Long id) {
		setId(id);
	}

	public Long getSolicitudIdsolicitud() {
		return (Long) getId();
	}

	@Override
	protected Solicitud createInstance() {
		Solicitud solicitud = new Solicitud();
		return solicitud;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		usuarioHome.setUsuarioIdUsuario(getInstance().getIdSolicitado());
		usuarioHome.load();
		Usuario usuarioBySolicitado = usuarioHome.getDefinedInstance();
		if (usuarioBySolicitado != null) {
			getInstance().setUsuarioBySolicitado(usuarioBySolicitado);
		}
		usuarioHome.setUsuarioIdUsuario(getInstance().getIdSolicitante());
		usuarioHome.load();
		Usuario usuarioBySolicitante = usuarioHome.getDefinedInstance();
		if (usuarioBySolicitante != null) {
			getInstance().setUsuarioBySolicitante(usuarioBySolicitante);
		}
	}

	public boolean isWired() {
		if (getInstance().getUsuarioBySolicitado() == null)
			return false;
		if (getInstance().getUsuarioBySolicitante() == null)
			return false;
		return true;
	}

	public Solicitud getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
