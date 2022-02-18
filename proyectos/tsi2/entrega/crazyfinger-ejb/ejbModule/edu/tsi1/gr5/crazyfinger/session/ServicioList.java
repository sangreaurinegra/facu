package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("servicioList")
public class ServicioList extends EntityQuery<Servicio> {

	private static final String EJBQL = "select servicio from Servicio servicio";

	private static final String[] RESTRICTIONS = {
			"lower(servicio.nombre) like lower(concat(#{servicioList.servicio.nombre},'%'))",
			"lower(servicio.tipo) like lower(concat(#{servicioList.servicio.tipo},'%'))",
			"lower(servicio.url) like lower(concat(#{servicioList.servicio.url},'%'))", };

	private Servicio servicio = new Servicio();

	public ServicioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Servicio getServicio() {
		return servicio;
	}
}
