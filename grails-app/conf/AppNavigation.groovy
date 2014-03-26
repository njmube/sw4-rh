navigation={
	app{
		home()
		catalogos(){
			puesto(controller:'puesto',action:'index')
			empleado(controller:'empleado',action:'index')
			departamento(controller:'departamento',action:'index')
			ubicacion(controller:'ubicacion',action:'index')
			conceptos(controller:'conceptoDeNomina', action:'index'){
				percepciones()
				deducciones()
			}
            sat(controller:'catalogosDelSat'){
            	bancos()
            	percepciones()
            	deducciones()
            	incapacidades()
            	regimenes(titleText:'Tipos de Régimen')  
            	riesgos()
            }
		}
		operaciones(){
			tiempoExtras(controller:'tiempoExtra',action:'index')
			nomina(controller:'nomina',action:'index')
			compensaciones()
			aportaciones()
			incapacidades()
			
		}
	}
}