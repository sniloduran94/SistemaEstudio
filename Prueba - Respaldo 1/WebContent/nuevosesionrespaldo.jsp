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

</head>

<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario =  (Trabajador)sesion.getAttribute("usuario");
			
		ArrayList<Object> reservas = (ArrayList<Object>)(request.getAttribute("reserva"));
		
		Campania camp = (Campania)(request.getAttribute("campania"));
			
		String[] fechasjs = (String[])(request.getAttribute("fechasdeshabilitadas"));		
		
		if(sesion == null || usuario == null || reservas==null || camp==null){  %>
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
	<h2 class="text-center wowload fadeInUp">Nueva/Modificar Sesión</h2>
	
	
	<!-- Información de la reserva -->
	<%	Reserva res = (Reserva)reservas.get(0); 
		String fecha = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
		fecha = sdf.format(res.getFecha());
		int reservaid = res.getId_Reserva();
		int rut = Integer.parseInt((reservas.get(1).toString()));
		String nombre = res.getId_Cliente()+"-"+(String) reservas.get(2)+" "+(String)reservas.get(3)+" "+(String)reservas.get(4);
		String campaniamostrar = res.getId_Campania()+" - "+(String)reservas.get(5);
		int personas;
		%>
   	<div class="col-md-5 wowload fadeInUp">
	<div class="panel panel-info">
			<div class="panel-heading text-center "><i
			class="fa fa-calendar fa-1x"></i> &nbsp;<strong><font size=3>Información de la reserva</font></strong></div>
 			<div class="panel-body">
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">ID Reserva</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=res.getId_Reserva() %></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Fecha</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=fecha%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Rut Cliente</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=rut%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Nombre Cliente</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=nombre%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Campaña</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=campaniamostrar%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad de personas</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=res.getCantidad_Personas()%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad de personas adicionales</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=res.getCantidad_Adicionales()%></label>
					<input type="hidden" value="<%=res.getCantidad_Adicionales() %>" id="16_Cantidad_Adicionales">
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad de veces de reagendamiento</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=res.getCantidad_Reagendamiento()%></label>
					<input type="hidden" value="<%=res.getCantidad_Reagendamiento()%>" id="16_Cantidad_Reagendamiento">
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Monto de pago en estudio</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=(res.getMonto_Pago_Estudio()==-1)?"N/A":res.getMonto_Pago_Estudio()%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Monto de pago adelantado</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=(res.getMonto_Pago_Adelantado()==-1)?"N/A":res.getMonto_Pago_Adelantado()%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Codigo</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=((res.getCodigo()).equals("null"))?"N/A":res.getCodigo()%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">¿Validado?</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=(res.isValidado())?"SI":"NO"%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Trabajador</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=res.getId_Trabajador()%>-<%=(String)reservas.get(6)%></label>
				</div><br>
		</div>
	</div>
		<div class="panel panel-info wowload fadeInUp">
			<div class="panel-heading text-center "><i
			class="fa fa-camera fa-1x"></i> &nbsp;<strong><font size=3>Información de la campaña</font></strong></div>
		<div class="panel-body">
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">ID Campaña</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=camp.getId_Campania() %></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Nombre</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=camp.getNombre() %></label>
				</div><br>
				<% SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy"); 
				   String fecha1 = sdf3.format(camp.getInicio_Vigencia());
				   String fecha2 = sdf3.format(camp.getFin_Vigencia()); %>
				 <div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Inicio de vigencia</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=fecha1%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Fin de vigencia</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=fecha2%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">¿Posee CD?</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=(camp.isCD())?"SI":"NO"%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 10x15</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=camp.getCant_10x15() %></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 15x21</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=camp.getCant_15x21() %></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 20x30</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=camp.getCant_20x30()%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad fotos 30x40</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=camp.getCant_30x40()%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Precio</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">$ <%=camp.getPrecio()%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Cantidad de personas</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for=""><%=res.getCantidad_Personas()%></label>
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Precio por persona adicional</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">$ <%=camp.getPrecio_Adicional() %></label>
					<input type="hidden" value="<%=camp.getPrecio_Adicional() %>" id="16_Precio_Adicional2">
				</div><br>
				<div class="form-group col-md-12 col-sm-12 col-xs-12">
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">Precio por vez de reagendamiento</label>
					<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="">$ <%=camp.getPrecio_Reagendamiento() %></label>
					<input type="hidden" value="<%=(camp.getPrecio_Reagendamiento())%>" id="16_Precio_Reagendamiento2">
				</div>
		</div>
	</div>
	</div>
	
	<div class="col-md-1">
	</div>
			
	<form class="form-horizontal wowload fadeInUp"
		action="ServletSesion?opcion=NuevoSesion" method="post">
		<fieldset>
		
			<%	SesionAuxiliar sa = (SesionAuxiliar) (request.getAttribute("sesionauxiliar"));
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
				String FechaAntigua = ""; 
				
				String fechareserva = sdf.format(res.getFecha());
				
				if((sa!=null) && (sa.getFecha_Entrega()!=null)){ 
							FechaAntigua = sdf2.format(sa.getFecha_Entrega());
				}
				if((sa!=null)&&(sa.getCont_Fecha_Entrega()>=0)){%>
					
					<input type="hidden" value="<%=sa.getCont_Fecha_Entrega()%>"
				name="35_Cont_Fecha_Entrega">
				
				<%}
			%>
			
			<!--  Valores escondidos de bandera -->
			
			<input type="hidden" value="<%=res.getId_Reserva()%>"
				name="16_Id_Reserva">
				
			<input type="hidden" value="<%=FechaAntigua%>"
				name="FechaAntigua">
				
			<!-- Form Name -->
			
				<%	if(sa==null){ %>
						<div class="alert alert-info alert-dismissable wowload fadeInUp">
						  <button type="button" class="close" data-dismiss="alert">&times;</button>
						  <strong><i class="fa fa-info-circle fa-1x"></i>&nbsp;&nbsp; Esta reserva aún no posee sesión asignada.</strong>
						</div>
					<%
				}else{
					%>
						<div class="alert alert-warning alert-dismissable wowload fadeInUp">
						  <button type="button" class="close" data-dismiss="alert">&times;</button>
						  <strong><i class="fa fa-info-circle fa-1x"></i>&nbsp;&nbsp; Esta reserva ya posee 
						  sesión asignada, por lo tanto, se sobreescribirá con los datos de este formulario.</strong>
						</div>
					<% 
				}
			 %>
			
			
			
			
			<!-- CheckBox input-->
			<div class="form-group">
			  <label class="col-md-4 control-label" for="35_Asistio">¿Asistió?</label>  
			  <div class="col-md-8">
			  	<% if(sa!=null && (sa.isAsistio())){ %>
			  		<input id="35_Asistio" name="35_Asistio" type="checkbox" checked onChange="ObtenerFechaSesion();DesactivarActivar();"> Sí / No
			  	<% }else{ %>
			  		<input id="35_Asistio" name="35_Asistio" type="checkbox" onChange="ObtenerFechaSesion();DesactivarActivar();"> Sí / No
			  	<% } %>
			  </div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Fecha_Entrega">Fecha de sesión<br>(Año / Mes / Día)</label>
				<div class="col-md-8">
					<input class="form-control" type="text" name="35_Fecha_Sesion"
						autocomplete="off" readonly value="<%=fechareserva%>"/>
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Valor_Por_Cobrar">Valor por cobrar&nbsp; <i
			class="fa fa-money fa-1x"></i></label>
				<div class="col-md-8">
					<% if(sa!=null && sa.getValor_Por_Cobrar()!=-1){ %>
						<input id="35_Valor_Por_Cobrar" name="35_Valor_Por_Cobrar"
						type="number" value="<%=sa.getValor_Por_Cobrar()%>" class="form-control input-md" onKeyUp="sumarExtras()">
					<% }else{ 
					   	if((res.getCodigo()).equals("")){
					   		int Anticipo = (res.getMonto_Pago_Adelantado()<0)?0:res.getMonto_Pago_Adelantado();%>
							<input id="35_Valor_Por_Cobrar" name="35_Valor_Por_Cobrar"
								type="number" value="<%=camp.getPrecio()-Anticipo%>" class="form-control input-md" onKeyUp="sumarExtras()">
						<% }else{ %>
							<input id="35_Valor_Por_Cobrar" name="35_Valor_Por_Cobrar"
								type="number" value="0" class="form-control input-md" onKeyUp="sumarExtras()">
					<% 	   }
						} %>
				</div>
			</div>

			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_CD">Valor CD&nbsp; <i
			class="fa fa-money fa-1x"></i></label>
				<div class="col-md-8">
					<% if(sa!=null && sa.getCD()!=-1){ %>
						<input id="35_CD" name="35_CD"
						type="number" value="<%=sa.getCD()%>" class="form-control input-md" onKeyUp="sumarExtras()">
					<% }else{ %>
					<input id="35_CD" name="35_CD"
						type="number" placeholder="0" class="form-control input-md" onKeyUp="sumarExtras()">
					<% } %>
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Extras">Valor extras&nbsp; <i
			class="fa fa-money fa-1x"></i></label>
				<div class="col-md-8">
					<% if(sa!=null && sa.getExtras()!=-1){ %>
						<input id="35_Extras" name="35_Extras"
							type="number" value="<%=sa.getExtras() %>" class="form-control input-md" onKeyUp="sumarExtras()">
					<% }else{ %>
						<input id="35_Extras" name="35_Extras"
							type="number" placeholder="0" class="form-control input-md" onKeyUp="sumarExtras()">
					<% } %>
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Persona_Adicional">Cantidad de persona(s) adicional(es)&nbsp; <i
			class="fa fa-money fa-1x"></i></label>
				<div class="col-md-8">
					<% if(sa!=null && sa.getPersona_Adicional()>=0){ %>
						<input id="35_Persona_Adicional" name="35_Persona_Adicional"
							type="number" value="<%=sa.getPersona_Adicional()%>" class="form-control input-md" onKeyUp="CalcularAdicional();sumarExtras()">
					<% }else{ %>
						<input id="35_Persona_Adicional" name="35_Persona_Adicional"
							type="number" value="<%=res.getCantidad_Adicionales()%>" class="form-control input-md" onKeyUp="CalcularAdicional();sumarExtras();">
					<% } %>
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="ResultadoAdicional2">Precio por persona adicional x Cant. Personas adicionales&nbsp; <i
			class="fa fa-money fa-1x"></i></label>
				<div class="col-md-8">
						<input id="ResultadoAdicional" name="ResultadoAdicional"
							 type="number" readonly class="form-control input-md" value="<%=camp.getPrecio_Adicional()*res.getCantidad_Adicionales()%>">
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Recargo_Por_Reagendar">Cantidad de veces de reagendamiento&nbsp; <i
			class="fa fa-money fa-1x"></i></label>
				<div class="col-md-8">
					<% if(sa!=null && sa.getPersona_Adicional()>=0){ %>
						<input id="35_Recargo_Por_Reagendar" name="35_Recargo_Por_Reagendar"
							type="number" value="<%=sa.getRecargo_Por_Reagendar() %>" class="form-control input-md" onKeyUp="CalcularReagendamiento();sumarExtras()">
					<% }else{ %>
						<input id="35_Recargo_Por_Reagendar" name="35_Recargo_Por_Reagendar"
							type="number" value="<%=res.getCantidad_Reagendamiento() %>" class="form-control input-md" onKeyUp="CalcularReagendamiento();sumarExtras()">
					<% } %>
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="ResultadoReagendamiento2">Precio de recargo al reagendar x Cant. veces reagendadas&nbsp; <i
			class="fa fa-money fa-1x"></i></label>
				<div class="col-md-8">
						<input id="ResultadoReagendamiento" name="ResultadoReagendamiento"
							 type="number" readonly class="form-control input-md" value="<%=(camp.getPrecio_Reagendamiento()*res.getCantidad_Reagendamiento())%>">
				</div>
			</div>
			
			<!-- Text input-->
			<div class="panel panel-success ">
			<div class="panel-heading text-center"><i
			class="fa fa-money fa-1x"></i> &nbsp;<strong><font size=3>Monto total extras</font></strong></div>
 			<div class="panel-body">
				<div class="form-group">
					<label class="col-md-4 control-label" for="35_Monto_Extras"><i
			class="fa fa-info-circle fa-1x"></i>(Autocalculado)</label>
					<div class="col-md-8">
						<% if(sa!=null){ %>
							<input id="35_Monto_Extras" name="35_Monto_Extras" type="number" placeholder="0" readonly class="form-control input-md" value="<%=sa.getMonto_Extras()%>">
						<% }else{ %>
							<input id="35_Monto_Extras" name="35_Monto_Extras" type="number" placeholder="0" readonly class="form-control input-md">
						<% } %>
					</div>
				</div>
			</div>
			</div>
			
			<!-- Multiple Checkboxes -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Fotografo">Fotógrafo&nbsp; <i
			class="fa fa-user fa-1x"></i></label>
				<div class="col-md-8">
					<% if(sa!=null){ %>
							<input class="form-control input-md" id="35_Fotografo"
								name="35_Fotografo" type="text" value="<%=sa.getFotografo()%>">
						<% }else{ %>
							<input class="form-control input-md" id="35_Fotografo"
								name="35_Fotografo" type="text" placeholder="Ejemplo: Luis">
						<% } %>
				</div>
			</div>
			
			<!-- Text input-->
			<div class="panel panel-primary">
			<div class="panel-heading text-center"><i
			class="fa fa-file-photo-o fa-1x"></i> &nbsp;<strong><font size=3>Cantidades de fotos</font></strong><br><br>
			(Por defecto, la cantidad de la campaña)</div>
 			<div class="panel-body">
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-2 control-label" for="35_Cant_10x15">Fotos 10x15&nbsp; </label>
				<div class="col-md-3">
					<% if(sa!=null){ %>
						<input id="35_Cant_10x15" name="35_Cant_10x15" type="number" value="<%=sa.getCant_10x15()+Integer.parseInt((String)reservas.get(7))%>" class="form-control input-md" />
					<% }else{ %>
						<input id="35_Cant_10x15" name="35_Cant_10x15" type="number" value="<%=(String)reservas.get(7)%>" class="form-control input-md" />
					<% } %>
					<input id="17_Cant_10x15" style="display:none;" name="17_Cant_10x15" type="number" value="<%=(String)reservas.get(7)%>" class="form-control input-md" />
				</div>
			
			<!-- Text input-->
				<label class="col-md-2 control-label" for="35_Cant_15x21">Fotos 15x21&nbsp;  </label>
				<div class="col-md-3">
					<% if(sa!=null){ %>
						<input id="35_Cant_15x21" name="35_Cant_15x21" type="number" value="<%=sa.getCant_15x21()+Integer.parseInt((String)reservas.get(8))%>" class="form-control input-md">
					<% }else{ %>
						<input id="35_Cant_15x21" name="35_Cant_15x21" type="number" value="<%=(String)reservas.get(8)%>" class="form-control input-md">
					<% } %>
					<input id="17_Cant_15x21" style="display:none;" name="17_Cant_15x21" type="number" value="<%=(String)reservas.get(8)%>" class="form-control input-md">
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-2 control-label" for="35_Cant_20x30">Fotos 20x30&nbsp;</label>
				<div class="col-md-3">
					<% if(sa!=null){ %>
						<input id="35_Cant_20x30" name="35_Cant_20x30" type="number" value="<%=sa.getCant_20x30()+Integer.parseInt((String)reservas.get(9))%>" class="form-control input-md">
					<% }else{ %>
						<input id="35_Cant_20x30" name="35_Cant_20x30" type="number" value="<%=(String)reservas.get(9)%>" class="form-control input-md">
					<% } %>
					<input id="17_Cant_20x30" style="display:none;" name="17_Cant_20x30" type="number" value="<%=(String)reservas.get(9)%>" class="form-control input-md">
				</div>
			
			<!-- Text input-->
				<label class="col-md-2 control-label" for="35_Cant_30x40">Fotos 30x40&nbsp; </label>
				<div class="col-md-3">
					<% if(sa!=null){ %>
						<input id="35_Cant_30x40" name="35_Cant_30x40" type="number" value="<%=sa.getCant_30x40()+Integer.parseInt((String)reservas.get(10))%>" class="form-control input-md">
					<% }else{ %>
						<input id="35_Cant_30x40" name="35_Cant_30x40" type="number" value="<%=(String)reservas.get(10)%>" class="form-control input-md">
					<% } %>
					<input id="17_Cant_30x40" style="display:none;" name="17_Cant_30x40" type="number" value="<%=(String)reservas.get(10)%>" class="form-control input-md">
				</div>
			</div>
			
			</div></div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Numero_Ticket">Número de ticket&nbsp; <i
			class="fa fa-ticket fa-1x"></i></label>
				<div class="col-md-8">
					<% if(sa!=null && sa.getNumero_Ticket()!=-1){ %>
						<input id="35_Numero_Ticket" name="35_Numero_Ticket"
							type="number" value="<%=sa.getNumero_Ticket()%>" class="form-control input-md">
					<% }else{ %>
						<input id="35_Numero_Ticket" name="35_Numero_Ticket"
							type="number" placeholder="1" class="form-control input-md">
					<% } %>
				</div>
			</div>
			
			<!-- CheckBox input-->
			<div class="form-group">
			  <label class="col-md-4 control-label" for="35_Fotos_Seleccionadas">¿Fotos seleccionadas?</label>  
			  <div class="col-md-8">
			  	<% if(sa!=null && (sa.isFotos_Seleccionadas())){ %>
			  		<input id="35_Fotos_Seleccionadas" name="35_Fotos_Seleccionadas" checked type="checkbox" onChange="ObtenerFechaPropuesta()"> Sí / No
			  	<% }else{ %>
				  	<input id="35_Fotos_Seleccionadas" name="35_Fotos_Seleccionadas" type="checkbox" onChange="ObtenerFechaPropuesta()"> Sí / No
				<% } %>
			  </div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Fecha_Entrega">Fecha de entrega<br>(Año / Mes / Día)</label>
				<div class="col-md-8">
					<% 	if((sa!=null) && (sa.getFecha_Entrega()!=null)){ 
							String Fecha = sdf2.format(sa.getFecha_Entrega());
					%>
							<input class="form-control" type="text" name="35_Fecha_Entrega"
								autocomplete="off" value="<%=Fecha%>" id="datetimepicker21"/>
						<% }else{ %>
							<input class="form-control" type="text" name="35_Fecha_Entrega"
								autocomplete="off" id="datetimepicker21" onLoad="ObtenerFechaPropuesta()"/>
						<% } %>
				</div>
				<% 	if((sa!=null) && (sa.getCont_Fecha_Entrega()>0)){ 	%>
				<label class="col-md-8 control-label" for="35_Fecha_Entrega">Fecha modificada <%=sa.getCont_Fecha_Entrega()%> vez/veces</label>
				<% } %>
			</div>
					
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Fecha_Envio_Imprimir">Fecha de envío a imprimir&nbsp; <i
			class="fa fa-print fa-1x"></i></label>
				<div class="col-md-8">
					<% 	if(sa!=null && sa.getFecha_Envio_Imprimir()!=null){ 
						String Fecha2 = sdf2.format(sa.getFecha_Envio_Imprimir());
					%>
							<input class="form-control" type="text" name="35_Fecha_Envio_Imprimir"
								autocomplete="off" value="<%=Fecha2%>" id="datetimepicker2"/> 
					<%  }else{ %>
							<input class="form-control" type="text" name="35_Fecha_Envio_Imprimir"
								autocomplete="off" id="datetimepicker2"/> 
					<%  } %>
				</div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Monto_Impresion">Monto impresión&nbsp; <i
			class="fa fa-print fa-1x"></i></label>
				<div class="col-md-8">
					<% if(sa!=null){ %>
						<input id="35_Monto_Impresion" name="35_Monto_Impresion"
							type="number" placeholder="1" value="<%=sa.getMonto_Impresion()%>" class="form-control input-md">
					<% }else{ %>
						<input id="35_Monto_Impresion" name="35_Monto_Impresion"
							type="number" value="0" class="form-control input-md">
					<% } %>
				</div>
			</div>
			
			<!-- Multiple Checkboxes -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Numero_Factura">N° de Factura</label>
				<div class="col-md-8">
					<% if(sa!=null){ %>
						<input class="form-control input-md" id="35_Numero_Factura"
							name="35_Numero_Factura" type="text" value="<%=sa.getNumero_Factura() %>" placeholder="" >
					<% }else{ %>
						<input class="form-control input-md" id="35_Numero_Factura"
							name="35_Numero_Factura" type="text" placeholder="" >
					<% } %>
				</div>
			</div>
			
			<!-- CheckBox input-->
			<div class="form-group">
			  <label class="col-md-4 control-label" for="35_Lista_Para_Entregar">¿Listas para entregar?</label>  
			  <div class="col-md-8">
			  	<% if(sa!=null && (sa.isLista_Para_Entregar())){ %>
			  		<input id="35_Lista_Para_Entregar" name="35_Lista_Para_Entregar" checked type="checkbox" onChange="CambioListaParaEntregar()"> Sí / No
			  	<% }else{ %>
				  	<input id="35_Lista_Para_Entregar" name="35_Lista_Para_Entregar" type="checkbox" onChange="CambioListaParaEntregar()"> Sí / No
				<% } %>
			  </div>
			</div>
			
			<!-- CheckBox input-->
			<div class="form-group">
			  <label class="col-md-4 control-label" for="35_Entregadas">¿Entregadas?</label>  
			  <div class="col-md-8">
			  	<% if(sa!=null && (sa.isEntregadas())){ %>
			  		<input id="35_Entregadas" name="35_Entregadas" checked type="checkbox" onChange="ObtenerFechaRetiro();CambioEntregadas()" > Sí / No
			  	<% }else{ %>
				  	<input id="35_Entregadas" name="35_Entregadas" type="checkbox" onChange="ObtenerFechaRetiro();CambioEntregadas();" > Sí / No
				<% } %>
			  </div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Fecha_Retiro">Fecha de retiro<br>(Año / Mes / Día)</label>
				<div class="col-md-8">
					<% 	if((sa!=null) && (sa.getFecha_Retiro()!=null)){ 
							String Fecha6 = sdf2.format(sa.getFecha_Retiro());
					%>
							<input class="form-control" type="text" name="35_Fecha_Retiro"
								autocomplete="off" value="<%=Fecha6%>" id="datetimepicker22" />
						<% }else{ %>
							<input class="form-control" type="text" name="35_Fecha_Retiro"
								autocomplete="off" id="datetimepicker22" />
						<% } %>
				</div>
			</div>
			
			<!-- Multiple Checkboxes -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="35_Fotografo">¿Quién retira?<br>Especifique nombre&nbsp; <i
			class="fa fa-user fa-1x"></i></label>
				<div class="col-md-8">
					<% if((sa!=null)&&(sa.getNombre_Retira()!=null)&&(!sa.getNombre_Retira().equals(""))){ %>
							<input class="form-control input-md" id="35_Nombre_Retira"
								name="35_Nombre_Retira" type="text" value="<%=sa.getNombre_Retira()%>" >
						<% }else{ %>
							<input class="form-control input-md" id="35_Nombre_Retira"
								name="35_Nombre_Retira" type="text" placeholder="Ejemplo: Luis" >
						<% } %>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-12 control-label" for="espaciado"></label>
				<div class="col-md-12">
					<button id="adelanto" name="boton" class="btn btn-success btn-block">Continuar</button>
				</div>
			</div>

		</fieldset>
	</form>
	<%	if((sa!=null) && (sa.getNumero_Ticket()>-1) && (sa.isAsistio())){ %>
				<script type="text/javascript">
					document.getElementById("35_Asistio").disabled = true;
					document.getElementById("datetimepicker23").readOnly = true;
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
				</script>
		<% }if((sa!=null) && (sa.getNumero_Ticket()<=1) && (sa.isAsistio())){ %>
				<script type="text/javascript">
					document.getElementById("datetimepicker21").readOnly = true;
					document.getElementById("datetimepicker2").readOnly = true;
					document.getElementById("35_Monto_Impresion").readOnly = true;
					document.getElementById("35_Numero_Factura").readOnly = true;
					document.getElementById("35_Lista_Para_Entregar").disabled = true;
					document.getElementById("35_Entregadas").disabled = true;
					document.getElementById("datetimepicker22").readOnly = true;
					document.getElementById("35_Nombre_Retira").readOnly = true;
					document.getElementById("datetimepicker22").readOnly = true;
					document.getElementById("35_Nombre_Retira").readOnly = true;
				</script>
					
		<% }if((sa==null)){ %>	
				<script type="text/javascript">
					document.getElementById("datetimepicker23").readOnly = true;
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
					document.getElementById("datetimepicker21").readOnly = true;
					document.getElementById("datetimepicker2").readOnly = true;
					document.getElementById("35_Monto_Impresion").readOnly = true;
					document.getElementById("35_Numero_Factura").readOnly = true;
					document.getElementById("35_Lista_Para_Entregar").disabled = true;
					document.getElementById("35_Entregadas").disabled = true;
					document.getElementById("datetimepicker22").readOnly = true;
					document.getElementById("35_Nombre_Retira").readOnly = true;
					document.getElementById("datetimepicker22").readOnly = true;
					document.getElementById("35_Nombre_Retira").readOnly = true;
				</script>		
		<% } 
		   if((sa!=null) && !(sa.isFotos_Seleccionadas())){ %>
		   	 <script type="text/javascript">
		    		document.getElementById("datetimepicker21").readOnly = true;
					document.getElementById("datetimepicker2").readOnly = true;
					document.getElementById("35_Monto_Impresion").readOnly = true;
					document.getElementById("35_Numero_Factura").readOnly = true;
					document.getElementById("35_Lista_Para_Entregar").disabled = true;
			  </script>
		<% }if((sa!=null)&&!(sa.isLista_Para_Entregar())){ %>
				 <script type="text/javascript">
					document.getElementById("35_Entregadas").disabled = true;
					document.getElementById("datetimepicker22").readOnly = true;
					document.getElementById("35_Nombre_Retira").readOnly = true;
			  </script>
		<% }if((sa!=null)&&!(sa.isEntregadas())){ %>
			  <script type="text/javascript">
					document.getElementById("datetimepicker22").readOnly = true;
					document.getElementById("35_Nombre_Retira").readOnly = true;
			  </script>
		<% } %>
</div>
<hr>
<!--Contact Ends-->

<%	} 
		%>

<!-- Footer Starts -->
<div class="footer text-center spacer">
	<p class="wowload flipInX"><a href="https://www.facebook.com/Genesis-FOTO-Estudio-CHILE-1541270662565794/" title="Facebook Genesis Estudio Fotográfico"><i class="fa fa-facebook-square fa-3x"></i></a>
	<a href="https://www.instagram.com/genesisestudio/" title="Instagram Genesis Estudio"><i class="fa fa-instagram fa-3x"></i></a></p>
 Ramón Carnicer #37 Piso 1, Providencia, Santiago |&nbsp;  <a href="https://www.google.cl/maps/place/Eliodoro+Ya%C3%B1ez+1075,+Providencia,+Regi%C3%B3n+Metropolitana/@-33.4338019,-70.622082,16z/data=!4m5!3m4!1s0x9662cf7d962a86e5:0x74b763052116819e!8m2!3d-33.4337339!4d-70.6201153" target="_blank"><i class="fa fa-map-marker fa-2x"></i>(Ver mapa)</a>   |   Fono 56 2 3204 2639   |   Celular 56 9 4435 4344 <br><br>
©Copyright 2016 Genesis Estudio. Todos los derechos reservados.<br><br>
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

<script type="text/javascript">
	function redirect(URL){
		window.location= '/Prueba/'+ URL;
	}	
</script>

<script type="text/javascript">
	sumaFecha = function(d, fecha)
	{
		 var Fecha = new Date();
		 var sFecha = fecha || (Fecha.getFullYear() + "/" + (Fecha.getMonth() +1) + "/" +  Fecha.getDate());
		 var sep = sFecha.indexOf('/') != -1 ? '/' : '-'; 
		 var aFecha = sFecha.split(sep);
		 var fecha = aFecha[0]+'/'+aFecha[1]+'/'+aFecha[2];
		 fecha= new Date(fecha);
		 fecha.setDate(fecha.getDate()+parseInt(d));
		 var anno=fecha.getFullYear();
		 var mes= fecha.getMonth()+1;
		 var dia= fecha.getDate();
		 mes = (mes < 10) ? ("0" + mes) : mes;
		 dia = (dia < 10) ? ("0" + dia) : dia;
		 var fechaFinal = anno+sep+mes+sep+dia;
		 return (fechaFinal);
	 }
</script>

<script type="text/javascript">
	function ObtenerFechaPropuesta(){
		if(document.getElementById("35_Fotos_Seleccionadas").checked == false){
			document.getElementById("datetimepicker21").value = "";
			document.getElementById("datetimepicker21").readOnly = true;
			document.getElementById("datetimepicker21").disabled = true;
			document.getElementById("datetimepicker21").readOnly = true;
			document.getElementById("datetimepicker2").readOnly = true;
			document.getElementById("35_Monto_Impresion").readOnly = true;
			document.getElementById("35_Numero_Factura").readOnly = true;
			document.getElementById("35_Lista_Para_Entregar").disabled = true;
			return;
		}
		document.getElementById("datetimepicker21").value = "";
		document.getElementById("datetimepicker21").readOnly = false;
		document.getElementById("datetimepicker21").disabled = false;
		document.getElementById("datetimepicker21").readOnly = false;
		document.getElementById("datetimepicker2").readOnly = false;
		document.getElementById("35_Monto_Impresion").readOnly = false;
		document.getElementById("35_Numero_Factura").readOnly = false;
		document.getElementById("35_Lista_Para_Entregar").disabled = false;
			
		var f=new Date();
		var ano = f.getFullYear();
		var mes = f.getMonth();
		var dia = f.getDate();
		var diaSemana = f.getDay();
		var meses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
		var diasSemana = new Array("Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado");
		var DiaEntrega = 0;
		var mesfinal = "";
		if((mes+1)<10){
			mesfinal = "0"+(mes+1);
		}else{
			mesfinal = (mes+1);
		}
		
		var fechaactual = ("'"+ano+"/"+(mesfinal)+"/"+dia+"'");
		if(diaSemana>=3){
			document.getElementById("datetimepicker21").value = sumaFecha((12-diaSemana), fechaactual);
		}else{
			document.getElementById("datetimepicker21").value = sumaFecha((5 - diaSemana), fechaactual);
		}
	}
	function ObtenerFechaSesion(){
		if(document.getElementById("35_Asistio").checked == false){
			document.getElementById("datetimepicker23").value = "";
			document.getElementById("datetimepicker23").readOnly = true;
			document.getElementById("datetimepicker23").disabled = true;
			return;
		}
		var f=new Date();
		var ano = f.getFullYear();
		var mes = f.getMonth();
		var dia = f.getDate();
		var diaSemana = f.getDay();
		var meses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
		var diasSemana = new Array("Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado");
		var DiaEntrega = 0;
		var mesfinal = "";
		if((mes+1)<10){
			mesfinal = "0"+(mes+1);
		}else{
			mesfinal = (mes+1);
		}
		
		var fechaactual = (ano+"/"+(mesfinal)+"/"+dia);
		document.getElementById("datetimepicker23").value = fechaactual;

	}
	function ObtenerFechaRetiro(){
		if(document.getElementById("35_Entregadas").checked == false){
			document.getElementById("datetimepicker22").value = "";
			document.getElementById("datetimepicker22").readOnly = true;
			document.getElementById("datetimepicker22").disabled = true;
			document.getElementById("35_Nombre_Retira").value = "";
			document.getElementById("35_Nombre_Retira").readOnly = true;
			document.getElementById("35_Nombre_Retira").disabled = true;
			
			return;
		}
		document.getElementById("datetimepicker22").readOnly = false;
		document.getElementById("datetimepicker22").disabled = false;
		document.getElementById("35_Nombre_Retira").value = "";
		document.getElementById("35_Nombre_Retira").readOnly = false;
		document.getElementById("35_Nombre_Retira").disabled = false;
		var f=new Date();
		var ano = f.getFullYear();
		var mes = f.getMonth();
		var dia = f.getDate();
		var diaSemana = f.getDay();
		var meses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
		var diasSemana = new Array("Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado");
		var DiaEntrega = 0;
		var mesfinal = "";
		if((mes+1)<10){
			mesfinal = "0"+(mes+1);
		}else{
			mesfinal = (mes+1);
		}
		
		var fechaactual = (ano+"/"+(mesfinal)+"/"+dia);
		document.getElementById("datetimepicker22").value = fechaactual;

	}
</script> 

<script type="text/javascript">
	window.onload = function()
	{	
		var num1 = Number(document.getElementById("35_Persona_Adicional").value);
		var num2 = Number(document.getElementById("16_Precio_Adicional2").value);
		document.getElementById("ResultadoAdicional").value = num1 * num2;
		var num1 = Number(document.getElementById("35_Recargo_Por_Reagendar").value);
		var num2 = Number(document.getElementById("16_Precio_Reagendamiento2").value);
		document.getElementById("ResultadoReagendamiento").value = num1 * num2;		
	    var num1 = Number(document.getElementById("35_Valor_Por_Cobrar").value);
		var num2 = Number(document.getElementById("35_CD").value);
		var num3 = Number(document.getElementById("35_Extras").value);
		var num4 = Number(document.getElementById("ResultadoAdicional").value);
		var num5 = Number(document.getElementById("ResultadoReagendamiento").value);
		document.getElementById("35_Monto_Extras").value = num1 + num2 + num3 + num4 + num5;
	}
</script>

<script type="text/javascript">
	function CalcularAdicional(){
		var num1 = Number(document.getElementById("35_Persona_Adicional").value);
		var num2 = Number(document.getElementById("16_Precio_Adicional2").value);
		document.getElementById("ResultadoAdicional").value = num1 * num2;
	}	
</script>

<script type="text/javascript">
	function CalcularReagendamiento(){
		var num1 = Number(document.getElementById("35_Recargo_Por_Reagendar").value);
		var num2 = Number(document.getElementById("16_Precio_Reagendamiento2").value);
		document.getElementById("ResultadoReagendamiento").value = num1 * num2;
	}	
</script>

<script type="text/javascript">
	function sumarExtras(){
		var num1 = Number(document.getElementById("35_Valor_Por_Cobrar").value);
		var num2 = Number(document.getElementById("35_CD").value);
		var num3 = Number(document.getElementById("35_Extras").value);
		var num4 = Number(document.getElementById("ResultadoAdicional").value);
		var num5 = Number(document.getElementById("ResultadoReagendamiento").value);
		document.getElementById("35_Monto_Extras").value = num1 + num2 + num3 + num4 + num5;
	}	
</script>
<script type="text/javascript">
	document.addEventListener("mousewheel", function(event){
    if(document.activeElement.type === "number"){
        document.activeElement.blur();
    }
});
</script>


</body>
</html>