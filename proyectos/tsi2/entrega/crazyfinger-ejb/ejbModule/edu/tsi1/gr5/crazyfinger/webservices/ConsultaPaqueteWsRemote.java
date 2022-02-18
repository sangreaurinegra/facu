package edu.tsi1.gr5.crazyfinger.webservices;

import javax.ejb.Remote;

import edu.tsi1.gr5.crazyfinger.entity.Paqueteturistico;

@Remote
public interface ConsultaPaqueteWsRemote {

	public String prueba(String entrada);
	
	public Paqueteturistico[] paquetesTuristicos();
}
