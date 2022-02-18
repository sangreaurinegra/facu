/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.vo;

import java.io.Serializable;
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

/**
 *
 * @author dell
 */
@Entity
@Table(name = "cache")
@NamedQueries({@NamedQuery(name = "Cache.findAll", query = "SELECT c FROM Cache c"), @NamedQuery(name = "Cache.findById", query = "SELECT c FROM Cache c WHERE c.id = :id"), @NamedQuery(name = "Cache.findByClave", query = "SELECT c FROM Cache c WHERE c.clave = :clave"), @NamedQuery(name = "Cache.findByValor", query = "SELECT c FROM Cache c WHERE c.valor = :valor")})
public class Cache implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE ,generator="sec_cache")
    @SequenceGenerator(name="sec_cache",initialValue=1,sequenceName="sec_cache")
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "clave")
    private String clave;


    @Column(name = "valor")
    private String valor;

    public Cache() {
    }

    public Cache(Long id) {
        this.id = id;
    }

    public Cache(Long id, String clave) {
        this.id = id;
        this.clave = clave;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
        if (!(object instanceof Cache)) {
            return false;
        }
        Cache other = (Cache) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.tsi2.gr5.vo.Cache[id=" + id + "]";
    }

}
