<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Progress Bar</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">  
<style type="text/css">
	#myProgress {
		width: 100%;
		background-color: #ddd;
	}
	
	#myBar {
		width: 1%;
		height: 30px;
		background-color: #4CAF50;
	}
	
	.ui-progressbar {
		position: relative;
	}
	
	.progress-label {
		position: relative;
		left: 50%;
		top: 4px;
		font-weight: bold;
		text-shadow: 1px 1px 0 #fff;
	}
</style>
</head>
<body>
	<h1>Exemplo com JavaScript</h1>
	
	<div id="myProgress">
		<div id="myBar"></div>
	</div>
	
	<br>
	
	<button onclick="exibirProgressBar();">Iniciar Progress Bar</button>
	
	<br>
	
	<h1>Exemplo com JQuery</h1>
	
	<div id="progressbar">
		<div class="progress-label">
			Carregando...
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script type="text/javascript">
		
		function exibirProgressBar() {
			var elem = document.getElementById('myBar');
			var width = 1;
			var id = setInterval(frame, 10);
			
			function frame() {
				if (width >= 100) {
					clearInterval(id);
				} else {
					width++;
					elem.style.width = width + "%";
				}
			}
		}
		
		$(function() {
			var progressBar = $('#progressbar'),
				progressLabel = $('.progress-label');
			
			progressBar.progressbar({
				value: false,
				change: function() {
					progressLabel.text(progressBar.progressbar('value') + "%");
				},
				complete: function() {
					progressLabel.text('Completo!');
				}
			});
			
			function progress() {
				var val = progressBar.progressbar('value') || 0;
				
				progressBar.progressbar('value', val + 2);
				
				if (val < 99) {
					setTimeout(progress, 80);
				}
			}
			
			setTimeout(progress, 2000);
		});
		
	</script>
</body>
</html>