
<%@ page import="edu.tsi3.scrumme.RevisionSprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'revisionSprint.label', default: 'RevisionSprint')}" />
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
            <g:hasErrors bean="${revisionSprintInstance}">
            <div class="errors">
                <g:renderErrors bean="${revisionSprintInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="titulo"><g:message code="revisionSprint.titulo.label" default="Titulo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: revisionSprintInstance, field: 'titulo', 'errors')}">
                                    <g:textField name="titulo" value="${revisionSprintInstance?.titulo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lugar"><g:message code="revisionSprint.lugar.label" default="Lugar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: revisionSprintInstance, field: 'lugar', 'errors')}">
                                    <g:textField name="lugar" value="${revisionSprintInstance?.lugar}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaInicio"><g:message code="revisionSprint.fechaInicio.label" default="Fecha Inicio" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: revisionSprintInstance, field: 'fechaInicio', 'errors')}">
                                    <g:datePicker name="fechaInicio" precision="day" value="${revisionSprintInstance?.fechaInicio}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechafin"><g:message code="revisionSprint.fechafin.label" default="Fechafin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: revisionSprintInstance, field: 'fechafin', 'errors')}">
                                    <g:datePicker name="fechafin" precision="day" value="${revisionSprintInstance?.fechafin}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="estado"><g:message code="revisionSprint.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: revisionSprintInstance, field: 'estado', 'errors')}">
                                    <g:select name="estado" from="${edu.tsi3.scrumme.util.EstadoReunion?.values()}" value="${revisionSprintInstance?.estado}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="proyecto"><g:message code="revisionSprint.proyecto.label" default="Proyecto" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: revisionSprintInstance, field: 'proyecto', 'errors')}">
                                    <g:select name="proyecto.id" from="${edu.tsi3.scrumme.Proyecto.list()}" optionKey="id" value="${revisionSprintInstance?.proyecto?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="acta"><g:message code="revisionSprint.acta.label" default="Acta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: revisionSprintInstance, field: 'acta', 'errors')}">
                                    <g:select name="acta.id" from="${edu.tsi3.scrumme.Texto.list()}" optionKey="id" value="${revisionSprintInstance?.acta?.id}" noSelection="['null': '']" />
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
