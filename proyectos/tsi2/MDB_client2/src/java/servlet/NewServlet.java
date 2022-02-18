/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.logging.*;
import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Maxi
 */
public class NewServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            
            String ws = request.getParameter("ws");
            String mensaje = ws;
            (new JmsProducer()).sendMessage(mensaje,"ConnectionFactory","/queue/Maxi",request.getSession());
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Mande mensaje "+ws+" </h1>");

            out.println("<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js\"></script>"+
            "<script type=\"text/javascript\">"+
            "var auto_refresh = setInterval("+
            "function ()"+
            "{"+
            "$('#load_tweets').load('RespuestaServlet').fadeIn(\"slow\");"+
            "}, 1000); "+
            "</script>"+
            "<div id=\"load_tweets\"> </div>");
            out.println("</body>");
            out.println("</html>");
            
        } finally { 
            out.close();
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





























    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    /*
                            @Resource(mappedName = "/queue/DLQ")
                        private Queue mensajes;
                        @Resource(mappedName = "DLQFactory")
                        private ConnectionFactory mensajesFactory;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Connection connection = null;
            Session session = null;
            try {

                connection = mensajesFactory.createConnection();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer messageProducer = session.createProducer(mensajes);
                TextMessage tm = session.createTextMessage();
                tm.setText("Un mensaje");
                messageProducer.send(tm);
            } catch (JMSException ex) {
                Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
             } finally {
                if (session != null) {
                    try {
                        session.close();
                    } catch (JMSException e) {
                        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (JMSException ex) {
                        Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                            }
                 }
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at  envio bien </h1>");
            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }

     */
}
