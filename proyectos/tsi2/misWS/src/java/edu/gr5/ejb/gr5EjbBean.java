/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gr5.ejb;

import javax.ejb.Stateless;

/**
 *
 * @author dell
 */
@Stateless
public class gr5EjbBean implements gr5EjbRemote {

    public Integer resta(int a, int b) {
        return a-b;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")



}
