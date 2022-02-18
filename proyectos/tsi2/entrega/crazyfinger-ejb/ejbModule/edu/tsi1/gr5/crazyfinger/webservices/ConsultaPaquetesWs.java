package edu.tsi1.gr5.crazyfinger.webservices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import edu.tsi1.gr5.crazyfinger.entity.Nodopt;
import edu.tsi1.gr5.crazyfinger.entity.Paqueteturistico;
import edu.tsi1.gr5.crazyfinger.session.PaqueteturisticoList;
import edu.tsi1.gr5.crazyfinger.webservices.datatypes.NodoWS;
import edu.tsi1.gr5.crazyfinger.webservices.datatypes.PaquetesTuristicosWS;


@Stateless
@Name("consultapaquetews")
@WebService(name = "ConsultaPaquetesWs", serviceName = "ConsultaPaquetesWs")
public class ConsultaPaquetesWs {
	
	@In(create=true)
	PaqueteturisticoList paqueteturisticoList;

	@WebMethod
	public String prueba(String entrada){
		System.out.println("Entro WS con "+entrada);	 
		return entrada+"procesada";
	}
	
	@WebMethod
	public PaquetesTuristicosWS[] paquetesTuristicos(){
		System.out.println("Entro WS paquetesTuristicos");	 
		
		List<PaquetesTuristicosWS> listapaquetes =  converter(paqueteturisticoList.getHabilitadosws());
		

		if(listapaquetes!=null)
			return  listapaquetes.toArray(new PaquetesTuristicosWS[listapaquetes.size()]);
		else
			return  new ArrayList<PaquetesTuristicosWS>().toArray(new PaquetesTuristicosWS[0]);

	}
	
	private List<PaquetesTuristicosWS> converter(List<Paqueteturistico> listapaquetes){
		ArrayList<PaquetesTuristicosWS> ret= new ArrayList<PaquetesTuristicosWS>();
		if(listapaquetes != null){
			
			for (Paqueteturistico paquete : listapaquetes) {
				PaquetesTuristicosWS paquetews = new PaquetesTuristicosWS();
				paquetews.setDescripcion(paquete.getDescripcion());
				paquetews.setNombre(paquete.getNombre());
				
				List<NodoWS> nodosws = new ArrayList<NodoWS>();
				Iterator<Nodopt> i = paquete.getNodopts().iterator();
				while (i.hasNext()) {
					Nodopt nodopt = i.next();
					NodoWS nodows = new NodoWS();
					if(nodopt.getEstadia()!=null){
						if(nodopt.getEstadia().getAlojamiento()!=null){
							nodows.setAlojamientocosto(nodopt.getEstadia().getAlojamiento().getCosto());
							nodows.setAlojamientonombre(nodopt.getEstadia().getAlojamiento().getNombre());
						}
						nodows.setEstadiacostototal(nodopt.getEstadia().getCostototal());
						nodows.setEstadiafechaDesde(nodopt.getEstadia().getFechaDesde());
						nodows.setEstadiafechaHasta(nodopt.getEstadia().getFechaHasta());
					}
					if(nodopt.getLugar()!=null){
						nodows.setLugarlatitud(nodopt.getLugar().getLatitud());
						nodows.setLugarlongitud(nodopt.getLugar().getLongitud());
						nodows.setLugarnombre(nodopt.getLugar().getNombre());
						nodows.setLugarpais(nodopt.getLugar().getPais());
						
					}
					if(nodopt.getPasajeSalida()!=null){
						if(nodopt.getPasajeSalida().getTraslado()!=null){
							nodows.setPasajeSalidacosto(nodopt.getPasajeSalida().getTraslado().getCosto());
							nodows.setPasajeSalidaempresa(nodopt.getPasajeSalida().getTraslado().getEmpresa());
							nodows.setPasajeSalidahorario(nodopt.getPasajeSalida().getTraslado().getHorario());
						}
					}
					
					nodosws.add(nodows);
				}
				
				paquetews.setNodosws(nodosws);
				ret.add(paquetews);
			}
		}
		
		return ret;
	}
	
}
