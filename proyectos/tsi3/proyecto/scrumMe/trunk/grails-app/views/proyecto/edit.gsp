
<%@ page import="edu.tsi3.scrumme.Proyecto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'proyecto.label', default: 'Proyecto')}" />
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
            <g:hasErrors bean="${proyectoInstance}">
            <div class="errors">
                <g:renderErrors bean="${proyectoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${proyectoInstance?.id}" />
                <g:hiddenField name="version" value="${proyectoInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nombre"><g:message code="proyecto.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: proyectoInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${proyectoInstance?.nombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaIni"><g:message code="proyecto.fechaIni.label" default="Fecha Ini" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: proyectoInstance, field: 'fechaIni', 'errors')}">
                                    <g:datePicker name="fechaIni" precision="day" value="${proyectoInstance?.fechaIni}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaFin"><g:message code="proyecto.fechaFin.label" default="Fecha Fin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: proyectoInstance, field: 'fechaFin', 'errors')}">
                                    <g:datePicker name="fechaFin" precision="day" value="${proyectoInstance?.fechaFin}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estado"><g:message code="proyecto.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: proyectoInstance, field: 'estado', 'errors')}">
                                    <g:select id="estado" name="estado" from="${edu.tsi3.scrumme.util.EstadoProyecto?.list()}" value="${fieldValue(bean: proyectoInstance, field: 'estado')}" optionValue="nombreEstado"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="backlog"><g:message code="proyecto.backlog.label" default="Backlog" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: proyectoInstance, field: 'backlog', 'errors')}">
                                    
<ul>
<g:each in="${proyectoInstance?.backlog?}" var="b">
    <li><g:link controller="tarea" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="tarea" action="create" params="['proyecto.id': proyectoInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'tarea.label', default: 'Tarea')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sprints"><g:message code="proyecto.sprints.label" default="Sprints" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: proyectoInstance, field: 'sprints', 'errors')}">
                                    
<ul>
<g:each in="${proyectoInstance?.sprints?}" var="s">
    <li><g:link controller="sprint" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="sprint" action="create" params="['proyecto.id': proyectoInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'sprint.label', default: 'Sprint')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="usuarios"><g:message code="proyecto.usuarios.label" default="Usuarios" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: proyectoInstance, field: 'usuarios', 'errors')}">
                                    
<ul>
<g:each in="${proyectoInstance?.usuarios?}" var="u">
    <li><g:link controller="usuarioProyecto" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="usuarioProyecto" action="create" params="['proyecto.id': proyectoInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'usuarioProyecto.label', default: 'UsuarioProyecto')])}</g:link>

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
