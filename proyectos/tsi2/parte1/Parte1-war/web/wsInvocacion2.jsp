<%-- 
    Document   : wsInvocacion2
    Created on : 05-sep-2009, 13:41:43
    Author     : Maxi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.*, javax.rmi.PortableRemoteObject,
edu.tsi2.gr5.ejb.*, javax.ejb.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <%
    try {
	edu.tsi2.gr5.ws.ServicioCompuestoWSService service = new edu.tsi2.gr5.ws.ServicioCompuestoWSService();
	edu.tsi2.gr5.ws.ServicioCompuestoWS port = service.getServicioCompuestoWSPort();
	// TODO process result here
	java.lang.String result = port.servicioCompuesto1();
	out.println("Result = "+result);
        HttpSession sesion = request.getSession(true);
        Parte1StatefulRemote parte1Stateful = (Parte1StatefulRemote)sesion.getAttribute("ejb");

        parte1Stateful.setInvocacion("servicioCompuesto1");
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
    </body>
</html>
