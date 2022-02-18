
<%@ page import="edu.tsi3.scrumme.PlanSprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'planSprint.label', default: 'PlanSprint')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'planSprint.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="titulo" title="${message(code: 'planSprint.titulo.label', default: 'Titulo')}" />
                        
                            <g:sortableColumn property="lugar" title="${message(code: 'planSprint.lugar.label', default: 'Lugar')}" />
                        
                            <g:sortableColumn property="fechaInicio" title="${message(code: 'planSprint.fechaInicio.label', default: 'Fecha Inicio')}" />
                        
                            <g:sortableColumn property="fechafin" title="${message(code: 'planSprint.fechafin.label', default: 'Fechafin')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'planSprint.estado.label', default: 'Estado')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${planSprintInstanceList}" status="i" var="planSprintInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${planSprintInstance.id}">${fieldValue(bean: planSprintInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: planSprintInstance, field: "titulo")}</td>
                        
                            <td>${fieldValue(bean: planSprintInstance, field: "lugar")}</td>
                        
                            <td><g:formatDate date="${planSprintInstance.fechaInicio}" /></td>
                        
                            <td><g:formatDate date="${planSprintInstance.fechafin}" /></td>
                        
                            <td>${fieldValue(bean: planSprintInstance, field: "estado")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${planSprintInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
