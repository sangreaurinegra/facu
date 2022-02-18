package edu.tsi1.gr5.crazyfinger.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;
import org.hibernate.validator.Length;

import edu.tsi1.gr5.crazyfinger.entity.Mail;
import edu.tsi1.gr5.crazyfinger.entity.Solicitud;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;

//@TransactionManagement(TransactionManagementType.BEAN)	
@Stateful
@Name("BuscarGente")
public class BuscarGenteBean implements BuscarGente
{
    @Logger private Log log;

    @In StatusMessages statusMessages;

    private String value;
    
    private List<Usuario> resultado;
    
    private List<Solicitud> amigos;

    @In(create= true)
    UsuarioList usuarioList;
    
    @In(create= true)
    SolicitudHome solicitudHome;
    
    @In
    Usuario usuario;
    
    @In(create= true)
    UsuarioHome usuarioHome;
    
    @In(create= true)
    MailHome mailHome;
    
    public void buscarGente()
    {
        // implement your business logic here
        log.info("BuscarGente.buscarGente() action called with: #{BuscarGente.value}");
        
        resultado = usuarioList.buscarGente(value);
        
        statusMessages.add("Usuarios encontrados: {0}",resultado.size());
    }

    // add additional action methods

    @Create
    public void init(){
    	resultado = new ArrayList<Usuario>();
    	usuarioHome.setInstance(usuario);
    	usuarioHome.load();
    	amigos = usuarioHome.amigos();
    }
    
    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Remove
    public void destroy() {}

	public List<Usuario> getResultado() {
		return resultado;
	}

	public void setResultado(List<Usuario> resultado) {
		this.resultado = resultado;
	}

	public List<Solicitud> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Solicitud> amigos) {
		this.amigos = amigos;
	}

	public String enviarMensaje(long idUsuario) {
		log.info("creando mail");
		Mail mail = mailHome.createInstance();
		mail.setEstado(Mail.ESTADO_INICIAL);
		mail.setMensaje(value);
		mail.setUsuarioByRemitente(usuario);
		log.info("buscando destinatario");
		usuarioHome.setUsuarioIdUsuario(idUsuario);
		usuarioHome.load();
		Usuario u = usuarioHome.getInstance();
		mail.setUsuarioByDestinatario(u);
		mail.setFechaEnviado(new Date());
		mailHome.setInstance(mail);
		log.info("enviando mail");
		mailHome.persist();
		log.info("mail enviado");
		statusMessages.clear();
		statusMessages.add("Mensaje para {0}, enviado con exito!",u.getNombre());
		return "";
		/**/
	}

	public String solicitarAmistad(long idUsuario) {
		Solicitud s = solicitudHome.createInstance();
		s.setEstado(Solicitud.ESTADO_PENDIENTE);
		s.setUsuarioBySolicitante(usuario);
		usuarioHome.setUsuarioIdUsuario(idUsuario);
		try{
			usuarioHome.load();
			Usuario u = usuarioHome.getDefinedInstance();
			s.setUsuarioBySolicitado(u);
			s.setMensaje(value);
			solicitudHome.setInstance(s);
			return "/SolicitudEdit.xhtml";
		}
		catch(Exception e){
			statusMessages.add("No se encontro el usuario de id: {0}", idUsuario);
			return "";
		}
		
	}


	public void aceptarSolicitud(long idSolicitud){
		if(idSolicitud > 0){
			solicitudHome.setSolicitudIdsolicitud(idSolicitud);
			solicitudHome.load();			
			solicitudHome.getInstance().setEstado(Solicitud.ESTADO_ACEPTADO);
			solicitudHome.persist();
			log.info("idSolicitud {0} ha sido aceptada",idSolicitud);
			log.info("Creando solicitud de respuesta");
			Solicitud sInicio= solicitudHome.getInstance();
			Solicitud s= solicitudHome.createInstance();
			s.setEstado(Solicitud.ESTADO_ACEPTADO);
			s.setMensaje("solicitud de respuesta, generada automaticamente");
			s.setUsuarioBySolicitado(sInicio.getUsuarioBySolicitante());
			s.setUsuarioBySolicitante(sInicio.getUsuarioBySolicitado());
			solicitudHome.setInstance(s);
			solicitudHome.persist();
			log.info("idSolicitud {0} ha sido generada",solicitudHome.getInstance().getIdsolicitud());
			
		}
		else{
			log.info("idSolicitud es cero o menor");
		}
	}
	
	public void rechazarSolicitud(long idSolicitud){
		if(idSolicitud > 0){
			solicitudHome.setSolicitudIdsolicitud(idSolicitud);
			solicitudHome.load();
			solicitudHome.remove();
			log.info("idSolicitud {0} ha sido removida",idSolicitud);
		}
		else{
			log.info("idSolicitud es cero o menor");
		}
		
	}
	
	public void limpiarMensaje(){
		value = "";
		log.info("mensaje limpiado");
	}
}
