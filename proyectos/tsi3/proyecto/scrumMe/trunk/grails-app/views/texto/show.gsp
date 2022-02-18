
<%@ page import="edu.tsi3.scrumme.Texto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'texto.label', default: 'Texto')}" />
        <title> <g:message code="texto.reunion.label" default="Acta Reunion " />
                            <g:link controller="reunion" action="show" id="${textoInstance?.reunion?.id}">${textoInstance?.reunion?.titulo.encodeAsHTML()}</g:link></title>
        <ckeditor:resources />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <ckeditor:config  toolbarStartupExpanded="false" templates_replaceContent="false"/>
                            
                            <ckeditor:editor name="texto" height="400px" width="100%" toolbar="none" >
									${textoInstance?.texto}
							</ckeditor:editor>
                        </tr>
                        <tr class="prop">
                          
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${textoInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
