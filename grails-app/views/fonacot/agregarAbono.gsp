<html>
<head>
	<meta charset="UTF-8">
	<meta name="layout" content="operacionesForm"/>
	<title>Abono a crédito FONACOT</title>
</head>
<body>

	<content tag="header">
		<h3>Abono a crédito FONACOT ${fonacotInstance.id}  ${fonacotInstance.empleado}</h3>
	</content>
	
	<content tag="operaciones">
		<ul class="nav nav-pills nav-stacked">
  			<li><g:link action="edit" id="${fonacotInstance.id}">
  					<span class="glyphicon glyphicon-arrow-left"></span> Regresar
  			    </g:link>
  			</li>
		</ul>
	</content>
	
	<content tag="formTitle">Registro de abono</content>
	
	<content tag="form">
		
		<g:hasErrors bean="${fonacotAbonoInstance}">
            <div class="alert alert-danger">
                <g:renderErrors bean="${fonacotAbonoInstance}" as="list" />
            </div>
        </g:hasErrors>
		
		<g:form action="salvarAbono"  class="form-horizontal" >
			<div class="modal-body">
			<g:hiddenField name="fonacot.id" value="${fonacotInstance.id}"/>
			<f:with bean="${fonacotAbonoInstance}">
				<f:field property="fecha" input-class="form-control"/>
				<f:field property="importe" input-class="form-control" input-type="text"/>
				<f:field property="comentario" input-class="form-control"/>
			</f:with>
			</div>
			<div class="modal-footer">
				<g:submitButton class="btn btn-primary" name="update" value="Salvar"/>
			</div>
		</g:form>
		
	</content>
	
</body>
</html>