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
		
		String FechaAntiguaGlobal = "''"; //Para uso de Javascript
		
		//Obtención de la reserva a modificar
		Reserva res = (Reserva)(request.getAttribute("reserva"));
		
		//Obtención de clientes para combobox para el formulario de reservas
		ArrayList<Cliente> clients = (ArrayList<Cliente>)(request.getAttribute("clientes"));
		// Obtención de campañas para combobox para el formulario de reservas
		ArrayList<Campania> campa = (ArrayList<Campania>)(request.getAttribute("campanias"));
		
		ArrayList<Vendedor> vendedores = (ArrayList<Vendedor>)(request.getAttribute("vendedores"));
		
		// Obtención de campaña para combobox para el formulario de reservas
		ArrayList<Campania> campatodas = (ArrayList<Campania>)(request.getAttribute("campaniastodas"));
		
		ArrayList<ArrayList<String>> canalesventasarray = (ArrayList<ArrayList<String>>)(request.getAttribute("canalesventasarray"));
		// Obtención de fechas de reservas para el formulario de reservas
		ArrayList<String> fechas =  (ArrayList<String>)(request.getAttribute("reservas"));
		// Obtención de tipos de sesiones para combobox para el formulario
		ArrayList<Tipo_Sesion> tipossesiones = (ArrayList<Tipo_Sesion>)(request.getAttribute("tipossesiones"));
		//Obtención de metodos de pago para combobox para el formulario de reservas
		ArrayList<Metodo_Pago> metodosdepago = (ArrayList<Metodo_Pago>)(request.getAttribute("metodo_pago"));
		
		if(res.getFecha()==null){
			FechaAntiguaGlobal = "'nulo'";			
		}
		
		System.out.println(FechaAntiguaGlobal);
		
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
	<h2 class="text-center wowload fadeInUp">Modificar reserva</h2>
	<form class="form-horizontal wowload fadeInUp"
		action="ServletReserva?opcion=ModificarReserva" method="post">
		<fieldset>
			<%	
				String FechaAntigua = "";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
				if(res.getFecha()!=null){
					FechaAntigua = sdf.format(res.getFecha());
					FechaAntiguaGlobal = "'"+FechaAntigua+"'";
				}else{
					FechaAntigua = "'nulo'";
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
				if(res.getFecha()!=null){
					fecha = sdf.format(res.getFecha());
					hora = sdf2.format(res.getFecha());
				}
			%>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="Fechadereserva">Fecha de la Reserva</label>
				<div class="col-md-4">
					<input class="form-control" type="text" value="<%=fecha%>" autocomplete="off" name="16_Fecha" id="datetimepicker2"  onChange="HorasDisponibles()" required />
					<br>
					<p id="demodanger" class="alert alert-danger" style="display:none;" role="alert"></p>
					<p id="demosuccess" class="alert alert-success" style="display:none;" role="alert"></p>
				</div>
				<div class="col-md-2" id="CampoPendiente">
					<input id="Pendiente" name="Pendiente" type="checkbox" onClick="ActualizarPendiente()">
					<strong>¿Pendiente?</strong>
				</div>
			</div>	

			<div class="form-group">	
				<label class="col-md-4 control-label" for="Horadereserva">Hora</label>
				<div class="col-md-4">
					<input class="form-control" type="text" value="<%=hora%>" autocomplete="off" name="16_Hora" id="datetimepicker1"  onClick="HorasDisponibles()" required />
				</div>
				<div class="col-md-3">
				<a onClick="VerReagendamiento()">Modificar veces<br>Reagendamiento</a>
				</div>
			</div>
			
			<div class="form-group" id="VecesReagendamiento" style="display:none">
				  <label class="col-md-4 control-label" for="">Veces reagendamiento</label>
				  <div class="col-md-4">
				  	<input class="form-control" type="number" value="<%=res.getCantidad_Reagendamiento()%>" name="16_Cantidad_Reagendamiento_Modificada" maxlength="20" id="16_Cantidad_Reagendamiento_Modificada" placeholder="" />
				  </div>
			</div>
			
			<input id="16_Pre_Reserva_Previo" value="<%=res.isPreReserva()?"checked":""%>" name="16_Pre_Reserva_Previo" type="hidden">
					
			<div class="form-group">
				<label class="col-md-4 control-label" for="16_Validado">¿Pre Reserva?</label>
				<div class="col-md-4" id="CampoPreReserva" >
					<input id="16_Pre_Reserva"   value="1" <%=res.isPreReserva()?"checked":""%> name="16_Pre_Reserva" type="radio" onChange="ActualizarPreReserva();"> Sí<br>
					<input id="NO16_Pre_Reserva" value="0" <%=res.isPreReserva()?"":"checked"%> name="16_Pre_Reserva" type="radio" onChange="ActualizarPreReserva();"> No
				</div>
			</div>
			
			<div class="form-group" id="MetodoDePago" style="visibility:none">
				<label class="col-md-4 control-label" for="37_Id_Metodo_Pago">Cuenta de depósito</label>
				<div class="col-md-4">
					<select name="37_Id_Metodo_Pago" id="37_Id_Metodo_Pago"
						class="form-control" required>
						<%
						Iterator<Metodo_Pago> iter6 = metodosdepago.iterator();
						if(!iter6.hasNext()){  %>
							<option value="null">No existen cuentas de depósito asociadas</option>
						<%}
						while(iter6.hasNext())
						{
							Metodo_Pago mp = (Metodo_Pago)iter6.next();
							if(res.getId_Metodo_Pago()==mp.getId_Metodo_Pago()){ %>
								<option selected value="<%=mp.getId_Metodo_Pago()%>"><%=mp.getNombre() %></option>
						<%	}else{ %>
								<option value="<%=mp.getId_Metodo_Pago()%>"><%=mp.getNombre() %></option>
						
					  <%} 
					  }%>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				  <label class="col-md-4 control-label" for="34_Id_Tipo_Sesion">Tipo de sesión</label>
				  <div class="col-md-4">
					<select class="form-control"  name="34_Id_Tipo_Sesion" id="34_Id_Tipo_Sesion" required>	
						<%
								Iterator<Tipo_Sesion> it2 = tipossesiones.iterator();
								while(it2.hasNext())
								{
									Tipo_Sesion ts = (Tipo_Sesion)it2.next();
									if(ts.getId_Tipo_Sesion()!=res.getId_Tipo_Sesion()){
						%>
						<option value="<%=ts.getId_Tipo_Sesion()%>"><%=ts.getTipo_Sesion()%></option>
						<% 			}else{	%>
						<option value="<%=ts.getId_Tipo_Sesion()%>" selected><%=ts.getTipo_Sesion()%></option>
						<%			}			
								} 	%>
					</select>
				  </div>
				</div>

			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="17_Id_Campania">Campaña</label>
				<div class="col-md-4">

					<select name="17_Id_Campania" id="17_Id_Campania"
						class="form-control" onChange="Requiere_Cupon()" required>
						<option value="1"></option>
						<%
				boolean seteoCampania = false;
				if(!campatodas.isEmpty()){
					seteoCampania = true;
					%>
						<option selected value="<%=campatodas.get(0).getId_Campania()%>"><%=campatodas.get(0).getNombre()%></option>
					<%
				}					
				Iterator<Campania> it = campa.iterator();
				while(it.hasNext())
				{
					Campania f = (Campania)it.next();
					if(res.getId_Campania()!=f.getId_Campania()){
					%>
						<option value="<%=f.getId_Campania()%>"><%=f.getNombre()%></option>
						<% 			}else if(seteoCampania){	%>
						<option value="<%=f.getId_Campania()%>" selected><%=f.getNombre()%></option>
						<%			}			
				} 	%>
					</select>


				</div>
			</div>

			<!-- Multiple Checkboxes -->
			<div class="form-group" style="display: block;">
				<label class="col-md-4 control-label" for="groupon">Código
					de cupón</label>
				<div class="col-md-4">
					<% if(res.getCodigo().equals("null")){ %>
					<textarea class="form-control" id="16_Codigo" name="16_Codigo">Código</textarea>
					<% }else{ %>
					<textarea class="form-control" id="16_Codigo" name="16_Codigo"><%=res.getCodigo()%></textarea>
					<% }%>
				</div>
			</div>
			
			<!-- CheckBox input-->
			<div class="form-group">
			  <label class="col-md-4 control-label" for="16_Validado">¿Validado?</label>  
			  <div class="col-md-4" id="CampoValidado" style="visibility:hidden;">
			  <% if(res.isValidado()) {	%>
			  		<input id="Validado" name="16_Validado" type="checkbox" checked> Sí/No
			  <% }else{					%>
			  		<input id="Validado" name="16_Validado"  type="checkbox"> Sí/No
			  <% } 						%>
			  </div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="16_Cantidad_Personas">Cantidad
					de personas que asisten a la sesión</label>
				<div class="col-md-4">
					<input id="16_Cantidad_Personas" name="16_Cantidad_Personas" min="0"
						type="number" placeholder="1" class="form-control input-md"
						value=<%=res.getCantidad_Personas() %> onClick="CobroReagenda()">
				</div>
			</div>
			
			<div class="form-group" style="display:none;">
				<input id="Cobro" name="Cobro" value="0">
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="15_Rut">Mail y nombre<br>
				<FONT SIZE=2>(Usuario)</font></label>
				<div class="col-md-4">
					<select name="15_Id_Cliente" id="15_Id_Cliente"
						class="form-control" required>
						<option value="1"></option>
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
								<option value="<%=f.getId_Cliente()%>"><%=f.getMail() %> -
								<%=f.getNombre()%> <%=f.getApellido_Pat() %></option>
								<% }else{ %>
								<option value="<%=f.getId_Cliente()%>" selected><%=f.getMail() %> -
								<%=f.getNombre()%> <%=f.getApellido_Pat() %></option>
								<% 				}
						} %>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				  <label class="col-md-4 control-label" for="16_Ubicacion">Vendedor</label>
				  <div class="col-md-4">
					<select class="form-control"  name="16_Ubicacion" id="16_Ubicacion" required>
					
						<%
						Iterator<Vendedor> iter3 = vendedores.iterator();
						if(!iter3.hasNext()){  %>
								<option value="null">No existen vendedores</option>
								<%			}
						while(iter3.hasNext())
						{
							Vendedor vend = (Vendedor)iter3.next(); 
							if(vend.getId_Vendedor()!=res.getVendedor()){
							%>
								<option value="<%=vend.getId_Vendedor()%>"><%=vend.getVendedor()%></option>
								<% }else{ %>
								<option value="<%=vend.getId_Vendedor()%>" selected><%=vend.getVendedor()%></option>
								<% 	}
						} %>
					</select>
				 </div>
			</div>
			
			<div class="form-group">
			  <label class="col-md-4 control-label" for="16_Observacion">Descripción</label>
			  <div class="col-md-8">
			  	<textarea name="16_Observacion" id="16_Observacion"><%=(res.getObservacion()!=null)?res.getObservacion():"Observacion"%></textarea>
			  </div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="espaciado"></label>
				<div class="col-md-4">
					<button id="adelanto" name="boton" onClick="CobroReagenda(); return Validar_Cupon();" class="btn btn-success">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Continuar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>

		</fieldset>
	</form>
</div>
<!--Contact Ends-->

<script type="text/javascript">
	function CobroReagenda(){
		if(document.getElementById("VecesReagendamiento").style.display=='none'){
			var FechaAntigua = document.getElementById("FechaAntigua").value;
			
			var FechaNueva = document.getElementById("datetimepicker2").value;
					
			if(countdays(FechaAntigua,FechaNueva)>=-2){
				
				var r = confirm('\'OK\', para generar cobro por reagendamiento. \'Cancelar\', para no cobrar');
				if(r== true){
					document.getElementById("Cobro").value = 1;
				}else{
					document.getElementById("Cobro").value = 0;
				}
			}
		}else{
			document.getElementById("Cobro").value = -1;
		}
	}
	
	function parseDate(str) {
	    var d = str.split('/')
	    return new Date( parseInt(d[0]),parseInt(d[1]) - 1,parseInt(d[2]));
	}
	
	function countdays(day1, day2){

		var oneday = 24*60*60*1000;
		
		var start_date = parseDate(day1);
		var end_date = parseDate(day2);
		
		
		var days = Math.floor((start_date.getTime() - end_date.getTime())/(oneday));
	
		return days;
	}
</script>
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

<script type="text/javascript">
	function Requiere_Cupon(){
		var ArregloDeCanalesDeVentas = <%=canalesventasarray%>;
		var id = document.getElementById("17_Id_Campania").value;
				
		var indexarray
		for(var i=0; i<ArregloDeCanalesDeVentas.length ; i++){
			if(ArregloDeCanalesDeVentas[i][0] == id){
				indexarray = i;
			}
		}
		
		if(ArregloDeCanalesDeVentas[indexarray][1]==0){
			document.getElementById("16_Codigo").required = false;
			document.getElementById("16_Codigo").value = "";
			document.getElementById("16_Codigo").style.visibility = 'hidden';
			document.getElementById("CampoValidado").style.visibility = 'hidden';
			document.getElementById("16_Validado").checked = false;
		}else{
			document.getElementById("16_Codigo").style.visibility = 'visible';
			document.getElementById("CampoValidado").style.visibility = 'visible';
			document.getElementById("16_Validado").checked = false;
		}
	}
	window.onLoad = Requiere_Cupon();
</script>

<script type="text/javascript">
	function Validar_Cupon(){
		
		var ArregloDeCanalesDeVentas = <%=canalesventasarray%>;
		var id = document.getElementById("17_Id_Campania").value;
				
		var indexarray
		for(var i=0; i<ArregloDeCanalesDeVentas.length ; i++){
			if(ArregloDeCanalesDeVentas[i][0] == id){
				indexarray = i;
			}
		}
		
		if(ArregloDeCanalesDeVentas[indexarray][1]!=0){
			if(document.getElementById("16_Codigo").value == ""){
				alert("Debe ingresar un código de cupón");
				document.getElementById("16_Codigo").value = "";
				return false;
			}
		}
		return true;
	}
</script>

<script>
  $(function() {
    $( "#dialog" ).dialog();
  });
  </script>



<script type="text/javascript">
 function HorasDisponibles(){
 	var dia = $('#datetimepicker2').val();
 	
 	//En caso de una fecha inválida o no seleccionada
 	if(dia==''){
 		document.getElementById('demosuccess').style.display = 'none';
 		document.getElementById("demodanger").innerHTML = "Seleccione una fecha (válida)";
 		document.getElementById('demodanger').style.display = 'block';
 		$('#datetimepicker1').datetimepicker('hide');
 	}else{
 	//en caso de tener una fecha, buscar horas disponibles
 		var array = <%=fechas%>;
 		var index = -1;
 		for(var i=0; i<array.length; i++){
 			var diaenelarray = String(array[i][0]);
 			var dia = String(dia);
 			if(diaenelarray==dia){
 			
 				var HorasOcupadas = [];
 				var HorasDisponibles = [];
 				var horas = String(array[i][1]); 
 				
 				while(horas.length>0){			
 					var primeracoma = horas.indexOf(',');
 			 		if(primeracoma == -1){
 			 		
 			 			if(horas.length>0){
 			 				hora = horas+":00";
 			 				HorasOcupadas.push(hora);
 			 			}
 			 			break;
 			 		}
 			 		
 			 		var hora = horas.substring(0,primeracoma);
 			 		hora = hora+":00";
 			 		HorasOcupadas.push(hora);
 			 		
 			 		horas = horas.substring(primeracoma+1);
 			 	}
 			 				 	
 			 	if(HorasOcupadas.indexOf("9:00")==-1){
 			 		HorasDisponibles.push('9:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("10:00")==-1){
 			 		HorasDisponibles.push('10:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("11:00")==-1){
 			 		HorasDisponibles.push('11:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("12:00")==-1){
 			 		HorasDisponibles.push('12:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("13:00")==-1){
 			 		HorasDisponibles.push('13:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("14:00")==-1){
 			 		HorasDisponibles.push('14:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("15:00")==-1){
 			 		HorasDisponibles.push('15:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("16:00")==-1){
 			 		HorasDisponibles.push('16:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("17:00")==-1){
 			 		HorasDisponibles.push('17:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("18:00")==-1){
 			 		HorasDisponibles.push('18:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("19:00")==-1){
 			 		HorasDisponibles.push('19:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("20:00")==-1){
 			 		HorasDisponibles.push('20:00');
 			 	}
 			 	if(HorasOcupadas.indexOf("21:00")==-1){
 			 		HorasDisponibles.push('21:00');
 			 	}
 			 	
 			 	document.getElementById('demodanger').style.display = 'none';
 			 	document.getElementById("demosuccess").innerHTML = "Horas disponibles para el dia <strong>"+dia+"</strong> :<br><strong> "+HorasDisponibles.length +" de 13</strong>";
 				document.getElementById('demosuccess').style.display = 'block';
 			 	
 			 	
 			 	$('#datetimepicker1').datetimepicker({
					datepicker:false,
					format:'H:i',
					allowTimes: HorasDisponibles
				});
				return;
 			}
 		}
 		document.getElementById('demodanger').style.display = 'none';
 		document.getElementById("demosuccess").innerHTML = "Horas disponibles para el dia <strong>"+dia+"</strong> :<br><strong> 13 de 13</strong>";
 		document.getElementById('demosuccess').style.display = 'block';	
 	}
 }
</script> 

<!-- jquery -->
<!--<script src="assets/jquery.js"></script>-->

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
//disabledWeekDays: [0],
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
	//disabledWeekDays: [0],
	minDate:'-1970/01/01', // yesterday is minimum date
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
	function VerReagendamiento(){
		if(document.getElementById("VecesReagendamiento").style.display=='none'){
			document.getElementById('VecesReagendamiento').style.display = 'block';
		}else{
	 		document.getElementById('VecesReagendamiento').style.display = 'none';
		}
	}
	window.onload = ActualizarPreReserva();
</script>


<script type="text/javascript">
	function ActualizarPreReserva(){
		if(document.getElementById("16_Pre_Reserva").checked){
			document.getElementById('MetodoDePago').style.display = 'block';
		}else{
	 		document.getElementById('MetodoDePago').style.display = 'none';
		}
	}
	window.onload = ActualizarPreReserva();
</script>

<script type="text/javascript">
	function ActualizarPendiente(){
		if(document.getElementById("Pendiente").checked){
			document.getElementById('demosuccess').style.display = 'none';
	 		document.getElementById('demodanger').style.display = 'none';
	 		$('#datetimepicker2').datetimepicker('hide');
	 		$('#datetimepicker1').datetimepicker('hide');
	 		document.getElementById("datetimepicker1").required = false;
	 		document.getElementById("datetimepicker2").required = false;
	 		document.getElementById("datetimepicker1").disabled = true;
	 		document.getElementById("datetimepicker2").disabled = true;
	 		document.getElementById("datetimepicker1").value = "";
	 		document.getElementById("datetimepicker2").value = "";
	 		var r = confirm('Usted ha dejado pendiente esta reserva, \n\n ¿Confirmas cobro por modificación de reserva?');
			if(r== true){
				document.getElementById("Cobro").value = 1;
			}else{
				document.getElementById("Cobro").value = 0;
			}
		}else{
	 		$('#datetimepicker2').datetimepicker('show');
	 		document.getElementById("datetimepicker1").required = true;
	 		document.getElementById("datetimepicker2").required = true;
	 		document.getElementById("datetimepicker1").disabled = false;
	 		document.getElementById("datetimepicker2").disabled = false;
	 		document.getElementById("datetimepicker1").value = "";
	 		document.getElementById("datetimepicker2").value = "";
	 		document.getElementById("Cobro").value = 0;
		}
	}
</script>

<script type="text/javascript">
	var Pendiente = <%=FechaAntiguaGlobal%>
	if(Pendiente == 'nulo'){
		document.getElementById('demosuccess').style.display = 'none';
		document.getElementById('demodanger').style.display = 'none';
		$('#datetimepicker2').datetimepicker('hide');
		$('#datetimepicker1').datetimepicker('hide');
		document.getElementById("datetimepicker1").required = false;
		document.getElementById("datetimepicker2").required = false;
		document.getElementById("datetimepicker1").disabled = true;
		document.getElementById("datetimepicker2").disabled = true;
		document.getElementById("datetimepicker1").value = "";
		document.getElementById("datetimepicker2").value = "";
		document.getElementById("Pendiente").checked = true;		
	}
</script>
<script type="text/javascript">
	document.addEventListener("mousewheel", function(event){
    if(document.activeElement.type === "number"){
        document.activeElement.blur();
    }
});
</script>

<script src="assets/PropiedadEstudio.js" type="text/javascript"></script>
</body>
</html>