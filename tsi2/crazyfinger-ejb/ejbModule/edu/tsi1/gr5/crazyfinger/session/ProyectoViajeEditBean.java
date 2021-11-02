package edu.tsi1.gr5.crazyfinger.session;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.FlushModeType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.core.Events;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import edu.tsi1.gr5.crazyfinger.entity.Actividad;
import edu.tsi1.gr5.crazyfinger.entity.InvitacionProyecto;
import edu.tsi1.gr5.crazyfinger.entity.Lugar;
import edu.tsi1.gr5.crazyfinger.entity.Nodo;
import edu.tsi1.gr5.crazyfinger.entity.Nodopt;
import edu.tsi1.gr5.crazyfinger.entity.Nodopv;
import edu.tsi1.gr5.crazyfinger.entity.Proyectoviaje;
import edu.tsi1.gr5.crazyfinger.entity.Solicitud;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;
import edu.tsi1.gr5.crazyfinger.pronosticos.TablaPronosticos;
import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.PronosticoDia;

import static edu.tsi1.gr5.crazyfinger.pronosticos.PronosticoHelper.*;
import static edu.tsi1.gr5.crazyfinger.helper.ProyectoViajeHelper.*;
@Name("pvEdit")
@Scope(ScopeType.CONVERSATION)
public class ProyectoViajeEditBean {

	@Logger private Log log;

    @In StatusMessages statusMessages;
    
    @In(create=true)
    ProyectoviajeHome proyectoviajeHome;
    
    @In(create=true)
    NodoHome nodoHome;
    
    @In(create=true)
    UsuarioHome usuarioHome;
    
    @In(create=true)
    InvitacionProyectoHome invitacionProyectoHome;
    
    @In
    Usuario usuario;
    
    @RequestParameter
    Long proyectoviajeIdProyectoviaje;
    
    @In(required=false)
    @Out(required=false,scope=ScopeType.CONVERSATION)
    Long idProyectoviajeEdicion;
    
    @In(create=true)
    NodopvList nodopvList;
    
    List<Nodopv> nodosOrdenados;
    
    long idProyecto = -1;
    
    Proyectoviaje proyecto;
    
    List<Solicitud> invitaciones;
    List<Solicitud> amigos;
    
    // Para enviar a cola pedido de pronosticos.
	private QueueConnection connection = null;
	private QueueSession session = null;
	private Destination colaWs = null;
	private MessageProducer producer = null;
    
	
    @Create
    public void init(){
    	if(idProyectoviajeEdicion != null){
    		idProyecto = idProyectoviajeEdicion.longValue();
    	}
    	if(proyectoviajeIdProyectoviaje != null){
    		idProyecto = proyectoviajeIdProyectoviaje.longValue();
    		idProyectoviajeEdicion = proyectoviajeIdProyectoviaje;
    	}
    	if(idProyecto > 0){
	    	proyectoviajeHome.setProyectoviajeIdProyectoviaje(idProyecto);
	    	proyectoviajeHome.load();
	    	proyecto = proyectoviajeHome.getInstance();
	    	actualizarPronosticos(proyecto);
    	}
    	usuarioHome.setUsuarioIdUsuario(usuario.getIdUsuario());
    	usuarioHome.load();
    	invitaciones = new ArrayList<Solicitud>();
    	amigos = new ArrayList<Solicitud>(usuarioHome.amigos());
    }

	public Proyectoviaje getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyectoviaje proyecto) {
		this.proyecto = proyecto;
	}
    
	public List<Nodopv> getNodosOrdenados() {
    	if(nodosOrdenados == null)
    		nodosOrdenados = new ArrayList<Nodopv>(nodopvList.nodosOrdenados(idProyecto));
		return nodosOrdenados;
	}

	public void setNodosOrdenados(List<Nodopv> nodosOrdenados) {
		this.nodosOrdenados = nodosOrdenados;
	}
    
    public void quitarNodo(long idNodo){
    	log.info("entramos a quitar nodo: {0}", idNodo);
    	//nodoHome.getEntityManager().setFlushMode(FlushModeType.COMMIT);
    	nodoHome.setNodoIdNodo(idNodo);
    	nodoHome.load();
    	nodoHome.remove();
    	//nodoHome.getEntityManager().flush();	
    	log.info("nodo: {0} quitado con exito", idNodo);
    	Events.instance().raiseEvent("proyectoViaje.ReloadProyecto");
    }
    
    @Observer("proyectoViaje.ReloadProyecto")
    public void reloadProyecto(){
    	proyectoviajeHome.load();
    	proyecto = proyectoviajeHome.getInstance();
    }
    
    public String verItinerario(){
    	if (proyecto!=null)
    		this.actualizarPronosticos(proyecto);
    	long id = proyectoviajeIdProyectoviaje != null? proyectoviajeIdProyectoviaje.longValue():idProyectoviajeEdicion.longValue();
    	return "/itinerario.xhtml?proyectoviajeIdProyectoviaje="+id;
    }
    
    @Begin(join=true)
    public String agregarInvitaciones(){
    	long id = proyectoviajeIdProyectoviaje != null? proyectoviajeIdProyectoviaje.longValue():idProyectoviajeEdicion.longValue();
    	return "/invitacionProyecto.xhtml?proyectoviajeIdProyectoviaje="+id;
    }
    
    @End
    public String guardarInvitaciones(){
    	try{
	    	for(Solicitud s : invitaciones){
	    		InvitacionProyecto i = new InvitacionProyecto();
	    		i.setEstado(i.ESTADO_PENDIENTE);
	    		i.setFecha(new Date());
	    		i.setProyecto(proyecto);
	    		i.setUsuarioOwner(usuario);
	    		i.setUsuarioInvitado(s.getUsuarioBySolicitado());
	    		proyecto.getInvitaciones().add(i);
	    		invitacionProyectoHome.setInstance(i);
	    		invitacionProyectoHome.persist();
	    	}
    	
	    	return "/ProyectoviajeEdit.xhtml?idProyecto="+proyecto.getIdProyectoviaje();
    	}
    	catch(Exception e){
    		log.error("error salvando invitaciones",e);
    	}
    	return "";
    	
    }
    
    public void accionDropInvitacion(org.richfaces.event.DropEvent e){
    	invitaciones.add((Solicitud)e.getDragValue());
    	amigos.remove(e.getDragValue());
    	System.out.println(e.getDragValue());
    	
    }

	public List<Solicitud> getInvitaciones() {
		return invitaciones;
	}

	public void setInvitaciones(List<Solicitud> invitaciones) {
		this.invitaciones = invitaciones;
	}

	public List<Solicitud> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Solicitud> amigos) {
		this.amigos = amigos;
	}
	
	public Lugar getCiudadCentrar() {
		// No se como pero va a retornar la ciudad para centrar el mapa
		// por ahora la primer ciudad de la lista
		if (this.proyecto.getNodopvs().size()>0) {
//			System.out.println("itinerario getCiudadCentrar " + ciudad.size());
//			System.out.println("lat " + ciudad.get(0).getLatitud());
//			System.out.println("lon " + ciudad.get(0).getLongitud());
			for (Nodopv n : this.proyecto.getNodopvs() ) {
				return n.getLugar();
			}
			return null;
		}
		else {
			System.out.println("itinerario getCiudadCentrar null !!");
			
			return null;
		}
	}
	
	public double getLatitudCentrar() {
		Lugar l = getCiudadCentrar();
		if (l==null) {
			return 0;
		}
		else {
			return l.getLatitud();
		}
	}
	
	public double getLongitudCentrar() {
		Lugar l = getCiudadCentrar();
		if (l==null) {
			return 0;
		}
		else {
			return l.getLongitud();
		}		
	}	
	
	public List<Lugar> getListaOrdenadaDeLugares() {
		HashMap<Long, Lugar> tabla = new HashMap<Long, Lugar>();
		long mayorOrden=0;
 		for (Nodopv n : this.proyecto.getNodopvs() ) {
			Lugar l = n.getLugar();
			long orden = n.getOrden();
			tabla.put(orden,l);
			if (orden>mayorOrden) {
				mayorOrden = orden;
			}
		}
 		ArrayList<Lugar> res = new ArrayList<Lugar>();
 		for (long i=1;i<=mayorOrden;i++) {
 			Lugar l = tabla.get(i);
 			if (l!=null) {
 				res.add(l);
 			}
 		}
 		return res;
	}
	
	public String infoNodo(Nodo nodo) {
		String enter = "<br>";
		String info = "<b>Lugar : </b>" + nodo.getLugar().getNombre() + enter;
		info += "<b>Salida : </b>" + nodo.getPasajeSalida().getFecha() + enter;
		info += "<b>Llegada : </b>" + nodo.getPasajeLlegada().getFecha() + enter;
		info += "<b>Alojamiento : </b>" + nodo.getEstadia().getAlojamiento().getNombre()  +", <b>costo : </b>" + 
			nodo.getEstadia().getAlojamiento().getCosto() + enter;
		info +=enter;
		String stringPronosticos = getStringPronosticos(nodo);
		log("String Pronosticos " + stringPronosticos);
//		info += stringPronosticos;
		return info;
	}
	
	public String getStringPronosticos(Nodo nodo) {
		GregorianCalendar desde = new GregorianCalendar();
		desde.setTime(nodo.getEstadia().getFechaDesde());
		GregorianCalendar hasta = new GregorianCalendar();
		hasta.setTime(nodo.getEstadia().getFechaHasta());
		GregorianCalendar hoy = new GregorianCalendar();
		long millisHoy = System.currentTimeMillis();
		hoy.setTimeInMillis(millisHoy);
		GregorianCalendar proxSemana = new GregorianCalendar();
		proxSemana.setTime(hoy.getTime());
		proxSemana.set(GregorianCalendar.DAY_OF_MONTH,proxSemana.get(GregorianCalendar.DAY_OF_MONTH)+7);
		
		dejarSoloDia(desde);
		dejarSoloDia(hasta);
		dejarSoloDia(hoy);
		dejarSoloDia(proxSemana);
		
		if (desde.before(hoy)) {
			desde = hoy;
		}
		if (hasta.before(hoy)) {
			return htmlPronosticosNoDisponibles();
		}
		if (hasta.after(proxSemana)) {
			hasta = proxSemana;
		}
		TablaPronosticos tabla = TablaPronosticos.getInstance();
		String res = "";
		log("getStringPronosticos desde=" + date2string(desde) + " hasta=" + date2string(hasta));
		List<PronosticoDia> pronosticos = new ArrayList<PronosticoDia>();
		while (desde.before(hasta) || (mismoDia(desde, hasta)) ) {
			log("getStringPronosticos desde=" + date2string(desde) + " hasta=" + date2string(hasta));
			
			String d2s = date2string(desde);	
			log("pregunto tienePronostico " + nodo.getLugar().getLatitud() + "," + nodo.getLugar().getLongitud() + "," + d2s);
			if (tabla.tienePronostico(nodo.getLugar().getLatitud(),nodo.getLugar().getLongitud(),d2s)) {
				PronosticoDia pronostico = tabla.getPronostico(nodo.getLugar().getLatitud(),nodo.getLugar().getLongitud(),d2s);
				pronosticos.add(pronostico);
			}
			desde.set(GregorianCalendar.DAY_OF_MONTH,desde.get(GregorianCalendar.DAY_OF_MONTH)+1);
		}
		
		if (pronosticos.size()==0) {
			return htmlPronosticosNoDisponibles();
		}
		
		res = toTablaPronosticos(pronosticos);
		
		log("res "+res);

		return res;
	}
	
	
	public void actualizarPronosticoTiempo(Nodopv nodo) {
		// TODO : por ahora actualizo todos los pronosticos con la fecha de hoy.
		String fechadehoy = date2string(new GregorianCalendar());
		double latitud = nodo.getLugar().getLatitud();
		double longitud = nodo.getLugar().getLongitud();
		
		try {
			log("voy a mandar mensaje fechadehoy="+fechadehoy + " latitud=" + latitud + " longitud=" + longitud);
			
			QueueSession session = getQueueSession();

			log("antes de obtener la cola . ");
			Destination dest = getColaWs();
			log("despues de obtener cola (dest==null) " + (dest==null));
			
//			MessageProducer producer = session.createProducer(dest);
			MessageProducer producer = getMessageProducer();
			
			// Crear el mensaje
			log("creando mensaje ...");
			ObjectMessage message = session.createObjectMessage();
			log("creando mensaje OK");
			message.setObjectProperty(MDB_PROPERTY_FECHA,fechadehoy);
			message.setObjectProperty(MDB_PROPERTY_LATITUD, new Double(latitud));
			message.setObjectProperty(MDB_PROPERTY_LONGITUD, new Double(longitud));
			
			log (" antess producer.send(message);");
			producer.send(message);
			log (" DESPUES !! producer.send(message);");
//			getQueueConnection().start();
			log (" //	sin sessioncontext!!!;");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private QueueConnection getQueueConnection() throws NamingException, JMSException {
		if (this.connection == null) {
			InitialContext jndi = new InitialContext();
			QueueConnectionFactory factory = (QueueConnectionFactory) jndi.lookup("ConnectionFactory");
			connection = factory.createQueueConnection();
			connection.start();
		}
		return this.connection;
	}

	private QueueSession getQueueSession() throws JMSException, NamingException {
		if (this.session == null) {
			connection = getQueueConnection();
			this.session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		}
		return this.session;
	}
	
	private Destination getColaWs() throws NamingException {
		if (colaWs == null) {
			InitialContext jndi = new InitialContext();
			colaWs = (Destination) jndi.lookup("queue/wsQueue");
		}
		return colaWs;
	}
	
	private MessageProducer getMessageProducer() throws JMSException, NamingException {
		if (producer == null) {
			producer = getQueueSession().createProducer(getColaWs());
		}
		return producer;
	}
	
	public void actualizarPronosticos(Proyectoviaje proy) {
		if (proy!=null) {
			for (Nodopv nodo : proy.getNodopvs()) {
				actualizarPronosticoTiempo(nodo);
			}
		}
	}
	
	
	
	


	public void log(String s) {
		System.out.println("[ProyectoViajeEditBean] " + s);
	}
	
	
}
