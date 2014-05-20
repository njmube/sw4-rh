

<table class="table table-striped table-bordered table-condensed">
	<thead>
		<tr>
			<g:sortableColumn property="id" title="Folio"/>
			<g:sortableColumn property="empleado.apellidoPaterno" title="Empleado"/>
			<th>Tipo</th>
			<th>Fecha</th>
			<th>Referencia (IMMSS)</th>
			<th>Comentario</th>
		</tr>
	</thead>
	<tbody>
		<g:each in="${incapacidadesList}" var="row">
			<tr>
				<td>
					<g:link action="edit" id="${row.id}">
						<g:formatNumber number="${row.id}" format="####"/>
					</g:link>
				</td>
				<td>
					<g:link action="edit" id="${row.id}">
						<g:fieldValue bean="${row}" field="empleado"/>
					</g:link>
				</td>
				<td><g:fieldValue bean="${row}" field="tipo"/> </td>
				<td><g:formatDate date="${row.dateCreated}" format="dd/MM/yyyy"/></td>
				<td><g:fieldValue bean="${row}" field="referenciaImms"/> </td>
				<td><g:fieldValue bean="${row}" field="comentario"/> </td>
			</tr>
		</g:each>
	</tbody>
</table>
<div class="pagination">
	<g:paginate total="${incapacidadTotalCount ?: 0}" />
</div>
