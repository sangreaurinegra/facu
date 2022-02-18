
<%@ page import="edu.tsi3.scrumme.Mensaje" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'mensaje.label', default: 'Mensaje')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${mensajeInstance}">
            <div class="errors">
                <g:renderErrors bean="${mensajeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="titulo"><g:message code="mensaje.titulo.label" default="Titulo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensajeInstance, field: 'titulo', 'errors')}">
                                    <g:textField name="titulo" value="${mensajeInstance?.titulo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="mensaje"><g:message code="mensaje.mensaje.label" default="Mensaje" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensajeInstance, field: 'mensaje', 'errors')}">
                                    <g:textArea name="mensaje" cols="40" rows="5" value="${mensajeInstance?.mensaje}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="link"><g:message code="mensaje.link.label" default="Link" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensajeInstance, field: 'link', 'errors')}">
                                    <g:textField name="link" value="${mensajeInstance?.link}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaLeido"><g:message code="mensaje.fechaLeido.label" default="Fecha Leido" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensajeInstance, field: 'fechaLeido', 'errors')}">
                                    <g:datePicker name="fechaLeido" precision="day" value="${mensajeInstance?.fechaLeido}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaEnviado"><g:message code="mensaje.fechaEnviado.label" default="Fecha Enviado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensajeInstance, field: 'fechaEnviado', 'errors')}">
                                    <g:datePicker name="fechaEnviado" precision="day" value="${mensajeInstance?.fechaEnviado}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="destino"><g:message code="mensaje.destino.label" default="Destino" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensajeInstance, field: 'destino', 'errors')}">
                                    <g:select name="destino.id" from="${edu.tsi3.scrumme.Usuario.list()}" optionKey="id" value="${mensajeInstance?.destino?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="estado"><g:message code="mensaje.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensajeInstance, field: 'estado', 'errors')}">
                                    <g:textField name="estado" value="${fieldValue(bean: mensajeInstance, field: 'estado')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tipo"><g:message code="mensaje.tipo.label" default="Tipo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mensajeInstance, field: 'tipo', 'errors')}">
                                    <g:textField name="tipo" value="${fieldValue(bean: mensajeInstance, field: 'tipo')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
