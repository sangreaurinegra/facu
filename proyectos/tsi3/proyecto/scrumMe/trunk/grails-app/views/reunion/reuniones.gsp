
<%@ page import="edu.tsi3.scrumme.Reunion" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="reunion.list.label" default="Reuniones" /></title>
    </head>
    <body>
        <div class="body">
            <h1>Reuniones del proyecto: ${proyecto.nombre }</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="titulo" title="${message(code: 'reunion.titulo.label', default: 'Titulo')}" />
                        
                            <g:sortableColumn property="lugar" title="${message(code: 'reunion.lugar.label', default: 'Lugar')}" />
                        
                            <g:sortableColumn property="fechaInicio" title="${message(code: 'reunion.fechaInicio.label', default: 'Fecha Inicio')}" />
                        
                            <g:sortableColumn property="fechaFin" title="${message(code: 'reunion.fechaFin.label', default: 'Fecha Fin')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'reunion.estado.label', default: 'Estado')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${reuniones}" status="i" var="reunionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${reunionInstance.id}">${fieldValue(bean: reunionInstance, field: "titulo")}</g:link></td>
                        
                            <td>${fieldValue(bean: reunionInstance, field: "lugar")}</td>
                        
                            <td><g:formatDate date="${reunionInstance.fechaInicio}" format="dd/MM/yyyy HH:mm" /></td>
                        
                            <td><g:formatDate date="${reunionInstance.fechaFin}"  format="dd/MM/yyyy HH:mm" /></td>
                        
                            <td>${fieldValue(bean: reunionInstance, field: "estado.nombreEstado")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <a id="proy" href="${createLink(action:'ver', controller:'proyecto', id:proyecto.id) }">Ir al proyecto</a>
        </div>
        <script type="text/javascript">
				$(function() {
					$("#proy").button({
				           icons: {
				              	primary: 'ui-icon-folder-open'
				          	}
				          });
					
				});
			</script>
    </body>
</html>
