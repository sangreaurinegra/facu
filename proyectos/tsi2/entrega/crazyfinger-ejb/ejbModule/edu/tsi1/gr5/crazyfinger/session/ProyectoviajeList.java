package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("proyectoviajeList")
public class ProyectoviajeList extends EntityQuery<Proyectoviaje> {

	private static final String EJBQL = "select proyectoviaje from Proyectoviaje proyectoviaje";

	private static final String[] RESTRICTIONS = { "lower(proyectoviaje.descripcion) like lower(concat(#{proyectoviajeList.proyectoviaje.descripcion},'%'))", };

	private Proyectoviaje proyectoviaje = new Proyectoviaje();
	private List<InvitacionProyecto> proyectosAmigos;

	public ProyectoviajeList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Proyectoviaje getProyectoviaje() {
		return proyectoviaje;
	}
	
	public List<InvitacionProyecto> proyectosDeAmigos(long idUsuario){
		Query q = getEntityManager().createQuery("select p from InvitacionProyecto p "+
				"where p.estado="+InvitacionProyecto.ESTADO_ACEPTADO + 
				" and p.usuarioInvitado.idUsuario="+idUsuario); //("select u from Usuario u where u.openid=:openid");
		
		//q.setParameter("nombre", criterio);
		return (List<InvitacionProyecto>)q.getResultList();
	}

	public List<InvitacionProyecto> getProyectosAmigos() {
		return proyectosAmigos;
	}

	public void setProyectosAmigos(List<InvitacionProyecto> proyectosAmigos) {
		this.proyectosAmigos = proyectosAmigos;
	}
}
