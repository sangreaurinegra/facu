package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("calificacionList")
public class CalificacionList extends EntityQuery<Calificacion> {

	private static final String EJBQL = "select calificacion from Calificacion calificacion";

	private static final String[] RESTRICTIONS = { "lower(calificacion.comentario) like lower(concat(#{calificacionList.calificacion.comentario},'%'))", };

	private Calificacion calificacion = new Calificacion();

	public CalificacionList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Calificacion getCalificacion() {
		return calificacion;
	}
}
