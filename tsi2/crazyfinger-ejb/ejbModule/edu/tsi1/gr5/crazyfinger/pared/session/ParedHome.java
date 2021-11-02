package edu.tsi1.gr5.crazyfinger.pared.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import edu.tsi1.gr5.crazyfinger.media.entity.Media;
import edu.tsi1.gr5.crazyfinger.pared.entity.Pared;
import edu.tsi1.gr5.crazyfinger.session.UsuarioHome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.URIException;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import EDU.oswego.cs.dl.util.concurrent.Channel;

import yarfraw.core.datamodel.ChannelFeed;
import yarfraw.core.datamodel.YarfrawException;
import yarfraw.io.FeedReader;

@Name("paredHome")
public class ParedHome extends EntityHome<Pared> {

	@In(create = true)
	UsuarioHome usuarioHome;
	
	List<Media> mediasAmigos;

	public void setParedIdPared(Long id) {
		setId(id);
	}

	public Long getParedIdPared() {
		return (Long) getId();
	}

	@Override
	protected Pared createInstance() {
		Pared pared = new Pared();
		return pared;
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
		return true;
	}

	public Pared getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Media> getMedias() {
		List<Media> ret = getInstance() == null ? null : new ArrayList<Media>(
				getInstance().getMedias());
		
		if(ret!=null){
			
			Collections.sort(ret);
		}
		
		return ret; 
	}

	public List<Media> getMediasAmigos(long idusuario){

		List<Media> ret = mediasAmigos;
		if(ret==null){
			ret = new ArrayList<Media>();
			usuarioHome.setUsuarioIdUsuario(idusuario);
			usuarioHome.load();
			List<Solicitud> amigos=usuarioHome.amigos();

			for (Solicitud solicitud : amigos) {
				ret.addAll(solicitud.getUsuarioBySolicitado().getMedias());
			}

			Collections.sort(ret);
			mediasAmigos=ret;
		}

		return ret ;
	}
	
}
