
<%@ page import="edu.tsi3.scrumme.Texto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'texto.label', default: 'Texto')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'texto.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="texto" title="${message(code: 'texto.texto.label', default: 'Texto')}" />
                        
                            <th><g:message code="texto.reunion.label" default="Reunion" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${textoInstanceList}" status="i" var="textoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${textoInstance.id}">${fieldValue(bean: textoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: textoInstance, field: "texto")}</td>
                        
                            <td>${fieldValue(bean: textoInstance, field: "reunion")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${textoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
