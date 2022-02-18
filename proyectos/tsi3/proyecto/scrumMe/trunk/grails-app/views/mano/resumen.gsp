
<%@ page import="edu.tsi3.planningpoker.Mano" %>
<%@ page import="edu.tsi3.planningpoker.CartaUsuario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'mano.label', default: 'Mano')}" />
        <title>Resumen: ${juego.nombre }</title>
    </head>
    <body>
        <div class="body">
            <h1>Resumen: ${juego.nombre }</h1>
            <div class="dialog">
            	<div id="acordion">
	            	<g:each in="${juego.manos }" var="mano">
	            		<g:if test="${mano.estado == Mano.ESTADO_EN_JUEGO  }">
	            			<h3><a>${mano.tarea.nombre } : En Juego!</a></h3>
	            		</g:if>
	            		<g:else>
	            			<h3><a>${mano.tarea.nombre } : ${mano.estimacion } horas</a></h3>
	            		</g:else>
	            		<div>
	            			<g:if test="${mano.estado != Mano.ESTADO_EN_JUEGO  }">
		            			<%
		            				def jugadas = CartaUsuario.findAllByMano(mano)
		            			%>
		            			<table>
				                    <thead>
				                        <tr>
				                        
				                            <th><g:message code="mano.tarea.label" default="Usuario" /></th>
				                   	    
				                            <th><g:message code="mano.juego.label" default="Carta" /></th> 
				                   	    
				                        </tr>
				                    </thead>
				                    <tbody>
				            			<g:each in="${jugadas }" var="jugada" status="i">
				            				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

					                            <td>${fieldValue(bean: jugada.usuario, field: "userRealName")}</td>
					                        
					                            <td>${fieldValue(bean: jugada.carta, field: "valor")}</td>
					                        
					                        </tr>
				            			</g:each>
		            				</tbody>
	                			</table>
                			</g:if>
	            		</div>
	            	</g:each>
            	</div>
            	<script type="text/javascript">
					$(function() {
						$("#acordion").accordion();
						$("#btn_juego").button({
				            icons: {
			                	primary: 'ui-icon-arrowrefresh-1-e'
			            	}
			            });
					});
				</script>
            	<div style="margin-top: 10px">
            		<a id="btn_juego" href="${ createLink(action:'index', controller:'juego') }">
       					Ir al juego
       				</a>
            	</div>
            </div>
        </div>
    </body>
</html>
