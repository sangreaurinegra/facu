package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

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
}
