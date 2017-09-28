<%@page import="java.util.Iterator"%>
<%@page import="modelo.*"%>
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
<title>Genesis Estudio</title>

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
 
<div class="modal" id="correomasivo"> <!-- Mision-->

	-<form class="form-horizontal wowload fadeInUp" id="Reserva"
		action="ServletReserva?opcion=RecordatorioMasivo" method="post">
		
	<div class="modal-dialog wowload flipInX">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<br>				
				<h4 class="modal-title bounceInUp">Enviar recordatorio masivo</h4>
			</div>
			
			<div class="modal-body bounceInUp">
							
			<div class="form-group wowload fadeInUp">
			  <label class="col-md-4 control-label" for="rango">Fecha <i class="fa fa-calendar fa-1x"></i></label>
			  <div class="col-md-8">
			  	<input class="form-control" type="text" id="datetimepicker211" autocomplete="off" placeholder="2017/07/30" name="Fecha"/><br>
			  	</div>
			</div>
								
			<div class="form-group">
				<label class="col-md-4 control-label" for="16_Validado">¿A quién se envía?</label>
				<div class="col-md-8" id="CampoPreReserva" >
					<input id="16_Pre_Reserva"   value="1"  name="PreReservas" type="checkbox" > Pre reservas<br>
					<input id="NO16_Pre_Reserva" value="1" checked name="Reservas" type="checkbox" > Reservas
				</div>
			</div>
						
			<div class="modal-footer bounceInUp">
				<label class="col-md-12 control-label" for="espaciado"></label>
				<div class="col-md-12">
					<button id="continuar" name="boton" class="btn btn-success" onclick="return FechaNoEnBlanco()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Continuar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
					<!-- <button id="continuar" name="boton" class="btn btn-success" onclick="return FechaNoEnBlanco(); return confirm('¿Estás seguro que deseas enviar un correo de recordatorio a todas las reservas del dia \'+document.getElementById(\\'datetimepicker211\\').value+ \'?')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Continuar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
				 -->
				</div>
			</div>				
			</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
		</form>
</div><!-- /.modal -->


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
				 <li onClick="redirect('ServletLogin?opcion=IndexTrabajadorAdmin')"><a href="">Menú</a></li>
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
  <h2>Enviar recordatorio de sesiones</h2>
		  	<form action="ServletReserva?opcion=FiltroFechasRecordatorio" method="post">
			<div class="form-group wowload fadeInUp">
			  <label class="col-md-2 control-label" for="rango">Buscar en rango de fechas <i class="fa fa-calendar fa-1x"></i></label>
			  <div class="col-md-4">
			  	<input class="form-control" type="text" id="datetimepicker2" autocomplete="off" placeholder="2016/01/30" name="Inicio"/><br>
			  	<input class="form-control" type="text" id="datetimepicker21" autocomplete="off" placeholder="2016/01/31" name="Fin" /><br>
			  	<button type="submit" id="adelanto" name="boton" onClick="MayorOIgual()" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
			  </div>
			</div>
			</form>
			<form action="ServletReserva?opcion=FiltroClienteRecordatorio" method="post">
				<div class="form-group wowload fadeInUp">
				  <label class="col-md-2 control-label" for="rango">Buscar por nombre de cliente <i class="fa fa-user fa-1x"></i></label>
				  <div class="col-md-4">
				  	<input class="form-control" type="text" id="15_Nombre" autocomplete="off" placeholder="Luis" name="15_Nombre"/><br>
				  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
				  </div>
				</div>
				</form>
				<br><br><br><br><br><br>
				<form action="ServletReserva?opcion=FiltroClienteRecordatorio" method="post">
				<div class="form-group wowload fadeInUp">
				  <label class="col-md-2 control-label" for="rango">Buscar por apellido de cliente <i class="fa fa-user fa-1x"></i></label>
				  <div class="col-md-4">
				  	<input class="form-control" type="text" id="15_Apellido_Pat" autocomplete="off" placeholder="Luis" name="15_Apellido_Pat"/><br>
				  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
				  </div>
				</div>
				</form>
				
				<br><br><br><br><br>
				
				<hr>
				
				<form action="ServletLogin?opcion=VisualizarPreReservasRecordatorio" method="post">
				<div class="form-group wowload fadeInUp">
				  <div class="col-md-6">
				  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Mostrar sólo pre reservas <i class="fa fa-search fa-1x"></i></button>
				  </div>
				</div>
				</form>
				
				
				<div class="form-group wowload fadeInUp">
				  <div class="col-md-6">
				  	<a href="#correomasivo" class="btn btn-warning btn-block" data-toggle="modal"><strong>Enviar correo masivo</strong></a>
				  </div>
				</div>
  		<%
				//Obtención de reservas para tabla de visualizacion de reservas 
				ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)(request.getAttribute("reservas"));
				Iterator<ArrayList<Object>> iterres1 = reservas.iterator();
				if(!iterres1.hasNext()){  %>
						<h3>No existen datos para mostrar </h3>
						</div>
	<%			}else{	%>
				  <div class="col-md-6 wowload fadeInUp">
					<h4>Resultado(s): <%=reservas.size()%></h4><br>
					<div class = "col-md-4"></div>
					<a href="ServletLogin?opcion=MirarCalendario" method="post">
						<button type="submit" class="btn btn-success btn-sm" name="Calendario" title="Mirar en un calendario todas las reservas" value="Calendario" >
                			<i class="fa fa-calendar fa-1x"></i> Ver reservas en calendario
            			</button>
            		</a>
            	  </div>
            		<table class="table table-hover wowload fadeInUp">
					    <thead>
						  <tr style="font-size:13px;">
							<th><h5>Fecha<br>Año/Mes/Día</h5></th>
							<th style="white-space: nowrap"><h5>Cliente</h5></th>
							<th><h5>Campaña</h5></th>
							<th><h5>Personas</h5></th>
							<th><h5>Personas<br>Adicionales</h5></th>
							<th><h5>Veces<br>Reagendar</h5></th>
							<th><h5>Monto Anticipo</h5></th>
							<th><h5>Código<br>Cupón</h5></th>
							<th><h5>¿Validado?</h5></th>
							<th><h5>¿Agendador?</h5></th>
							<th><h5>Enviar recordatorio</h5></th>
						  </tr>
						</thead>
						<tbody>
	<%				while(iterres1.hasNext())
					{
						ArrayList<Object> nueva = iterres1.next();
						Reserva res = (Reserva)nueva.get(0); 
						String fecha = null;
						if(res.getFecha()!=null){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
							fecha = sdf.format(res.getFecha());
						}else{
							fecha = "Pendiente";
						}	
						int reservaid = res.getId_Reserva();
						int rut = Integer.parseInt((nueva.get(1).toString()));
						String nombre = (String) nueva.get(2)+" "+(String)nueva.get(3)+" "+(String)nueva.get(4);
						String campaniamostrar = res.getId_Campania()+" - "+(String)nueva.get(5);
						int personas;
						%>
						<tr>
							<td><h5><%=fecha%></h5></td>
							<td><h5><%=nombre%></h5></td>
							<td><h5><%=campaniamostrar%></h5></td>
							<td><h5><%=res.getCantidad_Personas()%></h5></td>
							<td><h5><%=res.getCantidad_Adicionales()%></h5></td>
							<td><h5><%=res.getCantidad_Reagendamiento() %></h5></td>
							<td><h5><%=(res.getMonto_Pago_Adelantado()==-1)?"N/A":res.getMonto_Pago_Adelantado()%></h5></td>
							<td><h5><%=((res.getCodigo()).equals("null"))?"N/A":res.getCodigo()%></h5></td>
							<td><h5><%=(res.isValidado())?"SI":"NO"%></h5></td>
							<td><h5><%=res.getId_Trabajador()%>-<%=(String)nueva.get(6)%></h5></td>
							<form action="ServletReserva?opcion=CambiarReserva" method="post">
									<input type="hidden" value="<%=res.getId_Reserva()%>" name="16_Id_Reserva">
							<td>	 
							<div class="btn-group  btn-group-lg">
									 <button type="submit" class="btn btn-success" name="RecordarReserva" value="RecordarReserva" >
                						<i class="fa fa-flag fa-1x"></i>
            						 </button>
            				</div>
            				</td>
							</form>		
						</tr>
	<% 				} %>
			    </tbody>
			  </table>
			</div>
	<%  }
	}	%>	


</body>

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

<script type="text/javascript">
	function redirect(URL){
		window.location= '/Prueba/'+ URL;
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
	timepicker: false
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
	timepicker: false
});
$('#datetimepicker211').datetimepicker({
	yearOffset:0,
	lang:'sp',
	timepicker:false,
	format:'Y/m/d',
	formatDate:'Y/m/d',
	dayOfWeekStart : 1,
	disabledWeekDays: [0],
	dayOfWeekShort: ["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"],
	timepicker: false
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
	document.getElementById("continuar").onclick = FechaNoEnBlanco;
	function FechaNoEnBlanco(){ 
	   	var clave1 = document.getElementById("datetimepicker211").value;
	   	  	   	
	   	if((clave1=="") && (clave1.length != 10) && !(Date.parse(clave1))){
			 document.getElementById("datetimepicker211").value = "";
		      alert("¡La fecha no puede quedar en blanco!");
		      document.getElementById("datetimepicker211").focus();	      
		}
	} 
</script>
<script type="text/javascript">
	function redirect(URL){
		window.location= '/GenesisEstudio/'+ URL;
	}
</script>

</body>
</html>

</body>
