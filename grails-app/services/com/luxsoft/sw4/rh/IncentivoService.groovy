package com.luxsoft.sw4.rh

import grails.transaction.Transactional
import grails.transaction.NotTransactional

@Transactional
class IncentivoService {

	//@NotTransactional
    def generarIncentivos(CalendarioDet ini,CalendarioDet fin) {

    	def tipo=ini.calendario.tipo=='SEMANA'?'SEMANAL':'QUINCENAL'
    	def empleados=Empleado.findAll(
			"from Empleado e where e.salario.periodicidad=? order by e.perfil.ubicacion.clave,e.apellidoPaterno asc",[tipo])
    	empleados.each{ empleado ->
    		if( (!empleado.baja) || (empleado.baja && empleado.baja.fecha>=ini.asistencia.fechaInicial) ){
    			
				Incentivo inc=Incentivo.find{tipo==tipo && calendarioIni==ini && calendarioFin==fin && empleado==empleado}
				if(!inc)
					inc=new Incentivo(
							tipo:tipo,
							calendarioIni:ini,
							calendarioFin:fin,
							empleado:empleado,
							ubicacion:empleado.perfil.ubicacion,
							
							ejercicio:ini.calendario.ejercicio
						)
					calcular(inc)
					if(tipo=='QUINCENAL')
						inc=aplicarBonoQuincenal(inc)
					inc.save failOnError:true


    			
    		}
    	}
    }

    

    def Incentivo aplicarQuncenal(Incentivo bono){
    	log.debug 'Calculando bono quincenal '+bono
    	def asistencia=Asistencia.findByCalendarioDet(bono.calendarioIno)
    	if(asistencia==null){
    		bono.comentario='No hay registros de asistencia'
    		return bono
    	}
    	//Aplicando reglas
    	bono.otorgado=true
    	bono.falstas=asistencia.faltas
    	bono.retardoMayor=asistencia.retardoMayor
    	bono.retardoMenor=asistencia.retardoMenor
    	bono.retardoComida=asistencia.retardoComida
    	bono.retardoTotal=asistencia
    }

}
