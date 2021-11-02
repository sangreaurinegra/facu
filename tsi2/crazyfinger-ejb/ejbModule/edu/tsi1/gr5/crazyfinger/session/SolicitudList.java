package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("solicitudList")
public class SolicitudList extends EntityQuery<Solicitud> {

	private static final String EJBQL = "select solicitud from Solicitud solicitud";

	private static final String[] RESTRICTIONS = { "lower(solicitud.mensaje) like lower(concat(#{solicitudList.solicitud.mensaje},'%'))", };

	private Solicitud solicitud = new Solicitud();

	@In
	Usuario usuario;
	
	public SolicitudList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}
	
	public List<Solicitud> solicitudesPendientes(){
		Query q = getEntityManager().createQuery("select s from Solicitud s where s.usuarioBySolicitado.idUsuario=:idUsuario and s.estado = 0"); //("select u from Usuario u where u.openid=:openid");
		
		q.setParameter("idUsuario", usuario.getIdUsuario());
		List<Solicitud> l = (List<Solicitud>)q.getResultList();
		if (l.size()>0) {
			return l;
		}
		return new ArrayList<Solicitud>();
	}
}
