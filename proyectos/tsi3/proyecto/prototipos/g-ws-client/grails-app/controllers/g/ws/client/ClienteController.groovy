package g.ws.client

import org.grails.plugins.wsclient.service.WebService

class ClienteController {

	WebService webService
	
	//String wsdlURL = "http://10.200.8.49:8090/grails-ws/services/maxi?wsdl"
	String wsdlURL = "http://10.200.8.49:8090/ws-xfire/services/maxi?wsdl"
	
    def s1 = {
			def proxy = webService.getClient(wsdlURL)

			def result = proxy.servicio1() 
			render result
	}
    
    def s2 = {
			def proxy = webService.getClient(wsdlURL)

			def result = proxy.servicio2()
			render result
	}
	
	 def index = { 
			wsdlURL = "http://www.w3schools.com/webservices/tempconvert.asmx?WSDL"  
			def proxy = webService.getClient(wsdlURL)

			def result = proxy.CelsiusToFahrenheit(0) 
			result = "You are probably freezing at ${result} degrees Farhenheit" 
			render result 
		} 
}
