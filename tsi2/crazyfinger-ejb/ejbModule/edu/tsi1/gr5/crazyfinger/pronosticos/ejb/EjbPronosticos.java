package edu.tsi1.gr5.crazyfinger.pronosticos.ejb;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import edu.tsi1.gr5.crazyfinger.pronosticos.PronosticoError;
import edu.tsi1.gr5.crazyfinger.pronosticos.TablaPronosticos;
import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.PronosticoDia;


//@Stateless
//@Name("ejbPronosticos")
public class EjbPronosticos implements PronosticosLocal{

	public void actualizarPronosticos(double latitud, double longitud) throws PronosticoError{
		// TODO Auto-generated method stub
		
	}

	public boolean existePronostico(String fecha, double latitud,
			double longitud)  throws PronosticoError{
		TablaPronosticos pronosticos = TablaPronosticos.getInstance();
		return pronosticos.tienePronostico(latitud,longitud,fecha);
	}

	public PronosticoDia obtenerPronostico(String fecha, double latitud,
			double longitud)  throws PronosticoError {
		TablaPronosticos pronosticos = TablaPronosticos.getInstance();
		return pronosticos.getPronostico(latitud,longitud,fecha);
	}


	
	@Remove
	public void eliminar() {
		
	}

	public void guardarPronostico(PronosticoDia pronostico) {
		TablaPronosticos.getInstance().putPronostico(pronostico);
	}
	
	private void log(String s) {
		System.out.println("[EjbPronosticos] " + s);
	}
}
