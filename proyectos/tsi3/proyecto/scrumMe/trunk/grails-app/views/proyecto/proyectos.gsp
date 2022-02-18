
<%@ page import="edu.tsi3.scrumme.util.EstadoProyecto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        
        <title>Proyectos</title>
    </head>
    <body>
        
        <div class="body">
            <h1>Proyectos</h1>
            <div class="dialog">
            	<div id="acordion">
	            	<g:each in="${proyectos }" var="proy">
	            		<h3><a>${proy.nombre } : ${proy.estado.nombreEstado }</a></h3>
	            		<div>
	            			<table>
		            			<g:each in="${proy.sprints.sort({it.numero}) }" var="sp">
		            				<tr>
		            					<td>${sp.numero }</td>
		            					<td>${sp.nombre }</td>
		            					<td>${sp.fraseObjetivo }</td>
		            					<td>Ini: <g:formatDate format="dd/MM/yyyy" date="${sp.fechaIni}" /></td>
		            					<td>Fin: <g:formatDate format="dd/MM/yyyy" date="${sp.fechaFin}" /></td>
		            					<td>${sp.textoEstado() }</td>
		            					<td><a id="sprint_${sp.id }" href="${ createLink(action:'dashboard', controller:'sprint',id:sp.id) }">
						       					Ir
						       				</a>
						       			</td>
		            				</tr>
		            				<script type="text/javascript">
										$(function() {
											$("#sprint_${sp.id }").button({
									            icons: {
								                	primary: 'ui-icon-arrowthick-1-e'
								            	}
								            });
										});
									</script>
		            			</g:each>
	            			</table>
	            			<div class="toolbar">
		            			<g:if test="${ proy.estado == EstadoProyecto.NO_INICIADO }">
									<a id="chg_proy_${proy.id }" href="${createLink(action:'ini', controller:'proyecto',id:proy.id) }">Iniciar</a>
								</g:if>
								<g:elseif test="${ proy.estado == EstadoProyecto.EN_DESA }">
									<a id="chg_proy_${proy.id }" href="${createLink(action:'fin', controller:'proyecto',id:proy.id) }">Finalizar</a>
								</g:elseif>
				                <a id="inijuego_${proy.id }" href="${ createLink(action:'ver', controller:'proyecto',id:proy.id) }">
			       					Ver
			       				</a> 
			       				<a id="asig_${proy.id }" href="${ createLink(action:'asignar', controller:'proyecto',id:proy.id) }">
			       					Asignar usuarios
			       				</a>
			       				<g:if test="${ proy.estado != EstadoProyecto.FINALIZADO }">
				       				<a id="c_sprint_${proy.id }" href="${ createLink(action:'create', controller:'sprint',params:[pid:proy.id]) }">
				       					Crear sprint
				       				</a>
			       				</g:if>
		       				</div>
		       				<script type="text/javascript">
								$(function() {
									$("#inijuego_${proy.id }").button({
							            icons: {
						                	primary: 'ui-icon-folder-open'
						            	}
						            });
									$("#asig_${proy.id }").button({
							            icons: {
						                	primary: 'ui-icon-person'
						            	}
						            });
									$("#c_sprint_${proy.id }").button({
							            icons: {
						                	primary: 'ui-icon-calendar'
						            	}
						            });
									$("#chg_proy_${proy.id }").button({
							            icons: {
						                	primary: 'ui-icon-power'
						            	}
						            });
								});
							</script>
	            		</div>
	            	</g:each>
            	</div>
				<div class="toolbar">
		            <a id="create" href="${ createLink(action:'create', controller:'proyecto') }">Crear</a>
		        </div>
            	<script type="text/javascript">
					$(function() {
						$("#acordion").accordion();
						$("#create").button({
				            icons: {
			                	primary: 'ui-icon-circle-plus'
			            	}
			            });
					});
				</script>
			</div>
        </div>
    </body>
</html>
