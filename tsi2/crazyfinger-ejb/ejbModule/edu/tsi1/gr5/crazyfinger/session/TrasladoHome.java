package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

@Name("trasladoHome")
public class TrasladoHome extends EntityHome<Traslado> {

	@In(create = true)
	LugarHome lugarHome;
	@In(create = true)
	MediotransporteHome mediotransporteHome;
/*	@In(create = true)
	long idLugarOrigen;
	@In(create = true)
	long idLugarDestino;
*/
	@RequestParameter
	String lugarFrom;

	public void setTrasladoIdTraslado(Long id) {
		setId(id);
	}

	public Long getTrasladoIdTraslado() {
		return (Long) getId();
	}

	@Override
	protected Traslado createInstance() {
		Traslado traslado = new Traslado();
		return traslado;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}
/*
	public void wire() {
		getInstance();
		Lugar lugarByDestino = lugarHome.getDefinedInstance();
		if (lugarByDestino != null) {
			getInstance().setLugarByDestino(lugarByDestino);
		}
		Lugar lugarByOrigen = lugarHome.getDefinedInstance();
		if (lugarByOrigen != null) {
			getInstance().setLugarByOrigen(lugarByOrigen);
		}
		Mediotransporte mediotransporte = mediotransporteHome
				.getDefinedInstance();
		if (mediotransporte != null) {
			getInstance().setMediotransporte(mediotransporte);
		}
	}
*/
	public void wire() {
		getInstance();
	
		if (lugarFrom!=null)
		{
			if (lugarFrom.compareTo("Destino")== 0)
			{
				Lugar lugarByDestino = lugarHome.getDefinedInstance();
				if (lugarByDestino != null) {
					getInstance().setLugarByDestino(lugarByDestino);
				}
			}
			else if (lugarFrom.compareTo("Origen")==0)
			{
				Lugar lugarByOrigen = lugarHome.getDefinedInstance();
				if (lugarByOrigen != null) {
					getInstance().setLugarByOrigen(lugarByOrigen);
				}
			}
		}
		else
		{
			try
			{
				lugarHome.setLugarIdLugar(getInstance().getIdLugarByDestino() );
				lugarHome.load();
				Lugar lugarByDestino = lugarHome.getDefinedInstance();
				if (lugarByDestino != null) {
					getInstance().setLugarByDestino(lugarByDestino);
				}
			}
			catch (org.jboss.seam.framework.EntityNotFoundException e)
			{
			}
		
			try
			{
				lugarHome.setLugarIdLugar(getInstance().getIdLugarByOrigen() );
				lugarHome.load();
				Lugar lugarByOrigen = lugarHome.getDefinedInstance();
				if (lugarByOrigen != null) {
					getInstance().setLugarByOrigen(lugarByOrigen);
				}
			}
			catch (org.jboss.seam.framework.EntityNotFoundException e)
			{
			}
		}
		
		Mediotransporte mediotransporte = mediotransporteHome
				.getDefinedInstance();
		if (mediotransporte != null) {
			getInstance().setMediotransporte(mediotransporte);
		}
	}
	public boolean isWired() {
		if (getInstance().getLugarByDestino() == null)
			return false;
		if (getInstance().getLugarByOrigen() == null)
			return false;
		return true;
	}

	public Traslado getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Pasaje> getPasajes() {
		return getInstance() == null ? null : new ArrayList<Pasaje>(
				getInstance().getPasajes());
	}

}
