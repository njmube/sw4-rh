<%@ page import="com.luxsoft.sw4.rh.Nomina" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="layout" content="dashboard_1"/>
	<title>Nomina</title>
</head>
<body>
	<content tag="header">
		<g:link action="index" id="${nominaInstance.id}">
			<h4>Nómina: ${nominaInstance.folio} ${nominaInstance.periodicidad} </h4>
		</g:link>
	</content>
	<content tag="buttonBar">
		<div class="button-panel">
			<div class="btn-group">
			<g:link action="index" class="btn btn-default">
				<span class="glyphicon glyphicon-repeat"></span> Refrescar
			</g:link>
			<g:link action="agregar" class="btn btn-primary" id="${nominaInstance.id}">
				<span class="glyphicon glyphicon-floppy-saved"></span> Alta
			</g:link>
			<g:link action="create" class="btn btn-default">
				<span class="glyphicon glyphicon-search"></span> Buscar
			</g:link>
			<g:link action="create" class="btn btn-default">
				<span class="glyphicon glyphicon-filter"></span> Filtrar
			</g:link>
			</div>
		</div>
		
	</content>
	<content tag="grid">
		<g:render template="nominaDetGridPanel"/>
	</content>

	

</body>
</html>
