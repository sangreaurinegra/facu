
<%@ page import="edu.tsi3.scrumme.Invitacion" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'invitacion.label', default: 'Invitacion')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'invitacion.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="fecha" title="${message(code: 'invitacion.fecha.label', default: 'Fecha')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'invitacion.estado.label', default: 'Estado')}" />
                        
                            <g:sortableColumn property="fechaRespuesta" title="${message(code: 'invitacion.fechaRespuesta.label', default: 'Fecha Respuesta')}" />
                        
                            <th><g:message code="invitacion.usuario.label" default="Usuario" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${invitacionInstanceList}" status="i" var="invitacionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${invitacionInstance.id}">${fieldValue(bean: invitacionInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${invitacionInstance.fecha}" /></td>
                        
                            <td>${fieldValue(bean: invitacionInstance, field: "estado.nombreEstado")}</td>
                        
                            <td><g:formatDate date="${invitacionInstance.fechaRespuesta}" /></td>
                        
                            <td>${fieldValue(bean: invitacionInstance, field: "usuario")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${invitacionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
