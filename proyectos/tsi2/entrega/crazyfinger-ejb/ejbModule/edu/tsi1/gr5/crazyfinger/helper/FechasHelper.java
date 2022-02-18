package edu.tsi1.gr5.crazyfinger.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechasHelper{
	
	private static FechasHelper instance;

	private FechasHelper() {
	}

	public static FechasHelper getInstance() {
		if (null == instance) {
			instance = new FechasHelper();
		}
		return instance;
	}
	
	public String fechaEnviadoFormato(Date fechaEnviado){
		String ret = "-";
		if(fechaEnviado!=null){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm ");
		ret = sdf.format(fechaEnviado);
		}
		return ret ;
	}
	
	public String fechaConFormato(Date fecha, String formato){
		String ret = "-";
		if(fecha!=null && formato != null){
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		ret = sdf.format(fecha);
		}
		return ret ;
	}
	
	
	/**
	 * pasa de YYYY-MM-DD HH:mm:SS a Date
	 * @param fecha
	 * @return
	 */
	public Date fechaHoraString2date(String fecha) {
		String year = fecha.substring(0,4);
		String month = fecha.substring(5,7);
		String day = fecha.substring(8,10);
		String hora = fecha.substring(11,13);
		String mins = fecha.substring(14,16);
		String secs = fecha.substring(17,19);
		GregorianCalendar g = new GregorianCalendar(
				Integer.parseInt(year), Integer.parseInt(month)-1,Integer.parseInt(day),
				Integer.parseInt(hora), Integer.parseInt(mins) , Integer.parseInt(secs));
		return g.getTime();
	}
	
}