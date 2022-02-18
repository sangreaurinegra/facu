
<%@ page import="edu.tsi3.scrumme.TipoTarea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tipoTarea.label', default: 'TipoTarea')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'tipoTarea.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'tipoTarea.nombre.label', default: 'Nombre')}" />
                        
                            <g:sortableColumn property="color" title="${message(code: 'tipoTarea.color.label', default: 'Color')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tipoTareaInstanceList}" status="i" var="tipoTareaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tipoTareaInstance.id}">${fieldValue(bean: tipoTareaInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tipoTareaInstance, field: "nombre")}</td>
                        
                            <td>${fieldValue(bean: tipoTareaInstance, field: "color")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tipoTareaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
