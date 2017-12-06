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

<script type="text/javascript" src="assets/tablesorter/jquery-latest.js"></script> 
<script type="text/javascript" src="assets/tablesorter/jquery.tablesorter.js"></script> 

<!-- Calendario -->
<link rel="stylesheet" type="text/css" href="assets/bootstrap/css/jquery.datetimepicker.css"/>

<!-- Fin Calendario -->
</head>

	<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario = (Trabajador)(sesion.getAttribute("usuario"));
				
		//Obtención de canales para combobox para el formulario 
		ArrayList<ArrayList<String>> campañas = (ArrayList<ArrayList<String>>)(request.getAttribute("campanias"));
				
		ArrayList<Canal_Venta> canalesventas = (ArrayList<Canal_Venta>)(request.getAttribute("canalesventas"));
		
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


<div class="container spacer">
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
  		<div><h2>Estadísticas de campañas</h2></div>
  		<form action="ServletCampania?opcion=FiltroEstadistica" method="post">
			<div class="form-group">
			  <label class="col-md-2 control-label" for="rango">Buscar en rango de fechas <i class="fa fa-calendar fa-1x"></i></label>
			  <div class="col-md-2">
			  	<input class="form-control" type="text" id="datetimepicker2" autocomplete="off" placeholder="2018/01/30" name="Inicio"/><br>
			  	<input class="form-control" type="text" id="datetimepicker21" autocomplete="off" placeholder="2018/01/31" name="Fin" /><br>
			  </div>
			 </div>
			 <div class="form-group">
			  <label class="col-md-2 control-label" for="rango">Buscar por nombre de campaña <i class="fa fa-user fa-1x"></i></label>
			  <div class="col-md-2">
			  	<input class="form-control" type="text" id="17_Nombre" autocomplete="off" placeholder="Maternal" name="17_Nombre"/><br>
			  </div>
			 </div>
			 <div class="form-group">
			  <label class="col-md-2 control-label" for="rango">Buscar por precio <i class="fa fa-money fa-1x"></i></label>
			  <div class="col-md-2">
			  	<input class="form-control" type="text" id="17_Precio" autocomplete="off" placeholder="15990" name="17_Precio"/><br>
			  </div>
			 </div>
			 <div class="form-group">
			  <label class="col-md-2 control-label" for="rango">Buscar por canal de venta <i class="fa fa-user fa-1x"></i></label>
			  <div class="col-md-2">
			  	<select class="form-control"  name="14_Id_Canal_Venta" id="14_Id_Canal_Venta">
					<option value="0"></option>
					<%
							Iterator<Canal_Venta> it = canalesventas.iterator();
							while(it.hasNext())
							{
								Canal_Venta c = (Canal_Venta)it.next();
					%>
							<option value="<%=c.getId_Canal_Venta()%>"><%=c.getCanal()%></option>
					<% 		}     %>
				</select>
				</div>
			</div>
			<button type="submit" id="adelanto" name="boton" onClick="MayorOIgual()" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
			  
			</form>
   <%
				//Obtención de reservas para tabla de visualizacion de reservas 
				ArrayList<ArrayList<String>> campanias = (ArrayList<ArrayList<String>>)(request.getAttribute("campanias"));
				Iterator<ArrayList<String>> iterres1 = campanias.iterator();
				if(!iterres1.hasNext()){  %>
						<h3>No existen datos para mostrar </h3>
	<%			}else{	%>
				<hr>
					<table id="tabladedatos" class="tablesorter table-hover table">
					    <thead>
						  <tr style="font-size:13px;">
						  	<th></th>
							<th><h5>Total<br>Agendada</h5></th>
							<th><h5>Id Campaña</h5></th>
							<th><h5>Campaña</h5></th>
							<th><h5>Precio</h5></th>
							<th><h5>Canal de Venta</h5></th>
						  </tr>
						</thead>
						<tbody>
	<%				while(iterres1.hasNext())
					{
						ArrayList<String> nueva = iterres1.next();
	%>
						<tr>
							<td><h5><i class="fa fa-camera fa-1x"></i></h5></td>
							<td><h5><%=nueva.get(2)%></h5></td>
							<td><h5><%=nueva.get(0)%></h5></td>
							<td><h5><%=nueva.get(1)%></h5></td>
							<td><h5><%=nueva.get(5)%></h5></td>
							<td><h5><%=nueva.get(4)%></h5></td>					
						</tr>
	<% 				} %>
			    </tbody>
			  </table>
			
	<%  }
	
	}	%>	</div>


</body>

<!-- Footer Starts -->
<div class="row"></div>
<div class="footer text-center spacer">
	<p>Sistema Estudio. Advancing Group Ltda.</p>
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
	$(document).ready(function() 
    	{ 
        	$("#tabladedatos").tablesorter(); 
    	} 
	);
    
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

<script src="assets/PropiedadEstudio.js" type="text/javascript"></script>

</body>
