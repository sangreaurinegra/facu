/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import edu.tsi2.gr5.ejb.ServicioCompuestoBean;
import edu.tsi2.gr5.ejb.ServicioCompuestoRemote;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Maxi
 */
@MessageDriven(name = "NewMessageBean", activationConfig =  {
        
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/Maxi")

    })
public class NewMessageBean implements MessageListener {
    
    //@EJB(name="ServicioCompuestoBean/remote-edu.tsi2.gr5.ejb.ServicioCompuestoRemote")
    ServicioCompuestoRemote scb;

    public NewMessageBean() {
        try {
            Context ctx = new InitialContext();
            scb = (ServicioCompuestoRemote) ctx.lookup("ServicioCompuestoBean/remote");
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }

    @Resource(mappedName="ConnectionFactory")
    ConnectionFactory myQueueFactory;

    public void onMessage(Message message) {
        System.out.println("Llego mensaje");
        TextMessage mensaje = (TextMessage) message ;
        String respuesta = "";

        try {
            StringTokenizer tok = new StringTokenizer(mensaje.getText(), "-");
            String ws = tok.nextToken();
            String sa = tok.nextToken();
            String sb = tok.nextToken();
            int a = Integer.parseInt(sa);
            int b = Integer.parseInt(sb);
            if (ws.equalsIgnoreCase("1")) {
                respuesta = scb.sumaMultiplicacion(a, b);
                System.out.println(respuesta);
            }
            else if(ws.equalsIgnoreCase("2")) {
                respuesta = scb.sumaRestaMultiplicacion(a, b);
                System.out.println(respuesta);
            }
            if (message.getJMSReplyTo() != null) {
                System.out.println("mande respuesta");
               Connection conn = myQueueFactory.createConnection();
               Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
               MessageProducer producer = session.createProducer(message.getJMSReplyTo());
               producer.send(session.createTextMessage(respuesta));
               producer.close();
               session.close();
               conn.close();
           }
            else {
            System.out.println("no mande respuesta");
            }
        } catch (JMSException ex) {
            Logger.getLogger(NewMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
