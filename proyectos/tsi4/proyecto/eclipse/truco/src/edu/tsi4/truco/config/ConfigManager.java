package edu.tsi4.truco.config;

import edu.tsi4.truco.data.persistencia.RMS;

public class ConfigManager {

	public static int TIPO_CON_BLUETOOTH = 1;
	public static int TIPO_CON_WS = 2;
	
	
	private static String configRS = "TrucoConfig";
	private  static int NUM_RECORD_USUARIO = 1;
	private  static int NUM_RECORD_TIPO_CON = 2;
	private  static int NUM_RECORD_SERV_URL = 3;
	//private  static int NUM_RECORD_USUARIO = 4;
	//private  static int NUM_RECORD_USUARIO = 5;
	//private  static int NUM_RECORD_USUARIO = 6;
	
	private static RMS rms;
	
	
	//METODOS DE OBTENCION DE CONFIGURACION
	
	public static int tipoConexion() throws Exception{
		String i = rms().readRecord(NUM_RECORD_TIPO_CON);
		return Integer.valueOf(i).intValue();
	}
	
	public static String urlServidor() throws Exception{
		return rms().readRecord(NUM_RECORD_SERV_URL);
		
	}
	
	public static String usuario() throws Exception{
		return rms().readRecord(NUM_RECORD_USUARIO);
		 
	}
	
	public static void saveTipoConexion(int valor) throws Exception{
		rms().writeRecord(NUM_RECORD_TIPO_CON, String.valueOf(valor));
	}
	
	public static void saveUrlServidor(String valor) throws Exception{
		rms().writeRecord(NUM_RECORD_SERV_URL, valor);
		
	}
	
	public static void saveUsuario(String valor) throws Exception{
		rms().writeRecord(NUM_RECORD_USUARIO, valor);
		 
	}
	
	//METODOS PROPIOS DE LA CLASE
	
	private static RMS rms() throws Exception{
		if(rms == null){
			rms = new RMS(configRS);
		}
		return rms;
	}
	
	/**
	 * este metodo iniciializa la configuracion debe ser ejecutado al inicio del juego, para que guarde los datos por defecto.
	 * @throws Exception
	 */
	public static void initConfig() throws Exception{
		 if(rms().cantRecords() <= 0){// no hay registros cargo los valores por defecto
			 //usuario
			 NUM_RECORD_USUARIO = rms().writeRecord("Creador");
			 // tipo conexion
			 NUM_RECORD_TIPO_CON = rms().writeRecord((new Integer(TIPO_CON_BLUETOOTH)).toString());
			 // servidor url
			 NUM_RECORD_SERV_URL = rms().writeRecord("http://localhost:8080/truco-ws/services/truco");
			 System.out.println("reg usuario:"+NUM_RECORD_USUARIO);
			 System.out.println("reg tipo con:"+NUM_RECORD_TIPO_CON);
			 System.out.println("reg url serv:"+NUM_RECORD_SERV_URL);
		 }
		 
	}
	
	/**
	 * este metodo cierra la conexion de lectura y escritura de registros
	 * @throws Exception
	 */
	public static void closeConfig() throws Exception{
		 rms().closeRecStore();
		 
	}
	
	
}
