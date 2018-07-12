package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import conexion.JavaMail;
import conexion.SQLS_conexion;
import modelo.Campania;
import modelo.Cliente;
import modelo.Evento;
import modelo.Reserva;
import modelo.SesionAuxiliar;
import modelo.Trabajador;
import modelo.Vendedor;

/**
 * Servlet implementation class LetTrabajador
 */
@WebServlet("/LetSesion")
@MultipartConfig
public class LetSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion; 
		
	protected void procesamientoPeticion(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
		
			String llegoSolicitud = request.getParameter("opcion");
		    
		    SQLS_conexion gd = new SQLS_conexion();
		    
		    RequestDispatcher rd = null;
		    
		    sesion = request.getSession();
		    
		    if (llegoSolicitud.equals("NuevoSesion")) {
		    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de reserva es "+ trab.getNombre());	    	
    	    	
    	    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	int llegoIdReserva = Integer.parseInt(request.getParameter("16_Id_Reserva"));
    	    	
    	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("Ultimas_Reservas].[16_ID_RESERVA", Integer.toString(llegoIdReserva), "Int");
				Reserva reserv = (Reserva) reservas.get(0).get(0);
    	    	
				ArrayList<Cliente> cli = gd.getClientesFiltro("15_ID_CLIENTE", Integer.toString(reserv.getId_Cliente()), "Int");
		    	Cliente cliente = cli.get(0); 
				
    	    	SesionAuxiliar sa = (SesionAuxiliar) (gd.getSesionAuxiliar(llegoIdReserva));
				request.setAttribute("sesionauxiliar", sa);
    	    	
    	    	String llegoAsistio = (request.getParameter("35_Asistio")!=null)?request.getParameter("35_Asistio"):null;
    	    	
    	    	int llegoNumeroTicket = (request.getParameter("35_Numero_Ticket")!="")?Integer.parseInt(request.getParameter("35_Numero_Ticket")):-1;
    	    	int llegoValorPorCobrar =  (request.getParameter("35_Valor_Por_Cobrar")!="")?Integer.parseInt(request.getParameter("35_Valor_Por_Cobrar")):-1;
    	    	int llegoCD =  (request.getParameter("35_CD")!="")?Integer.parseInt(request.getParameter("35_CD")):-1;
    	    	int llegoExtras = (request.getParameter("35_Extras")!="")?Integer.parseInt(request.getParameter("35_Extras")):-1;		 
    	    	int llegoDescuento = (request.getParameter("35_Descuento")!="")?Integer.parseInt(request.getParameter("35_Descuento")):-1;		 
    	    	int llegoPersona_Adicional = (request.getParameter("35_Persona_Adicional")!="")?Integer.parseInt(request.getParameter("35_Persona_Adicional")):-1;
    	    	int llegoRecargoPorReagendar =  (request.getParameter("35_Recargo_Por_Reagendar")!="")?Integer.parseInt(request.getParameter("35_Recargo_Por_Reagendar")):-1;
    	    	int llegoMontoExtras = (request.getParameter("35_Monto_Extras")!="")?Integer.parseInt(request.getParameter("35_Monto_Extras")):-1;
    	    	String llegoFotografo = request.getParameter("35_Fotografo");
    	    	String llegoFotosSeleccionadas = request.getParameter("35_Fotos_Seleccionadas");
    	    	
    	    	String llegoFechaAntigua = request.getParameter("FechaAntigua");
    	    	
    	    	String llegoFechaEntrega = request.getParameter("35_Fecha_Entrega");
    	    	//String llegoEntregadas = request.getParameter("35_Entregadas");
    	    	//String llegoFechaEnvioImprimir = request.getParameter("35_Fecha_Envio_Imprimir");
    	    	//int llegoMontoImpresion = (request.getParameter("35_Monto_Impresion")!="")?Integer.parseInt(request.getParameter("35_Monto_Impresion")):-1;
    	    	//String llegoNumeroFactura = request.getParameter("35_Numero_Factura");
    	    	
    	    	int llegoCant10x15 = Integer.parseInt(request.getParameter("35_Cant_10x15"));
    	    	int llego17Cant10x15 = Integer.parseInt(request.getParameter("17_Cant_10x15"));
    	    	
		    	int llegoCant15x21 = Integer.parseInt(request.getParameter("35_Cant_15x21")); 
		    	int llego17Cant15x21 = Integer.parseInt(request.getParameter("17_Cant_15x21")); 
		    	
		    	int llegoCant20x30 = Integer.parseInt(request.getParameter("35_Cant_20x30")); 
		    	int llego17Cant20x30 = Integer.parseInt(request.getParameter("17_Cant_20x30")); 
		    	
		    	int llegoCant30x40 = Integer.parseInt(request.getParameter("35_Cant_30x40"));
		    	int llego17Cant30x40 = Integer.parseInt(request.getParameter("17_Cant_30x40"));
		    	
		    	String llegoContador = request.getParameter("35_Cont_Fecha_Entrega");
		    	//String llegoFechaRetira = request.getParameter("35_Fecha_Retiro");
		    	//String llegoNombreRetira = request.getParameter("35_Nombre_Retira");
		    	//String llegoListaParaEntregar = request.getParameter("35_Lista_Para_Entregar");
		    	String llegoFechaSesion = request.getParameter("35_Fecha_Sesion");
    	    	
		    	int llegoCampaniaModificada = Integer.parseInt(request.getParameter("17_Id_Campania"));
		    	
		    	//Atributos de evento
		    	String llegoFormaPago = request.getParameter("39_Forma_Pago");
		    	String llegoValor = request.getParameter("");
		    	String llegoItem = "Sesion";
		    	int llegoEstado = 1;
		    	String llegoMovimiento = "Ingreso";
		    	
    	    	SesionAuxiliar saux = new SesionAuxiliar();
    	    	
    	    	if((llegoContador!=null)&&(!llegoContador.equals(""))){
    	    		int contador = Integer.parseInt(llegoContador);
    	    		//System.out.println("Cambio: "+ llegoFechaAntigua + " y "+llegoFechaEntrega);
    	    		if(!llegoFechaAntigua.equals(llegoFechaEntrega)){
    	    			contador++;
    	    		}
		    		saux.setCont_Fecha_Entrega(contador);
		    	}
    	    	//saux.setNombre_Retira(llegoNombreRetira);
    	    	
    	    	/*if(llegoListaParaEntregar!=null && llegoListaParaEntregar.equals("on")){
    				saux.setLista_Para_Entregar(true);
    			}else{
    				saux.setLista_Para_Entregar(false);
    			}*/
    	    	if(llegoAsistio!=null && llegoAsistio.equals("on")){
    				saux.setAsistio(true);
    			}else{
    				saux.setAsistio(false);
    			}
    	    	if(llegoFotosSeleccionadas!=null && llegoFotosSeleccionadas.equals("on")){
    				saux.setFotos_Seleccionadas(true);
    			}else{
    				saux.setFotos_Seleccionadas(false);
    			}
    	    	/*if(llegoEntregadas!=null && llegoEntregadas.equals("on")){
    				saux.setEntregadas(true);
    			}else{
    				saux.setEntregadas(false);
    			}*/
    	    	
    	    	java.util.Date Fecha1 = ((llegoFechaEntrega!=null)&&(!llegoFechaEntrega.equals("")))?(sdf2.parse(llegoFechaEntrega)):null;
    			//java.util.Date Fecha2 =  ((llegoFechaEnvioImprimir!=null)&&(!llegoFechaEnvioImprimir.equals("")))?(sdf2.parse(llegoFechaEnvioImprimir)):null;
    			//java.util.Date Fecha3 =  ((llegoFechaRetira!=null)&&(!llegoFechaRetira.equals("")))?(sdf2.parse(llegoFechaRetira)):null;
    			java.util.Date Fecha4 =  ((llegoFechaSesion!=null)&&(!llegoFechaSesion.equals("")))?(sdf2.parse(llegoFechaSesion)):null;
    			
    			//saux.setFecha_Retiro(Fecha3);
    			//saux.setFecha_Sesion(Fecha4);
    			
    	    	saux.setNumero_Ticket(llegoNumeroTicket);
    	    	
    	    	//gd.ActualizarTicket(llegoNumeroTicket, llegoIdReserva);
    	    	
    	    	saux.setValor_Por_Cobrar(llegoValorPorCobrar);
    	    	saux.setCD(llegoCD);
    	    	saux.setExtras(llegoExtras);
    	    	saux.setDescuento(llegoDescuento);
    	    	saux.setPersona_Adicional(llegoPersona_Adicional);
    	    	saux.setRecargo_Por_Reagendar(llegoRecargoPorReagendar);
    	    	saux.setMonto_Extras(llegoMontoExtras);
    	    	saux.setFotografo(llegoFotografo);
    	    	saux.setFecha_Entrega(Fecha1);
    	    	//saux.setFecha_Envio_Imprimir(Fecha2);
    	    	//saux.setMonto_Impresion(llegoMontoImpresion);
    	    	//saux.setNumero_Factura(llegoNumeroFactura);
    	    	saux.setId_Reserva_Asoc(llegoIdReserva);
    	    	saux.setCampaniaConvetida(llegoCampaniaModificada);
    	    	
    	    	if(llegoCant10x15>llego17Cant10x15){
    	    		saux.setCant_10x15(llegoCant10x15-llego17Cant10x15);
    	    	}else{
    	    		saux.setCant_10x15(0);
    	    	}
    	    
    	    	if(llegoCant15x21>llego17Cant15x21){
    	    		saux.setCant_15x21(llegoCant15x21-llego17Cant15x21);
    	    	}else{
    	    		saux.setCant_15x21(0);
    	    	}
    	    
    	    	if(llegoCant20x30>llego17Cant20x30){
    	    		saux.setCant_20x30(llegoCant20x30-llego17Cant20x30);
    	    	}else{
    	    		saux.setCant_20x30(0);
    	    	}
    	    	
    	    	if(llegoCant30x40>llego17Cant30x40){
    	    		saux.setCant_30x40(llegoCant30x40-llego17Cant30x40);
    	    	}else{
    	    		saux.setCant_30x40(0);
    	    	}
    	    	
    	    	//Ingreso de la sesión auxiliar
    			
    	    	Evento ev = new Evento (); 
    			SimpleDateFormat sdf4 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	    	 
    	    	String fechaFinal = sdf4.format(Fecha4);
    	    	ev.setFecha(fechaFinal);
    	    	ev.setForma_Pago(llegoFormaPago);
    	    	ev.setValor(llegoMontoExtras);
    	    	ev.setTrabajador(trab.getId_Trabajador());
    	    	ev.setItem(llegoItem);
    	    	//ev.setDescripcion();
    	    	ev.setEstado(llegoEstado);
    	    	ev.setNumero_Boleta(llegoNumeroTicket);
    	    	ev.setMovimiento(llegoMovimiento);    			
    	    	
    	    	int resultado = gd.IngresarSesionAuxiliar(saux, ev);
    	    	
    	    	if((sa!=null) && (sa.isLista_Para_Entregar()==false && saux.isLista_Para_Entregar())){
    	    		JavaMail mail = new JavaMail();
    	    		
	    			String correo = cliente.getMail();
	    			String correo2 = trab.getEmail();
	    			String correo3 = "salomon.nilo@advancing.cl"; 
	    			
	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
	    	    	
	    	    	String correoEnvia = Vend.getMail();
	    	    	String nombreEnvia = Vend.getVendedor();
	    	    	String logoIcono = Vend.getLogo_Icono();
	    	    	String pagina = Vend.getWeb();
	    	    	String direccion = Vend.getDireccion();
	    	    	String clave = Vend.getMail_PW();
	    	    	String googleMap = Vend.getGoogleMap();

	    	    	String AsuntoDeCorreo = "Retiro de fotografías en "+nombreEnvia;
	    	    		/*	
	    	    	if(reserv.getVendedor().equals("Genesis")){
	    	    		correoEnvia = "contacto@genesisestudio.cl";
	    	    		nombreEnvia = "Genesis Estudio";
	    	    		logoIcono = "LogoLetrasGenesis";
	    	    		pagina = "www.GenesisEstudio.cl";
	    	    	}
	    	    	if(reserv.getVendedor().equals("Expressiones")){
	    	    		correoEnvia = "contacto@fotoexpressiones.com";
	    	    		nombreEnvia = "Foto Expressiones";
	    	    		logoIcono = "LogoLetrasExpressiones";
	    	    		pagina = "www.FotoExpressiones.com";
	    	    	}*/
	    			
	    			String MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
	    					+ " Le informamos que <strong>hemos recepcionado sus fotografías en el estudio fotográfico</strong> y"
	    					+ " que están listas para entregárselas,"
	    					+ " favor acercarse a nuestras dependencias.<br>"
	    					+ "¡Te esperamos!"
	    					+ "<br>Para más información visita nuestra página: "+pagina	    				
	    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\""+logoIcono+"\"/></center>";
	    			    	    			    	    	    	
	    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave,false);
    			  	 	    			
    	    	}
    	    	if((sa!=null) && (sa.isEntregadas()==false && saux.isEntregadas())){
	    	    		JavaMail mail = new JavaMail();
		    			
		    			String correo = cliente.getMail();
		    			String correo2 = trab.getEmail();
		    			String correo3 = "salomon.nilo@advancing.cl"; 
		    			
		    			SimpleDateFormat sdf511 = new SimpleDateFormat("dd/MM/yyyy");
		    					    	    			    			
		    			String fechaentrega = sdf511.format(saux.getFecha_Entrega());
		    			String NombreQuienRetira = saux.getNombre_Retira();

		    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
		    			
		    	    	String correoEnvia = Vend.getMail();
		    	    	String nombreEnvia = Vend.getVendedor();
		    	    	String logoIcono = Vend.getLogo_Icono();
		    	    	String pagina = Vend.getWeb();
		    	    	String direccion = Vend.getDireccion();
		    	    	String clave = Vend.getMail_PW();
		    	    	String googleMap = Vend.getGoogleMap();
		    	    	
		    	    	String AsuntoDeCorreo = "Confirmación de retiro de fotografías";
		    	    			/*
		    	    	if(reserv.getVendedor().equals("Genesis")){
		    	    		correoEnvia = "contacto@genesisestudio.cl";
		    	    		nombreEnvia = "Genesis Estudio";
		    	    		logoIcono = "LogoLetrasGenesis";
		    	    		pagina = "www.GenesisEstudio.cl";
		    	    	}
		    	    	if(reserv.getVendedor().equals("Expressiones")){
		    	    		correoEnvia = "contacto@fotoexpressiones.com";
		    	    		nombreEnvia = "Foto Expressiones";
		    	    		logoIcono = "LogoLetrasExpressiones";
		    	    		pagina = "www.FotoExpressiones.com";
		    	    	}*/
		    			
		    			String MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
		    					+ " Le confirmamos que las fotografías fueron retiradas por <strong>"+NombreQuienRetira
		    					+ "</strong> el día </strong>"+fechaentrega 
		    					+ "</strong> <br><br><strong>¡Agradecemos su preferencia, le esperamos para una próxima oportunidad!</strong>"
		    					+ "<br>No olvide visitarnos en: "+pagina
		    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\""+logoIcono+"\"/></center>";
		    			
		    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave, false);
		    			
		    			ArrayList<ArrayList<Object>> sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();
		    	    	request.setAttribute("sesiones", sesiones);
		    	    	
		    	    	if(resultado==0){
		    	    		String mensaje = "Hubo un problema al crear la sesión, probablemente el número de ticket ya está asignado a otra Sesión/Evento";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "danger");
		    	    	}
		    	    			    			
		    	    	rd = request.getRequestDispatcher("/visualizarsesiones.jsp");
    	    	}
    	    	   			
    		    ArrayList<ArrayList<Object>> reservass = null;
    			    		  
    		    if(sesion.getAttribute("15_Nombre")!=null){
    				reservass = (ArrayList<ArrayList<Object>>)gd.getNoPreReservasSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");	
        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
        	    	reservass = (ArrayList<ArrayList<Object>>)gd.getNoPreReservasSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");	
        	    }else if(sesion.getAttribute("16_Fecha1")!=null || sesion.getAttribute("16_Fecha2")!=null){
        	    	reservass = (ArrayList<ArrayList<Object>>)gd.getNoPreReservasSinIdConFechas("", "", "", (String)sesion.getAttribute("16_Fecha1"), (String)sesion.getAttribute("16_Fecha2"));	
        		}else if(sesion.getAttribute("24_Id_Ticket")!=null){
    				reservass = (ArrayList<ArrayList<Object>>)gd.getNoPreReservasSinIdLike("24_Id_Ticket", (String)sesion.getAttribute("24_Id_Ticket"), "String");	
        		}else{
        			reservass = (ArrayList<ArrayList<Object>>)gd.getNoPreReservasSinIdLike("", "", "");	
        		}
    			
    			request.setAttribute("reservas", reservass);
    			    			    			
    	    	rd = request.getRequestDispatcher("/nuevosesiones.jsp");
    	    	rd.forward(request, response);
		    }
		    
		    if (llegoSolicitud.equals("EnviarMail")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    
    	    	
    	    	String llegoReservaa = (request.getParameter("16_Id_Reserva"));
    	    	
    	    	String llegoMail = (request.getParameter("15_Mail"));
    	    	String llegoNombre = (request.getParameter("Nombre"));
    	    	String fechaSesion = "";
    	    	fechaSesion = request.getParameter("fechasesion");
    	    	
    	    	ArrayList<Reserva> res = gd.getReservasFiltro("16_ID_RESERVA", llegoReservaa, "Int");
    	    	Reserva reserv = res.get(0); 
    	    	
    	    	if(!fechaSesion.equals("")){
    	    		fechaSesion = "Usted tuvo una sesión fotográfica el día "+fechaSesion +".<br>";
    	    	}
    	    	
    	    	SesionAuxiliar sa = gd.getSesionAuxiliar(Integer.parseInt(llegoReservaa));
    	    	    		
    			//Opciones - Modificar o eliminar
    			String llegoSeleccionadas = request.getParameter("NoSeleccionadas");
    			String llegoListas = request.getParameter("Listas");
    			String llegoEntregadas = request.getParameter("Entregadas");
      			
    			if((llegoSeleccionadas!=null)||(llegoListas!=null)||(llegoEntregadas!=null)){
    				if((llegoSeleccionadas!=null)&&(!(llegoSeleccionadas.equals("")))){
    					
    					JavaMail mail = new JavaMail();
    	    			
    	    			String correo = llegoMail;
    	    			String correo2 = trab.getEmail();
    	    			String correo3 = "salomon.nilo@advancing.cl";
    	    			
    	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
    	    			
    	    	    	String correoEnvia = Vend.getMail();
    	    	    	String nombreEnvia = Vend.getVendedor();
    	    	    	String logoIcono = Vend.getLogo_Icono();
    	    	    	String pagina = Vend.getWeb();
    	    	    	String direccion = Vend.getDireccion();
    	    	    	String clave = Vend.getMail_PW();
    	    	    	String googleMap = Vend.getGoogleMap();
    	    	    	
    	    	    	String AsuntoDeCorreo = "Selección de fotografías - "+nombreEnvia;
    	    	    	   	    	    	    	    			
    	    			String MensajeDeCorreo = "Estimado/a <strong>"+llegoNombre+":</strong> <br><br><br>"
    	    					+ fechaSesion
    	    					+ " Usted aún <strong>no ha seleccionado las fotografías</strong> que desea,"
    	    					+ " favor ponerse en contacto con nosotros o acercarse a nuestras dependencias.<br>"
    	    					+ "¡Te esperamos!"
    	    					+ "<br>Nuestra página es: "+pagina
    	    					+ "<br><br><br><strong>"+nombreEnvia+"</strong><br><br><center><img src=\""+logoIcono+"\"/></center>";
    	    			    	    			
    	    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave, false);
    	    			  	    	    	
    	    			ArrayList<ArrayList<Object>> sesiones = null;	
			    		  
    	    		    if(sesion.getAttribute("15_Nombre")!=null){
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");
    	        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	        	    	sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");    
    	        	    }else{
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();
    	        		}
    	    			request.setAttribute("sesiones", sesiones);
    	    	    	    	    			
    	    	    	rd = request.getRequestDispatcher("/visualizarsesiones.jsp");
    				}
    				if((llegoListas!=null)&&(!llegoListas.equals(""))){
    					
    					JavaMail mail = new JavaMail();
    	    			
    	    			String correo = llegoMail;
    	    			String correo2 = trab.getEmail();
    	    			String correo3 = "salomon.nilo@advancing.cl"; 

    	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
    	    			
    	    	    	String correoEnvia = Vend.getMail();
    	    	    	String nombreEnvia = Vend.getVendedor();
    	    	    	String logoIcono = Vend.getLogo_Icono();
    	    	    	String pagina = Vend.getWeb();
    	    	    	String direccion = Vend.getDireccion();
    	    	    	String clave = Vend.getMail_PW();
    	    	    	String googleMap = Vend.getGoogleMap();
    	    	    	
    	    	    	String AsuntoDeCorreo = "Retiro de fotografías en "+nombreEnvia;
    	    	    	    	    			
    	    			String MensajeDeCorreo = "Estimado/a <strong>"+llegoNombre+":</strong> <br><br><br>"
    	    					+ fechaSesion
    	    					+ " Le informamos que <strong>hemos recepcionado sus fotografías en el estudio fotográfico</strong> y"
    	    					+ " que están listas para entregárselas,"
    	    					+ " favor acercarse a nuestras dependencias.<br>"
    	    					+ "¡Te esperamos!"
    	    					+ "<br>Nuestra página es: "+pagina 
    	    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\""+logoIcono+"\"/></center>";
    	    			    	    			    	    	    	
    	    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave, false);
	    			  	 	    			
    	    			ArrayList<ArrayList<Object>> sesiones = null;	
			    		  
    	    		    if(sesion.getAttribute("15_Nombre")!=null){
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");
    	        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	        	    	sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");    
    	        	    }else{
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();
    	        		}
    	    			request.setAttribute("sesiones", sesiones);
    	    			
    	    	    	rd = request.getRequestDispatcher("/visualizarsesiones.jsp");
    				}
    				if((llegoEntregadas!=null)&&(!llegoEntregadas.equals(""))){
    					
    					JavaMail mail = new JavaMail();
    	    			
    	    			String correo = llegoMail;
    	    			String correo2 = trab.getEmail();
    	    			String correo3 = "salomon.nilo@advancing.cl"; 
    	    			    	    	    	
    	    			String fechaentrega = request.getParameter("fecharetiro");
    	    			String NombreQuienRetira = request.getParameter("nombreretiro");

    	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
    	    			
    	    	    	String correoEnvia = Vend.getMail();
    	    	    	String nombreEnvia = Vend.getVendedor();
    	    	    	String logoIcono = Vend.getLogo_Icono();
    	    	    	String pagina = Vend.getWeb();
    	    	    	String direccion = Vend.getDireccion();
    	    	    	String clave = Vend.getMail_PW();
    	    	    	String googleMap = Vend.getGoogleMap();
    	    	    	
    	    	    	String AsuntoDeCorreo = "Confirmación de retiro de fotografías";
    	    	    	    	    	    	    	    	    	
    	    			String MensajeDeCorreo = "Estimado/a <strong>"+llegoNombre+":</strong> <br><br><br>"
    	    					+ fechaSesion
    	    					+ " Le confirmamos que las fotografías fueron retiradas por <strong>"+NombreQuienRetira
    	    					+ "</strong> el día </strong>"+fechaentrega 
    	    					+ "</strong> <br><br><strong>¡Agradecemos su preferencia, le esperamos para una próxima oportunidad!</strong>"
    	    					+ "<br>No dejes de visitar nuestra página es: "+pagina
    	    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\""+logoIcono+"\"/></center>";
    	    			
    	    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave, false);
    			  	 		    	    	
    	    			ArrayList<ArrayList<Object>> sesiones = null;	
			    		  
    	    		    if(sesion.getAttribute("15_Nombre")!=null){
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");
    	        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	        	    	sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");    
    	        	    }else{
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();
    	        		}
    	    			request.setAttribute("sesiones", sesiones);
    	    			
    	    	    	rd = request.getRequestDispatcher("/visualizarsesiones.jsp");
    				}
    			}
    			rd.forward(request, response);
    	  }
		    
		    if (llegoSolicitud.equals("FiltroClienteSesiones")) {
    	    	
    	    	Trabajador usuario =  null;
    	    	usuario = (Trabajador) sesion.getAttribute("usuario");
    	    	//request.setAttribute("usuario", usuario);
    			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
    			
    			String columna = "";
    			this.InvalidarFiltros();
    			
    			if(request.getParameter("15_Nombre")!=null){
    				columna = "15_Nombre";
    				sesion.setAttribute("15_Nombre", request.getParameter("15_Nombre"));
    			}
    			if(request.getParameter("15_Apellido_Pat")!=null){
    				columna = "15_Apellido_Pat";
    				sesion.setAttribute("15_Apellido_Pat", request.getParameter("15_Apellido_Pat"));
    			}
    			    			
    			ArrayList<ArrayList<Object>> sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike(columna, request.getParameter(columna), "String");	
    	    	request.setAttribute("sesiones", sesiones);
    			
    	    	rd = request.getRequestDispatcher("/visualizarsesiones.jsp");;
    	    	rd.forward(request, response);
    	    }
		    
		    if (llegoSolicitud.equals("FiltroClienteSesionesEstado")) {
    	    	
    	    	Trabajador usuario =  null;
    	    	usuario = (Trabajador) sesion.getAttribute("usuario");
    	    	//request.setAttribute("usuario", usuario);
    			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
    			
    			String columna = "";
    			this.InvalidarFiltros();
    			
    			if(request.getParameter("15_Nombre")!=null){
    				columna = "15_Nombre";
    				sesion.setAttribute("15_Nombre", request.getParameter("15_Nombre"));
    			}
    			if(request.getParameter("15_Apellido_Pat")!=null){
    				columna = "15_Apellido_Pat";
    				sesion.setAttribute("15_Apellido_Pat", request.getParameter("15_Apellido_Pat"));
    			}
    			    			
    			ArrayList<ArrayList<Object>> sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike(columna, request.getParameter(columna), "String");	
    	    	request.setAttribute("sesiones", sesiones);
    			
    	    	rd = request.getRequestDispatcher("/visualizarsesionesestado.jsp");;
    	    	rd.forward(request, response);
    	    }
		    
		    
		   if (llegoSolicitud.equals("CambiarEstado")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    
    	    	
    	    	String llegoReservaa = (request.getParameter("16_Id_Reserva"));
    	    	
    	    	//String llegoMail = (request.getParameter("15_Mail"));
    	    	//String llegoNombre = (request.getParameter("Nombre"));
    	    	//String fechaSesion = "";
    	    	//fechaSesion = request.getParameter("fechasesion");
    	    	
    	    	ArrayList<Reserva> res = gd.getReservasFiltro("16_ID_RESERVA", llegoReservaa, "Int");
    	    	Reserva reserv = res.get(0); 
    	    	
    	    	SesionAuxiliar sa = gd.getSesionAuxiliar(Integer.parseInt(llegoReservaa));
    	    	    		
    			//Opciones - Modificar o eliminar
    			String llegoSeleccionadas = request.getParameter("CambiarS");
    			String llegoListas 		  = request.getParameter("CambiarL");
    			String llegoEntregadas 	  = request.getParameter("CambiarE");
      			
    			if((llegoSeleccionadas!=null)||(llegoListas!=null)||(llegoEntregadas!=null)){
    				if((llegoSeleccionadas!=null)&&(!(llegoSeleccionadas.equals("")))){
    					
    					boolean seleccionadas = (request.getParameter("CambiarS").equals("1"))?true:false;
    					
    					Date date = new Date();
    					gd.ConvertirFecha(date);
    					
    					sa.setFotos_Seleccionadas(seleccionadas);
    					if(seleccionadas){
    						sa.setFecha_Entrega(date);
    					}else{
    						sa.setLista_Para_Entregar(seleccionadas);
    						sa.setEntregadas(seleccionadas);
    						
    						sa.setFecha_Entrega(null);
    						sa.setFecha_Envio_Imprimir(null);
    						sa.setFecha_Retiro(null);
    					}
    					
    					gd.ModificarSesionAuxiliar(sa);    					    					
    					
    	    			ArrayList<ArrayList<Object>> sesiones = null;	
			    		  
    	    		    if(sesion.getAttribute("15_Nombre")!=null){
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");
    	        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	        	    	sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");    
    	        	    }else{
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();
    	        		}
    	    			request.setAttribute("sesiones", sesiones);
    	    	    	    	    			
    	    	    	rd = request.getRequestDispatcher("/visualizarsesionesestado.jsp");
    				}
    				if((llegoListas!=null)&&(!llegoListas.equals(""))){
    					
    					boolean listas = (request.getParameter("CambiarL").equals("1"))?true:false;
    					
    					Date date = new Date();
    					gd.ConvertirFecha(date);
    					
    					sa.setLista_Para_Entregar(listas);
    					if(listas){
    						sa.setFotos_Seleccionadas(listas);
    						
    						sa.setFecha_Entrega(date);
    						sa.setFecha_Envio_Imprimir(date);
    					}else{
    						sa.setEntregadas(listas);
    						
    						sa.setFecha_Envio_Imprimir(null);
    						sa.setFecha_Retiro(null);
    					}    					
    					
    					gd.ModificarSesionAuxiliar(sa);  
    					
    	    			ArrayList<ArrayList<Object>> sesiones = null;	
			    		  
    	    		    if(sesion.getAttribute("15_Nombre")!=null){
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");
    	        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	        	    	sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");    
    	        	    }else{
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();
    	        		}
    	    			request.setAttribute("sesiones", sesiones);
    	    			
    	    	    	rd = request.getRequestDispatcher("/visualizarsesionesestado.jsp");
    				}
    				if((llegoEntregadas!=null)&&(!llegoEntregadas.equals(""))){
    					
    					boolean entregadas = (request.getParameter("CambiarE").equals("1"))?true:false;
    					
    					Date date = new Date();
    					gd.ConvertirFecha(date);
    					
    					sa.setEntregadas(entregadas);
    					if(entregadas){
    						sa.setFotos_Seleccionadas(entregadas);
    						sa.setLista_Para_Entregar(entregadas);
    						
    						sa.setFecha_Envio_Imprimir(date);
    						sa.setFecha_Entrega(date);
    						sa.setFecha_Retiro(date);
    					}else{
    						
    						//sa.setFecha_Envio_Imprimir(null);
    						//sa.setFecha_Entrega(null);
    						sa.setFecha_Retiro(null);
    					}   
    					
    					gd.ModificarSesionAuxiliar(sa); 
    					
    	    			ArrayList<ArrayList<Object>> sesiones = null;	
			    		  
    	    		    if(sesion.getAttribute("15_Nombre")!=null){
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");
    	        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	        	    	sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");    
    	        	    }else{
    	        			sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();
    	        		}
    	    			request.setAttribute("sesiones", sesiones);
    	    			
    	    	    	rd = request.getRequestDispatcher("/visualizarsesionesestado.jsp");
    				}
    			}
    			rd.forward(request, response);
    	  }
		    
	}
       
	
	public static String Desencriptar(String textoEncriptado) throws Exception {
		 
        String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
	}
		
	public static String Encriptar(String texto) {
		 
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetSesion() {
        super();
        // TODO Auto-generated constructor stub
    }

    	
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            procesamientoPeticion(request, response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void InvalidarFiltros() throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);

		sesion.setAttribute("15_Nombre", null);
    	sesion.setAttribute("15_Apellido_Pat", null);
    	sesion.setAttribute("16_Fecha1", null);
    	sesion.setAttribute("16_Fecha2", null);
    	sesion.setAttribute("24_Id_Ticket", null);
    	sesion.setAttribute("14_Id_Canal_Venta", null);
    	
    	return ;
	}

    public String DomingoASabado (String fecha) throws java.text.ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date fecha1 = sdf.parse(fecha);
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(fecha1); // Configuramos la fecha que se recibe
    	if(calendar.get(Calendar.DAY_OF_WEEK) == 1){
    		calendar.add(Calendar.DAY_OF_YEAR, -1);  // numero de días a añadir, o restar en caso de días<0
    	}
 	    fecha = sdf.format(calendar.getTime()); 
    	return fecha;
    }    

}
