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

<script type="text/javascript" src="assets/tablesorter/jquery-latest.js"></script> 
<script type="text/javascript" src="assets/tablesorter/jquery.tablesorter.js"></script> 

</head>

	<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario = (Trabajador)(sesion.getAttribute("usuario"));
				
		//Obtención de canales para combobox para el formulario 
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
  		<div><h2>Campañas</h2></div>
  		<div class="col-lg-6">
  		<form action="ServletLogin?opcion=GenerarNuevoCampania" method="post">	 
				<div class="btn-group  btn-group-lg">
					<button type="submit" class="btn btn-success" name="NuevoCampania" value="Nuevo" title="Crear nueva campaña">
                		<i class="fa fa-plus-square fa-1x"></i> <i class="fa fa-camera fa-2x"></i>
            		</button>
				</div>
		</form>	
		</div>
		<form action="ServletCampania?opcion=FiltroNombre" method="post">
			<div class="form-group wowload fadeInUp">
			  <label class="col-md-2 control-label" for="rango">Buscar por nombre de campaña <i class="fa fa-user fa-1x"></i></label>
			  <div class="col-md-4">
			  	<input class="form-control" type="text" id="17_Nombre" autocomplete="off" placeholder="Maternal" name="17_Nombre"/><br>
			  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
			  </div>
			</div>
			</form>
			<br><br><br><br><br><br>
			<form action="ServletCampania?opcion=FiltroCanalVenta" method="post">
			<div class="form-group wowload fadeInUp">
			  <label class="col-md-2 control-label" for="rango">Buscar por canal de venta<i class="fa fa-user fa-1x"></i></label>
			  <div class="col-md-4">
			  	<select class="form-control"  name="14_Id_Canal_Venta" id="14_Id_Canal_Venta">
					<option value="1"></option>
					<%
							Iterator<Canal_Venta> it = canalesventas.iterator();
							while(it.hasNext())
							{
								Canal_Venta c = (Canal_Venta)it.next();
					%>
							<option value="<%=c.getId_Canal_Venta()%>"><%=c.getCanal()%></option>
					<% 		}     %>
				</select>	
				<br>
				<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
			  </div>
			</div>
			</form>
   <%
				//Obtención de reservas para tabla de visualizacion de reservas 
				ArrayList<ArrayList<Object>> campanias = (ArrayList<ArrayList<Object>>)(request.getAttribute("campanias"));
				Iterator<ArrayList<Object>> iterres1 = campanias.iterator();
				if(!iterres1.hasNext()){  %>
						<h3>No existen datos para mostrar </h3>
	<%			}else{	%>
				<div class="col-lg-6">
				<h4>Resultado(s): <%=campanias.size() %> Campaña(s)</h4>
				</div>
				<hr>
					<table id="tabladedatos" class="tablesorter table-hover table">
					    <thead>
						  <tr style="font-size:13px;">
						  	<th></th>
							<th><h5>ID</h5></th>
							<th><h5>Nombre</h5></th>
							<th><h5>Canal de Venta</h5></th>
							<th><h5>Inicio y Fin<br>de Vigencia</h5></th>
							<th><h5>Precio</h5></th>
							<th><h5>Máx.<br>Pers.*</h5></th>
							<th><h5>CD?/<br>Cant.<br>Fotos</h5></th>
							<th><h5>Cant<br>Fotos</h5></th>
							<th><h5>Precio<br>Persona<br>Adicional</h5></th>
							<th><h5>Precio<br>por<br>Reagendar</h5></th>
							<th><h5>Modificar</h5></th>
							<th><h5>Eliminar</h5></th>
						  </tr>
						</thead>
						<tbody>
	<%				while(iterres1.hasNext())
					{
						ArrayList<Object> nueva = iterres1.next();
						Campania camp = (Campania)nueva.get(0); 
						String fecha1 = null;
						String fecha2 = null;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
						fecha1 = sdf.format(camp.getInicio_Vigencia());
						fecha2 = sdf.format(camp.getFin_Vigencia());
	%>
						<tr>
							<td><h5><i class="fa fa-camera fa-1x"></i></h5></td>
							<td><h5><%=camp.getId_Campania()%></h5></td>
							<td><h5><%=camp.getNombre()%></h5></td>
							<td><h5><%=camp.getId_Canal_Venta()%>-<%=nueva.get(1)%></h5></td>
							<td><h5>Inicio:&nbsp;<%=fecha1%><br>Fin:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=fecha2%></h5></td>
							<td><h5><%=camp.getPrecio()%></h5></td>
							<td><h5><%=camp.getMaximo_Personas()%></h5></td>
							<td><h5><%=(camp.isCD())?"Sí":"No"%>-<%=camp.getCant_Fotos_CD()%></h5></td>
							<td><h5>10x15:&nbsp;<%=camp.getCant_10x15()%><br>15x21:&nbsp;<%=camp.getCant_15x21()%><br>20x30:&nbsp;<%=camp.getCant_20x30()%><br>30x40:&nbsp;<%=camp.getCant_30x40()%></h5></td>
							<td><h5><%=camp.getPrecio_Adicional() %></h5></td>	
							<td><h5><%=camp.getPrecio_Reagendamiento() %></h5></td>	
							<form action="ServletCampania?opcion=CambiarCampania" method="post">
									<input type="hidden" value="<%=camp.getId_Campania()%>" name="17_Id_Campania">
							<td>	 
								<div class="btn-group  btn-group-lg">
									 <button type="submit" class="btn btn-success" name="ModificarCampania" value="Modificar" >
                						<i class="fa fa-pencil-square-o fa-1x"></i>
            						 </button>
            					</div>
            				</td>
            				<td>
            					<div class="btn-group  btn-group-lg">
								     <button type="submit" class="btn btn-danger" name="EliminarCampania" value="Eliminar" onclick="return confirm('¿Estás seguro que deseas eliminar esta campaña?')">
                						<i class="fa fa-trash fa-1x"></i>
            						 </button>
            					</div>
            				</td>
							</form>		
						</tr>
	<% 				} %>
			    </tbody>
			  </table>
			  * Máximo de personas que permite la campaña
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

<script type="text/javascript">
	$(document).ready(function() 
    	{ 
        	$("#tabladedatos").tablesorter(); 
    	} 
	);
    
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
