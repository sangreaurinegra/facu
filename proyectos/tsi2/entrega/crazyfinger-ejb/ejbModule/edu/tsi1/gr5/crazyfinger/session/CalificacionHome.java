package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessages;
import org.jboss.web.tomcat.service.StatusServlet;

@Name("calificacionHome")
public class CalificacionHome extends EntityHome<Calificacion> {

	@In(create = true)
	ServicioHome servicioHome;
	@In(create = true)
	UsuarioHome usuarioHome;
	
	//para el form
	private int puntos;
	private String comentario;

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

	public void agregarCalficacion(long idServicio, long idUsuario){
		Calificacion c= this.createInstance();
		c.setComentario(comentario);
		c.setPuntaje(puntos);
		usuarioHome.setUsuarioIdUsuario(idUsuario);
		usuarioHome.load();
		c.setUsuario(usuarioHome.getInstance());
		servicioHome.setServicioIdServicio(idServicio);
		servicioHome.load();
		c.setServicio(servicioHome.getInstance());
		this.setInstance(c);
		this.persist();
		servicioHome.getInstance().getCalificacions().add(this.getInstance());
		
		StatusMessages.instance().clear();
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}
