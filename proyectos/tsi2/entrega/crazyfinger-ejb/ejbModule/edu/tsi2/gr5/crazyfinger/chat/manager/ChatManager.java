package edu.tsi2.gr5.crazyfinger.chat.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.MessageDriven;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import edu.tsi1.gr5.crazyfinger.entity.Solicitud;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;
import edu.tsi1.gr5.crazyfinger.session.UsuarioList;

import javax.ejb.ActivationConfigProperty;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static edu.tsi2.gr5.crazyfinger.chat.manager.ConstantesTiposMensaje.*;

@Name("chatManager")
@MessageDriven(name = "chatDrivenBean", activationConfig =  {
      
      @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/chatTopic")

  })
public class ChatManager implements MessageListener{


	
	@In(create = true)
	private UsuarioList usuarioList;
	
	private InitialContext jndi=null;
	private ConnectionFactory connectionFactory = null;
	
	public void onMessage(Message msg) {
		log("onMessage");
		try {
			if (esRegistroUsuario(msg)) {
				log("onMessage esRegistroUsuario ");
				registrarUsuario(msg);
			}
			else if (esLogOutUsuario(msg)) {
				log("onMessage esLogOutUsuario ");
				desRegistrarUsuario(msg);
			}
			else {
				log("ERROR!!! ONMESSAGE - NO ES NI REGISTRO USUARIO NI LOGOFF");
			}
		} catch (JMSException e) {
			// TODO HACER ALGO CON ESTO!
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO HACER ALGO CON ESTO!
			e.printStackTrace();
		}
	}
	
	public boolean esRegistroUsuario(Message msg) throws JMSException {
		Integer tipoMensaje = (Integer)msg.getObjectProperty(PROPERTY_TIPO_MENSAJE);
		if (tipoMensaje != null) {
			if (tipoMensaje == TIPO_MENSAJE_REGISTRO) {
				return true;
			}
		}
		return false;
	}
	
	public boolean esLogOutUsuario(Message msg) throws JMSException {
		Integer tipoMensaje = (Integer)msg.getObjectProperty(PROPERTY_TIPO_MENSAJE);
		if (tipoMensaje != null) {
			if (tipoMensaje == TIPO_MENSAJE_SALIDA_DEL_CHAT) {
				return true;
			}
		}
		return false;
	}
	
	public void registrarUsuario(Message msg) throws JMSException, NamingException {
		log (" registrarUsuario");
		Long idUsuario = (Long)msg.getObjectProperty(PROPERTY_ID_USUARIO);
		log (" registrarUsuario idUsuario " + idUsuario);
		Destination destination = msg.getJMSReplyTo();
		RegistroColasUsuarios registro = RegistroColasUsuarios.getInstance();
		// Guardar el usuario y la cola donde va a recibir mensajes.
		registro.add(idUsuario, destination);
		// Obtener los contactos para avisarle
		List<Usuario> contactos = obtenerContactos(idUsuario);
		for (Usuario contacto : contactos) {
			log ("avisar contacto " + contacto.getIdUsuario() + " " + contacto.getNombre());
			// obtener cola
			if (registro.exists(contacto.getIdUsuario())) {
				log ("registro.exists(contacto.getIdUsuario() " + contacto.getIdUsuario() + " " + contacto.getNombre());
				log ("registro.getDestination(contacto.getIdUsuario()) es null? " + (registro.getDestination(contacto.getIdUsuario())==null) );
				log ("destination es null? " + (destination==null) );
				
				// si está conectado le aviso que se anotó un contacto.
				enviarNotificacionRegistro(registro.getDestination(contacto.getIdUsuario()),idUsuario,destination);
				// y le aviso a el que se anotó el contacto.
				enviarNotificacionRegistro(destination,contacto.getIdUsuario(),registro.getDestination(contacto.getIdUsuario()));
			}
		}
	}
	


	public void desRegistrarUsuario(Message msg) throws JMSException, NamingException {
		log("desRegistrarUsuario");
		Long idUsuario = (Long)msg.getObjectProperty(PROPERTY_ID_USUARIO);
		RegistroColasUsuarios registro = RegistroColasUsuarios.getInstance();
		// Guardar el usuario y la cola donde va a recibir mensajes.
		registro.removeDestination(idUsuario);
		// Obtener los contactos para avisarle
		List<Usuario> contactos = obtenerContactos(idUsuario);
		for (Usuario contacto : contactos) {
			// obtener cola
			if (registro.exists(contacto.getIdUsuario())) {
				// si está conectado le aviso.
				enviarNotificacionSalida(registro.getDestination(contacto.getIdUsuario()),idUsuario);
			}
		}
	}
	
	/**
	 * Obtiene los contactos de un usuario  
	 * @param idUsuario el id del usuario
	 * @return una lista de usuarios con los contactos.
	 */
	public List<Usuario> obtenerContactos(Long idUsuario) {
		log("obtenerContactos");
		// Obtener usuario.
		Usuario usuario = usuarioList.getUsuarioPorIdUsuario(idUsuario);
		
		HashMap<Long, Usuario> tabla = new HashMap<Long, Usuario>();
		for (Solicitud sol : usuario.getSolicitudsForSolicitado()) {
			Usuario contacto = sol.getUsuarioBySolicitante(); 
			tabla.put(contacto.getIdUsuario(), contacto);
		}
		for (Solicitud sol : usuario.getSolicitudsForSolicitante()) {

			Usuario contacto = sol.getUsuarioBySolicitado(); 
			tabla.put(contacto.getIdUsuario(), contacto);
		}
		List<Usuario> contactos = new ArrayList<Usuario>();
		contactos = new ArrayList<Usuario>();
		contactos.addAll(tabla.values());
		return contactos;
	}
	
	
	private void enviarNotificacionRegistro(Destination colaContacto,
			Long idUsuario, Destination colaUsuarioRegistrado) throws NamingException, JMSException {
		log("enviarNotificacionRegistro");
		Connection connection = getConnectionFactory().createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(colaContacto);
		
        ObjectMessage om = session.createObjectMessage();
		om.setObjectProperty(PROPERTY_TIPO_MENSAJE, TIPO_MENSAJE_REGISTRO);
        om.setObjectProperty(PROPERTY_ID_USUARIO, idUsuario);
        om.setJMSReplyTo(colaUsuarioRegistrado);
		
        producer.send(om);
        
        connection.start();
        
        
        // TODO: esta bien cerrar todo esto ahora?
		producer.close();
		session.close();
		connection.close();
		
	}
	
	private void enviarNotificacionSalida(Destination colaContacto,
			Long idUsuario) throws NamingException, JMSException {
		log("enviarNotificacionSalida");
		Connection connection = getConnectionFactory().createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(colaContacto);
		
        ObjectMessage om = session.createObjectMessage();
		om.setObjectProperty(PROPERTY_TIPO_MENSAJE, TIPO_MENSAJE_SALIDA_DEL_CHAT);
        om.setObjectProperty(PROPERTY_ID_USUARIO, idUsuario);
		
        producer.send(om);
        
        connection.start();
        
        
        // TODO: esta bien cerrar todo esto ahora?
		producer.close();
		session.close();
		connection.close();
		
	}
	
	private ConnectionFactory getConnectionFactory() throws NamingException {
		if (connectionFactory == null) {
			log("{.getConnectionFactory}Obtener ConectionFactory");
			this.connectionFactory = (ConnectionFactory)getJndi().lookup("ConnectionFactory");
			log("{.getConnectionFactory} Exito al obtener conectionFactory");
		}
		return this.connectionFactory;
	}
	
	private InitialContext getJndi() throws NamingException {
		if (jndi == null) {
			jndi = new InitialContext();			
		}
		return jndi;
	}
	
	
	private void log(String s) {
		System.out.println("[ChatManager] " + s);
	}
	
}
