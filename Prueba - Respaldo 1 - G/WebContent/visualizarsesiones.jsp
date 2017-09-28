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


<div class="container-fluid spacer wowload fadeInUp">
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
  <h2>Envío de mails por evento</h2>
  <form action="ServletSesion?opcion=FiltroClienteSesiones" method="post">
	<div class="form-group wowload fadeInUp">
	  <label class="col-md-2 control-label" for="rango">Buscar por nombre de cliente <i class="fa fa-user fa-1x"></i></label>
	  <div class="col-md-4">
	  	<input class="form-control" type="text" id="15_Nombre" autocomplete="off" placeholder="Luis" name="15_Nombre"/><br>
	  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
	  </div>
	</div>
	</form>
	<br><br><br><br><br><br>
	<form action="ServletSesion?opcion=FiltroClienteSesiones" method="post">
	<div class="form-group wowload fadeInUp">
	  <label class="col-md-2 control-label" for="rango">Buscar por apellido de cliente <i class="fa fa-user fa-1x"></i></label>
	  <div class="col-md-4">
	  	<input class="form-control" type="text" id="15_Apellido_Pat" autocomplete="off" placeholder="Luis" name="15_Apellido_Pat"/><br>
	  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
	  </div>
	</div>
	</form>
	<%
				//Obtención de reservas para tabla de visualizacion de reservas 
				ArrayList<ArrayList<Object>> sesiones = (ArrayList<ArrayList<Object>>)(request.getAttribute("sesiones"));
				Iterator<ArrayList<Object>> iterres1 = sesiones.iterator();
				if(!iterres1.hasNext()){  %>
						<h3>No existen datos para mostrar </h3>
						</div>
	<%			}else{	%>
				  <div class="col-md-6 wowload fadeInUp">
					<h4>Resultado(s): <%=sesiones.size()%></h4><br>
					<div class = "col-md-4"></div>
            	  </div>
            		<table class="table table-hover wowload fadeInUp">
					    <thead>
						  <tr style="font-size:13px;">
							<th><h5>Fecha<br>sesión<br>(Día/Mes/Año)</h5></th>
							<th><h5>Mail/Nombre</h5></th>
							<th><h5>Monto<br>extras</h5></th>
							<th><h5>Fotógrafo</h5></th>
							<th><h5>Numero<br>ticket</h5></th>
							<th><h5>Fecha<br>entrega<br>de fotos</h5></th>
							<th><h5>Fecha<br>envio<br>imprimir</h5></th>
							<th><h5>Numero<br>factura</h5></th>
							<th><h5>Fecha<br>Retiro</h5></th>
							<th><h5>Retirado<br>por</h5></th>
							<th><h5>¿Asistió?</h5></th>
							<th><h5>¿Fotos<br>seleccionadas?</h5></th>
							<th><h5>¿Fotos<br>listas<br>para<br>entregar?</h5></th>
							<th><h5>¿Entregadas?</h5></th>
						  </tr>
						</thead>
						<tbody>
	<%				while(iterres1.hasNext())
					{
						ArrayList<Object> nueva = iterres1.next();
						SesionAuxiliar sa = (SesionAuxiliar)nueva.get(0); 
												
						String fechaentrega = null;
						String fechaenvioimprimir = null;
						String fecharetiro = null;
						String fechasesion = null;
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
						%>
						<tr>
							<td><h5><%=(sa.getFecha_Sesion()!=null)?sdf.format(sa.getFecha_Sesion()):"N/A"%></h5></td>
							<td><h5><%=(((String)(nueva.get(2))).equals("null"))?"":(String)nueva.get(2)%>/<br><br>
									<%=(((String)(nueva.get(3))).equals("null"))?"N/A":(String)nueva.get(3)%> <%=(((String)(nueva.get(4))).equals("null"))?"":(String)nueva.get(4)%></h5></td>
							<td><h5><%=sa.getMonto_Extras()%></h5></td>
							<td><h5><%=sa.getFotografo()%></h5></td>
							<td><h5><%=sa.getNumero_Ticket()%></h5></td>
							<td><h5><%=(sa.getFecha_Entrega()!=null)?sdf.format(sa.getFecha_Entrega()):"N/A" %></h5></td>
							<td><h5><%=(sa.getFecha_Envio_Imprimir()!=null)?sdf.format(sa.getFecha_Envio_Imprimir()):"N/A"%></h5></td>
							<td><h5><%=sa.getNumero_Factura() %></h5></td>
							<td><h5><%=(sa.getFecha_Retiro()!=null)?sdf.format(sa.getFecha_Retiro()):"N/A"%></h5></td>
							<td><h5><%=sa.getNombre_Retira() %></h5></td>
							<td><h5><%=(sa.isAsistio())?"SI":"NO" %></h5></td>
							<form action="ServletSesion?opcion=EnviarMail" method="post">
									<input type="hidden" value="<%=sa.getId_Reserva_Asoc() %>" name="16_Id_Reserva">
									<input type="hidden" value="<%=(((String)(nueva.get(2))).equals("null"))?"":(String)nueva.get(2) %>" name="15_Mail">
									<input type="hidden" value="<%=(((String)(nueva.get(3))).equals("null"))?"N/A":(String)nueva.get(3)%> <%=(((String)(nueva.get(4))).equals("null"))?"":(String)nueva.get(4) %>" name="Nombre">
									<input type="hidden" value="<%=(sa.getFecha_Sesion()!=null)?sdf.format(sa.getFecha_Sesion()):""%>" name="fechasesion">
									<input type="hidden" value="<%=(sa.getFecha_Retiro()!=null)?sdf.format(sa.getFecha_Retiro()):""%>" name="fecharetiro">
									<input type="hidden" value="<%=sa.getNombre_Retira()%>" name="nombreretiro">
							<td><h5>
								<center><%=(sa.isFotos_Seleccionadas())?"SI":"NO" %></center><br>
							<% if(!sa.isFotos_Seleccionadas()){ %>
								<button type="submit" class="btn btn-info btn-sm" name="NoSeleccionadas" value="NoSeleccionadas" onclick="return confirm('¿Estás seguro que deseas enviar un mail de aviso de fotografías no seleccionadas?')">
                						<i class="fa fa-envelope-o fa-2x"></i>
            					</button>
							<% }else{ %>
								<button style="background-color:grey;" disabled type="submit" class="btn btn-info btn-sm" name="NoSeleccionadas" value="NoSeleccionadas" onclick="return confirm('¿Estás seguro que deseas enviar un mail de aviso de fotografías seleccionadas?')">
                						<i class="fa fa-envelope-o fa-2x"></i>
            					</button>
							<% } %>
							</h5></td>
							<td><h5>
								<center><%=(sa.isLista_Para_Entregar())?"SI":"NO" %></center><br>
							<% if(sa.isFotos_Seleccionadas() && sa.isLista_Para_Entregar() && !sa.isEntregadas()){ %>
								 <button type="submit" class="btn btn-info btn-sm" name="Listas" value="Listas" onclick="return confirm('¿Estás seguro que deseas enviar un mail de confirmación de fotografías listas para entregar?')">
                						<i class="fa fa-envelope-o fa-2x"></i>
            					 </button>
							<% }else{ %>
								 <button style="background-color:grey;" disabled type="submit" class="btn btn-info btn-sm" name="Listas" value="Listas" onclick="return confirm('¿Estás seguro que deseas enviar un mail de confirmación de fotografías listas para entregar?')">
                						<i class="fa fa-envelope-o fa-2x"></i>
            					 </button>
							<% } %>
							</h5></td>
							<td><h5>
								<center><%=(sa.isEntregadas())?"SI":"NO" %></center><br>
							<% if(sa.isFotos_Seleccionadas() && sa.isLista_Para_Entregar() && sa.isEntregadas()){ %>
								 <button type="submit" class="btn btn-info btn-sm" name="Entregadas" value="Entregadas" onclick="return confirm('¿Estás seguro que deseas enviar un mail de confirmación de entrega?')">
                						<i class="fa fa-envelope-o fa-2x"></i>
            					 </button>
							<% }else{ %>
								 <button style="background-color:grey;" disabled type="submit" class="btn btn-info btn-sm" name="Entregadas" value="Entregadas" onclick="return confirm('¿Estás seguro que deseas enviar un mail de confirmación de entrega?')">
                						<i class="fa fa-envelope-o fa-2x"></i>
            					 </button>
							<% } %>
							</h5></td>
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
		window.location= '/GenesisEstudio/'+ URL;
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
</body>
</html>