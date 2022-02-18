<%@ page import="com.infosoftglobal.fusioncharts.FusionChartsCreator"%>
<%@ page import="edu.tsi3.scrumme.Proyecto" %>
<%@ page import="edu.tsi3.scrumme.Sprint" %>
<%@ page import="edu.tsi3.scrumme.util.EstadoProyecto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Resumen de proyecto: ${proyecto.nombre }</title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
            	<div class="toolbar">
            		<g:if test="${ proyecto.estado == EstadoProyecto.NO_INICIADO }">
						<a id="chg_proy" href="${createLink(action:'ini', controller:'proyecto',id:proyecto.id) }">Iniciar</a>
						&nbsp;&nbsp;
					</g:if>
					<g:elseif test="${ proyecto.estado == EstadoProyecto.EN_DESA }">
						<a id="chg_proy" href="${createLink(action:'fin', controller:'proyecto',id:proyecto.id) }">Finalizar</a>
						&nbsp;&nbsp;
					</g:elseif>
       				<a id="asig" href="${ createLink(action:'asignar', controller:'proyecto',id:proyecto.id) }">
       					Asignar usuarios
       				</a>
       				&nbsp;&nbsp;
       				<g:if test="${ proyecto.estado != EstadoProyecto.FINALIZADO }">
	       				<a id="c_sprint" href="${ createLink(action:'create', controller:'sprint',params:[pid:proyecto.id]) }">
	       					Crear sprint
	       				</a>
       				</g:if>
       				<g:if test="${session.sprint_desa != null }">
	       				&nbsp;&nbsp;
	       				<a id="d_sprint" href="${ createLink(action:'dashboard', controller:'sprint',id:session.sprint_desa) }">
	       					Sprint Actual
	       				</a>
       				</g:if>
            	</div>
            	<script type="text/javascript">
					$(function() {
						$("#asig").button({
				            icons: {
			                	primary: 'ui-icon-person'
			            	}
			            });
						$("#c_sprint").button({
				            icons: {
			                	primary: 'ui-icon-calendar'
			            	}
			            });
						$("#d_sprint").button({
				            icons: {
			                	primary: 'ui-icon-arrowthick-1-e'
			            	}
			            });
						$("#chg_proy").button({
				            icons: {
			                	primary: 'ui-icon-power'
			            	}
			            });
					});
				</script>
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            
                            <td valign="top" >
                            	<g:message code="proyecto.fechaIni.label" default="Inicio: " />
                            	<g:formatDate date="${proyecto?.fechaIni}" format="dd/MM/yyyy"/>
                            	-
                            	<g:message code="proyecto.fechaFin.label" default="Fin: " />
                            	<g:formatDate date="${proyecto?.fechaFin}" format="dd/MM/yyyy" />	
                            	&nbsp;&nbsp;
                    			<g:message code="proyecto.estado.label" default="Estado: " />
                    			<b>${proyecto?.estado.nombreEstado}</b>        	
                            </td>
                            <td rowspan="6">
                            	<div style="height: 150px; width: 300px; float: right;">
                            		<%
									//Create the chart - Column 3D Chart with data from Data/Data.xml
									String chartHTMLCode2 = FusionChartsCreator.createChartHTML(
											"${resource(dir:'swf',file:'Pie3D.swf')}?",
											"${createLink(action:'pieTareasTipo', controller:'chart',id:proyecto.id) }", "",
											"tsi 3 2010", 300, 150, false);
								%> <%=chartHTMLCode2%>
                            	</div>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">
	                            <g:link controller="proyecto" action="backlog" id="${proyecto.id}">
	                            	<g:message code="proyecto.backlog.label" default="Backlog" />
	                            </g:link>
                            </td>                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">
                            	<g:message code="proyecto.sprints.label" default="Sprints" />
                            	&nbsp;&nbsp;(
                            	<span style="color:blue">No iniciado, </span>
                            	<span style="color:orange">En desa, </span>
                            	<span style="color:green">Finalizado </span>
                            	)
                            </td>
                        </tr>    
                        <tr class="prop">
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${proyecto.sprints.sort({it.numero})}" var="s">
                                	<%
                                		def color = 'blue'
                                		if(s.estado == Sprint.ESTADO_DESA)
                                			color = 'orange'
                                		else if(s.estado == Sprint.ESTADO_FINALIZADO)
                                			color = 'green'
                                	%>
                                    <li style="float: left; margin-right: 10px;">
                                    	<g:link controller="sprint" action="dashboard" id="${s.id}" style="color:${color }">${s?.encodeAsHTML()}</g:link>
                                    </li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="proyecto.usuarios.label" default="Usuarios" /></td>
                        </tr>    
                        <tr class="prop">
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${proyecto.usuarios}" var="u">
                                    <li style="float: left; margin-right: 10px;">
                                    	<g:link controller="usuario" action="show" id="${u.usuario.id}">
                                    		${u?.usuario.userRealName.encodeAsHTML()}
                                    	</g:link>
                                    </li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <%
	//Create the chart - Column 3D Chart with data from Data/Data.xml
	String chartHTMLCode = FusionChartsCreator.createChartHTML(
			"${resource(dir:'swf',file:'Area2D.swf')}",
			"${createLink(action:'burndown', controller:'chart',id:proyecto.id) }", "",
			"tsi 3 2010", 710, 355, false);
%> <%=chartHTMLCode%></div>
        </div>
        
        
        
        
    </body>
</html>
