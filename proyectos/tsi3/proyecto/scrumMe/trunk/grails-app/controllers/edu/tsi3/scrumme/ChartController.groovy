package edu.tsi3.scrumme

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import edu.tsi3.scrumme.util.EstadoTarea;


class ChartController {
	
	def index = { }
	
	
	
	private Chart crearChart(){
		Chart ch = new Chart()
		//  palette='2' caption='Monthly Unit Sales' xAxisName='Month' yAxisName='Units' showValues='0' decimals='0' formatNumberScale='0'
		ch.palette = "2"
		ch.caption = "poronga 1"
		ch.xAxisName = "poronga 2"
		ch.yAxisName = "poronga 3"
		ch.showValues = "0"
		ch.decimals = "0"
		ch.formatNumberScale = "0"
		
		
		Set s1 = new Set()
		s1.label = "Ene"
		s1.value = "420"
		ch.set.add s1
		
		Set s2 = new Set()
		s2.label = "Feb"
		s2.value = "150"
		ch.set.add s2
		
		
		Set s3 = new Set()
		s3.label = "Mar"
		s3.value = "220"
		ch.set.add s3
		return ch
	}
	
	private Chart burndownImpl(long idproyecto){
		Chart ch = new Chart()
		ch.palette = "3"
		ch.caption= 'Burn Down Sprint en curso'
		def proyectoInstance = Proyecto.get(idproyecto)
		if (!proyectoInstance) {
			log.error= '[Chart] No encaro el proyecto con id '+idproyecto
			return ch
		}
		Sprint sprint = null
		if(session.sprint_desa)
			sprint = Sprint.get(session.sprint_desa)
		
		int estimacion = 0
		Hashtable mapa = new Hashtable()
		def fechaIni = sprint?sprint.getFechaIni(): proyectoInstance.getFechaIni()
		def fechaFin = sprint?sprint.getFechaFin(): proyectoInstance.getFechaFin()
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
		double valorAux
		//    	Recorro las tareas y cargo hash con creciones y finalizaciones
		def tareas = sprint? sprint.tareas : proyectoInstance.backlog
		for (Tarea tarea : tareas) {
			// Creacion 			
			if(tarea.fechaCreacion<=fechaIni){
				
				agregarAlMapa(mapa,sdf.format(fechaIni),tarea.estimacion ) 
				
			}else{
				agregarAlMapa(mapa,sdf.format(tarea.fechaCreacion),tarea.estimacion )
			}
			
			// Finalizadas
			if(tarea.estado==EstadoTarea.FINALIZADA){
				agregarAlMapa(mapa,sdf.format(tarea.fechaFin),-tarea.estimacion )
			}
		}
		
		//		Recorro las fechas y calculo valor dia a dia
		
		double lastre = 0
		
		def fechaIniChart =  fechaIni
		
		def days = 0
		
		if(sprint != null){
			days =  fechaFin - fechaIni
		}else{
			days = new Date() - fechaIni
		}
		
		Set set 
		
		for(int i=0 ; i<=days ; i++){
			set = new Set()
			set.label = sdf.format(fechaIniChart + i)
			if(mapa.get(sdf.format(fechaIniChart + i))){
				lastre = lastre + mapa.get(sdf.format(fechaIniChart + i))
			}
			set.value = lastre
			ch.set.add set
		}
		return ch
	}
	
	
	private Chart pieTareasTipoImpl(long idproyecto){
		Chart ch = new Chart()
		ch.palette = "3"
		ch.caption= 'Tarea por Tipo'
		def proyectoInstance = Proyecto.get(idproyecto)
		if (!proyectoInstance) {
			log.error= '[Chart] No encaro el proyecto con id '+idproyecto
			return ch
		}
		else {
			[proyectoInstance: proyectoInstance]
		}
		
		Hashtable mapa = new Hashtable()
		
		//    	Recorro las tareas y cargo hash con los tipos y las cantidades 
		for (Tarea tarea : proyectoInstance.backlog) {
			// Creacion 
			agregarAlMapa(mapa,tarea.tipo.getNombre(),1.0)
		}
		
		Set set 
		mapa.keys()
		for(String key : mapa.keys()){
			set = new Set()
			
			set.value =  mapa.get(key)
			
			set.color = TipoTarea.findByNombre(key).color
			
			
			set.label = key.length()>4 ? key.substring(0, 4): key
			
			ch.set.add set
		}

		return ch
	}
	
	private agregarAlMapa(Hashtable mapa,String clave,double valor ){
		if(mapa.get(clave)){
			mapa.put(clave,valor+mapa.get(clave))
		}else{
			mapa.put(clave,valor)
		}
	}
	
	def burndown = { 

			Chart ch = burndownImpl(Long.parseLong(params.id)) //session.proyecto)
			render(ContentType:"text/xml"){
				chart(caption:ch.caption, showPercentageValues:'1', plotFillColor:'2F8EF8', plotGradientColor:'F829BD' ,plotBorderColor:'F89327' ,yAxisName:'horas'){
					for(s in ch.getSet()){
						Set(label:s.label, value:s.value)
						
					}
				}
			}
		
	}
	
	def pieTareasTipo = { 

			Chart ch = pieTareasTipoImpl(Long.parseLong(params.id))
			render(ContentType:"text/xml"){
				chart(caption:ch.caption){
					for(s in ch.getSet().sort({it.value})){
						Set(label:s.label, value:s.value, color:s.color)
						
					}
				}
			}
		
	}
	
}
