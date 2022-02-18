
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
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'juego.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="fechaFin" title="${message(code: 'juego.fechaFin.label', default: 'Fecha Fin')}" />
                        
                            <g:sortableColumn property="fechaIni" title="${message(code: 'juego.fechaIni.label', default: 'Fecha Ini')}" />
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'juego.nombre.label', default: 'Nombre')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'juego.estado.label', default: 'Estado')}" />
                        
                            <th><g:message code="juego.creador.label" default="Creador" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${juegoInstanceList}" status="i" var="juegoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${juegoInstance.id}">${fieldValue(bean: juegoInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${juegoInstance.fechaFin}" /></td>
                        
                            <td><g:formatDate date="${juegoInstance.fechaIni}" /></td>
                        
                            <td>${fieldValue(bean: juegoInstance, field: "nombre")}</td>
                        
                            <td>${fieldValue(bean: juegoInstance, field: "estado")}</td>
                        
                            <td>${fieldValue(bean: juegoInstance, field: "creador")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${juegoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
