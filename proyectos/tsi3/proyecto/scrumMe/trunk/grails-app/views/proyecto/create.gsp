
<%@ page import="edu.tsi3.scrumme.Proyecto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <script type="text/javascript" src="${resource(dir:'js/jquery', file:'timepicker.js')}" ></script>
        <g:set var="entityName" value="${message(code: 'proyecto.label', default: 'Proyecto')}" />
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
            <g:hasErrors bean="${proyectoInstance}">
            <div class="errors">
                <g:renderErrors bean="${proyectoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                                    <input type="text" name="fechaIni" id="datetimeini" value="${formatDate(date:proyectoInstance?.fechaIni,format:'dd/MM/yyyy HH:mm')}">
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaFin"><g:message code="proyecto.fechaFin.label" default="Fecha Fin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: proyectoInstance, field: 'fechaFin', 'errors')}">
                                    <input type="text" name="fechaFin" id="datetimefin" value="${formatDate(date:proyectoInstance?.fechaFin,format:'dd/MM/yyyy HH:mm')}">
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton id="create" name="create" class="save" value="${message(code: 'proyecto.button.create.label', default: 'Crear')}" /></span>
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
