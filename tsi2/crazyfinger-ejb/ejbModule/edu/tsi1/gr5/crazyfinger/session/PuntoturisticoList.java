package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("puntoturisticoList")
public class PuntoturisticoList extends EntityQuery<Puntoturistico> {

	private static final String EJBQL = "select puntoturistico from Puntoturistico puntoturistico";

	private static final String[] RESTRICTIONS = {
			"lower(puntoturistico.detalle) like lower(concat(#{puntoturisticoList.puntoturistico.detalle},'%'))",
			"lower(puntoturistico.nombre) like lower(concat(#{puntoturisticoList.puntoturistico.nombre},'%'))", };

	private Puntoturistico puntoturistico = new Puntoturistico();

	private List<Puntoturistico> lista;
	private String ciudad;
	private String pais;
	private String nombre;
	
	public String getNombre() {
		if(puntoturistico.getNombre() != null)
			nombre = puntoturistico.getNombre();
		return nombre;
	}

	public void setNombre(String nombre) {
		puntoturistico.setNombre(nombre);
		this.nombre = nombre;
	}	
	
	public List<Puntoturistico> getLista() {
		if(lista == null)
			lista = buscar();//lista = super.getResultList();
		return lista;
	}

	public void setLista(List<Puntoturistico> lista) {
		this.lista = lista;
	}

	public String getCiudad() {
		if(puntoturistico.getLugar() != null && puntoturistico.getLugar().getNombre() != null)
			ciudad = puntoturistico.getLugar().getNombre();
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		if(puntoturistico.getLugar() == null)
			puntoturistico.setLugar(new Lugar());
		puntoturistico.getLugar().setNombre(ciudad);
		this.ciudad = ciudad;
	}

	public String getPais() {
		if(puntoturistico.getLugar() != null && puntoturistico.getLugar().getPais() != null )
			pais = puntoturistico.getLugar().getPais();
		return pais;
	}

	public void setPais(String pais) {
		if(puntoturistico.getLugar() == null)
			puntoturistico.setLugar(new Lugar());
		puntoturistico.getLugar().setPais(pais);
		this.pais = pais;
	}

	public PuntoturisticoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Puntoturistico getPuntoturistico() {
		return puntoturistico;
	}
	
	public List<Puntoturistico> buscar() {
		if((nombre == null || nombre.equalsIgnoreCase("")) &&
			(ciudad == null || ciudad.equalsIgnoreCase("")) &&
			(pais == null || pais.equalsIgnoreCase("")))
			return getResultList();
		String query = "select p from Puntoturistico p ";
		String where = "";
		if(!(ciudad == null || ciudad.equalsIgnoreCase(""))){
			where += " upper(p.lugar.nombre) like upper('%"+getCiudad()+"%') ";
		}
			
		if(!(pais == null || pais.equalsIgnoreCase(""))){
			if(where.length() > 0) where += " and ";
			where += " upper(p.lugar.pais) like upper('%"+getPais()+"%') ";
		}
			
		if(!(nombre == null || nombre.equalsIgnoreCase(""))){
			if(where.length() > 0) where += " and ";
			where += " upper(p.nombre) like upper('%"+getNombre()+"%') ";
		}
		
		Query q = getEntityManager().createQuery(query +" where "+where);
		
		List<Puntoturistico> lista = (List<Puntoturistico>)q.getResultList();

		return lista;
		
	}
	/*
	 * String query = "select p from Paqueteturistico p "+
						"where upper(p.nodopts.lugar.nombre) like upper('"+ciudad+"') or "+
						" upper(p.nodopts.lugar.pais) like upper('"+pais+"') or "+
						" upper(p.nombre) like upper('"++"') or ";
	 * 
	 */
}
