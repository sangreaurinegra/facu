package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("usuarioList")
public class UsuarioList extends EntityQuery<Usuario> {

	private static final String EJBQL = "select usuario from Usuario usuario";

	private static final String[] RESTRICTIONS = {
			"lower(usuario.nombre) like lower(concat(#{usuarioList.usuario.nombre},'%'))",
			"lower(usuario.openid) like lower(concat(#{usuarioList.usuario.openid},'%'))",
			"lower(usuario.password) like lower(concat(#{usuarioList.usuario.password},'%'))", };

	private Usuario usuario = new Usuario();

	public UsuarioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public Usuario getUsuarioPorOpenId(String openid) {
		Query q = getEntityManager().createQuery("select u from Usuario u where u.openid=:openid"); //("select u from Usuario u where u.openid=:openid");
		
		q.setParameter("openid", openid);
		List<Usuario> l = (List<Usuario>)q.getResultList();
		if (l.size()>0) {
			return l.get(0);
		}
		else {
			return null;
		}
	}
	
	public Usuario getUsuarioPorIdUsuario(long idUsuario) {
		Query q = getEntityManager().createQuery("select u from Usuario u where u.idUsuario=:idusuario"); //("select u from Usuario u where u.openid=:openid");
		
		q.setParameter("idusuario", idUsuario);
		List<Usuario> l = (List<Usuario>)q.getResultList();
		if (l.size()>0) {
			return l.get(0);
		}
		else {
			return null;
		}
	}
	
	
	public Usuario getUsuarioPorNombre(String nombre) {
		Query q = getEntityManager().createQuery("select u from Usuario u where u.nombre=:nombre"); //("select u from Usuario u where u.openid=:openid");
		
		q.setParameter("nombre", nombre);
		List<Usuario> l = (List<Usuario>)q.getResultList();
		if (l.size()>0) {
			return l.get(0);
		}
		else {
			return null;
		}
	}
	
	public List<Usuario> buscarGente(String criterio) {
		Query q = getEntityManager().createQuery("select u from Usuario u "+
				"where u.nombre like '%"+criterio.trim()+"%' or"+
				" u.pais like '%"+criterio.trim()+"%' or"+
				" u.ciudad like '%"+criterio.trim()+"%' "); //("select u from Usuario u where u.openid=:openid");
		
		//q.setParameter("nombre", criterio);
		return (List<Usuario>)q.getResultList();
		
	}
}
