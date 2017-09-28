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
		
		//Obtención de comunas para combobox para el formulario de reservas
		ArrayList<Comuna> comunas = (ArrayList<Comuna>)(request.getAttribute("comunas"));
		// Obtención de ciudades para combobox para el formulario de reservas
		ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)(request.getAttribute("ciudades"));
		// Obtención de ciudades para combobox para el formulario de reservas
		ArrayList<String> mails = (ArrayList<String>)(request.getAttribute("mails"));
		
		if(sesion == null || usuario == null || ciudades==null){ %>
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
<h2 class="text-center wowload fadeInUp">Modificar cliente</h2> 
	<form name="formcliente" class="form-horizontal wowload fadeInUp" action="ServletCliente?opcion=ModificarCliente" method="post">
	<fieldset>
	<input type="hidden" value="<%=cli.getId_Cliente()%>" name="15_Id_Cliente">

<!-- Form Name -->

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="15_Mail">Mail *</label>
  <div class="col-md-4">
  	<input class="form-control" onChange="MailRepetido()" type="text" value="<%=cli.getMail()%>" name="15_Mail" maxlength="40" id="15_Mail" placeholder="maildeejemplo@ejemplo.com"/>
  	<br>
  	<p id="demodanger" class="alert alert-danger"
						style="display: none;" role="alert"></p>
  </div>
</div>

<div class="form-group">
	<label class="col-md-4 control-label" for="rutdecliente">R.U.T.<br><font size=2>Sin puntos ni guión(Reemplazar k por 0)</font></label>
	<div class="col-md-4">
		<input class="form-control" onChange="prueba_rut()" value="<%=cli.getRut() %>" type="number" maxlength="9" name="15_Rut_Cliente" id="15_Rut_Cliente" placeholder="123456780"/>
	</div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label" for="15_Nombre">Nombre *</label>
  <div class="col-md-4">
  	<input class="form-control" type="text" value="<%=cli.getNombre() %>" name="15_Nombre" maxlength="30" id="15_Nombre" placeholder="" required/>
  </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label" for="15_Apellido_Pat">Apellido Paterno *</label>
  <div class="col-md-4">
  	<input class="form-control" type="text" value="<%=cli.getApellido_Pat() %>" name="15_Apellido_Pat" maxlength="15" id="15_Apellido_Pat" placeholder="" required=""/>
  </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label" for="15_Apellido_Mat">Apellido Materno</label>
  <div class="col-md-4">
  	<input class="form-control" type="text" value="<%=cli.getApellido_Mat() %>" name="15_Apellido_Mat" maxlength="15" id="15_Apellido_Mat" placeholder=""/>
  </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label" for="15_Telefono">Teléfono *</label>
  <div class="col-md-4">
  	<input class="form-control" type="number" value="<%=cli.getFono()%>" name="15_Telefono" maxlength="20" id="15_Telefono" placeholder=""/>
  </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label" for="15_Celular">Celular *</label>
  <div class="col-md-4">
  	<input class="form-control" type="number" value="<%=cli.getCelular()%>" name="15_Celular" maxlength="20" id="15_Celular" placeholder=""/>
  </div>
</div>

<div class="form-group" style="display:none;">
  <label class="col-md-4 control-label" for="Contrasenia">Contraseña</label>
  <div class="col-md-4">
  	<input class="form-control" type="password" value="<%=cli.getConstrasenia() %>" name="15_Contrasenia" maxlength="30" id="15_Contrasenia" placeholder=""/>
  </div>
</div>

<div class="form-group" style="display:none;">
  <label class="col-md-4 control-label" for="Contrasenia">Repetir Contraseña</label>
  <div class="col-md-4">
  	<input class="form-control" type="password" value="<%=cli.getConstrasenia()%>" name="R15_Contrasenia" maxlength="30" id="R15_Contrasenia" placeholder="" onChange="comprobarClave()"/>
  </div>
</div>

<div class="form-group">
  <label class="col-md-4 control-label" for="15_Direccion">Dirección</label>
  <div class="col-md-4">
  	<input class="form-control" type="text" value="<%=cli.getDireccion() %>" name="15_Direccion" maxlength="30" id="15_Dirección" placeholder="Casa #11, CalleDeEjemplo"/>
  </div>
</div>

<!-- Select Basic -->
<div class="form-group">
  <label class="col-md-4 control-label" for="ciudadlabel">Comuna / Ciudad</label>
  <div class="col-md-4">
	<select name="06_Id_Ciudad" id="06_Id_Ciudad" class="form-control" required="">
		<option value="1"></option>
		<%
				Iterator<Comuna> it = comunas.iterator();
				while(it.hasNext())
				{
					Comuna c = (Comuna)it.next();
					if(cli.getId_Comuna()!=c.getId_Comuna()){
					%>
						<option value="<%=c.getId_Comuna()%>"><%=c.getComuna()%> / <%=(ciudades.get(c.getId_Ciudad_Asoc()-1)).getCiudad()%></option>
					<% }else{ %>
						<option value="<%=c.getId_Comuna()%>" selected><%=c.getComuna()%> / <%=(ciudades.get(c.getId_Ciudad_Asoc()-1)).getCiudad()%></option>
	<% 				}
				} %>
	</select>
  </div>
</div>




<!-- CheckBox input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="16_Cantidad_Personas">Click para escribir reclamo u comentario</label>  
  <div class="col-md-4">
	<input class="form-control" id="15_Reclamo" name="15_Reclamo" type="checkbox" onClick="mostrarOcultar(this)"> 	
  </div>
</div>

<%
	String obs = "";
	if((cli.getObservacion()!=null)&&(!cli.getObservacion().equals("null"))){
		obs = cli.getObservacion();
	}
 %>
<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="checkbox"></label>  
  <div class="col-md-4">
  	<input class="form-control" value="<%=obs%>" maxlength="50" style="visibility:hidden;" id="15_Observacion" name="15_Observacion" type="text">
  </div>
</div>

<div class="form-group">
	<label class="col-md-4 control-label" for="espaciado"></label>
	<div class="col-md-4">
		<button id="adelanto" name="boton" class="btn btn-success" onclick='return(validarCelularTelefono());return(verif(this.form)); MM_validateForm()'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Continuar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
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
    re=/^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,3})$/
    if(!re.exec(valor))    {
        return false;
    }else{
        return true;
    }
}
</script>


<script type="text/javascript">
	/*$( "#06_Id_Ciudad" ).change(function() {
		alert("Hello! I am an alert box!!");
  		var IfCiudad = document.getElementById('06_Id_Ciudad').value;
  		var todaslascomunas = new Array(346);
  		var largo = ;
  		for (var i=0; i< largo; i++){
	  		todaslascomunas[i] = new Array(2);
  			todaslascomunas[i][1] =;
		alert("Cantidad:"+ totaslascomunas.length);
  	})
	*/
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
	
	function validarCelularTelefono(){
		if((document.getElementById('15_Telefono').value).length > 0 || (document.getElementById('15_Celular').value).length > 0){
			return true;
		}else{
			alert('Ingrese un número celular o un número telefónico para continuar');
			return false;
		}
	}
</script>

</body>
