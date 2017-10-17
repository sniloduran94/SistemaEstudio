<%@page import="java.util.Iterator"%>
<%@page import="modelo.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title></title>

<!-- <link rel="stylesheet" href="assets/table/table.css">  -->

<link href='http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100.caa' rel='stylesheet' type='text/css'>

<!-- Google fonts -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,700' rel='stylesheet' type='text/css'>

<!-- font awesome -->
<!--<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">-->

<!-- Custom Fonts -->
<link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">

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
<link rel="stylesheet" type="text/css" href="assets/bootstrap/css/jquery.datetimepicker.css"/>

<!-- Fin Calendario -->

</head>
	<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario = (Trabajador)(sesion.getAttribute("usuario"));
		if(sesion == null || usuario == null){  %>
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

        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation" id="top-nav">
          <div class="container">
            <div class="navbar-header">
              <!-- Logo Starts -->
              <a class="navbar-brand" href="#home"><img src="images/LogoLetras2.png" alt="logo"></a>
              <!-- #Logo Ends -->

              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
            </div>

             <!-- Nav Starts -->
            <div class="navbar-collapse  collapse">
              <ul class="nav navbar-nav navbar-right scroll">
              	 <% if(usuario.getEsAdmin()==1){ %>
              		<li onClick="redirect('ServletLogin?opcion=IndexTrabajadorAdmin')"><a href="">Menú</a></li> 	
              	 <% }else{ %>
              	 	<li onClick="redirect('ServletLogin?opcion=IndexTrabajadorFotografo')"><a href="">Menú</a></li>		
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

<body>

<div class="container spacer wowload fadeInUp">
  <% if(request.getAttribute("mensaje") != null && request.getAttribute("tipomensaje")!=null){
		if(!(request.getAttribute("mensaje").equals("")) &&  request.getAttribute("tipomensaje").equals("success")){%>
		<!-- Mensaje-->
		<div class="alert alert-success alert-dismissable">
		  <button type="button" class="close" data-dismiss="alert">&times;</button>
		  <%=request.getAttribute("mensaje")%>
	  	</div>
   <%	}else{	%>
   		<!-- Mensaje-->
		<div class="alert alert-danger alert-dismissable">
		  <button type="button" class="close" data-dismiss="alert">&times;</button>
		  <%=request.getAttribute("mensaje")%>
	  	</div>
  <%	} 
	  } %>
  <h2>Reservas</h2>
	<% if(request.getAttribute("SinFiltros")==null){ %>
	
  	<form action="ServletReserva?opcion=FiltroReservas" method="post">
	<div class="form-group wowload fadeInUp">
	  <label class="col-md-2 control-label" for="rango">Buscar en rango de fechas <i class="fa fa-calendar fa-1x"></i></label>
	  <div class="col-md-4">
	  	<input class="form-control" type="text" id="datetimepicker2" autocomplete="off" placeholder="2016/01/30" name="Inicio"/><br>
	  	<input class="form-control" type="text" id="datetimepicker21" autocomplete="off" placeholder="2016/01/31" name="Fin" /><br>
	  </div>
	</div>
	<div class="form-group wowload fadeInUp">
	  <label class="col-md-2 control-label" for="rango">Buscar por nombre de cliente <i class="fa fa-user fa-1x"></i></label>
	  <div class="col-md-4">
	  	<input class="form-control" type="text" id="15_Nombre" autocomplete="off" placeholder="Luis" name="15_Nombre"/><br>
	  </div>
	</div>
	<div class="form-group wowload fadeInUp">
	  <label class="col-md-2 control-label" for="rango">Buscar por apellido de cliente <i class="fa fa-user fa-1x"></i></label>
	  <div class="col-md-4">
	  	<input class="form-control" type="text" id="15_Apellido_Pat" autocomplete="off" placeholder="Luis" name="15_Apellido_Pat"/><br>
	  	</div>
	</div>
	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
	</form>
	
	<br><br>
	<form action="ServletReserva?opcion=FiltroPreReserva" method="post">
	<div class="form-group wowload fadeInUp">
	  <div class="col-md-6 col-sm-6">
	  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block "><i class="fa fa-calendar fa-1x"></i> Mostrar sólo pre reservas <i class="fa fa-search fa-1x"></i></button>
	  </div>
	</div>
	</form>
	<br><br><br><br>
	
	<% } %>
	<%
				//Obtención de reservas para tabla de visualizacion de reservas 
				ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)(request.getAttribute("reservas"));
				Iterator<ArrayList<Object>> iterres1 = reservas.iterator();
				
				System.out.println("Pasa por acá");
				
				if(!iterres1.hasNext()){  %>
						<h3>No existen datos para mostrar </h3>
						</div>
	<%			}else{	%>
				  <div class="col-md-6 wowload fadeInUp">
					<h4>Resultados: <%=reservas.size()%></h4><br>
					<div class = "col-md-4"></div>
					<a href="ServletLogin?opcion=MirarCalendario" method="post">
						<button type="submit" class="btn btn-success btn-sm" name="Calendario" title="Mirar en un calendario todas las reservas" value="Calendario" >
                			<i class="fa fa-calendar fa-1x"></i> Ver reservas en calendario
            			</button>
            		</a>
            	  </div>
            		<table id="table" class="table table-hover wowload fadeInUp">
					    <thead>
						  <tr style="font-size:13px;">
							<th ><h5>Fecha<br>Año/Mes/Día</h5></th>
							<th ><h5>Cliente</h5></th>
							<th ><h5>Campaña</h5></th>
							<th style="width:15%" class="reserva"><h5>Descripción</h5></th>
							<th class="reserva"><h5>Personas</h5></th>
							<th class="reserva"><h5>Personas<br>Adicionales</h5></th>
							<th class="reserva"><h5>Veces<br>Reagendar</h5></th>
							<th class="reserva"><h5>Monto Anticipo</h5></th>
							<% if(usuario.getEsAdmin()==1){ %>
									<th class="reserva"><h5>Código<br>Cupón</h5></th>
									<th class="reserva"><h5>¿Pre<br>Reserva?</h5></th>
									<th ><h5>Modificar/Eliminar</h5></th>
							<% }else{ %>
									<th class="campania"><h5>Precio</h5></th>
									<th class="campania"><h5>Máx.<br>Pers.*</h5></th>
									<th class="campania"><h5>CD?/<br>Cant.<br>Fotos</h5></th>
									<th class="campania"><h5>Cant<br>Fotos</h5></th>
									<th class="campania"><h5>Precio<br>Persona<br>Adicional</h5></th>
									<th class="campania"><h5>Precio<br>por<br>Reagendar</h5></th>
									<th ><h5>Cambiar vista<br>Campaña/Reserva</h5></th>
							<% } %>
						  </tr>
						</thead>
						<tbody>
	<%				while(iterres1.hasNext())
					{
						ArrayList<Object> nueva = iterres1.next();
						Reserva res = (Reserva)nueva.get(0); 
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						String fecha = null;
						 
						if(res.getFecha()!=null){  // Fecha reserva
							fecha = sdf.format(res.getFecha());
						}else{
							fecha = "Pendiente";
						}		
									
						int reservaid = res.getId_Reserva();
						int rut = Integer.parseInt((nueva.get(1).toString()));
						String nombre = (String) nueva.get(2)+" "+(String)nueva.get(3)+" "+(String)nueva.get(4);
						String campaniamostrar = res.getId_Campania()+" - "+(String)nueva.get(5);
						
						String tipodesesionmostrar = (String)nueva.get(12);
						int personas;
						String descripcion = "N/A";
						if(res.getObservacion() != null){
							if(res.getObservacion().length()>80){
								descripcion = res.getObservacion().substring(0,80);
							}else{
								if(res.getObservacion().equals("null") || res.getObservacion().equals("Descripción")){
									descripcion = "N/A";
								}else{
									descripcion = res.getObservacion();
								}
							}
						}
						%>						
						<tr >
							<td ><h5><%=fecha%></h5></td>
							<% if(usuario.getEsAdmin()==1){ %>
								<td style="white-space: nowrap"><h5><%=nombre%></h5></td>
							<% }else{ %>
								<td style="white-space: nowrap"><h5><%=nombre%><br><br><br><br></h5></td>
							<% } %>
							<td ><h5><%=campaniamostrar%><br>Tipo: <%=tipodesesionmostrar%></h5></td>
							<td ><h5><%=descripcion%></h5></td>
							<td ><h5><%=res.getCantidad_Personas()%></h5></td>
							<td ><h5><%=res.getCantidad_Adicionales()%></h5></td>
							<td ><h5><%=res.getCantidad_Reagendamiento() %></h5></td>
							<td ><h5><%=(res.getMonto_Pago_Adelantado()==-1)?"N/A":res.getMonto_Pago_Adelantado()%></h5></td>
							<% if(usuario.getEsAdmin()==1){ %>
									<td ><h5><%=((res.getCodigo()).equals("null"))?"N/A":res.getCodigo()%></h5></td>
									<td ><h5><%=(res.isPreReserva())?"SI":"NO"%></h5></td>
							<% }else{ %>
								<td ><h5><%=(Integer)nueva.get(16)%></h5></td>
								<td ><h5><%=(Integer)nueva.get(15)%></h5></td>
								<td ><h5><%=((boolean)nueva.get(17))?"Sí":"No"%>-<%=(Integer)nueva.get(18)%></h5></td>
								<td ><h5>10x15:&nbsp;<%=(String)nueva.get(7)%><br>15x21:&nbsp;<%=(String)nueva.get(8)%><br>20x30:&nbsp;<%=(String)nueva.get(9)%><br>30x40:&nbsp;<%=(String)nueva.get(10)%></h5></td>
								<td ><h5><%=(Integer)nueva.get(19)%></h5></td>
								<td ><h5><%=(Integer)nueva.get(20)%></h5></td>			
							<% } %>				
							<form action="ServletReserva?opcion=CambiarReserva" method="post">
									<input type="hidden" value="<%=res.getId_Reserva()%>" name="16_Id_Reserva">
							<td>	 
							<div class="btn-group  btn-group-lg">
									 <button style="display:none;" type="submit" class="btn btn-success" name="AgregarSesion" value="Agregar" title="Agregar datos de una nueva sesión">
                						<i class="fa fa-plus fa-1x"></i>
            						 </button>
            						 
            				<% if(usuario.getEsAdmin()==1){ %>
									 <button type="submit" class="btn btn-success" name="ModificarReserva" value="Modificar" >
                						<i class="fa fa-pencil-square-o fa-1x"></i>
            						 </button>
								     <button type="submit" class="btn btn-danger" name="EliminarReserva" value="Eliminar" onclick="return confirm('¿Estás seguro que deseas eliminar esta reserva?')">
                						<i class="fa fa-trash fa-1x"></i>
            						 </button>
            				<% }else{ %>
            						<a onclick="CambiarConfiguracionCampania()" href="javascript:void(0);">
                							<i class="fa fa-camera fa-2x"></i>
            						 </a>
            						 <a onclick="CambiarConfiguracionReserva()" href="javascript:void(0);">
                							<i class="fa fa-calendar fa-2x"></i>
            						 </a>
            				<% } %> 
            				</div>
							<button style="display:none;"  type="submit" class="btn btn-success" name="ReenviarMail" value="ReenviarMail" >
                				<i class="fa fa-envelope fa-1x"></i>
            				</button>
            				</td>
							</form>		
						</tr>
	<% 				} %>
			    </tbody>
			  </table>
			</div>
	<%  }
	}	%>	

<!-- Footer Starts -->
<div class="footer text-center spacer">
	<p class="wowload flipInX">Sistema Estudio. Advancing Group Ltda.</a></p>
 <br><br>
©Copyright 2017. Todos los derechos reservados.<br><br>
</div>
<!-- # Footer Ends -->
<a href="#home" class="gototop "><i class="fa fa-angle-up  fa-3x"></i></a>

<!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls">
    <!-- The container for the modal slides -->
    <div class="slides"></div>
    <!-- Controls for the borderless lightbox -->
    <h3 class="title">Title</h3>
    <a class="prev">â¹</a>
    <a class="next">âº</a>
    <a class="close">Ã</a>
    <!-- The modal dialog, which will be used to wrap the lightbox content -->    
</div>

<!-- jquery -->
<script src="assets/jquery.js"></script>

<!-- wow script -->
<script src="assets/wow/wow.min.js"></script>


<!-- boostrap -->
<script src="assets/bootstrap/js/bootstrap.js" type="text/javascript" ></script>

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
lang:'en',
disabledDates:['1986/01/08','1986/01/09','1986/01/10'],
startDate:	'1986/01/05'
});
$('#datetimepicker').datetimepicker({value:'2015/04/15 05:03',step:10});

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
	step:5
});
$('#datetimepicker2').datetimepicker({
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
$('#datetimepicker21').datetimepicker({
	yearOffset:0,
	lang:'sp',
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
$('#datetimepicker_dark').datetimepicker({theme:'dark'})
</script>
<script>
	document.getElementById("adelanto").onclick = MayorOIgual;
	function MayorOIgual(){ 
	   	var clave1 = document.getElementById("datetimepicker2").value;
	    var	clave2 = document.getElementById("datetimepicker21").value;
	   	if((clave2!=="")&&(clave1!=="")){
		   	if ( Date.parse(clave1) >=  Date.parse(clave2)){
			 document.getElementById("datetimepicker21").value = "";
		      alert("La segunda fecha puede estar en blanco, o ser mayor que la primera fecha");
		      returnToPreviousPage();
			} 
		}
	} 
</script>
<script>
	$(document).ready(function() {
    var masked = $('.campania');
		$.each(masked, function() {
			var idx = $(this).index();
			$.each($('tr'), function() {
		        $(this).find('th').eq(idx).hide();
		        $(this).find('td').eq(idx).hide();
		        });
		});
		var masked = $('.reserva');
		$.each(masked, function() {
			var idx = $(this).index();
			$.each($('tr'), function() {
		        $(this).find('th').eq(idx).show();
		        $(this).find('td').eq(idx).show();
		        });
		});
	});
	function CambiarConfiguracionCampania(){
		var masked = $('.campania');
		$.each(masked, function() {
			var idx = $(this).index();
			$.each($('tr'), function() {
		        $(this).find('th').eq(idx).hide();
		        $(this).find('td').eq(idx).hide();
		        });
		});
		var masked = $('.reserva');
		$.each(masked, function() {
			var idx = $(this).index();
			$.each($('tr'), function() {
		        $(this).find('th').eq(idx).show();
		        $(this).find('td').eq(idx).show();
		        });
		});
	}
	function CambiarConfiguracionReserva(){
		var masked = $('.reserva');
		$.each(masked, function() {
			var idx = $(this).index();
			$.each($('tr'), function() {
		        $(this).find('th').eq(idx).hide();
		        $(this).find('td').eq(idx).hide();
		        });
		});
		var masked = $('.campania');
		$.each(masked, function() {
			var idx = $(this).index();
			$.each($('tr'), function() {
		        $(this).find('th').eq(idx).show();
		        $(this).find('td').eq(idx).show();
		        });
		});
	}
</script>

<script src="assets/PropiedadEstudio.js" type="text/javascript"></script>
</body>
</html>