/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.mdb;

import edu.tsi2.gr5.ejb.ServicioCompuestoBean;
import edu.tsi2.gr5.ejb.ServicioCompuestoRemote;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.InitialContext;

/**
 *serviciosws
 * @author dell
 */
@MessageDriven(name = "ConsumidorMDBBean", activationConfig =  {

        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/Maxi")

    })
public class ConsumidorMDBBean implements MessageListener {

    

    public ConsumidorMDBBean() {
    }

    public void onMessage(Message message) {
        System.out.println("********** Llegue a ConsumidorMDBBean "+message);

         ServicioCompuestoRemote sc  = null;
        String res ="";
                   try{
                       //obtain an instance of the home interface
                       InitialContext cntxt = new InitialContext( );
                       sc =  (ServicioCompuestoRemote)cntxt.lookup("ServicioCompuestoBean/remote");
                       res = sc.sumaRestaMultiplicacion(3, 4);
                   }
                   catch(Exception e){
                    e.printStackTrace();
                   }

                    System.out.println("Resultado "+res);

    }
    
}
