
<%@ page import="edu.tsi3.scrumme.Sprint" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sprint.label', default: 'Sprint')}" />
        <script type="text/javascript" src="${resource(dir:'js/jquery', file:'timepicker.js')}" ></script>
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
            <g:hasErrors bean="${sprintInstance}">
            <div class="errors">
                <g:renderErrors bean="${sprintInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numero"><g:message code="sprint.numero.label" default="Número" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'numero', 'errors')}">
                                    <g:textField name="numero" value="${fieldValue(bean: sprintInstance, field: 'numero')}" />
                                </td>
                            </tr>
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
                                    <input type="text" name="fechaIni" id="datetimeini" value="${formatDate(date:sprintInstance?.fechaIni,format:'dd/MM/yyyy HH:mm')}">
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaFin"><g:message code="sprint.fechaFin.label" default="Fecha Fin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'fechaFin', 'errors')}">
                                    <input type="text" name="fechaFin" id="datetimefin" value="${formatDate(date:sprintInstance?.fechaFin,format:'dd/MM/yyyy HH:mm')}">
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="proyecto"><g:message code="sprint.proyecto.label" default="Proyecto" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sprintInstance, field: 'proyecto', 'errors')}">
                                	<% def proy = pid?pid:sprintInstance?.proyecto?.id %>
                                    <g:select name="proyecto.id" from="${edu.tsi3.scrumme.Proyecto.list()}" optionKey="id" value="${proy}"  />
                                </td>
                            </tr>
                        
                            
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                	<input type="hidden" name="pid" value="${pid }" />
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
<script type="text/javascript">
$(function() {
	$('#datetimeini').datepicker({
	duration: '',
        showTime: true,
        constrainInput: false,
        stepMinutes: 1,
        stepHours: 1,
        altTimeField: '',
        time24h: true ,
        dateFormat: 'dd/mm/yy'
     });
	$('#datetimefin').datepicker({
		duration: '',
	        showTime: true,
	        constrainInput: false,
	        stepMinutes: 1,
	        stepHours: 1,
	        altTimeField: '',
	        time24h: true ,
	        dateFormat: 'dd/mm/yy'
	     });
	    
});
</script>
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
