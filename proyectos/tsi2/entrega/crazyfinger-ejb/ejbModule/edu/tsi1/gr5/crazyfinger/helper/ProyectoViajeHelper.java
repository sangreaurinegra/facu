package edu.tsi1.gr5.crazyfinger.helper;

import java.util.List;

import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.PronosticoDia;

public class ProyectoViajeHelper {

	
	public static String toTablaPronosticos(List<PronosticoDia> pronosticos) {
		String tabla = "<TABLE BORDER=\"1\">";
		tabla +="<TR> <TH>Dia</TH> <TH>Alta</TH> <TH>Baja</TH> <TH>Pronostico</TH> </TR>";
		for (PronosticoDia pronostico : pronosticos) {
			tabla +="<TR>";
			
			tabla +="<TD>" +pronostico.getFecha()+ "</TD> ";
			tabla +="<TD>" + pronostico.getTempHigh()+ "</TD> ";
			tabla +="<TD>" + pronostico.getTempLow()+ "</TD> ";
			tabla +="<TD>" +tagImage(pronostico) + "</TD> ";
			
			tabla +="</TR>";
		}
		
		tabla += "</TABLE> ";
		return tabla;
	}
	
	public static String htmlPronosticosNoDisponibles() {
		return "<h2>Pronostico no disponible</h2>";
	}
	
	
	public static String tagImage(PronosticoDia pronostico) {
		String tagImage = "<a href=\"" + pronostico.getWebUrl() + "\" >";
		tagImage += "<IMG SRC=\"";
		tagImage += pronostico.getImage();
		tagImage += "\" ALT=\"" + pronostico.getDescription() + "\"" ;
		
		String onclick = " onclick='window.open(\"" 
			 + pronostico.getWebUrl() + "\",\"Window\",\"menubar=no,width=1000,height=2850,toolbar=no\");'";
		
//		tagImage += onclick ;
		tagImage += " /></a>";
		
		return tagImage;
	}
	
//	public static String tagImage(PronosticoDia pronostico) {
//		String tagImage = "<IMG SRC=\"";
//		tagImage += pronostico.getImage();
//		tagImage += "\" ALT=\"" + pronostico.getDescription() + "\"" ;
//		
//		String onclick = " onclick='window.open(\"" 
//			 + pronostico.getWebUrl() + "\",\"Window\",\"menubar=no,width=1000,height=2850,toolbar=no\");'";
//		
////		tagImage += onclick ;
//		tagImage += " />";
//		
//		return tagImage;
//	}
	
}
