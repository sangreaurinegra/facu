package edu.tsi1.gr5.crazyfinger.session;

import java.util.Date;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import edu.tsi1.gr5.crazyfinger.entity.InvitacionProyecto;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;
import edu.tsi1.gr5.crazyfinger.pared.entity.Pared;

@Name("invitacionProyectoHome")
public class InvitacionProyectoHome extends EntityHome<InvitacionProyecto>
{
	@In(create=true)
    ProyectoviajeHome proyectoviajeHome;
    
    @In(create=true)
    UsuarioHome usuarioHome;
	
	public void setInvitacionProyectoIdInvitacionProyecto(Long id) {
		setId(id);
	}

	public Long getInvitacionProyectoIdInvitacionProyecto() {
		return (Long) getId();
	}

	@Override
	protected InvitacionProyecto createInstance() {
		InvitacionProyecto usuario = new InvitacionProyecto();
		return usuario;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		
	}

	public boolean isWired() {
		return true;
	}

	public InvitacionProyecto getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public void aceptarInvitacion(long idInvitacion){
		setInvitacionProyectoIdInvitacionProyecto(idInvitacion);
		load();
		getInstance().setEstado(InvitacionProyecto.ESTADO_ACEPTADO);
		getInstance().setFechaAceptado(new Date());
		this.persist();
	}
	
	public void declinarInvitacion(long idInvitacion){
		setInvitacionProyectoIdInvitacionProyecto(idInvitacion);
		load();
		this.remove();
	}
	

}
