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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title></title>

<link href="assets/jquery-ui.css" rel="stylesheet">

<style>
	body{
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

.input{	
}
.input-wide{
	width: 500px;
}

</style>

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
		usuario =  (Trabajador)sesion.getAttribute("usuario");
				
		//Obtención de mensaje del mail 
		String mensaje = (String)(request.getAttribute("mensaje"));
		if(sesion == null || usuario == null){  %>
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

<br><br>

<!--FORMULARIO-->
<br><br>
<div id="home"  class="container spacer about">
<h2 class="text-center wowload fadeInUp">Modificar mensaje de pie de mails</h2> 
	<form name="formcliente" class="form-horizontal wowload fadeInUp" action="ServletLogin?opcion=CambiarMensajePieMail" method="post">
	<fieldset>

<!-- Form Name -->

<!-- Text input-->

<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({
  selector: 'textarea',  // change this value according to your HTML
  spellchecker_language: 'es'
});</script>
   

<div class="form-group">
  <label class="col-md-4 control-label" for="36_Descripcion">Descripción</label>
  <div class="col-md-8">
    <textarea name="36_Descripcion" id="36_Descripcion"><%=mensaje%></textarea>
  </div>
</div>


<div class="form-group">
	<label class="col-md-4 control-label" for="espaciado"></label>
	<div class="col-md-4">
		<button id="adelanto" name="boton" class="btn btn-success" onclick='return(verif(this.form)); MM_validateForm()'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Continuar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
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
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <!-- The modal dialog, which will be used to wrap the lightbox content -->    
</div>
	
<script src="assets/jquery.js"></script>

<script src="assets/jquery-ui.js"></script>

<script>
  $(function() {
    $( "#dialog" ).dialog();
  });
</script>

<!-- jquery -->
<!--<script src="assets/jquery.js"></script>-->

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
	function mostrarOcultar(obj) {
		document.getElementById('17_Cant_Fotos_CD').style.visibility = (obj.checked) ? 'visible' : 'hidden';
	}
</script>

<script>/*
window.onerror = function(errorMsg) {
	$('#console').html($('#console').html()+'<br>'+errorMsg)
}*/

$.datetimepicker.setLocale('en');

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
	lang:'sp',
	timepicker:false,
	format:'Y/m/d',
	formatDate:'Y/m/d',
});
$('#datetimepicker21').datetimepicker({
	yearOffset:0,
	lang:'sp',
	timepicker:false,
	format:'Y/m/d',
	formatDate:'Y/m/d',
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