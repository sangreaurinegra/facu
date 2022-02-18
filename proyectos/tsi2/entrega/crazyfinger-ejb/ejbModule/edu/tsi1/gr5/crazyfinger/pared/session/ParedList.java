package edu.tsi1.gr5.crazyfinger.pared.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import edu.tsi1.gr5.crazyfinger.pared.entity.Pared;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("paredList")
public class ParedList extends EntityQuery<Pared> {

	private static final String EJBQL = "select pared from Pared pared";
	
	

	private static final String[] RESTRICTIONS = { "lower(pared.titulo) like lower(concat(#{paredList.pared.titulo},'%'))", };

	private Pared pared = new Pared();

	public ParedList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Pared getPared() {
		return pared;
	}
	
}
