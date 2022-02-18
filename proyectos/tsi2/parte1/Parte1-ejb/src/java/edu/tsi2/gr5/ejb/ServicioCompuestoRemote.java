/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;

import javax.ejb.Remote;

/**
 *
 * @author dell
 */
@Remote
public interface ServicioCompuestoRemote {
    
    String operacion1();

    public String estadoDelTiempo(String zipCode);

    public String sumaMultiplicacion(int a, int b);

    public String sumaRestaMultiplicacion(int a, int b);
}
