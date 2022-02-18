
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
							<th><g:message code="invitacionJuego.juego.label" default="Juego" /></th>
							
                            <th></th>
                   	    
                            <th></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${invitacionJuegoInstanceList}" status="i" var="invitacionJuegoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: invitacionJuegoInstance, field: "juego")}</td>
                        
                            <td>
								<a href="${createLink(action:'aceptar', controller:'invitacionJuego', id:invitacionJuegoInstance.id) }">Aceptar</a>
							</td>
                        
                            <td>
								<a href="${createLink(action:'delete', controller:'invitacionJuego', id:invitacionJuegoInstance.id) }">Eliminar</a>
							</td>
                        
                            
                        
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
