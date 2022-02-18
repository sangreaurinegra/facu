package edu.tsig.geoserver.rest;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

import edu.tsig.utils.http.ClienteHttp;

public class WmsRest implements ActionPipelineProcessor {

	public WmsRest(ConfigTree config){
		
	}
	
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public Message process(Message msg) throws ActionProcessingException {
		try {		
			// obtenemos el request
			HttpRequest request = HttpRequest.getRequest(msg);
			System.out.println(request.getRequestURI());
	
			HashMap<String, String> params = new HashMap<String, String>();
			for(String k : request.getQueryParams().keySet()){
				System.out.println(k+"="+request.getQueryParams().get(k)[0]);
				params.put(k,request.getQueryParams().get(k)[0]);
			}
			String url_geo = "http://localhost:8080/geoserver/";
			String url_esb = request.getRequestURI(); 
			String url_router = "router";
			if(url_esb.endsWith("rest") || url_esb.endsWith("rest/"))
				url_router = "rest";
			url_esb = url_esb.replace("/wms-rest/http/"+url_router+"/","");
			url_esb = url_esb.replace("/wms-rest/http/"+url_router,"");
			if (url_esb.equalsIgnoreCase(""))
				url_esb = "wms";
			url_geo += url_esb;		
			String res = "";
			if(url_geo.endsWith(".png") || url_geo.endsWith(".jpg"))
				msg.getBody().add( ClienteHttp.executeGetBinario(url_geo));
			else if(params.size() > 0 && params.get("REQUEST").equalsIgnoreCase("GetMap"))
				msg.getBody().add( ClienteHttp.executeGetBinario(url_geo, params));
			else{
				if(params.size() > 0)
					res = ClienteHttp.executeGet(url_geo, params);
				else
					res = ClienteHttp.executeGet(url_geo);
				System.out.println();
				System.out.println("OBTENIDO--------------------------------------------------");
				System.out.println(res);
				res = res.replace("http://localhost:8080/geoserver", "http://localhost:8180/wms-rest/http/"+url_router);
				System.out.println("MODIFICADO--------------------------------------------------");
				System.out.println(res);
				msg.getBody().add( res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
