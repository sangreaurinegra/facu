package edu.tsi4.truco.util;

public class Logger {

	private static boolean loguear = true;
	
	public static int NADA = 0;
	public static int ERROR = 1;
	public static int INFO = 2;
	
	public static void Log(int level, Class clase, String msg){
		String detalle="";
		switch (level) {
		case 0:
			detalle="[LOG]";
			break;
		case 1:
			detalle="[ERROR]";
			break;
		case 2:
			detalle="[INFO]";
			break;
		default:
			break;
		}
		if(loguear){
			System.out.println(detalle+" "+clase.getName()+" "+msg);
			System.out.flush();
		}
	}
	
}
