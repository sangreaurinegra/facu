package edu.tsig.converter;

import java.util.Map;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

public class QueryParam2MsgBodyAction implements ActionPipelineProcessor {

	public QueryParam2MsgBodyAction(ConfigTree config){
		
	}
	
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public Message process(Message msg) throws ActionProcessingException {
		HttpRequest request = (HttpRequest)msg.getProperties().getProperty("request");
		if(request == null){
			request = (HttpRequest)msg.getProperties().getProperty("org.jboss.soa.esb.http.HttpRequest#request");
		}
		if(request == null){
			System.out.println("--> ES ESTA: org.jboss.soa.esb.http.HttpRequest");
			request = (HttpRequest)msg.getProperties().getProperty("org.jboss.soa.esb.http.HttpRequest");
		}
		else
			System.out.println("--> ES ESTA: org.jboss.soa.esb.http.HttpRequest#request");
		if(request != null){
			System.out.println(request.getQueryParams());
			for(Object k : request.getQueryParams().keySet())
				msg.getBody().add(k.toString(),request.getQueryParams().get(k).toString());
		}
		
		Map params = (Map) msg.getProperties().getProperty("RequestParameterMap");
		if(params != null)
			for(Object k : params.keySet())
				msg.getBody().add(k.toString()+"="+params.get(k).toString());
		System.out.println(msg.getProperties()); 
		System.out.println(msg.getHeader());
		return msg;
	}

	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
