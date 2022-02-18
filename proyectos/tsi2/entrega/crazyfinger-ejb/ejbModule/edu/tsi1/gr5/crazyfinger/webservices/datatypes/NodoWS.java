package edu.tsi1.gr5.crazyfinger.webservices.datatypes;

import java.util.Date;

import edu.tsi1.gr5.crazyfinger.entity.Alojamiento;
import edu.tsi1.gr5.crazyfinger.entity.Estadia;
import edu.tsi1.gr5.crazyfinger.entity.Lugar;
import edu.tsi1.gr5.crazyfinger.entity.Pasaje;

public class NodoWS {

	
	private String alojamientonombre;
	private Double alojamientocosto;
	
	
	
	private Date estadiafechaDesde;
	private Date estadiafechaHasta;
	private double estadiacostototal;

	
	private String pasajeSalidahorario;
	private String pasajeSalidaempresa;
	private Double pasajeSalidacosto;
	
	
	private String lugarnombre;
	private String lugarpais;
	private double lugarlatitud;
	private double lugarlongitud;
	public String getAlojamientonombre() {
		return alojamientonombre;
	}
	public void setAlojamientonombre(String alojamientonombre) {
		this.alojamientonombre = alojamientonombre;
	}
	public Double getAlojamientocosto() {
		return alojamientocosto;
	}
	public void setAlojamientocosto(Double alojamientocosto) {
		this.alojamientocosto = alojamientocosto;
	}
	public Date getEstadiafechaDesde() {
		return estadiafechaDesde;
	}
	public void setEstadiafechaDesde(Date estadiafechaDesde) {
		this.estadiafechaDesde = estadiafechaDesde;
	}
	public Date getEstadiafechaHasta() {
		return estadiafechaHasta;
	}
	public void setEstadiafechaHasta(Date estadiafechaHasta) {
		this.estadiafechaHasta = estadiafechaHasta;
	}
	public double getEstadiacostototal() {
		return estadiacostototal;
	}
	public void setEstadiacostototal(double estadiacostototal) {
		this.estadiacostototal = estadiacostototal;
	}
	
	public String getPasajeSalidahorario() {
		return pasajeSalidahorario;
	}
	public void setPasajeSalidahorario(String pasajeSalidahorario) {
		this.pasajeSalidahorario = pasajeSalidahorario;
	}
	public String getPasajeSalidaempresa() {
		return pasajeSalidaempresa;
	}
	public void setPasajeSalidaempresa(String pasajeSalidaempresa) {
		this.pasajeSalidaempresa = pasajeSalidaempresa;
	}
	public Double getPasajeSalidacosto() {
		return pasajeSalidacosto;
	}
	public void setPasajeSalidacosto(Double pasajeSalidacosto) {
		this.pasajeSalidacosto = pasajeSalidacosto;
	}
	public String getLugarnombre() {
		return lugarnombre;
	}
	public void setLugarnombre(String lugarnombre) {
		this.lugarnombre = lugarnombre;
	}
	public String getLugarpais() {
		return lugarpais;
	}
	public void setLugarpais(String lugarpais) {
		this.lugarpais = lugarpais;
	}
	public double getLugarlatitud() {
		return lugarlatitud;
	}
	public void setLugarlatitud(double lugarlatitud) {
		this.lugarlatitud = lugarlatitud;
	}
	public double getLugarlongitud() {
		return lugarlongitud;
	}
	public void setLugarlongitud(double lugarlongitud) {
		this.lugarlongitud = lugarlongitud;
	}
	
	
	
}
