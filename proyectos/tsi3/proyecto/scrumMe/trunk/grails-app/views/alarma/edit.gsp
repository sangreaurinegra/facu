
<%@ page import="edu.tsi3.scrumme.Alarma" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'alarma.label', default: 'Alarma')}" />
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
            <g:hasErrors bean="${alarmaInstance}">
            <div class="errors">
                <g:renderErrors bean="${alarmaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${alarmaInstance?.id}" />
                <g:hiddenField name="version" value="${alarmaInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="titulo"><g:message code="alarma.titulo.label" default="Titulo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: alarmaInstance, field: 'titulo', 'errors')}">
                                    <g:textField name="titulo" value="${alarmaInstance?.titulo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mensaje"><g:message code="alarma.mensaje.label" default="Mensaje" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: alarmaInstance, field: 'mensaje', 'errors')}">
                                    <g:textArea name="mensaje" cols="40" rows="5" value="${alarmaInstance?.mensaje}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="link"><g:message code="alarma.link.label" default="Link" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: alarmaInstance, field: 'link', 'errors')}">
                                    <g:textField name="link" value="${alarmaInstance?.link}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estado"><g:message code="alarma.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: alarmaInstance, field: 'estado', 'errors')}">
                                    <g:textField name="estado" value="${fieldValue(bean: alarmaInstance, field: 'estado')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="avisados"><g:message code="alarma.avisados.label" default="Avisados" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: alarmaInstance, field: 'avisados', 'errors')}">
                                    <g:select name="avisados" from="${edu.tsi3.scrumme.Usuario.list()}" multiple="yes" optionKey="id" size="5" value="${alarmaInstance?.avisados}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaAlarma"><g:message code="alarma.fechaAlarma.label" default="Fecha Alarma" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: alarmaInstance, field: 'fechaAlarma', 'errors')}">
                                    <g:datePicker name="fechaAlarma" precision="minute" value="${alarmaInstance?.fechaAlarma}"  />
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
