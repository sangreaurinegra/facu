package edu.tsi1.gr5.crazyfinger.entity;

// Generated 03-oct-2009 15:52:18 by Hibernate Tools 3.2.4.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.security.integration.password.Password;

import edu.tsi1.gr5.crazyfinger.media.entity.Media;
import edu.tsi1.gr5.crazyfinger.pared.entity.Pared;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario")
public class Usuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5055030611947516810L;
	
	private long idUsuario;
	private Pared pared;
	private String nombre;
	private String password;
	private String openid;
	private Set<Media> medias = new HashSet<Media>(0);
	private Set<Calificacion> calificacions = new HashSet<Calificacion>(0);
	private Set<Solicitud> solicitudsForSolicitado = new HashSet<Solicitud>(0);
	private Set<Mail> mailsForRemitente = new HashSet<Mail>(0);
	private Set<Proyectoviaje> proyectoviajes = new HashSet<Proyectoviaje>(0);
	private Set<Solicitud> solicitudsForSolicitante = new HashSet<Solicitud>(0);
	private Set<Mail> mailsForDestinatario = new HashSet<Mail>(0);
	private Set<Comentario> comentarios = new HashSet<Comentario>(0);

	//datos del perfil de usuario
	private String pais;
    private Date fechaNacimiento;
    private String ciudad;
    private String sexo;
    private String estadoCivil;
    
    
    private byte[] image;
	private String imageContentType;
	
	public Usuario() {
	}

	public Usuario(long idUsuario, Pared pared, String nombre, String password) {
		this.idUsuario = idUsuario;
		this.pared = pared;
		this.nombre = nombre;
		this.password = password;
	}

	public Usuario(long idUsuario, Pared pared, String nombre, String password,
			String openid, Set<Media> medias, Set<Calificacion> calificacions,
			Set<Solicitud> solicitudsForSolicitado,
			Set<Mail> mailsForRemitente, Set<Proyectoviaje> proyectoviajes,
			Set<Solicitud> solicitudsForSolicitante,
			Set<Mail> mailsForDestinatario, Set<Comentario> comentarios) {
		this.idUsuario = idUsuario;
		this.pared = pared;
		this.nombre = nombre;
		this.password = password;
		this.openid = openid;
		this.medias = medias;
		this.calificacions = calificacions;
		this.solicitudsForSolicitado = solicitudsForSolicitado;
		this.mailsForRemitente = mailsForRemitente;
		this.proyectoviajes = proyectoviajes;
		this.solicitudsForSolicitante = solicitudsForSolicitante;
		this.mailsForDestinatario = mailsForDestinatario;
		this.comentarios = comentarios;
	}

	@GeneratedValue
	@Id
	@Column(name = "id_usuario", unique = true)//, nullable = false
	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=true)
	public Pared getPared() {
		if(pared == null)
			pared = new Pared();
		return this.pared;
	}

	public void setPared(Pared pared) {
		this.pared = pared;
	}

	@Column(name = "nombre", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "password", nullable = false, length = 10)
	@NotNull
	@Length(max = 10)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "openid", length = 250)
	@Length(max = 250)
	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Media> getMedias() {
		return this.medias;
	}

	public void setMedias(Set<Media> medias) {
		this.medias = medias;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Calificacion> getCalificacions() {
		return this.calificacions;
	}

	public void setCalificacions(Set<Calificacion> calificacions) {
		this.calificacions = calificacions;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioBySolicitado")
	public Set<Solicitud> getSolicitudsForSolicitado() {
		return this.solicitudsForSolicitado;
	}

	public void setSolicitudsForSolicitado(
			Set<Solicitud> solicitudsForSolicitado) {
		this.solicitudsForSolicitado = solicitudsForSolicitado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioByRemitente")
	public Set<Mail> getMailsForRemitente() {
		return this.mailsForRemitente;
	}

	public void setMailsForRemitente(Set<Mail> mailsForRemitente) {
		this.mailsForRemitente = mailsForRemitente;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Proyectoviaje> getProyectoviajes() {
		return this.proyectoviajes;
	}

	public void setProyectoviajes(Set<Proyectoviaje> proyectoviajes) {
		this.proyectoviajes = proyectoviajes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioBySolicitante")
	public Set<Solicitud> getSolicitudsForSolicitante() {
		return this.solicitudsForSolicitante;
	}

	public void setSolicitudsForSolicitante(
			Set<Solicitud> solicitudsForSolicitante) {
		this.solicitudsForSolicitante = solicitudsForSolicitante;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioByDestinatario")
	public Set<Mail> getMailsForDestinatario() {
		return this.mailsForDestinatario;
	}

	public void setMailsForDestinatario(Set<Mail> mailsForDestinatario) {
		this.mailsForDestinatario = mailsForDestinatario;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Comentario> getComentarios() {
		return this.comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@Column(name = "pais", nullable = true, length = 50)
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(name = "fechaNacimiento", nullable = true)
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Column(name = "ciudad", nullable = true, length = 50)
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	@Column(name = "sexo", nullable = true, length = 50)
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Column(name = "estadoCivil", nullable = true, length = 50)
	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Column(name = "imagecontenttype")
	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	@Column(name = "imagedata")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
}