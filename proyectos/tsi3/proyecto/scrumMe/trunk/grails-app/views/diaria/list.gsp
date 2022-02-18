
<%@ page import="edu.tsi3.scrumme.Diaria" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'diaria.label', default: 'Diaria')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'diaria.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="titulo" title="${message(code: 'diaria.titulo.label', default: 'Titulo')}" />
                        
                            <g:sortableColumn property="lugar" title="${message(code: 'diaria.lugar.label', default: 'Lugar')}" />
                        
                            <g:sortableColumn property="fechaInicio" title="${message(code: 'diaria.fechaInicio.label', default: 'Fecha Inicio')}" />
                        
                            <g:sortableColumn property="fechafin" title="${message(code: 'diaria.fechafin.label', default: 'Fechafin')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'diaria.estado.label', default: 'Estado')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${diariaInstanceList}" status="i" var="diariaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${diariaInstance.id}">${fieldValue(bean: diariaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: diariaInstance, field: "titulo")}</td>
                        
                            <td>${fieldValue(bean: diariaInstance, field: "lugar")}</td>
                        
                            <td><g:formatDate date="${diariaInstance.fechaInicio}" /></td>
                        
                            <td><g:formatDate date="${diariaInstance.fechafin}" /></td>
                        
                            <td>${fieldValue(bean: diariaInstance, field: "estado")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${diariaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
