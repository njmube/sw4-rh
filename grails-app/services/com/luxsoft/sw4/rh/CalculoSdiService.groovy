package com.luxsoft.sw4.rh

import grails.transaction.Transactional

@Transactional
class CalculoSdiService {

    def calcularSdi(ModificacionSalarial m) {

    	log.info "Calculando SDI para empledo: $m.empleado"
    	def rows=[]
    	["QUINCENA","SEMANA"].each{
    		
    		log.info "SDI para $ejercicio  bimestre $bimestre tipo $it"
    		
    		def res=CalendarioDet
    		.executeQuery("select min(d.inicio),max(d.fin) from CalendarioDet d where d.bimestre=? and d.calendario.tipo=? and d.calendario.ejercicio=?"
    		,[bimestre,it,ejercicio])
    	
    		def inicio=res.get(0)[0]
    		def fin=res.get(0)[1]
    		log.info "Periodo: $inicio al $fin"
    		
    		def zona=ZonaEconomica.findByClave('A')
    	
    		def query=sqlPorBimestre_old
    			.replaceAll('@FECHA_INI',inicio.format('yyyy/MM/dd'))
    			.replaceAll('@FECHA_FIN',fin.format('yyyy/MM/dd'))
    			.replaceAll('@FECHA_ULT_MODIF',fin.format('yyyy/MM/dd'))
    			.replaceAll('@TIPO', it=='SEMANA'? 'S.periodicidad=\'SEMANAL\'' : 'S.periodicidad<>\'QUINCENAL\'')
    			.replaceAll('@PERIODO',it+'L')
    								
    		//println query
    			Sql sql=new Sql(dataSource)
    			sql.eachRow(query){ row->
    				
    				def empleado=Empleado.findById(row.id)
    				if(empleado){
    					//println 'SDI para: '+empleado
    					def found=CalculoSdi.findByEmpleadoAndEjercicioAndBimestreAndTipo(empleado,ejercicio,bimestre,'CALCULO_SDI')
    					if(!found){
    						found=new CalculoSdi(
    							empleado:empleado,
    							ejercicio:ejercicio,
    							bimestre:bimestre,
    							tipo:'CALCULO_SDI',
    							fechaIni:inicio,
    							fechaFin:fin
    							
    							).save flush:true
    					}
    					
    						found.sdiAnterior=empleado.salario.salarioDiarioIntegrado
    						found.sdb=empleado.salario.salarioDiario
    						found.years=( (fin-empleado.alta)/365)
    						found.dias=fin-empleado.alta+1
    						found.vacDias=row.VAC_DIAS
    						found.vacPrima=row.VAC_PRIMA
    						found.agndoDias=row.AGNDO_DIAS
    						found.factor=row.FACTOR
    						found.sdiF=found.sdb*found.factor
    						found.diasLabBim=row.DIAS_LAB_BIM
    						
    						
    						
    						
    						
    						found.compensacion=0.0
    						found.incentivo=0.0
    						found.bonoPorDesemp=0.0
    						found.hrsExtrasDobles=0.0
    						found.hrsExtrasTriples=0.0
    						found.comisiones=0.0
    						found.primaDom=0.0
    						found.vacacionesP=0.0
    						actualizarVariables(found)
    						registrarTiempoExtraDoble(found)
    						found.with{
    							variable=compensacion+incentivo+bonoPorDesemp+hrsExtrasDobles+hrsExtrasTriples+comisiones+primaDom+vacacionesP
    						}
    						
    						
    						
    						found.varDia=found.variable/found.diasLabBim
    						
    						def sdiNvo=found.sdiF+found.varDia
    						found.sdiCalc=sdiNvo
    						
    						if(found.sdb==0.0){
    							sdiNvo=found.varDia*found.factor
    						}
    						
    						def topoSalarial=25*zona.salario
    						found.topeSmg=topoSalarial
    						
    						if(sdiNvo>topoSalarial)
    							found.sdiNvo=topoSalarial
    						else{
    							found.sdiNvo=sdiNvo
    						}
    						
    						found.smg=zona.salario
    						if(found.sdiAnterior==found.sdiNvo){
    							found.sdiInf=0.0
    						}else{
    							found.sdiInf=found.sdiNvo
    						}
    						if(empleado.alta>inicio){
    							found.diasBim=fin-empleado.alta
    						}else{
    							found.diasBim=fin-inicio
    						}
    						found.incapacidades=row.INCAPACIDADES
    						found.faltas=row.FALTAS
    						
    				
    					
    				}
    				
    			}
    	}
    	
    	return rows

    }


    private actualizarVariables(CalculoSdi sdi){
    	
    	def partidas=NominaPorEmpleadoDet
    	.findAll("from NominaPorEmpleadoDet d where d.parent.empleado=? and d.concepto.id in(19,22,24,41,42,44) and d.parent.nomina.ejercicio=? and d.parent.nomina.calendarioDet.bimestre=?"
    			 ,[sdi.empleado,sdi.ejercicio,sdi.bimestre])
    
    	sdi.compensacion=0.0
    	sdi.incentivo=0.0
    	sdi.bonoPorDesemp=0.0
    	
    	sdi.comisiones=0.0
    	sdi.primaDom=0.0
    	sdi.vacacionesP=0.0
    
    	partidas.each{it->
    		switch(it.concepto.id){
    			case 19:
    				sdi.compensacion+=it.importeGravado+it.importeExcento
    				break
    			case 22:
    				sdi.incentivo+=it.importeGravado+it.importeExcento
    				break
    			case 24:
    				sdi.bonoPorDesemp+=it.importeGravado+it.importeExcento
    				break
    			case 41:
    				sdi.comisiones+=it.importeGravado+it.importeExcento
    				break
    			case 42:
    				sdi.primaDom+=it.importeGravado+it.importeExcento
    				break
    			case 44:
    				sdi.vacacionesP+=it.importeGravado+it.importeExcento
    				break
    		}
    	}
    	
    }
    
    private registrarTiempoExtraDoble(CalculoSdi sdi){
    	def partidas=TiempoExtraDet
    	.executeQuery("from TiempoExtraDet d where d.tiempoExtra.empleado=? and d.tiempoExtra.asistencia.calendarioDet.bimestre=? and d.tiempoExtra.asistencia.calendarioDet.calendario.ejercicio=?"
    				 ,[sdi.empleado,sdi.bimestre,sdi.ejercicio])
    	
    	def triples=partidas.sum 0.0 ,{
    		it.tiempoExtraImss.integraTriples
    	}
    	def dobles=partidas.sum 0.0,{
    		if(it.tiempoExtraImss){
    		  it.tiempoExtraImss.integra
    		}
    	} 
    	sdi.hrsExtrasDobles=dobles
    	sdi.hrsExtrasTriples=triples
    
    }
}
