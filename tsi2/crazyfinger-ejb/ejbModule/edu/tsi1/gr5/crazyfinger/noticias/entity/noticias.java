package edu.tsi1.gr5.crazyfinger.noticias.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;
import org.hibernate.validator.Length;

@Entity
public class noticias implements Serializable
{
    // seam-gen attributes (you should probably edit these)
    private Long id;
    private Integer version;
    private String titulo;
    private String subtitulo;
    private String descripcion;
    // add additional entity attributes

    // seam-gen attribute getters/setters with annotations (you probably should edit)

    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

    @Length(max = 20)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    @Length(max = 40)
	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	@Length(max = 999)
	@Column(length=999)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

    
}
