package edu.tsi1.gr5.crazyfinger.webservices.datatypes;

import java.io.Serializable;
import java.util.Date;

public class DatoNodo implements Serializable{

	// TODO : sacar lugar y orden;
	
	private int estado;
//	private int orden;
//	private int estadia;
	private int lugar;
	private DatoPasaje pasajeLlegada;
	private DatoPasaje pasajeSalida;
	private int tipoNodo;
	// Datos de la estadia
	private int alojamiento;
	private String fechaDesde;
	private String fechaHasta;
	
	public DatoNodo() {
		
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

//	public int getOrden() {
//		return orden;
//	}
//
//	public void setOrden(int orden) {
//		this.orden = orden;
//	}

//	public int getEstadia() {
//		return estadia;
//	}
//
//	public void setEstadia(int estadia) {
//		this.estadia = estadia;
//	}

	public int getLugar() {
		return lugar;
	}

	public void setLugar(int lugar) {
		this.lugar = lugar;
	}

	public DatoPasaje getPasajeLlegada() {
		return pasajeLlegada;
	}

	public void setPasajeLlegada(DatoPasaje pasajeLlegada) {
		this.pasajeLlegada = pasajeLlegada;
	}

	public DatoPasaje getPasajeSalida() {
		return pasajeSalida;
	}

	public void setPasajeSalida(DatoPasaje pasajeSalida) {
		this.pasajeSalida = pasajeSalida;
	}

	public int getTipoNodo() {
		return tipoNodo;
	}

	public void setTipoNodo(int tipoNodo) {
		this.tipoNodo = tipoNodo;
	}

	public int getAlojamiento() {
		return alojamiento;
	}

	public void setAlojamiento(int alojamiento) {
		this.alojamiento = alojamiento;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	
	
}
