
<%@ page import="edu.tsi3.scrumme.Reunion" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'reunion.label', default: 'Reunion')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'reunion.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="titulo" title="${message(code: 'reunion.titulo.label', default: 'Titulo')}" />
                        
                            <g:sortableColumn property="lugar" title="${message(code: 'reunion.lugar.label', default: 'Lugar')}" />
                        
                            <g:sortableColumn property="fechaInicio" title="${message(code: 'reunion.fechaInicio.label', default: 'Fecha Inicio')}" />
                        
                            <g:sortableColumn property="fechaFin" title="${message(code: 'reunion.fechaFin.label', default: 'Fecha Fin')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'reunion.estado.label', default: 'Estado')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${reunionInstanceList}" status="i" var="reunionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${reunionInstance.id}">${fieldValue(bean: reunionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: reunionInstance, field: "titulo")}</td>
                        
                            <td>${fieldValue(bean: reunionInstance, field: "lugar")}</td>
                        
                            <td><g:formatDate date="${reunionInstance.fechaInicio}" /></td>
                        
                            <td><g:formatDate date="${reunionInstance.fechaFin}" /></td>
                        
                            <td>${fieldValue(bean: reunionInstance, field: "estado.nombreEstado")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${reunionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
