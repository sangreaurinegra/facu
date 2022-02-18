
<%@ page import="edu.tsi3.planningpoker.InvitacionJuego" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'invitacionJuego.label', default: 'InvitacionJuego')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'invitacionJuego.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'invitacionJuego.estado.label', default: 'Estado')}" />
                        
                            <th><g:message code="invitacionJuego.usuario.label" default="Usuario" /></th>
                   	    
                            <th><g:message code="invitacionJuego.juego.label" default="Juego" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${invitacionJuegoInstanceList}" status="i" var="invitacionJuegoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${invitacionJuegoInstance.id}">${fieldValue(bean: invitacionJuegoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: invitacionJuegoInstance, field: "estado")}</td>
                        
                            <td>${fieldValue(bean: invitacionJuegoInstance, field: "usuario")}</td>
                        
                            <td>${fieldValue(bean: invitacionJuegoInstance, field: "juego")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${invitacionJuegoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
