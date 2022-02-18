package edu.tsi4.truco.bl;

import edu.tsi4.truco.bl.truco.JuegoHandler;

public class HandlerFactory {
	
	public static IJuegoHandler handler(){
		return  new JuegoHandler();;
	}
}
