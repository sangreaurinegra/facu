package edu.tsi1.gr5.crazyfinger.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import edu.tsi1.gr5.crazyfinger.entity.Usuario;
import edu.tsi1.gr5.crazyfinger.pared.entity.Pared;

@Name("registrarBean")
@Scope(ScopeType.CONVERSATION)
public class RegistrarBean {
	
	@In(create=true)
	UsuarioHome usuarioHome;
	
	@In(create=true)
	UsuarioList usuarioList;
	
	//@In(create=true)
	Usuario nuevo;
	
	//@In(create=true)
	Pared pared;
	
	@In
	private FacesMessages facesMessages;
	
	@Create
	public void init(){
		nuevo = new Usuario();
		pared = new Pared();
	}
	
	public String registrar(){
		if(validar()){
			Usuario u = usuarioList.getUsuarioPorNombre(nuevo.getNombre());
			if (u == null){
				u = usuarioList.getUsuarioPorOpenId(nuevo.getOpenid());
				if (u == null){
					// guardamos el usuario
					nuevo.setPared(pared);
					usuarioHome.setInstance(nuevo);

					usuarioHome.persist();

					facesMessages.addToControl("exito", "{0}, Bienvenido a Crazy Finger Turismo y viajes.",nuevo.getNombre());
					return "exito";
				}
				facesMessages.addToControl("ya existe", "Ya existe un usuario con ese openid por favor elija otro");
				return "ya existe";
			}
			facesMessages.addToControl("ya existe", "Ya existe un usuario con ese nombre por favor elija otro");
			return "ya existe";
		}
		facesMessages.addToControl("error registro", "Campos invalidos o nulos");
		return "error";
	}

	private boolean validar(){
		if(
				nuevo.getNombre() != null &&
				nuevo.getOpenid() != null &&
				pared.getTitulo() != null
		)
			return true;
		return false;
	}
	
	public Usuario getNuevo() {
		return nuevo;
	}

	public void setNuevo(Usuario nuevo) {
		this.nuevo = nuevo;
	}

	public Pared getPared() {
		return pared;
	}

	public void setPared(Pared pared) {
		this.pared = pared;
	}


}
