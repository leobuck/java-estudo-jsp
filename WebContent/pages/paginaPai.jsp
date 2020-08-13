<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Página Pai Load pgae JQuery</h1>
	
	<button onclick="carregar();">Carregar página</button>
	
	<div id="mostrarPaginaFilha"></div>
	
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script type="text/javascript">
	
		function carregar() {
			$('#mostrarPaginaFilha').load('paginaFilha.jsp');
		}
		
	</script>
</body>
</html>