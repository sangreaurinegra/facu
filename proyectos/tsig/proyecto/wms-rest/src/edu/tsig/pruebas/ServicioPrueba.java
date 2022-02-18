package edu.tsig.pruebas;

import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.actions.annotation.BodyParam;
import org.jboss.soa.esb.actions.annotation.Process;
import org.jboss.soa.esb.actions.annotation.PropertyParam;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.lifecycle.annotation.Initialize;
import org.jboss.soa.esb.listeners.ListenerTagNames;

import com.thoughtworks.xstream.XStream;

public class ServicioPrueba {

	private Service service;

    @Initialize
    public void init(ConfigTree config) {
        service = new Service(config.getParent().getAttribute(ListenerTagNames.SERVICE_CATEGORY_NAME_TAG), config.getParent().getAttribute(ListenerTagNames.SERVICE_NAME_TAG));
    }

    @Process
    public String printHttpRequestInfo(@BodyParam byte[] httpPayload, @PropertyParam HttpRequest requestInfo ) {

        System.out.println("&&&&&&&&&&&&&&&& MyAction &&&&&&&&&&&&&&&&&&&&&");
        System.out.println("");
        System.out.println("Service: " + service);
        System.out.println("");
        System.out.println("------------Http Request Info (XStream Encoded)-------------------");
        String requestInfoXML;

        XStream xstream = new XStream();
        requestInfoXML = xstream.toXML(requestInfo);

        System.out.println(requestInfoXML);

        System.out.println("------------Http Request body -------------------");
		System.out.println(httpPayload);

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

        StringBuilder response = new StringBuilder();

        response.append("Service: " + service + "\n\n");
        response.append("------------Http Request Info (XStream Encoded)-------------------\n");
        response.append(requestInfoXML);

        return response.toString();
	}
	
}
