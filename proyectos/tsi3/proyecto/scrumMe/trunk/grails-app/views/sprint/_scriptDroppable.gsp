<script type='text/javascript'>
	$(function() {
		$('#${ attrs.panelid}').droppable({
			drop: function(event, ui) {
				$.ajax({type: "GET", url: "${attrs.url}",data: "tarea="+ui.draggable.attr('id')+"&estado="+this.id+"&x="+ui.draggable.css('left')+"&y="+ui.draggable.css('top'),
					success: function(msg){
						//alert( 'Tarea ha finalizado' + msg );
					}
				});
			}
		});
	});
</script>