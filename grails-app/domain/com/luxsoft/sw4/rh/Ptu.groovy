package com.luxsoft.sw4.rh

import groovy.transform.EqualsAndHashCode
import org.grails.databinding.BindingFormat

@EqualsAndHashCode(includes='ejercicio')
class Ptu {
    
	Integer ejercicio

	BigDecimal remanente=0.0 	//Remanente del ejercicio anterior (Lo que quedo sin repartir)
	BigDecimal monto=0.0 //Monto determinado a repartir del ejercicio 
	BigDecimal factor=1.2

	/** Propiedades dinamicas **/
	BigDecimal salarioTope   // El salario neto mas alto entre los empleados sindicalizados * factor
	BigDecimal total     //monto+salarioTipo+remanente
	
	Date dateCreated
	Date lastUpdated


    static constraints = {
		ejercicio inList:[2014,2015,2016,2017,2018]

    }

    static mapping = {
		partidas cascade: "all-delete-orphan"
	}

	static hasMany = [partidas: PtuDet]

    

	static transients = ['diasDelEjercicio','salarioTope']

    String toString(){
    	return "PTU $ejercicio  "
    }

    public Integer getDiasDelEjercicio(){
    	if(!diasDelEjercicio){
    		use(TimeCategory){
    			def duration= fechaFinal-fechaInicial+1.day
    			diasDelEjercicio=duration.days
    		}
    	}
    	return diasDelEjercicio
		
	}

	def getSalarioTope(){
		def found=getEmpleadoTope()
		return found?.getSalarioNeto()?:0.0
	}

	def getEmpleadoTope(){
		def found=partidas.max({if(it.empleado.perfil.tipo=='SINDICALIZADO') it.getSalarioNeto()})
		return found
	}

	def getTotal(){
		return monto-getSalarioTope()+remanente
	}
	


}

