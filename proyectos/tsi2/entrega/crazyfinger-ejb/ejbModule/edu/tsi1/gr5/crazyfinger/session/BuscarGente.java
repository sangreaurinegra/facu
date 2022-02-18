package edu.tsi1.gr5.crazyfinger.session;

import java.util.List;

import javax.ejb.Local;

import edu.tsi1.gr5.crazyfinger.entity.Solicitud;
import edu.tsi1.gr5.crazyfinger.entity.Usuario;

@Local
public interface BuscarGente
{
    public void buscarGente();
    public String getValue();
    public void setValue(String value);
    public void destroy();

    // add additional interface methods here
    public void init();
    public List<Usuario> getResultado();
    public List<Solicitud> getAmigos();
	public void setResultado(List<Usuario> resultado);
	public String solicitarAmistad(long idUsuario);
	public String enviarMensaje(long idUsuario);
	public void aceptarSolicitud(long idSolicitud);
	public void rechazarSolicitud(long idSolicitud);
	public void limpiarMensaje();
}
