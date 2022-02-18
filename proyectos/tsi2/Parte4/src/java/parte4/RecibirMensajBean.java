/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parte4;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RecibirMensajBean extends BaseBean {


    private String respuesta = null;

    public RecibirMensajBean(){
        System.out.println("Bean creado!!");
        recibir();
        System.out.println("Recibido!!");
    }

    public void setRespuesta(String res){
        respuesta = res;
    }

     public String getRespuesta(){
        if (respuesta == null)
            return "";
        return respuesta;
    }

     public void submitButtonListener(ActionEvent e) {
        //mandarMensaje();
        valueChangeEffect.setFired(false);
    }


     private void recibir(){
         respuesta = "<h1>Respuesta NO HA LLEGADO</h1>";
         Message res = null;
         MessageConsumer consumer =null;
            try {
                HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
                HttpSession sesion = request.getSession(false);
                if (sesion != null ){
                    Connection connection = (Connection)sesion.getAttribute("conexion");
                    Session session = (Session)sesion.getAttribute("session");
                    TemporaryQueue tempQueue = (TemporaryQueue)sesion.getAttribute("tempQueue");
                    if (connection != null && session!= null && tempQueue!= null){
                        connection.start();
                         consumer = session.createConsumer(tempQueue);

                        res = consumer.receive(10000);
                    }
                    else
                        System.out.println("falta cosas");
                }
                else{
                    System.out.println("no hay sesion");
                }
                if (res != null) {
               System.out.println("Response received");
           } else {
               System.out.println("Response not received due to timeout");
           }
           if (consumer != null)
                consumer.close();
            } catch (JMSException ex) {
                Logger.getLogger(RecibirMensajBean.class.getName()).log(Level.SEVERE, null, ex);
            }

         try {
            if (res != null)
                respuesta = "<h1>Respuesta recibida: " + ((TextMessage) res).getText() + "</h1>";
            else
                respuesta = "<h1>Respuesta NO HA LLEGADO</h1>";
        } catch (JMSException ex) {
            Logger.getLogger(RecibirMensajBean.class.getName()).log(Level.SEVERE, null, ex);
        }

     }

}
