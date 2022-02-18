
<%@ page import="edu.tsi3.planningpoker.Mano" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'mano.label', default: 'Mano')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${manoInstance}">
            <div class="errors">
                <g:renderErrors bean="${manoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${manoInstance?.id}" />
                <g:hiddenField name="version" value="${manoInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estado"><g:message code="mano.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manoInstance, field: 'estado', 'errors')}">
                                    <g:textField name="estado" value="${fieldValue(bean: manoInstance, field: 'estado')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tarea"><g:message code="mano.tarea.label" default="Tarea" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manoInstance, field: 'tarea', 'errors')}">
                                    <g:select name="tarea.id" from="${edu.tsi3.scrumme.Tarea.list()}" optionKey="id" value="${manoInstance?.tarea?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="juego"><g:message code="mano.juego.label" default="Juego" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manoInstance, field: 'juego', 'errors')}">
                                    <g:select name="juego.id" from="${edu.tsi3.planningpoker.Juego.list()}" optionKey="id" value="${manoInstance?.juego?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estimacion"><g:message code="mano.estimacion.label" default="Estimacion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manoInstance, field: 'estimacion', 'errors')}">
                                    <g:textField name="estimacion" value="${fieldValue(bean: manoInstance, field: 'estimacion')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
