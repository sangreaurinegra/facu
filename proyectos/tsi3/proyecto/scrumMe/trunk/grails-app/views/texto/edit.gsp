
<%@ page import="edu.tsi3.scrumme.Texto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'texto.label', default: 'Texto')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <ckeditor:resources />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Acta de Reunion ${textoInstance?.reunion?.titulo} </h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${textoInstance}">
            <div class="errors">
                <g:renderErrors bean="${textoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${textoInstance?.id}" />
                <g:hiddenField name="version" value="${textoInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            
                                <td valign="top" class="value ${hasErrors(bean: textoInstance, field: 'texto', 'errors')}">
                                    <ckeditor:editor name="texto" height="400px" width="100%" toolbar="Full" >
										${textoInstance?.texto}
									</ckeditor:editor>
                                </td>
                            
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
