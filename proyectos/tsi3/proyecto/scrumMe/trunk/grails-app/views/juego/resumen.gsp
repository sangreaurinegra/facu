
<%@ page import="edu.tsi3.planningpoker.Juego" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'juego.label', default: 'Juego')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <a id="create" href="${ createLink(action:'create', controller:'juego') }">Crear juego</a>
        </div>
        <div class="body">
            <h1>Resumen Juegos</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
            	<h2>Por iniciar</h2>
            	<div id="acordion">
	            	<g:each in="${iniciar }" var="juego">
	            		<h3><a>${juego.nombre }</a></h3>
	            		<div>
			                <a id="inijuego_${juego.id }" href="${ createLink(action:'iniciar', controller:'juego',id:juego.id) }">
		       					Iniciar juego
		       				</a> 
		       				<script type="text/javascript">
								$(function() {
									$("#inijuego_${juego.id }").button({
							            icons: {
						                	primary: 'ui-icon-power'
						            	}
						            });
								});
							</script>
	            		</div>
	            	</g:each>
            	</div>
            	
            	<h2>Activos</h2>
            	<div id="acordion2">
	            	<g:each in="${activos }" var="juego">
	            		<h3><a>${juego.nombre }</a></h3>
	            		<div>
	            			<h4>Manos</h4>
	            			<table>
			                    <thead>
			                        <tr>
			                        
			                            <th>Tarea</th>
			                            <th>Estado</th>
			                            <th>Estimación</th>
			                   	    
			                        </tr>
			                    </thead>
			                    <tbody>
			                    <g:each in="${juego.manos}" status="i" var="mano">
			                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

			                            <td>${fieldValue(bean: mano.tarea, field: "nombre")}</td>
			                        
			                            <td>${mano.textoEstado()}</td>
			                        
			                            <td>${fieldValue(bean: mano, field: "estimacion")}</td>
			                        
			                        </tr>
			                    </g:each>
			                    </tbody>
			                </table>
			                <a id="inijuego_${juego.id }" href="${ createLink(action:'juego', controller:'juego',id:juego.id) }">
		       					Ir al juego
		       				</a> 
		       				<script type="text/javascript">
								$(function() {
									$("#inijuego_${juego.id }").button({
							            icons: {
						                	primary: 'ui-icon-arrowthick-1-e'
						            	}
						            });
								});
							</script>
	            		</div>
	            	</g:each>
            	</div>
            	<h2>Finalizados</h2>
            	<div id="acordion3">
	            	<g:each in="${finalizados }" var="juego">
	            		<h3><a>${juego.nombre }</a></h3>
	            		<div>
	            			<h4>Manos</h4>
	            			<table>
			                    <thead>
			                        <tr>
			                        
			                            <th>Tarea</th>
			                            <th>Estado</th>
			                            <th>Estimación</th>
			                   	    
			                        </tr>
			                    </thead>
			                    <tbody>
			                    <g:each in="${juego.manos}" status="i" var="mano">
			                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

			                            <td>${fieldValue(bean: mano.tarea, field: "nombre")}</td>
			                        
			                            <td>${mano.textoEstado()}</td>
			                        
			                            <td>${fieldValue(bean: mano, field: "estimacion")}</td>
			                        
			                        </tr>
			                    </g:each>
			                    </tbody>
			                </table>
	            		</div>
	            	</g:each>
            	</div>
            	<script type="text/javascript">
					$(function() {
						$("#acordion").accordion();
						$("#acordion2").accordion();
						$("#acordion3").accordion();
						$("#create").button({
				            icons: {
			                	primary: 'ui-icon-circle-plus'
			            	}
			            });
					});
				</script>
			</div>
        </div>
    </body>
</html>
