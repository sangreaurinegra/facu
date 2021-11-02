package edu.tsi1.gr5.crazyfinger.pronosticos;

import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.ClavePronostico;
import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.PronosticoDia;

public class TablaPronosticos {

	private Map<ClavePronostico, PronosticoDia> pronosticos 
						= new HashMap<ClavePronostico, PronosticoDia>();
	
	private static TablaPronosticos instance = null;
	
	private  TablaPronosticos() {
		
	}
	
	public static TablaPronosticos getInstance() {
		if (instance == null) {
			instance = new TablaPronosticos();
		}
		return instance;
	}
	
	public void putPronostico(PronosticoDia pronostico) {
		ClavePronostico clave = new ClavePronostico();
		clave.setFecha(pronostico.getFecha());
		clave.setLatitud(pronostico.getLatitud());
		clave.setLongitud(pronostico.getLongitud());
		pronosticos.put(clave,pronostico);
	}
	
	public PronosticoDia getPronostico(double latitud, double longitud, String fecha){
		ClavePronostico clave = new ClavePronostico();
		clave.setFecha(fecha);
		clave.setLatitud(latitud);
		clave.setLongitud(longitud);
		return getPronostico(clave);
	}

	public PronosticoDia getPronostico(ClavePronostico clave){
		return pronosticos.get(clave);
	}

	public boolean tienePronostico(double latitud, double longitud, String fecha){
		ClavePronostico clave = new ClavePronostico();
		clave.setFecha(fecha);
		clave.setLatitud(latitud);
		clave.setLongitud(longitud);
		return tienePronostico(clave);
	}
	
	public boolean tienePronostico(ClavePronostico clave){
//		printPronosticos();
		return pronosticos.containsKey(clave);
	}
	
//	public void printPronosticos() {
//		for (ClavePronostico key : this.pronosticos.keySet()) {
//			PronosticoDia pronostico = this.pronosticos.get(key);
//			System.out.println(" pronostico " + key.getFecha() + "," + key.getLatitud() + "," + key.getLongitud());
//		}
//		
//	}
	
	
}
