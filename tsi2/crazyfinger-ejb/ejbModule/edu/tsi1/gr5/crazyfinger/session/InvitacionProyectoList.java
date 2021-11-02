package edu.tsi1.gr5.crazyfinger.session;

import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import edu.tsi1.gr5.crazyfinger.entity.InvitacionProyecto;
import edu.tsi1.gr5.crazyfinger.entity.Puntoturistico;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;

@Name("invitacionProyectoList")
public class InvitacionProyectoList extends EntityQuery<InvitacionProyecto>
{
	@In
	Usuario usuario;
	
	List<InvitacionProyecto> invitacionesPendientes;
	
    public InvitacionProyectoList()
    {
        setEjbql("select invitacionProyecto from InvitacionProyecto invitacionProyecto");
    }

	public List<InvitacionProyecto> getInvitacionesPendientes() {
		if(invitacionesPendientes == null)
			invitacionesPendientes = buscarInvitacionesPendientes();
		return invitacionesPendientes;
	}

	public void setInvitacionesPendientes(List<InvitacionProyecto> invitacionesPendientes) {
		this.invitacionesPendientes = invitacionesPendientes;
	}
	
	public List<InvitacionProyecto> buscarInvitacionesPendientes(){
			String query = "select p from InvitacionProyecto p where p.estado="+InvitacionProyecto.ESTADO_PENDIENTE+
							" and p.usuarioInvitado.idUsuario="+usuario.getIdUsuario();
			Query q = getEntityManager().createQuery(query);
			
			List<InvitacionProyecto> lista = (List<InvitacionProyecto>)q.getResultList();

			return lista;
		
		
	}
	
}
