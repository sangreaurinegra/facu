package edu.tsi1.gr5.crazyfinger.entity;

// Generated 03-oct-2009 15:52:18 by Hibernate Tools 3.2.4.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Mediotransporte generated by hbm2java
 */
@Entity
@Table(name = "mediotransporte")
public class Mediotransporte implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352522534921176392L;
	
	private long idMediotransporte;
	private String tipo;
	private String url;

	public Mediotransporte() {
	}

	public Mediotransporte(long idMediotransporte) {
		this.idMediotransporte = idMediotransporte;
	}

	public Mediotransporte(long idMediotransporte,
			String tipo) {
		this.idMediotransporte = idMediotransporte;
		this.tipo = tipo;
	}

	@GeneratedValue
	@Id
	@Column(name = "id_mediotransporte", unique = true, nullable = false)
	public long getIdMediotransporte() {
		return this.idMediotransporte;
	}

	public void setIdMediotransporte(long idMediotransporte) {
		this.idMediotransporte = idMediotransporte;
	}

	@Column(name = "tipo", length = 50)
	@Length(max = 50)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "url", length = 250)
	@Length(max = 250)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}