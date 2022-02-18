package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("nodoptList")
public class NodoptList extends EntityQuery<Nodopt> {

	private static final String EJBQL = "select nodopt from Nodopt nodopt";

	private static final String[] RESTRICTIONS = {};

	private Nodopt nodopt = new Nodopt();

	public NodoptList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Nodopt getNodopt() {
		return nodopt;
	}
	
	public Long getMaxOrden(long idPaquete){
		
		Query q = getEntityManager().createQuery("select max(n.orden) from Nodopt n where n.paqueteturistico.idPaqueteturistico = :nombre"); //("select u from Usuario u where u.openid=:openid");
		
		q.setParameter("nombre", idPaquete);
		List<?> l = q.getResultList();
		if(l.size()> 0 && l.get(0) != null)
			return (Long)l.get(0);
		return 0L;
	}
	
	public List<Nodopt> nodosOrdenados(long idPaquete){
		
		Query q = getEntityManager().createQuery("select n from Nodopt n where n.paqueteturistico.idPaqueteturistico = :nombre"+
													" order by n.orden asc");
		
		q.setParameter("nombre", idPaquete);
		List<Nodopt> l = (List<Nodopt>)q.getResultList();

		return l;
		
	}
}
