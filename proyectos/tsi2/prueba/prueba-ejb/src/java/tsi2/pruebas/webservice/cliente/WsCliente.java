    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tsi2.pruebas.webservice.cliente;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.Service;

import tsi2.pruebas.webservice.ejb.EjbWsPruebaRemote;

/**
 *
 * @author Maxi
 */
public class WsCliente {


    public static void main(String [] parametros) throws MalformedURLException, ServiceException {
        System.out.println("Iniciando WsCliente");
        // Servidor - nombre del jar (??) - nombre de la clase que tiene la anotación @Webservice.
        URL urlWs = new URL("http://localhost:8080/prueba-ejb/WsPrueba?wsdl");

        QName qname = new QName("http://webservice.castolo.com","WsPruebaService");

        ServiceFactory sf = ServiceFactory.newInstance();
        Service s = sf.createService(urlWs,qname);

        EjbWsPruebaRemote proxy = (EjbWsPruebaRemote) s.getPort(EjbWsPruebaRemote.class);

        System.out.println("Accediendo ws");
        proxy.hola();

        


    }


}
