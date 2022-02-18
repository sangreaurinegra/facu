
<%@ page import="edu.tsi3.scrumme.Sprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sprint.label', default: 'Sprint')}" />
        <title>Dashboard</title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
            <g:each in="${todo}" var="t">
				<scrumme:draggable tareaid="${t.id}" />
			</g:each>
			<g:each in="${working}" var="t">
				<scrumme:draggable tareaid="${t.id}" />
			</g:each>
			<g:each in="${ready}" var="t">
				<scrumme:draggable tareaid="${t.id}" />
			</g:each>
			<scrumme:droppable url="${createLink(action:'cambioEstado', controller:'tarea') }" panelid="ptodo"></scrumme:droppable>
			<scrumme:droppable url="${createLink(action:'cambioEstado', controller:'tarea') }" panelid="pworking"></scrumme:droppable>
			<scrumme:droppable url="${createLink(action:'cambioEstado', controller:'tarea') }" panelid="pready"></scrumme:droppable>
		<%-- <script>
		  $(document).ready(function(){
		    $('#switcher').themeswitcher();
		  });
		</script>
		<script type="text/javascript"
		  src="http://jqueryui.com/themeroller/themeswitchertool/">
		</script>
		<div id="switcher"></div>
		
		<hr>
		--%>
		<div class="demo">
		
		<div id="dialog" title="Detalle de tarea"></div>
		<div id="dialogEdit" title="Editar tarea" style="width: 383px"></div>
		<script type="text/javascript">
			$(function() {
				$("#dialog").dialog({ autoOpen: false });
				$("#dialogEdit").dialog({ autoOpen: false, height: 347, width: 420 });
			});
			function mostrar(id){ 
				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'detalles', controller:'tarea') }",
					   data: "tarea="+id,
					   success: function(html){
						$("#dialog").html(html);
						$("#dialog").dialog('open');
					   }
					 });
				return false;
			}
			function mostrarEdit(id){ 
				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'dialogoedit', controller:'tarea') }",
					   data: "id="+id,
					   success: function(html){
						$("#dialogEdit").html(html);
						$("#btn_update").button({
				            icons: {
			                	primary: 'ui-icon-disk'
			            	}
			            });
						$("#btn_cerrar").button({
				            icons: {
			                	primary: 'ui-icon-close'
			            	}
			            });
						$("#dialogEdit").dialog('open');
					   }
					 });
				return false;
			}
		</script>
		<script type="text/javascript">
			$(function() {
				$("#addtarea").button({
		            icons: {
	                	primary: 'ui-icon-circle-plus'
	            	}
	            });
				$("#chg_sp").button({
		            icons: {
	                	primary: 'ui-icon-power'
	            	}
	            });
				$("#resumen").button({
		            icons: {
	                	primary: 'ui-icon-note'
	            	}
	            });
				$("#ver_u").button({
		            icons: {
	                	primary: 'ui-icon-play'
	            	},text:false
	            });
				$("#reset_sp").button({
		            icons: {
	                	primary: 'ui-icon-arrowrefresh-1-s'
	            	},text:false
	            });
			});
			function ver_usuario(){
				var url = "${createLink(action:'dashboard', controller:'sprint',id:params.id) }";
				url += '?u=';
				url += $('#usuarios').val();
				window.location = url;
			}
		</script>
		
		<div class="toolbar">
			<g:if test="${ sprint.estado != Sprint.ESTADO_FINALIZADO }">
				<a id="addtarea" href="${createLink(action:'create', controller:'tarea',params:[sprintid:params.id]) }">Tarea</a>
			</g:if>
			<g:if test="${ sprint.estado == Sprint.ESTADO_NO_INICIADO }">
				<a id="chg_sp" href="${createLink(action:'ini', controller:'sprint',params:[id:params.id]) }">Iniciar</a>
			</g:if>
			<g:elseif test="${ sprint.estado == Sprint.ESTADO_DESA }">
				<a id="chg_sp" href="${createLink(action:'fin', controller:'sprint',params:[id:params.id]) }">Finalizar</a>
			</g:elseif>
			<a id="resumen" href="${createLink(action:'resumen', controller:'proyecto',id:sprint.proyecto.id) }">Resúmen de proyecto</a>
			<div style="float: right;">
				<g:select id="usuarios" from="${usuarios}" optionKey='id' optionValue='userRealName' value="${params.u }" />
				<a id="ver_u" onclick="ver_usuario()">Ver</a>
				<a id="reset_sp" href="${createLink(action:'dashboard', controller:'sprint',params:[id:params.id]) }">Reset</a>
			</div>
		</div>
		<div class="demo">
			
			<div id="ptodo" class="ui-widget-header droppable" style="position: relative">
				<p>back log</p>
				
				<%
					def offsetx = 0
					def offsety = 0
					def anchoD = grailsApplication.config.anchoDashboard
					def ancho = grailsApplication.config.anchoTarea
				%>
				<g:each in="${todo}" var="t">
					<%//al x le sumo el ancho del cuadrado
						if(t.calcularX(offsetx)+ancho -grailsApplication.config.inibacklog >= anchoD){
							offsetx = 0
						}
					%>
					<scrumme:tareaDraggable tareaid="${t.id}" x="${ t.calcularX(offsetx) }" y="${ t.calcularY(offsety) }" />
					<%//aumento los offset
							offsetx += grailsApplication.config.offsetbasico
							offsety += grailsApplication.config.offsetbasico
					%>
				</g:each>
			</div>
			<div id="pworking" class="ui-widget-header droppable" style="position: relative">
				<p>En proceso</p>
				
				<%
					offsetx = 0
					offsety = 0
				%>
				<g:each in="${working}" var="t">
					<%//al x le sumo el ancho del cuadrado
						if(t.calcularX(offsetx)+ancho -grailsApplication.config.iniworking >= anchoD){
							offsetx = 0
						}
					%>
					<scrumme:tareaDraggable tareaid="${t.id}" x="${ t.calcularX(offsetx) }" y="${ t.calcularY(offsety) }"/>
					<%//aumento los offset
							offsetx += grailsApplication.config.offsetbasico
							offsety += grailsApplication.config.offsetbasico
					%>
				</g:each>
			</div>
			<div id="pready" class="ui-widget-header droppable" style="position: relative">
				<p>Finalizado</p>
				
				<%
					offsetx = 0
					offsety = 0
				%>
				<g:each in="${ready}" var="t">
					<%//al x le sumo el ancho del cuadrado
						if(t.calcularX(offsetx)+ancho -grailsApplication.config.iniready >= anchoD){
							offsetx = 0
						}
					%>
					<scrumme:tareaDraggable tareaid="${t.id}" x="${ t.calcularX(offsetx) }" y="${ t.calcularY(offsety) }" />
					<%//aumento los offset
							offsetx += grailsApplication.config.offsetbasico
							offsety += grailsApplication.config.offsetbasico
					%>
				</g:each>
			</div>
			
		</div>
        </div>
        </div>
        </div>
        <script type="text/javascript">
	        function updateFunc(id){
	        	var url = "${createLink(action:'dashboard', controller:'sprint', id:params.id) }";
				$.post("${createLink(action:'update',controller:'tarea')}",
					   { 'descripcion': $('#descripcion').val(), 'nombre': $('#nombre').val(), 'tipo.id': $('#tipo').val(),'id':id, 'estimacion': $('#estimacion').val() },
					   	function(data){
						   $("#dialogEdit").dialog('close');
						   var ref = $('#refrescar_dashboard').val();
						   if(ref == 'on' || ref == 'checked')
							   window.location = url;
						}
					);
			}
		</script>
    </body>
</html>
