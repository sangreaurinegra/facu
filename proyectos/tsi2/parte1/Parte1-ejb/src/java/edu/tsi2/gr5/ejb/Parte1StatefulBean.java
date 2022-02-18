/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 *
 * @author Maxi
 */
@Stateful(mappedName="Stateful")
public class Parte1StatefulBean implements Parte1StatefulRemote {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
    Map<String,Integer> invocados;


    public String iniciarSesion(){
        invocados = new TreeMap<String, Integer>();
        return (new Date()).toString();
    }

    @Remove
    public String cerrarSesion(){
        String resultado = "";
        Set<String> keys = invocados.keySet();
        for(Iterator<String> it = keys.iterator(); it.hasNext();){
            String ws = it.next();
            resultado += "<tr><td>"+ws + "</td><td>"+invocados.get(ws) + "</td></tr>";
        }
        return resultado;
    }

    public void setInvocacion(String wsName) throws RemoteException {
        try{
            Integer cant = invocados.get(wsName);
            if (cant != null){
                cant ++;
            }
            else{
                cant = new Integer(1);
            }
            invocados.put(wsName, cant);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
