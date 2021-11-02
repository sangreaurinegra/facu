package edu.tsi1.gr5.crazyfinger.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.openid.OpenId;

import edu.tsi1.gr5.crazyfinger.entity.Usuario;

@Name("despuesLogin")
public class DespuesLogin {

	@In(required=false)
    OpenId openid;
    
    @In(create=true)
    UsuarioList usuarioList;
    
    @Out(scope=ScopeType.SESSION)
    Usuario usuario;
    
    @Logger private Log log;
    
    @In
    Credentials credentials;
    
	@Observer("tsi2.CargarUsuario")
    public void cargarUsuario(){
    	log.info("Entro a cargar usuario");
    	if(openid != null){
    		log.info("openid usuario: {0}",openid.getValidatedId());
    		usuario = usuarioList.getUsuarioPorOpenId(openid.getValidatedId());
    		if(usuario == null){
    			// sacamos http://
    			String corto = openid.getValidatedId().substring(7);
    			corto = corto.substring(0, corto.length()-1);
    			log.info("intentamos con el corto: {0}",corto);
    			usuario = usuarioList.getUsuarioPorOpenId(corto);
    		}
    		log.info("usuario: {0}",usuario.getNombre());
    		credentials.setUsername(usuario.getNombre());
    		log.info("seteamos el usuario");
    	}
    }
	
}
