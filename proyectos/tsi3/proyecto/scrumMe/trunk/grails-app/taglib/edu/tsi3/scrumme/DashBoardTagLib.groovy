package edu.tsi3.scrumme

class DashBoardTagLib {
	static namespace = "scrumme"
	
	def draggable = {attrs ->
		out << "<script type='text/javascript'>"
		out << '    $(function() {$("#'+"${attrs.tareaid}"+'").draggable();});'
		out << "</script>"	
	}
	
	def droppable = {attrs ->
		out << render(template:'../sprint/scriptDroppable', model:[attrs:attrs])	
	}
	
	def tareaDraggable = {attrs ->
		def tarea = Tarea.get(new Long(attrs.tareaid))
		def x = tarea.x==0?attrs.x:tarea.x
		def y = tarea.y==0?attrs.y:tarea.y		
		out << render(template:'../tarea/draggable', model:[tarea:tarea, x:x, y:y])
	}
	/*
	 * positiones iniciales:
	 *      backlog - proceso - finalizada
	 * top   105    -  105    -   105
	 * left  22     -  337    -   650
	 * limite derecho
	 *       318    -  630    -   945
	 * */
}
