<div>
	<div style="position: relative;">
		<h3 style="float:left; margin-bottom: 0px">${tarea.nombre }</h3>
		<span style="padding: 2px; background-color: white; position: absolute; right: 0px; top: 1px; font-weight: bold;" 
				title="${tarea.asignado?.userRealName }">
			${tarea.asignado?.nick }
		</span>
		<br>
		<p style="width: 270px; margin-bottom: 0px; padding-top: 2px" align="justify">
			${tarea.descripcion }
		</p>
	</div>
	<br>
	<div>
		<g:if test="${tarea.fechaIni != null }" >
			Inicio:<g:formatDate date="${tarea.fechaIni}" format="dd / MM / yyyy HH:mm" /><br>
		</g:if>
		<g:if test="${tarea.fechaFin != null }" >
			Fin:<g:formatDate date="${tarea.fechaFin}" format="dd / MM / yyyy HH:mm" />
		</g:if>
	</div>
</div>