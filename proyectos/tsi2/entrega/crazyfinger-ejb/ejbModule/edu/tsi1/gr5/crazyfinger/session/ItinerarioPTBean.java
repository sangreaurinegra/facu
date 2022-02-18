package edu.tsi1.gr5.crazyfinger.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.hibernate.validator.Size;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;

import edu.tsi1.gr5.crazyfinger.entity.Actividad;
import edu.tsi1.gr5.crazyfinger.entity.Alojamiento;
import edu.tsi1.gr5.crazyfinger.entity.Estadia;
import edu.tsi1.gr5.crazyfinger.entity.Lugar;
import edu.tsi1.gr5.crazyfinger.entity.Nodo;
import edu.tsi1.gr5.crazyfinger.entity.Nodopt;
import edu.tsi1.gr5.crazyfinger.entity.Pasaje;
import edu.tsi1.gr5.crazyfinger.entity.Traslado;
import edu.tsi1.gr5.crazyfinger.helper.FechasHelper;

@Name("itinerariopt")
@Scope(ScopeType.CONVERSATION)
public class ItinerarioPTBean {
	   @Logger private Log log;

	    @In StatusMessages statusMessages;

	    @In(create=true)
	    LugarList lugarList;
	    
	    @In(create=true)
	    LugarHome lugarHome;
	    
	    @In(create=true)
	    TrasladoList trasladoList;
	    
	    @In(create=true)
	    TrasladoHome trasladoHome;
	    
	    @In(create=true)
	    PaqueteturisticoHome paqueteturisticoHome;
	    
	    @In(create=true)
	    NodoptHome nodoptHome;
	    
	    @In(create=true)
	    NodoptList nodoptList;
	    
	    @In(create=true)
	    EstadiaHome estadiaHome;
	    
	    @In(create=true)
	    PasajeHome pasajeHome;
	    
	    @In(required=false)
	    @Out(required=false,scope=ScopeType.CONVERSATION)
	    Long idPaqueteturisticoEdicion;
	    
	    @RequestParameter
	    Long paqueteturisticoIdPaqueteturistico;
	    
	    // listados para elegir
	    List<Lugar> ciudades;
	    List<Traslado> traslados;
	    List<Alojamiento> hoteles;
	    List<Actividad> actividadesDisponibles;
	    
	    //cosas elegidas
	    List<Lugar> ciudad;
	    List<Traslado> llegada;
	    List<Traslado> salida;
	    List<Alojamiento> estadia;
	    List<Actividad> actividades;
	    
	    Date fechaLlegada;
	    Date fechaSalida;
	    boolean cargarSalida = false;
	    Nodopt nodo = null;
	    long idProyecto = 1;
	    
	    @org.jboss.seam.annotations.Create
	    public void init(){
	    	System.out.println("init");
	    	llegada = new ArrayList<Traslado>();
	        salida = new ArrayList<Traslado>();
	        estadia = new ArrayList<Alojamiento>();
	        actividades = new ArrayList<Actividad>();
	    	ciudad = new ArrayList<Lugar>();
	    	ciudades = lugarList.getResultList();
	    	if(idPaqueteturisticoEdicion != null)
	    		idProyecto = idPaqueteturisticoEdicion.longValue();
	    	if(paqueteturisticoIdPaqueteturistico != null){
	    		idProyecto = paqueteturisticoIdPaqueteturistico.longValue();
	    		idPaqueteturisticoEdicion = paqueteturisticoIdPaqueteturistico;
	    	}
	    	fechaLlegada = new Date();
	        fechaSalida = new Date();
	    }
	    

		public List<Traslado> getTraslados() {
			//cargamos primero las llegadas, luego las salidas
			if(cargarSalida){
				lugarHome.setInstance(ciudad.get(0));
				traslados = new ArrayList<Traslado>(lugarHome.getTrasladosForOrigen());
				if(salida.size()>0)
					traslados.remove(salida.get(0));
			}
			else{
				lugarHome.setInstance(ciudad.get(0));
				traslados = new ArrayList<Traslado>(lugarHome.getTrasladosForDestino());
				if(llegada.size()>0)
					traslados.remove(llegada.get(0));
			}
			return traslados;
		}

		public void setTraslados(List<Traslado> traslados) {
			this.traslados = traslados;
		}

		public List<Alojamiento> getHoteles() {
			lugarHome.setInstance(ciudad.get(0));
			hoteles = new ArrayList<Alojamiento>(lugarHome.getAlojamientos());
			if(estadia.size()>0)
				hoteles.remove(estadia.get(0));
			return hoteles;
		}

		public void setHoteles(List<Alojamiento> hoteles) {
			this.hoteles = hoteles;
		}

		public List<Actividad> getActividadesDisponibles() {
			lugarHome.setInstance(ciudad.get(0));
			actividadesDisponibles = new ArrayList<Actividad>(lugarHome.getActividades());
			if(actividades.size()>0)
				for(int i = 0; i < actividades.size();i++){
					actividadesDisponibles.remove(actividades.get(i));
				}
			return actividadesDisponibles;
		}

		public void setActividadesDisponibles(List<Actividad> actividadesDisponibles) {
			this.actividadesDisponibles = actividadesDisponibles;
		}

		public List<Traslado> getLlegada() {
			return llegada;
		}

		public void setLlegada(List<Traslado> llegada) {
			this.llegada = llegada;
		}

		public List<Traslado> getSalida() {
			return salida;
		}

		public void setSalida(List<Traslado> salida) {
			this.salida = salida;
		}

		public List<Alojamiento> getEstadia() {
			return estadia;
		}

		public void setEstadia(List<Alojamiento> estadia) {
			this.estadia = estadia;
		}

		public List<Actividad> getActividades() {
			return actividades;
		}

		public void setActividades(List<Actividad> actividades) {
			this.actividades = actividades;
		}

		public Date getFechaLlegada() {
			return fechaLlegada;
		}

		public void setFechaLlegada(Date fechaLlegada) {
			this.fechaLlegada = fechaLlegada;
		}

		public Date getFechaSalida() {
			return fechaSalida;
		}

		public void setFechaSalida(Date fechaSalida) {
			this.fechaSalida = fechaSalida;
		}
	    
	    public List<Lugar> getCiudades() {
			return ciudades;
		}

		public void setCiudades(List<Lugar> ciudades) {
			this.ciudades = ciudades;
		}

		public List<Lugar> getCiudad() {
			System.out.println("ciudad");
			return ciudad;
		}

		public void setCiudad(List<Lugar> ciudad) {
			this.ciudad = ciudad;
		}

		public String itinerario()
	    {
	        // implement your business logic here
	        log.info("Itinerario.itinerario() action called");
	        //statusMessages.add("itinerario");
	        return "/elegirCiudadpt.xhtml";
	    }

	    // add additional action methods

		public void accionDropCiudad(org.richfaces.event.DropEvent e){
			if(ciudad.size() == 0){
		    	ciudad.add((Lugar)e.getDragValue());
		    	ciudades.remove(e.getDragValue());
		    	System.out.println(e.getDragValue());  
			}
	    }
		
		public void accionDropLlegada(org.richfaces.event.DropEvent e){
			if(llegada.size() == 0){
		    	llegada.add((Traslado)e.getDragValue());
		    	traslados.remove(e.getDragValue());
		    	System.out.println(e.getDragValue());
			}
	    }
		
		public void accionDropEstadia(org.richfaces.event.DropEvent e){
			if(estadia.size() == 0){
		    	estadia.add((Alojamiento)e.getDragValue());
		    	hoteles.remove(e.getDragValue());
		    	System.out.println(e.getDragValue());
			}
	    }
		
		public void accionDropActividad(org.richfaces.event.DropEvent e){
	    	actividades.add((Actividad)e.getDragValue());
	    	actividadesDisponibles.remove(e.getDragValue());
	    	System.out.println(e.getDragValue());
	    	
	    }
		
		public void accionDropSalida(org.richfaces.event.DropEvent e){
	    	if(salida.size() == 0){
	    		salida.add((Traslado)e.getDragValue());
	        	traslados.remove(e.getDragValue());
	        	System.out.println(e.getDragValue());
	        	
	    	}
			
	    }
		
		public void cambioFechaLlegada(javax.faces.event.ValueChangeEvent evento){
			fechaLlegada = (Date)evento.getNewValue();
		}
		
		public void cambioFechaSalida(javax.faces.event.ValueChangeEvent evento){
			fechaSalida = (Date)evento.getNewValue();
		}
		
		@Observer("paqueteTuristico.CargarSalida")
		public void cargarSalida(){
			cargarSalida = true;
		}
		
		public void salvarTramo(){
			try{
				paqueteturisticoHome.setPaqueteturisticoIdPaqueteturistico(idProyecto);
				paqueteturisticoHome.load();
				paqueteturisticoHome.persist();
				
				Nodopt aux =  nodoptHome.createInstance();
				
				aux.setLugar(ciudad.get(0));
				aux.setTipoNodo(Nodo.TIPO_NODO_PT);
				aux.setOrden(nodoptList.getMaxOrden(idProyecto)+1);
				aux.setPaqueteturistico(paqueteturisticoHome.getDefinedInstance());
				
				Estadia e = estadiaHome.createInstance();
				e.setAlojamiento(estadia.get(0));
				e.setFechaDesde(fechaLlegada);
				e.setFechaHasta(fechaSalida);
				e.calcularCosto();
				estadiaHome.setInstance(e);
				estadiaHome.persist();
				//e.setNodo(nodo);
				aux.setEstadia(e);
				
				if(actividades.size() > 0){
					Set<Actividad> acts = new TreeSet<Actividad>(actividades);
					aux.setNodos(acts);
				}
				
				if(salida.size() ==1){
					Pasaje pSalida = pasajeHome.createInstance();
					pSalida.setFecha(FechasHelper.getInstance().fechaConFormato(fechaSalida,"dd/MM/yyyy"));
					pSalida.setTraslado(salida.get(0));
					pasajeHome.setInstance(pSalida);
					pasajeHome.persist();
					aux.setPasajeSalida(pSalida);
				}
				if(nodo == null){
					Pasaje pLlegada = pasajeHome.createInstance();
					pLlegada.setFecha(FechasHelper.getInstance().fechaConFormato(fechaLlegada,"dd/MM/yyyy"));
					pLlegada.setTraslado(llegada.get(0));
					pasajeHome.setInstance(pLlegada);
					pasajeHome.persist();
					aux.setPasajeLlegada(pLlegada);
				}
				else{
					aux.setPasajeLlegada(nodo.getPasajeLlegada());
				}
				
				nodoptHome.setInstance(aux);
				nodoptHome.persist();
				nodo = aux;
				statusMessages.clear();
				statusMessages.add("El tramo ha sido agregado correctamente");
			
			}
			catch(Exception e){
				log.error("error salvando", e);
			}
		}
		
		public void salvarTramoYSeguir(){
			salvarTramo();
			if(nodo != null){
				// preparamos las vaariables para la siguiente iteracion
				//ciudad
				ciudad.remove(0);
				ciudad.add(salida.get(0).getLugarByDestino());
				//traslado llegada
				llegada.remove(0);
				llegada.add(salida.get(0));
				//salida
				salida.remove(0);
				//actividades
				actividades.remove(0);
				//estadia
				estadia.remove(0);
				//cargar salida
				cargarSalida = false;
			}
		}
		

		public Lugar getCiudadCentrar() {
			// No se como pero va a retornar la ciudad para centrar el mapa
			// por ahora la primer ciudad de la lista
			if (ciudad.size()>0) {
				System.out.println("itinerario getCiudadCentrar " + ciudad.size());
				System.out.println("lat " + ciudad.get(0).getLatitud());
				System.out.println("lon " + ciudad.get(0).getLongitud());
				return ciudad.get(0);
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
		

		
}
