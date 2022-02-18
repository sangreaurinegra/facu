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
public interface ServicioCompuesto4MdbRemote {

    void EnviarMsgAServicioCompuesto(String msg);
    
}
