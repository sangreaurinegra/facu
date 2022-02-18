package edu.tsi1.gr5.crazyfinger.pronosticos;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


import net.wxbug.api.ApiForecastData;
import edu.tsi1.gr5.crazyfinger.pronosticos.datatypes.PronosticoDia;

public class PronosticoHelper {

	public static final String MDB_PROPERTY_FECHA = "fecha";
	public static final String MDB_PROPERTY_LATITUD = "latitud";
	public static final String MDB_PROPERTY_LONGITUD = "longitud";
	
	
	public static PronosticoDia toPronosticoDia(ApiForecastData a, String fecha, double latitud, double longitud) {
		PronosticoDia p = new PronosticoDia();
		
		p.setFecha(fecha);
		p.setLatitud(latitud);
		p.setLongitud(longitud);
		
		
		p.setConditionID(a.getConditionID());
		p.setDescription(a.getDescription());
		p.setIcon(a.getIcon());
		p.setImage(a.getImage());
		p.setNight(a.isIsNight());
		p.setPrediction(a.getPrediction());
		p.setShortPrediction(a.getShortPrediction());
		p.setShortTitle(a.getShortTitle());
		p.setTempHigh(a.getTempHigh());
		p.setTempLow(a.getTempLow());
		p.setTempUnit(a.getTempUnit());
		p.setTitle(a.getTitle());
		p.setWebUrl(a.getWebUrl());
		return p;
	}

	public static GregorianCalendar getDia(GregorianCalendar hoy, int desplazamiento) {
		GregorianCalendar dia = new GregorianCalendar();
		dia.setTime(hoy.getTime());
		dia.set(GregorianCalendar.DAY_OF_MONTH, dia.get(GregorianCalendar.DAY_OF_MONTH)+desplazamiento);
		return dia;
	}
	
	
	public static String date2string(Date d) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(d);
		String s = "" + g.get(GregorianCalendar.YEAR);
		s+="-" + (g.get(GregorianCalendar.MONTH)+1) + "-" + g.get(GregorianCalendar.DAY_OF_MONTH);
		return s;
	}

	public static String date2string(GregorianCalendar g) {
		String s = "" + g.get(GregorianCalendar.YEAR);
		s+="-" + (g.get(GregorianCalendar.MONTH)+1) + "-" + g.get(GregorianCalendar.DAY_OF_MONTH);
		return s;
	}

	
	public static List<PronosticoDia> convertir(ApiForecastData [] as, double latitud, double longitud) {
		List<PronosticoDia> res = new ArrayList<PronosticoDia>();
		long millis = System.currentTimeMillis();
		GregorianCalendar hoy = new GregorianCalendar();
		hoy.setTimeInMillis(millis);
		for (int i=0;i<as.length;i++) {
			String fecha = date2string(getDia(hoy,i));
			PronosticoDia pronostico = toPronosticoDia(as[i], fecha, latitud, longitud);
			res.add(pronostico);
		}
		return res;
	}
	
	public static boolean mismoDia(Date fecha1, Date fecha2) {
		GregorianCalendar g1= new GregorianCalendar();
		GregorianCalendar g2= new GregorianCalendar();
		g1.setTime(fecha1);
		g2.setTime(fecha2);

		
		return mismoDia(g1, g2);
	}
	
	public static boolean mismoDia(GregorianCalendar g1, GregorianCalendar g2) {

		if (g1.get(GregorianCalendar.YEAR)!=g2.get(GregorianCalendar.YEAR)) {
			return false;
		}
		if (g1.get(GregorianCalendar.MONTH)!=g2.get(GregorianCalendar.MONTH)) {
			return false;
		}
		if (g1.get(GregorianCalendar.DAY_OF_MONTH)!=g2.get(GregorianCalendar.DAY_OF_MONTH)) {
			return false;
		}
		
		return true;
	}
	
	public static void dejarSoloDia(GregorianCalendar g) {
		g.set(GregorianCalendar.MINUTE, 0);
		g.set(GregorianCalendar.HOUR_OF_DAY, 0);
		g.set(GregorianCalendar.SECOND, 0);
		g.set(GregorianCalendar.MILLISECOND, 0);
	}
	
	
	public static void main(String[] args) {
		GregorianCalendar hoy = new GregorianCalendar();
		long millis = System.currentTimeMillis();
		hoy.setTimeInMillis(millis);

		for (int i=0;i<7;i++) {
			System.out.println("_" + i + " - " + date2string(getDia(hoy, i).getTime()));
		}
		
		GregorianCalendar g1 = new GregorianCalendar(2009,10,10,10,10,10);
		GregorianCalendar g2 = new GregorianCalendar(2010,10,10,20,10,10);
		GregorianCalendar g3 = new GregorianCalendar(2009,9,10,9,10,10);
		GregorianCalendar g4 = new GregorianCalendar(2009,10,9,10,10,10);
		GregorianCalendar g5 = new GregorianCalendar(2009,10,11,6,10,10);
		
		System.out.println("_" + date2string(g1.getTime()) + " - " + date2string(g2.getTime()) +" - "+ mismoDia(g1.getTime(), g2.getTime()));
		System.out.println("_" + date2string(g1.getTime()) + " - " + date2string(g3.getTime()) +" - "+ mismoDia(g1.getTime(), g3.getTime()));
		System.out.println("_" + date2string(g1.getTime()) + " - " + date2string(g4.getTime()) +" - "+ mismoDia(g1.getTime(), g4.getTime()));
		System.out.println("_" + date2string(g1.getTime()) + " - " + date2string(g5.getTime()) +" - "+ mismoDia(g1.getTime(), g5.getTime()));

		System.out.println("_" + date2string(g2.getTime()) + " - " + date2string(g1.getTime()) +" - "+ mismoDia(g2.getTime(), g1.getTime()));
		System.out.println("_" + date2string(g2.getTime()) + " - " + date2string(g2.getTime()) +" - "+ mismoDia(g2.getTime(), g2.getTime()));
		System.out.println("_" + date2string(g2.getTime()) + " - " + date2string(g3.getTime()) +" - "+ mismoDia(g2.getTime(), g3.getTime()));
		System.out.println("_" + date2string(g2.getTime()) + " - " + date2string(g4.getTime()) +" - "+ mismoDia(g2.getTime(), g4.getTime()));
		System.out.println("_" + date2string(g2.getTime()) + " - " + date2string(g5.getTime()) +" - "+ mismoDia(g2.getTime(), g5.getTime()));

		System.out.println("_" + date2string(g3.getTime()) + " - " + date2string(g1.getTime()) +" - "+ mismoDia(g3.getTime(), g1.getTime()));
		System.out.println("_" + date2string(g3.getTime()) + " - " + date2string(g2.getTime()) +" - "+ mismoDia(g3.getTime(), g2.getTime()));
		System.out.println("_" + date2string(g3.getTime()) + " - " + date2string(g3.getTime()) +" - "+ mismoDia(g3.getTime(), g3.getTime()));
		System.out.println("_" + date2string(g3.getTime()) + " - " + date2string(g4.getTime()) +" - "+ mismoDia(g3.getTime(), g4.getTime()));
		System.out.println("_" + date2string(g3.getTime()) + " - " + date2string(g5.getTime()) +" - "+ mismoDia(g3.getTime(), g5.getTime()));

		System.out.println("_" + date2string(g4.getTime()) + " - " + date2string(g1.getTime()) +" - "+ mismoDia(g4.getTime(), g1.getTime()));
		System.out.println("_" + date2string(g4.getTime()) + " - " + date2string(g2.getTime()) +" - "+ mismoDia(g4.getTime(), g2.getTime()));
		System.out.println("_" + date2string(g4.getTime()) + " - " + date2string(g3.getTime()) +" - "+ mismoDia(g4.getTime(), g3.getTime()));
		System.out.println("_" + date2string(g4.getTime()) + " - " + date2string(g4.getTime()) +" - "+ mismoDia(g4.getTime(), g4.getTime()));
		System.out.println("_" + date2string(g4.getTime()) + " - " + date2string(g5.getTime()) +" - "+ mismoDia(g4.getTime(), g5.getTime()));

		System.out.println("_" + date2string(g5.getTime()) + " - " + date2string(g1.getTime()) +" - "+ mismoDia(g5.getTime(), g1.getTime()));
		System.out.println("_" + date2string(g5.getTime()) + " - " + date2string(g2.getTime()) +" - "+ mismoDia(g5.getTime(), g2.getTime()));
		System.out.println("_" + date2string(g5.getTime()) + " - " + date2string(g3.getTime()) +" - "+ mismoDia(g5.getTime(), g3.getTime()));
		System.out.println("_" + date2string(g5.getTime()) + " - " + date2string(g4.getTime()) +" - "+ mismoDia(g5.getTime(), g4.getTime()));
		System.out.println("_" + date2string(g5.getTime()) + " - " + date2string(g5.getTime()) +" - "+ mismoDia(g5.getTime(), g5.getTime()));
		
	}
	
	
}
