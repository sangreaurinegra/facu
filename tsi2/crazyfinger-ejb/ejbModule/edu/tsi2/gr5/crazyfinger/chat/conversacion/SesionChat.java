package edu.tsi2.gr5.crazyfinger.chat.conversacion;

import static edu.tsi2.gr5.crazyfinger.chat.manager.ConstantesTiposMensaje.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import edu.tsi1.gr5.crazyfinger.entity.Usuario;
import edu.tsi2.gr5.crazyfinger.chat.ChatBean;
import edu.tsi2.gr5.crazyfinger.chat.MensajesPendientes;


@Name("sesionChat")
@Scope(ScopeType.PAGE)
public class SesionChat implements Serializable{

	private Usuario usuario;
	private Usuario usuarioDestino;
	
	private long idUsuarioOrigen, idUsuarioDestino;
	
	private String inputMensaje;
	
	@In
	private ChatBean chatBean;
	
//	private MensajesPendientes mensajesPendientes;
	
	private InitialContext jndi=null;
	private ConnectionFactory connectionFactory = null;
	private Session session = null;
	private Connection connection = null;
	private MessageConsumer subscriber = null; 
	private MessageProducer producer = null; 

	Destination colaOrigen = null;
	Destination colaDestino = null;
	
	private List<String> mensajesRecibidos = new ArrayList<String>();
	
	public SesionChat() {
//		this.mensajesPendientes = chatBean.getMensajesPendientes();
//		log (" cantidadDeMensajes " + mensajesPendientes.cantidadDeMensajes());

	}
	
	public void actualizarChat(long idUsuarioOrigen,long idUsuarioDestino) {
		log("idUsuarioDestino= " + idUsuarioDestino );
		log("idUsuarioOrigen= " + idUsuarioOrigen );
		this.idUsuarioDestino = idUsuarioDestino;
		this.idUsuarioOrigen = idUsuarioOrigen;
		
		this.colaOrigen = chatBean.getRegistroConexiones().getDestination(idUsuarioOrigen);
		this.colaDestino = chatBean.getRegistroConexiones().getDestination(idUsuarioDestino);
				
		log("(this.colaOrigen==null) " + (this.colaOrigen==null));
		log("(this.colaDestino==null) " + (this.colaDestino==null));
		
		try {
			establecerConexiones();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void establecerConexiones() throws JMSException, NamingException {
		log("establecerConexiones");
		Session sesion = getSession();
		log("creando Producer (colaDestino==null) ->" + (colaDestino==null));
		producer = sesion.createProducer(this.colaDestino);

		getConnection().start();
	}
	

	
	public List<String> obtenerMensajes() {
		List<String> mensajes = getMensajesPendientes();
//		log(" OBTENER MENSAJES - IDUSUARIO " +  idUsuarioOrigen + " size " + mensajes.size());
		mensajesRecibidos.addAll(mensajes);
		return mensajesRecibidos;
	}
	
	
	private List<String> getMensajesPendientes() {
//		return MensajesPendientes.getInstance().consumirMensajesDe(idUsuarioDestino);
		return chatBean.getMensajesPendientes().consumirMensajesDe(idUsuarioDestino);
	}
	
	

	private InitialContext getJndi() throws NamingException {
		if (jndi == null) {
			jndi = new InitialContext();			
		}
		return jndi;
	}
	
	private ConnectionFactory getConnectionFactory() throws NamingException {
		if (connectionFactory == null) {
			log("{Conversacion.getConnectionFactory}Obtener ConectionFactory");
			this.connectionFactory = (ConnectionFactory)getJndi().lookup("ConnectionFactory");
			log("{Conversacion.getConnectionFactory} Exito al obtener conectionFactory");
		}
		return this.connectionFactory;
	}

	private Connection getConnection() throws JMSException, NamingException {
		if (connection == null) {
			log("{Conversacion.getConnection}Obtener Conection ");
			connection = getConnectionFactory().createConnection();
			log("{Conversacion.getConnection} exito al Obtener Conection ");
		}
		
		return connection;
	}
	
	private Session getSession() throws JMSException, NamingException {
		if (session == null) {
			log("{Conversacion.getSession}Obtener session");
			session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			log("{Conversacion.getSession}exito al Obtener session");
		}
		return session;
	}
	
//	private MessageConsumer getSubscriber () throws JMSException, NamingException {
//		if (subscriber == null) {
//			log("{Conversacion.getProducer}Obtener subscriber");
//			subscriber = getSession().createConsumer(this.tempQueue);
//			log("{Conversacion.getProducer}Obtener subscriber");
//		}
//		return subscriber;
//	}
	

	
	// ---- Setters y getters ..

	public Usuario getUsuarioOrigen() {
		return usuario;
	}


	public void setUsuarioOrigen(Usuario usuarioOrigen) {
		this.usuario = usuarioOrigen;
	}


	public Usuario getUsuarioDestino() {
		return usuarioDestino;
	}


	public void setUsuarioDestino(Usuario usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public String getInputMensaje() {
		return inputMensaje;
	}

	public void setInputMensaje(String inputMensaje) {
		this.inputMensaje = inputMensaje;
	}
	
	public void enviarMensaje() throws JMSException, NamingException {
		log(" ** enviar mensaje  **  " );
		String txt = getInputMensaje();
		setInputMensaje("");
		log(" ** enviar mensaje  TEXTO **  " + txt );

		log(" envmensaje getSession" );
		Session sesion = getSession();
		log ("sesion null? " + (sesion==null));
		
		log("antes de crear mensaje para mandar");
		ObjectMessage om = sesion.createObjectMessage();
		om.setObjectProperty(PROPERTY_TEXTO_MENSAJE, txt);
		om.setObjectProperty(PROPERTY_ID_USUARIO,idUsuarioOrigen);
		om.setObjectProperty(PROPERTY_TIPO_MENSAJE, TIPO_MENSAJE_TEXTO);
		
		log("mensajesRecibidos es null? "+(mensajesRecibidos==null));
		mensajesRecibidos.add("[yo] " + txt);
		
		log("Enviando mensaje: " + txt + " producer=null? " + (producer==null));
		producer.send(om);
	}
	
	public long getIdUsuarioOrigen() {
		return idUsuarioOrigen;
	}

	public void setIdUsuarioOrigen(long idUsuarioOrigen) {
		this.idUsuarioOrigen = idUsuarioOrigen;
	}

	public long getIdUsuarioDestino() {
		return idUsuarioDestino;
	}

	public void setIdUsuarioDestino(long idUsuarioDestino) {
		this.idUsuarioDestino = idUsuarioDestino;
	}

	public void log(String s) {
		System.out.println("[SesionChat] " + s);
	}
	
	public boolean hayMensajesPendientes() {
//		return MensajesPendientes.getInstance().hayMensajesPendientesDe(idUsuarioDestino);
		return chatBean.getMensajesPendientes().hayMensajesPendientesDe(idUsuarioDestino);
	}
	
	public void cerrarSesion() {
		try {
			if (producer!=null)
				producer.close();
			log("cerrando sesion!!!!");
			getSession().close();
			getConnection().close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
