<%@page import="java.util.Iterator"%>
<%@page import="modelo.*"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="us">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Prueba (Estudio Fotográfico)</title>


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


<style>
/**
 * jQuery Editable Select
 * Indri Muska <indrimuska@gmail.com>
 *
 * Source on GitHub @ https://github.com/indrimuska/jquery-editable-select
 */

input.es-input { padding-right: 20px !important; background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAICAYAAADJEc7MAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAIGNIUk0AAG2YAABzjgAA4DIAAIM2AAB5CAAAxgwAADT6AAAgbL5TJ5gAAABGSURBVHjaYvz//z8DOYCJgUzA0tnZidPK8vJyRpw24pLEpwnuVHRFhDQxMDAwMPz//x+OOzo6/iPz8WFGuocqAAAA//8DAD/sORHYg7kaAAAAAElFTkSuQmCC) right center no-repeat; }
input.es-input.open {
	-webkit-border-bottom-left-radius: 0; -moz-border-radius-bottomleft: 0; border-bottom-left-radius: 0;
	-webkit-border-bottom-right-radius: 0; -moz-border-radius-bottomright: 0; border-bottom-right-radius: 0; }
.es-list { position: absolute; padding: 0; margin: 0; border: 1px solid #d1d1d1; display: none; z-index: 1000; background: #fff; max-height: 160px; overflow-y: auto;
	-moz-box-shadow: 0 2px 3px #ccc; -webkit-box-shadow: 0 2px 3px #ccc; box-shadow: 0 2px 3px #ccc; }
.es-list li { display: block; padding: 5px 10px; margin: 0; }
.es-list li.selected { background: #f3f3f3; }
.es-list li[disabled] { opacity: .5; }

</style>
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

</head>
<body>
<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario =  (Trabajador)sesion.getAttribute("usuario");
		
		//Obtención de clientes para combobox para el formulario de reservas
		ArrayList<Cliente> clients = (ArrayList<Cliente>)(request.getAttribute("clientes"));
		// Obtención de campañas para combobox para el formulario de reservas
		ArrayList<Campania> campa = (ArrayList<Campania>)(request.getAttribute("campanias"));
		
		ArrayList<Vendedor> vendedores = (ArrayList<Vendedor>)(request.getAttribute("vendedores"));
		
		ArrayList<ArrayList<String>> canalesventasarray = (ArrayList<ArrayList<String>>)(request.getAttribute("canalesventasarray"));
		// Obtención de fechas de reservas para el formulario de reservas
		ArrayList<String> fechas =  (ArrayList<String>)(request.getAttribute("reservas"));
		// Obtención de tipos de sesiones para combobox para el formulario
		ArrayList<Tipo_Sesion> tipossesiones = (ArrayList<Tipo_Sesion>)(request.getAttribute("tipossesiones"));
		//Obtención de metodos de pago para combobox para el formulario de reservas
		ArrayList<Metodo_Pago> metodosdepago = (ArrayList<Metodo_Pago>)(request.getAttribute("metodo_pago"));
		
		ArrayList<Comuna> comunas = (ArrayList<Comuna>)(request.getAttribute("comunas"));
		// Obtención de ciudades para combobox para el formulario de reservas
		ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)(request.getAttribute("ciudades"));
		// Obtención de ciudades para combobox para el formulario de reservas
		ArrayList<String> mails = (ArrayList<String>)(request.getAttribute("mails"));
		
		String cuponesdeshabilitados = (String)(request.getAttribute("cuponesdeshabilitados"));
		
		if(sesion == null || usuario == null || clients==null || campa==null || fechas==null){  %>
			<!-- Mensaje de bienvenida -->
			<div class="alert alert-danger alert-dismissable">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<strong>¡Debes iniciar sesión para ver esta página!</strong>
			</div>
	<%  }else{
				sesion.setAttribute("usuario", usuario);
    %>

<div class="topbar animated fadeInLeftBig"></div>
<div class="modal" id="nuevocliente"> <!-- Mision-->
	<div class="modal-dialog wowload flipInX">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<br>				
				<!-- Footer Starts -->
								<!-- # Footer Ends -->
				<h4 class="modal-title bounceInUp">Nuevo cliente</h4>
			</div>
			<div class="modal-body bounceInUp">
							
				<!-- Form Name -->
				
				<!-- Text input-->
				<div class="form-group">
				  <label class="col-md-4 control-label" for="15_Mail">Mail *</label>
				  <div class="col-md-8">
				  	<input class="form-control" onChange="MailRepetido()" type="text" value="" name="15_Mail" maxlength="40" id="15_Mail" placeholder="maildeejemplo@ejemplo.com" required/>
				  	<br>
				  	<p id="demodanger" class="alert alert-danger" style="display: none;" role="alert"></p>
				  </div>
				</div>
								
				<div class="form-group">
					<label class="col-md-4 control-label" for="rutdecliente">R.U.T.</label>
					<div class="col-md-8">
						<input class="form-control" type="number" onChange="prueba_rut()" name="15_Rut_Cliente" id="15_Rut_Cliente" placeholder="123456780"/>
					</div>
					<br><br>
					
				</div>
				
				<div class="form-group">
				  <label class="col-md-4 control-label" for="15_Nombre">Nombre *</label>
				  <div class="col-md-8">
				  	<input class="form-control" type="text" value="" name="15_Nombre" maxlength="30" id="15_Nombre" placeholder="" required/>
				  </div>
				</div>
				
				<div class="form-group">
				  <label class="col-md-4 control-label" for="15_Apellido_Pat">Apellido Paterno *</label>
				  <div class="col-md-8">
				  	<input class="form-control" type="text" value="" name="15_Apellido_Pat" maxlength="15" id="15_Apellido_Pat" placeholder="" required/>
				  </div>
				</div>
				
				<div class="form-group">
				  <label class="col-md-4 control-label" for="15_Apellido_Mat">Apellido Materno</label>
				  <div class="col-md-8">
				  	<input class="form-control" type="text" value="" name="15_Apellido_Mat" maxlength="15" id="15_Apellido_Mat" placeholder=""/>
				  </div>
				</div>
				
				<div class="form-group">
				  <label class="col-md-4 control-label" for="15_Telefono">Teléfono (8 dígitos) *<br> </label>
				
				  <div class="col-md-8">
				  	<input class="form-control" type="number" value="" name="15_Telefono" maxlength="8" id="15_Telefono" placeholder="" OnChange="validarNumeros(this)"/>
				  </div>
				</div>
				
						
				<div class="form-group">
				  <label class="col-md-4 control-label" for="15_Celular">Celular (8 dígitos) *</label>
				  <div class="col-md-8">
				  	<input class="form-control" type="number" value="" name="15_Celular" maxlength="20" id="15_Celular" placeholder="" OnChange="validarNumeros(this)"/>
				  </div>
				</div>
				
				<div class="form-group">
				  <label class="col-md-4 control-label" for="Contrasenia">Contraseña</label>
				  <div class="col-md-8">
				  	<input class="form-control" type="password" value="" name="15_Contrasenia" maxlength="30" id="15_Contrasenia" placeholder=""/>
				  </div>
				</div>
				
				<div class="form-group">
				  <label class="col-md-4 control-label" for="Contrasenia">Repetir Contraseña</label>
				  <div class="col-md-8">
				  	<input class="form-control" type="password" value="" name="R15_Contrasenia" maxlength="30" id="R15_Contrasenia" placeholder="" onChange="comprobarClave()"/>
				  </div>
				</div>
				
				<div class="form-group">
				  <label class="col-md-4 control-label" for="15_Direccion">Dirección</label>
				  <div class="col-md-8">
				  	<input class="form-control" type="text" value="" name="15_Direccion" maxlength="30" id="15_Dirección" placeholder="Casa #11, CalleDeEjemplo"/>
				  </div>
				</div>
				
				<!-- Select Basic -->
				<div class="form-group">
				  <label class="col-md-4 control-label" for="ciudadlabel">Comuna / Ciudad</label>
				  <div class="col-md-8">
					<select name="06_Id_Ciudad" id="06_Id_Ciudad" class="form-control">
						<option value="0"></option>
						<%
								Iterator<Comuna> itc = comunas.iterator();
								while(itc.hasNext())
								{
									Comuna c = (Comuna)itc.next();
						%>
								<option value="<%=c.getId_Comuna()%>"><%=c.getComuna()%> / <%=(ciudades.get(c.getId_Ciudad_Asoc()-1)).getCiudad()%></option>
						<% 		} %>
					</select>
				  </div>
				</div>
				
				<!-- CheckBox input-->
				<div class="form-group">
				  <label class="col-md-4 control-label" for="16_Cantidad_Personas">Click para escribir reclamo u comentario</label>  
				  <div class="col-md-8">
				  	<input id="reclamo" name="15_Reclamo" type="checkbox" onClick="mostrarOcultar(this)"> 
				  </div>
				</div>
				
				<!-- Text input-->
				<div class="form-group">
				  <label class="col-md-4 control-label" for="checkbox"></label>  
				  <div class="col-md-8">
				  	<input class="form-control" maxlength="50" style="visibility:hidden;" id="15_Observacion" name="15_Observacion" type="text">
				  </div>
				</div>
				
				<div class="modal-footer bounceInUp">
					<label class="col-md-4 control-label" for="espaciado"></label>
					<div class="col-md-8">
						<button id="adelanto" name="boton" class="btn btn-success" onclick='validarCelularTelefono();'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Continuar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
				
			</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Header Starts -->

<div class="navbar-wrapper">
	<div class="container">
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation" id="top-nav">
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
						<li><a href="ServletLogin?opcion=IndexTrabajadorAdmin">Menú
						</a></li>
						<li><a href="ServletLogin?opcion=CerrarSesion">Cerrar Sesión</a></li>
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
	<h2 class="text-center wowload fadeInUp">Nueva reserva</h2>
	<form class="form-horizontal wowload fadeInUp" id="Reserva"
		action="ServletReserva?opcion=Reservar" method="post">
		<fieldset>

			<!-- Form Name -->

				


			<!-- Text input-->
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4" style="text-align:center;" onClick="window.open('ServletLogin?opcion=MirarCalendario', '', 'width=700,height=600')">
					<a ><i
						class="fa fa-calendar fa-2x"></i><font size=5> Click aquí para ver reservas
							en calendario</font>
					</a>
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label" for="Fechadereserva">Fecha
					de la Reserva</label>
				<div class="col-md-4">
					<input class="form-control" type="text" name="16_Fecha"
						autocomplete="off" id="datetimepicker2" 
						onChange="HorasDisponibles()" required /> <br> 
									
						
					<p id="demodanger" class="alert alert-danger"
						style="display: none;" role="alert"></p>
					<p id="demosuccess" class="alert alert-success"
						style="display: none;" role="alert"></p>
				</div>
				<div class="col-md-2" id="CampoPendiente">
					<input id="Pendiente" name="Pendiente" type="checkbox" onClick="ActualizarPendiente()">
					<strong>¿Pendiente?</strong>
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label" for="Horadereserva">Hora</label>
				<div class="col-md-4">
					<input class="form-control" type="text" name="16_Hora"
						autocomplete="off" onClick="HorasDisponibles()"
						id="datetimepicker1" required />
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="16_Validado">¿Pre Reserva?</label>
				<div class="col-md-4" id="CampoPreReserva" >
					<input id="16_Pre_Reserva"   value="1" checked name="16_Pre_Reserva" type="radio" onChange="ActualizarPreReserva();"> Sí<br>
					<input id="NO16_Pre_Reserva" value="0" name="16_Pre_Reserva" type="radio" onChange="ActualizarPreReserva();"> No
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
							Metodo_Pago f = (Metodo_Pago)iter6.next(); %>
							<option value="<%=f.getId_Metodo_Pago()%>"><%=f.getNombre() %></option>
						<%} %>
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
									if(ts.getId_Tipo_Sesion()==6){
						%>
								<option value="<%=ts.getId_Tipo_Sesion()%>" selected><%=ts.getTipo_Sesion()%></option>
						<% 			}else{ %>
								<option value="<%=ts.getId_Tipo_Sesion()%>"><%=ts.getTipo_Sesion()%></option>
						<% 			}  	}%>
					</select>
				 </div>
			</div>
				
			<!-- Select Basic -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="17_Id_Campania">Campaña</label>
				<div class="col-md-4">

					<select name="17_Id_Campania" id="17_Id_Campania"
						class="form-control" onChange="Requiere_Cupon()" required>
						<option value="0"></option>
						<%
				Iterator<Campania> it = campa.iterator();
				while(it.hasNext())
				{
					Campania f = (Campania)it.next();
		%>
						<option value="<%=f.getId_Campania()%>"><%=f.getNombre()%></option>
						<% 			} %>
					</select>
				</div>
				
				<div class="col-md-2" onClick="window.open('ServletLogin?opcion=GenerarNuevoCampania');">
				<button name="boton" class="btn btn-info btn-xs btn-block"><i class="fa fa-camera fa-1x"> Nueva</i></button>
				</div>
			</div>
			
			<!-- Multiple Checkboxes -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="groupon">Código
					de cupón</label>
				<div class="col-md-4">
					<input class="form-control input-md" id="16_Codigo" onChange="CuponOcupado()"
						name="16_Codigo" type="text" style="visibility:hidden;" placeholder="">
				</div>
			</div>
			
			<!-- CheckBox input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="16_Validado">¿Validado?</label>
				<div class="col-md-4" id="CampoValidado" style="visibility:hidden;">
					<input id="Validado" name="16_Validado" type="checkbox">
					Sí/No
				</div>
			</div>
			
			<div id="infoanticipo" style="display:none">
			<!-- Text input-->
			<div class="panel panel-success">
			<div class="panel-heading text-center"><i
			class="fa fa-money fa-2x"></i> &nbsp;<strong><font size=5>Información del anticipo</font></strong></div>
 			<div class="panel-body">
 			
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Fecha_Anticipo">Fecha del anticipo</label>
					<div class="col-md-4">
						<input id="datetimepicker211" name="16_Fecha_Anticipo"
							type="text" class="form-control input-md" value="">	
						</div>
				</div>
								
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Nombre_Anticipo">Nombre</label>
					<div class="col-md-4">
						<input id="16_Nombre_Anticipo" name="16_Nombre_Anticipo"
							type="text" class="form-control input-md" value="">
					</div>
				</div>
				
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Tipo_Anticipo">Tipo de anticipo</label>
					<div class="col-md-4">
					<select name="16_Tipo_Anticipo" id="16_Tipo_Anticipo"
						class="form-control" required>
							<option selected value="Transferencia" selected>Transferencia</option>
							<option value="Deposito" >Depósito</option>
							<option value="TDebito" >Tarjeta de débito</option>
							<option value="TCredito" >Tarjeta de crédito</option>
							<option value="Cheque" >Cheque</option>
							<option value="Efectivo" >Efectivo</option>
					</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="16_Monto_Pago_Adelantado">Monto de Pago de Anticipo $</label>
					<div class="col-md-4">
						<input id="16_Monto_Pago_Adelantado" name="16_Monto_Pago_Adelantado"
							type="number" placeholder="1" class="form-control input-md"
							value="0">
					</div>
				</div>
							
			</div>
			</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="16_Cantidad_Personas">Cantidad
					de personas que asisten a la sesión</label>
				<div class="col-md-4">
					<input id="16_Cantidad_Personas" name="16_Cantidad_Personas"
						min="0" type="number" value="1" class="form-control input-md">

				</div>
			</div>
						
			<div class="form-group">
				<label class="col-md-4 control-label" for="15_Rut">Mail y nombre</label>
				<div class="col-md-4">	
							
					<select name="15_Id_Cliente2" id="15_Id_Cliente2" class="form-control span7"  required>
						<option value="1"></option>
						<%
						Iterator<Cliente> iter2 = clients.iterator();
						if(!iter2.hasNext()){  %>
							<option value="null">No existen clientes</option>
						<%}
						while(iter2.hasNext())
						{
							Cliente f = (Cliente)iter2.next(); %>
							<option value="<%=f.getId_Cliente()%>"><%=f.getMail() %> - <%=f.getNombre()%> <%=f.getApellido_Pat() %></option>
						<%} %>
					</select>
					
				</div>
				<div class="col-md-3">
				<a href="#nuevocliente" class="btn btn-warning btn-block" data-toggle="modal"><strong>Nuevo cliente</strong></a>
				</div>
			</div>
			
			<div class="form-group">
				  <label class="col-md-4 control-label" for="16_Ubicacion">Vendedor</label>
				  <div class="col-md-4">
					<select class="form-control"  name="16_Ubicacion" id="16_Ubicacion" required>
						<%
						Iterator<Vendedor> it3 = vendedores.iterator();
						while(it3.hasNext())
						{
							Vendedor vend = (Vendedor)it3.next();
						%>
						<option value="<%=vend.getId_Vendedor()%>"><%=vend.getVendedor()%></option>
						<% 			} %>
					</select>
				 </div>
			</div>
			
			<div class="form-group">
			  <label class="col-md-4 control-label" for="16_Observacion">Descripción</label>
			  <div class="col-md-8">
			  	<!-- <input class="form-control" type="text" value="" name="17_Descripcion" maxlength="290" id="17_Descripcion" value="" /> -->
			  	<textarea name="16_Observacion" id="16_Observacion">Descripción</textarea>
			  </div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label" for="espaciado"></label>
				<div class="col-md-4">
					<button id="adelanto" name="boton" class="btn btn-success btn-block" onclick='return Verificar_Confirmacion();'>Continuar</button>
				</div>
			</div>
		
			
		</fieldset>
	</form>


</div>
<!--Contact Ends-->

<%	} 
		%>

<!-- Footer Starts -->
<div class="footer text-center spacer">
	<p class="wowload flipInX"><a href="https://www.facebook.com/Genesis-FOTO-Estudio-CHILE-1541270662565794/" title="Facebook Genesis Estudio Fotográfico"><i class="fa fa-facebook-square fa-3x"></i></a>
	<a href="https://www.instagram.com/genesisestudio/" title="Instagram Genesis Estudio"><i class="fa fa-instagram fa-3x"></i></a></p>
 Eliodoro Yañez #1075 Oficina 21, Providencia, Santiago |&nbsp;  <a href="https://www.google.cl/maps/place/Eliodoro+Ya%C3%B1ez+1075,+Providencia,+Regi%C3%B3n+Metropolitana/@-33.4338019,-70.622082,16z/data=!4m5!3m4!1s0x9662cf7d962a86e5:0x74b763052116819e!8m2!3d-33.4337339!4d-70.6201153" target="_blank"><i class="fa fa-map-marker fa-2x"></i>(Ver mapa)</a>   |   Fono 56 2 3204 2639   |   Celular 56 9 4435 4344 <br><br>
©Copyright 2016 Genesis Estudio. Todos los derechos reservados.<br><br>
</div>
<!-- # Footer Ends -->
<a href="#home" class="gototop"><i class="fa fa-angle-up  fa-3x"></i></a>

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
	window.onLoad = function(){
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
			//document.getElementById("Validado").checked = false;
			alert("Valor "+document.getElementById("16_Pre_Reserva").value);

		}else{
			document.getElementById("16_Codigo").style.visibility = 'visible';
			document.getElementById("CampoValidado").style.visibility = 'visible';
			//document.getElementById("Validado").checked = false;
		}		
		document.getElementById('infoanticipo').style.display = 'none';
		
		if(document.getElementById("17_Id_Campania").value!= 0 && ArregloDeCanalesDeVentas[indexarray][1]==0 && ArregloDeCanalesDeVentas[indexarray][2]>0 && document.getElementById("NO16_Pre_Reserva").checked){
			document.getElementById('infoanticipo').style.display = 'block';
		}else{
			document.getElementById('infoanticipo').style.display = 'none';
		}
	};
</script>

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
			//document.getElementById("16_Validado").checked = false;
		}else{
			document.getElementById("16_Codigo").style.visibility = 'visible';
			document.getElementById("CampoValidado").style.visibility = 'visible';
			//document.getElementById("16_Validado").checked = false;
		}
		
		if(document.getElementById("17_Id_Campania").value!= 0 && ArregloDeCanalesDeVentas[indexarray][1]==0 && ArregloDeCanalesDeVentas[indexarray][2]>0 && document.getElementById("NO16_Pre_Reserva").checked){
			document.getElementById('infoanticipo').style.display = 'block';
		}else{
			document.getElementById('infoanticipo').style.display = 'none';
		}
			
	}
</script>

<script type="text/javascript">
	function Validar_Cupon(){
		var ArregloDeCanalesDeVentas = <%=canalesventasarray%>;
		var id = document.getElementById("17_Id_Campania").value;
					
		if(id=="0"){
			alert("Ingrese una campaña válida");
			return false;
		}		
				
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

<script type="text/javascript">
	function Validar_Cliente(){
		var id = document.getElementById("15_Id_Cliente").value;		
		if(id!="1"){
			return true;
		}else{
			alert("Ingrese un mail y nombre válido");
			return false;
		}
	}
</script>


<script type="text/javascript">
	function Verificar_Confirmacion(){  
		
		if(Validar_Cupon()){
			if(Validar_Cliente()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
</script>

<script>
  $(function() {
    $( "#dialog" ).dialog();
  });
</script>
<script type="text/javascript">
 function HorasDisponibles(){
 
 	$('#datetimepicker1').datetimepicker({
		datepicker:false,
		format:'H:i',
		allowTimes:['9:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00']
	});
	
 	var dia = $('#datetimepicker2').val();
 	//En caso de una fecha inválida o no seleccionada
 	if(dia==null || dia == "null" || dia==''){
 		document.getElementById('demosuccess').style.display = 'none';
 		document.getElementById("demodanger").innerHTML = "Seleccione una fecha (válida)";
 		document.getElementById('demodanger').style.display = 'block';
 		
 		$('#datetimepicker1').datetimepicker('hide');
 		$('#datetimepicker1').hide();
 		//$('#datetimepicker1').datetimepicker('hide');
 	}else{
 	//en caso de tener una fecha, buscar horas disponibles
 		$('#datetimepicker1').show();
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
 			 	
 			 	if(HorasDisponibles.length==0){
		 			 	document.getElementById('demosuccess').style.display = 'none';
				 		document.getElementById("demodanger").innerHTML = "No quedan horas disponibles para este dia<br>"
				 			+"Por favor selecciona otro día";
				 		document.getElementById('demodanger').style.display = 'block';
				 		$('#datetimepicker1').datetimepicker('hide');
						return;
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

<script type="text/javascript">
function CuponOcupado(){
 	var codigo = $('#16_Codigo').val();
 	if(codigo==null || codigo == "null" || codigo==''){
 	
 	}else{
 	//en caso de tener una fecha, buscar horas disponibles
 		var array = <%=cuponesdeshabilitados%>;
 		var index = -1;
 		
 		for(var i=0; i<array.length; i++){
 			var cupondesha = String(array[i]);
 		 	var codigo = String(codigo);
 			
 			if(codigo.toLowerCase()==cupondesha.toLowerCase()){ 		
 				alert("Este código de cupón ya ha sido usado con anterioridad, por favor ingresa uno nuevo")
 				document.getElementById("16_Codigo").value = "";
 				document.getElementById("16_Codigo").focus();
 				
 				$('#myModal').modal();
 				$('#myModal').modal('show');
				return;
 			}
 		}
 	}
 }
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
		}else{
	 		$('#datetimepicker2').datetimepicker('show');
	 		document.getElementById("datetimepicker1").required = true;
	 		document.getElementById("datetimepicker2").required = true;
	 		document.getElementById("datetimepicker1").disabled = false;
	 		document.getElementById("datetimepicker2").disabled = false;
		}
	}
</script>
<script type="text/javascript">
	function ActualizarPreReserva(){
		if(document.getElementById("16_Pre_Reserva").checked){
			document.getElementById('MetodoDePago').style.display = 'block';
			Requiere_Cupon();
		}else{
	 		document.getElementById('MetodoDePago').style.display = 'none';
	 		Requiere_Cupon();
		}
	}
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
	$('#datetimepicker211').datetimepicker({
		yearOffset:0,
		lang:'es',
		timepicker:false,
		format:'Y/m/d',
		formatDate:'Y/m/d',
		dayOfWeekStart : 1,
		//disabledWeekDays: [0],
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
function MailRepetido(){
 	var array = <%=mails%>;
 	var maildelformulario = document.getElementById('15_Mail').value;
 	for(var i=0; i<array.length; i++){
 		var mail = String(array[i][0]);
 		if(maildelformulario==mail){
 			document.getElementById('demodanger').className = "alert alert-danger";
 	 		document.getElementById('demodanger').innerHTML = "¡Mail repetido, intenta con otro correo!";
 	 		document.getElementById('demodanger').style.display = 'block';
 	 		return;
 		}
 	}
 	if(valEmail(maildelformulario)==false){
 		document.getElementById('demodanger').className = "alert alert-danger";
	 	document.getElementById('demodanger').innerHTML = "¡Correo inválido!";
	 	document.getElementById('demodanger').style.display = 'block';
	 	return;
 	}
 	if(maildelformulario != ""){
	 	document.getElementById('demodanger').className = "alert alert-success";
 		document.getElementById('demodanger').innerHTML = "Correo válido";
		document.getElementById('demodanger').style.display = 'block';
 	}
}

function valEmail(valor){
    re = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i
    if(!re.test(valor))    {
        return false;
    }else{
        return true;
    }
}
</script>
<script type='text/javascript'>
        function addFields(){
        	
            // Number of inputs to create
            var number = document.getElementById("member").value;
            // Container <div> where dynamic content will be placed
            var container = document.getElementById("Reserva");
            // Clear previous contents of the container
            while (container.hasChildNodes()) {
                container.removeChild(container.lastChild);
            }
            for (i=0;i<number;i++){
                // Append a node with a random text
                container.appendChild(document.createTextNode("Member " + (i+1)));
                // Create an <input> element, set its type and name attributes
                var input = document.createElement("input");
                input.type = "text";
                input.name = "member" + i;
                container.appendChild(input);
                // Append a line break 
                container.appendChild(document.createElement("br"));
            }
        }
    </script>


<script type="text/javascript">
	function ValidaRut(origen){
		var cadena = '32765432';
		var dv = origen.substr(8,1);
		var resultado = 0;
		var resto = 0;
		var digito = 0;
		for(i=0;i<=7;i++){
			resultado = resultado + (parseInt(origen.charAt(i)) * parseInt(cadena.charAt(i)));
		}
		resto = Math.floor(resultado/11)*11;
		digito = 11-(resultado-resto);
		switch(digito){
			case 10:
			if(dv == '0'){ return true;}
			break;
			case 11:
			if(dv == '0'){return true;}
			break;
			default:
			if(dv == digito){ return true;}
		}
		return false;
	}

	function prueba_rut(){
		var r = document.getElementById('15_Rut_Cliente').value;
		var d = r.slice(-1);
		if(r.length==8){ r = '0' + r;}
			if(r.length==9){
				if(ValidaRut(document.getElementById('15_Rut_Cliente').value)){
					return ;
				}else{
					alert('El rut ingresado no es valido.');
					document.getElementById('15_Rut_Cliente').value = '';
				}
			}else{
				alert('Ingrese el rut sin puntos ni guión.');
				document.getElementById('15_Rut_Cliente').value = '';
			}
	}
	
	function validarNumeros(Campo){
		if((document.getElementById(Campo.id).value).length <= 9){
			return true;
		}else{
			alert('Ingrese un número celular o un número telefónico válido (9 dígitos)');
			document.getElementById(Campo.id).value = "";
			document.getElementById(Campo.id).focus();
			return false;
		}
	}
	function validarCelularTelefono(){
		
		if((document.getElementById('15_Telefono').value).length > 0 || (document.getElementById('15_Celular').value).length > 0){
				
			$("[data-dismiss=modal]").trigger({ type: "click" });
			document.getElementById('15_Id_Cliente2').value = document.getElementById('15_Mail').value;
			
			$('#15_Id_Cliente2').empty();
			$('#15_Id_Cliente2').removeAttr('onclick');
			$('#15_Id_Cliente2').append('<option selected value="0">'+document.getElementById('15_Mail').value+'</option>');
						
			document.getElementById('15_Id_Cliente2').readOnly = true;
			
			var container = document.getElementById("Reserva");
			$('#nuevocliente input, #nuevocliente select').each(
			    function(index){  
			        var input = $(this);
			        //alert('Type: ' + input.attr('type') + 'Name: ' + input.attr('name') + 'Value: ' + input.val());
			        //alert('Name: ' + input.attr('name') + ' Value: ' + input.val());
			    	var inputc = document.createElement("input");
	                inputc.type = 'hidden';
	                inputc.name = input.attr('name');
	                inputc.value = input.val();
	                container.appendChild(inputc);
	                container.appendChild(document.createElement("br"));
	                container.readonly = true;
			    }
			);
			
			$('#15_Id_Cliente2').disabled = true;						
			return ;
		}else{
			alert('Ingrese un número celular o un número telefónico para continuar');
			return ;
		}
	}
</script>

<script type="text/javascript">
	function mostrarOcultar(obj) {
		document.getElementById('15_Observacion').style.visibility = (obj.checked) ? 'visible' : 'hidden';
	}
	
</script>

<script>
	function comprobarClave(){ 
	   	clave1 =  document.getElementById("15_Contrasenia").value;
	   	clave2 =  document.getElementById("R15_Contrasenia").value;
		
		if((clave1.equals(""))&&(clave2.equals(""))){
			return ;
		}
		
	   	if (clave1 != clave2){
		  document.getElementById("R15_Contrasenia").value = "";
	      alert("Las contraseñas no coinciden, intente nuevamente");
		} 
	}
</script>

<!-- 
<script src="http://code.jquery.com/jquery-latest.min.js"></script> -->
<script src="assets/dist/jquery-editable-select.min.js"></script>

<script>
      window.onload = function () {
        $('#15_Id_Cliente2').editableSelect();
        $('#default').editableSelect({ effects: 'default' });
        $('#slide').editableSelect({ effects: 'slide' });
        $('#fade').editableSelect({ effects: 'fade' });
        $('#filter').editableSelect({ filter: false });
        $('#html').editableSelect();
        $('#onselect').editableSelect({
          onSelect: function (element) {
            $('#afterSelect').html($(this).val());
          }
        });
      }

    </script>
    <script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

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
		window.location= '/Prueba/'+ URL;
	}	
</script>



</body>
</html>