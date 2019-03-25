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

<link rel="stylesheet" href="assets/tablesorter/css/theme.blue.css" type="text/css" />
<link rel="stylesheet" href="assets/tablesorter/css/theme.green.css" type="text/css" />

<link rel="stylesheet" href="assets/tablesorter/addons/pager/jquery.tablesorter.pager.css">

<style>
    .tablesorter thead .disabled {
        display:none !important;
    }
</style>


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
  		<div class="col-lg-6 col-md-6">
  		<form action="ServletLogin?opcion=GenerarNuevoCliente" method="post">	 
				<div class="btn-group  btn-group-lg">
					<button type="submit" class="btn btn-success" name="NuevoCliente" value="Nuevo" title="Crear nuevo cliente">
                		<i class="fa fa-plus-square fa-1x"></i> <i class="fa fa-user fa-2x"></i>
            		</button>
				</div>
		</form>	
		</div>
		
		<div class="col-lg-6">
				<button type="button"  class="reset btn btn-success btn-sm btn-block"><i class="fa fa-undo fa-1x"></i> Borrar filtros </button>
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
					<table id="tabladedatos" class="tablesorter">
					    <thead>
						  <tr style="font-size:13px;">
						  	<th data-sorter="false" data-filter="false"></th>
							<th><h5>Nombre</h5></th>
							<!-- <th><h5>Direccion</h5></th>
							<th><h5>Ciudad</h5></th>
							<th><h5>Comuna</h5></th>	-->
							<th><h5>Fono</h5></th>
							<th><h5>Celular</h5></th>
							<th><h5>Mail</h5></th>
							<th><h5>Reclamo</h5></th>
							<th data-sorter="false" data-filter="false"><h5>Modificar</h5></th>
							<th data-sorter="false" data-filter="false"><h5>Cambiar<br>Contraseña</h5></th>
							<th data-sorter="false" data-filter="false"><h5>Eliminar</h5></th>
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

<script type="text/javascript" src="assets/tablesorter/js/jquery.tablesorter.combined.js"></script> 

<!-- <script type="text/javascript" src="assets/tablesorter/addons/pager/jquery.tablesorter.pager.js"></script> -->


<script src="assets/tablesorter/js/jquery.tablesorter.js"></script>
<script src="assets/tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>
<script src="assets/tablesorter/js/jquery.tablesorter.widgets.js"></script>

<script id="js">$(function(){

  var $table = $('table'),
  // define pager options
  pagerOptions = {
  	
    // target the pager markup - see the HTML block below
    container: $(".pager"),
    // output string - default is '{page}/{totalPages}';
    // possible variables: {size}, {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
    // also {page:input} & {startRow:input} will add a modifiable input in place of the value
    output: '{startRow} - {endRow} / {filteredRows} ({totalRows})',
    // if true, the table will remain the same height no matter how many records are displayed. The space is made up by an empty
    // table row set to a height to compensate; default is false
    fixedHeight: true,
    // remove rows from the table to speed up the sort of large tables.
    // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
    removeRows: false,
    // go to page selector - select dropdown that sets the current page
    cssGoto: '.gotoPage'
  };

  // Initialize tablesorter
  // ***********************
  $table
    .tablesorter({
      theme: 'blue',
      headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!
      widthFixed: true,
      widgets: ['zebra', 'filter'],
      widgetOptions : {
			filter_columnFilters: true,
			filter_placeholder: { search : 'Buscar...' },
			filter_reset: '.reset'
		}
      
    }).bind('filterInit', function(){
        $table.find('.tablesorter-filter').hide().each(function(){
            var w, $t = $(this);
            w = $t.closest('td').innerWidth();
            $t
                .show()
                .css({
                    'min-width': w,
                    width: w // 'auto' makes it wide again
                });
        });
    })

    // initialize the pager plugin
    // ****************************
    //.tablesorterPager(pagerOptions);

});
	
</script>

<script src="assets/PropiedadEstudio.js" type="text/javascript"></script>

</body>
