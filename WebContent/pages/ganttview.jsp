<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Gantt View</title>
	<link rel="stylesheet" type="text/css" href="../css/jquery-ui-1.8.4.css" />
	<link rel="stylesheet" type="text/css" href="../css/reset.css" />
	<link rel="stylesheet" type="text/css" href="../css/jquery.ganttView.css" />
	<style type="text/css">
		body {
			font-family: tahoma, verdana, helvetica;
			font-size: 0.8em;
			padding: 10px;
		}
	</style>
</head>
<body>
	<h1>Gantt View</h1>
	
	<br>
	
	<div id="ganttChart"></div>
	<br/><br/>
	<div id="eventMessage"></div>
	
	<script type="text/javascript" src="../js/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="../js/date.js"></script>
	<script type="text/javascript" src="../js/jquery-ui-1.8.4.js"></script>
	<script type="text/javascript" src="../js/jquery.ganttView.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			/*var ganttData = [
				{
					id: 1, name: "Projeto Java Web", series: [
						{ name: "Planejado", start: new Date(2020,00,05), end: new Date(2020,00,20) },
						{ name: "Atual", start: new Date(2020,00,06), end: new Date(2020,00,17), color: "#f0f0f0" },
						{ name: "Projetado", start: new Date(2020,00,06), end: new Date(2020,00,25), color: "#e0e0e0" }
					]
				}
			];*/
			
			$.get("buscarDatasPlanejamento", function(response) {
				
				var ganttDataResposta = JSON.parse(response);
				
				var ganttData = "";
				ganttData += "[";
				
				$.each(ganttDataResposta, function(index, projeto) {
					
					ganttData += "{ \"id\": \"" + projeto.id + "\", \"name\": \"" + projeto.nome + "\", \"series\": [";
					
					$.each(projeto.series, function(idx, serie) {
						var cores = "#3366FF, #00CC00".split(',');
						
						var cor;
						if (idx === 0) {
							cor = "#CC33CC";
						} else {
							cor = Number.isInteger(idx / 2) ? cores[0] : cores[1];
						}
						
						var dataInicial = serie.dataInicial.split('-');
						var dataFinal = serie.dataFinal.split('-');
						
						ganttData += "{ \"name\": \"" + serie.nome + "\", \"start\": \"" + new Date(dataInicial[0], dataInicial[1], dataInicial[2]) + "\", \"end\": \"" + new Date(dataFinal[0], dataFinal[1], dataFinal[2]) + "\", \"color\": \"" + cor + "\", \"projeto\": \"" + serie.projetoId + "\", \"serie\": \"" + serie.id + "\" }";
						
						if (idx < projeto.series.length - 1) {
							ganttData += ",";
						}
						
					});
					
					ganttData += "]}";
					
					if (index < ganttDataResposta.length - 1) {
						ganttData += ",";
					}
				});
				
				ganttData += "]";
				
				ganttData = JSON.parse(ganttData);
				
				$("#ganttChart").ganttView({ 
					data: ganttData,
					slideWidth: 800,
					behavior: {
						onClick: function (data) { 
							var msg = "Evento de click: { inicio: " + data.start.toString("dd/MM/yyyy") + ", fim: " + data.end.toString("dd/MM/yyyy") + " }";
							$("#eventMessage").text(msg);
						},
						onResize: function (data) { 
							var msg = "Evento de redimensionar: { inicio: " + data.start.toString("dd/MM/yyyy") + ", fim: " + data.end.toString("dd/MM/yyyy") + " }";
							$("#eventMessage").text(msg);
							var start = data.start.toString("yyyy-mm-dd");
							var end = data.end.toString("yyyy-mm-dd");
							$.post('buscarDatasPlanejamento', { start: start, end: end, serie: data.serie, projeto: data.projeto}, function(data) {
								alert(data);
							});
						},
						onDrag: function (data) { 
							var msg = "Evento de arrastar e soltar: { inicio: " + data.start.toString("dd/MM/yyyy") + ", fim: " + data.end.toString("dd/MM/yyyy") + " }";
							$("#eventMessage").text(msg);
							var start = data.start.toString("yyyy-mm-dd");
							var end = data.end.toString("yyyy-mm-dd");
							$.post('buscarDatasPlanejamento', { start: start, end: end, serie: data.serie, projeto: data.projeto}, function(data) {
								alert(data);
							});
						}
					}
				});
			});
			
		});
	</script>
</body>
</html>