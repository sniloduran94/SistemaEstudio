<%@page import="java.util.Iterator"%>
<%@page import="modelo.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="us">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title></title>

<link href="assets/jquery-ui.css" rel="stylesheet">

<style>
body {
	font: 62.5% "Trebuchet MS", sans-serif;
	margin: 50px;
}

.demoHeaders {
	margin-top: 2em;
}

#dialog-link {
	padding: .4em 1em .4em 20px;
	text-decoration: none;
	position: relative;
}

#dialog-link span.ui-icon {
	margin: 0 5px 0 0;
	position: absolute;
	left: .2em;
	top: 50%;
	margin-top: -8px;
}

#icons {
	margin: 0;
	padding: 0;
}

#icons li {
	margin: 2px;
	position: relative;
	padding: 4px 0;
	cursor: pointer;
	float: left;
	list-style: none;
}

#icons span.ui-icon {
	float: left;
	margin: 0 4px;
}

.fakewindowcontain .ui-widget-overlay {
	position: absolute;
}

select {
	width: 200px;
}
</style>

<style type="text/css">
.custom-date-style {
	background-color: red !important;
}

.input {
	
}

.input-wide {
	width: 500px;
}
</style>

<!-- Google fonts -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,700'
	rel='stylesheet' type='text/css'>

<!-- font awesome -->
<!--<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">-->

<!-- Custom Fonts -->
<link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet"
	type="text/css">

<!-- bootstrap -->
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />

<!-- animate.css -->
<link rel="stylesheet" href="assets/animate/animate.css" />
<link rel="stylesheet" href="assets/animate/set.css" />

<!-- gallery -->
<link rel="stylesheet" href="assets/gallery/blueimp-gallery.min.css">

<!-- favicon -->
<link rel="shortcut icon" href="images/favicon.png" type="image/x-icon">
<link rel="icon" href="images/favicon.png" type="image/x-icon">


<link rel="stylesheet" href="assets/style.css">

<!-- Calendario -->
<link rel="stylesheet" type="text/css"
	href="assets/bootstrap/css/jquery.datetimepicker.css" />

<!-- Fin Calendario -->

</head>

<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario =  (Trabajador)sesion.getAttribute("usuario");
			
		ArrayList<String> ev = (ArrayList<String>)(request.getAttribute("evento"));
			
		if(sesion == null || usuario == null || ev==null){  %>
			<!-- Mensaje de bienvenida -->
			<div class="alert alert-danger alert-dismissable">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<strong>¡Debes iniciar sesión para ver esta página!</strong>
			</div>
	<%  }else{
				sesion.setAttribute("usuario", usuario);
    %>

<script type="text/javascript">var CampPrecios = []; </script>

<div class="topbar animated fadeInLeftBig"></div>

<!-- Header Starts -->
<div class="navbar-wrapper">
	<div class="container">
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation"
			id="top-nav">
			<div class="container">
				<div class="navbar-header">
					<!-- Logo Starts -->
					<a class="navbar-brand" href="#home"><img
						src="images/LogoLetras2.png" alt="logo"></a>
					<!-- #Logo Ends -->


					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>

				</div>


				<!-- Nav Starts -->
				<div class="navbar-collapse  collapse">
					<ul class="nav navbar-nav navbar-right scroll">
              	 <% if(usuario.getEsAdmin()==1){ %>
              	 	<li class="active" onClick="redirect('ServletLogin?opcion=IndexTrabajadorAdmin')"><a href="">Menú</a></li>
              	 <% }else{ %>
              	 	<li class="active" onClick="redirect('ServletLogin?opcion=IndexTrabajadorFotografo')"><a href="">Menú</a></li>
              	 <% } %>
				 <li onClick="redirect('ServletLogin?opcion=CerrarSesion')"><a href="">Cerrar Sesión</a></li>
              </ul>
				</div>
				<!-- #Nav Ends -->

			</div>
		</div>

	</div>
</div>
<!-- #Header Starts -->

<br>
<br>

<!--FORMULARIO-->
<br>
<br>
<div id="home" class="container spacer about">
	<h2 class="text-center">Imprimir ticket - Sesión</h2>
	
	
	<!-- Información de la reserva -->
		<div class="col-md-5 ">
	<div class="panel panel-info">
			<div class="panel-heading text-center "><i
			class="fa fa-calendar fa-1x"></i> &nbsp;<strong><font size=3>Información de la reserva</font></strong></div>
 			<div class="panel-body">
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Número Boleta</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(1)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Fecha</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(2).substring(0,10)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Fotógrafo</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(3)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Vendedor</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(4)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Nombre Cliente</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(5) + " "+ ev.get(6)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Canal de venta</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(7)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Tipo de sesión</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(8)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos CD</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(9)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 10x15</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(10)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 15x21</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(11)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 20x30</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(12)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 30x40</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(13)%></label>
				</div><br>
					<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Precio por persona adicional</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(26)%></label>
				</div><br>
					<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Precio por reagendamiento</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(36)%></label>
				</div><br>
			
			
		</div>
	</div>
		<div class="panel panel-info ">
			<div class="panel-heading text-center "><i
			class="fa fa-camera fa-1x"></i> &nbsp;<strong><font size=3>Pagos</font></strong></div>
		<div class="panel-body">
					<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Precio de la sesión</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(14)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Monto de pago adelantado</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(15).equals("-1")?"N/A":ev.get(15)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Valor por cobrar</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(16)%></label>
				</div><br>				
		</div>
	</div>
	</div>
	
	<div class="col-md-1">
		
	</div>
	
	<div class="col-md-6">
		<div class="panel panel-info">
			<div class="panel-heading text-center "><i
			class="fa fa-calendar fa-1x"></i> &nbsp;<strong><font size=3>Adicionales</font></strong></div>
 			<div class="panel-body">
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Descuento</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=(ev.get(20)==null)?"":ev.get(20)%></label>
				</div><br>				
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Nombre campaña agendada</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=(ev.get(32)==null)?"":ev.get(32)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Nombre campaña convertida</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=(ev.get(33)==null || ev.get(33).equals(ev.get(32)))?"N/A":ev.get(33)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 10x15 adicionales</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(21)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 15x21 adicionales</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(22)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 20x30 adicionales</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(23)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 30x40 adicionales</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(24)%></label>
				</div><br>
					<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Monto por Extras</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=(ev.get(18).equals("-1"))?"N/A":ev.get(18)%></label>
				</div><br>
					<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Persona(s) adicional(es)</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(25)%></label>
				</div><br>
					<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Recargo por reagendar</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(29)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Forma de pago</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(30)%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Valor a pagar</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=ev.get(31)%></label>
				</div><br>
		</div>
	</div>
	<form action="ServletEvento?opcion=ImprimirEvento" method="post">
		<input type="hidden" value="<%=ev.get(0)%>" name="39_Id_Evento">
			<div class="btn-group-lg">
				<% if(ev.get(35)!=null&&ev.get(35).equals("2")){ %>
					 <button type="submit" class="btn btn-block btn-success" disabled name="ImprimirEvento" value="Imprimir" onclick="return confirm('¿Imprimir este evento?')">
	              	 <i class="fa fa-print fa-1x"></i> Imprimir
              	 <% }else{ %>
              	 	<button type="submit" class="btn btn-block btn-success" name="ImprimirEvento" value="Imprimir" onclick="return confirm('¿Imprimir este evento?')">
	              	 <i class="fa fa-print fa-1x"></i> Imprimir
              	 <% } %>
            	 </button>
            </div>
    <form>	
	</div>
		
	
</div>
<hr>
<!--Contact Ends-->

<%	} 
		%>

<!-- Footer Starts -->
<div class="footer text-center spacer">
	<p>Sistema Estudio. Advancing Group Ltda.</p>
 <br><br>
©Copyright 2017. Todos los derechos reservados.<br><br>
</div>
<!-- # Footer Ends -->
<a href="#home" class="gototop "><i class="fa fa-angle-up  fa-3x"></i></a>

<!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
<div id="blueimp-gallery"
	class="blueimp-gallery blueimp-gallery-controls">
	<!-- The container for the modal slides -->
	<div class="slides"></div>
	<!-- Controls for the borderless lightbox -->
	<h3 class="title">Title</h3>
	<a class="prev">‹</a> <a class="next">›</a> <a class="close">×</a>
	<!-- The modal dialog, which will be used to wrap the lightbox content -->
</div>

<script src="assets/jquery.js"></script>

<script src="assets/jquery-ui.js"></script>
<script>
  $(function() {
    $( "#dialog" ).dialog();
  });
</script>

<!-- boostrap -->
<script src="assets/bootstrap/js/bootstrap.js" type="text/javascript"></script>

<!-- jquery mobile -->
<script src="assets/mobile/touchSwipe.min.js"></script>
<script src="assets/respond/respond.js"></script>

<!-- gallery -->
<script src="assets/gallery/jquery.blueimp-gallery.min.js"></script>

<!-- custom script -->
<script src="assets/script.js"></script>

<script src="assets/bootstrap/js/jquery.js"></script>
<script src="build/jquery.datetimepicker.full.js"></script>

<script src="assets/lang-all.js"></script>

<script type="text/javascript">
	function DesactivarActivar(){
		if(document.getElementById("35_Asistio").checked == false){
			document.getElementById("35_Valor_Por_Cobrar").readOnly = true;
			document.getElementById("35_CD").readOnly = true;
			document.getElementById("35_Extras").readOnly = true;
			document.getElementById("35_Persona_Adicional").readOnly = true;
			document.getElementById("35_Recargo_Por_Reagendar").readOnly = true;
			document.getElementById("35_Fotografo").readOnly = true;
			document.getElementById("35_Cant_10x15").readOnly = true;
			document.getElementById("35_Cant_15x21").readOnly = true;
			document.getElementById("35_Cant_20x30").readOnly = true;
			document.getElementById("35_Cant_30x40").readOnly = true;
			document.getElementById("35_Numero_Ticket").readOnly = true;
			document.getElementById("35_Fotos_Seleccionadas").disabled = true;
		}else{
			document.getElementById("35_Numero_Ticket").readOnly = false;
			document.getElementById("35_Valor_Por_Cobrar").readOnly = false;
			document.getElementById("35_CD").readOnly = false;
			document.getElementById("35_Extras").readOnly = false;
			document.getElementById("35_Persona_Adicional").readOnly = false;
			document.getElementById("35_Recargo_Por_Reagendar").readOnly = false;
			document.getElementById("35_Fotografo").readOnly = false;
			document.getElementById("35_Fotos_Seleccionadas").disabled = false;
			document.getElementById("datetimepicker21").readOnly = false;
			document.getElementById("35_Cant_10x15").readOnly = false;
			document.getElementById("35_Cant_15x21").readOnly = false;
			document.getElementById("35_Cant_20x30").readOnly = false;
			document.getElementById("35_Cant_30x40").readOnly = false;
		}
	}
		function CambioListaParaEntregar(){
			if(document.getElementById("35_Lista_Para_Entregar").checked == false){
				document.getElementById("35_Entregadas").disabled = true;
				document.getElementById("35_Entregadas").readOnly = true;
			}else{
				document.getElementById("35_Entregadas").disabled = false;
				document.getElementById("35_Entregadas").readOnly = false;
			}
		}
		function CambioEntregadas(){
			if(document.getElementById("35_Entregadas").checked == false){
				document.getElementById("datetimepicker22").readOnly = true;
				document.getElementById("35_Nombre_Retira").readOnly = true;
			}else{
				document.getElementById("datetimepicker22").readOnly = false;
				document.getElementById("35_Nombre_Retira").readOnly = false;
			}
		}
</script>

<script>
	window.onerror = function(errorMsg) {
		$('#console').html($('#console').html()+'<br>'+errorMsg)
	};
	
	$.datetimepicker.setLocale('es');
	
	$('#datetimepicker_format').datetimepicker({value:'2015/04/15 05:03', format: $("#datetimepicker_format_value").val()});
	$("#datetimepicker_format_change").on("click", function(e){
		$("#datetimepicker_format").data('xdsoft_datetimepicker').setOptions({format: $("#datetimepicker_format_value").val()});
	});
	$("#datetimepicker_format_locale").on("change", function(e){
		$.datetimepicker.setLocale($(e.currentTarget).val());
	});
	
	$('#datetimepicker').datetimepicker({
	dayOfWeekStart : 1,
	lang:'es',
	formatDate:'Y.m.d',
	startDate:	'2016/01/01',
	disabledWeekDays: [0],
	dayOfWeekShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"],
	timepicker: false,
	});
	
	$('#datetimepicker').datetimepicker({value:'',step:60});
	
	$('.some_class').datetimepicker();
	
	$('#default_datetimepicker').datetimepicker({
		formatTime:'H:i',
		formatDate:'d.m.Y',
		//defaultDate:'8.12.1986', // it's my birthday
		defaultDate:'+03.01.1970', // it's my birthday
		defaultTime:'10:00',
		timepickerScrollbar:false
	});
	
	$('#datetimepicker10').datetimepicker({
		step:5,
		inline:true
	});
	$('#datetimepicker_mask').datetimepicker({
		mask:'9999/19/39 29:59'
	});
	
	$('#datetimepicker1').datetimepicker({
		datepicker:false,
		format:'H:i',
		allowTimes:['9:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00']
	});
	$('#datetimepicker2').datetimepicker({
		yearOffset:0,
		lang:'es',
		timepicker:false,
		format:'Y/m/d',
		formatDate:'Y/m/d',
		dayOfWeekStart : 1,
		disabledWeekDays: [0],
		dayOfWeekShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"],
		timepicker: false,
		scrollMonth: false,
		scrollTime: false
	});
	$('#datetimepicker21').datetimepicker({
		yearOffset:0,
		lang:'es',
		timepicker:false,
		format:'Y/m/d',
		formatDate:'Y/m/d',
		dayOfWeekStart : 1,
		disabledWeekDays: [0],
		dayOfWeekShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"],
		timepicker: false,
		scrollMonth: false,
		scrollTime: false
	});
	$('#datetimepicker22').datetimepicker({
		yearOffset:0,
		lang:'es',
		timepicker:false,
		format:'Y/m/d',
		formatDate:'Y/m/d',
		dayOfWeekStart : 1,
		disabledWeekDays: [0],
		dayOfWeekShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"],
		timepicker: false,
		scrollMonth: false,
		scrollTime: false
	});
	$('#datetimepicker23').datetimepicker({
		yearOffset:0,
		lang:'es',
		timepicker:false,
		format:'Y/m/d',
		formatDate:'Y/m/d',
		dayOfWeekStart : 1,
		disabledWeekDays: [0],
		dayOfWeekShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"],
		timepicker: false,
		scrollMonth: false,
		scrollTime: false
	});
	$('#destroy').click(function(){
		if( $('#datetimepicker6').data('xdsoft_datetimepicker') ){
			$('#datetimepicker6').datetimepicker('destroy');
			this.value = 'create';
		}else{
			$('#datetimepicker6').datetimepicker();
			this.value = 'destroy';
		}
	});
	var logic = function( currentDateTime ){
		if (currentDateTime && currentDateTime.getDay() == 6){
			this.setOptions({
				minTime:'11:00'
			});
		}else
			this.setOptions({
				minTime:'8:00'
			});
	};
	$('#datetimepicker7').datetimepicker({
		onChangeDateTime:logic,
		onShow:logic
	});
	$('#datetimepicker8').datetimepicker({
		onGenerate:function( ct ){
			$(this).find('.xdsoft_date')
				.toggleClass('xdsoft_disabled');
		},
		minDate:'-1970/01/2',
		maxDate:'+1970/01/2',
		timepicker:false
	});
	$('#datetimepicker9').datetimepicker({
		onGenerate:function( ct ){
			$(this).find('.xdsoft_date.xdsoft_weekend')
				.addClass('xdsoft_disabled');
		},
		weekends:['01.01.2014','02.01.2014','03.01.2014','04.01.2014','05.01.2014','06.01.2014'],
		timepicker:false
	});
	var dateToDisable = new Date();
		dateToDisable.setDate(dateToDisable.getDate() + 2);
	$('#datetimepicker11').datetimepicker({
		beforeShowDay: function(date) {
			if (date.getMonth() == dateToDisable.getMonth() && date.getDate() == dateToDisable.getDate()) {
				return [false, ""]
			}
	
			return [true, ""];
		}
	});
	$('#datetimepicker12').datetimepicker({
		beforeShowDay: function(date) {
		if (date.getMonth() == dateToDisable.getMonth() && date.getDate() == dateToDisable.getDate()) {
			return [true, "custom-date-style"];
		}
			return [true, ""];
		}
	});
	$('#datetimepicker_dark').datetimepicker({theme:'dark'});
</script>


<script src="assets/PropiedadEstudio.js" type="text/javascript"></script>

</body>
</html>