package edu.tsi1.gr5.crazyfinger.webservices.ejb;

import java.rmi.RemoteException;

import javax.ejb.Remote;

import edu.tsi1.gr5.crazyfinger.webservices.datatypes.DatoProyectoViaje;
import edu.tsi1.gr5.crazyfinger.webservices.datatypes.RespuestaAltaProyecto;

@Remote
public interface RemoteProyectoViaje {

	public RespuestaAltaProyecto altaProyecto(DatoProyectoViaje dato) throws RemoteException;
	
}
