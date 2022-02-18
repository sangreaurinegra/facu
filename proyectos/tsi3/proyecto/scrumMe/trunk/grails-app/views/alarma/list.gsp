
<%@ page import="edu.tsi3.scrumme.Alarma" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'alarma.label', default: 'Alarma')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'alarma.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="titulo" title="${message(code: 'alarma.titulo.label', default: 'Titulo')}" />
                        
                            <g:sortableColumn property="mensaje" title="${message(code: 'alarma.mensaje.label', default: 'Mensaje')}" />
                        
                            <g:sortableColumn property="link" title="${message(code: 'alarma.link.label', default: 'Link')}" />
                        
                            <g:sortableColumn property="estado" title="${message(code: 'alarma.estado.label', default: 'Estado')}" />
                        
                            <g:sortableColumn property="fechaAlarma" title="${message(code: 'alarma.fechaAlarma.label', default: 'Fecha Alarma')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${alarmaInstanceList}" status="i" var="alarmaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${alarmaInstance.id}">${fieldValue(bean: alarmaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: alarmaInstance, field: "titulo")}</td>
                        
                            <td>${fieldValue(bean: alarmaInstance, field: "mensaje")}</td>
                        
                            <td>${fieldValue(bean: alarmaInstance, field: "link")}</td>
                        
                            <td>${fieldValue(bean: alarmaInstance, field: "estado")}</td>
                        
                            <td><g:formatDate date="${alarmaInstance.fechaAlarma}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${alarmaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
