package controlador;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import conexion.JavaMail;
import conexion.SQLS_conexion;
import modelo.Campania;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Metodo_Pago;
import modelo.Reserva;
import modelo.SesionAuxiliar;
import modelo.Tipo_Sesion;
import modelo.Trabajador;
import modelo.Vendedor;

/**
 * Servlet implementation class LetReserva
 */
@WebServlet("/LetReserva")
public class LetReserva extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion;
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetReserva() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void procesamientoPeticion(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException,
           	SQLException, ParseException, java.text.ParseException {
    		
    	    String llegoSolicitud = request.getParameter("opcion");
    	    
    	    SQLS_conexion gd = new SQLS_conexion();
    	    
    	    RequestDispatcher rd = null;
    	    
    	    sesion = request.getSession();
    	    
    	    
    	    
    	    if (llegoSolicitud.equals("Reservar")) {
    	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	
    	    	
    	    	System.out.println("El usuario en la solicitud de reserva es "+ trab.getNombre() + new Date());
    	    	int llegoId = 0;	
    	    	
    	    	ArrayList<Cliente> Clientes = (ArrayList<Cliente>)gd.getClientesFiltro("","","");
    	    	int IdCliente = 0;
    	    	
    	    	Iterator<Cliente> iter2 = Clientes.iterator();
    	    	while(iter2.hasNext())
				{
					Cliente f = (Cliente)iter2.next(); 
					if(request.getParameter("15_Id_Cliente2").equals(f.getMail()+" - "+f.getNombre()+" "+f.getApellido_Pat())){
						IdCliente = f.getId_Cliente();
					}
				}    	    	
    	    	
    	    	if(IdCliente == 0){
    	    			boolean LlegoComunaYCiudad = false;
		    	    	
		    	    	Cliente cli = new Cliente();   	    	
		    	    	
		    	    	//Obtencion de parámetros de formulario
		    	    	String llegoMail = request.getParameter("15_Mail");
		    	    	
		    	    	String llegoRut = request.getParameter("15_Rut_Cliente");
		    	    	if(!llegoRut.equals("")){
		    	    		int IntllegoRut = Integer.parseInt(llegoRut);
		    	    		cli.setRut(IntllegoRut);
		    	    	}else{
		    	    		cli.setRut(-1);
		    	    	}
		    	    	String llegoNombre = request.getParameter("15_Nombre");
		    	    	String llegoApellidoPat = request.getParameter("15_Apellido_Pat");
		    	    	String llegoApellidoMat = request.getParameter("15_Apellido_Mat");
		    	    	String llegoContrasenia = request.getParameter("15_Contrasenia");
		    	    	if(llegoContrasenia.equals("")){
		    	    		if(llegoMail.length()>5){
		    	    			llegoContrasenia = llegoMail.substring(0, 4);
		    	    		}else{
		    	    			llegoContrasenia = llegoApellidoPat + llegoNombre;
		    	    		}
		    	    	}
		    	    	String llegoDireccion = request.getParameter("15_Direccion");
		    	    	int llegoComuna = Integer.parseInt(request.getParameter("06_Id_Ciudad"));
		    	    	if(llegoComuna==0){
		    	    		cli.setId_Comuna(0);
		        			cli.setId_Ciudad(0);
		    	    	}else{
		    	    		cli.setId_Comuna(llegoComuna);
		    	    		Ciudad ciu = gd.getCiudadesFiltro(llegoComuna).get(0);
		        			cli.setId_Ciudad(ciu.getId_Ciudad());
		    	    	}
		    	    	String llegoTelefono = request.getParameter("15_Telefono");
		    	    	if(!llegoTelefono.equals("")){
		    	    		llegoTelefono = llegoTelefono.replace(" ", "");
		    	    				    	    		
		    	    		int IntllegoTelefono = Integer.parseInt(llegoTelefono);
		    	    		cli.setFono(IntllegoTelefono);
		    	    	}else{
		    	    		cli.setFono(-1);
		    	    	}
		    	    	String llegoCelular = request.getParameter("15_Celular");
		    	    	if(!llegoCelular.equals("")){
		    	    		llegoCelular = llegoCelular.replace(" ", "");
		    	    		int IntllegoCelular = Integer.parseInt(llegoCelular);
		    	    		cli.setCelular(IntllegoCelular);
		    	    	}else{
		    	    		cli.setCelular(-1);
		    	    	}
		    	    	
		    	    	String llegoReclamo = request.getParameter("15_Reclamo");
		    	    	String llegoComentario = request.getParameter("15_Observacion");
		    	    	
		    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
		    			System.out.println("Nombre en LetCliente - NuevoCliente: "+ usuario.getNombre());
		
		    			cli.setNombre(llegoNombre);
		    			cli.setApellido_Pat(llegoApellidoPat);
		    			cli.setApellido_Mat(llegoApellidoMat);
		    			cli.setDireccion(llegoDireccion);
		    			
		    			cli.setConstrasenia(this.Encriptar(llegoContrasenia));
		    			
		    			cli.setMail(llegoMail);
		    			
		    			if(llegoReclamo!=null && llegoReclamo.equals("on")){
		    				cli.setReclamo(true);
		    				cli.setObservacion(llegoComentario);
		    			}else{
		    				cli.setReclamo(false);
		    				cli.setObservacion(null);
		    			}
		    			cli.setId_Estado(1);
		    			
		    			gd.IngresarCliente(cli);
		    			
		    			ArrayList<Cliente> clientes = gd.getClientesFiltro("15_Mail", llegoMail, "String");
		    	    	Cliente clienteamodificar = clientes.get(0); 
		    	    	
		    	    	llegoId = clienteamodificar.getId_Cliente();
		    	}else{
		    		 	llegoId = IdCliente;
		    	}
    	    	
    	    	String llegoDia = "";
    	    	String llegoHora = "";
    	    	
    	    	String llegoFecha = "";
    	    	Date fechaConHora = null;
    			
    	    	if( request.getParameter("16_Fecha")!= null &&
    	    		request.getParameter("16_Hora") != null &&
    	    		!(request.getParameter("16_Fecha").equals("null")) && 
    	    		!(request.getParameter("16_Hora").equals("null"))){
    	    		
    	    		llegoDia = request.getParameter("16_Fecha");
        	    	llegoHora = request.getParameter("16_Hora");
        	    	llegoFecha = llegoDia +" "+llegoHora;
        	    	
        	    	fechaConHora = gd.ConvertirFecha(llegoFecha);
    	    	}
    	    	
    	    	String llegoCampania = request.getParameter("17_Id_Campania");
    	    	String llegoCodigo = request.getParameter("16_Codigo");
    	    	int llegoCantidadPersonas =  Integer.parseInt(request.getParameter("16_Cantidad_Personas"));
    	    	String llegoValidado = request.getParameter("16_Validado");
    	    	int llegoTipoSesion = Integer.parseInt(request.getParameter("34_Id_Tipo_Sesion"));
    	    	
    	    	String llegoPreReserva = request.getParameter("16_Pre_Reserva");
    	    	String llegoUbicacion = request.getParameter("16_Ubicacion");
    	    	int llegoMetodoPago = -1;
    	    	if(!request.getParameter("37_Id_Metodo_Pago").equals("null")){
    	    		llegoMetodoPago = Integer.parseInt(request.getParameter("37_Id_Metodo_Pago"));
    	    	}
    	    	
        	    int llegoMonto = Integer.parseInt(request.getParameter("16_Monto_Pago_Adelantado").replace(".", ""));
        	    String llegoFechaAnticipo = request.getParameter("16_Fecha_Anticipo");
        	    String llegoNombreAnticipo = request.getParameter("16_Nombre_Anticipo");
        	    String llegoTipoAnticipo = request.getParameter("16_Tipo_Anticipo");
        	    
        	    if(llegoMonto <= 0){
        	    	llegoMonto = -1;
        	    }
        	    String llegoObservacion = request.getParameter("16_Observacion");
        	    
        	    
    	    	Campania camp = ((ArrayList<Campania>)(gd.getCampañasFiltro("17_ID_CAMPANIA", llegoCampania, "Int"))).get(0);
     	    	int diferencia = Diferencia(camp.getMaximo_Personas(),llegoCantidadPersonas);
    	    	
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetReserva - Reservar: "+ usuario.getNombre());
    			
    			ArrayList<Cliente> cli = gd.getClientesFiltro("15_ID_CLIENTE", Integer.toString(llegoId), "Int");
    	    	Cliente cliente = cli.get(0); 
    			    			
				Reserva res = new Reserva();
    			res.setId_Cliente(llegoId);
    			res.setId_Campania(Integer.parseInt(llegoCampania));
    			res.setId_Trabajador(usuario.getId_Trabajador());
    			res.setCantidad_Personas(llegoCantidadPersonas);
    			res.setCantidad_Adicionales(diferencia);
    			res.setCantidad_Reagendamiento(0);
    			res.setId_Tipo_Sesion(llegoTipoSesion);
    			
    			res.setId_Estado(1);
    			if(fechaConHora != null){
    				res.setFecha(fechaConHora);
    			}
    			res.setCodigo(llegoCodigo);
    			if(llegoValidado!=null && llegoValidado.equals("on")){
    				res.setValidado(true);
    			}else{
    				res.setValidado(false);
    			}
    			if(llegoPreReserva!=null && llegoPreReserva.equals("1")){
    				res.setPreReserva(true);
    			}else{
    				res.setPreReserva(false);
    			}
    			res.setVendedor(Integer.parseInt(llegoUbicacion));
    			res.setId_Metodo_Pago(llegoMetodoPago);
    			
    			if(llegoMonto>0){
    				res.setMonto_Pago_Adelantado(llegoMonto);
          	    	res.setFecha_Anticipo(llegoFechaAnticipo);
          	    	res.setNombre_Anticipo(llegoNombreAnticipo);
          	    	res.setTipo_Anticipo(llegoTipoAnticipo);
          	    }
    			res.setObservacion(llegoObservacion);
    			
    			int RespuestaDeInsercion = 0;
    			RespuestaDeInsercion = gd.IngresarReserva(res);
    			
    			if(RespuestaDeInsercion == 0){
    				String mensaje = "ERROR! Intente ingresar la reserva nuevamente (Posible error: Fecha y hora duplicada)";
					
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "danger");
					    					
    				rd = request.getRequestDispatcher("/indextrabajador.jsp");
        	    	rd.forward(request, response);
        	    	
    			}else{
    			    			
	    			JavaMail mail = new JavaMail();
	    			
	    			String correo = cliente.getMail();
	    			String correo2 = usuario.getEmail();
	    			String correo3 = "salomon.nilo@advancing.cl"; 
	    			
	    			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
	    			SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
	    			
	    			String fecha2 = "";
	    			String hora = "";
	    			String horario = "";
	    			
	    			if(res.getFecha() != null){
	    				fecha2 = DomingoASabado(sdf2.format(res.getFecha()));	
	        			hora = sdf3.format(res.getFecha());	
	        			horario = "es <strong>"+fecha2+ "</strong> a las <strong>"+hora+"</strong> horas ";
	    			}else{
	    				horario = "está pendiente, la agenda es ";
	    			}
	    	    	String adicional = "";
	    	    	if(res.getCantidad_Adicionales()>0){
	    	    		adicional = "<br><br> Por ende deberás pagar <strong>"+res.getCantidad_Adicionales()+"</strong> persona(s) adicional(es).";
	    	    	}
	    	    	
	    	    	String montoanticipo = "";
	    			if(res.getMonto_Pago_Adelantado()>0 && camp.getAbono()>0){
	    				
	    				montoanticipo = " Se ha confirmado su sesión fotográfica con el abono recibido de $"+NumberFormat.getIntegerInstance().format(res.getMonto_Pago_Adelantado());    				
	    			}
	    	    	String PDcampania = "";
	    	    	if(!camp.getDescripcion().equals("")){
	    	    		PDcampania = "<br><br> <em>"+camp.getDescripcion()+"</em>";
	    	    	}
	    	    	
	    	    	String MensajeMail = (String)(gd.getMensajeMail());
	    	    	if(!MensajeMail.equals("")){
	    	    		MensajeMail = "<br><br>" + MensajeMail ; 
	    	    	}

	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(res.getVendedor()), "Int").get(0);
	    			
	    	    	String correoEnvia = Vend.getMail();
	    	    	String nombreEnvia = Vend.getVendedor();
	    	    	String logoIcono = Vend.getLogo_Icono();
	    	    	String pagina = Vend.getWeb();
	    	    	String direccion = Vend.getDireccion();
	    	    	String clave = Vend.getMail_PW();
	    	    	/*
	    	    	if(res.getVendedor().equals("Genesis")){
	    	    		correoEnvia = "contacto@genesisestudio.cl";
	    	    		nombreEnvia = "Genesis Estudio";
	    	    		logoIcono = "LogoLetrasGenesis";
	    	    		pagina = "www.GenesisEstudio.cl";
	    	    		direccion = direcciong;
	    	    	}
	    	    	if(res.getVendedor().equals("Expressiones")){
	    	    		correoEnvia = "contacto@fotoexpressiones.com";
	    	    		nombreEnvia = "Foto Expressiones";
	    	    		logoIcono = "LogoLetrasExpressiones";
	    	    		pagina = "www.FotoExpressiones.com";
	    	    		direccion = direccione;
	    	    	}
	    	    	if(res.getVendedor().equals("Genesis2")){
	    	    		correoEnvia = "contacto@genesisestudio.cl";
	    	    		nombreEnvia = "Genesis Estudio";
	    	    		logoIcono = "LogoLetrasGenesis";
	    	    		pagina = "www.GenesisEstudio.cl";
	    	    		direccion = direcciong2;
	    	    	}*/
	    	    	    	    	
	    	    	String MensajeDeCorreo;
	    	    	String AsuntoDeCorreo; 
	    	    	
	    	    	if(res.isPreReserva()){
	    	    		String MetodoPago = (String)(gd.getMetodoPago(res.getId_Metodo_Pago()));
	        	    	if(!MetodoPago.equals("")){
	        	    		MetodoPago = "<br><br>Los datos de transferencia son:<br>" + MetodoPago ; 
	        	    	}
	        	    	AsuntoDeCorreo = "Comprobante de pre-reserva";
	    	    		MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
	        					+ "Se ha pre-reservado una sesión fotográfica. <br><br>"
	        					+ "La fecha "
	        					+ horario 
	        					+ "para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
	        					+ "persona(s)."+adicional
	        					+ PDcampania
	        					+ "<br><br><span style='font-size:12.0pt;color:red'> Para confirmar la hora, debes transferir o depositar $"
	        					+ NumberFormat.getIntegerInstance().format(camp.getAbono())
	        					+", durante las próximas 24 horas, sino se anulará la pre-reserva. </span>"
	        					+ MetodoPago
	        					+ "La dirección es: "+direccion
	        					+ "<br>Nuestra página es: "+pagina
	        					+ MensajeMail
	        					+ "<br><br> No faltes, ¡Te esperamos!"
	        					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
	    	    	}else{
	    	    		AsuntoDeCorreo = "Comprobante de reserva";
	    	    		MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
	    					+ "Se ha agendado una sesión fotográfica. "+montoanticipo
	    					+ " <br><br>"
	    					+ "La fecha es <strong>"+fecha2+ "</strong> a las <strong>"+hora+
	    					"</strong> horas para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
	    					+ "persona(s)."+adicional
	    					+ "<br><br> No faltes, ¡Te esperamos!"
	    					+ PDcampania
	    					+ MensajeMail
	    					+ "La dirección es: "+direccion
	    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
	    	    		
	    	    	}
	    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave);
	    			
	    	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
	    	    	rd.forward(request, response);
    			}
    	    }
    	    
    	    if (llegoSolicitud.equals("CambiarReserva")) {
    	      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	  
    	    	
    	    	String llegoReservaa = (request.getParameter("16_Id_Reserva"));
    	    	System.out.println("Id de reserva: "+llegoReservaa);
    	    	
    	    	ArrayList<Reserva> res = gd.getReservasFiltro("16_ID_RESERVA", llegoReservaa, "Int");
    	    	Reserva reserv = res.get(0); 
    	    	
    	    	//Obtención del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    		
    			//Opciones - Modificar o eliminar
    			String llegoModificar = request.getParameter("ModificarReserva");
    			String llegoEliminar = request.getParameter("EliminarReserva");
    			String llegoReenviar = request.getParameter("ReenviarMail");
    			String llegoAsignar = request.getParameter("AsignarMonto");
    			String llegoAgregarSesion = request.getParameter("AgregarSesion");
    			String llegoRecordar = request.getParameter("RecordarReserva");
      			
    			if((llegoModificar!=null)||(llegoEliminar!=null)||(llegoReenviar!=null)||(llegoAsignar!=null)||(llegoAgregarSesion!=null)||(llegoRecordar!=null)){
    				if((llegoModificar!=null)&&(!(llegoModificar.equals("")))){
    					//Caso de Modificar una reserva	
    					request.setAttribute("reserva", reserv);
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");
    					
    					ArrayList<ArrayList<String>> Fechas = (ArrayList<ArrayList<String>>)gd.getAgenda();
    					request.setAttribute("reservas", Fechas);
    			    	
    			    	ArrayList<Campania> Campanias = (ArrayList<Campania>)gd.getCampañasVigentes();	
    					request.setAttribute("campanias", Campanias);
    					
    					ArrayList<Vendedor> Vendedores = (ArrayList<Vendedor>)gd.getVendedoresSinId("", "", "");
    					request.setAttribute("vendedores", Vendedores);
    					
    					ArrayList<Campania> TodasLasCampanias = (ArrayList<Campania>)gd.getCampañasFiltro("17_Id_Campania", Integer.toString(reserv.getId_Campania()), "Int");
    					//ArrayList<Campania> TodasLasCampanias = (ArrayList<Campania>)gd.getCampañas();
    					request.setAttribute("campaniastodas", TodasLasCampanias);
    					
    					request.setAttribute("canalesventasarray", (ArrayList<ArrayList<String>>)(gd.getCanalesVentasArray()));
    					
    					ArrayList<Cliente> Clientes = (ArrayList<Cliente>)gd.getClientesFiltro("","","");
    					request.setAttribute("clientes", Clientes);
    					
    					request.setAttribute("tipossesiones", (ArrayList<Tipo_Sesion>) (gd.getTiposSesiones()));
    					
    					ArrayList<Metodo_Pago> Metodos = (ArrayList<Metodo_Pago>)gd.getMetodoPagosFiltroSinHTML("","","");
    					request.setAttribute("metodo_pago", Metodos);
    					
    					
    					rd = request.getRequestDispatcher("/modificarreserva.jsp");
    				}
    				if((llegoEliminar!=null)&&(!llegoEliminar.equals(""))){
    					//Caso de Eliminar una reserva	
    					int corroboracion = gd.EliminarReserva(reserv.getId_Reserva());
    					
    					if(corroboracion>0){
    						String mensaje = "Reserva eliminada correctamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "success");
    						    						
    						ArrayList<ArrayList<Object>> reservas = null;
    						
    						if(sesion.getAttribute("15_Nombre")!=null){
    				    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");	
    			    	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    				    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");	
    			    	    }else if(sesion.getAttribute("16_Fecha1")!=null || sesion.getAttribute("16_Fecha2")!=null){
    				    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechas("", "", "", (String)sesion.getAttribute("16_Fecha1"), (String)sesion.getAttribute("16_Fecha2"));	
    			    		}else {
    				    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
    			    		}
    						
    						request.setAttribute("reservas", reservas);

    				    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
    					}else{
    						String mensaje = "ERROR! Intente eliminar nuevamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "danger");
    						
    						ArrayList<ArrayList<Object>> reservas = null;
    						
    						if(sesion.getAttribute("15_Nombre")!=null){
    				    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");	
    			    	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    				    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");	
    			    	    }else if(sesion.getAttribute("16_Fecha1")!=null || sesion.getAttribute("16_Fecha2")!=null){
    				    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechas("", "", "", (String)sesion.getAttribute("16_Fecha1"), (String)sesion.getAttribute("16_Fecha2"));	
    			    		}else {
    				    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
    			    		}
    						
    						request.setAttribute("reservas", reservas);
    						    						
    				    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
    					}
    				}
    				if((llegoReenviar!=null)&&(!llegoReenviar.equals(""))){
    					Campania camp = ((ArrayList<Campania>)(gd.getCampañasFiltro("17_ID_CAMPANIA", Integer.toString(reserv.getId_Campania()), "Int"))).get(0);
    	    	      	    			
    	    			ArrayList<Cliente> cli = gd.getClientesFiltro("15_ID_CLIENTE", Integer.toString(reserv.getId_Cliente()), "Int");
    	    	    	Cliente cliente = cli.get(0); 
    					
    					JavaMail mail = new JavaMail();
    	    			
    	    			String correo = cliente.getMail();
    	    			String correo2 = usuario.getEmail();
    	    			String correo3 = "salomon.nilo@advancing.cl"; 
    	    			
    	    			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    	    			SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
    	    			
    	    			String fecha2 ="";
    	    			String hora ="";
    	    			String horario = "";
    	    			
    	    			if(reserv.getFecha() != null){
    	    				fecha2 = DomingoASabado(sdf2.format(reserv.getFecha()));	
    	        			hora = sdf3.format(reserv.getFecha());	
    	        			horario = "es <strong>"+fecha2+ "</strong> a las <strong>"+hora+"</strong> horas ";
    	    			}else{
    	    				horario = "está pendiente, la agenda es ";
    	    			}
    	    			
    	    	    	String adicional = "";
    	    	    	if(reserv.getCantidad_Adicionales()>0){
    	    	    		adicional = "<br><br> Por ende deberás pagar <strong>"+reserv.getCantidad_Adicionales()+"</strong> persona(s) adicional(es).";
    	    	    	}
    	    			
    	    	    	String PDcampania = "";
    	    	    	if(!camp.getDescripcion().equals("")){
    	    	    		PDcampania = "<br><br> <em>"+camp.getDescripcion()+"</em>";
    	    	    	}
    	    	    	
    	    	    	String MensajeMail = (String)(gd.getMensajeMail());
    	    	    	if(!MensajeMail.equals("")){
    	    	    		MensajeMail = "<br><br>" + MensajeMail ; 
    	    	    	}

    	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
    	    			
    	    	    	String correoEnvia = Vend.getMail();
    	    	    	String nombreEnvia = Vend.getVendedor();
    	    	    	String logoIcono = Vend.getLogo_Icono();
    	    	    	String pagina = Vend.getWeb();
    	    	    	String direccion = Vend.getDireccion();
    	    	    	String clave = Vend.getMail_PW();
    	    	    	
    	    	    	String AsuntoDeCorreo = "Comprobante de reserva";
    	    	    	
    	    	    	
    	    	    	
    	    			String MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
    	    					+ "Se ha agendado una sesión fotográfica. <br><br>"
    	    					+ horario
    	    					+ "para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
    	    					+ "persona(s)."+adicional
    	    					+ "<br><br> No faltes, ¡Te esperamos!"
    	    					+ PDcampania
    	    					+ MensajeMail
    	    					+ "La dirección es: "+direccion
    	    					+ "<br>Nuestra página es: "+pagina
    	    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
    	    			

    	    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave);
    	    			    	
    	    			ArrayList<ArrayList<Object>> reservas = null;
    	    			
    	    			if(sesion.getAttribute("15_Nombre")!=null){
    	    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");	
    	        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");	
    	        	    }else if(sesion.getAttribute("16_Fecha1")!=null || sesion.getAttribute("16_Fecha2")!=null){
    	    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechas("", "", "", (String)sesion.getAttribute("16_Fecha1"), (String)sesion.getAttribute("16_Fecha2"));	
        	    		}else {
    	    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
        	    		}
    	    			
    	    			request.setAttribute("reservas", reservas);
    	    			
    	    	    	rd = request.getRequestDispatcher("/visualizarreservasc.jsp");
    				}
    				if((llegoRecordar!=null)&&(!llegoRecordar.equals(""))){
    					Campania camp = ((ArrayList<Campania>)(gd.getCampañasFiltro("17_ID_CAMPANIA", Integer.toString(reserv.getId_Campania()), "Int"))).get(0);
    	    	      	    			
    	    			ArrayList<Cliente> cli = gd.getClientesFiltro("15_ID_CLIENTE", Integer.toString(reserv.getId_Cliente()), "Int");
    	    	    	Cliente cliente = cli.get(0); 
    					
    					JavaMail mail = new JavaMail();
    	    			
    	    			String correo = cliente.getMail();
    	    			String correo2 = usuario.getEmail();
    	    			String correo3 = "salomon.nilo@advancing.cl"; 
    	    			
    	    			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    	    			SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
    	    			
    	    			String fecha2 ="";
    	    			String hora ="";
    	    			String horario = "";
    	    			
    	    			if(reserv.getFecha() != null){
    	    				fecha2 = DomingoASabado(sdf2.format(reserv.getFecha()));	
    	        			hora = sdf3.format(reserv.getFecha());	
    	        			horario = "es <strong>"+fecha2+ "</strong> a las <strong>"+hora+"</strong> horas ";
    	    			}else{
    	    				horario = "está pendiente, la agenda es ";
    	    			}
    	    			
    	    			
    	    	    	String adicional = "";
    	    	    	if(reserv.getCantidad_Adicionales()>0){
    	    	    		adicional = "<br><br> Por ende deberás pagar <strong>"+reserv.getCantidad_Adicionales()+"</strong> persona(s) adicional(es).";
    	    	    	}
    	    			
    	    	    	String PDcampania = "";
    	    	    	if(!camp.getDescripcion().equals("")){
    	    	    		PDcampania = "<br><br> <em>"+camp.getDescripcion()+"</em>";
    	    	    	}
    	    	    	
    	    	    	String PDcampaniaAdicional = "";
    	    	    	if(!camp.getDescripcion_Adicional().equals("")){
    	    	    		PDcampaniaAdicional = "<br><br> <em>"+camp.getDescripcion_Adicional()+"</em>";
    	    	    	}
    	    	    	
    	    	    	String MensajeMail = (String)(gd.getMensajeMail());
    	    	    	if(!MensajeMail.equals("")){
    	    	    		MensajeMail = "<br><br>" + MensajeMail ; 
    	    	    	}

    	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
    	    			
    	    	    	String correoEnvia = Vend.getMail();
    	    	    	String nombreEnvia = Vend.getVendedor();
    	    	    	String logoIcono = Vend.getLogo_Icono();
    	    	    	String pagina = Vend.getWeb();
    	    	    	String direccion = Vend.getDireccion();
    	    	    	String clave = Vend.getMail_PW();
    	    	    	
    	    	    	String AsuntoDeCorreo = "Recordatorio de reserva";
    	    	    	/*
    	    	    	if(reserv.getVendedor().equals("Genesis")){
    	    	    		correoEnvia = "contacto@genesisestudio.cl";
    	    	    		nombreEnvia = "Genesis Estudio";
    	    	    		logoIcono = "LogoLetrasGenesis";
    	    	    		pagina = "www.GenesisEstudio.cl";
    	    	    		direccion = direcciong;
    	    	    	}
    	    	    	if(reserv.getVendedor().equals("Expressiones")){
    	    	    		correoEnvia = "contacto@fotoexpressiones.com";
    	    	    		nombreEnvia = "Foto Expressiones";
    	    	    		logoIcono = "LogoLetrasExpressiones";
    	    	    		pagina = "www.FotoExpressiones.com";
    	    	    		direccion = direccione;
    	    	    	}
    	    	    	if(reserv.getVendedor().equals("Genesis2")){
    	    	    		correoEnvia = "contacto@genesisestudio.cl";
    	    	    		nombreEnvia = "Genesis Estudio";
    	    	    		logoIcono = "LogoLetrasGenesis";
    	    	    		pagina = "www.GenesisEstudio.cl";
    	    	    		direccion = direcciong2;
    	    	    	}
    	    	    	*/
    	    	    	String MensajeDeCorreo = "";
    	    	    	
    	    	    	if(reserv.isPreReserva()){
    	    	    		String MetodoPago = (String)(gd.getMetodoPago(reserv.getId_Metodo_Pago()));
    	        	    	if(!MetodoPago.equals("")){
    	        	    		MetodoPago = "<br><br>Los datos de transferencia son:<br>" + MetodoPago ; 
    	        	    	}
    	        	    	AsuntoDeCorreo = "Comprobante de pre-reserva";
    	    	    		MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
    	        					+ "Le recordamos que usted ha pre-reservado una sesión fotográfica. <br><br>"
    	        					+ "La fecha " 
    	        					+ horario 
    	        					+ "para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
    	        					+ "persona(s)."+adicional
    	        					+ PDcampania
    	        					+ "<br><br><span style='font-size:12.0pt;color:red'> Para confirmar la hora, debes transferir o depositar $"
    	        					+NumberFormat.getIntegerInstance().format(camp.getAbono())
    	        					+", durante las próximas horas, sino se anulará la pre-reserva. </span>"
    	        					+ MetodoPago
    	        					+ "La dirección es: "+direccion
    	        					+ "<br>Nuestra página es: "+pagina
    	        					+ MensajeMail
    	        					
    	        					+ "<br><br> No faltes, ¡Te esperamos!"
    	        					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
    	    	    	}else{
    	    	    		MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
        	    					+ "Le recordamos que usted posee agendada una sesión fotográfica. <br><br>"
        	    					+ "La fecha agendada es para el <strong>"+fecha2+ "</strong> a las <strong>"+hora+
        	    					"</strong> horas para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
        	    					+ "persona(s)."+adicional
        	    					+ "<br><br> No faltes, ¡Te esperamos!"
        	    					+ PDcampania
        	    					+ PDcampaniaAdicional
        	    					+ MensajeMail
        	    					+ "La dirección es: "+direccion
        	    					+ "<br>Nuestra página es: "+pagina
        	    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
        	    			
    	    	    	}
    	    	    	
    	    			
    	    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave);
   	    			    	        	    			
    	    			ArrayList<ArrayList<Object>> reservas = null;
    	    			
    	    			if(sesion.getAttribute("15_Nombre")!=null){
    	    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");	
    	        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");	
    	        	    }else if(sesion.getAttribute("16_Fecha1")!=null || sesion.getAttribute("16_Fecha2")!=null){
    	    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechas("", "", "", (String)sesion.getAttribute("16_Fecha1"), (String)sesion.getAttribute("16_Fecha2"));	
        	    		}else {
    	    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
        	    		}
    	    			
    	    			request.setAttribute("reservas", reservas);
    	    			
    	    	    	rd = request.getRequestDispatcher("/visualizarreservasrecordatorio.jsp");
    				}
    				if((llegoAsignar!=null)&&(!llegoAsignar.equals(""))){
    					//Caso de asignar monto a una reserva
    					request.setAttribute("reserva", reserv);
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");
    					
    					ArrayList<ArrayList<String>> Fechas = (ArrayList<ArrayList<String>>)gd.getAgenda();
    					request.setAttribute("reservas", Fechas);
    			    	
    			    	ArrayList<Campania> Campanias = (ArrayList<Campania>)gd.getCampañas();	
    					request.setAttribute("campanias", Campanias);
    					
    					ArrayList<Cliente> Clientes = (ArrayList<Cliente>)gd.getClientesFiltro("","","");
    					request.setAttribute("clientes", Clientes);
    					
    					rd = request.getRequestDispatcher("/asignaranticipo.jsp");
    				}
    				if((llegoAgregarSesion!=null)&&(!llegoAgregarSesion.equals(""))){
    					//Caso de asignar datos a una sesion	
    					
    					ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("16_ID_RESERVA", Integer.toString(reserv.getId_Reserva()), "Int");
    					request.setAttribute("reserva", reservas.get(0));
    					
    					SesionAuxiliar sa = (SesionAuxiliar) (gd.getSesionAuxiliar(reserv.getId_Reserva()));
    					request.setAttribute("sesionauxiliar", sa); 
    					
    					Campania camp = (Campania)(gd.getCampañasFiltro("17_Id_Campania", Integer.toString(reserv.getId_Campania()), "Int")).get(0);
    					request.setAttribute("campania", camp);
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");
    					
    					rd = request.getRequestDispatcher("/nuevosesion.jsp");
    				}
    			}
    			rd.forward(request, response);
    	  }
    	    
    	  if (llegoSolicitud.equals("ModificarReserva")) {
  	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
  	    	System.out.println("El usuario en la solicitud de reserva es "+ trab.getNombre());
  	    	
  	    	String llegoFecha;
  	    	if((request.getParameter("16_Fecha")==null)||(request.getParameter("16_Hora")==null)||(request.getParameter("16_Fecha").equals(""))||(request.getParameter("16_Hora").equals(""))){
  	    		llegoFecha = "";
  	    	}else{
  	    		String llegoDia = request.getParameter("16_Fecha");
  	    		String llegoHora = request.getParameter("16_Hora");
  	    		llegoFecha = llegoDia +" "+llegoHora;
  	    	}
	    	
	    	int llegoCobro = Integer.parseInt(request.getParameter("Cobro"));
	    	int llegoReagendamiento = 0;
	    	if(request.getParameter("16_Cantidad_Reagendamiento_Modificada")!=null){
	    		llegoReagendamiento = Integer.parseInt(request.getParameter("16_Cantidad_Reagendamiento_Modificada"));
	    	}
  	    	int llegoId = Integer.parseInt(request.getParameter("15_Id_Cliente"));
  	    	String llegoCampania = request.getParameter("17_Id_Campania");
  	    	String llegoCodigo = request.getParameter("16_Codigo");
  	    	int llegoCantidadPersonas =  Integer.parseInt(request.getParameter("16_Cantidad_Personas"));
  	    	int llegoIdReserva = Integer.parseInt(request.getParameter("16_Id_Reserva"));
  	    	int CantReagendamiento = Integer.parseInt(request.getParameter("16_Cantidad_Reagendamiento"));
  	    	String llegoValidado = request.getParameter("16_Validado");
  	    	int llegoTipoSesion = Integer.parseInt(request.getParameter("34_Id_Tipo_Sesion")); 
  	    	
  	    	String llegoPreReserva = request.getParameter("16_Pre_Reserva");
	    	String llegoUbicacion = request.getParameter("16_Ubicacion");
	    	int llegoMetodoPago = Integer.parseInt(request.getParameter("37_Id_Metodo_Pago"));	    	
	    	String llegoPreReservaPrevio = request.getParameter("16_Pre_Reserva_Previo");
	    	
	    	String llegoObservacion = request.getParameter("16_Observacion");
	    	
  	    	ArrayList<Reserva> reservaamodificar = gd.getReservasFiltro("16_ID_RESERVA", Integer.toString(llegoIdReserva), "Int");
	    	Reserva res = reservaamodificar.get(0); 
  	    	
  	    	Campania camp = ((ArrayList<Campania>)(gd.getCampañasFiltro("17_ID_CAMPANIA", llegoCampania, "Int"))).get(0);
   	    	int diferencia = Diferencia(camp.getMaximo_Personas(),llegoCantidadPersonas);
  	    	
  	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
  			System.out.println("Nombre en LetReserva - Modificar: "+ usuario.getNombre());
  			
  			ArrayList<Cliente> cli = gd.getClientesFiltro("15_ID_CLIENTE", Integer.toString(llegoId), "Int");
	    	Cliente cliente = cli.get(0); 
  				    		    	
  			Date fechaConHora = null;
  			  			
  			if(!llegoFecha.equals("")){
  				fechaConHora = gd.ConvertirFecha(llegoFecha);
  				res.setId_Estado(1);
  			}else{
  				res.setId_Estado(3);
  			}
  			  			
  			if((res.getFecha()!=null)&&(!res.getFecha().equals(fechaConHora)&&(llegoCobro==1))){
  					CantReagendamiento++;
  			}
  			if((res.getFecha()!=null)&&(llegoCobro==-1)){
  				CantReagendamiento = llegoReagendamiento;
  			}  			
  			
			res.setId_Reserva(llegoIdReserva);
  			res.setId_Cliente(llegoId);
  			res.setId_Campania(Integer.parseInt(llegoCampania));
  			res.setId_Trabajador(usuario.getId_Trabajador());
  			res.setCantidad_Personas(llegoCantidadPersonas);
  			res.setCantidad_Adicionales(diferencia);
  			res.setCantidad_Reagendamiento(CantReagendamiento);
  			res.setId_Tipo_Sesion(llegoTipoSesion);
  			
  			res.setFecha(fechaConHora);
  			res.setCodigo(llegoCodigo);
  			if(llegoValidado!=null && llegoValidado.equals("on")){
				res.setValidado(true);
			}else{
				res.setValidado(false);
			}
  			if(llegoPreReserva!=null && llegoPreReserva.equals("1")){
				res.setPreReserva(true);
				res.setId_Metodo_Pago(llegoMetodoPago);
			}else{
				res.setPreReserva(false);
				res.setId_Metodo_Pago(0);
			}
			res.setVendedor(Integer.parseInt(llegoUbicacion));
			res.setObservacion(llegoObservacion);
  			
  			gd.ActualizarReserva(res);

  			//Envío de correo electrónico
  			JavaMail mail = new JavaMail();
			String correo = cliente.getMail();
			String correo2 = usuario.getEmail();
			String correo3 = "salomon.nilo@advancing.cl";
			String MensajeDeCorreo = "";

			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(res.getVendedor()), "Int").get(0);
			
	    	String correoEnvia = Vend.getMail();
	    	String nombreEnvia = Vend.getVendedor();
	    	String logoIcono = Vend.getLogo_Icono();
	    	String pagina = Vend.getWeb();
	    	String direccion = Vend.getDireccion();
	    	String clave = Vend.getMail_PW();
	    	
	    	String AsuntoDeCorreo = "Confirmación de reserva";
	    				
			if(fechaConHora!=null){
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
				String fecha2 = DomingoASabado(sdf2.format(res.getFecha()));
				SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
				String hora = sdf3.format(res.getFecha());
		    	String adicional = "";
		    	String reagenda = "";
		    	if(res.getCantidad_Reagendamiento()>=1 && camp.getPrecio_Reagendamiento()>0){
		    		int PrecioReagenda = res.getCantidad_Reagendamiento() * camp.getPrecio_Reagendamiento();
		    		String Precio = NumberFormat.getIntegerInstance().format(PrecioReagenda);
		    		reagenda = "El monto por el reagendamiento corresponde a <strong> "+ Precio +"</strong>.<br><br>";
		    	}
		    	if(res.getCantidad_Adicionales()>0){
		    		String PrecioAdicional = NumberFormat.getIntegerInstance().format(res.getCantidad_Adicionales());
		    		adicional = "<br><br> Por ende deberás pagar <strong>"+PrecioAdicional+"</strong> persona(s) adicional(es).";
		    	}
		    	String PDcampania = "";
		    	if(!camp.getDescripcion().equals("")){
		    		PDcampania = "<br><br> <em>"+camp.getDescripcion()+"</em>";
		    	}
		    	
		    	String MensajeMail = (String)(gd.getMensajeMail());
    	    	if(!MensajeMail.equals("")){
    	    		MensajeMail = "<br><br>" + MensajeMail ; 
    	    	}
    	    	    	    	
    	    	if(llegoPreReservaPrevio.equals("checked") && llegoPreReserva!=null && llegoPreReserva.equals("0")){
    	    		MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
    						+ "Se ha <strong>confirmado</strong> una sesión fotográfica. <br><br>"
    	    				//+ reagenda 
    						+ "La fecha es <strong>"+fecha2+ "</strong> a las <strong>"+hora+
    						"</strong> horas para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
    						+ "persona(s)."+adicional
    						+ "<br><br> No faltes, ¡Te esperamos!"
    						+ PDcampania
    						+ MensajeMail
    						+ "La dirección es: "+direccion
    						+ "<br>Nuestra página es: "+pagina						
    						+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
        	    
    	    	}else{
					MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
							+ "Se ha <strong>reagendado</strong> una sesión fotográfica. <br><br>"
							+ reagenda
							+ "La fecha es <strong>"+fecha2+ "</strong> a las <strong>"+hora+
							"</strong> horas para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
							+ "persona(s)."+adicional
							+ "<br><br> No faltes, ¡Te esperamos!"
							+ PDcampania
							+ MensajeMail
							+ "La dirección es: "+direccion
							+ "<br>Nuestra página es: "+pagina						
							+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
    	    	}
    	    }else{
    	    	MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
						+ "Usted ha dejado <strong>pendiente</strong> su sesión fotográfica. <br><br>"
						+ "Por lo tanto, quedamos atentos a su confirmación, ¡Te esperamos!"
						+ "<br>Para más información visite nuestra página: "+pagina
						+ "<br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
    	    }
			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave);
		    	 
  			String mensaje = "Reserva modificada correctamente";
			
			request.setAttribute("mensaje", mensaje);
			request.setAttribute("tipomensaje", "success");
						
			ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
			request.setAttribute("reservas", reservas);

	    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
	    	rd.forward(request, response);
    	  }
    	  
    	  if (llegoSolicitud.equals("Validar")) {
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de reserva es "+ trab.getNombre());
    	    	
    	    	int llegoIdReserva = Integer.parseInt(request.getParameter("16_Id_Reserva"));
    	    	int result = gd.ValidarReserva(llegoIdReserva);
    	    	
    	    	if(result>0){    				
    				String mensaje = "Reserva validada correctamente";
    	  			
    	  			request.setAttribute("mensaje", mensaje);
    	  			request.setAttribute("tipomensaje", "success");    		
    	    	}else{
    	    		String mensaje = "La reserva NO se validó correctamente, intente nuevamente";
    	  			
    	  			request.setAttribute("mensaje", mensaje);
    	  			request.setAttribute("tipomensaje", "danger");  	    		
    	    	}
    	    	
    	    	ArrayList<ArrayList<Object>> reservas = null;
    			
    			if(sesion.getAttribute("15_Nombre")!=null){
    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");	
        	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");	
        	    }else {
    	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
	    		}    			
    			request.setAttribute("reservas", reservas);
				
		    	rd = request.getRequestDispatcher("/validarreservas.jsp");
		    	rd.forward(request, response); 
      	  }
    	  
    	  if (llegoSolicitud.equals("FiltroFechas")) {
  	    	
  	    	Trabajador usuario =  null;
  	    	usuario = (Trabajador) sesion.getAttribute("usuario");
  	    	//request.setAttribute("usuario", usuario);
  			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
  			
  			String Fecha1 = "";
  			String Fecha2 = "";
  			Fecha1 = request.getParameter("Inicio");
  			Fecha2 = request.getParameter("Fin");
  			  			
  			sesion.setAttribute("16_Fecha1", Fecha1);
	    	sesion.setAttribute("16_Fecha2", Fecha2);
  			
  	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechas("", "", "", Fecha1, Fecha2);	
  			request.setAttribute("reservas", reservas);
  			
  	    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
  	    	rd.forward(request, response);
  	    }
    	  
    	  if (llegoSolicitud.equals("FiltroFechasAnticipo")) {
    	    	
    	    	Trabajador usuario =  null;
    	    	usuario = (Trabajador) sesion.getAttribute("usuario");
    	    	//request.setAttribute("usuario", usuario);
    			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
    			
    			this.InvalidarFiltros();
    			
    			String Fecha1 = "";
    			String Fecha2 = "";
    			Fecha1 = request.getParameter("Inicio");
    			Fecha2 = request.getParameter("Fin");
    		
    			sesion.setAttribute("16_Fecha1", Fecha1);
    	    	sesion.setAttribute("16_Fecha2", Fecha2);
    			    			
    	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechasAnticipo("", "", "", Fecha1, Fecha2);	
    			request.setAttribute("reservas", reservas);
    			
    	    	rd = request.getRequestDispatcher("/visualizarreservasanticipo.jsp");
    	    	rd.forward(request, response);
    	    }
    	  
    	  if (llegoSolicitud.equals("FiltroCliente")) {
    	    	
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
    			    			
    			ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike(columna, request.getParameter(columna), "String");	
    	    	request.setAttribute("reservas", reservas);
    			
    	    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
    	    	rd.forward(request, response);
    	    }
    	  
    	  if (llegoSolicitud.equals("FiltroPreReserva")){
  	    	
  	    	Trabajador usuario =  null;
  	    	usuario = (Trabajador) sesion.getAttribute("usuario");
  	    	//request.setAttribute("usuario", usuario);
  			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
  			  			  			  			
  			ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("16_Pre_Reserva", "1", "Int");	
  	    	request.setAttribute("reservas", reservas);
  			
  	    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
  	    	rd.forward(request, response);
  	    }
    	  
    	  if (llegoSolicitud.equals("FiltroClienteAnticipo")) {
  	    	
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
  			  			
  			ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLikeAnticipo(columna, request.getParameter(columna), "String");	
  	    	request.setAttribute("reservas", reservas);
  			
  	    	rd = request.getRequestDispatcher("/visualizarreservasanticipo.jsp");
  	    	rd.forward(request, response);
  	    }
    	  
    	  if (llegoSolicitud.equals("FiltroClienteValidar")) {
  	    	
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
  			  			
  			ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike(columna, request.getParameter(columna), "String");	
  	    	request.setAttribute("reservas", reservas);
  			
  	    	rd = request.getRequestDispatcher("/validarreservas.jsp");
  	    	rd.forward(request, response);
  	    }
    	  
    	  if (llegoSolicitud.equals("FiltroClienteRecordatorio")) {
  	    	
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
  			  			
  			ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike(columna, request.getParameter(columna), "String");	
  	    	request.setAttribute("reservas", reservas);
  						
	    	rd = request.getRequestDispatcher("/visualizarreservasrecordatorio.jsp");
  	    	rd.forward(request, response);
  	    }
    	  
    	  if (llegoSolicitud.equals("FiltroClienteC")) {
    	    	
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
    			    			
    			ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike(columna, request.getParameter(columna), "String");	
    	    	request.setAttribute("reservas", reservas);
    						
    	    	rd = request.getRequestDispatcher("/visualizarreservasc.jsp");
    	    	rd.forward(request, response);
    	    }
    	  
    	  if (llegoSolicitud.equals("FiltroClienteNuevoSesiones")) {
    	    	
    	    	Trabajador usuario =  null;
    	    	usuario = (Trabajador) sesion.getAttribute("usuario");
    	    	//request.setAttribute("usuario", usuario);
    			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
    			
    			this.InvalidarFiltros();
    			
    			String columna = "";
    			
    			String parametro = "";
    			
    			if(request.getParameter("15_Nombre")!=null){
    				columna = "15_Nombre";
    	  			parametro = request.getParameter(columna);
    	  			sesion.setAttribute("15_Nombre", request.getParameter("15_Nombre"));
    			}
    			if(request.getParameter("15_Apellido_Pat")!=null){
    				columna = "15_Apellido_Pat";
    	  			parametro = request.getParameter(columna);
    	  			sesion.setAttribute("15_Nombre", request.getParameter("15_Apellido_Pat"));
    			}
    			if(request.getParameter("24_Id_Ticket")!=null){
    				columna = "24_Id_Ticket";

    	  			parametro = request.getParameter(columna);
    	  			sesion.setAttribute("15_Nombre", request.getParameter("24_Id_Ticket"));
    	  			    				
    				if(parametro.equals("0") || parametro.equals("N/A")){
    					parametro = "-1";
    				}
    			}
    	  			
    			ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getNoPreReservasSinIdLike(columna, parametro, "String");	
    	    	request.setAttribute("reservas", reservas);
    						
    	    	rd = request.getRequestDispatcher("/nuevosesiones.jsp");
    	    	rd.forward(request, response);
    	    }

    	  
    	  if (llegoSolicitud.equals("FiltroFechasRecordatorio")) {
    	    	
    		  Trabajador usuario =  null;
    	    	usuario = (Trabajador) sesion.getAttribute("usuario");
    	    	//request.setAttribute("usuario", usuario);
    			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
    			
    			this.InvalidarFiltros();
    			
    			String Fecha1 = "";
    			String Fecha2 = "";
    			Fecha1 = request.getParameter("Inicio");
    			Fecha2 = request.getParameter("Fin");
    			
    			sesion.setAttribute("16_Fecha1", Fecha1);
    	    	sesion.setAttribute("16_Fecha2", Fecha2);
    			
    	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechas("", "", "", Fecha1, Fecha2);	
    			request.setAttribute("reservas", reservas);
    			
    	    	rd = request.getRequestDispatcher("/visualizarreservasrecordatorio.jsp");
    	    	rd.forward(request, response);
    	}
    	  
    	  if (llegoSolicitud.equals("FiltroFechasC")) {
  	    	
    		  Trabajador usuario =  null;
    	    	usuario = (Trabajador) sesion.getAttribute("usuario");
    	    	//request.setAttribute("usuario", usuario);
    			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
    			
    			this.InvalidarFiltros();
    			
    			String Fecha1 = "";
    			String Fecha2 = "";
    			Fecha1 = request.getParameter("Inicio");
    			Fecha2 = request.getParameter("Fin");
    			
    			sesion.setAttribute("16_Fecha1", Fecha1);
    	    	sesion.setAttribute("16_Fecha2", Fecha2);
    			
    	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechas("", "", "", Fecha1, Fecha2);	
    			request.setAttribute("reservas", reservas);
    			
    	    	rd = request.getRequestDispatcher("/visualizarreservasc.jsp");
    	    	rd.forward(request, response);
    	}
    	  
    	  if (llegoSolicitud.equals("FiltroFechasSesiones")) {
    	    	
    		  Trabajador usuario =  null;
    	    	usuario = (Trabajador) sesion.getAttribute("usuario");
    	    	//request.setAttribute("usuario", usuario);
    			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
    			
    			this.InvalidarFiltros();
    			
    			String Fecha1 = "";
    			String Fecha2 = "";
    			Fecha1 = request.getParameter("Inicio");
    			Fecha2 = request.getParameter("Fin");
    			
    			sesion.setAttribute("16_Fecha1", Fecha1);
    	    	sesion.setAttribute("16_Fecha2", Fecha2);
    			    			
    	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getNoPreReservasSinIdConFechas("", "", "", Fecha1, Fecha2);	
    			request.setAttribute("reservas", reservas);
    			
    	    	rd = request.getRequestDispatcher("/nuevosesiones.jsp");
    	    	rd.forward(request, response);
    	}
    	  
    	if (llegoSolicitud.equals("AsignarAnticipo")) {
    	    Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    System.out.println("El usuario en la solicitud de reserva es "+ trab.getNombre());
  	    	
  	    	int llegoIdReserva = Integer.parseInt(request.getParameter("16_Id_Reserva"));
    	    int llegoMonto = Integer.parseInt(request.getParameter("16_Monto_Pago_Adelantado").replace(".", ""));
    	    String llegoFechaAnticipo = request.getParameter("16_Fecha_Anticipo");
    	    String llegoHoraAnticipo = request.getParameter("16_Hora_Anticipo");
    	    String llegoNombreAnticipo = request.getParameter("16_Nombre_Anticipo");
    	    String llegoTipoAnticipo = request.getParameter("16_Tipo_Anticipo");
    	    
    	    if(llegoMonto <= 0){
    	    	llegoMonto = -1;
    	    }
    	    	
    	    ArrayList<Reserva> reservaamodificar = gd.getReservasFiltro("16_ID_RESERVA", Integer.toString(llegoIdReserva), "Int");
  	    	Reserva reserv = reservaamodificar.get(0); 
    		
  	    	reserv.setId_Reserva(llegoIdReserva);
  	    	reserv.setMonto_Pago_Adelantado(llegoMonto);
  	    	reserv.setFecha_Anticipo(llegoFechaAnticipo);
  	    	reserv.setHora_Anticipo(llegoHoraAnticipo);
  	    	reserv.setNombre_Anticipo(llegoNombreAnticipo);
  	    	reserv.setTipo_Anticipo(llegoTipoAnticipo);
  	    	
  	    	gd.AsignarAnticipo(reserv);
    		  	    	
  	    	  	    	
  	    	Campania camp = ((ArrayList<Campania>)(gd.getCampañasFiltro("17_ID_CAMPANIA", Integer.toString(reserv.getId_Campania()), "Int"))).get(0);
 	
  	    	
  	    	ArrayList<Cliente> cli = gd.getClientesFiltro("15_ID_CLIENTE", Integer.toString(reserv.getId_Cliente()), "Int");
	    	Cliente cliente = cli.get(0); 
		
			JavaMail mail = new JavaMail();
			
			String correo = cliente.getMail();
			String correo2 = trab.getEmail();
			String correo3 = "salomon.nilo@advancing.cl"; 
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			String fecha2 = DomingoASabado(sdf2.format(reserv.getFecha()));
			
			SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
			String hora = sdf3.format(reserv.getFecha());
			
	    	String adicional = "";
	    	if(reserv.getCantidad_Adicionales()>0){
	    		String PrecioAdicional = NumberFormat.getIntegerInstance().format(reserv.getCantidad_Adicionales());
	    		adicional = "<br><br> Por ende deberás pagar <strong>"+PrecioAdicional+"</strong> persona(s) adicional(es).";
	    	}
			
	    	String PDcampania = "";
	    	if(!camp.getDescripcion().equals("")){
	    		PDcampania = "<br><br> <em>"+camp.getDescripcion()+"</em>";
	    	}
	    	
	    	String MensajeMail = (String)(gd.getMensajeMail());
	    	if(!MensajeMail.equals("")){
	    		MensajeMail = "<br><br>" + MensajeMail ; 
	    	}

			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
			
	    	String correoEnvia = Vend.getMail();
	    	String nombreEnvia = Vend.getVendedor();
	    	String logoIcono = Vend.getLogo_Icono();
	    	String pagina = Vend.getWeb();
	    	String direccion = Vend.getDireccion();
	    	String clave = Vend.getMail_PW();
	    		    	
	    	String MensajeDeCorreo;
	    	String AsuntoDeCorreo; 
	    		    	
	    	AsuntoDeCorreo = "Comprobante de reserva";
	    	MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
					+ "Se ha confirmado su sesión fotográfica con el abono recibido de $"+NumberFormat.getIntegerInstance().format(reserv.getMonto_Pago_Adelantado())+". <br><br>"
					+ "La fecha es <strong>"+fecha2+ "</strong> a las <strong>"+hora+
					"</strong> horas para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
					+ "persona(s)."+adicional
					+ "<br><br> No faltes, ¡Te esperamos!"
					+ PDcampania
					+ MensajeMail
					+ "La dirección es: "+direccion
					+ "<br>Nuestra web: "+pagina
					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
	    	
	    	
			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave);	    	
  	    	
  	    	
  	    	
    		String mensaje = "Monto de $"+llegoMonto+" asignado correctamente";
  			
  			request.setAttribute("mensaje", mensaje);
  			request.setAttribute("tipomensaje", "success");
  						
  			ArrayList<ArrayList<Object>> reservas = null;
			
			if(sesion.getAttribute("15_Nombre")!=null){
	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Nombre", (String)sesion.getAttribute("15_Nombre"), "String");	
    	    }else if(sesion.getAttribute("15_Apellido_Pat")!=null){
	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("15_Apellido_Pat", (String)sesion.getAttribute("15_Apellido_Pat"), "String");	
    	    }else if(sesion.getAttribute("16_Fecha1")!=null || sesion.getAttribute("16_Fecha2")!=null){
	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdConFechas("", "", "", (String)sesion.getAttribute("16_Fecha1"), (String)sesion.getAttribute("16_Fecha2"));	
    		}else {
	    		reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
    		}
			
			request.setAttribute("reservas", reservas);
			
  	    	rd = request.getRequestDispatcher("/visualizarreservasanticipo.jsp");
  	    	rd.forward(request, response);
      	  }
    	
    	  if (llegoSolicitud.equals("RecordatorioMasivo")) {
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Recordatorio: "+ usuario.getNombre());
	    	
			String llegofecha = request.getParameter("Fecha");
			String llegoreservas = request.getParameter("Reservas");
			String llegoprereservas = request.getParameter("PreReservas");
						
			if(llegofecha!=null && llegofecha.length()==10){
				
				llegofecha = llegofecha.replace('/', '-');
				
				boolean prereserva = false;
				boolean reserva = false;
				
				
				if(llegoreservas!=null && llegoreservas.equals("1")){
					reserva = true;
				}
				if(llegoprereservas!=null && llegoprereservas.equals("1")){
					prereserva = true;
				}
							
				ArrayList<ArrayList<Object>> reservasarecordar = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdLike("16_Fecha", llegofecha , "String");	
				
				while(!reservasarecordar.isEmpty()){
					Reserva reserv = (Reserva) reservasarecordar.get(0).get(0);
									
					
					if((reserv.isPreReserva() && !prereserva) || (!reserv.isPreReserva()) && !reserva){
						reservasarecordar.remove(0);
						continue;
					}						
					
					Campania camp = ((ArrayList<Campania>)(gd.getCampañasFiltro("17_ID_CAMPANIA", Integer.toString(reserv.getId_Campania()), "Int"))).get(0);
		  			
					ArrayList<Cliente> cli = gd.getClientesFiltro("15_ID_CLIENTE", Integer.toString(reserv.getId_Cliente()), "Int");
			    	Cliente cliente = cli.get(0); 
					
					JavaMail mail = new JavaMail();
					
					String correo = cliente.getMail();
					String correo2 = usuario.getEmail();
					String correo3 = "salomon.nilo@advancing.cl"; 
					
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
					
					String fecha2 ="";
					String hora ="";
					String horario = "";
					
					if(reserv.getFecha() != null){
						fecha2 = DomingoASabado(sdf2.format(reserv.getFecha()));	
		    			hora = sdf3.format(reserv.getFecha());	
		    			horario = "es <strong>"+fecha2+ "</strong> a las <strong>"+hora+"</strong> horas ";
					}else{
						horario = "está pendiente, la agenda es ";
					}
					
					
			    	String adicional = "";
			    	if(reserv.getCantidad_Adicionales()>0){
			    		adicional = "<br><br> Por ende deberás pagar <strong>"+reserv.getCantidad_Adicionales()+"</strong> persona(s) adicional(es).";
			    	}
					
			    	String PDcampania = "";
			    	if(!camp.getDescripcion().equals("")){
			    		PDcampania = "<br><br> <em>"+camp.getDescripcion()+"</em>";
			    	}
			    	
			    	String PDcampaniaAdicional = "";
			    	if(!camp.getDescripcion_Adicional().equals("")){
			    		PDcampaniaAdicional = "<br><br> <em>"+camp.getDescripcion_Adicional()+"</em>";
			    	}
			    	
			    	String MensajeMail = (String)(gd.getMensajeMail());
			    	if(!MensajeMail.equals("")){
			    		MensajeMail = "<br><br>" + MensajeMail ; 
			    	}

	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(reserv.getVendedor()), "Int").get(0);
	    			
	    	    	String correoEnvia = Vend.getMail();
	    	    	String nombreEnvia = Vend.getVendedor();
	    	    	String logoIcono = Vend.getLogo_Icono();
	    	    	String pagina = Vend.getWeb();
	    	    	String direccion = Vend.getDireccion();
	    	    	String clave = Vend.getMail_PW();
	    	    	
			    	String AsuntoDeCorreo = "Recordatorio de reserva";
			    				    	
			    	String MensajeDeCorreo = "";
			    	
			    	if(reserv.isPreReserva()){
			    		String MetodoPago = (String)(gd.getMetodoPago(reserv.getId_Metodo_Pago()));
		    	    	if(!MetodoPago.equals("")){
		    	    		MetodoPago = "<br><br>Los datos de transferencia son:<br>" + MetodoPago ; 
		    	    	}
		    	    	AsuntoDeCorreo = "Comprobante de pre-reserva";
			    		MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
		    					+ "Le recordamos que usted ha pre-reservado una sesión fotográfica. <br><br>"
		    					+ "La fecha " 
		    					+ horario 
		    					+ "para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
		    					+ "persona(s)."+adicional
		    					+ PDcampania
		    					+ "<br><br><span style='font-size:12.0pt;color:red'> Para confirmar la hora, debes transferir o depositar $"
		    					+  NumberFormat.getIntegerInstance().format(camp.getAbono())
		    					+", durante las próximas horas, sino se anulará la pre-reserva. </span>"
		    					+ MetodoPago
		    					+ "La dirección es: "+direccion
		    					+ "<br>Nuestra página es: "+pagina
		    					+ MensajeMail
		    					
		    					+ "<br><br> No faltes, ¡Te esperamos!"
		    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
			    	}else{
			    		MensajeDeCorreo = "Estimado/a <strong>"+cliente.getNombre()+":</strong> <br><br><br>"
		    					+ "Le recordamos que usted posee agendada una sesión fotográfica. <br><br>"
		    					+ "La fecha agendada es para el <strong>"+fecha2+ "</strong> a las <strong>"+hora+
		    					"</strong> horas para la campaña \""+camp.getNombre()+"\" que ofrece fotografías para <strong>"+camp.getMaximo_Personas()+"</strong> "
		    					+ "persona(s)."+adicional
		    					+ "<br><br> No faltes, ¡Te esperamos!"
		    					+ PDcampania
		    					+ PDcampaniaAdicional
		    					+ MensajeMail
		    					+ "La dirección es: "+direccion
		    					+ "<br>Nuestra página es: "+pagina
		    					+ "<br><br><br><strong>"+nombreEnvia+". </strong><br><br><center><img src=\"LogoLetras.png\"/></center>";
		    			
			    	}
					mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave);   	
					
					reservasarecordar.remove(0);
				}
			}
			
			
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
			request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/visualizarreservasrecordatorio.jsp");
	    	rd.forward(request, response);
	      }
    	  
    	  if (llegoSolicitud.equals("FiltroReservas")) {
  	    	
    		  	Trabajador usuario =  null;
    		  	boolean and = false;
    	    	usuario = (Trabajador) sesion.getAttribute("usuario");
    	    	//request.setAttribute("usuario", usuario);
    			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
    			
    			this.InvalidarFiltros();
    			
    			String Fecha1 = "";
    			String Fecha2 = "";
    			String Nombre = "";
    			String Apellido = "";
    		
    			Fecha1 = request.getParameter("Inicio");
    			Fecha2 = request.getParameter("Fin");
    			Nombre = request.getParameter("15_Nombre");
    			Apellido = request.getParameter("15_Apellido_Pat");
    			    			    	    	
    	    	if(request.getParameter("Inicio")!=null){
    				sesion.setAttribute("Inicio", request.getParameter("Inicio"));
    			}
    	    	if(request.getParameter("Fin")!=null){
    				sesion.setAttribute("Fin", request.getParameter("Fin"));
    			}
    	    	if(request.getParameter("15_Nombre")!=null){
    				sesion.setAttribute("15_Nombre", request.getParameter("15_Nombre"));
    			}
    			if(request.getParameter("15_Apellido_Pat")!=null){
    				sesion.setAttribute("15_Apellido_Pat", request.getParameter("15_Apellido_Pat"));
    			}
    			
    			
    			String CondicionDeBusqueda = " AND ";
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String Fecha12 = null;
				
				if(!(Fecha1.equals(""))){
					java.util.Date DiaSiguiente = (java.util.Date) sdf.parse(Fecha1);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DiaSiguiente); // Configuramos la fecha que se recibe
					calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
					Fecha12 = sdf.format(calendar.getTime());	
				}
						
				if((!(Fecha1.equals("")))||(!(Fecha2.equals("")))){
					if((!(Fecha1.equals("")))&&(Fecha2.equals(""))){
						 CondicionDeBusqueda += " [16_FECHA] BETWEEN '"+Fecha1+"' AND '"+Fecha12+"' ";
					}
					if((!(Fecha2.equals("")))&&(!(Fecha1.equals("")))){
						CondicionDeBusqueda += " [16_FECHA] BETWEEN '"+Fecha1+"' AND '"+Fecha2+"' ";
					} 
					and = true;
				}else{
					CondicionDeBusqueda += " ";
				}
				
				if(!Nombre.equals("")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [15_Nombre] LIKE '%"+Nombre+"%' ";
					and = true;
				}
				
				if(!Apellido.equals("")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [15_Apellido_Pat] LIKE '%"+Apellido+"%' ";
					and = true;
				}
    			    			
    	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getNoPreReservasSinIdLike(CondicionDeBusqueda);	
    			request.setAttribute("reservas", reservas);
    			
    	    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
    	    	rd.forward(request, response);
    	  }
    	  
    	  if (llegoSolicitud.equals("FiltroReservasSwitch")) {
    	    	
  		  	Trabajador usuario =  null;
  		  	boolean and = false;
  	    	usuario = (Trabajador) sesion.getAttribute("usuario");
  	    	//request.setAttribute("usuario", usuario);
  			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
  			
  			this.InvalidarFiltros();
  			
  			String Fecha1 = "";
  			String Fecha2 = "";
  			String Nombre = "";
  			String Apellido = "";
  		
  			Fecha1 = request.getParameter("Inicio");
  			Fecha2 = request.getParameter("Fin");
  			Nombre = request.getParameter("15_Nombre");
  			Apellido = request.getParameter("15_Apellido_Pat");
  			    			    	    	
  	    	if(request.getParameter("Inicio")!=null){
  				sesion.setAttribute("Inicio", request.getParameter("Inicio"));
  			}
  	    	if(request.getParameter("Fin")!=null){
  				sesion.setAttribute("Fin", request.getParameter("Fin"));
  			}
  	    	if(request.getParameter("15_Nombre")!=null){
  				sesion.setAttribute("15_Nombre", request.getParameter("15_Nombre"));
  			}
  			if(request.getParameter("15_Apellido_Pat")!=null){
  				sesion.setAttribute("15_Apellido_Pat", request.getParameter("15_Apellido_Pat"));
  			}
  			
  			
  			String CondicionDeBusqueda = " AND ";
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String Fecha12 = null;
				
				if(!(Fecha1.equals(""))){
					java.util.Date DiaSiguiente = (java.util.Date) sdf.parse(Fecha1);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DiaSiguiente); // Configuramos la fecha que se recibe
					calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
					Fecha12 = sdf.format(calendar.getTime());	
				}
						
				if((!(Fecha1.equals("")))||(!(Fecha2.equals("")))){
					if((!(Fecha1.equals("")))&&(Fecha2.equals(""))){
						 CondicionDeBusqueda += " [16_FECHA] BETWEEN '"+Fecha1+"' AND '"+Fecha12+"' ";
					}
					if((!(Fecha2.equals("")))&&(!(Fecha1.equals("")))){
						CondicionDeBusqueda += " [16_FECHA] BETWEEN '"+Fecha1+"' AND '"+Fecha2+"' ";
					} 
					and = true;
				}else{
					CondicionDeBusqueda += " ";
				}
				
				if(!Nombre.equals("")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [15_Nombre] LIKE '%"+Nombre+"%' ";
					and = true;
				}
				
				if(!Apellido.equals("")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [15_Apellido_Pat] LIKE '%"+Apellido+"%' ";
					and = true;
				}
  			    			
  	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId(CondicionDeBusqueda);	
  			request.setAttribute("reservas", reservas);
  			
  	    	rd = request.getRequestDispatcher("/visualizarreservasswitch.jsp");
  	    	rd.forward(request, response);
    	  }
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
    
    public int Diferencia (int campania, int personastotales){
    	if(personastotales > campania){
    		return personastotales - campania;
    	}else{
    		return 0;
    	}
    }
    
    /*
     * Transformador de fecha
     */
    private static Date toDate(String dateString) throws java.text.ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
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
		}
    }
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		try {
			procesamientoPeticion(req, resp);
		} catch (ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	

}
