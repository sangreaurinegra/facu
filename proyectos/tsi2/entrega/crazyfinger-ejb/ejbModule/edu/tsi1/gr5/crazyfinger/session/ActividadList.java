package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("actividadList")
public class ActividadList extends EntityQuery<Actividad> {

	private static final String EJBQL = "select actividad from Actividad actividad";

	private static final String[] RESTRICTIONS = {
			"lower(actividad.detalle) like lower(concat(#{actividadList.actividad.detalle},'%'))",
			"lower(actividad.nombre) like lower(concat(#{actividadList.actividad.nombre},'%'))",
			"lower(actividad.tipo) like lower(concat(#{actividadList.actividad.tipo},'%'))",
			"lower(actividad.url) like lower(concat(#{actividadList.actividad.url},'%'))", };

	private Actividad actividad = new Actividad();

	public ActividadList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Actividad getActividad() {
		return actividad;
	}
}
