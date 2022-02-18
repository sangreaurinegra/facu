package uy.edu.fing.inco.hpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class BgpSecuencial {
	
	private HashMap<String, ArrayList<Nodo>> datosAsPath;
	private ArrayList<Nodo> asPathLengths;
	private HashMap<String, Integer> countLineas;

	public BgpSecuencial() {
		// se inicializa el hash para 1000 valores
		datosAsPath = new HashMap<String, ArrayList<Nodo>>(1000);
		countLineas = new HashMap<String, Integer>(10000);
		asPathLengths = new ArrayList<Nodo>();
	}
	
	/**************************** 
	 * METODOS PARA ESTADISTICA 1
	 ****************************/
	public void insertarCountLineas(String linea){
		if (countLineas.containsKey(linea)){
			Integer count = countLineas.get(linea);
			count++;
			countLineas.remove(linea);
			countLineas.put(linea, count);
		}else{
			countLineas.put(linea, 1);
		}
	}
	
	public void imprimirEstadistica1(){
		Set<String> lineas = countLineas.keySet();
		System.out.println("#########################");
		System.out.println("ESTADISTICA 1: DUPLICADOS");
		System.out.println("##########################");
		for (String linea : lineas) {
			Integer count = countLineas.get(linea);
			if (count > 1){
				System.out.println(count+"	-	"+linea);
			}
		}
	}
	/**************************** 
	 ****************************
	 ****************************/
	
	/******************************** 
	 * METODOS PARA ESTADISTICA 2 Y 3
	 ********************************/
	public void insertarDatosAsPath(String prefijo, String asPath){
		if (datosAsPath.containsKey(prefijo)){
			ArrayList<Nodo> elementos = datosAsPath.get(prefijo);
			Nodo elem = null;
			for (Nodo nodo : elementos) {
				if (nodo.getRuta().equals(asPath)){
					elem = nodo;
					break;
				}
			}
			if (elem != null){ // Prefijo y asPath existente
				elem.setCantidad(elem.getCantidad() + 1);
			}else{ // Prefijo existente y asPath no
				elem = new Nodo();
				elem.setRuta(asPath);
				elem.setLargo((asPath.split(" ")).length);
				elem.setCantidad(1);
				elementos.add(elem);
			}
		}else{ // Prefijo nuevo
			ArrayList<Nodo> elementos = new ArrayList<Nodo>();
			Nodo elem = new Nodo();
			elem.setRuta(asPath);
			elem.setLargo((asPath.split(" ")).length);
			elem.setCantidad(1);
			elementos.add(elem);
			
			datosAsPath.put(prefijo, elementos);
		}
	}

	public void imprimirEstadistica2(){
		System.out.println("###############################");
		System.out.println("ESTADISTICA 2: DIFERENTES RUTAS");
		System.out.println("###############################");
		Set<String> prefijos = datosAsPath.keySet();
		for (String prefijo : prefijos) {
			ArrayList<Nodo> elementos = datosAsPath.get(prefijo);
			if (elementos.size() > 1){
				System.out.println(prefijo+" "+elementos.size());
				/*for (Nodo elem : elementos) {
					System.out.println("	"+elem.getRuta());
				}*/
			}
		}
	}
	
	public void imprimirEstadistica3(){
		System.out.println("#############################");
		System.out.println("ESTADISTICA 3: AS_PATH LENGTH");
		System.out.println("#############################");
		Set<String> prefijos = datosAsPath.keySet();
		for (String prefijo : prefijos) {
			ArrayList<Nodo> elementos = datosAsPath.get(prefijo);
			for (Nodo elem : elementos) {
				asPathLengths.add(new Nodo(prefijo,elem.getLargo()));
			}
		}
		Collections.sort(asPathLengths);
		for (Nodo elem : asPathLengths) {
			System.out.println(elem.getLargo()+"	"+elem.getRuta());
		}
	}
	/******************************** 
	 ********************************
	 ********************************/
	
	public static void main(String[] args){
		Date inicio = new Date();
		if (args.length != 1){
			System.out.println("Error: parametro invalido.");
			System.exit(0);
		}
		
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	 
	    try {
	    	BgpSecuencial estadisticas = new BgpSecuencial();
	    	
	    	archivo = new File (args[0]);
	        fr = new FileReader (archivo);
	        br = new BufferedReader(fr,4096);
	 
	        String linea;
	        while((linea = br.readLine()) != null){
	        	String[] lineaSplit = linea.split("\\|");
	        	
	        	estadisticas.insertarCountLineas(linea);
	        	
	        	if (lineaSplit.length >= 7){
	        		String prefijo = lineaSplit[5];
	        		String asPath = lineaSplit[6];
	        		estadisticas.insertarDatosAsPath(prefijo, asPath);
	        	}
	        }
	        estadisticas.imprimirEstadistica1();
	        System.out.println();
	        estadisticas.imprimirEstadistica2();
	        System.out.println();
	        estadisticas.imprimirEstadistica3();
	        System.out.println();
	    }
	    catch(Exception e){
	    	System.out.println("Error: leyendo el archivo.");
	    	e.printStackTrace();
	    }finally{
	    	try{                   
	    		if( null != fr ){ fr.close(); }                 
	        }catch (Exception e2){
	        	System.out.println("Error: cerrando el archivo.");
	        	e2.printStackTrace();
	        }
	    }
	    Date fin = new Date();
	    long duracion = fin.getTime() - inicio.getTime();
	    long hora = duracion / (60 * 60 * 1000) % 24; 
	    long min = duracion / (60 * 1000) % 60;
	    long seg = duracion / 1000 % 60;
	    
	    System.out.println();
	    System.out.println("DURACION: "+hora+":"+min+":"+seg);
	}

}
