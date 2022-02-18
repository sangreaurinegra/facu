
<%@ page import="edu.tsi3.planningpoker.Mano" %>
<%@ page import="edu.tsi3.scrumme.Proyecto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'mano.label', default: 'Mano')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="mano.create.label" default="Crear mano" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${manoInstance}">
            <div class="errors">
                <g:renderErrors bean="${manoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tarea"><g:message code="mano.tarea.label" default="Tarea" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manoInstance, field: 'tarea', 'errors')}">
                                    <g:select name="tarea.id" from="${Proyecto.get(session.proyecto)?.backlog}" optionKey="id" value="${manoInstance?.tarea?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="juego"><g:message code="mano.juego.label" default="Juego" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manoInstance, field: 'juego', 'errors')}">
                                    <g:select name="juego.id" from="${edu.tsi3.planningpoker.Juego.list()}" optionKey="id" value="${params.jid}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <g:submitButton id="btn_crear" name="create" class="save" value="${message(code: 'mano.button.create.label', default: 'Crear')}" />
                    <a id="btn_finjuego" href="${ createLink(action:'finalizar', controller:'juego', params:[jid:params.jid]) }">
       					Fin del juego
       				</a>
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
				$("#btn_finjuego").button({
		            icons: {
	                	primary: 'ui-icon-power'
	            	}
	            });
				$("#btn_resumen").button({
		            icons: {
	                	primary: 'ui-icon-folder-open'
	            	}
	            });
			});
	        
        </script>
        </div>
    </body>
</html>
