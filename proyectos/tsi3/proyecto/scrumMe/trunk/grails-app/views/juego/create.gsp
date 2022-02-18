
<%@ page import="edu.tsi3.planningpoker.Juego" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'juego.label', default: 'Juego')}" />
         <script type="text/javascript" src="${resource(dir:'js/jquery', file:'timepicker.js')}" ></script>
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="juego.create.label" args="[entityName]" default="Crear juego" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${juegoInstance}">
            <div class="errors">
                <g:renderErrors bean="${juegoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nombre"><g:message code="juego.nombre.label" default="Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'nombre', 'errors')}">
                                    <g:textField name="nombre" value="${juegoInstance?.nombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaIni"><g:message code="juego.fechaIni.label" default="Fecha Inicio" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'fechaIni', 'errors')}">
                                    <input type="text" name="fechaIni" id="datetimeini" value="${formatDate(date:juegoInstance?.fechaIni,format:'dd/MM/yyyy HH:mm')}">
                                </td>
                            </tr> 
							<g:if test="${proy != null }">
	                            <tr class="prop">
	                                <td valign="top" class="name">
	                                    <label for="creador"><g:message code="juego.creador.label" default="Participantes" /></label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean: juegoInstance, field: 'creador', 'errors')}">
	                                    <table>
		                                    <g:each in="${ proy.usuarios }" var="up">
		                                    	<g:if test="${session.usuario.id != up.usuario.id }">
			                                    	<tr>
			                                    		<td>
			                                    			<input type="checkbox" name="u_${up.usuario.id }" />${up.usuario.userRealName }
			                                    		</td>
			                                    	</tr>
		                                    	</g:if>
		                                    </g:each>
	                                    </table>
	                                </td>
	                            </tr>
                        	</g:if>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton id="btn_crear" name="create" class="save" value="${message(code: 'juego.button.create.label', default: 'Crear')}" /></span>
                    <a id="btn_resumen" href="${ createLink(action:'resumen', controller:'juego') }">
       					Resumen juegos
       				</a>
                </div>
            </g:form>
            <script type="text/javascript">
	        $(function() {
				$("#btn_crear").button({
		            icons: {
	                	primary: 'ui-icon-calculator'
	            	},
	               	text:false
	            });
				$("#btn_resumen").button({
		            icons: {
	                	primary: 'ui-icon-folder-open'
	            	}
	            });

			});
	        
        </script>
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
});
</script>
    </body>
</html>
