/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi2.gr5.ejb;



import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 *
 * @author dell
 */
@Stateless
public class ServicioCompuesto4MdbBean implements ServicioCompuesto4MdbRemote {
    
    
 
  public void EnviarMsgAServicioCompuesto(String msg){


      System.out.println("******************ENTRE");
      (new JmsProducer()).sendMessage("sdf","ConnectionFactory","/queue/Maxi");
    }

 public class JmsProducer {
    private JmsProducer() {}
    public void sendMessage(Serializable payload, String connectionFactoryJndiName,
        String destinationJndiName)  {
        try {
            ConnectionFactory connectionFactory = null;
            Connection connection = null;
            Session session = null;
            Destination destination = null;
            MessageProducer messageProducer = null;
            ObjectMessage message = null;
            System.out.println("In sendMessage of JmsProducter, "
               +" getting ConnectionFactory for jndi name: "+connectionFactoryJndiName );
            connectionFactory = getJmsConnectionFactory(
                                                     connectionFactoryJndiName);

            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = getJmsDestination(destinationJndiName);
            messageProducer = session.createProducer(destination);
            message = session.createObjectMessage(payload);
            messageProducer.send(message);
            System.out.println("Message sent to messageProducer");
            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException je) {
            je.printStackTrace();
        }
    }
}


   public static ConnectionFactory getJmsConnectionFactory(String jmsConnectionFactoryJndiName)
              {
        ConnectionFactory jmsConnectionFactory = null;
        try {
            Context ctx = new InitialContext();
            jmsConnectionFactory = (ConnectionFactory) ctx.lookup(jmsConnectionFactoryJndiName);
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return jmsConnectionFactory;
    }
    public static Destination getJmsDestination(String jmsDestinationJndiName)
             {
        Destination jmsDestination = null;
        try {
            Context ctx = new InitialContext();
            jmsDestination = (Destination) ctx.lookup(jmsDestinationJndiName);
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return jmsDestination;
    }

}
