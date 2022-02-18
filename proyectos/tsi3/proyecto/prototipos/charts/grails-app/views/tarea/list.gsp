
<%@ page import="edu.tsi3.prototipo.Tarea" %>
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
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'tarea.nombre.label', default: 'Nombre')}" />
                        
                            <g:sortableColumn property="updated" title="${message(code: 'tarea.updated.label', default: 'Updated')}" />
                        
                            <g:sortableColumn property="descripcion" title="${message(code: 'tarea.descripcion.label', default: 'Descripcion')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'tarea.dateCreated.label', default: 'Date Created')}" />
                        
                            <th><g:message code="tarea.user.label" default="User" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tareaInstanceList}" status="i" var="tareaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tareaInstance.id}">${fieldValue(bean: tareaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "nombre")}</td>
                        
                            <td><g:formatDate date="${tareaInstance.updated}" /></td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "descripcion")}</td>
                        
                            <td><g:formatDate date="${tareaInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: tareaInstance, field: "user")}</td>
                        
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
