package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("comentarioList")
public class ComentarioList extends EntityQuery<Comentario> {

	private static final String EJBQL = "select comentario from Comentario comentario";

	private static final String[] RESTRICTIONS = { "lower(comentario.mensaje) like lower(concat(#{comentarioList.comentario.mensaje},'%'))", };

	private Comentario comentario = new Comentario();

	public ComentarioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Comentario getComentario() {
		return comentario;
	}
}
