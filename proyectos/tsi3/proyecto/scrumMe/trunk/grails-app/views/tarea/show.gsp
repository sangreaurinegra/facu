
<%@ page import="edu.tsi3.scrumme.Tarea" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tarea.label', default: 'Tarea')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
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
                            <td valign="top" class="name"><g:message code="tarea.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tareaInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.asignado.label" default="Asignado" /></td>
                            
                            <td valign="top" class="value"><g:link controller="usuario" action="show" id="${tareaInstance?.asignado?.id}">${tareaInstance?.asignado?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.descripcion.label" default="Descripcion" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tareaInstance, field: "descripcion")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.fechaIni.label" default="Fecha Ini" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${tareaInstance?.fechaIni}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.fechaFin.label" default="Fecha Fin" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${tareaInstance?.fechaFin}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.proyecto.label" default="Proyecto" /></td>
                            
                            <td valign="top" class="value"><g:link controller="proyecto" action="show" id="${tareaInstance?.proyecto?.id}">${tareaInstance?.proyecto?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.nombre.label" default="Nombre" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tareaInstance, field: "nombre")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.estado.label" default="Estado" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: tareaInstance, field: "estado.nombreEstado")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.tipo.label" default="Tipo" /></td>
                            
                            <td valign="top" class="value"><g:link controller="tipoTarea" action="show" id="${tareaInstance?.tipo?.id}">${tareaInstance?.tipo?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="tarea.sprint.label" default="Sprint" /></td>
                            
                            <td valign="top" class="value"><g:link controller="sprint" action="show" id="${tareaInstance?.sprint?.id}">${tareaInstance?.sprint?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${tareaInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
