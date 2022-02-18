package edu.tsi1.gr5.crazyfinger.webservices.datatypes;

public class RespuestaAltaProyecto {

	private boolean estado; 
	private String descripcion;
	
	public RespuestaAltaProyecto() {
		
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public static RespuestaAltaProyecto crearRespuestaMala(String desc) {
		RespuestaAltaProyecto r = new RespuestaAltaProyecto();
		r.setEstado(false);
		r.setDescripcion(desc);
		return r;
	}

	public static RespuestaAltaProyecto crearRespuestaOk() {
		RespuestaAltaProyecto r = new RespuestaAltaProyecto();
		r.setEstado(true);
		r.setDescripcion("OK");
		return r;
	}
	
}
