<%-- 
    Document   : index
    Created on : 02-sep-2009, 19:07:03
    Author     : dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.*, javax.rmi.PortableRemoteObject,
edu.tsi2.gr5.ejb.*, javax.ejb.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Laboratorio tsi2</title>
    </head>
    <body>

        <h2>
	  <%
                Parte1StatefulRemote parte1Stateful = null;

                   try{
                       //obtain an instance of the home interface
                       InitialContext cntxt = new InitialContext( );
                       parte1Stateful =
                               (Parte1StatefulRemote)cntxt.lookup("Stateful");
                       //parte1Stateful = (Parte1StatefulRemote)PortableRemoteObject.narrow(ref,Parte1StatefulRemote.class);
                       HttpSession sesion = request.getSession(true);
                       sesion.setAttribute("ejb",parte1Stateful);

                     String fechaInicio = parte1Stateful.iniciarSesion();
                     out.println("Sesion iniciada el: "+ fechaInicio);
                   }
                   catch(Exception e){
                    e.printStackTrace();
                   }

            %>

        </h2>

        <h1>Parte1</h1>
    <%-- start web service invocation --%><hr/>
    <%
    try {
	edu.tsi2.gr5.ws.ServicioCompuestoWSService service = new edu.tsi2.gr5.ws.ServicioCompuestoWSService();
	edu.tsi2.gr5.ws.ServicioCompuestoWS port = service.getServicioCompuestoWSPort();
	 // TODO initialize WS operation arguments here
	java.lang.String arg0 = "11400"; // la zona de casa
	// TODO process result here
	java.lang.String result = port.operationCompuesta1(arg0);
	out.println("Result = "+result);
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
    <%-- end web service invocation --%><hr/>
<h1>Parte2</h1>
            <%-- start web service invocation --%><hr/>
    <%
    try {
	edu.tsi2.gr5.ws.ServicioCompuestoWSService service = new edu.tsi2.gr5.ws.ServicioCompuestoWSService();
	edu.tsi2.gr5.ws.ServicioCompuestoWS port = service.getServicioCompuestoWSPort();
	// TODO process result here
	java.lang.String result = port.servicioCompuesto1();
	out.println("Result = "+result);
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
    <%-- end web service invocation --%><hr/>


    <h1>Suma y Multiplicacion</h1>
    

    <%-- start web service invocation --%><hr/>
    <%

    try {
         int arg0 = 2;
	int arg1 = 3;
	edu.tsi2.gr5.ws.ServicioCompuestoWSService service = new edu.tsi2.gr5.ws.ServicioCompuestoWSService();
	edu.tsi2.gr5.ws.ServicioCompuestoWS port = service.getServicioCompuestoWSPort();
	 // TODO initialize WS operation arguments here
	
	// TODO process result here
	java.lang.String result = port.sumaMultiplicacion(arg0, arg1);
	out.println("Result = "+result);
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
    <%-- end web service invocation --%><hr/>

     <h1>Suma Resta y Multiplicacion</h1>

         <%-- start web service invocation --%><hr/>
    <%
    try {
        int a = 2;
	int b = 3;
	edu.tsi2.gr5.ws.ServicioCompuestoWSService service = new edu.tsi2.gr5.ws.ServicioCompuestoWSService();
	edu.tsi2.gr5.ws.ServicioCompuestoWS port = service.getServicioCompuestoWSPort();
	 // TODO initialize WS operation arguments here
	// TODO process result here
	java.lang.String result = port.sumaRestaMultiplicacion(a, b);
	out.println("Result = "+result);
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
    <%-- end web service invocation --%><hr/>


    </body>
</html>
