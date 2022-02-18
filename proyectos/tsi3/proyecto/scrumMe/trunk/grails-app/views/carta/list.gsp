
<%@ page import="edu.tsi3.planningpoker.Carta" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'carta.label', default: 'Carta')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'carta.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="imagen" title="${message(code: 'carta.imagen.label', default: 'Imagen')}" />
                        
                            <g:sortableColumn property="valor" title="${message(code: 'carta.valor.label', default: 'Valor')}" />
                        
                            <g:sortableColumn property="comodin" title="${message(code: 'carta.comodin.label', default: 'Comodin')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${cartaInstanceList}" status="i" var="cartaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${cartaInstance.id}">${fieldValue(bean: cartaInstance, field: "id")}</g:link></td>
                        
                            <td>
								<img src="${createLink(action:'imagen', controller:'carta',id:cartaInstance.id) }" />
							</td>
                        
                            <td>${fieldValue(bean: cartaInstance, field: "valor")}</td>
                        
                            <td><g:formatBoolean boolean="${cartaInstance.comodin}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${cartaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
