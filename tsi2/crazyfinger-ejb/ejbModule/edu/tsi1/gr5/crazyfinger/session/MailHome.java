package edu.tsi1.gr5.crazyfinger.session;

import java.util.Date;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessages;

@Name("mailHome")
public class MailHome extends EntityHome<Mail> {

	@In(create = true)
	UsuarioHome usuarioHome;
	
	@In StatusMessages statusMessages;

	Mail respuesta;
	
	public void setMailIdMail(Long id) {
		setId(id);
	}

	public Long getMailIdMail() {
		return (Long) getId();
	}

	@Override
	protected Mail createInstance() {
		Mail mail = new Mail();
		return mail;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		usuarioHome.setUsuarioIdUsuario(getInstance().getIdDestinatario());
		usuarioHome.load();
		Usuario usuarioByDestinatario = usuarioHome.getDefinedInstance();
		if (usuarioByDestinatario != null) {
			getInstance().setUsuarioByDestinatario(usuarioByDestinatario);
		}
		usuarioHome.setUsuarioIdUsuario(getInstance().getIdRemitente());
		usuarioHome.load();
		Usuario usuarioByRemitente = usuarioHome.getDefinedInstance();
		if (usuarioByRemitente != null) {
			getInstance().setUsuarioByRemitente(usuarioByRemitente);
		}
	}

	public boolean isWired() {
		if (getInstance().getUsuarioByDestinatario() == null)
			return false;
		if (getInstance().getUsuarioByRemitente() == null)
			return false;
		return true;
	}

	public Mail getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public void marcarLeido(long idMail){
		setMailIdMail(idMail);
		load();
		getInstance().setEstado(Mail.ESTADO_LEIDO);
		update();
		statusMessages.clear();
	}
	
	public String crearRespuesta(long idMail){
		
		respuesta = this.createInstance();
		setMailIdMail(idMail);
		load();
		respuesta.setUsuarioByDestinatario(getInstance().getUsuarioByRemitente());
		respuesta.setUsuarioByRemitente(getInstance().getUsuarioByDestinatario());
		respuesta.setEstado(Mail.ESTADO_INICIAL);
		respuesta.setFechaEnviado(new Date());
		this.setInstance(respuesta);

		return "/MailEdit.xhtml";
	}
	
	@Override
	public String persist(){
		getInstance().setFechaEnviado(new Date());
		getInstance().setEstado(Mail.ESTADO_INICIAL);
		return super.persist();
	}
	
	public void eliminar(long idMail){
		setMailIdMail(idMail);
		load();
		this.remove();
		this.getEntityManager().flush();
		statusMessages.clear();
	}
	
}
