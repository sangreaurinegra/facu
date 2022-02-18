
<%@ page import="edu.tsi3.scrumme.Reunion" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'reunion.label', default: 'Reunion')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
       
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>${fieldValue(bean: reunionInstance, field: "titulo")}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            
                            
                        
                            <td valign="top" class="name"><g:message code="reunion.lugar.label" default="Lugar " /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: reunionInstance, field: "lugar")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="reunion.fechaInicio.label" default="Inicio" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${reunionInstance?.fechaInicio}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="reunion.fechaFin.label" default="Fin" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${reunionInstance?.fechaFin}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="reunion.estado.label" default="Estado" /></td>
                            
                            <td valign="top" class="value">${reunionInstance?.estado?.nombreEstado.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="reunion.proyecto.label" default="Proyecto" /></td>
                            
                            <td valign="top" class="value"><g:link controller="proyecto" action="show" id="${reunionInstance?.proyecto?.id}">${reunionInstance?.proyecto?.nombre.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="value"><g:link controller="texto" action="show" id="${reunionInstance?.acta?.id}">Acta</g:link></td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="reunion.invitaciones.label" default="Invitaciones" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${reunionInstance.invitaciones}" var="i">
                                    <li><g:link controller="invitacion" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="reunion.documentos.label" default="Documentos" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${reunionInstance.documentos}" var="d">
                                    <li><g:link controller="documento" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${reunionInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
        <%-- 
        <script type="text/javascript">
			$(function() {
				$("#dialog").dialog({ autoOpen: false });
				$("#dialogEdit").dialog({ autoOpen: false });
			});
			function mostrar(id){ 
				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'detalles', controller:'tarea') }",
					   data: "tarea="+id,
					   success: function(html){
						$("#dialog").html(html);
						$("#dialog").dialog('open');
					   }
					 });
				return false;
			}
			function mostrarEdit(id){ 
				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'dialogoedit', controller:'tarea') }",
					   data: "id="+id,
					   success: function(html){
						$("#dialogEdit").html(html);
						$("#dialogEdit").dialog('open');
					   }
					 });
				return false;
			}
			
		</script>
        
        <script type="text/javascript">
	$(function() {
		$("#ver_${reunionInstance?.acta?.id}").button({
            icons: {
               	primary: 'ui-icon-search'
           	},
           	text:false
           });
		$("#editar_${reunionInstance?.acta?.id}").button({
            icons: {
               	primary: 'ui-icon-pencil'
           	},
           	text:false
           });
	});
</script>
     --%>   
    </body>
</html>
