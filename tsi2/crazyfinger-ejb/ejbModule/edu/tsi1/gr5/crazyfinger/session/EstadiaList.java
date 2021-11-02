package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("estadiaList")
public class EstadiaList extends EntityQuery<Estadia> {

	private static final String EJBQL = "select estadia from Estadia estadia";

	private static final String[] RESTRICTIONS = {};

	private Estadia estadia = new Estadia();

	public EstadiaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Estadia getEstadia() {
		return estadia;
	}
}
