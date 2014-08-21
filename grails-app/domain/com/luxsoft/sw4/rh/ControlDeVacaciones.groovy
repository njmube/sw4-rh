package com.luxsoft.sw4.rh

import org.apache.commons.lang.time.DateUtils;

class ControlDeVacaciones {

	Empleado empleado
	
	Date aniversario
	
	Long ejercicio

	BigDecimal acumuladoExcento

	BigDecimal acumuladoGravado
	
	int antiguedadDias
	
	int antiguedadYears

	int diasVacaciones
	
	int diasTrasladados=0

	int diasTomados

	int diasDisponibles

	Date dateCreated

	Date lastUpdated


    static constraints = {
		empleado unique:['ejercicio']
    }

    static transients = ['diasDisponibles','vigencia']

    int getDiasDisponibles(){
    	return diasVacaciones+diasTrasladados-diasTomados
    }

    String toString(){
    	"$ejercicio $empleado $diasDisponibles"
    }
	
	static mapping = {
		aniversario type:'date'
	}
	
	Date getVigencia(){
		if(aniversario)
			return DateUtils.addMonths(aniversario, 6)
		return null
	}
	
}