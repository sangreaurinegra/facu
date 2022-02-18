<div id="${tarea.id }" class=" draggable" style="position: absolute; top: ${y}px; left: ${x}px; background-color:#${tarea.tipo.color}; z-index:9">
	<div style="position: relative; height:99px;">
		<h3 >${tarea.nombre }</h3>
		<span style="padding: 2px; background-color: white; position: absolute; right: 0px; top: 1px; font-weight: bold;"
			title="${tarea.asignado?.userRealName }">${tarea.asignado?.nick }</span>
		<span style="padding: 2px; position: absolute; right: 0px; top: 23px; color: black;">${tarea.estimacion }</span>
		
		<div style="position: absolute; bottom: 1px;">
			<a id="ver_${tarea.id }" onclick="mostrar(${tarea.id })" title="Ver detalles" style="float: left; ">Ver detalles</a>			
			<a id="editar_${tarea.id }" onclick="mostrarEdit(${tarea.id })" title="Editar" style="margin-left: 30px">Editar</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("#ver_${tarea.id }").button({
            icons: {
               	primary: 'ui-icon-search'
           	},
           	text:false
           });
		$("#editar_${tarea.id }").button({
            icons: {
               	primary: 'ui-icon-pencil'
           	},
           	text:false
           });
	});
</script>