package edu.tsi1.gr5.crazyfinger.pronosticos.datatypes;

public class ClavePronostico {

	private double latitud;
	private double longitud;
	private String fecha;
	
	public ClavePronostico() {
		
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String toString() {
		return fecha +" lat=" + latitud + " lon=" + longitud;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClavePronostico) {
			ClavePronostico c = (ClavePronostico) obj;
			
			// TODO: poner margen de error con latitud y longitud
			return (this.fecha.equals(c.fecha)&&(this.latitud==c.latitud)&&(this.longitud==c.longitud));
		}
		return false;
	}
	
}
