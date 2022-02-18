
<%@ page import="edu.tsi3.scrumme.Documento" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'documento.label', default: 'Documento')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'documento.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'documento.nombre.label', default: 'Nombre')}" />
                        
                            <g:sortableColumn property="fecha" title="${message(code: 'documento.fecha.label', default: 'Fecha')}" />
                        
                            <th><g:message code="documento.proyecto.label" default="Proyecto" /></th>
                   	    
                            <g:sortableColumn property="path" title="${message(code: 'documento.path.label', default: 'Path')}" />
                        
                            <th><g:message code="documento.reunion.label" default="Reunion" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${documentoInstanceList}" status="i" var="documentoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${documentoInstance.id}">${fieldValue(bean: documentoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: documentoInstance, field: "nombre")}</td>
                        
                            <td><g:formatDate date="${documentoInstance.fecha}" /></td>
                        
                            <td>${fieldValue(bean: documentoInstance, field: "proyecto")}</td>
                        
                            <td>${fieldValue(bean: documentoInstance, field: "path")}</td>
                        
                            <td>${fieldValue(bean: documentoInstance, field: "reunion")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${documentoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
