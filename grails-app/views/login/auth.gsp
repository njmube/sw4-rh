<%@page expressionCodec="none" %>
<html>
<head>
	
	<title>SW4 - RH Acceso</title>
</head>

<body>



<div class="container">
	<div class="row">
		<div class="col-md-12 jumbotron">
			<h1>Luxor RH
				<p>Sistema integral de recursos humanos</p>
			</h1>
		</div>
	</div> <!-- end .row 1 -->
	
	<g:if test="${flash.message}">
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-danger">
					${flash.message}
				</div>
			</div>
		</div> <!-- end .row 2 -->
	</g:if>
	
	<div class="row">
		<div class="col-md-6 col-sm-offset-3">
			<div class="row">
				<g:render template="loginForm"/>
			</div>
			
			
		</div>
	</div>
%{-- 
	<div class="row">
		<div class="col-md-6 col-centered">
			<g:render template="loginForm"/>
		</div>
	</div> --}%

</div>

	%{-- <div class='inner'>
		<div class='fheader'><g:message code="springSecurity.login.header"/></div>

		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>

		<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
			<p>
				<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
				<input type='text' class='text_' name='j_username' id='username'/>
			</p>

			<p>
				<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
				<input type='password' class='text_' name='j_password' id='password'/>
			</p>

			<p id="remember_me_holder">
				<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
				<label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
			</p>

			<p>
				<input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
			</p>
		</form>
	</div> --}%

<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
