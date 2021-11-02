package edu.tsi1.gr5.crazyfinger.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import edu.tsi1.gr5.crazyfinger.entity.Actividad;
import edu.tsi1.gr5.crazyfinger.entity.Alojamiento;
import edu.tsi1.gr5.crazyfinger.entity.Lugar;
import edu.tsi1.gr5.crazyfinger.entity.Nodo;
import edu.tsi1.gr5.crazyfinger.entity.Puntoturistico;
import edu.tsi1.gr5.crazyfinger.entity.Servicio;
import edu.tsi1.gr5.crazyfinger.entity.Traslado;
import edu.tsi1.gr5.crazyfinger.media.entity.Media;

@Name("lugarHome")
public class LugarHome extends EntityHome<Lugar> {

	public void setLugarIdLugar(Long id) {
		setId(id);
	}

	public Long getLugarIdLugar() {
		return (Long) getId();
	}

	@Override
	protected Lugar createInstance() {
		Lugar lugar = new Lugar();
		return lugar;
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
	
	public List<Alojamiento> getAlojamientos() {
		List<Alojamiento> lista = new ArrayList<Alojamiento>();
		for(Iterator<Servicio> it = getInstance().getServicios().iterator();it.hasNext();){
			Servicio s = it.next();
			if(s.getTipo().equalsIgnoreCase("hotel")){
				Alojamiento a = new Alojamiento();
				a.setNombre(s.getNombre());
				a.setCalificacions(s.getCalificacions());
				a.setCosto(s.getCosto());
				a.setIdServicio(s.getIdServicio());
				a.setLugar(s.getLugar());
				a.setTipo(s.getTipo());
				a.setUrl(s.getUrl());
				lista.add(a);
			}
				
		}
		return lista;
	}

	public Lugar getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Actividad> getActividades() {
		return getInstance() == null ? null : new ArrayList<Actividad>(
				getInstance().getActividades());
	}

	public List<Media> getMedias() {
		return getInstance() == null ? null : new ArrayList<Media>(
				getInstance().getMedias());
	}

	public List<Nodo> getNodos() {
		return getInstance() == null ? null : new ArrayList<Nodo>(getInstance()
				.getNodos());
	}

	public List<Puntoturistico> getPuntoturisticos() {
		return getInstance() == null ? null : new ArrayList<Puntoturistico>(
				getInstance().getPuntoturisticos());
	}

	public List<Servicio> getServicios() {
		return getInstance() == null ? null : new ArrayList<Servicio>(
				getInstance().getServicios());
	}

	public List<Traslado> getTrasladosForDestino() {
		return getInstance() == null ? null : new ArrayList<Traslado>(
				getInstance().getTrasladosForDestino());
	}

	public List<Traslado> getTrasladosForOrigen() {
		return getInstance() == null ? null : new ArrayList<Traslado>(
				getInstance().getTrasladosForOrigen());
	}

}
