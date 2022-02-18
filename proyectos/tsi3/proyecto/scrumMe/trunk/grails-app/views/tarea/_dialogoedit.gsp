
<%@ page import="edu.tsi3.scrumme.Tarea" %>
<div class="body">
    <h3>Editar: ${tareaInstance?.nombre}</h3>
    <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${tareaInstance}">
    <div class="errors">
        <g:renderErrors bean="${tareaInstance}" as="list" />
    </div>
    </g:hasErrors>
    <g:form method="post" >
        <g:hiddenField name="id" value="${tareaInstance?.id}" />
        <g:hiddenField name="version" value="${tareaInstance?.version}" />
        <div class="dialog">
            <table>
                <tbody>
                
                	<tr class="prop">
                        <td valign="top" class="name">
                          <label for="nombre"><g:message code="tarea.nombre.label" default="Nombre" /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'nombre', 'errors')}">
                            <g:textField id="nombre" name="nombre" value="${tareaInstance?.nombre}" />
                        </td>
                    </tr>
                    
                    <tr class="prop">
                        <td valign="top" class="name">
                          <label for="descripcion"><g:message code="tarea.descripcion.label" default="Descripcion" /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'descripcion', 'errors')}">
                            <g:textArea id="descripcion" name="descripcion" cols="40" rows="5" value="${tareaInstance?.descripcion}" />
                        </td>
                    </tr>

					<tr class="prop">
                        <td valign="top" class="name">
                            <label for="estimacion"><g:message code="tarea.nombre.label" default="Estimación" /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'estimacion', 'errors')}">
                            <g:textField name="estimacion" value="${tareaInstance?.estimacion}" size="4"/>
                        </td>
                    </tr>
					
                    <tr class="prop">
                        <td valign="top" class="name">
                          <label for="tipo"><g:message code="tarea.tipo.label" default="Tipo" /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: tareaInstance, field: 'tipo', 'errors')}">
                            <g:select id="tipo" name="tipo" from="${edu.tsi3.scrumme.TipoTarea.list()}" optionKey="id" value="${tareaInstance?.tipo?.id}"  />
                        </td>
                    </tr>
                
                </tbody>
            </table>
        </div>
        <br />
        <input id="refrescar_dashboard" name="refrescar_dashboard" type="checkbox" checked="checked"  />Refrescar dashboard al guardar.
        <div class="buttons">
        	<a id="btn_update" onclick="updateFunc(${tareaInstance?.id})">Guardar</a>
            <a id="btn_cerrar" onclick="$('#dialogEdit').dialog('close');">Cerrar</a>
        </div>
    </g:form>
    
</div>


