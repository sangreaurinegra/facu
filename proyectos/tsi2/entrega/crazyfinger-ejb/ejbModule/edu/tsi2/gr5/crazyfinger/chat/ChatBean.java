package edu.tsi2.gr5.crazyfinger.chat;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.ajax4jsf.event.PushEventListener;
import org.apache.bsf.engines.javascript.JavaScriptEngine;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import edu.tsi1.gr5.crazyfinger.entity.Solicitud;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;
import edu.tsi1.gr5.crazyfinger.session.UsuarioHome;
import edu.tsi1.gr5.crazyfinger.session.UsuarioList;
import edu.tsi2.gr5.crazyfinger.chat.manager.RegistroColasUsuarios;

import static edu.tsi2.gr5.crazyfinger.chat.manager.ConstantesTiposMensaje.*;

/**
 * TODO: manejo de errores.
 * @author ALEX101
 *
 */
@Name("chatBean")
@Scope(ScopeType.SESSION)
public class ChatBean implements MessageListener , Serializable  {

	private List<Usuario> contactos = null;
	private Usuario usuario;
	
	private int cantidadDeContactos = 0;

	@In(create = true)
	UsuarioList usuarioList;
	
	RegistroConexiones registroInvitaciones = null;
	
	private InitialContext jndi=null;	

	private MensajesPendientes mensajesPendientes = new MensajesPendientes();
	
	private Destination miDestination;
	
	// Datos para la sesion
	private ConnectionFactory connectionFactory = null;
	private Session sesionCola = null;
	private Connection connection = null;
	private MessageConsumer queueSubscriber = null; 
	// ------------------------------
	// Para registrarse.
	private TopicSession pubSession;
	private TopicSession subSession;
	private TopicPublisher publisher;
	private TopicSubscriber subscriber;
	private TopicConnection tcon;
	// -------------------------------
	
	public ChatBean(){
		try {
			InitialContext cntxt = getJndi();
//			ejb = (PanelContactosChatRemote) cntxt.lookup("crazyfinger-ear/PanelContactosEjb/local");
			if (darUsuario()!=null) {
			
				cargarContactos();
				enviarMensajeAChatManager();
			}
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		contactos = new ArrayList<Usuario>();
	}
	
	public void cargarUsuario(String openid) {
//		//log("cargarUsuario - openid " + openid);
//		//log("(ejb==null) " + (ejb==null));
		
		if (this.darUsuario()==null) {
			this.usuario = usuarioList.getUsuarioPorOpenId(openid);
	
			//log("ususario " + usuario.getNombre());
			cargarContactos();
			
			enviarMensajeAChatManager();
		}
		
	}
	
	
	public void enviarMensajeAChatManager() {
		try {
			//log("enviarMensajeAChatManager");
			
			InitialContext jndi = getJndi();
			
			// Obtener TopicConnectionFactory
			//log("Obtener TopicConnectionFactory -?" );
			TopicConnectionFactory tf = (TopicConnectionFactory) jndi.lookup("ConnectionFactory");
			//log("exito al Obtener TopicConnectionFactory " + (tf!=null) );
			
			tcon = tf.createTopicConnection();
			
	        pubSession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
	        subSession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			
	        // Buscar el topic
	        //log("Obtener topic" );
	        Topic chatTopic = (Topic) jndi.lookup("topic/chatTopic"); 
	        //log("exito al Obtener topic " + (chatTopic!=null) );
	        
	        publisher = pubSession.createPublisher(chatTopic);
	        miDestination = getSesionCola().createTemporaryQueue();
	        
	        

	        ObjectMessage message = pubSession.createObjectMessage();
	        message.setObjectProperty(PROPERTY_TIPO_MENSAJE, TIPO_MENSAJE_REGISTRO);
	        message.setObjectProperty(PROPERTY_ID_USUARIO, usuario.getIdUsuario());
	        log("********** miDest==null " + (miDestination==null));
	        message.setJMSReplyTo(miDestination);
	        
	        escucharCola(miDestination);
	        
	        this.getRegistroConexiones().putDestination(usuario.getIdUsuario(), miDestination);
	        
	        publisher.send(message);
	        
	        tcon.start();
	        
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void escucharCola(Destination dest) throws JMSException, NamingException {

		Session sesion = getSesionCola();

		this.queueSubscriber = sesion.createConsumer(dest);
		
		this.queueSubscriber.setMessageListener(this);
		
		getConnection().start();
		
	}
	
	/* (non-Javadoc)
	 * @see edu.tsi2.gr5.crazyfinger.chat.PanelContactosChatRemote#cargarContactos()
	 */
	public void cargarContactos() {
		//log("cargarContactos " + (darUsuario()==null));
		contactos = obtenerContactosDe(darUsuario());
	}
	
	private boolean aceptada(Solicitud sol) {
		return (sol.getEstado() == Solicitud.ESTADO_ACEPTADO);
	}

	public List<Usuario> getContactos() {
		return darContactos();
	}

	public void setContactos(List<Usuario> contactos) {
		this.contactos = contactos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public long obtenerIdUsuario() {
		return darUsuario().getIdUsuario();
	}
	
	
	public int getCantidadDeContactos() {
		if (this.darContactos() == null) {
			cantidadDeContactos = 0;
		}
		else {
			cantidadDeContactos = this.darContactos().size();
		}
		return cantidadDeContactos;
	}
	
	
	private InitialContext getJndi() throws NamingException {
		if (jndi == null) {
			jndi = new InitialContext();
		}
		return jndi;
	}
	
	public void onMessage(Message om) {
		// puede ser mensaje de un usuario o del chatManager avisando un nuevo amigo conectado
		log(" onMessage");
		try {
			if (esMensajeTexto(om) ) {
				// Procesar Mensaje de texto
				procesarMensajeTexto(om);
			}
			else if (esMensajeRegistro(om)) {
				log("lleg{o mensaje registro");
				// Procesar registro
				procesarRegistro(om);
			}
			else if (esMensajeDesRegistro(om)) {
				procesarDesRegistro(om);
			}
			else {
				log("ERROR !!!! LLEGO MENSAJE EXTRAÑO");
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void procesarRegistro(Message msg) throws JMSException {
		Long idUsuarioRegistro = (Long) msg.getObjectProperty(PROPERTY_ID_USUARIO);
		Destination cola = msg.getJMSReplyTo();
		log ("procesarMensajeRegistro idUsuarioRegistro" + idUsuarioRegistro + " cola=null " + (cola==null));
		
		this.getRegistroConexiones().putDestination(idUsuarioRegistro, cola);
	}
	
	public void procesarDesRegistro(Message msg) throws JMSException {
		Long idUsuarioDesRegistro = (Long) msg.getObjectProperty(PROPERTY_ID_USUARIO);
		Destination cola = msg.getJMSReplyTo();
		log ("procesarDesRegistro idUsuarioRegistro" + idUsuarioDesRegistro + " cola=null " + (cola==null));
		
		this.getRegistroConexiones().removeInvitacion(idUsuarioDesRegistro);
		
	}
	
	
	public void procesarMensajeTexto(Message msg) throws JMSException {
		Long idUsuarioMensaje = (Long) msg.getObjectProperty(PROPERTY_ID_USUARIO);
		String texto = (String) msg.getObjectProperty(PROPERTY_TEXTO_MENSAJE);
		log("msgTexto idusuarioregistro " + idUsuarioMensaje);
		log("msgTexto texto " + texto);
//		MensajesPendientes.getInstance().addMensajePendiente(idUsuarioRegistro, texto);
		mensajesPendientes.addMensajePendiente(idUsuarioMensaje, texto);

	}
	
	public boolean esMensajeDesRegistro(Message msg) throws JMSException {
		Integer tipoMensaje = (Integer) msg.getObjectProperty(PROPERTY_TIPO_MENSAJE);
		log("esMensajeDesRegistro + " + tipoMensaje);
		if (tipoMensaje == TIPO_MENSAJE_SALIDA_DEL_CHAT) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean esMensajeRegistro(Message msg) throws JMSException {
		Integer tipoMensaje = (Integer) msg.getObjectProperty(PROPERTY_TIPO_MENSAJE);
		log("esMensajeRegistro + " + tipoMensaje);
		if (tipoMensaje == TIPO_MENSAJE_REGISTRO) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean esMensajeTexto(Message msg) throws JMSException {
		Integer tipoMensaje = (Integer) msg.getObjectProperty(PROPERTY_TIPO_MENSAJE);
		if (tipoMensaje == TIPO_MENSAJE_TEXTO) {
			return true;
		}
		else {
			return false;
		}
	}


	public boolean estaOnline(long idUsuario) {
		return getRegistroConexiones().estaOnline(idUsuario);
	}
	
	public boolean hayMensajeDe(long idUsuario) {
		return mensajesPendientes.hayMensajesPendientesDe(idUsuario);
	}
	

	public boolean hayInvitacionesPendientes() {
		return (getRegistroConexiones().cantidadDeInvitaciones()>0);
	}
	
	public boolean hayMensajesPendientes() {
		return (mensajesPendientes.cantidadDeMensajes()!=0);
	}
	
	
	public RegistroConexiones getRegistroConexiones() {
		if (registroInvitaciones == null) {
			//log(" new Registroinvitaciones() " + (usuario==null));
			registroInvitaciones = new RegistroConexiones();
		}
		return registroInvitaciones;
	}
	
	public String tituloChat() {
		if (mensajesPendientes.cantidadDeMensajes()>0) {
			return "CHAT (" + mensajesPendientes.cantidadDeMensajes() + ")";
		}
		else {
			return "Chat";
		}
	}
	
	public String styleTituloChat() {
//		MensajesPendientes mp = MensajesPendientes.getInstance();
		if (mensajesPendientes.cantidadDeMensajes()>0) {
			return "color:PaleTurquoise;font-size:large;font-style:italic;background-color:Navy;background-repeat:repeat-x;";
		}
		else {
			return "font-size:small;";
		}
	}
	
	public String nombreParaUsuario(Usuario cto) {
		String nombreParaUsuario = cto.getNombre();
		if (this.getRegistroConexiones().estaOnline(cto.getIdUsuario())) {
			nombreParaUsuario += " - ONLINE";
		}
		else {
			nombreParaUsuario += " (offline)";
		}
		if (this.hayMensajeDe(cto.getIdUsuario())) {
			nombreParaUsuario += "(" + mensajesPendientes.mensajesDe(cto.getIdUsuario()) + ")";
		}
		return nombreParaUsuario;
	}
	
	public String styleParaUsuario(Usuario cto) {
		String styleUsu = "";
		if (this.getRegistroConexiones().estaOnline(cto.getIdUsuario())) {
			styleUsu += "color:PaleTurquoise;font-size:large;font-style:italic;background-color:Navy;background-repeat:repeat-x;";
		}
		if (this.hayMensajeDe(cto.getIdUsuario())) {
			styleUsu += "color:Tomato;font-size:large;font-style:italic;background-color:Navy;background-repeat:repeat-x;";
		}
		return styleUsu;
	}
	
	private List<Usuario> darContactos() {
		if (contactos == null) {
			contactos = new ArrayList<Usuario>();
		}
		usuario = darUsuario();
		if (usuario!=null) {
			if (contactos.size()==0) {
				int sols = usuario.getSolicitudsForSolicitado().size()+usuario.getSolicitudsForSolicitante().size();
				if (sols!=0) {
					cargarContactos();
				}
			}
		}
		return contactos;
	}
	
	
	private Usuario darUsuario() {
		if (usuario == null) {
			//log(" {darUsuario} obteniendo usuario ");
			usuario = (Usuario) Component.getInstance("usuario");
			//log(" {darUsuario} EXITO obteniendo usuario (usuario==null) ->" + (usuario==null));

		}
		return usuario;
	}
	
	
	
	private ConnectionFactory getConnectionFactory() throws NamingException {
		if (connectionFactory == null) {
			//log("{Conversacion.getConnectionFactory}Obtener ConectionFactory");
			this.connectionFactory = (ConnectionFactory)getJndi().lookup("ConnectionFactory");
			//log("{Conversacion.getConnectionFactory} Exito al obtener conectionFactory");
		}
		return this.connectionFactory;
	}

	private Connection getConnection() throws JMSException, NamingException {
		if (connection == null) {
			//log("{Conversacion.getConnection}Obtener Conection ");
			connection = getConnectionFactory().createConnection();
			//log("{Conversacion.getConnection} exito al Obtener Conection ");
		}
		
		return connection;
	}
	
	private Session getSesionCola() throws JMSException, NamingException {
		if (sesionCola == null) {
			//log("{.getSession}Obtener session");
			sesionCola = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			//log("{.getSession}exito al Obtener session");
		}
		return sesionCola;
	}
	
	public void cerrarChat() {
		log("Cerrar chat!!!!!");
		try {
			if(!usuario.getNombre().equalsIgnoreCase("admin")){
				// mandar mensaje de desconexion.
				if (publisher!=null) {
			        ObjectMessage message = pubSession.createObjectMessage();
			        message.setObjectProperty(PROPERTY_TIPO_MENSAJE, TIPO_MENSAJE_SALIDA_DEL_CHAT);
			        message.setObjectProperty(PROPERTY_ID_USUARIO, usuario.getIdUsuario());
			        publisher.send(message);
				}
				
				// Cerrar conexiones.
				if (publisher!=null)
					publisher.close();
	
				if (subscriber!=null)
					subscriber.close();
	
				
				if (pubSession!=null)
					pubSession.close();
				
				if (subSession!=null)
					subSession.close();
				
				if (tcon!=null)
					tcon.close();
				
				if (queueSubscriber!=null)
					queueSubscriber.close();
				
				getSesionCola().close();
				getConnection().close();
				
				sesionCola = null;
				connection = null;
			}
			else//and usuario.nombre != 'admin'
				log("NO Cerrar chat!!!!! porque es admin");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Usuario> obtenerContactosDe(Usuario usuario) {
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
		
	
	private void log(String s) {
		String logear = "[CHATBEAN] " + s;
		if (usuario!=null)
			logear = "[CHATBEAN] {" + usuario.getNombre() + "} "+ s;
		System.out.println(logear);
	}

	public MensajesPendientes getMensajesPendientes() {
		return mensajesPendientes;
	}
	
	
	
}
