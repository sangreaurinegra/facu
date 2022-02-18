
<%@ page import="edu.tsi3.scrumme.UsuarioProyecto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'usuarioProyecto.id.label', default: 'Id')}" />
                        
                            <th><g:message code="usuarioProyecto.usuario.label" default="Usuario" /></th>
                   	    
                            <th><g:message code="usuarioProyecto.proyecto.label" default="Proyecto" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${usuarioProyectoInstanceList}" status="i" var="usuarioProyectoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${usuarioProyectoInstance.id}">${fieldValue(bean: usuarioProyectoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: usuarioProyectoInstance, field: "usuario")}</td>
                        
                            <td>${fieldValue(bean: usuarioProyectoInstance, field: "proyecto")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${usuarioProyectoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
