package edu.tsi1.gr5.crazyfinger.pronosticos.ejb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.seam.annotations.Name;

import edu.tsi1.gr5.crazyfinger.pronosticos.PronosticoError;
import edu.tsi1.gr5.crazyfinger.pronosticos.TablaPronosticos;
import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.PronosticoDia;
import edu.tsi1.gr5.crazyfinger.pronosticos.webservices.WeatherbugConnector;

import static edu.tsi1.gr5.crazyfinger.pronosticos.PronosticoHelper.*;

@Name("mdbPronosticos")
@MessageDriven(name = "pronosticosDrivenBean", activationConfig =  {
      
      @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
      @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/wsQueue")

  })
public class MdbPronosticos implements MessageListener{

	private WeatherbugConnector connector = null;
	
	
//	@Resource
//	SessionContext ctx; 
	
	
	public void onMessage(Message m) {
//		log("llegó mensaje (ejb==null) " + (ejb==null) );
		log("onMessage");
		ObjectMessage om = (ObjectMessage) m;
		try {
			String fecha = (String) om.getObjectProperty(MDB_PROPERTY_FECHA);
			Double latitud = (Double) om.getObjectProperty(MDB_PROPERTY_LATITUD);
			Double longitud = (Double) om.getObjectProperty(MDB_PROPERTY_LONGITUD);

			log ("fecha "  + fecha);
			log ("latitud " + latitud );
			log ("longitud " + longitud);
			
			log ("Obteniendo pronosticos");
			List<PronosticoDia> pronosticos = getConnector().getPronosticos(latitud, longitud);
			log ("exito al obtener pronosticos . cantidad de pronosticos="+pronosticos.size());
			
			for (PronosticoDia pronostico : pronosticos) {
//				ejb.guardarPronostico(pronostico);
				TablaPronosticos.getInstance().putPronostico(pronostico);
				log("pronostico - " + pronostico.getFecha() + " - " + pronostico.getDescription());
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PronosticoError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private WeatherbugConnector getConnector() {
		if (connector == null) {
			connector = new WeatherbugConnector();
		}
		return connector;
	}
	
	public void log(String s) {
		System.out.println("[MdbPronosticos] " + s);
	}
	
}
