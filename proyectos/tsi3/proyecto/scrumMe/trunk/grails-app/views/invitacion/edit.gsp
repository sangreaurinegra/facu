
<%@ page import="edu.tsi3.scrumme.Invitacion" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'invitacion.label', default: 'Invitacion')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Invitacion a Reunion ${invitacionInstance?.reunion?.titulo} en ${invitacionInstance?.reunion?.lugar} </h1>
            
           
          <h1>del Proyecto ${invitacionInstance?.reunion?.proyecto?.nombre}</h1>
          
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${invitacionInstance}">
            <div class="errors">
                <g:renderErrors bean="${invitacionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${invitacionInstance?.id}" />
                <g:hiddenField name="version" value="${invitacionInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fecha"><g:message code="invitacion.fecha.label" default="Fecha" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: invitacionInstance, field: 'fecha', 'errors')}">
                                    <g:datePicker name="fecha" precision="day" value="${invitacionInstance?.fecha}"  />
                                </td>
                            </tr>
                        
                         <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaRespuesta"><g:message code="invitacion.fechaRespuesta.label" default="Fecha Respuesta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: invitacionInstance, field: 'fechaRespuesta', 'errors')}">
                                    <g:datePicker name="fechaRespuesta" precision="day" value="${invitacionInstance?.fechaRespuesta}" noSelection="['': '']" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="estado"><g:message code="invitacion.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: invitacionInstance, field: 'estado', 'errors')}">
                                    <g:select name="estado" from="${edu.tsi3.scrumme.util.EstadoInvitacion?.values()}" value="${fieldValue(bean: invitacionInstance, field: 'estado')}" optionValue="nombreEstado" />
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
