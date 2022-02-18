package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

@Name("paqueteturisticoList")
public class PaqueteturisticoList extends EntityQuery<Paqueteturistico> {

	private static final String EJBQL = "select paqueteturistico from Paqueteturistico paqueteturistico";

	private static final String[] RESTRICTIONS = {
			"lower(paqueteturistico.descripcion) like lower(concat(#{paqueteturisticoList.paqueteturistico.descripcion},'%'))",
			"lower(paqueteturistico.nombre) like lower(concat(#{paqueteturisticoList.paqueteturistico.nombre},'%'))", };

	private Paqueteturistico paqueteturistico = new Paqueteturistico();

	public PaqueteturisticoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Paqueteturistico getPaqueteturistico() {
		return paqueteturistico;
	}
	
	private String ciudades;
	private String paises;
	private Date fechaDesde;
	private Date fechaHasta;
	
	private List<Paqueteturistico> habilitados;

	public String getCiudades() {
		return ciudades;
	}

	public void setCiudades(String ciudades) {
		this.ciudades = ciudades;
	}

	public String getPaises() {
		return paises;
	}

	public void setPaises(String paises) {
		this.paises = paises;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public List<Paqueteturistico> getHabilitados() {
		if(habilitados == null)
			habilitados = obtenerPaquetesHabilitados();
		return habilitados;
	}
	
	@Begin
	public List<Paqueteturistico> getHabilitadosws() {
		if(habilitados == null)
			habilitados = obtenerPaquetesHabilitados();
		return habilitados;
	}
	
	public void setHabilitados(List<Paqueteturistico> habilitados) {
		this.habilitados = habilitados;
	}

	public void setPaqueteturistico(Paqueteturistico paqueteturistico) {
		this.paqueteturistico = paqueteturistico;
	}
	
	public List<Paqueteturistico> listaPaquetes(String nombre){
		if(nombre.equalsIgnoreCase("admin"))
			return getResultList();
		return getHabilitados();
	}
	
	public List<Paqueteturistico> obtenerPaquetesHabilitados(){
		Query q = getEntityManager().createQuery("select n from Paqueteturistico n where n.estado = :nombre");

		q.setParameter("nombre", Paqueteturistico.ESTADO_HABILITADO);
		return (List<Paqueteturistico>)q.getResultList();
		/*List<Paqueteturistico> lista = new ArrayList<Paqueteturistico>(getResultList());
		
		for(Paqueteturistico p : lista){
			if(p.getEstado() != Paqueteturistico.ESTADO_HABILITADO)
				lista.remove(p);
		}
		
		return lista;*/
	}
	
	public void buscarPaquetesHabilitados(){
		Query q = getEntityManager().createQuery("select n from Paqueteturistico n where n.estado = :nombre");

		q.setParameter("nombre", Paqueteturistico.ESTADO_HABILITADO);
		habilitados = (List<Paqueteturistico>)q.getResultList();
		
	}
	
}
