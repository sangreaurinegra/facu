
<%@ page import="edu.tsi3.scrumme.Tarea" %>
<%@ page import="edu.tsi3.scrumme.Proyecto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tarea.label', default: 'Tarea')}" />
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
            <g:hasErrors bean="${tareaInstance}">
            <div class="errors">
                <g:renderErrors bean="${tareaInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nombre"><g:message code="tarea.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${tareaInstance?.nombre}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="descripcion"><g:message code="tarea.descripcion.label" default="Descripcion" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'descripcion', 'errors')}">
                                    <g:textArea name="descripcion" cols="40" rows="5" value="${tareaInstance?.descripcion}" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tipo"><g:message code="tarea.tipo.label" default="Tipo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'tipo', 'errors')}">
                                    <g:select name="tipo.id" from="${edu.tsi3.scrumme.TipoTarea.list()}" optionKey="id" value="${tareaInstance?.tipo?.id}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="estimacion"><g:message code="tarea.nombre.label" default="Estimación" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'estimacion', 'errors')}">
                                    <g:textField name="estimacion" value="${tareaInstance?.estimacion}" size="4"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sprint"><g:message code="tarea.sprint.label" default="Sprint" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'sprint', 'errors')}">
                                	<%
                                		def valor = tareaInstance?.sprint?.id
                                		if(sprint)
                                			valor = new Long(sprint)
                                	%>
                                    <g:select name="sprint.id" from="${proyecto?.sprints}" optionKey="id" value="${valor}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton id="create" name="create" class="save" value="${message(code: 'tarea.button.create.label', default: 'Crear')}" /></span>
                </div>
                <g:if test="${sprint != null }">
                	<input type="hidden" name="sprintid" value="${sprint }"/>
                </g:if>
            </g:form>
        </div>
        <script type="text/javascript">
			$(function() {
				$("#create").button({
		            icons: {
		               	primary: 'ui-icon-search'
		           	}
		           });

			});
		</script>
    </body>
</html>
