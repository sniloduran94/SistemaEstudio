package controlador;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


import org.apache.tomcat.util.codec.binary.Base64;


import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import conexion.SQLS_conexion;
import conexion.UserHomeApplet;
import conexion.WriteExcel;
import conexion.WriteExcelCampania;
import conexion.WriteExcelCliente;
import conexion.WriteExcelSesion;
import modelo.*;

/**
 * Servlet implementation class LetInicioSesion
 */
@WebServlet("/LetInicioSesion")
public class LetInicioSesion extends HttpServlet {
	HttpSession sesion;
	Trabajador trab = null;
	private static final long serialVersionUID = 1L;

	protected void procesamientoPeticion(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException,
       	SQLException, ParseException, java.text.ParseException {
		
	    String llegoSolicitud = request.getParameter("opcion");
	    
	    SQLS_conexion gd = new SQLS_conexion();
	    
	    RequestDispatcher rd = null;
	    	    
	    if (llegoSolicitud.equals("IniciarSesion")) {
	    
	    	String llegousuario = request.getParameter("usuario");
	    	String llegocontrasenia = request.getParameter("pass");
	    		    	
	    	ArrayList<Object> Trabajadores = (ArrayList<Object>)(gd.ExisteUsuario(llegousuario,this.Encriptar(llegocontrasenia)));
	    	
	    	if(!Trabajadores.isEmpty()){
	    		Trabajador worker = (Trabajador) Trabajadores.get(0);
	    		trab = worker;
	    		
	    		if(worker.getEsAdmin()==1){
	    			
	    			sesion = request.getSession(true);
	    			sesion.setAttribute("usuario", trab);
	    			
	    			Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
	    			System.out.println("Nombre en LetSesion - InicioSesion: "+ usuario.getNombre() + " "+   new Date());
	    			
	    			request.setAttribute("mensaje", "<strong>¡Bienvenido(a) </strong>");
	    			request.setAttribute("tipomensaje", "bienvenida" );
	    			
	    			rd = request.getRequestDispatcher("/indextrabajador.jsp");
	    		}
	    		else{
	    			/* 
	    			 * Si es trabajador común
	    			 */
	    			
	    			sesion = request.getSession(true);
	    			sesion.setAttribute("usuario", trab);
	    			
	    			Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
	    			System.out.println("Nombre en LetSesion - InicioSesion: "+ usuario.getNombre());
	    			
	    			request.setAttribute("mensaje", "<strong>¡Bienvenido(a) </strong>");
	    			request.setAttribute("tipomensaje", "bienvenida" );
	    			
	    			rd = request.getRequestDispatcher("/indexfotografo.jsp");
	    		}
	    	}else{
	    		/*
	    		 * Si es cliente
	    		 */
	    		rd = request.getRequestDispatcher("/login.html");
	    	}
            rd.forward(request, response);
	    }
	    
	    if(llegoSolicitud.equals("IndexTrabajadorAdmin")) {
		    
	    	this.InvalidarFiltros();
	    	
	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
	    	
	    	rd.forward(request, response);
	    }
	    
	    if(llegoSolicitud.equals("IndexTrabajadorFotografo")) {
		    
	    	this.InvalidarFiltros();
	    	
	    	rd = request.getRequestDispatcher("/indexfotografo.jsp");
	    	
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("Agendar")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Agendar: "+ usuario.getNombre());
						
			ArrayList<ArrayList<String>> fechas = (ArrayList<ArrayList<String>>)gd.getAgenda();
			request.setAttribute("reservas", fechas);
	    	
	    	ArrayList<Campania> Campanias = (ArrayList<Campania>)gd.getCampañasVigentes();	
			request.setAttribute("campanias", Campanias);
			request.setAttribute("canalesventasarray", (ArrayList<ArrayList<String>>)(gd.getCanalesVentasArray()));
			
			ArrayList<Vendedor> Vendedores = (ArrayList<Vendedor>)gd.getVendedoresSinId("", "", "");
			request.setAttribute("vendedores", Vendedores);
			
			ArrayList<Cliente> Clientes = (ArrayList<Cliente>)gd.getClientesFiltro("","","");
			request.setAttribute("clientes", Clientes);
			
			request.setAttribute("tipossesiones", (ArrayList<Tipo_Sesion>) (gd.getTiposSesiones()));
			
			String CodigosDeshabilitados = (String)gd.CuponesDeshabilitados();
			request.setAttribute("cuponesdeshabilitados", CodigosDeshabilitados);
			
			ArrayList<Metodo_Pago> Metodos = (ArrayList<Metodo_Pago>)gd.getMetodoPagosFiltroSinHTML("","","");
			request.setAttribute("metodo_pago", Metodos);
			
			ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunas();
 			request.setAttribute("comunas", comunasatransformar);
			
			ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
			request.setAttribute("ciudades", ciudades);
			
			ArrayList<String> mails = (ArrayList<String>)gd.getMailsClientes("");
			request.setAttribute("mails", mails);
			
			sesion.setAttribute("usuario", usuario);
						
	    	rd = request.getRequestDispatcher("/reservar(connuevocliente).jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarReserva")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
	    	request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarReservaSwitch")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
	    	request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/visualizarreservasswitch.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarReservasPendientes")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	
			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdPendientes("", "", "");	
	    	request.setAttribute("reservas", reservas);
	    	
	    	request.setAttribute("SinFiltros", true);
			
	    	rd = request.getRequestDispatcher("/visualizarreservas.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarReservaSesiones")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("16_Pre_Reserva", "0", "Int");	
			request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/nuevosesiones.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarSesiones")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Visualizar Sesiones: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();	
	    	request.setAttribute("sesiones", sesiones);
			
	    	rd = request.getRequestDispatcher("/visualizarsesiones.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarSesionesEstado")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Visualizar Sesiones: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> sesiones = (ArrayList<ArrayList<Object>>)gd.getSesionesSinId();	
	    	request.setAttribute("sesiones", sesiones);
			
	    	rd = request.getRequestDispatcher("/visualizarsesionesestado.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarReservasRecordatorio")) {
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Recordatorio: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
			request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/visualizarreservasrecordatorio.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarPreReservasRecordatorio")) {
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Recordatorio: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("16_Pre_Reserva", "1", "Int");	
			request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/visualizarreservasrecordatorio.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarReservaC")) {
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
			request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/visualizarreservasc.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarReservaAnticipo")) {
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Anticipo: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinIdAnticipo("", "", "");	
			request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/visualizarreservasanticipo.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarNuevoCliente")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Generar Nuevo Cliente: "+ usuario.getNombre());
	    	
	    	ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunas();
 			request.setAttribute("comunas", comunasatransformar);
			
			ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
			request.setAttribute("ciudades", ciudades);
			
			ArrayList<String> mails = (ArrayList<String>)gd.getMailsClientes("");
			request.setAttribute("mails", mails);
			
	    	rd = request.getRequestDispatcher("/nuevocliente.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarNuevoEvento")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Generar Nuevo Evento: "+ usuario.getNombre());
	    				
	    	rd = request.getRequestDispatcher("/nuevoevento.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarCliente")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - VisualizarClientes: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)gd.getClientesSinId("", "", "");	
			request.setAttribute("clientes", clientes);
			
			ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunasId();
 			request.setAttribute("comunas", comunasatransformar);
			
			ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
			request.setAttribute("ciudades", ciudades);
			
	    	rd = request.getRequestDispatcher("/visualizarclientes.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarEvento")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - VisualizarEvento: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> eventos = (ArrayList<ArrayList<Object>>)gd.getEventosSinId("");	
			request.setAttribute("eventos", eventos);
			
	    	rd = request.getRequestDispatcher("/visualizareventos.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarNuevoMetodoPago")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Generar Nuevo Metodo Pago: "+ usuario.getNombre());
	    							
	    	rd = request.getRequestDispatcher("/nuevometodopago.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarMetodoPago")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - Visualizar Campanias: "+ usuario.getNombre());
	    	
	    	ArrayList<Metodo_Pago> metodo_pago = (ArrayList<Metodo_Pago>)gd.getMetodoPagosFiltro("", "", "");	
			request.setAttribute("metodo_pago", metodo_pago);
			
	    	rd = request.getRequestDispatcher("/visualizarmetodopago.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarNuevoCampania")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - VisualizarClientes: "+ usuario.getNombre());
	    				
			request.setAttribute("canalesventas", (ArrayList<Canal_Venta>) (gd.getCanalesVentas()));
	   
			rd = request.getRequestDispatcher("/nuevocampania.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarCampania")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - Visualizar Campanias: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> campanias = (ArrayList<ArrayList<Object>>)gd.getCampañasSinId("", "", "");	
			request.setAttribute("campanias", campanias);
			
			ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
			request.setAttribute("canalesventas", canalesdeventa);
			
	    	rd = request.getRequestDispatcher("/visualizarcampanias.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarEstadisticaCampania")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - Visualizar Campanias: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<String>> campanias = (ArrayList<ArrayList<String>>)gd.EstadisticaCampania("");	
			request.setAttribute("campanias", campanias);
			
			ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
			request.setAttribute("canalesventas", canalesdeventa);
			
	    	rd = request.getRequestDispatcher("/visualizarestadisticascampanias.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarNuevoTrabajador")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Generar Nuevo Trabajador: "+ usuario.getNombre());
			
	    	rd = request.getRequestDispatcher("/nuevotrabajador.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("VisualizarTrabajador")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - VisualizarTrabajador: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> trabajadores = (ArrayList<ArrayList<Object>>)gd.getTrabajadoresSinId("", "", "");	
			request.setAttribute("trabajadores", trabajadores);
			
	    	rd = request.getRequestDispatcher("/visualizartrabajadores.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarXLS")) {
	    	System.out.println("SOLICITUD DE GENERACION");
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - VisualizarClientes: "+ usuario.getNombre());
	    	
			ArrayList<ArrayList<String>> Arreglo = (ArrayList<ArrayList<String>>)(gd.ArrayExcel());
			
			try{
				WriteExcel test = new WriteExcel();
				String home = System.getProperty("user.home");
				String dir = System.getProperty("user.dir");
				
				System.out.println("C:/Users/" + System.getProperty("user.name") + "/Downloads/");
				
				UserHomeApplet dirUsu = new UserHomeApplet();
				System.out.println("Prop "+dirUsu.getUserHome());
								
				String fmt = home+"/Downloads/GenesisEstudio.xls";
				File f = null;
				for (int i = 1; i < 100; i++) {
				    f = new File(String.format(fmt, i));
				    if (!f.exists()) {
				        break;
				    }
				}
				try {
				    System.out.println(f.getCanonicalPath());
				} catch (IOException e) {
				    e.printStackTrace();
				}	
				
			    test.setOutputFile(f.getCanonicalPath());
				//test.setOutputFile("c:/users/nb-desktop/FotoExpressiones.xls");
			    test.write(Arreglo);
			    System.out.println("Please check the result file under c:/users/nb-desktop/prueba.xls");
			    String mensaje = "<strong>Archivo Microsoft Office Excel creado correctamente</strong><br><br>"
						+ "Por favor revisa tu carpeta de descargas. Ejemplo: C://Usuarios/MiUsuario/Downloads/";
				
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "success");
				
				rd= getServletContext().getRequestDispatcher("/DownloadFileServlet?fileName=GenesisEstudio.xls"); 
				response.setHeader("Content-Disposition", "attachment; filename=\"GenesisEstudio.xls\"");
				rd.include(request, response); 
				
			}catch(Exception e){	
				e.printStackTrace();
				String mensaje = "<strong>¡Se produjo un error!</strong><br><br>"
						+ "Descripción: "+e.getMessage();
				
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "danger");
			}
		
			rd = request.getRequestDispatcher("/indextrabajador.jsp");
			rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarXLSSesiones")) {

	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - Generar XLS Sesiones : "+ usuario.getNombre());
	    	
			ArrayList<ArrayList<String>> Arreglo = (ArrayList<ArrayList<String>>)(gd.ArrayExcelSesiones());
			
			try{
				
				WriteExcelSesion test = new WriteExcelSesion();
				String home = System.getProperty("user.home");
				
				String fmt = home+"/Downloads/GenesisEstudioSesiones.xls";
				File f = null;
				for (int i = 1; i < 100; i++) {
				    f = new File(String.format(fmt, i));
				    if (!f.exists()) {
				        break;
				    }
				}
				try {
				    System.out.println(f.getCanonicalPath());
				} catch (IOException e) {
				    e.printStackTrace();
				}			
			    test.setOutputFile(f.getCanonicalPath());
			    
				//test.setOutputFile("c:/users/nb-desktop/FotoExpressiones.xls");
			    test.write(Arreglo);
			    System.out.println("Please check the result file under c:/users/nb-desktop/prueba.xls");
			    String mensaje = "<strong>Archivo Microsoft Office Excel creado correctamente</strong><br><br>"
						+ "Por favor revisa tu carpeta de descargas. Ejemplo: C://Usuarios/MiUsuario/Downloads/";
				
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "success");
				
				rd= getServletContext().getRequestDispatcher("/DownloadFileServlet?fileName=GenesisEstudioSesiones.xls"); 
				response.setHeader("Content-Disposition", "attachment; filename=\"GenesisEstudioSesiones.xls\"");
				rd.include(request, response); 
				
			}catch(Exception e){	
				System.out.println(e.getMessage());
				String mensaje = "<strong>¡Se produjo un error!</strong><br><br>"
						+ "Descripción: "+e.getMessage();
				
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "danger");
			}
						
			rd = request.getRequestDispatcher("/indextrabajador.jsp");
			rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarXLSCampanias")) {

	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - GenerarXLSCampanias: "+ usuario.getNombre());
	    	
			ArrayList<ArrayList<String>> Arreglo = (ArrayList<ArrayList<String>>)(gd.ArrayExcelCampanias());
			
			try{
				
				WriteExcelCampania test = new WriteExcelCampania();
				String home = System.getProperty("user.home");
				
				String fmt = home+"/Downloads/CampañasGenesisEstudio.xls";
				File f = null;
				for (int i = 1; i < 100; i++) {
				    f = new File(String.format(fmt, i));
				    if (!f.exists()) {
				        break;
				    }
				}
				try {
				    System.out.println(f.getCanonicalPath());
				} catch (IOException e) {
				    e.printStackTrace();
				}	
				
				
			    test.setOutputFile(f.getCanonicalPath());
				//test.setOutputFile("c:/users/nb-desktop/FotoExpressiones.xls");
			    test.write(Arreglo);
			    System.out.println("Please check the result file under c:/users/nb-desktop/prueba.xls");
			    String mensaje = "<strong>Archivo Microsoft Office Excel creado correctamente</strong><br><br>"
						+ "Por favor revisa tu carpeta de descargas. Ejemplo: C://Usuarios/MiUsuario/Downloads/";
				
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "success");
				
				rd= getServletContext().getRequestDispatcher("/DownloadFileServlet?fileName=CampañasGenesisEstudio.xls"); 
				response.setHeader("Content-Disposition", "attachment; filename=\"CampañasGenesisEstudio.xls\"");
				rd.include(request, response); 
				
			}catch(Exception e){	
				System.out.println(e.getMessage());
				String mensaje = "<strong>¡Se produjo un error!</strong><br><br>"
						+ "Descripción: "+e.getMessage();
				
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "danger");
			}
						
			rd = request.getRequestDispatcher("/indextrabajador.jsp");
			rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("GenerarXLSClientes")) {

	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - GenerarXLSClientes: "+ usuario.getNombre());
	    	
			ArrayList<ArrayList<String>> Arreglo = (ArrayList<ArrayList<String>>)(gd.ArrayExcelClientes());
			
			try{
				WriteExcelCliente test = new WriteExcelCliente();
				String home = System.getProperty("user.home");
				
				String fmt = home+"/Downloads/ClientesGenesisEstudio.xls";
				File f = null;
				for (int i = 1; i < 100; i++) {
				    f = new File(String.format(fmt, i));
				    if (!f.exists()) {
				        break;
				    }
				}
				try {
				    System.out.println(f.getCanonicalPath());
				} catch (IOException e) {
				    e.printStackTrace();
				}	
				
				test.setOutputFile(f.getCanonicalPath());
				
				//test.setOutputFile("c:/users/nb-desktop/FotoExpressiones.xls");
			    test.write(Arreglo);
			    System.out.println("Please check the result file under c:/users/nb-desktop/prueba.xls");
			    String mensaje = "<strong>Archivo Microsoft Office Excel creado correctamente</strong><br><br>"
						+ "Por favor revisa tu carpeta de descargas. Ejemplo: C://Usuarios/MiUsuario/Downloads/";
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "success");
				
				rd= getServletContext().getRequestDispatcher("/DownloadFileServlet?fileName=ClientesGenesisEstudio.xls"); 
				response.setHeader("Content-Disposition", "attachment; filename=\"ClientesGenesisEstudio.xls\"");
				rd.include(request, response); 
				
			}catch(Exception e){	
				System.out.println(e.getMessage());
				String mensaje = "<strong>¡Se produjo un error!</strong><br><br>"
						+ "Descripción: "+e.getMessage();
				
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "danger");
			}
						
			rd = request.getRequestDispatcher("/indextrabajador.jsp");
			rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("MirarCalendario")) {
	    	
    	    Trabajador usuario =  null;
    	    usuario = (Trabajador) sesion.getAttribute("usuario");
    	    System.out.println("Nombre en LetSesion - MirarCalendario: "+ usuario.getNombre());
    		    	    
    	    ArrayList<ArrayList<String>> reservas = (ArrayList<ArrayList<String>>)gd.getCalendario();	
			request.setAttribute("reservas", reservas);
    	    
    	    rd = request.getRequestDispatcher("/mirarcalendario.jsp");
    	    rd.forward(request, response);
    	}
	    
	   if (llegoSolicitud.equals("ValidarReservas")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Visualizar: "+ usuario.getNombre());
	    	
	    	ArrayList<ArrayList<Object>> reservas = (ArrayList<ArrayList<Object>>)gd.getReservasSinId("", "", "");	
			request.setAttribute("reservas", reservas);
			
	    	rd = request.getRequestDispatcher("/validarreservas.jsp");
	    	rd.forward(request, response);
	    }
	   
	   if (llegoSolicitud.equals("CerrarSesion")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - CerrarSesion: "+ usuario.getNombre());
			
			sesion.setAttribute("usuario", null);
			sesion.invalidate();
			
	    	rd = request.getRequestDispatcher("/login.html");
	    	rd.forward(request, response);
	    }
	   
	   if (llegoSolicitud.equals("GenerarNuevoCanalDeVenta")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Generar Nuevo Canal De Venta: "+ usuario.getNombre());
	    				
	    	rd = request.getRequestDispatcher("/nuevocanaldeventa.jsp");
	    	rd.forward(request, response);
	    }
	   
	   	if (llegoSolicitud.equals("VisualizarCanalDeVenta")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - Canal De Venta: "+ usuario.getNombre());
	    	
			ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
			request.setAttribute("canalesdeventa", canalesdeventa);
			
	    	rd = request.getRequestDispatcher("/visualizarcanalesdeventa.jsp");
	    	rd.forward(request, response);
	    }
	   	
	   	if (llegoSolicitud.equals("GenerarNuevoTipoSesion")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
			System.out.println("Nombre en LetSesion - Generar Nuevo Tipo de Sesion: "+ usuario.getNombre());
	    				
	    	rd = request.getRequestDispatcher("/nuevotiposesion.jsp");
	    	rd.forward(request, response);
	    }
	   	
	   	if (llegoSolicitud.equals("VisualizarTipoSesiones")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en LetSesion - Tipo de sesiones: "+ usuario.getNombre());
	    	
			ArrayList<Tipo_Sesion> tipossesiones = (ArrayList<Tipo_Sesion>)gd.getTiposSesiones();	
			request.setAttribute("tipossesiones", tipossesiones);
			
	    	rd = request.getRequestDispatcher("/visualizartipossesiones.jsp");
	    	rd.forward(request, response);
	    }
	   	
	   	if (llegoSolicitud.equals("MostrarAlbum")) {
	    	
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
			System.out.println("Nombre en Mostrar Album: "+ usuario.getNombre());
	    				
	    	rd = request.getRequestDispatcher("/mostraralbum.jsp");
	    	rd.forward(request, response);
	    }
	   	
	    if (llegoSolicitud.equals("AdministrarMensajePieMail")) {
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
	    	
			System.out.println("Nombre en LetSesion - Anticipo: "+ usuario.getNombre());
	    	
	    	String MensajePie = (String)gd.getMensajeMail();	
			request.setAttribute("mensaje", MensajePie);
			
	    	rd = request.getRequestDispatcher("/modificarmensajepiemail.jsp");
	    	rd.forward(request, response);
	    }
	    
	    if (llegoSolicitud.equals("CambiarMensajePieMail")) {
	    	Trabajador usuario =  null;
	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	    	//request.setAttribute("usuario", usuario);
	    	
	    	System.out.println("PASA POR ACA");
	    	
	    	String mensaje = (String)(request.getParameter("36_Descripcion")); 
	    	
			System.out.println("Nombre en LetSesion - MensajeAnticipo: "+ usuario.getNombre());
	    	
	    	gd.setMensajeMail(mensaje);	
			
	    	String mensajefinal = "Mensaje modificado correctamente";
			
			request.setAttribute("mensaje", mensajefinal);
			request.setAttribute("tipomensaje", "success");	    	
	    	
	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
	    	rd.forward(request, response);
	    }
	   
	}
	
	/*
     * Obtención de matriz para JavaScript
     */
    public String[][] ConvertirAMatriz(ArrayList<Comuna> ma){
    	if(ma!=null){
    		String matriz[][] = new String[ma.size()][2];
    		for (int i = 0; i < matriz.length; i++) {
				matriz[i][0] = Integer.toString((ma.get(i)).getId_Comuna());
				matriz[i][1] = ma.get(i).getComuna();
			}
    		return matriz;
    	}
		return null;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		try {
			procesamientoPeticion(request, response);
			/*if(sesion==null){
				RequestDispatcher rd = null;
				rd = request.getRequestDispatcher("/login.html");
		    	rd.forward(request, response);
			}else{*/
				
			//}
		} catch (ParseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
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

    
}
