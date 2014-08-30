package com.luxsoft.sw4.rh

import java.math.RoundingMode;

import org.apache.commons.logging.LogFactory

import com.luxsoft.sw4.rh.imss.*

class ProcesadorDeIncentivo {
	
	def conceptoClave='P010'
	
	def concepto
	
	private static final log=LogFactory.getLog(this)
	
	def procesar(NominaPorEmpleado ne) {
		
		
		if(!concepto) {
			concepto=ConceptoDeNomina.findByClave(conceptoClave)
		}
		assert concepto,"Se debe de dar de alta el concepto de nomina: $conceptoClave"

		//Localizar el concepto
		def nominaPorEmpleadoDet=ne.conceptos.find(){ 
			it.concepto==concepto
		}
		if(!nominaPorEmpleadoDet){
				nominaPorEmpleadoDet=new NominaPorEmpleadoDet(concepto:concepto,importeGravado:0.0,importeExcento:0.0,comentario:'PENDIENTE')
				ne.addToConceptos(nominaPorEmpleadoDet)
		}
		
		def tipo=ne.empleado.perfil.tipoDeIncentivo
		
		
		def incentivo=Incentivo.findByTipoAndEmpleadoAndAsistencia(tipo,ne.empleado,ne.asistencia)
			
		if(incentivo){
			log.debug "Procesando Incentivo $incentivo.id (${ne.empleado} ) "
			def importeGravado=0.0

			switch(tipo) {
				case 'SEMANAL':
					importeGravado=calcularImporteSemanal(ne,incentivo)
						
					break
				case 'QUINCENAL':
					importeGravado=calcularImporteQuincenal(ne,incentivo)
					break
				case 'MENSUAL':
					importeGravado=calcularImporteMensual(ne,incentivo)
					break
			}
				
			if(importeGravado>0.0){
				//println 'Mayor a 0.0 agregar'
				nominaPorEmpleadoDet.importeGravado=importeGravado
				nominaPorEmpleadoDet.importeExcento=0
				ne.actualizar()
			}else{
				//println 'Menor a 0.0 quitar'
				ne.removeFromConceptos(nominaPorEmpleadoDet)
			}
		}else{
			if(nominaPorEmpleadoDet){
				ne.removeFromConceptos(nominaPorEmpleadoDet)
			
			}
		}

		
	}

	BigDecimal calcularImporteQuincenal(NominaPorEmpleado ne,Incentivo i){
		def importe=0.0
		def salario=ne.conceptos.find{it.concepto.clave=='P001'}
		def vac=ne.conceptos.find{it.concepto.clave=='P025'}
		if(salario ){
			def bono=(i.tasaBono1+i.tasaBono2)
			def vacImp=vac?vac.getTotal():0.0
			def total=+salario.total+vacImp
			log.debug 'Base para Incentivo  : '+total+ ' bono del: '+bono
			importe=total*(bono)
		}
		return importe
	}

	BigDecimal calcularImporteMensual(NominaPorEmpleado ne,Incentivo i){
		def importe=0.0
		def salario=ne.empleado.salario.salarioDiario
		importe=(salario*30)*i.tasaBono2
		return importe

	}
	
	BigDecimal calcularImporteSemanal(NominaPorEmpleado ne,Incentivo i){
		
		log.debug "Aplicando incentivo  Tasas $i.tasaBono1 y $i.tasaBono2"
		def importe=0.0
		def salario=ne.conceptos.find{it.concepto.clave=='P001'}
		def vac=ne.conceptos.find{it.concepto.clave=='P025'}
		if(salario ){
			def bono=(i.tasaBono1+i.tasaBono2)
			def vacImp=vac?vac.getTotal():0.0
			def total=+salario.total+vacImp
			importe=total*bono
			log.debug "Bono del $bono sobre $total total: $importe "
			
		}
		return importe
	}
	
	def getModel(NominaPorEmpleadoDet det) {
		def nominaEmpleado=det.parent
		def model=[:]
		return model
	}
	
	def getTemplate() {
		return "/nominaPorEmpleado/conceptoInfo/percepcionIncentivo"
	}
	
	String toString() {
		"Procesador de Incentivo "
	}

}
