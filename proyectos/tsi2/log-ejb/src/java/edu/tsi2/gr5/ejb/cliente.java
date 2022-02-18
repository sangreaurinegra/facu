/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;

import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 *
 * @author dell
 */
public class cliente {

   public static void main(String[] args) throws NamingException{

       System.out.println("Inicio Cliente");

       InitialContext ctx = new InitialContext();

       LogActividadesFacadeRemote ejb = (LogActividadesFacadeRemote) ctx.lookup("LogActividadesFacade/remote");

       ejb.loguear("ws","oper", "1|2|3", 1, 0);

       

   }

}
