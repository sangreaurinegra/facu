/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tsi2.pruebas.webservice.ejb;

import javax.ejb.Stateless;

/**
 *
 * @author Maxi
 */
@Stateless
public class EjbWsPruebaBean implements EjbWsPruebaRemote {
    

    public void hola() {
        System.out.println("HOLA!!!");
    }
    
    public String comprar(String documento){
        System.out.println("Comprando " + documento + " ???");
        return "OK";
    }

    public String reverso() {
        System.out.println("reverso ....");
        return "REVERSO!! OK ...";
    }

    public void consulta() {
        System.out.println(" ... consulta() ...");
    }


}
