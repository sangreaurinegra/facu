package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("nodoList")
public class NodoList extends EntityQuery<Nodo> {

	private static final String EJBQL = "select nodo from Nodo nodo";

	private static final String[] RESTRICTIONS = {};

	private Nodo nodo = new Nodo();

	public NodoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Nodo getNodo() {
		return nodo;
	}
}
