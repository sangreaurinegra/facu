package edu.tsi1.gr5.crazyfinger.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import edu.tsi1.gr5.crazyfinger.entity.Usuario;

@Name("authenticator")
public class Authenticator
{
    @Logger private Log log;

    @In Identity identity;
    @In Credentials credentials;

    @In(create=true)
    UsuarioList usuarioList;
    
    @Out(scope=ScopeType.SESSION,required=false)
    Usuario usuario;
    
    public boolean authenticate()
    {
        log.info("authenticating {0}", credentials.getUsername());
        //write your authentication logic here,
        //return true if the authentication was
        //successful, false otherwise
        //if ("admin".equals(credentials.getUsername()))
        //{
        	
        	usuario = usuarioList.getUsuarioPorNombre(credentials.getUsername());
            identity.addRole("admin");
            return true;
        //}
        //return false;
    }

    public void terminarSesion(){
    	usuario = null;
    }
    
}
