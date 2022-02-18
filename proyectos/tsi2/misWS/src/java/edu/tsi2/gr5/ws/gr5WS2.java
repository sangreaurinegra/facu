/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ws;

import edu.gr5.ejb.gr5EjbRemote;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author dell
 */
@WebService()
@Stateless()
public class gr5WS2 {
    @EJB
    private gr5EjbRemote ejbRef;
    // Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "resta")
    public Integer resta(@WebParam(name = "a")
    int a, @WebParam(name = "b")
    int b) {
        return ejbRef.resta(a, b);
    }

}
