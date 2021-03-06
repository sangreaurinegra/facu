package edu.tsi1.gr5.crazyfinger.media.entity;

// Generated 03-oct-2009 15:52:18 by Hibernate Tools 3.2.4.GA

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import edu.tsi1.gr5.crazyfinger.entity.Comentario;
import edu.tsi1.gr5.crazyfinger.entity.Lugar;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;
import edu.tsi1.gr5.crazyfinger.helper.FechasHelper;
import edu.tsi1.gr5.crazyfinger.pared.entity.Pared;

/**
 * Media generated by hbm2java
 */
@Entity
@Table(name = "media")
public class Media implements java.io.Serializable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3708580241417365543L;
	
//	public static final long TIPO_FOTO = 0;
//	public static final long TIPO_VIDEO = 1;
	
	private long idMedia;
	private Lugar lugar;
	private Pared pared;
	private Usuario usuario;
	private String url;
	private String datosextras;
	private Date fechaEnviado;
	
	private byte[] image;
	private String imageContentType;
	
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
//	private Set<File> files = new HashSet<File>(0);
	
	
	
	TipoMedia tipomedia = TipoMedia.FOTO;


	public Media() {
		
	}

	public Media(long idMedia, Usuario usuario) {
		this.idMedia = idMedia;
		this.usuario = usuario;
	}

	public Media(long idMedia, Lugar lugar, Pared pared, Usuario usuario,
			String url, String datosextras) {
		this.idMedia = idMedia;
		this.lugar = lugar;
		this.pared = pared;
		this.usuario = usuario;
		this.url = url;
		this.datosextras = datosextras;
	}

	@GeneratedValue
	@Id
	@Column(name = "id_media", unique = true, nullable = false)
	public long getIdMedia() {
		return this.idMedia;
	}

	public void setIdMedia(long idMedia) {
		this.idMedia = idMedia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lugar")
	public Lugar getLugar() {
		return this.lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pared")
	public Pared getPared() {
		return this.pared;
	}

	public void setPared(Pared pared) {
		this.pared = pared;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario", nullable = false)
	@NotNull
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "url", length = 999)
	@Length(max = 999)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "datosextras")
	public String getDatosextras() {
		return this.datosextras;
	}

	public void setDatosextras(String datosextras) {
		this.datosextras = datosextras;
	}

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "media")
	public List<Comentario> getComentarios() {
		return this.comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
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

	@Column(name = "imagecontenttype")
	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	// manejo de tipo media
	public enum TipoMedia {
		FOTO("Foto"),
		VIDEO("Video");

		private String key;
			
		TipoMedia(String key) {
			this.key= key;
		}
		
		public String getKey() {
			return key;
		}
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name="tipomedia")
	public TipoMedia getTipomedia() {
		return tipomedia;
	}

	public void setTipomedia(TipoMedia tipomedia) {
		this.tipomedia = tipomedia;
	}
	
	@Column(name = "fechaenviado",nullable = false)
	public Date getFechaEnviado() {
		return fechaEnviado;
	}

	public void setFechaEnviado(Date fechaEnviado) {
		this.fechaEnviado = fechaEnviado;
	}
	
//	@Factory("tiposMedia")
//	   public TipoMedia[] getTiposMedia()
//	   {
//	      return TipoMedia.values();
//	   }
	
	public String fechaEnviadoFormato(){
		return FechasHelper.getInstance().fechaEnviadoFormato(fechaEnviado);
	}

	public int compareTo(Object otraMedia) {
		// TODO Auto-generated method stub
		int ret = 0;
		Date aux =((Media) otraMedia).getFechaEnviado();
		if(fechaEnviado!=null && aux!=null ){
			if(fechaEnviado.before(aux)){
				ret=-1;
			}else{
				if(fechaEnviado.before(aux)){
					ret=1;
				}
			}
		}
		
		return ret;
	}
	
}



