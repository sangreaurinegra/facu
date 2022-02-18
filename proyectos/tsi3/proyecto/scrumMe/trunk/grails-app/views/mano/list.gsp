
<%@ page import="edu.tsi3.planningpoker.Mano" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'mano.label', default: 'Mano')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'mano.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'mano.estado.label', default: 'Estado')}" />
                        
                            <th><g:message code="mano.tarea.label" default="Tarea" /></th>
                   	    
                            <th><g:message code="mano.juego.label" default="Juego" /></th>
                   	    
                            <g:sortableColumn property="estimacion" title="${message(code: 'mano.estimacion.label', default: 'Estimacion')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${manoInstanceList}" status="i" var="manoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${manoInstance.id}">${fieldValue(bean: manoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: manoInstance, field: "estado")}</td>
                        
                            <td>${fieldValue(bean: manoInstance, field: "tarea")}</td>
                        
                            <td>${fieldValue(bean: manoInstance, field: "juego")}</td>
                        
                            <td>${fieldValue(bean: manoInstance, field: "estimacion")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${manoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
