package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("trasladoList")
public class TrasladoList extends EntityQuery<Traslado> {

	private static final String EJBQL = "select traslado from Traslado traslado";

	private static final String[] RESTRICTIONS = {
			"lower(traslado.empresa) like lower(concat(#{trasladoList.traslado.empresa},'%'))",
			"lower(traslado.horario) like lower(concat(#{trasladoList.traslado.horario},'%'))",
			//"lower(traslado.tipo) like lower(concat(#{trasladoList.traslado.tipo},'%'))",
			};

	private Traslado traslado = new Traslado();

	public TrasladoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Traslado getTraslado() {
		return traslado;
	}
}
