package com.luxsoft.sw4



import grails.validation.Validateable;

import java.text.DateFormat
import java.text.SimpleDateFormat

import org.grails.databinding.BindingFormat;

@Validateable
class Periodo {
	
	@BindingFormat('dd/MM/yyyy')
	Date fechaInicial
	
	@BindingFormat('dd/MM/yyyy')
	Date fechaFinal
	
	static String defaultFormat='dd/MM/yyyy'
	
	static constraints = {
		fechaInicial()
		fechaFinal(nullable:false,validator:{val,object->
			if(val<object.fechaInicial)
				return 'fechaFinal.anteriorAFechaInicial'
			else
				return true
		})
	}
	
	Periodo(){
		fechaInicial=new Date()
		fechaFinal=new Date()
	}
	
	Periodo(String f1,String f2){
		fechaInicial=Date.parse(defaultFormat, f1).clearTime()
		fechaFinal=Date.parse(defaultFormat,f2).clearTime()
	}
	
	String toString(){
		"${fechaInicial.format(defaultFormat)} - ${fechaFinal.format(defaultFormat)}"
	}
	
	def int dias(){
		return fechaFinal-fechaInicial
	}
	
	

}
