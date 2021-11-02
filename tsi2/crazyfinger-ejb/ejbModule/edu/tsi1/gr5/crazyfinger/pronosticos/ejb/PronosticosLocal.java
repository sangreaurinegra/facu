package edu.tsi1.gr5.crazyfinger.pronosticos.ejb;

import javax.ejb.Local;

import edu.tsi1.gr5.crazyfinger.pronosticos.PronosticoError;
import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.PronosticoDia;

@Local
public interface PronosticosLocal {
	
	public void actualizarPronosticos(double latitud, double longitud) throws PronosticoError;
	
	public PronosticoDia obtenerPronostico(String fecha, double latitud,double longitud) throws PronosticoError;
	
	public boolean existePronostico(String fecha, double latitud, double longitud) throws PronosticoError;
	
	public void guardarPronostico(PronosticoDia pronostico) throws PronosticoError;

}
