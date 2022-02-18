
<%@ page import="edu.tsi3.scrumme.Sprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sprint.label', default: 'Sprint')}" />
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
            <g:hasErrors bean="${sprintInstance}">
            <div class="errors">
                <g:renderErrors bean="${sprintInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${sprintInstance?.id}" />
                <g:hiddenField name="version" value="${sprintInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nombre"><g:message code="sprint.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${sprintInstance?.nombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fraseObjetivo"><g:message code="sprint.fraseObjetivo.label" default="Frase Objetivo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'fraseObjetivo', 'errors')}">
                                    <g:textArea name="fraseObjetivo" cols="40" rows="5" value="${sprintInstance?.fraseObjetivo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaIni"><g:message code="sprint.fechaIni.label" default="Fecha Ini" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'fechaIni', 'errors')}">
                                    <g:datePicker name="fechaIni" precision="day" value="${sprintInstance?.fechaIni}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaFin"><g:message code="sprint.fechaFin.label" default="Fecha Fin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'fechaFin', 'errors')}">
                                    <g:datePicker name="fechaFin" precision="day" value="${sprintInstance?.fechaFin}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estado"><g:message code="sprint.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'estado', 'errors')}">
                                    <g:textField name="estado" value="${fieldValue(bean: sprintInstance, field: 'estado')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tareas"><g:message code="sprint.tareas.label" default="Tareas" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'tareas', 'errors')}">
                                    
<ul>
<g:each in="${sprintInstance?.tareas?}" var="t">
    <li><g:link controller="tarea" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="tarea" action="create" params="['sprint.id': sprintInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'tarea.label', default: 'Tarea')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="proyecto"><g:message code="sprint.proyecto.label" default="Proyecto" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'proyecto', 'errors')}">
                                    <g:select name="proyecto.id" from="${edu.tsi3.scrumme.Proyecto.list()}" optionKey="id" value="${sprintInstance?.proyecto?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="numero"><g:message code="sprint.numero.label" default="Numero" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'numero', 'errors')}">
                                    <g:textField name="numero" value="${fieldValue(bean: sprintInstance, field: 'numero')}" />
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
