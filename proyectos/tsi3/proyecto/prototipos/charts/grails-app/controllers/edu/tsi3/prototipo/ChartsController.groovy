package edu.tsi3.prototipo

import java.util.ArrayList;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ContentType;

import grails.converters.XML

class ChartsController {
	
	// def cantTareas = { render Post.list() as XML }
	
	def index = {
			
			
	}
	
	def pru1 = { 
		
		Chart ch = crearChart()
		
		render ch as XML
		
	}
	
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
	
	def pru2 = { 
			Chart ch = crearChart()
			render(ContentType:"text/xml"){
				chart(caption:'tsi 3 2010',showPercentageValues:'1'){
					for(s in ch.getSet()){
						Set(label:s.label, value:s.value)
						
					}
				}
				
			}
	}
	
}
