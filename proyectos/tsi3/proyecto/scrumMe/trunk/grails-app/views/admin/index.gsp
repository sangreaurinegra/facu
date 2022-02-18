
<%@ page import="edu.tsi3.scrumme.Alarma" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        
        <title>Administración</title>
    </head>
    <body>
        <div class="body">
            <h2>Administración del sistema</h2>
            <div class="dialog">
            	<h3>Administración de usuarios</h3>
                <table>
					<tr class="prop">
						<td valign="top">
							<a id="btn_adm_usuarios" href="${createLink(action:'list', controller:'usuario') }">Ver usuarios</a>
							&nbsp;&nbsp;
							<a id="btn_adm_crear_usuario" href="${createLink(action:'create', controller:'usuario') }">Crear usuario</a>
						</td>
						<td valign="top">
							<a id="btn_adm_roles" href="${createLink(action:'list', controller:'role') }">Ver roles</a>
							&nbsp;&nbsp;
							<a id="btn_adm_crear_rol" href="${createLink(action:'create', controller:'role') }">Crear rol</a>
						</td>                  
					</tr>
                </table>
				<br>
				<h3>Permisos de la aplicación</h3>
                <table>
					<tr class="prop">
						<td valign="top">
							<a id="btn_adm_reqmap" href="${createLink(action:'list', controller:'requestMap') }">Ver permisos</a>
						</td>
						<td valign="top">
							<a id="btn_adm_crear_reqmap" href="${createLink(action:'create', controller:'requestMap') }">Crear permiso</a>
						</td>                  
					</tr>
                </table>
                <br>
				<h3>Configuración para proyectos</h3>
				<table>
					<tr class="prop">
						<td valign="top">
							<a id="btn_adm_tipousr" href="${createLink(action:'list', controller:'tipoUsuario') }">Ver tipos de usuario</a>
							&nbsp;&nbsp;
							<a id="btn_adm_crear_tipousr" href="${createLink(action:'create', controller:'tipoUsuario') }">Crear tipo de usuario</a>
						</td>
						<td valign="top">
							<a id="btn_adm_tipotarea" href="${createLink(action:'list', controller:'tipoTarea') }">Ver tipos de tarea</a>
							&nbsp;&nbsp;
							<a id="btn_adm_crear_tipotarea" href="${createLink(action:'create', controller:'tipoTarea') }">Crear tipo tarea</a>
						</td>                  
					</tr>
				</table>
				<br>
				<h3>Otros</h3>
				<table>
					<tr class="prop">
						<td valign="top">
							<a id="btn_adm_alarmas" href="${createLink(action:'list', controller:'alarma') }">Ver alarmas</a>
						</td>
						<td valign="top">
							<a id="btn_adm_mensajes" href="${createLink(action:'list', controller:'mensaje') }">Ver mensajes</a>
						</td>                  
					</tr>
				</table>
            </div>
            <script type="text/javascript">
				$(function() {
					$("#btn_adm_usuarios").button({
				           icons: {
				              	primary: 'ui-icon-search'
				          	}
				          });
					$("#btn_adm_crear_usuario").button({
				           icons: {
				              	primary: 'ui-icon-circle-plus'
				          	}
				          });
					$("#btn_adm_alarmas").button({
				           icons: {
				              	primary: 'ui-icon-search'
				          	}
				          });
					$("#btn_adm_mensajes").button({
				           icons: {
				              	primary: 'ui-icon-search'
				          	}
				          });
					$("#btn_adm_reqmap").button({
				           icons: {
				              	primary: 'ui-icon-search'
				          	}
				          });
					$("#btn_adm_crear_reqmap").button({
				           icons: {
				              	primary: 'ui-icon-circle-plus'
				          	}
				          });
					$("#btn_adm_roles").button({
				           icons: {
				              	primary: 'ui-icon-search'
				          	}
				          });
					$("#btn_adm_crear_rol").button({
				           icons: {
				              	primary: 'ui-icon-circle-plus'
				          	}
				          });
					$("#btn_adm_tipousr").button({
				           icons: {
				              	primary: 'ui-icon-search'
				          	}
				          });
					$("#btn_adm_crear_tipousr").button({
				           icons: {
				              	primary: 'ui-icon-circle-plus'
				          	}
				          });
					$("#btn_adm_tipotarea").button({
				           icons: {
				              	primary: 'ui-icon-search'
				          	}
				          });
					$("#btn_adm_crear_tipotarea").button({
				           icons: {
				              	primary: 'ui-icon-circle-plus'
				          	}
				          });
				});
			</script>
        </div>
    </body>
</html>
