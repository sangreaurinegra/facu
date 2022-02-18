
<%@ page import="edu.tsi3.scrumme.Reunion" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <script type="text/javascript" src="${resource(dir:'js/jquery', file:'timepicker.js')}" ></script>
        <g:set var="entityName" value="${message(code: 'reunion.label', default: 'Reunion')}" />
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
            <g:hasErrors bean="${reunionInstance}">
            <div class="errors">
                <g:renderErrors bean="${reunionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="titulo"><g:message code="reunion.titulo.label" default="Titulo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reunionInstance, field: 'titulo', 'errors')}">
                                    <g:textField name="titulo" value="${reunionInstance?.titulo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lugar"><g:message code="reunion.lugar.label" default="Lugar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reunionInstance, field: 'lugar', 'errors')}">
                                    <g:textField name="lugar" value="${reunionInstance?.lugar}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaInicio"><g:message code="reunion.fechaInicio.label" default="Fecha Inicio" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reunionInstance, field: 'fechaInicio', 'errors')}">
                                    <input type="text" name="fechaInicio" id="datetimeini" value="${formatDate(date:reunionInstance?.fechaInicio,format:'dd/MM/yyyy HH:mm')}">
                                    
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaFin"><g:message code="reunion.fechaFin.label" default="Fecha Fin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reunionInstance, field: 'fechaFin', 'errors')}">
                                    <input type="text" name="fechaFin" id="datetimefin" value="${formatDate(date:reunionInstance?.fechaFin,format:'dd/MM/yyyy HH:mm')}">
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="estado"><g:message code="reunion.estado.label" default="Estado" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: reunionInstance, field: 'estado', 'errors')}">
                                    <g:select name="estado" from="${edu.tsi3.scrumme.util.EstadoReunion?.values()}" value="${reunionInstance?.estado}" optionValue="nombreEstado"/>
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
    </body>
</html>
