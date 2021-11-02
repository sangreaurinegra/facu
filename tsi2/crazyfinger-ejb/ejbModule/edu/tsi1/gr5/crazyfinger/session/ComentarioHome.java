package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import edu.tsi1.gr5.crazyfinger.media.entity.Media;
import edu.tsi1.gr5.crazyfinger.media.session.MediaHome;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("comentarioHome")
public class ComentarioHome extends EntityHome<Comentario> {

	@In(create = true)
	MediaHome mediaHome;
	@In(create = true)
	UsuarioHome usuarioHome;

	public void setComentarioIdComentario(Long id) {
		setId(id);
	}

	public Long getComentarioIdComentario() {
		return (Long) getId();
	}

	@Override
	protected Comentario createInstance() {
		Comentario comentario = new Comentario();
		return comentario;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Media media = mediaHome.getDefinedInstance();
		if (media != null) {
			getInstance().setMedia(media);
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

	public Comentario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
