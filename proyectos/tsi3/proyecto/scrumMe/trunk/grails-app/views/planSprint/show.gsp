
<%@ page import="edu.tsi3.scrumme.PlanSprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'planSprint.label', default: 'PlanSprint')}" />
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
                            <td valign="top" class="name"><g:message code="planSprint.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: planSprintInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.titulo.label" default="Titulo" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: planSprintInstance, field: "titulo")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.lugar.label" default="Lugar" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: planSprintInstance, field: "lugar")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.fechaInicio.label" default="Fecha Inicio" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${planSprintInstance?.fechaInicio}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.fechafin.label" default="Fechafin" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${planSprintInstance?.fechafin}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.estado.label" default="Estado" /></td>
                            
                            <td valign="top" class="value">${planSprintInstance?.estado?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.proyecto.label" default="Proyecto" /></td>
                            
                            <td valign="top" class="value"><g:link controller="proyecto" action="show" id="${planSprintInstance?.proyecto?.id}">${planSprintInstance?.proyecto?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.acta.label" default="Acta" /></td>
                            
                            <td valign="top" class="value"><g:link controller="texto" action="show" id="${planSprintInstance?.acta?.id}">${planSprintInstance?.acta?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.invitaciones.label" default="Invitaciones" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${planSprintInstance.invitaciones}" var="i">
                                    <li><g:link controller="invitacion" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.sprint.label" default="Sprint" /></td>
                            
                            <td valign="top" class="value"><g:link controller="sprint" action="show" id="${planSprintInstance?.sprint?.id}">${planSprintInstance?.sprint?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="planSprint.documentos.label" default="Documentos" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${planSprintInstance.documentos}" var="d">
                                    <li><g:link controller="documento" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${planSprintInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
