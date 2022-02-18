
<%@ page import="edu.tsi3.scrumme.Proyecto" %>
<%@page import="edu.tsi3.scrumme.UsuarioProyecto"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        
        <title>Asignar usuarios</title>
    </head>
    <body>
        <div class="body">
            <h2>Proyecto: ${proyecto.nombre }</h2>
            <g:form action="guardarAsignacion" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	<g:each in="${usuarios }" var="u" >
	                            <tr class="prop">
	                                <td valign="top" class="name">
	                                    <label for="nombre">${u.userRealName }</label>
	                                </td>
	                                <td valign="top" class="value">
	                                    <% def usuproy = UsuarioProyecto.findByUsuarioAndProyecto(u,proyecto) %>
	                                    <g:if test="${usuproy != null }">
	                                    	<input type="checkbox" name="u_${u.id }" checked="checked" />
	                                    </g:if>
	                                    <g:else>
	                                    	<input type="checkbox" name="u_${u.id }" />
	                                    </g:else>
	                                </td>
	                            </tr>
                            </g:each>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                	<input type="hidden" name="pid" value="${proyecto.id }" />
                    <g:submitButton id="create" name="submit" class="save" value="${message(code: 'default.button.guardar.label', default: 'Guardar')}" />
                </div>
            </g:form>
            <script type="text/javascript">
	            $("#create").button({
		            icons: {
	                	primary: 'ui-icon-power'
	            	}
	            });
            </script>
        </div>
    </body>
</html>
