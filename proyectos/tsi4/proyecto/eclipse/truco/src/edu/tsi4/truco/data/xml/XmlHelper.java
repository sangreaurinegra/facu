package edu.tsi4.truco.data.xml;

import java.util.Enumeration;
import java.util.Hashtable;

import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.bl.truco.mensaje.MensajeEnvio;
import edu.tsi4.truco.bl.truco.mensaje.MensajeRespuesta;


import com.exploringxml.xml.Node;
import com.exploringxml.xml.Xparse;

public class XmlHelper {

	
	
	public static MensajeAbstracto getMensaje(String xml){
		
		boolean esRespuesta=false;
		Node root = new Xparse().parse(xml);
		int occur[] = {1};
		Node msg = root.find("env", occur);
		if(msg==null){
			msg = root.find("res", occur);
			esRespuesta=true;
		}
//		System.out.println("[Xparse]"+cmd.attributes.get("id"));
//		System.out.flush(); 
		String cmd = (String) msg.attributes.get("cmd");
		MensajeAbstracto ret = null ;
		if(esRespuesta) ret = new MensajeRespuesta(Integer.parseInt(cmd));
		else ret = new MensajeEnvio(Integer.parseInt(cmd));
//		System.out.println("[Xparse]"+id);
//		System.out.flush(); 
		Node params = msg.find("params", occur);
		Hashtable hParam = params.attributes;
		Enumeration keys = hParam.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			ret.addParametro(key, (String) hParam.get(key));
		}
		return ret;
	}
	
}
