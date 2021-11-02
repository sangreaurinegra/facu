package edu.tsi1.gr5.crazyfinger.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	
}