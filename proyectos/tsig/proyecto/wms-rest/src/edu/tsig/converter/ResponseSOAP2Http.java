package edu.tsig.converter;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class ResponseSOAP2Http implements ActionPipelineProcessor {

	public ResponseSOAP2Http(ConfigTree config){
		
	}
	
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public Message process(Message msg) throws ActionProcessingException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = (HashMap<String, Object>) msg.getBody().get("response");
		
		msg.getBody().add(params.get("getFeatureInfoResponse.return"));
		return msg;
	}

	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
