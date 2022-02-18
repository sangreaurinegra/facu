/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tsi2.pruebas.webservice.ws;

import tsi2.pruebas.webservice.ejb.*;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.ejb.Remote;

/**
 *
 * @author Maxi
 */
@WebService()
@Stateless()
@Remote(EjbWsPruebaRemote.class)
public class WsPrueba {
    @EJB
    private EjbWsPruebaRemote ejbRef;
    // Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "hola")
    @Oneway
    public void hola() {
        ejbRef.hola();
    }

    @WebMethod(operationName = "comprar")
    public String comprar(@WebParam(name = "documento")
    String documento) {
        return ejbRef.comprar(documento);
    }

    @WebMethod(operationName = "reverso")
    public String reverso() {
        return ejbRef.reverso();
    }

    @WebMethod(operationName = "consulta")
    @Oneway
    public void consulta() {
        ejbRef.consulta();
    }

}
