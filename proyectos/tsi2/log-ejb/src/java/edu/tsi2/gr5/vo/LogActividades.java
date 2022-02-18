/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "log_actividades")
@SequenceGenerator(name="log_seq_generator",
    sequenceName="log_actividades_sequence",
    initialValue=1,
    allocationSize=1
)
@NamedQueries({@NamedQuery(name = "LogActividades.findAll", query = "SELECT l FROM LogActividades l")})
public class LogActividades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="log_seq_generator")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "web_service")
    private String webService;
    @Basic(optional = false)
    @Column(name = "operacion")
    private String operacion;
    @Column(name = "parametros")
    private String parametros;
    @Column(name = "resultado")
    private Integer resultado;
    @Column(name = "en_cache")
    private Integer enCache;
    @Basic(optional = false)
    @Column(name = "fecha_adicion")
    @Temporal(TemporalType.TIME)
    private Date fechaAdicion;

    public LogActividades() {
    }

    public LogActividades(Long id) {
        this.id = id;
    }

    public LogActividades(Long id, String webService, String operacion, Date fechaAdicion) {
        this.id = id;
        this.webService = webService;
        this.operacion = operacion;
        this.fechaAdicion = fechaAdicion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebService() {
        return webService;
    }

    public void setWebService(String webService) {
        this.webService = webService;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

    public Integer getEnCache() {
        return enCache;
    }

    public void setEnCache(Integer enCache) {
        this.enCache = enCache;
    }

    public Date getFechaAdicion() {
        return fechaAdicion;
    }

    public void setFechaAdicion(Date fechaAdicion) {
        this.fechaAdicion = fechaAdicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogActividades)) {
            return false;
        }
        LogActividades other = (LogActividades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.tsi2.gr5.vo.LogActividades[id=" + id + "]";
    }

}
