package edu.tsi4.truco.data.comm;

import edu.tsi4.truco.config.ConfigManager;

public abstract class ConnectionFactory {

	private static ICommunicationConnection conexion;
	
	public static ICommunicationConnection getConnection() throws Exception{
		int tipo = ConfigManager.tipoConexion();
		return conexionInstance(tipo);
		
	}

	private static ICommunicationConnection conexionInstance(int tipo) {
		if(tipo == ConfigManager.TIPO_CON_BLUETOOTH)
			if(conexion == null)
				conexion = new BTCommConnection();
		if(tipo == ConfigManager.TIPO_CON_WS)
			if(conexion == null)
				conexion = new WSCommConnection();	
		
		return conexion;
	} 
	
}
