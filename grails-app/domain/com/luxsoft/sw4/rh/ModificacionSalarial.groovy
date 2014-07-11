package com.luxsoft.sw4.rh

import groovy.transform.ToString
import org.grails.databinding.BindingFormat

@ToString(includes='empleado,tipo,salarioAnterior,salarioNuevo',includeNames=true,includePackage=false)
class ModificacionSalarial {

	Empleado empleado

	@BindingFormat('dd/MM/yyyy')
	Date fecha

	String tipo 



	BigDecimal salarioAnterior

	BigDecimal salarioNuevo

	BigDecimal sdiAnterior
	BigDecimal sdiNuevo
	
	String comentario

	Date dateCreated
	Date lastUpdated

    static constraints = {
    	tipo inList:['CALCULO_SDI','AUMENTO']
    	comentario nullable:true
    }

    static mapping = {
    	fecha type:'date'
    }
}