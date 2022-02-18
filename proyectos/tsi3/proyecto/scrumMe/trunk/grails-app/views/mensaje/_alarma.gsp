
<%@ page import="edu.tsi3.scrumme.Mensaje" %>
<div class="dialog">
    <table>
        <tbody>
        	<g:each in="${mensajes }" var="msg">
	            <tr class="prop">
	                
	                <td valign="top" class="value">
	                	<img src="${resource(dir:'images/skin', file:'clock_red.png')}" />
	                	&nbsp;&nbsp;
	                	<g:link action="ver" controller="mensaje" id="${msg.id }" >
	                		${fieldValue(bean: msg, field: "titulo")}
	                	</g:link>
	                	&nbsp;&nbsp;&nbsp;&nbsp;
						
	                </td>
	                <td valign="top" class="value">
	                	<a href="#" onclick="marcarLeido(${msg.id });return false;"  title="Marcar como leido">
							<img src="${resource(dir:'images/skin', file:'email_delete.png')}" />
	                	</a>
	                </td>
	            </tr>
        	</g:each>
        	<g:each in="${invs }" var="inv">
	            <tr class="prop">
	                
	                <td valign="top" class="value">
	                	<img src="${resource(dir:'images/skin', file:'clock.png')}" />
	                	&nbsp;&nbsp;
	                	Reunión: ${fieldValue(bean: inv.reunion, field: "titulo")}
						
	                </td>
	                <td valign="top" class="value">
	                	<a href="#" onclick="aceptarInv(${inv.id });return false;"  title="Aceptar invitación" style="text-decoration: none;">
							<img src="${resource(dir:'images/skin', file:'accept.png')}" />
	                	</a>
	                	|
	                	<a href="#" onclick="rechazarInv(${inv.id });return false;"  title="Rechazar invitación" style="text-decoration: none;">
							<img src="${resource(dir:'images/skin', file:'cancel.png')}" />
	                	</a>
	                </td>
	            </tr>
        	</g:each>
        </tbody>
    </table>
</div>
<script type="text/javascript">
<!--
 function marcarLeido(id){
	$.ajax({
		   type: "GET",
		   url: "${createLink(action:'marcarLeido', controller:'mensaje') }",
		   data: "id="+id,
		   success: function(){
			   $.ajax({
				   type: "GET",
				   url: "${createLink(action:'checkAlarmas', controller:'mensaje') }",
				   data: "",
				   success: function(html){
					   if(html != "no"){
							$("#alarmas").html(html);
							$("#alarmas").get(0).style.display = '';
					   }
					   else{
						   $("#alarmas").get(0).style.display = 'none';
					   }
				   }
				 });
		   }
		 });

 }

function aceptarInv(id){
	$.ajax({
		   type: "GET",
		   url: "${createLink(action:'aceptar', controller:'invitacion') }",
		   data: "id="+id,
		   success: function(){
			   $.ajax({
				   type: "GET",
				   url: "${createLink(action:'checkAlarmas', controller:'mensaje') }",
				   data: "",
				   success: function(html){
					   if(html != "no"){
							$("#alarmas").html(html);
							$("#alarmas").get(0).style.display = '';
					   }
					   else{
						   $("#alarmas").get(0).style.display = 'none';
					   }
				   }
				 });
		   }
		 });

 }

function rechazarInv(id){
	$.ajax({
		   type: "GET",
		   url: "${createLink(action:'rechazar', controller:'invitacion') }",
		   data: "id="+id,
		   success: function(){
			   $.ajax({
				   type: "GET",
				   url: "${createLink(action:'checkAlarmas', controller:'mensaje') }",
				   data: "",
				   success: function(html){
					   if(html != "no"){
							$("#alarmas").html(html);
							$("#alarmas").get(0).style.display = '';
					   }
					   else{
						   $("#alarmas").get(0).style.display = 'none';
					   }
				   }
				 });
		   }
		 });

 }
//-->
</script>

