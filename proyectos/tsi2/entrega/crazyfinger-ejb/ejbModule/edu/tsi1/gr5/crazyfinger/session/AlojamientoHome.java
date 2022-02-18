package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("alojamientoHome")
public class AlojamientoHome extends EntityHome<Alojamiento> {

	private long promedio = -1;
	
	public void setAlojamientoIdServicio(Long id) {
		setId(id);
	}

	public Long getAlojamientoIdServicio() {
		return (Long) getId();
	}

	@Override
	protected Alojamiento createInstance() {
		Alojamiento alojamiento = new Alojamiento();
		return alojamiento;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Alojamiento getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Estadia> getEstadias() {
		return getInstance() == null ? null : new ArrayList<Estadia>(
				getInstance().getEstadias());
	}

	public String puntajeEstrellasPromedio(long idServicio){
		if(idServicio < 0)// si es menor que cero asumo que tengo que usar el de la instancia cargada
			idServicio = getInstance().getIdServicio();

		promedio = promedio(idServicio);
		
		String estrellas = "";
		String una = "<img src='img/star.png'/>&nbsp;";
		for(int i = 0; i< promedio;i++){
			estrellas += una;
		}
		return estrellas;
	}

	public long promedio(long idServicio){
		Query q = getEntityManager().createQuery("select avg(c.puntaje) from Calificacion c "+
				"where c.servicio.idServicio = "+idServicio); //("select u from Usuario u where u.openid=:openid");
		//obtenemos la lista
		Double r = (Double)q.getSingleResult();
		if(r != null)
			return Math.round(r);
		return 0;
	}
	
}
