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
        <title>Practico</title>
    </head>
    <body>
        <h1>
	   <%
                 //declare a "global" reference to an instance of the home interface of the session bean
                //@EJB(mappedName="Parte1StatefulRemote")
                Parte1StatefulRemote parte1Stateful = null;

                 
                   try{
                       //obtain an instance of the home interface
                       InitialContext cntxt = new InitialContext( );
                       parte1Stateful =
                               (Parte1StatefulRemote)cntxt.lookup("Stateful");
                       //parte1Stateful = (Parte1StatefulRemote)PortableRemoteObject.narrow(ref,Parte1StatefulRemote.class);
                       HttpSession sesion = request.getSession(true);
                       sesion.setAttribute("ejb",parte1Stateful);

                       //instantiate the session bean
                     //Parte1StatefulBean ejb = parte1Stateful.create();
                     //invoke the remote methods
                     String fechaInicio = parte1Stateful.iniciarSesion();
                     out.println("Sesion iniciada el: "+ fechaInicio);
                   }
                   catch(Exception e){
                    e.printStackTrace();
                   }
               
            %>

        </h1>
        <h1>Parte1</h1>
        <script type="text/javascript" src="/Parte1-war/js/jquerymin.js"></script>
        <!-- script type="text/javascript">
            var auto_refresh = setInterval(
            function ()
            {
            $('#load_tweets').load('record_count.php').fadeIn("slow");
            }, 10000); // refresh every 10000 milliseconds

            <body>
            <div id="load_tweets"> </div>
            </body>

         </script -->
        <input type="button" value="Mostrar" onclick="$('#load_tweets').load('wsInvocacion1.jsp').fadeIn('slow')"/>
        <input type="button" value="Limpiar" onclick="document.getElementById('load_tweets').innerHTML='' "/>
        <hr/>
            <div id="load_tweets"></div>
        <hr/>
  
    
        <h1>Parte2</h1>
        <input type="button" value="Mostrar" onclick="$('#p2').load('wsInvocacion2.jsp').fadeIn('slow')"/>
        <input type="button" value="Limpiar" onclick="document.getElementById('p2').innerHTML='' "/>
        <hr/>
            <div id="p2"></div>
        <hr/>
            
        <h1>Suma y Multiplicacion</h1>

        <input type="text" id="a1" value="2"  />
        <input type="text" id="b1" value="3"  />
        <input type="button" value="Mostrar" onclick="$('#sym').load(urlInvocacion3(document.getElementById('a1').value,document.getElementById('b1').value)).fadeIn('slow')"/>
        <input type="button" value="Limpiar" onclick="document.getElementById('sym').innerHTML='' "/>
        <hr/>
            <div id="sym"></div>
        <hr/>

        <h1>Suma Resta y Multiplicacion</h1>
        <script type="text/javascript">
            
            function urlInvocacion4(vA,vB)
            {
            return 'wsInvocacion4.jsp?a='+vA+'&b='+vB ;
            }

            function urlInvocacion3(vA,vB)
            {
            return 'wsInvocacion3.jsp?a='+vA+'&b='+vB ;
            }

         </script>

        <input type="text" id="a" value="2" onclick="document.getElementById('srym').innerHTML='' "/>
        <input type="text" id="b" value="3" onclick="document.getElementById('srym').innerHTML='' "/>
        <input type="button" value="Mostrar" onclick="$('#srym').load(urlInvocacion4(document.getElementById('a').value,document.getElementById('b').value)).fadeIn('slow')"/>
        <input type="button" value="Limpiar" onclick="document.getElementById('srym').innerHTML='' "/>
        <hr/>
            <div id="srym"></div>
        <hr/>

    <a href="finSesion.jsp">Finalizar sesión.</a>
    </body>
</html>
