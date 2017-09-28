<center><%@page import="modelo.*"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Genesis Estudio</title>

<!-- Google fonts -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,700' rel='stylesheet' type='text/css'>

<!-- font awesome -->
<!--<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">-->

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

<!-- BOTONES DE MEN� ----------------------------------------------------------------->


    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="../dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../bower_components/morrisjs/morris.css" rel="stylesheet">
	
	<!-- Custom Fonts -->
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">

	<style type="text/css">
		.fa-stack-1x { top: .25em; }
		.fa-stack {
		  font-size: .5em;
		  vertical-align: -16%;
		}
		.filetype-text {
		  font-size: .7em;
		  font-weight: 700;
		  font-family: 'Oswald';
		}
	</style>	

</head>

	<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario = (Trabajador)(sesion.getAttribute("usuario"));
		if(sesion == null || usuario == null){  %>
	        	<!-- Mensaje de bienvenida -->
				<div class="alert alert-danger alert-dismissable">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>¡Debes iniciar sesión para ver esta página!</strong>
				</div>
	<%  }else{	
				sesion.setAttribute("usuario", usuario);
    %>

<body>
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
              	 <% if(usuario.getEsAdmin()==1){ %>
              	 	<li class="active" onClick="redirect('ServletLogin?opcion=IndexTrabajadorAdmin')"><a href="">Menú</a></li>
              	 <% }else{ %>
              	 	<li class="active" onClick="redirect('ServletLogin?opcion=IndexTrabajadorFotografo')"><a href="">Menú</a></li>
              	 <% } %>
				 <li onClick="redirect('ServletLogin?opcion=CerrarSesion')"><a href="">Cerrar Sesión</a></li>
              </ul>
            </div>
            <!-- #Nav Ends -->

          </div>
        </div>

      </div>
    </div>
<!-- #Header Starts -->

<!-- #Body Starts -->

<!-- Mensaje de bienvenida -->

<br><br>

<div id="home" class="container">
	<!-- Menú --> 
	
	<% if(request.getAttribute("mensaje") != null && request.getAttribute("tipomensaje")!=null && (request.getAttribute("tipomensaje")).equals("bienvenida")){ %>
	<!-- Mensaje de bienvenida -->
	<div class="alert alert-success alert-dismissable">
	  <button type="button" class="close" data-dismiss="alert">&times;</button>
	  <%=request.getAttribute("mensaje")%>
	   <strong> <%= usuario.getNombre() %>!</strong>
	</div>
	<% }else if(request.getAttribute("mensaje") != null && request.getAttribute("tipomensaje")!=null){ %>
	<!-- Mensaje de bienvenida -->
	<div class="alert alert-<%=request.getAttribute("tipomensaje")%> alert-dismissable">
	  <button type="button" class="close" data-dismiss="alert">&times;</button>
	  <%=request.getAttribute("mensaje")%>
	</div>
	<% } %>
	
	<h3 class="text-center  wowload fadeInLeft">Opciones</h3>
	<p class="text-center  wowload fadeInLeft">Selecciona una acción</p>
	<div class="row wowload fadeInLeft">
				<div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-calendar-o fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div>Visualizar<br>reservas</div>
                                </div>
                            </div>
                        </div>
                        <a href="ServletLogin?opcion=VisualizarReserva" method="post">
                            <div class="panel-footer">
                                <span class="pull-left">¡Visualizar!</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-calendar fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div>Ver reservas<br>en modo<br>calendario</div>
                                </div>
                            </div>
                        </div>
                        <a href="ServletLogin?opcion=MirarCalendario" method="post">
                            <div class="panel-footer">
                                <span class="pull-left">¡Ver!</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>  
         </div>
</div>
		<%  }
        	
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
    <a class="prev">�</a>
    <a class="next">�</a>
    <a class="close">�</a>
    <!-- The modal dialog, which will be used to wrap the lightbox content -->    
</div>


<!-- jquery -->

<script src="assets/bootstrap/js/jquery.js"></script>

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

<!------------------------------ BOTONES DE MEN� ---------------------------->
<!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="../bower_components/raphael/raphael-min.js"></script>
    <script src="../bower_components/morrisjs/morris.min.js"></script>
    <script src="../js/morris-data.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

<!------------------------------ FIN BOTONES DE MENú--------------------------->


<script type="text/javascript">
	function redirect(URL){
		window.location= '/GenesisEstudio/'+ URL;
	}
</script>

</body>
</html>