<%-- 
    Document   : finSesion
    Created on : 05-sep-2009, 11:46:04
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
        <h1>Fin de la sesion</h1>
        <br>
        Descripcion de los web services invocados.
        <br>
        <table border="2" >
            <tr>
                <th>Web Service</th>
                <th>Cant Invocaciones</th>
            </tr>
            <%
                 Parte1StatefulRemote parte1Stateful;


                   try{

                       HttpSession sesion = request.getSession(true);
                       parte1Stateful = (Parte1StatefulRemote)sesion.getAttribute("ejb");

                       //instantiate the session bean
                     //Parte1StatefulBean ejb = parte1Stateful.create();
                     //invoke the remote methods
                     String fin = parte1Stateful.cerrarSesion();
                     out.println(fin);
                   }
                   catch(Exception e){
                    e.printStackTrace();
                   }

            %>
        </table>
    </body>
</html>
