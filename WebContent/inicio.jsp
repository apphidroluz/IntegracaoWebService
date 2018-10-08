<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<!doctype html>

<html>



<head>

<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>

<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="entity.*, persistence.*, config.*"%>


<link rel="stylesheet" href="css/botoes.css">
<link rel="stylesheet" href="css/bootstrap.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<script src="js/bootstrap.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id"
	content="814156775949-ltn7105ab0u3fhapiusagvku8rg02i9h.apps.googleusercontent.com">

<link rel="shortcut icon" href="LogoOri.ico">
<title>Bem Vindo ao Portal Hidroluz Medição</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>


	<div class="modal-dialog" style="margin-top: 10px;">

		<div class="modal-content">

			<div class="modal-header">

				<div align="center">

					<img src="img/logo-ntransp3.png" class="img-logo">

				</div>

				<h3 class="modal-title text-center" id="myModalLabel">Acesso ao
					Painel de Importação</h3>

			</div>



			<div class="modal-body">

				<form method="post" action="Controle?cmd=logar">

					<c:if test="${msg != null}">

						<div class="alert alert-danger alert-dismissable">

							<a class="close" data-dismiss="alert" aria-label="close">×</a> <strong>${msg}</strong>

						</div>

					</c:if>

					<c:if test="${msg_cad01 != null}">

						<div class="alert alert-success alert-dismissable">

							<a class="close" data-dismiss="alert" aria-label="close">×</a> <strong>${msg_cad01}</strong>


						</div>

					</c:if>

					<c:if test="${msg_cad02 != null}">

						<div class="alert alert-success alert-dismissable">

							<a class="close" data-dismiss="alert" aria-label="close">×</a> <strong>${msg_cad02}</strong>

							<a href="Controle?cmd=ReenviarEmail"
								style="text-align: left; margin-left: 10px;" title="Reenviar">Reenviar</a>

						</div>

					</c:if>



					<div class="form-group">

						<div class="input-group">

							<input type="text" class="form-control" id="Login"
								placeholder="Email" name="login"> <label for="Login"
								class="input-group-addon glyphicon glyphicon-user"></label>

						</div>

					</div>


					<div class="form-group">

						<div class="input-group">

							<input type="password" class="form-control" id="Password"
								placeholder="Senha" name="senha"> <label for="Password"
								class="input-group-addon glyphicon glyphicon-lock"></label>

						</div>

					</div>



					<div style="margin-bottom: 20px;">

						<div class="row">

							<div class="col-md-6"></div>

							<div class="col-md-6"></div>

						</div>

					</div>

					<div class="modal-footer">

						<input value="Acessar" type="submit"
							class="form-control btn btn-danger" style="color: white">



					</div>

					<div class="modal-footer" style="margin-top: -20px;"></div>




				</form>




			</div>
		</div>
	</div>
</body>
</html>