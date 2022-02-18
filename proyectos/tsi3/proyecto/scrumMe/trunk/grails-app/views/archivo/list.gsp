
<%@ page import="edu.tsi3.scrumme.Archivo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'archivo.label', default: 'Archivo')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'archivo.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'archivo.nombre.label', default: 'Nombre')}" />
                        
                            <g:sortableColumn property="fecha" title="${message(code: 'archivo.fecha.label', default: 'Fecha')}" />
                        
                            <th><g:message code="archivo.proyecto.label" default="Proyecto" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${archivoInstanceList}" status="i" var="archivoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${archivoInstance.id}">${fieldValue(bean: archivoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: archivoInstance, field: "nombre")}</td>
                        
                            <td><g:formatDate date="${archivoInstance.fecha}" /></td>
                        
                            <td>${fieldValue(bean: archivoInstance, field: "proyecto")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${archivoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
