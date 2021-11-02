package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("nodopvList")
public class NodopvList extends EntityQuery<Nodopv> {

	private static final String EJBQL = "select nodopv from Nodopv nodopv";

	private static final String[] RESTRICTIONS = {};

	private Nodopv nodopv = new Nodopv();

	public NodopvList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Nodopv getNodopv() {
		return nodopv;
	}
	
	public Long getMaxOrden(long idProyecto){
		
		Query q = getEntityManager().createQuery("select max(n.orden) from Nodopv n where n.proyectoviaje.idProyectoviaje = :nombre"); //("select u from Usuario u where u.openid=:openid");
		
		q.setParameter("nombre", idProyecto);
		List<?> l = q.getResultList();
		if(l.size()> 0 && l.get(0) != null)
			return (Long)l.get(0);
		return 0L;
	}
	
	public List<Nodopv> nodosOrdenados(long idPaquete){
		
		Query q = getEntityManager().createQuery("select n from Nodopv n where n.proyectoviaje.idProyectoviaje = :nombre"+
													" order by n.orden asc");
		
		q.setParameter("nombre", idPaquete);
		List<Nodopv> l = (List<Nodopv>)q.getResultList();

		return l;
		
	}
}
