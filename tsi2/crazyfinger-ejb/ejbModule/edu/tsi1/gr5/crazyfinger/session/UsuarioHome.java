package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import edu.tsi1.gr5.crazyfinger.media.entity.Media;
import edu.tsi1.gr5.crazyfinger.pared.entity.Pared;
import edu.tsi1.gr5.crazyfinger.pared.session.ParedHome;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Redirect;
import org.jboss.seam.framework.EntityHome;

@Name("usuarioHome")
public class UsuarioHome extends EntityHome<Usuario> {

	@In(create = true)
	ParedHome paredHome;
	@In
	private FacesMessages facesMessages;
	
	@In(required = false)
	@Out(scope=ScopeType.SESSION, required=false)
	private Usuario usuario;
	
	
	
	// solo se usa al salvar un usuario, es horrible ponerlo aca pero no jodan
	String verifyPassword;

	@Override
	public String persist(){
		//if(verifyPassword.equalsIgnoreCase(getInstance().getPassword())){
			paredHome.setInstance(getInstance().getPared());
			paredHome.persist();
			getInstance().setPassword("nopassword");
			return super.persist();
		//}
		//facesMessages.addToControl("password", "El password no coincide escribalo nuevamente");
		//return null;
	}
	
	@Override
	public String update(){
	
		paredHome.setInstance(getInstance().getPared());
		paredHome.update();
		getInstance().setPassword("nopassword");
		byte[] img = getInstance().getImage();
		if(img==null || img.length==0){
			byte[] imgsession = usuario.getImage();
			if(imgsession==null || imgsession.length==0){
				getInstance().setImage(null);
				getInstance().setImageContentType(null);
			}else{
				getInstance().setImage(imgsession);
				getInstance().setImageContentType(usuario.getImageContentType());
			}
		}
		Usuario user = getInstance();
		String result=super.update();
		if(result.equalsIgnoreCase("updated")){
			usuario.setImage(getInstance().getImage());
			usuario.setImageContentType(getInstance().getImageContentType());
			usuario.setCiudad(getInstance().getCiudad());
			usuario.setEstadoCivil(getInstance().getEstadoCivil());
			usuario.setFechaNacimiento(getInstance().getFechaNacimiento());
			usuario.setNombre(getInstance().getNombre());
			usuario.setOpenid(getInstance().getOpenid());
			usuario.setPais(getInstance().getPais());
			usuario.setSexo(getInstance().getSexo());
		}
		return result;
	}

	public void setUsuarioIdUsuario(Long id) {
		setId(id);
	}

	public Long getUsuarioIdUsuario() {
		return (Long) getId();
	}

	@Override
	protected Usuario createInstance() {
		Usuario usuario = new Usuario();
		return usuario;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Pared pared = paredHome.getDefinedInstance();
		if (pared != null) {
			getInstance().setPared(pared);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Usuario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Calificacion> getCalificacions() {
		return getInstance() == null ? null : new ArrayList<Calificacion>(
				getInstance().getCalificacions());
	}

	public List<Comentario> getComentarios() {
		return getInstance() == null ? null : new ArrayList<Comentario>(
				getInstance().getComentarios());
	}

	public List<Mail> getMailsForDestinatario() {
		return getInstance() == null ? null : new ArrayList<Mail>(getInstance()
				.getMailsForDestinatario());
	}

	public List<Mail> getMailsForRemitente() {
		return getInstance() == null ? null : new ArrayList<Mail>(getInstance()
				.getMailsForRemitente());
	}

	public List<Media> getMedias() {
		return getInstance() == null ? null : new ArrayList<Media>(
				getInstance().getMedias());
	}

	public List<Proyectoviaje> getProyectoviajes() {
		return getInstance() == null ? null : new ArrayList<Proyectoviaje>(
				getInstance().getProyectoviajes());
	}

	public List<Solicitud> getSolicitudsForSolicitado() {
		return getInstance() == null ? null : new ArrayList<Solicitud>(
				getInstance().getSolicitudsForSolicitado());
	}

	public List<Solicitud> getSolicitudsForSolicitante() {
		return getInstance() == null ? null : new ArrayList<Solicitud>(
				getInstance().getSolicitudsForSolicitante());
	}
	
	//retorna aquellas solicitudes que aceptadas
	public List<Solicitud> amigos() {
		if( getInstance() == null) return null;
		List<Solicitud> amigos =	new ArrayList<Solicitud>();
		for(Iterator<Solicitud> it = getInstance().getSolicitudsForSolicitante().iterator();it.hasNext();){
			Solicitud s = it.next();
			if(s.getEstado() == s.ESTADO_ACEPTADO)
				amigos.add(s);
		}
		
		return amigos;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}
	
	public void vistaUsuario(){
		Redirect.instance().setViewId("/Usuario.xhtml");
		Redirect.instance().execute();
	}
	
	public List<Proyectoviaje> proyectosDeUsuario(long idUsuario) {
		if(getInstance() == null || (getInstance() != null && getInstance().getIdUsuario() != idUsuario)){
			this.setUsuarioIdUsuario(idUsuario);
			this.load();
		}
			
		return getInstance() == null ? null : new ArrayList<Proyectoviaje>(
				getInstance().getProyectoviajes());
	}
	
}
