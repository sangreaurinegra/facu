package edu.tsi1.gr5.crazyfinger.media.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import edu.tsi1.gr5.crazyfinger.media.entity.Media;
import edu.tsi1.gr5.crazyfinger.pared.entity.Pared;
import edu.tsi1.gr5.crazyfinger.pared.session.ParedHome;
import edu.tsi1.gr5.crazyfinger.session.ComentarioHome;
import edu.tsi1.gr5.crazyfinger.session.LugarHome;
import edu.tsi1.gr5.crazyfinger.session.UsuarioHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.persistence.Column;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("mediaHome")
public class MediaHome extends EntityHome<Media> {

	@In(create = true)
	LugarHome lugarHome;
	@In(create = true)
	ParedHome paredHome;
	
	@In(create = true)
	ComentarioHome comentarioHome;
	
	
	@In
	Usuario usuario;
	
	String valueComentario;

	public void setMediaIdMedia(Long id) {
		setId(id);
	}

	public Long getMediaIdMedia() {
		return (Long) getId();
	}

	@Override
	protected Media createInstance() {
		Media media = new Media();
		return media;
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
		Pared pared = paredHome.getDefinedInstance();
		if (pared != null) {
			getInstance().setPared(pared);
		}
		//Usuario usuario = usuarioHome.getDefinedInstance();
		if (usuario != null) {
			if (getInstance().getUsuario() != null) {
				getInstance().setUsuario(usuario);
			}
		}
	}

	public boolean isWired() {
		if (getInstance().getUsuario() == null)
			return false;
		return true;
	}

	public Media getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Comentario> getComentarios() {
		return getInstance() == null ? null : new ArrayList<Comentario>(
				getInstance().getComentarios());
	}
	
	@Override
	public String persist(){
		getInstance().setUsuario(usuario);
		getInstance().setFechaEnviado(new Date());
		return super.persist();
	}
	
	public void publicarEnPared(){
		System.out.println("publicarEnPared ");
		getInstance().setPared(usuario.getPared());
		update();
	}

	public String getValueComentario() {
		return valueComentario;
	}

	public void setValueComentario(String valueComentario) {
		this.valueComentario = valueComentario;
	}
	
	
	public void agregarComentario(){
		System.out.println("Comentario "+valueComentario);
		Comentario com = new Comentario();
		com.setMedia(getInstance());
		com.setMensaje(valueComentario);
		com.setUsuario(usuario);
		com.setFechaEnviado(new Date());
		
		comentarioHome.setInstance(com);
		comentarioHome.persist();
		
		getInstance().getComentarios().add(com);
		
		
		
	}
	
	
	
}
