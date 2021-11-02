package edu.tsi1.gr5.crazyfinger.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.hibernate.validator.Length;

@Entity
@Table(name = "invitacionproyecto")
public class InvitacionProyecto implements Serializable
{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -1803943328274320056L;
	
	public static final int ESTADO_PENDIENTE = 0;
    public static final int ESTADO_ACEPTADO = 1;
    // SI SE RECHAZA LA INVITACION SE BORRA.
    
    private long idInvitacionProyecto;
    private Usuario usuarioOwner;
    private Usuario usuarioInvitado;
    private Proyectoviaje proyecto;
    private int estado;
    private Date fecha;
    private Date fechaAceptado;
    
    
	@Id @GeneratedValue
	@Column(name = "id_invitacionproyecto", unique = true, nullable = false)
	public long getIdInvitacionProyecto() {
		return idInvitacionProyecto;
	}
	public void setIdInvitacionProyecto(long idInvitacionProyecto) {
		this.idInvitacionProyecto = idInvitacionProyecto;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=false)
	public Usuario getUsuarioOwner() {
		return usuarioOwner;
	}
	public void setUsuarioOwner(Usuario usuarioOwner) {
		this.usuarioOwner = usuarioOwner;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=false)
	public Usuario getUsuarioInvitado() {
		return usuarioInvitado;
	}
	public void setUsuarioInvitado(Usuario usuarioInvitado) {
		this.usuarioInvitado = usuarioInvitado;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=false)
	public Proyectoviaje getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyectoviaje proyecto) {
		this.proyecto = proyecto;
	}
	
	@Column(name = "estado", nullable = false)
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	@Column(name = "fecha", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	@Column(name = "fechaaceptado", nullable = true)
	@Temporal(TemporalType.DATE)
	public Date getFechaAceptado() {
		return fechaAceptado;
	}
	public void setFechaAceptado(Date fechaAceptado) {
		this.fechaAceptado = fechaAceptado;
	}
    
	
	public String textoEstado(){
		if(estado == ESTADO_ACEPTADO)
			return "Aceptado";
		else
			return "Pendiente";
	}
}
