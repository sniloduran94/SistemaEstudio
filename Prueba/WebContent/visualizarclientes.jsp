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

</head>

	<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario = (Trabajador)(sesion.getAttribute("usuario"));
		//Obtención de comunas para combobox para el formulario de reservas
		ArrayList<Comuna> comunas = (ArrayList<Comuna>)(request.getAttribute("comunas"));
		// Obtención de ciudades para combobox para el formulario de reservas
		ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)(request.getAttribute("ciudades"));
		
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
  		<div><h2>Clientes</h2></div>
  		
  		<div class="row">
  		<div class="col-lg-4 col-md-4">
  		<form action="ServletCliente?opcion=FiltroCliente" method="post">
			<div class="form-group wowload fadeInUp">
			  <label class="col-md-4 control-label" for="rango">Buscar por nombre de cliente <i class="fa fa-user fa-1x"></i></label>
			  <div class="col-md-8">
			  	<input class="form-control" type="text" id="15_Nombre" autocomplete="off" placeholder="Luis" name="15_Nombre"/><br>
			  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
			  </div>
			</div>
			</form>
			</div>
		<div class="col-lg-4 col-md-4">
			<form action="ServletCliente?opcion=FiltroCliente" method="post">
			<div class="form-group wowload fadeInUp">
			  <label class="col-md-4 control-label" for="rango">Buscar por apellido de cliente <i class="fa fa-user fa-1x"></i></label>
			  <div class="col-md-8">
			  	<input class="form-control" type="text" id="15_Apellido_Pat" autocomplete="off" placeholder="Luis" name="15_Apellido_Pat"/><br>
			  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-calendar fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
			  </div>
			</div>
			</form>
  		</div>
  		<div class="col-lg-4 col-md-4">
			<form action="ServletCliente?opcion=FiltroClienteFono" method="post">
			<div class="form-group wowload fadeInUp">
			  <label class="col-md-4 control-label" for="rango">Buscar por teléfono o celular <i class="fa fa-phone fa-1x"></i></label>
			  <div class="col-md-8">
			  	<input class="form-control" type="Number" id="15_FoT" autocomplete="off" placeholder="1234567" name="15_FoT"/><br>
			  	<button type="submit" id="adelanto" name="boton" class="btn btn-success btn-sm btn-block"><i class="fa fa-phone fa-1x"></i> Buscar <i class="fa fa-search fa-1x"></i></button>
			  </div>
			</div>
			</form>
  		</div>
  		<div class="col-lg-6 col-md-6">
  		<form action="ServletLogin?opcion=GenerarNuevoCliente" method="post">	 
				<div class="btn-group  btn-group-lg">
					<button type="submit" class="btn btn-success" name="NuevoCliente" value="Nuevo" title="Crear nuevo cliente">
                		<i class="fa fa-plus-square fa-1x"></i> <i class="fa fa-user fa-2x"></i>
            		</button>
				</div>
		</form>	
		</div>
		
		</div> <br><br>
   <%
				//Obtención de reservas para tabla de visualizacion de reservas 
				ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)(request.getAttribute("clientes"));
				Iterator<ArrayList<Object>> iterres1 = clientes.iterator();
				if(!iterres1.hasNext()){  %>
						<h3>No existen datos para mostrar </h3>
	<%			}else{	%>
				<div class="col-lg-6">
				<h4>Resultado(s): <%=clientes.size() %> Cliente(s)</h4>
				</div>
				<hr>
					<table id="tabladedatos" class="tablesorter table-hover table">
					    <thead>
						  <tr style="font-size:13px;">
						  	<th></th>
							<th><h5>Nombre</h5></th>
							<!-- <th><h5>Direccion</h5></th>
							<th><h5>Ciudad</h5></th>
							<th><h5>Comuna</h5></th>	-->
							<th><h5>Fono</h5></th>
							<th><h5>Celular</h5></th>
							<th><h5>Mail</h5></th>
							<th><h5>Reclamo</h5></th>
							<th><h5>Modificar</h5></th>
							<th><h5>Cambiar<br>Contraseña</h5></th>
							<th><h5>Eliminar</h5></th>
						  </tr>
						</thead>
						<tbody>
	<%				while(iterres1.hasNext())
					{
						ArrayList<Object> nueva = iterres1.next();
						Cliente cli = (Cliente)nueva.get(0); 
	%>
						<tr>
							<td><h5><i class="fa fa-user fa-2x"></i></h5></td>
							<td style="white-space: nowrap"><h5><%=cli.getNombre()%> <%=cli.getApellido_Pat()%> <%=cli.getApellido_Mat()%></h5></td>
							<td><h5><%=cli.getFono()%></h5></td>
							<td><h5><%=cli.getCelular()%></h5></td>
							<td><h5><%=cli.getMail()%></h5></td>
							<td><h5><%=((cli.getObservacion()!=null)&&(!cli.getObservacion().equals("null"))?cli.getObservacion():"N/A")%></h5></td>
							<form action="ServletCliente?opcion=CambiarCliente" method="post">
									<input type="hidden" value="<%=cli.getId_Cliente()%>" name="15_Id_Cliente">
							<td>	 
								<div class="btn-group  btn-group-lg">
									 <button type="submit" class="btn btn-success" name="ModificarReserva" value="Modificar" >
                						<i class="fa fa-pencil-square-o fa-1x"></i>
            						 </button>
            					</div>
            				</td>
            				<td>
            					<div class="btn-group  btn-group-lg">
            						 <button type="submit" class="btn btn-info" name="CambiarContrasenia" value="Cambiar" title="Modificar Contrasenia">
                						<i class="fa fa-key fa-1x"></i>
            						 </button>
            					</div>
            				</td>
            				<td>
            					<div class="btn-group  btn-group-lg">
								     <button type="submit" class="btn btn-danger" name="EliminarReserva" value="Eliminar" onclick="return confirm('¿Estás seguro que deseas eliminar este cliente?')">
                						<i class="fa fa-trash fa-1x"></i>
            						 </button>
            					</div>
            				</td>
							</form>		
						</tr>
	<% 				} %>
			    </tbody>
			  </table>
			
	<%  }
	}	%>	
	</div>

</body>

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

<script type="text/javascript">
	$(document).ready(function() 
    	{ 
        	$("#tabladedatos").tablesorter(); 
    	} 
	);
</script>

<script src="assets/PropiedadEstudio.js" type="text/javascript"></script>

</body>
