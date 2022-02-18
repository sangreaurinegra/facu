package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("lugarList")
public class LugarList extends EntityQuery<Lugar> {

	private static final String EJBQL = "select lugar from Lugar lugar";

	private static final String[] RESTRICTIONS = {
			"lower(lugar.nombre) like lower(concat(#{lugarList.lugar.nombre},'%'))",
			"lower(lugar.pais) like lower(concat(#{lugarList.lugar.pais},'%'))", };

	private Lugar lugar = new Lugar();

	public LugarList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Lugar getLugar() {
		return lugar;
	}
	
	List<String> etiquetasLugares;

	public List<String> getEtiquetasLugares() {
		if(etiquetasLugares == null)
			etiquetasLugares = getEtiquetas();
		return etiquetasLugares;
	}

	public void setEtiquetasLugares(List<String> etiquetasLugares) {
		this.etiquetasLugares = etiquetasLugares;
	}
	
	private List<String> getEtiquetas() {
		List<String> list = new ArrayList<String>();
		for(Lugar l: this.getResultList()){
			list.add(l.getNombre()+","+l.getPais());
		}
		
		return list;
	}
	
	public Lugar getLugarPorId(long id) {
		Query q = getEntityManager().createQuery("select l from Lugar l where l.idLugar=:idlugar");
		
		q.setParameter("idlugar", id);
		List<Lugar> l = (List<Lugar>)q.getResultList();
		if (l.size()>0) {
			return l.get(0);
		}
		else {
			return null;
		}
	}
	
}
