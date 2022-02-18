
<%@ page import="edu.tsi3.scrumme.Tarea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tarea.label', default: 'Tarea')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'tarea.id.label', default: 'Id')}" />
                        
                            <th><g:message code="tarea.asignado.label" default="Asignado" /></th>
                   	    
                            <g:sortableColumn property="descripcion" title="${message(code: 'tarea.descripcion.label', default: 'Descripcion')}" />
                        
                            <g:sortableColumn property="fechaIni" title="${message(code: 'tarea.fechaIni.label', default: 'Fecha Ini')}" />
                        
                            <g:sortableColumn property="fechaFin" title="${message(code: 'tarea.fechaFin.label', default: 'Fecha Fin')}" />
                        
                            <th><g:message code="tarea.proyecto.label" default="Proyecto" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tareaInstanceList}" status="i" var="tareaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tareaInstance.id}">${fieldValue(bean: tareaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "asignado")}</td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "descripcion")}</td>
                        
                            <td><g:formatDate date="${tareaInstance.fechaIni}" /></td>
                        
                            <td><g:formatDate date="${tareaInstance.fechaFin}" /></td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "proyecto")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tareaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
