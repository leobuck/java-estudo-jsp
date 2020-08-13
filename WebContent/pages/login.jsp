<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Login</h1>
	
	<form action="autenticacaoServlet" method="post">
		<input type="hidden" id="url" name="url" value="<%= request.getParameter("url") %>" />
	
		<label>Login</label>
		<input type="text" id="login" name="login" />
		
		<br><br>
		
		<label>Senha</label>
		<input type="password" id="senha" name="senha" />
		
		<br><br>
		
		<input type="submit" value="Acessar">
	</form>
</body>
</html>