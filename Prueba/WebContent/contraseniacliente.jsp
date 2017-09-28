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
<title>Genesis Estudio</title>

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
		
		//Obtención de la reserva a modificar
		Cliente cli = (Cliente)(request.getAttribute("cliente"));
		
		if(sesion == null || usuario == null || cli==null){ %>
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
<h2 class="text-center wowload fadeInUp">Modificar contraseña</h2> 
	<form name="formcliente" class="form-horizontal wowload fadeInUp" action="ServletCliente?opcion=ModificarContrasenia" method="post">
	<fieldset>
	<input type="hidden" value="<%=cli.getId_Cliente()%>" name="15_Id_Cliente">

<!-- Form Name -->

<!-- Text input-->

<div class="form-group">
	<label class="col-md-4 control-label" for="contraantigua">Ingrese antigua contrasenia</label>
	<div class="col-md-4">
		<input class="form-control" value="<%=cli.getConstrasenia() %>" type="password" maxlength="30" name="A15_Contrasenia" id="A15_Contrasenia" placeholder="123456780" required/>
	</div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label" for="Contrasenia">Contraseña</label>
  <div class="col-md-4">
  	<input class="form-control" type="password" value="" name="15_Contrasenia" maxlength="30" id="15_Contrasenia" placeholder=""/>
  </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label" for="Contrasenia">Repetir Contraseña</label>
  <div class="col-md-4">
  	<input class="form-control" type="password" value="" name="R15_Contrasenia" maxlength="30" id="R15_Contrasenia" placeholder="" onChange="comprobarClave()"/>
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
		document.getElementById('15_Observacion').style.visibility = (obj.checked) ? 'visible' : 'hidden';
	}
</script>
<script>
	function comprobarClave(){ 
	   	clave1 =  document.getElementById("15_Contrasenia").value;
	   	clave2 =  document.getElementById("R15_Contrasenia").value;
	
	   	if (clave1 != clave2){
		  document.getElementById("R15_Contrasenia").value = "";
	      alert("Las contraseñas no coinciden, intente nuevamente");
		} 
	} 
</script>

<script type="text/javascript">
	function redirect(URL){
		window.location= '/GenesisEstudio/'+ URL;
	}
	function CerrarSesion(){
	<% 
		System.out.println("Sesion Invalidada");
	%>
	}
</script>

</body>
