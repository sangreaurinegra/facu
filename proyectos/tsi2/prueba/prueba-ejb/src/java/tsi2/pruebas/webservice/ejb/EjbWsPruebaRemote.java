/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tsi2.pruebas.webservice.ejb;

import javax.ejb.Remote;

/**
 *
 * @author Maxi
 */
@Remote
public interface EjbWsPruebaRemote {

    public void hola();

    public String comprar(String documento);

    public String reverso();

    void consulta();
    
}
