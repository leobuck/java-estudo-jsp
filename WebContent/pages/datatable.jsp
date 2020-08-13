<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DataTable JQuery</title>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css" />
</head>
<body>
	
	<table id="minhaTabela" class="display" style="width:100%">
        <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Senha</th>
            </tr>
        </thead>
	</table>
	
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript">
	
		$(document).ready(function() {
		    $('#minhaTabela').DataTable({
		        "processing": true,
		        "serverSide": true,
		        "ajax": "carregarDadosDataTable"
		    });
		});
		
	</script>
</body>
</html>