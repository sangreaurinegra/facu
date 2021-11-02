package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("pasajeList")
public class PasajeList extends EntityQuery<Pasaje> {

	private static final String EJBQL = "select pasaje from Pasaje pasaje";

	private static final String[] RESTRICTIONS = { "lower(pasaje.fecha) like lower(concat(#{pasajeList.pasaje.fecha},'%'))", };

	private Pasaje pasaje = new Pasaje();

	public PasajeList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Pasaje getPasaje() {
		return pasaje;
	}
}
