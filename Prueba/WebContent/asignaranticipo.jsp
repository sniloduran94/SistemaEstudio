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
<title>Genesis Estudio</title>

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
		
		//Obtención de la reserva a modificar
		Reserva res = (Reserva)(request.getAttribute("reserva"));
		
		//Obtención de clientes para combobox para el formulario de reservas
		ArrayList<Cliente> clients = (ArrayList<Cliente>)(request.getAttribute("clientes"));
		// Obtención de campañas para combobox para el formulario de reservas
		ArrayList<Campania> campa = (ArrayList<Campania>)(request.getAttribute("campanias"));
		// Obtención de fechas de reservas para el formulario de reservas
		ArrayList<String> fechas =  (ArrayList<String>)(request.getAttribute("reservas"));
		if(sesion == null || usuario == null || clients==null || campa==null || res==null){  %>
<!-- Mensaje de bienvenida -->
<div class="alert alert-danger alert-dismissable">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<strong>¡Debes iniciar sesión para ver esta página!</strong>
</div>
<%  }else{
				sesion.setAttribute("usuario", usuario);
    %>

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
				 <li ><a href="ServletLogin?opcion=IndexTrabajadorAdmin">Menú </a></li>
				 <li ><a href="ServletLogin?opcion=CerrarSesion">Cerrar Sesión</a></li>
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
	<h2 class="text-center wowload fadeInUp">Asignar anticipo</h2>
	<form class="form-horizontal wowload fadeInUp"
		action="ServletReserva?opcion=AsignarAnticipo" method="post">
		<fieldset>
			<%	
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String FechaAntigua = ""; 
				if(res.getFecha() != null){
					FechaAntigua = sdf.format(res.getFecha());
				}
			%>
			<input type="hidden" value="<%=res.getId_Reserva()%>"
				name="16_Id_Reserva"> <input type="hidden"
				value="<%=res.getCantidad_Reagendamiento()%>"
				name="16_Cantidad_Reagendamiento">
				<input type="hidden" value="<%=FechaAntigua%>"
				id="FechaAntigua">
				
			<!-- Form Name -->

			<!-- Text input-->
			<%	
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm"); 
				String fecha = "";
				String hora = "";
				if(res.getFecha() == null){
					fecha = "Pendiente";
					hora = "Pendiente";
				}else{
					fecha = sdf.format(res.getFecha());
					hora = sdf2.format(res.getFecha());
				}
			%>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="Fechadereserva">Fecha de la Reserva</label>
				<div class="col-md-4">					
					<input readonly class="form-control" type="text" placeholder="<%=fecha%>" autocomplete="off" name="16_Fecha" required />
					<br>
					<p id="demodanger" class="alert alert-danger" style="display:none;" role="alert"></p>
					<p id="demosuccess" class="alert alert-success" style="display:none;" role="alert"></p>
				</div>
			</div>	

			<div class="form-group">	
				<label class="col-md-4 control-label" for="Horadereserva">Hora</label>
				<div class="col-md-4">
					<input readonly class="form-control" type="text" placeholder="<%=hora%>" autocomplete="off" name="16_Hora"   onClick="HorasDisponibles()" required />
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label" for="15_Rut">R.U.T.<br>
				<FONT SIZE=2>(Usuario)</font></label>
				<div class="col-md-4">
					<select readonly name="15_Id_Cliente" id="15_Id_Cliente"
						class="form-control" required>
						<%
				Iterator<Cliente> iter2 = clients.iterator();
				if(!iter2.hasNext()){  %>
						<option value="null">No existen clientes</option>
						<%			}
				while(iter2.hasNext())
				{
					Cliente f = (Cliente)iter2.next(); 
					if(res.getId_Cliente()!=f.getId_Cliente()){
					%>
						<% }else{ %>
						<option value="<%=f.getId_Cliente()%>" selected><%=f.getRut()%>
							-
							<%=f.getNombre()%></option>
						<% 				}
				} %>
					</select>
				</div>

			</div>

			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="17_Id_Campania">Campaña</label>
				<div class="col-md-4">

					<select name="17_Id_Campania" readonly id="17_Id_Campania"
						class="form-control" required>
						<%
				Iterator<Campania> it = campa.iterator();
				while(it.hasNext())
				{
					Campania f = (Campania)it.next();
					if(res.getId_Campania()!=f.getId_Campania()){
					%>
						<% 			}else{	%>
						<option value="<%=f.getId_Campania()%>" selected><%=f.getNombre()%></option>
						<%			}			
				} 	%>
					</select>


				</div>
			</div>

			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="16_Cantidad_Personas">Cantidad
					de personas que asisten a la sesión</label>
				<div class="col-md-4">
					<input id="16_Cantidad_Personas" name="16_Cantidad_Personas"
						type="number" readonly placeholder="1" class="form-control input-md"
						value=<%=res.getCantidad_Personas() %> onClick="CobroReagenda()">
				</div>
			</div>
			
			<!-- CheckBox input-->
			<div class="form-group">
			  <label class="col-md-4 control-label" for="16_Validado">¿Validado?</label>  
			  <div class="col-md-4">
			  <% if(res.isValidado()) {	%>
			  		<input id="Validado" name="16_Validado" type="checkbox"  disabled readonly checked> Sí/No
			  <% }else{					%>
			  		<input id="Validado" name="16_Validado"  type="checkbox" disabled readonly> Sí/No
			  <% } 						%>
			  </div>
			</div>
						
			<hr>		
						
			<% 
			   int MontoAnticipo = 0;
			   if(res.getMonto_Pago_Adelantado()>=0){
					MontoAnticipo = res.getMonto_Pago_Adelantado();
			   }
			
			 %>			
						
			<!-- Text input-->
			<div class="panel panel-success">
			<div class="panel-heading text-center"><i
			class="fa fa-money fa-2x"></i> &nbsp;<strong><font size=5>Información del anticipo</font></strong></div>
 			<div class="panel-body">
 			
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Fecha_Anticipo">Fecha del anticipo</label>
					<div class="col-md-4">
						<input id="datetimepicker21" name="16_Fecha_Anticipo"
							 autocomplete="off" type="text" class="form-control input-md" value="<%=(((res.getFecha_Anticipo())==null)||((res.getFecha_Anticipo().equals("null"))))?"":res.getFecha_Anticipo()%>">	
						</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Fecha_Anticipo">Hora del anticipo</label>
					<div class="col-md-4">
						<input id="datetimepicker11" name="16_Hora_Anticipo"
							type="text" class="form-control input-md" value="<%=(((res.getHora_Anticipo()) == null)||((res.getHora_Anticipo().equals("null"))))?"":res.getHora_Anticipo()%>">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Nombre_Anticipo">Nombre</label>
					<div class="col-md-4">
						<% if(res.getNombre_Anticipo() == null){ %>
							<input id="16_Nombre_Anticipo" name="16_Nombre_Anticipo"
								type="text" class="form-control input-md" value="">
						<% } else{ %>
							<input id="16_Nombre_Anticipo" name="16_Nombre_Anticipo"
									type="text" class="form-control input-md" value="<%=res.getNombre_Anticipo()%>">
						<% } %>
					</div>
				</div>
				
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Tipo_Anticipo">Tipo de anticipo</label>
					<div class="col-md-4">
					<select name="16_Tipo_Anticipo" id="16_Tipo_Anticipo"
						class="form-control" required>
						<% if(res.getTipo_Anticipo()!=null && res.getTipo_Anticipo().equals("Transferencia")){ %>
								<option selected value="Transferencia" selected>Transferencia</option>
								<option value="Deposito" >Depósito</option>
								<option value="TDebito" >Tarjeta de débito</option>
								<option value="TCredito" >Tarjeta de crédito</option>
								<option value="Cheque" >Cheque</option>
								<option value="Efectivo" >Efectivo</option>
						<% }
						   if(res.getTipo_Anticipo()!=null && res.getTipo_Anticipo().equals("Deposito")){ %>
								<option value="Transferencia" >Transferencia</option>
								<option selected value="Deposito" >Depósito</option>
								<option value="TDebito" >Tarjeta de débito</option>
								<option value="TCredito" >Tarjeta de crédito</option>
								<option value="Cheque" >Cheque</option>
								<option value="Efectivo" >Efectivo</option>
						<% }
						   if(res.getTipo_Anticipo()!=null && res.getTipo_Anticipo().equals("TDebito")){ %>
								<option value="Transferencia" >Transferencia</option>
								<option value="Deposito" >Depósito</option>
								<option selected value="TDebito" >Tarjeta de débito</option>
								<option value="TCredito" >Tarjeta de crédito</option>
								<option value="Cheque" >Cheque</option>
								<option value="Efectivo" >Efectivo</option>
						<% 	}
							if(res.getTipo_Anticipo()!=null && res.getTipo_Anticipo().equals("TCredito")){ %>
								<option value="Transferencia" >Transferencia</option>
								<option value="Deposito" >Depósito</option>
								<option value="TDebito" >Tarjeta de débito</option>
								<option selected value="TCredito" >Tarjeta de crédito</option>
								<option value="Cheque" >Cheque</option>
								<option value="Efectivo" >Efectivo</option>
						<% 	}
							if(res.getTipo_Anticipo()!=null && res.getTipo_Anticipo().equals("Cheque")){ %>
								<option value="Transferencia" >Transferencia</option>
								<option value="Deposito" >Depósito</option>
								<option value="TDebito" >Tarjeta de débito</option>
								<option value="TCredito" >Tarjeta de crédito</option>
								<option selected value="Cheque" >Cheque</option>
								<option value="Efectivo" >Efectivo</option>
						<% 	}
							if(res.getTipo_Anticipo()!=null && res.getTipo_Anticipo().equals("Efectivo")){ %>
								<option value="Transferencia" >Transferencia</option>
								<option value="Deposito" >Depósito</option>
								<option value="TDebito" >Tarjeta de débito</option>
								<option value="TCredito" >Tarjeta de crédito</option>
								<option value="Cheque" >Cheque</option>
								<option selected value="Efectivo" >Efectivo</option>
						<% 	}
							if(res.getTipo_Anticipo()==null || res.getTipo_Anticipo().equals("")){ %>
								<option value="Transferencia" >Transferencia</option>
								<option value="Deposito" >Depósito</option>
								<option value="TDebito" >Tarjeta de débito</option>
								<option value="TCredito" >Tarjeta de crédito</option>
								<option value="Cheque" >Cheque</option>
								<option value="Efectivo" >Efectivo</option>
						<% } %>
					</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Monto_Pago_Adelantado">Monto de Pago de Anticipo $</label>
					<div class="col-md-4">
						<input id="16_Monto_Pago_Adelantado" name="16_Monto_Pago_Adelantado"
							type="number" placeholder="1" class="form-control input-md"
							value=<%=MontoAnticipo%>>
					</div>
				</div>
							
			</div>
			</div>
			
			<div class="form-group" style="display:none;">
				<input id="Cobro" name="Cobro" value="0">
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="espaciado"></label>
				<div class="col-md-4">
					<button id="adelanto" name="boton" class="btn btn-success btn-block">Asignar</button>
				</div>
			</div>
			
		</fieldset>
	</form>
</div>
<!--Contact Ends-->
<%	}%>

<!-- Footer Starts -->
<div class="footer text-center spacer">
	<p class="wowload flipInX">Sistema Estudio. Advancing Group Ltda.</a></p>
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

<!-- wow script -->
<script src="assets/wow/wow.min.js"></script>


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
	$('#datetimepicker11').datetimepicker({
		datepicker:false,
		format:'H:i',
		allowTimes:['00:00','01:00','02:00','03:00','04:00','05:00','06:00','07:00','08:00','9:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00']
	});
	$('#datetimepicker2').datetimepicker({
		yearOffset:0,
		lang:'es',
		timepicker:false,
		format:'Y/m/d',
		formatDate:'Y/m/d',
		dayOfWeekStart : 1,
		disabledWeekDays: [0],
		minDate:'-1970/01/01', // yesterday is minimum date
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
	$('#datetimepicker3').datetimepicker({
		inline:true
	});
	$('#datetimepicker4').datetimepicker();
	$('#open').click(function(){
		$('#datetimepicker4').datetimepicker('show');
	});
	$('#close').click(function(){
		$('#datetimepicker4').datetimepicker('hide');
	});
	$('#reset').click(function(){
		$('#datetimepicker4').datetimepicker('reset');
	});
	$('#datetimepicker5').datetimepicker({
		datepicker:false,
		allowTimes:['12:00','13:00','15:00','17:00','17:05','17:20','19:00','20:00'],
		step:5
	});
	$('#datetimepicker6').datetimepicker();
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
<script type="text/javascript">
	document.addEventListener("mousewheel", function(event){
    if(document.activeElement.type === "number"){
        document.activeElement.blur();
    }
});
</script>

<script type="text/javascript">
function redirect(URL){
	window.location= '/GenesisEstudio/'+ URL;
}	
</script>

</body>
</html>