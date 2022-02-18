
<%@ page import="edu.tsi3.scrumme.RevisionSprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'revisionSprint.label', default: 'RevisionSprint')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'revisionSprint.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="titulo" title="${message(code: 'revisionSprint.titulo.label', default: 'Titulo')}" />
                        
                            <g:sortableColumn property="lugar" title="${message(code: 'revisionSprint.lugar.label', default: 'Lugar')}" />
                        
                            <g:sortableColumn property="fechaInicio" title="${message(code: 'revisionSprint.fechaInicio.label', default: 'Fecha Inicio')}" />
                        
                            <g:sortableColumn property="fechafin" title="${message(code: 'revisionSprint.fechafin.label', default: 'Fechafin')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'revisionSprint.estado.label', default: 'Estado')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${revisionSprintInstanceList}" status="i" var="revisionSprintInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${revisionSprintInstance.id}">${fieldValue(bean: revisionSprintInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: revisionSprintInstance, field: "titulo")}</td>
                        
                            <td>${fieldValue(bean: revisionSprintInstance, field: "lugar")}</td>
                        
                            <td><g:formatDate date="${revisionSprintInstance.fechaInicio}" /></td>
                        
                            <td><g:formatDate date="${revisionSprintInstance.fechafin}" /></td>
                        
                            <td>${fieldValue(bean: revisionSprintInstance, field: "estado")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${revisionSprintInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
