
<table id="grid" class="table table-striped table-bordered table-condensed">
	<thead>
		<tr>
			<th>Nombre</th>
			<th>Ubicación</th>
			<th>Salario</th>
			<th>Vacaciones</th>
			<th>Comisiones</th>
			<th>Retardos</th>
			<th>Salario Neto</th>
			<th>Total</th>
			
		</tr>
	</thead>
	<tbody>
		<g:each 
			in="${ptuInstance?.partidas.sort({ a,b -> a.empleado.perfil.ubicacion.clave <=> b.empleado.perfil.ubicacion.clave?: a.empleado.apellidoPaterno<=>b.empleado.apellidoPaterno  }) }" var="row">
			<tr>
				<td>
					<g:link action="edit" id="${row.id}">
						${fieldValue(bean:row,field:"empleado")}
					</g:link>
				</td>
				<td>${fieldValue(bean:row,field:'empleado.perfil.ubicacion.clave')}</td>
				<td>${formatNumber(number:row.salario, type:'currency')}</td>
				<td>${formatNumber(number:row.vacaciones, type:'currency')}</td>
				<td>${formatNumber(number:row.comisiones, type:'currency')}</td>
				<td>${formatNumber(number:row.retardos, type:'currency')}</td>
				<td>${formatNumber(number:row.salarioNeto, type:'currency')}</td>
				<td>${formatNumber(number:row.total, type:'currency')}</td>
				
			</tr>
		</g:each>
	</tbody>
</table>
