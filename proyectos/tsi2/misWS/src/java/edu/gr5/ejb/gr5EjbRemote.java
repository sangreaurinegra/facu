/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gr5.ejb;

import javax.ejb.Remote;

/**
 *
 * @author dell
 */
@Remote
public interface gr5EjbRemote {

    Integer resta(int a, int b);
    
}
