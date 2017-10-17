<%@page import="java.util.Iterator"%>
<%@page import="modelo.*"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8' />
<!--  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">-->
<title></title>

<link href='fullcalendar-2.6.0/fullcalendar.css' rel='stylesheet' />
<link href='fullcalendar-2.6.0/fullcalendar.print.css' rel='stylesheet' media='print' />

<!-- bootstrap -->
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />


<style>
	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
		color: green ;
	}

	#calendar {
		max-width: 900px;
		margin: 0 auto;
	}

</style>
</head>
<body>
	<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario =  (Trabajador)sesion.getAttribute("usuario");
		
		// Obtención de fechas de reservas para el formulario de reservas
		ArrayList<ArrayList<String>> fechas =  (ArrayList<ArrayList<String>>)(request.getAttribute("reservas"));
		System.out.println("Cantidad: "+fechas.size());
				
		if(sesion == null || usuario == null || fechas==null){  %>
	        	<!-- Mensaje de bienvenida -->
				<div class="alert alert-danger alert-dismissable">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>¡Debes iniciar sesión para ver esta página!</strong>
				</div>
    <%  }else{
				sesion.setAttribute("usuario", usuario);
    %>
    <div style="text-align:center;">
		   <button style="text-align:center;"  class="btn btn-success btn-lg" onclick="javascript:history.back()">
		             Volver al menu
			</button>
			<button style="text-align:center;"  class="btn btn-success btn-lg" onclick="javascript:location.reload();">
		             Actualizar
			</button>
	</div>
	
    <br>
	<div id='calendar' ></div>
	
	<% } %>
	
	<!-- Footer Starts -->
	<div class="footer text-center spacer">
	Copyright 2017 Sistema Estudio. All rights reserved.
	</div>
	<!-- # Footer Ends -->
	<a href="#home" class="gototop "><i class="fa fa-angle-up  fa-3x"></i></a>
	
	<script src='fullcalendar-2.6.0/lib/moment.min.js'></script>
	<script src='fullcalendar-2.6.0/lib/jquery.min.js'></script>
	<script src='fullcalendar-2.6.0/fullcalendar.min.js'></script>
	<script src='fullcalendar-2.6.0/lang/es.js'></script>
	<script>
		var array = <%=fechas%>;
		var HorasOcupadas = [];
		for(var i=0; i<array.length; i++){
			if(array[i][3].toString()=='0'){
				HorasOcupadas.push(
				{
					title: (array[i][0]).toString(),
					start: (array[i][1]).toString(),
					end: (array[i][2]).toString(),
					color: '#257e4a'
				});
			}else{
				HorasOcupadas.push(
				{
					title: (array[i][0]).toString(),
					start: (array[i][1]).toString(),
					end: (array[i][2]).toString(),
					color: '#b3b300'
				});
			}
		}
		
		$(document).ready(function() {
			$('#calendar').fullCalendar({
				header: {
					left:  'prev,next today',
					center:'title',
					right: 'month,agendaWeek,agendaDay'
				},
				displayEventTime : false,		
				selectable: true,
				selectHelper: true,
				minTime: '09:00:00',
				maxTime: '22:00:00',
				editable: false,
				eventLimit: false, // allow "more" link when too many events
				events:  HorasOcupadas,
				allDayHtml:"Todo<br/>el dia",
				dayNamesShort:["dom","lun","mar","mie","jue","vie","sab"],
				buttonText:{month:"Mes",week:"Semana",day:"Dia",list:"Agenda"},
				monthNames:["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"],
				titleRangeSeparator:" \u2014 " 
			});
		});
	</script>
	
	
<!-- boostrap -->
<script src="assets/bootstrap/js/bootstrap.js" type="text/javascript" ></script>


<script src="assets/PropiedadEstudio.js" type="text/javascript"></script>
</body>
</html>
