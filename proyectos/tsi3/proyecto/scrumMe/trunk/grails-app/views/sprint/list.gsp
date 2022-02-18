
<%@ page import="edu.tsi3.scrumme.Sprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sprint.label', default: 'Sprint')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'sprint.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'sprint.nombre.label', default: 'Nombre')}" />
                        
                            <g:sortableColumn property="fraseObjetivo" title="${message(code: 'sprint.fraseObjetivo.label', default: 'Frase Objetivo')}" />
                        
                            <g:sortableColumn property="fechaIni" title="${message(code: 'sprint.fechaIni.label', default: 'Fecha Ini')}" />
                        
                            <g:sortableColumn property="fechaFin" title="${message(code: 'sprint.fechaFin.label', default: 'Fecha Fin')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'sprint.estado.label', default: 'Estado')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sprintInstanceList}" status="i" var="sprintInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${sprintInstance.id}">${fieldValue(bean: sprintInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: sprintInstance, field: "nombre")}</td>
                        
                            <td>${fieldValue(bean: sprintInstance, field: "fraseObjetivo")}</td>
                        
                            <td><g:formatDate date="${sprintInstance.fechaIni}" /></td>
                        
                            <td><g:formatDate date="${sprintInstance.fechaFin}" /></td>
                        
                            <td>${fieldValue(bean: sprintInstance, field: "estado")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${sprintInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
