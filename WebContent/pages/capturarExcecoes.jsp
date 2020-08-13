<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Capturar exceções com JQuery</title>
</head>
<body>
	<h3>Capturar exceções com JQuery</h3>
	<input type="text" placeholder="Informar valor" id="txtValor">
	<input type="button" value="Testar Exceção" onclick="testarExcecao()">
	
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script type="text/javascript">
		function testarExcecao() {
			var valorInformado = $('#txtValor').val();
			console.log(valorInformado);
			
			$.ajax({
				method: "POST",
				url: "capturarExcecao",
				data: { valorInformado: valorInformado }
			}).done(function(response) {
				alert("Sucesso: " + response);
			}).fail(function( jqXHR, textStatus, errorThrown) {
				alert("Erro: " + jqXHR.responseText);
			});
		}
	</script>
</body>
</html>