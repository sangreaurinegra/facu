/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ws;


import edu.tsi2.gr5.ejb.ServicioCompuestoRemote;
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
public class ServicioCompuestoWS {

   
    @EJB
    private ServicioCompuestoRemote ejbRef;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "servicioCompuesto1")
    public String servicioCompuesto1() {
        //TODO write your implementation code here:
        return "hola desde ws compuesto";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "operationCompuesta1")
    public String operationCompuesta1(String zipCode) {
        //TODO write your implementation code here:
        
        return ejbRef.estadoDelTiempo(zipCode);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "sumaMultiplicacion")
    public String sumaMultiplicacion(int a, int b) {
        return ejbRef.sumaMultiplicacion(a, b);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "sumaRestaMultiplicacion")
    public String sumaRestaMultiplicacion(@WebParam(name = "a")
    int a, @WebParam(name = "b")
    int b) {
        //TODO write your implementation code here:
        return ejbRef.sumaRestaMultiplicacion(a, b);
    }

    
    
    // Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

}
