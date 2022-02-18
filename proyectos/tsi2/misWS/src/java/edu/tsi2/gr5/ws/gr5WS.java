/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ws;

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
public class gr5WS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "suma")
    public Integer suma(@WebParam(name = "a")
    int a, @WebParam(name = "b")
    int b) {
        //TODO write your implementation code here:
        return a+b;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "producto")
    public Integer producto(@WebParam(name = "a")
    int a, @WebParam(name = "b")
    int b) {
        //TODO write your implementation code here:

        return a*b;
    }

    
}
