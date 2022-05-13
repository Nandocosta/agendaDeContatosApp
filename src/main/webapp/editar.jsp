<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Agenda de contatos</title>
<link rel="icon" href="imagens/favicon.png">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<div class="centralizar">
		<h1>Editar contato</h1>
		<form name="frmContato" action="update">
			<table>
				<tr>
					<td><span>Id: </span><br><input type="text" name="idcon" id="caixa3" readonly value="<%out.print(request.getAttribute("idcon"));%>"></td>
				</tr>
				<tr>
					<td><span>Nome: </span><br><input type="text" name="nome" class="caixa1" value="<%out.print(request.getAttribute("nome"));%>"></td>
				</tr>
				<tr>
					<td><span>Fone: </span><br><input type="text" name="fone" class="caixa2" value="<%out.print(request.getAttribute("fone"));%>"></td>
				</tr>
				<tr>
					<td><span >Email: </span><br><input type="text" name="email" class="caixa1" value="<%out.print(request.getAttribute("email"));%>"></td>
				</tr>
			</table>
			<input type="button" value="Salvar" class="botao1" onclick="validarForm()">
		</form>
	</div>
	<script src="scripts/validador.js"></script>
</body>
</html>