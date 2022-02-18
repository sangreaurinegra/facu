package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("mediotransporteList")
public class MediotransporteList extends EntityQuery<Mediotransporte> {

	private static final String EJBQL = "select mediotransporte from Mediotransporte mediotransporte";

	private static final String[] RESTRICTIONS = {
			"lower(mediotransporte.tipo) like lower(concat(#{mediotransporteList.mediotransporte.tipo},'%'))",
			"lower(mediotransporte.url) like lower(concat(#{mediotransporteList.mediotransporte.url},'%'))", };

	private Mediotransporte mediotransporte = new Mediotransporte();

	public MediotransporteList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Mediotransporte getMediotransporte() {
		return mediotransporte;
	}
}
