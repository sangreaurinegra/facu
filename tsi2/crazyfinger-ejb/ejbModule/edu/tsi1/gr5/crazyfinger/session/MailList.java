package edu.tsi1.gr5.crazyfinger.session;

import edu.tsi1.gr5.crazyfinger.entity.*;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

@Name("mailList")
public class MailList extends EntityQuery<Mail> {

	private static final String EJBQL = "select mail from Mail mail";

	private static final String[] RESTRICTIONS = { "lower(mail.mensaje) like lower(concat(#{mailList.mail.mensaje},'%'))", };

	private Mail mail = new Mail();
	private List<Mail> recibidos;
	private List<Mail> enviados;
	
	@In
	Usuario usuario;

	public MailList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Mail getMail() {
		return mail;
	}
	
	public boolean hayMensajesSinLeer(long idUsuario){
		Query q = getEntityManager().createQuery("select m from Mail m "+
				"where m.usuarioByDestinatario.idUsuario = "+idUsuario+
				" and m.estado = 1 "); //("select u from Usuario u where u.openid=:openid");
		//obtenemos la lista
		List<Mail> lista = (List<Mail>)q.getResultList();
		return lista.size() > 0;
	}
	
	public int cantMensajesSinLeer(long idUsuario){
		Query q = getEntityManager().createQuery("select m from Mail m "+
				"where m.usuarioByDestinatario.idUsuario = "+idUsuario+
				" and m.estado = 1 "); //("select u from Usuario u where u.openid=:openid");
		//obtenemos la lista
		List<Mail> lista = (List<Mail>)q.getResultList();
		return lista.size();
	}
	
	public List<Mail> mensajesRecibidos(long idUsuario){
		
		Query q = getEntityManager().createQuery("select m from Mail m "+
				"where m.usuarioByDestinatario.idUsuario = "+idUsuario+
				" order by m.estado asc, m.fechaEnviado desc "); //("select u from Usuario u where u.openid=:openid");
		//obtenemos la lista
		List<Mail> lista = (List<Mail>)q.getResultList();
		
		return lista;
	}
	
	public List<Mail> mensajesEnviados(long idUsuario){
		
		Query q = getEntityManager().createQuery("select m from Mail m "+
				"where m.usuarioByRemitente.idUsuario = "+idUsuario+
				" order by m.estado asc, m.fechaEnviado desc "); //("select u from Usuario u where u.openid=:openid");
		//obtenemos la lista
		List<Mail> lista = (List<Mail>)q.getResultList();
		
		return lista;
	}

	public List<Mail> getRecibidos() {
		if(recibidos == null)
			recibidos = mensajesRecibidos(usuario.getIdUsuario());
		return recibidos;
	}

	public void setRecibidos(List<Mail> recibidos) {
		this.recibidos = recibidos;
	}

	public List<Mail> getEnviados() {
		if(enviados == null)
			enviados = mensajesEnviados(usuario.getIdUsuario());
		return enviados;
	}

	public void setEnviados(List<Mail> enviados) {
		this.enviados = enviados;
	}
}
