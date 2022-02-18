package edu.tsi1.gr5.crazyfinger.webservices.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import edu.tsi1.gr5.crazyfinger.entity.Actividad;
import edu.tsi1.gr5.crazyfinger.entity.Alojamiento;
import edu.tsi1.gr5.crazyfinger.entity.Estadia;
import edu.tsi1.gr5.crazyfinger.entity.Lugar;
import edu.tsi1.gr5.crazyfinger.entity.Nodopv;
import edu.tsi1.gr5.crazyfinger.entity.Pasaje;
import edu.tsi1.gr5.crazyfinger.entity.Proyectoviaje;
import edu.tsi1.gr5.crazyfinger.entity.Traslado;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;
import edu.tsi1.gr5.crazyfinger.helper.FechasHelper;
import edu.tsi1.gr5.crazyfinger.session.AlojamientoHome;
import edu.tsi1.gr5.crazyfinger.session.AlojamientoList;
import edu.tsi1.gr5.crazyfinger.session.EstadiaHome;
import edu.tsi1.gr5.crazyfinger.session.EstadiaList;
import edu.tsi1.gr5.crazyfinger.session.LugarList;
import edu.tsi1.gr5.crazyfinger.session.NodopvHome;
import edu.tsi1.gr5.crazyfinger.session.PasajeHome;
import edu.tsi1.gr5.crazyfinger.session.ProyectoviajeHome;
import edu.tsi1.gr5.crazyfinger.session.TrasladoList;
import edu.tsi1.gr5.crazyfinger.session.UsuarioList;
import edu.tsi1.gr5.crazyfinger.webservices.datatypes.DatoNodo;
import edu.tsi1.gr5.crazyfinger.webservices.datatypes.DatoPasaje;
import edu.tsi1.gr5.crazyfinger.webservices.datatypes.DatoProyectoViaje;
import edu.tsi1.gr5.crazyfinger.webservices.datatypes.RespuestaAltaProyecto;

@Stateless
@Name("ejbAltaProyectoViaje")
@WebService(name = "AltaProyectoViaje", serviceName = "AltaProyectoViaje")
public class EjbAltaProyectoViaje implements RemoteProyectoViaje{

	// TODO : crear estadias.
	
	@In(create=true)
	private ProyectoviajeHome proyectoviajeHome;
	
	@In(create=true)
	private UsuarioList usuarioList;
	
	@In(create=true)
	private NodopvHome nodopvHome;
	
	@In(create=true)
	private EstadiaList estadiaList;

	@In(create=true)	
	private LugarList lugarList;
		
	@In(create=true)
	private TrasladoList trasladoList;
	
	@In(create=true)
	private PasajeHome pasajeHome;
	
	@In(create=true)
	private AlojamientoList alojamientoList;
	
	@In(create=true)
	private EstadiaHome estadiaHome;
	
	@WebMethod(operationName="altaProyecto")
	public RespuestaAltaProyecto altaProyecto(DatoProyectoViaje dato)
			throws RemoteException {
		System.out.println(" altaProyecto!! ");

		log(" buscando usuario  " + dato.getOpenid());
		Usuario usuario = usuarioList.getUsuarioPorOpenId(dato.getOpenid());
		if (usuario==null) {
			return RespuestaAltaProyecto.crearRespuestaMala("Usuario no existe : " + dato.getOpenid());
		}
		
		log("pvHome.clearInstance();");
		Proyectoviaje pv = new Proyectoviaje();
		
		pv.setDescripcion(dato.getDescripcion());
		pv.setEstado(dato.getEstado());
		pv.setUsuario(usuario);

		pv.setNodopvs(new HashSet<Nodopv>());
		
		int orden = 1;
		HashSet<Nodopv> nodos = new HashSet<Nodopv>();
		for (DatoNodo dn : dato.getNodos()) {
			Nodopv nodo = new Nodopv();
			
//			Estadia estadia = estadiaList.getEstadiaPorId(dn.getEstadia());
//			if (estadia == null) {
//				return RespuestaAltaProyecto.crearRespuestaMala("Estadia no existe : " + dn.getEstadia());
//			}
//			nodo.setEstadia(estadia);
			
			
			// Obtener alojamiento
			Alojamiento alojamiento = alojamientoList.getAlojamientoPorId(dn.getAlojamiento());
			if (alojamiento == null) {
				return RespuestaAltaProyecto.crearRespuestaMala("alojamiento no existe : " + dn.getAlojamiento());
			}
			
			Estadia estadia = new Estadia();
			estadia.setAlojamiento(alojamiento);
//			estadia.setCostototal(alojamiento.getCosto());
			estadia.setFechaDesde(FechasHelper.getInstance().fechaHoraString2date(dn.getFechaDesde()));
			estadia.setFechaHasta(FechasHelper.getInstance().fechaHoraString2date(dn.getFechaHasta()));
			estadia.calcularCosto();
			nodo.setEstadia(estadia);
			
			nodo.setEstado(dn.getEstado());
	
			long idLugar = dn.getLugar();
			Lugar lugar = lugarList.getLugarPorId(idLugar);
			if (lugar == null) {
				return RespuestaAltaProyecto.crearRespuestaMala("lugar no existe : " + idLugar);
			}
			nodo.setLugar(lugar);
			
			nodo.setOrden(orden);
			orden++;
			
			DatoPasaje pl = dn.getPasajeLlegada();
			Pasaje pllegada = new Pasaje();
			pllegada.setFecha(pl.getFecha());
			Traslado traslado = trasladoList.getTrasladoPorId(pl.getTraslado());
			if (traslado == null) {
				return RespuestaAltaProyecto.crearRespuestaMala("traslado no existe : " + pl.getTraslado());
			}
			pllegada.setTraslado(traslado);
			nodo.setPasajeLlegada(pllegada);
			
			pl = dn.getPasajeSalida();
			Pasaje psalida = new Pasaje();
			psalida.setFecha(pl.getFecha());
			traslado = trasladoList.getTrasladoPorId(pl.getTraslado());
			if (traslado == null) {
				return RespuestaAltaProyecto.crearRespuestaMala("traslado no existe : " + pl.getTraslado());
			}
			psalida.setTraslado(traslado);
			nodo.setPasajeSalida(psalida);
			
			nodo.setNodos(new HashSet<Actividad>());
			nodo.setTipoNodo(dn.getTipoNodo());
			
//			pv.getNodopvs().add(nodo);
			nodos.add(nodo);
		}
		
		log("pvHome.setId(pv); " + pv.getNodopvs().size());
		proyectoviajeHome.setInstance(pv);
		log ("guardando pvhome");
		proyectoviajeHome.persist();

		
		for (Nodopv nodo : nodos) {
			// Guardar estadia.
			estadiaHome.setInstance(nodo.getEstadia());
			estadiaHome.persist();
			
			// Guardar pasajes
			pasajeHome.setInstance(nodo.getPasajeLlegada());
			pasajeHome.persist();
			pasajeHome.setInstance(nodo.getPasajeSalida());
			pasajeHome.persist();
			
			// guardar nodos
			nodo.setProyectoviaje(pv);
			nodopvHome.setInstance(nodo);
			nodopvHome.persist();
		}

		log(" pvhome ... ok");
		return RespuestaAltaProyecto.crearRespuestaOk();
	}

	private void log(String s) {
		System.out.println("[EjbAltaProyectoViaje] " +s);
	}
	
}
