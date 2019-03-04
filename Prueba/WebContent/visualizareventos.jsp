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

<link rel="stylesheet" href="assets/tablesorter/css/theme.green.css" type="text/css" />

<link rel="stylesheet" href="assets/tablesorter/css/theme.blue.css" type="text/css" />
<style>
    .tablesorter thead .disabled {
        display:none !important;
    }
</style>
</head>
<body>

<div class="modal" id="modalMotivoAnulacion"> <!-- Mision-->
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<br><h4 class="modal-title bounceInUp">Motivo de Anulación</h4>
			</div>
			<div class="modal-body bounceInUp">
<!-- Mantencion Evolutiva: Enviar correo señalando el motivo de anulación -->
			<%%>
				Ingrese el motivo por el cual desea anular Ticket Nro: <input id="NumerodeTicket" readonly /><br>
				<br><br> Motivo: <br><br>
					<form action="ServletEvento?opcion=CambiarEvento" method="post">
 					<input class="form-control" type="text" id="39_Motivo_Anulacion" autocomplete="off" placeholder="Ingrese motivo de anulación" name="39_Motivo_Anulacion"/><br>
					 	<input type="hidden" id="39_Id_Evento" name="39_Id_Evento"   class="form-control" type="text">
					 	<input type="hidden" id="39_Motivo_Anulacion" name="39_Motivo_Anulacion"   class="form-control" type="text">
						<div class="btn-group  btn-group-lg">
						 		<button type="submit" class="btn btn-success" name="AnularEvento" value="Anular" >
             						   ANULAR
         						 </button>
	   					</div>
   					</form>	





 			
	</div>
		<div class="modal-footer bounceInUp">
			<button type="button" class="btn btn-default btn3d" data-dismiss="modal">Cerrar</button>
		</div>
	</div><!-- /.modal-content -->
			
	</div><!-- /.modal-dialog -->
		
</div><!-- /.modal -->


	<% 
		HttpSession sesion = (HttpSession) request.getSession();
		Trabajador usuario = null;
		usuario = (Trabajador)(sesion.getAttribute("usuario"));
		
		//Obtención de reservas para tabla de visualizacion de reservas 
		ArrayList<ArrayList<Object>> eventos = (ArrayList<ArrayList<Object>>)(request.getAttribute("eventos"));
		Iterator<ArrayList<Object>> iterres1 = eventos.iterator();
		
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
  		<div class="row">
  			<div class="col-log-14">
  				<h2>Ingresos y egresos</h2>
  			</div>	
  		</div>
  		<div class="row">
			<div class="col-lg-14">
				<button type="button"  class="reset btn btn-success btn-block"><i class="fa fa-undo fa-1x"></i> Borrar filtros de búsqueda</button>
			</div>
		</div>
  		<br><br>
  		<div class="row">
  			<div class="row">
        <%if(!iterres1.hasNext()){  %>
			<div class="col-lg-14">
				<h3>No existen datos para mostrar </h3>
			</div>
		<%}else{%>
			<div class="row">
				<h4>Resultado(s): <%=eventos.size() %> Ingresos y egresos</h4>
			</div>
			<div class="row">
				<table id="tabladedatos" class="tablesorter table-hover table" >
					<thead>
						  <tr style="font-size:13px;">
						  	<th data-sorter="false" data-filter="false"></th>
							<th><h5>N° Boleta</h5></th>
							<th><h5>Fecha</h5></th>
							<th style="width:5%"><h5>Forma de Pago</h5></th>
							<th><h5>Movimiento</h5></th>
							<th style="width:9%"><h5>Sesion Asoc</h5></th>
							<th><h5>Valor total por cobrar</h5></th>
							<th><h5>ID Usuario</h5></th>
							<th><h5>Item</h5></th>
							<th><h5>Descripcion</h5></th>
							<th><h5>Estado</h5></th>	
							<th data-sorter="false" data-filter="false"><h5>Id Evento</h5></th>
							<th data-sorter="false" data-filter="false"><h5>Anular</h5></th>
							<th data-sorter="false" data-filter="false"><h5>Imprimir</h5></th>
						  </tr>
					</thead>
					<tbody>
					<%while(iterres1.hasNext())
					{
						ArrayList<Object> nueva = iterres1.next();
						Evento ev = (Evento)nueva.get(0); 
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
						String reserva = null;
						 
						if(nueva.get(1)!=null && nueva.get(2)!=null){  // Fecha reserva
							reserva = sdf.format(nueva.get(1)) + "<br>" + nueva.get(2) + " "+ nueva.get(3);
						}else{
							reserva = "N/A";
						}
					%>
						<tr>
							<td><h5><i class="fa fa-user fa-2x"></i></h5></td>

							<%
							
							int vBoleta = ev.getNumero_Boleta();
							vBoleta = Math.round(vBoleta );
							
							 %>

							<td><h5><%=Math.round(vBoleta)%></h5></td>
							<td><h5><%=ev.getFecha().replace(" ","<br>")%></h5></td>
							<td><h5><%=ev.getForma_Pago()%></h5></td>
							<td><h5><%=ev.getMovimiento()%></h5></td>
							<td><h5><%=reserva%></h5></td>
							<td><h5><%=ev.getValor()%></h5></td>
							<td><h5><%=ev.getTrabajador()%> - <%=nueva.get(4)%></h5></td>
							<td><h5><%=ev.getItem()%></h5></td>
							<td><h5><%=(ev.getDescripcion().equals("null"))?"":ev.getDescripcion()%></h5></td>
							<td><h5><%=(ev.getEstado()==1)?"Activa":"Anulada"%></h5></td>
							
							<td><h5><%=ev.getId_Evento() %></h5></td>
							
							<td>	
									<input type="hidden" value="<%=ev.getId_Evento()%>" name="param_39_Numero_Boleta">
								<div class="btn-group  btn-group-lg">
									<% if(ev.getEstado()==1){  //Activo o inactivo %>
									 <button type="button" class="btn btn-success" name="AnularEvento" value="Anular" data-toggle="modal" data-target="#modalMotivoAnulacion" onclick="asignaTicket('<%=ev.getId_Evento()%>')">
                						<i class="fa fa-minus-circle fa-1x"></i>
                						
            						 </button>
            						<%}else{ %>
            						 <button type="submit" class="btn btn-success" disabled name="AnularEvento" value="Anular" onclick="return confirm('¿Estás seguro que deseas anular este evento?')">
                						<i class="fa fa-minus-circle fa-1x"></i>
            						 </button>
            						<%} %>
            					</div>
            				</td>
							
            				<td>
            					
            					 	<% if(nueva.get(1)!=null && nueva.get(2)!=null && ev.getEstado() == 1){  // Fecha reserva %>
									<form action="ServletEvento?opcion=CambiarEvento" method="post">
									<input type="hidden" value="<%=ev.getId_Evento()%>" name="39_Id_Evento">
									
										<div class="btn-group  btn-group-lg">
										 <button type="submit" class="btn btn-success" name="ImprimirEvento" value="Imprimir" >
                							<i class="fa fa-print fa-1x"></i>
            						 	</button>
            						</form></div>
            					
									<%}else if((nueva.get(1)==null || nueva.get(2)==null) && ev.getEstado() == 1){ %>
									<form action="ServletEvento?opcion=ImprimirEventoSimple" method="post">
									<input type="hidden" value="<%=ev.getId_Evento()%>" name="39_Id_Evento">	
									<div class="btn-group  btn-group-lg">
										 <button type="submit" class="btn btn-success" name="ImprimirEventoSimple" value="Imprimir" >
                							<i class="fa fa-print fa-1x"></i>
            						 	 </button>
            						</form></div>
            					
									<%}else{ %>
									<form action="ServletEvento?opcion=CambiarEvento" method="post">
									<input type="hidden" value="<%=ev.getId_Evento()%>" name="39_Id_Evento">
									<div class="btn-group  btn-group-lg">
										 <button type="submit" class="btn btn-success" name="ImprimirEventoSimple" disabled value="Imprimir" >
                							<i class="fa fa-print fa-1x"></i>
            						 	 </button></div>
									<%} %>
									
								</form>
            				</td>	
						</tr>
	<% 				} %>
			    </tbody>
			  </table>
			 </div>
	<%  }  %>	
	</div>
	
	</div>
</div>
		  
	
<!-- Footer Starts -->
<div class="footer text-center spacer">
	<p >Sistema Estudio. Advancing Group Ltda.</p>
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
	<% } %>
</body>

<!-- jquery -->
<script src="assets/jquery.js"></script>


<script type="text/javascript" src="assets/tablesorter/js/jquery.tablesorter.combined.js"></script> 

<script type="text/javascript" src="assets/tablesorter/addons/pager/jquery.tablesorter.pager.js"></script>

<!-- boostrap -->
<script src="assets/bootstrap/js/bootstrap.js" type="text/javascript" ></script>

<!-- jquery mobile -->
<script src="assets/mobile/touchSwipe.min.js"></script>
<script src="assets/respond/respond.js"></script>

<!-- gallery -->
<script src="assets/gallery/jquery.blueimp-gallery.min.js"></script>

<!-- custom script -->
<script src="assets/script.js"></script>

<script id="js"> 
<!-- SBRITO -->
function asignaTicket(Valor){
      document.getElementById("NumerodeTicket").value = Valor;
      document.getElementById("39_Id_Evento").value = Valor;
}
function recuperaTicket(){
 return document.getElementById("NumerodeTicket").value
}
/*
	$(document).ready(function() {

	var $table = $('table').tablesorter({
		
		widthFixed : true,
		theme: 'blue',
		widgets: ["zebra", "filter"],
		widgetOptions : {
			// filter_anyMatch replaced! Instead use the filter_external option
			// Set to use a jQuery selector (or jQuery object) pointing to the
			// external filter (column specific or any match)
			filter_external : '.search',
			// add a default type search to the first name column
			filter_defaultFilter: { 1 : '~{query}' },
			// include column filters
			filter_columnFilters: true,
			filter_placeholder: { search : 'Buscar...' },
			filter_saveFilters : true,
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
    }).tablesorterPager({container: $("#pager"), positionFixed: false }); ;

	// make demo search buttons work
	$('button[data-column]').on('click', function(){
		var $this = $(this),
			totalColumns = $table[0].config.columns,
			col = $this.data('column'), // zero-based index or "all"
			filter = [];

		// text to add to filter
		filter[ col === 'all' ? totalColumns : col ] = $this.text();
		$table.trigger('search', [ filter ]);
		return false;
	});

});*/
	
$(function(){

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
</html>
