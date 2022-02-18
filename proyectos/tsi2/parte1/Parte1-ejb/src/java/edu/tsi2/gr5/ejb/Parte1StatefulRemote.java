/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;

import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author Maxi
 */
@Remote
public interface Parte1StatefulRemote {

    
     String iniciarSesion() throws RemoteException;

     String cerrarSesion() throws RemoteException;

     void setInvocacion(String wsName) throws RemoteException;
    
}
