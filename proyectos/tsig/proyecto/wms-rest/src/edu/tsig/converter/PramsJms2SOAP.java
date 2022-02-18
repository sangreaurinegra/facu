package edu.tsig.converter;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

public class PramsJms2SOAP implements ActionPipelineProcessor {

	public PramsJms2SOAP(ConfigTree config){
		
	}
	
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public Message process(Message msg) throws ActionProcessingException {
		// TODO Auto-generated method stub
		System.out.println("-- Params a enviar en el soap --");
		
		HttpRequest request = HttpRequest.getRequest(msg);
		
		HashMap<String, String> params = new HashMap<String, String>();
		for(String k : request.getQueryParams().keySet()){
			System.out.println(k+"="+request.getQueryParams().get(k)[0]);
			params.put("getFeatureInfo."+k,request.getQueryParams().get(k)[0]);
		}
		msg.getBody().add("request-params", params);
		return msg;
	}

	private String camelCase(String s){
		String r = s.toLowerCase();
		if(r.indexOf("_") > 0){
			int i = r.indexOf("_");
			char c = Character.toUpperCase(r.charAt(i+1));
			
		}
		return r;
	}
	
	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
