package com.luxsoft.sw4.rh

import org.apache.commons.logging.LogFactory

class ProcesadorDeVacaciones {
	
	def conceptoClave='P025'
	
	def concepto
	
	private static final log=LogFactory.getLog(this)
	
	def procesar(NominaPorEmpleado ne) {
		
		if(!concepto) {
			concepto=ConceptoDeNomina.findByClave(conceptoClave)
		}
		log.info "Procesando vacaciones para ${ne.empleado}"
		

		def empleado=ne.empleado
		def asistencia=ne.asistencia
		def salarioDiario=ne.salarioDiarioBase
		
		if(asistencia.vacaciones){
			
			//Localizar el concepto
			def nominaPorEmpleadoDet=ne.conceptos.find(){
				it.concepto==concepto
			}
			
			if(!nominaPorEmpleadoDet){
				nominaPorEmpleadoDet=new NominaPorEmpleadoDet(concepto:concepto,importeGravado:0.0,importeExcento:0.0,comentario:'PENDIENTE')
				ne.addToConceptos(nominaPorEmpleadoDet)
			}
			def importe=salarioDiario*asistencia.vacaciones
			if(importe){
				nominaPorEmpleadoDet.importeGravado=importe
				nominaPorEmpleadoDet.importeExcento=0
			}
			
		}
		
		if(asistencia.vacacionesp){
			//Localizar el concepto
			def conceptoPag=ConceptoDeNomina.findByClave('P031')
			
			def nominaPorEmpleadoDet=ne.conceptos.find(){
				it.concepto==conceptoPag
			}
			
			if(!nominaPorEmpleadoDet){
				nominaPorEmpleadoDet=new NominaPorEmpleadoDet(concepto:concepto,importeGravado:0.0,importeExcento:0.0,comentario:'PENDIENTE')
				ne.addToConceptos(nominaPorEmpleadoDet)
			}
			
			def importe=salarioDiario*asistencia.vacacionesp
			if(importe){
				nominaPorEmpleadoDet.importeGravado=importe
				nominaPorEmpleadoDet.importeExcento=0
			}
		}
		
		
				
		
	}
	
	def getModel(NominaPorEmpleadoDet det) {
		def ne=det.parent
		def asistencia=ne.asistencia
		def model=[:]
		model.salario=ne.salarioDiarioBase
		model.vacaciones=asistencia.vacaciones
		model.vacacionesp=asistencia.vacacionesp
		model.importe=det.importeGravado
		return model
	}
	
	def getTemplate() {
		return "/nominaPorEmpleado/conceptoInfo/percepcionVacaciones"
	}
	
	String toString() {
		"Procesador de vacaciones "
	}

}