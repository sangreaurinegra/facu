
<%@ page import="edu.tsi3.planningpoker.Juego" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'juego.label', default: 'Juego')}" />
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
            <g:hasErrors bean="${juegoInstance}">
            <div class="errors">
                <g:renderErrors bean="${juegoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${juegoInstance?.id}" />
                <g:hiddenField name="version" value="${juegoInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaFin"><g:message code="juego.fechaFin.label" default="Fecha Fin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'fechaFin', 'errors')}">
                                    <g:datePicker name="fechaFin" precision="day" value="${juegoInstance?.fechaFin}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaIni"><g:message code="juego.fechaIni.label" default="Fecha Ini" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'fechaIni', 'errors')}">
                                    <g:datePicker name="fechaIni" precision="day" value="${juegoInstance?.fechaIni}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="manos"><g:message code="juego.manos.label" default="Manos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'manos', 'errors')}">
                                    
<ul>
<g:each in="${juegoInstance?.manos?}" var="m">
    <li><g:link controller="mano" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="mano" action="create" params="['juego.id': juegoInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'mano.label', default: 'Mano')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nombre"><g:message code="juego.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${juegoInstance?.nombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estado"><g:message code="juego.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'estado', 'errors')}">
                                    <g:textField name="estado" value="${fieldValue(bean: juegoInstance, field: 'estado')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="invitados"><g:message code="juego.invitados.label" default="Invitados" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'invitados', 'errors')}">
                                    
<ul>
<g:each in="${juegoInstance?.invitados?}" var="i">
    <li><g:link controller="invitacionJuego" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="invitacionJuego" action="create" params="['juego.id': juegoInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'invitacionJuego.label', default: 'InvitacionJuego')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="creador"><g:message code="juego.creador.label" default="Creador" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'creador', 'errors')}">
                                    <g:select name="creador.id" from="${edu.tsi3.scrumme.Usuario.list()}" optionKey="id" value="${juegoInstance?.creador?.id}"  />
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
