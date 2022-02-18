/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parte4;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.jms.*;
import javax.naming.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.icefaces.application.showcase.view.bean.BaseBean;

/**
 *
 * @author Maxi
 */
public class MandarMensajeBean extends BaseBean {


    private int a;
    private int b;
    private int c;
    private int d;
    private String mensaje;
    private String respuesta = null;
    private String respuesta2 = null;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int a) {
        this.c = a;
    }

    public int getD() {
        return d;
    }

    public void setD(int b) {
        this.d = b;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


     public void submitButtonListener(ActionEvent e) {
        mandarMensaje("1");
        valueChangeEffect.setFired(false);
    }

     public void submitButtonListener2(ActionEvent e) {
        mandarMensaje("2");
        valueChangeEffect.setFired(false);
    }

    public void setRespuesta(String res){
        respuesta = "mensaje enviado";
    }

     public String getRespuesta(){
        if (respuesta == null)
            return "";
        return respuesta;
    }

     public void setRespuesta2(String res){
        respuesta2 = "mensaje enviado";
    }

     public String getRespuesta2(){
        if (respuesta2 == null)
            return "";
        return respuesta2;
    }


    private void mandarMensaje(String ws){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
      	HttpSession httpSession = request.getSession(false);

        if (ws.equalsIgnoreCase("1"))
            mensaje = ws +"-"+ a +"-"+ b;
        else
            mensaje = ws +"-"+ c +"-"+ d;
        (new JmsProducer()).sendMessage(mensaje,"ConnectionFactory","/queue/Maxi",httpSession);

        // si todo sale bien
        if (ws.equalsIgnoreCase("1"))
            setRespuesta("");
        else
            setRespuesta2("");
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


    public class JmsProducer {
        private JmsProducer() {}
        public void sendMessage(Serializable payload, String connectionFactoryJndiName,
            String destinationJndiName, HttpSession sesion)  {
            try {
                ConnectionFactory connectionFactory = null;
                Connection connection = null;
                Session session = null;
                Destination destination = null;
                MessageProducer messageProducer = null;
                TextMessage message = null;
                System.out.println("In sendMessage of JmsProducter, "
                   +" getting ConnectionFactory for jndi name: "+connectionFactoryJndiName );
                connectionFactory = getJmsConnectionFactory(
                                                         connectionFactoryJndiName);

                connection = connectionFactory.createConnection();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                destination = getJmsDestination(destinationJndiName);
                messageProducer = session.createProducer(destination);
                message = session.createTextMessage((String) payload);
                TemporaryQueue tempQueue = session.createTemporaryQueue();
                message.setJMSReplyTo(tempQueue);

                messageProducer.send(message);
                System.out.println("Message sent to messageProducer");
                messageProducer.close();
                //session.close();
                //connection.close();
                //MessageConsumer consumer = session.createConsumer(tempQueue);

                 //Message   res = consumer.receive(10000);
                 //System.out.println(res.toString());
                sesion.setAttribute("conexion", connection);
                sesion.setAttribute("session", session);
                sesion.setAttribute("tempQueue", tempQueue);
            } catch (JMSException je) {
                je.printStackTrace();
            }
        }
    }
}
