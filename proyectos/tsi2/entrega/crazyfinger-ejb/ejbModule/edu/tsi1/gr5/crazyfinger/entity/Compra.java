package edu.tsi1.gr5.crazyfinger.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="compra")
public class Compra implements java.io.Serializable
{
    private long idCompra;
    private Usuario usuario;
    private Paqueteturistico paqueteTuristico;

    public Compra(){
    }
    
    public String toString(){
    	return usuario.getNombre()+" "+paqueteTuristico.getNombre();
    }
    
    public Compra(long idCompra, Usuario usuario, Paqueteturistico paqueteTuristico){
    	this.idCompra=idCompra;
    	this.paqueteTuristico=paqueteTuristico;
    	this.usuario=usuario;
    }
    
    @GeneratedValue
    @Id 
    @Column(name = "id_compra", unique = true, nullable = false)
    public long getIdCompra() {
        return this.idCompra;
    }

    public void setIdCompra(long idCompra) {
        this.idCompra = idCompra;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paqueteturistico", nullable = false)
	@NotNull
	public Paqueteturistico getPaqueteTuristico() {
		return this.paqueteTuristico;
	}

	public void setPaqueteTuristico(Paqueteturistico paqueteTuristico) {
		this.paqueteTuristico = paqueteTuristico;
	}
}
