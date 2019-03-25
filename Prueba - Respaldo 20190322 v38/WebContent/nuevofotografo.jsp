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
<h2 class="text-center">Nuevo Fotógrafo</h2> 
	<form name="formcliente" class="form-horizontal " action="ServletTrabajador?opcion=NuevoTrabajador" method="post">
	<fieldset>

<!-- Form Name -->

<div class="form-group">
  <label class="col-md-4 control-label" for="04_Nombre">Nombre *</label>
  <div class="col-md-4">
  	<input class="form-control" type="text" value="" name="04_Nombre" maxlength="30" id="04_Nombre" placeholder="" required/>
  </div>
</div>


<!-- CheckBox input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="16_Cantidad_Personas">¿Activo?</label>  
  <div class="col-md-4">
  	<input id="04_EsAdmin" name="04_EsAdmin" type="checkbox" onClick="mostrarOcultar(this)">
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
	<p>Sistema Estudio. Advancing Group Ltda.</a></p>
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
		var r = document.getElementById('04_Rut_Trabajador').value;
		var d = r.slice(-1);
		if(r.length==8){ r = '0' + r;}
			if(r.length==9){
				if(ValidaRut(document.getElementById('04_Rut_Trabajador').value)){
					return ;
				}else{
					alert('El rut ingresado no es valido.');
					document.getElementById('04_Rut_Trabajador').value = '';
				}
			}else{
				alert('Ingrese el rut sin puntos ni guión.');
				document.getElementById('04_Rut_Trabajador').value = '';
			}
	}
</script>

<script>
	function comprobarClave(){ 
	   	clave1 =  document.getElementById("04_Contrasenia").value;
	   	clave2 =  document.getElementById("R04_Contrasenia").value;
		
		if((clave1.equals(""))&&(clave2.equals(""))){
			return ;
		}
		
	   	if (clave1 != clave2){
		  document.getElementById("R04_Contrasenia").value = "";
	      alert("Las contraseñas no coinciden, intente nuevamente");
		} 
	}
</script>

<script src="assets/PropiedadEstudio.js" type="text/javascript"></script>
</body>
