
<%@ page import="edu.tsi3.prototipo.Usuario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'usuario.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="password" title="${message(code: 'usuario.password.label', default: 'Password')}" />
                        
                            <g:sortableColumn property="userId" title="${message(code: 'usuario.userId.label', default: 'User Id')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'usuario.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="foto" title="${message(code: 'usuario.foto.label', default: 'Foto')}" />
                        
                            <g:sortableColumn property="homepage" title="${message(code: 'usuario.homepage.label', default: 'Homepage')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${usuarioInstanceList}" status="i" var="usuarioInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${usuarioInstance.id}">${fieldValue(bean: usuarioInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: usuarioInstance, field: "password")}</td>
                        
                            <td>${fieldValue(bean: usuarioInstance, field: "userId")}</td>
                        
                            <td><g:formatDate date="${usuarioInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: usuarioInstance, field: "foto")}</td>
                        
                            <td>${fieldValue(bean: usuarioInstance, field: "homepage")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${usuarioInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
