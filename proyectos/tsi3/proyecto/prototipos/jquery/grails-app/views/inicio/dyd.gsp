<html>
    <head>
        <title>Ejemplo drag and drop</title>
		<meta name="layout" content="main" />
		
		
    </head>
    <body>
		<style type="text/css">
			#draggable { width: 100px; height: 100px; padding: 0.5em; float: left; margin: 10px 10px 10px 0; }
			#draggable2 { width: 100px; height: 100px; padding: 0.5em; float: left; margin: 10px 10px 10px 0; }
			#droppable { width: 300px; height: 600px; padding: 0.5em; float: left;}
			#droppable2 { width: 300px; height: 600px; padding: 0.5em; float: left;}
			#droppable3 { width: 300px; height: 600px; padding: 0.5em; float: left;}
		</style>
		<script type="text/javascript">
			$(function() {
				$("#draggable").draggable();
				$("#draggable2").draggable();
				$("#droppable").droppable({
					drop: function(event, ui) {
						$(this).addClass('ui-state-highlight').find('p').html('Back log');
					}
				});
				$("#droppable2").droppable({
					drop: function(event, ui) {
						$(this).addClass('ui-state-highlight').find('p').html('En proceso');
					}
				});
				$("#droppable3").droppable({
					drop: function(event, ui) {
					$.ajax({
						   type: "GET",
						   url: "cambio",
						   data: "estado=fin",
						   success: function(msg){
						     alert( "Tarea ha finalizado" + msg );
						   }
						 });
					}
				});
		
			});
			
		</script>
		
		
		
		<script>
		  $(document).ready(function(){
		    $('#switcher').themeswitcher();
		  });
		</script>
		<script type="text/javascript"
		  src="http://jqueryui.com/themeroller/themeswitchertool/">
		</script>
		<div id="switcher"></div>
		
		<hr>
		<div class="demo">
		
		<div id="dialog" title="Basic dialog">
			<p>This is the default dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p>
			
		</div>
		<script type="text/javascript">
			$(function() {
				$("#dialog").dialog({ autoOpen: false });
			});
			function mostrar(id){ 
				$.ajax({
					   type: "GET",
					   url: "tarea",
					   data: "tarea="+id,
					   success: function(html){
						$("#dialog").append(html);
						$("#dialog").dialog('open');
					   }
					 });
				return false;
			}
		</script>
		
		<div class="demo">
			
		<div id="draggable" class="ui-widget-content">
			<p>Drag me to my target</p>
			<br>
			<a onclick="mostrar('mi tarea')">detalles >></a>
		</div>
		<div id="draggable2" class="ui-widget-content">
			<p>otra tarea</p>
			<br>
			<a onclick="mostrar('otra tarea')">detalles >></a>
		</div>
		<div id="droppable" class="ui-widget-header">
			<p>back log</p>
		</div>
		<div id="droppable2" class="ui-widget-header">
			<p>en proceso</p>
		</div>
		<div id="droppable3" class="ui-widget-header">
			<p>Finalizado</p>
		</div>
		</div>
    </body>
</html>