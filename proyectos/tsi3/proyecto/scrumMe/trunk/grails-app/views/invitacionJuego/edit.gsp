
<%@ page import="edu.tsi3.planningpoker.InvitacionJuego" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'invitacionJuego.label', default: 'InvitacionJuego')}" />
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
            <g:hasErrors bean="${invitacionJuegoInstance}">
            <div class="errors">
                <g:renderErrors bean="${invitacionJuegoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${invitacionJuegoInstance?.id}" />
                <g:hiddenField name="version" value="${invitacionJuegoInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estado"><g:message code="invitacionJuego.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: invitacionJuegoInstance, field: 'estado', 'errors')}">
                                    <g:textField name="estado" value="${fieldValue(bean: invitacionJuegoInstance, field: 'estado')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="usuario"><g:message code="invitacionJuego.usuario.label" default="Usuario" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: invitacionJuegoInstance, field: 'usuario', 'errors')}">
                                    <g:select name="usuario.id" from="${edu.tsi3.scrumme.Usuario.list()}" optionKey="id" value="${invitacionJuegoInstance?.usuario?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="juego"><g:message code="invitacionJuego.juego.label" default="Juego" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: invitacionJuegoInstance, field: 'juego', 'errors')}">
                                    <g:select name="juego.id" from="${edu.tsi3.planningpoker.Juego.list()}" optionKey="id" value="${invitacionJuegoInstance?.juego?.id}"  />
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
