package edu.tsi1.gr5.crazyfinger.session;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import edu.tsi1.gr5.crazyfinger.entity.Alojamiento;
import edu.tsi1.gr5.crazyfinger.entity.Lugar;

@Name("alojamientoList")
public class AlojamientoList extends EntityQuery<Alojamiento> {

	private static final String EJBQL = "select alojamiento from Alojamiento alojamiento";

	private static final String[] RESTRICTIONS = {};

	private Alojamiento alojamiento = new Alojamiento();

	private List<Alojamiento> lista;
	private String ciudad;
	private String pais;
	private String nombre;
	
	public AlojamientoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Alojamiento getAlojamiento() {
		return alojamiento;
	}
	
	public String getNombre() {
		if(alojamiento.getNombre() != null)
			nombre = alojamiento.getNombre();
		return nombre;
	}

	public void setNombre(String nombre) {
		alojamiento.setNombre(nombre);
		this.nombre = nombre;
	}	
	
	public List<Alojamiento> getLista() {
		if(lista == null)
			lista = buscar();//lista = super.getResultList();
		return lista;
	}

	public void setLista(List<Alojamiento> lista) {
		this.lista = lista;
	}

	public String getCiudad() {
		if(alojamiento.getLugar() != null && alojamiento.getLugar().getNombre() != null)
			ciudad = alojamiento.getLugar().getNombre();
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		if(alojamiento.getLugar() == null)
			alojamiento.setLugar(new Lugar());
		alojamiento.getLugar().setNombre(ciudad);
		this.ciudad = ciudad;
	}

	public String getPais() {
		if(alojamiento.getLugar() != null && alojamiento.getLugar().getPais() != null )
			pais = alojamiento.getLugar().getPais();
		return pais;
	}

	public void setPais(String pais) {
		if(alojamiento.getLugar() == null)
			alojamiento.setLugar(new Lugar());
		alojamiento.getLugar().setPais(pais);
		this.pais = pais;
	}


	public Alojamiento getAlojamientoPorId(long id) {
		Query q = getEntityManager().createQuery("select a from Alojamiento a where a.idServicio=:idAlojamiento");
		
		q.setParameter("idAlojamiento", id);
		List<Alojamiento> l = (List<Alojamiento>)q.getResultList();
		if (l.size()>0) {
			return l.get(0);
		}
		else {
			return null;
		}
	}

	
	public List<Alojamiento> buscar() {
		if((nombre == null || nombre.equalsIgnoreCase("")) &&
			(ciudad == null || ciudad.equalsIgnoreCase("")) &&
			(pais == null || pais.equalsIgnoreCase("")))
			return getResultList();
		String query = "select p from Alojamiento p ";
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
		
		List<Alojamiento> lista = (List<Alojamiento>)q.getResultList();

		return lista;
		
	}
}
