
<%@ page import="edu.tsi3.scrumme.PlanSprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'planSprint.label', default: 'PlanSprint')}" />
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
            <g:hasErrors bean="${planSprintInstance}">
            <div class="errors">
                <g:renderErrors bean="${planSprintInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${planSprintInstance?.id}" />
                <g:hiddenField name="version" value="${planSprintInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="titulo"><g:message code="planSprint.titulo.label" default="Titulo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'titulo', 'errors')}">
                                    <g:textField name="titulo" value="${planSprintInstance?.titulo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lugar"><g:message code="planSprint.lugar.label" default="Lugar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'lugar', 'errors')}">
                                    <g:textField name="lugar" value="${planSprintInstance?.lugar}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaInicio"><g:message code="planSprint.fechaInicio.label" default="Fecha Inicio" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'fechaInicio', 'errors')}">
                                    <g:datePicker name="fechaInicio" precision="day" value="${planSprintInstance?.fechaInicio}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechafin"><g:message code="planSprint.fechafin.label" default="Fechafin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'fechafin', 'errors')}">
                                    <g:datePicker name="fechafin" precision="day" value="${planSprintInstance?.fechafin}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estado"><g:message code="planSprint.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'estado', 'errors')}">
                                    <g:select name="estado" from="${edu.tsi3.scrumme.util.EstadoReunion?.values()}" value="${planSprintInstance?.estado}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="proyecto"><g:message code="planSprint.proyecto.label" default="Proyecto" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'proyecto', 'errors')}">
                                    <g:select name="proyecto.id" from="${edu.tsi3.scrumme.Proyecto.list()}" optionKey="id" value="${planSprintInstance?.proyecto?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="acta"><g:message code="planSprint.acta.label" default="Acta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'acta', 'errors')}">
                                    <g:select name="acta.id" from="${edu.tsi3.scrumme.Texto.list()}" optionKey="id" value="${planSprintInstance?.acta?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="invitaciones"><g:message code="planSprint.invitaciones.label" default="Invitaciones" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'invitaciones', 'errors')}">
                                    <g:select name="invitaciones" from="${edu.tsi3.scrumme.Invitacion.list()}" multiple="yes" optionKey="id" size="5" value="${planSprintInstance?.invitaciones}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sprint"><g:message code="planSprint.sprint.label" default="Sprint" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'sprint', 'errors')}">
                                    <g:select name="sprint.id" from="${edu.tsi3.scrumme.Sprint.list()}" optionKey="id" value="${planSprintInstance?.sprint?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="documentos"><g:message code="planSprint.documentos.label" default="Documentos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: planSprintInstance, field: 'documentos', 'errors')}">
                                    
<ul>
<g:each in="${planSprintInstance?.documentos?}" var="d">
    <li><g:link controller="documento" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="documento" action="create" params="['planSprint.id': planSprintInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'documento.label', default: 'Documento')])}</g:link>

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
