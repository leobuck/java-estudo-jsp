<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload</title>
<style>
	table, th, td {
		border: 1px solid black;
		border-collapse: collapse;
	}
</style>
</head>
<body>
	<h1>Upload</h1>
	
	<form enctype="application/x-www-form-urlencoded">
		<input type="file" id="file" name="file" onchange="uploadFile();" />
	</form>
	
	<img alt="Imagem" src="" id="target" width="200px" height="200px">
	
	<br><br>
	
	<a href="fileUpload?acao=carregar">Carregar arquivos</a>
	
	<br><br>
	
	<table style="width:100%">
		<thead>
			<tr>
				<th>Arquivo</th>
				<th>Ação</th>
			</tr>
		</thead>
		<c:forEach items="${arquivos}" var="arq">
			<tbody>
				<tr>
					<td>${arq.id}</td>
					<td><a href="fileUpload?acao=download&id=${arq.id}">Download</a></td>
				</tr>
			</tbody>
		</c:forEach>
		
	</table>
	
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script type="text/javascript">
	
		function uploadFile() {
			var target = document.querySelector('img');
			var file = document.querySelector('input[type=file]').files[0];
			
			var reader = new FileReader();
			
			reader.onloadend = function() {
				target.src = reader.result;
				
				$.ajax({
					method: 'POST',
					url: 'fileUpload',
					contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
					data: {
						fileUpload: reader.result
					}
				}).done(function(response) {
					alert('Sucesso: ' + response);
				}).fail(function (xhr, status, errorThrown) {
					alert('Erro: ' + xhr.responseText);
				});
			}
			
			if (file) {
				reader.readAsDataURL(file);
			} else {
				target.src = '';
			}
		}
		
	</script>
</body>
</html>