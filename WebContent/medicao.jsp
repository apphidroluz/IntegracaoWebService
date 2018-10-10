<%@ page import="entity.*, persistence.*, java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!doctype html>
<html>

<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/stylesistema.css">
<link rel="stylesheet" href="jquery-filestyle.css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
<script src="https://code.highcharts.com/highcharts.src.js"></script>

<link rel="shortcut icon" href="LogoOri.ico">
<title>Portal Hidroluz Medição</title>
</head>

<body>
	<!-- BARRA DE NAVEGAÃ�Ã�O -->
	<nav class="navbar navbar-fixed-top navbar-custom" id="mainNav">
		<div class="container-fluid">
			<div class="navbar-header" style="margin-top: -20px" align="center">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#barra-collapse">
					<span class="sr-only">Barra Navegação</span> Menu&nbsp; <i class="fa fa-bars"></i>
				</button>
				<a href="#" class="navbar-brand" style="margin-top: 100xp">
					<img src="img/logo-ntransp.png" class="imagemnav"></a>
			</div>
		</div>
		<div class="col-md-12"
			style="margin-left: 250px; margin-top: -65px; width: 600px;">
			<div class="row">
				<div class="col-md-6" style="text-align: left;">
					<input type="hidden" value="${COD_CLI}" id="unidade"
						readonly="readonly"> <a id="unidade" value="${COD_CLI}"></a>
					<b>Condominio: </b>${CONDO}<br> 
					<b>Endereço: </b>${ENDE}<br>
				</div>
			</div>
		</div>
		<div style="float: right; color: #fff; margin-right: 60px; margin-top: -65px">
				<a type="submit" class="btn btn-danger" href="Controle?cmd=consulta_importacao&codigo=${COD_CLI}"
					style="font-size: 12px; text-decoration: none; color: primary; margin-top: 15px;">Consultar Importações</a> 
				<a type="submit" class="btn btn-info" href="Controle?cmd=sair"
					style="font-size: 12px; text-decoration: none; color: primary; margin-top: 15px;">Sair</a>
		</div>
	</nav>
	<!-- FECHA BARRA DE NAVEGAÇÃO -->

	<!-- Modal -->
	<div class="modal fade" id="modalAviso" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="panel panel-info">
				<div class="panel panel-heading">
					<div class="panel-title">Alguns Hidrometros não foram encontrados:</div>
				</div>
				<div class="panel-body">
					<table class="table table-bordered table-hover table-condensed" style="margin-top: -20px">
						<thead>
							<tr>
								<th style="text-align: center">Localização</th>
								<th style="text-align: center">Nº do Hidrometro</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${lista}" var="linha">
								<tr>
									<td>${linha.local}</td>
									<td>${linha.num_hidro}</td>
							</c:forEach>
						</tbody>
					</table>
					<div class="col-md-10"></div>
					<div class="col-md-2">
						<button type="button" class="btn btn-danger text-right btn-sm"
							data-dismiss="modal" style="margin-left: 20px; margin-top: -5px">Fechar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12 cliente">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="container" align="center">
					<div>
						<div class="col-md-12" align="right">
							<div class="col-md-4" align="left" >
								<a href="Controle?cmd=aviso" class="btn btn-success btnv"
								title="Clique para ver os avisos de leitura" style="font-size: 12px; margin-left: -60px">Alertas</a>
							</div>
							<div class="col-md-4" align="center" style="font-size: 15px; margin-top: 5px">
								<b> LEITURA DOS HIDRÔMETROS </b>
							</div>
							<div class="col-md-4" align="right">
								<a href="Controle?cmd=exportar" class="btn btn-success btnv"
								title="Clique para exportar as leituras" style="font-size: 12px; margin-right: -60px">Exportar</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${msg != null}">
				<div class="alert alert-success alert-dismissable">
					<a class="close" data-dismiss="alert" aria-label="close"
						style="text-align: center;">×</a> <strong>${msg}</strong>
				</div>
			</c:if>
			<div class="panel-body">
				<table class="cell-border compact table-striped table-hover" id="tabela2">
					<thead>
						<tr>
							<th>Data e Hora</th>
							<th>Localização</th>
							<th>Hidrometro</th>
							<th>Indice Atual (m³)</th>
							<th>Indice Anterior (m³)</th>
							<th>Consumo (m³)</th>
							<th>Há Vazamento</th>
							<th>Houve Vazamento</th>
							<th>Retorno D'água</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dados}" var="linha">
							<tr>
								<td>${linha.data}</td>
								<td>${linha.localizacao}</td>
								<td>${linha.numHidrometro}</td>
								<td>${linha.indice_atual}</td>
								<td>${linha.indice_antigo}</td>
								<td>${linha.consumo}</td>
								<c:if test="${linha.haVazamento == 0}">
									<td>Não</td>
								</c:if>
								<c:if test="${linha.haVazamento == 1}">
									<td>Sim</td>
								</c:if>
								<c:if test="${linha.houveVazamento == 0}">
									<td>Não</td>
								</c:if>
								<c:if test="${linha.houveVazamento == 1}">
									<td>Sim</td>
								</c:if>
								<c:if test="${linha.retornoAgua == 0}">
									<td>Não</td>
								</c:if>
								<c:if test="${linha.retornoAgua == 1}">
									<td>Sim</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript" charset="UTF-8">
	$(document).ready(function() {
		$('#tabela2').DataTable({
			"pagingType" : "simple_numbers",
			"language" : {
				"paginate" : {
					"next" : "Próxima",
					"previous" : "Anterior"
				},
				"lengthMenu" : "Mostrar _MENU_ registros por página",
				"zeroRecords" : "Nada encontrado... - Desculpe",
				"info" : "Mostrando página _PAGE_ de _PAGES_",
				"infoEmpty" : "Sem registros",
				"infoFiltered" : "(Filtrado de _MAX_ registros)"
			}
		});
	});
</script>

<script type="text/javascript">
	$(window).on('load', function() {
		$('#modalAviso').modal('show');
	});
</script>

</html>