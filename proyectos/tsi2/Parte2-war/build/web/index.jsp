<%-- 
    Document   : index
    Created on : 06-sep-2009, 16:18:35
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
        <title>Parte 2</title>
    </head>
    <body>
        <h1>Llamo a Ejb para que mande Mensaje</h1>


         <%

          ServicioCompuesto4MdbRemote sc4mdb  = null;

                   try{
                       //obtain an instance of the home interface
                       InitialContext cntxt = new InitialContext( );
                       sc4mdb =  (ServicioCompuesto4MdbRemote)cntxt.lookup("ServicioCompuesto4MdbBean/remote");

                       sc4mdb.EnviarMsgAServicioCompuesto("hola desde jsp");
                   }
                   catch(Exception e){
                    e.printStackTrace();
                   }


            %>

    </body>
</html>
