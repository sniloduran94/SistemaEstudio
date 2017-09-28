package controlador;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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

import conexion.SQLS_conexion;
import modelo.Campania;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Comuna;
import modelo.Reserva;
import modelo.Trabajador;

/**
 * Servlet implementation class LetCliente
 */
@WebServlet("/LetCliente")
public class LetCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion; 
	
	protected void procesamientoPeticion(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
		
			String llegoSolicitud = request.getParameter("opcion");
		    
		    SQLS_conexion gd = new SQLS_conexion();
		    
		    RequestDispatcher rd = null;
		    
		    sesion = request.getSession();
		    
		    if (llegoSolicitud.equals("NuevoCliente")) {
		    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de reserva es "+ trab.getNombre());
    	    	
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
    	    		llegoContrasenia = llegoMail.substring(0, 4);
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
    	    	System.out.println(llegoReclamo);
    	    	
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
    			
    	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
    	    	rd.forward(request, response);
		    }
		    
		    if (llegoSolicitud.equals("CambiarCliente")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	  
    	    	
    	    	String llegoCliente = (request.getParameter("15_Id_Cliente"));
    	    	System.out.println("Id Cliente: "+llegoCliente);
    	    	
    	    	ArrayList<Cliente> cli = gd.getClientesFiltro("15_ID_CLIENTE", llegoCliente, "Int");
    	    	Cliente clienteamodificar = cli.get(0); 
    	    	
    	    	//Obtención del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetCliente - Cambiar Cliente: "+ usuario.getNombre());
    		
    			//Opciones - Modificar o eliminar
    			String llegoModificar = request.getParameter("ModificarReserva");
    			String llegoEliminar = request.getParameter("EliminarReserva");
    			String llegoCambiar = request.getParameter("CambiarContrasenia");
    			
    			System.out.println(llegoModificar);
    			System.out.println(llegoEliminar);
    			System.out.println(llegoCambiar);
    			
    			
    			if((llegoModificar!=null)||(llegoEliminar!=null)||(llegoCambiar!=null)){
    				if((llegoModificar!=null)){
    					//Caso de Modificar una cliente	
    					request.setAttribute("cliente", clienteamodificar);
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");
    					
    					ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunas();
    		 			request.setAttribute("comunas", comunasatransformar);
    					
    					ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
    					request.setAttribute("ciudades", ciudades);
    					
    					ArrayList<String> mails = (ArrayList<String>)gd.getMailsClientes(Integer.toString(clienteamodificar.getId_Cliente()));
    					request.setAttribute("mails", mails);
    					
    					boolean SinFiltros = true;
    					request.setAttribute("SinFiltros", SinFiltros);
    					
    					rd = request.getRequestDispatcher("/modificarcliente.jsp");
    				}
    				if((llegoEliminar!=null)){
    					//Caso de Eliminar una reserva	
    					int corroboracion = gd.EliminarCliente(clienteamodificar.getId_Cliente());
    					
    					if(corroboracion>0){
    						String mensaje = "Cliente eliminado correctamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "success");
    						
    						ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)gd.getClientesSinId("", "", "");	
    						request.setAttribute("clientes", clientes);	
    						
    						ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunasId();
    			 			request.setAttribute("comunas", comunasatransformar);
    						
    						ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
    						request.setAttribute("ciudades", ciudades);
    						
    				    	rd = request.getRequestDispatcher("/visualizarclientes.jsp");
    					}else{
    						String mensaje = "ERROR! Intente eliminar nuevamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "danger");
    						
    						ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)gd.getClientesSinId("", "", "");	
    						request.setAttribute("clientes", clientes);
    						
    						ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunasId();
    			 			request.setAttribute("comunas", comunasatransformar);
    						
    						ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
    						request.setAttribute("ciudades", ciudades);
    						
    				    	rd = request.getRequestDispatcher("/visualizarclientes.jsp");
    					}
    				}
    				if((llegoCambiar!=null)){
    					//Caso Cambiar Contrasenia
    					clienteamodificar.setConstrasenia(this.Desencriptar(clienteamodificar.getConstrasenia()));
    					request.setAttribute("cliente", clienteamodificar);
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");
    					
    					rd = request.getRequestDispatcher("/contraseniacliente.jsp");
    					
    				}
    			}
    			rd.forward(request, response);
    	  }
		    
		    if (llegoSolicitud.equals("ModificarContrasenia")) {
	  	    	
		    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de reserva es "+ trab.getNombre());
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	int llegoId = Integer.parseInt(request.getParameter("15_Id_Cliente"));
    	    	String llegoContraseniaA = this.Encriptar(request.getParameter("A15_Contrasenia"));
    	    	String llegoContrasenia  = request.getParameter("15_Contrasenia");
    	    	
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetCliente - Modificar Contrasenia: "+ usuario.getNombre());
    			
    			ArrayList<Cliente> clienteamodificar = gd.getClientesFiltro("15_ID_CLIENTE", Integer.toString(llegoId), "Int");
    			Cliente cli = clienteamodificar.get(0);
    			
    			if(!(llegoContraseniaA.equals(cli.getConstrasenia()))){
    				String mensaje = "ERROR! La contraseña antigua no coincide con la nueva";
					
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "danger");
					
					ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)gd.getClientesSinId("", "", "");	
					request.setAttribute("clientes", clientes);
					
					ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunasId();
		 			request.setAttribute("comunas", comunasatransformar);
					
					ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
					request.setAttribute("ciudades", ciudades);
					
			    	rd = request.getRequestDispatcher("/visualizarclientes.jsp");
    			}else{
    				cli.setConstrasenia(this.Encriptar(llegoContrasenia));
    			   			
    				gd.CambiarContraseniaCliente(cli);
    			
    				String mensaje = "La contraseña cambiada correctamente";
					
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "success");
					
					ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)gd.getClientesSinId("", "", "");	
					request.setAttribute("clientes", clientes);
					
					ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunasId();
		 			request.setAttribute("comunas", comunasatransformar);
					
					ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
					request.setAttribute("ciudades", ciudades);
					
					rd = request.getRequestDispatcher("/visualizarclientes.jsp");
    			}
    	    	rd.forward(request, response);
	    	  }
		    
		  
		  if (llegoSolicitud.equals("ModificarCliente")) {
	  	    	
	  	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
	  	    	System.out.println("El usuario en la solicitud de modificacion es "+ trab.getNombre());
	  	    	
	  	    //Obtencion de parámetros de formulario
	  	    	
	  	    	Cliente cli = new Cliente();
	  	    	int llegoIdCliente = Integer.parseInt(request.getParameter("15_Id_Cliente"));
	  	    	
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
    	    		int IntllegoTelefono = Integer.parseInt(llegoTelefono);
    	    		cli.setRut(IntllegoTelefono);
    	    	}else{
    	    		cli.setRut(-1);
    	    	}
    	    	int llegoCelular = Integer.parseInt(request.getParameter("15_Celular"));
    	    	String llegoMail = request.getParameter("15_Mail");
    	    	String llegoReclamo = request.getParameter("15_Reclamo");
    	    	String llegoComentario = request.getParameter("15_Observacion");
    	    	System.out.println(llegoReclamo);
    	    	  	    	
	  	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
	  			System.out.println("Nombre en LetReserva - Modificar: "+ usuario.getNombre());
	  			
	  			cli.setId_Cliente(llegoIdCliente);
    			cli.setNombre(llegoNombre);
    			cli.setApellido_Pat(llegoApellidoPat);
    			cli.setApellido_Mat(llegoApellidoMat);
    			cli.setDireccion(llegoDireccion);  		
    			cli.setCelular(llegoCelular);
    			cli.setMail(llegoMail);
    			
    			System.out.println("RECLAMO: " + llegoReclamo);
    			
    			if(llegoReclamo!=null && llegoReclamo.equals("on")){
    				System.out.println("Check en ON");
    				cli.setReclamo(true);
    				cli.setObservacion(llegoComentario);
    			}else{
    				System.out.println("Check en OFF o NULL");
    				cli.setReclamo(false);
    				cli.setObservacion(null);
    			}
    			cli.setId_Estado(1);
	  			
	  			gd.ActualizarCliente(cli);
	  			
	  			String mensaje = "Cliente modificado correctamente";
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "success");
							
				ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)gd.getClientesSinId("", "", "");	
				request.setAttribute("clientes", clientes);
				
				ArrayList<Comuna> comunasatransformar = (ArrayList<Comuna>)gd.getComunasId();
	 			request.setAttribute("comunas", comunasatransformar);
				
				ArrayList<Ciudad> ciudades = (ArrayList<Ciudad>)gd.getCiudades();
				request.setAttribute("ciudades", ciudades);
				
		    	rd = request.getRequestDispatcher("/visualizarclientes.jsp");
		    	rd.forward(request, response);
	    	  }
		  
		  if (llegoSolicitud.equals("FiltroCliente")) {
	  	    	
	  	    	Trabajador usuario =  null;
	  	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	  	    	//request.setAttribute("usuario", usuario);
	  			System.out.println("Nombre en LetCliente - Visualizar Clientes: "+ usuario.getNombre());
	  			
	  			String columna = "";
	  			
	  			if(request.getParameter("15_Nombre")!=null){
	  				columna = "15_Nombre";
	  			}
	  			if(request.getParameter("15_Apellido_Pat")!=null){
	  				columna = "15_Apellido_Pat";
	  			}
	  			
	  			System.out.println(columna +request.getParameter(columna) );
	  			
	  			ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)gd.getClientesSinIdLike(columna, request.getParameter(columna), "String");	
	  	    	request.setAttribute("clientes", clientes);
	  			
	  	    	rd = request.getRequestDispatcher("/visualizarclientes.jsp");
	  	    	rd.forward(request, response);
	  	    }
		  
		  if (llegoSolicitud.equals("FiltroClienteFono")) {
	  	    	
	  	    	Trabajador usuario =  null;
	  	    	usuario = (Trabajador) sesion.getAttribute("usuario");
	  	    	//request.setAttribute("usuario", usuario);
	  			System.out.println("Nombre en LetCliente - Filtro Fono : "+ usuario.getNombre());
	  			
	  			String columna = "15_FoT";
	  			
	  			String busqueda = request.getParameter(columna) + "%' OR [15_Celular] LIKE '%"+request.getParameter(columna)+"";
	  				  		  			
	  			ArrayList<ArrayList<Object>> clientes = (ArrayList<ArrayList<Object>>)gd.getClientesSinIdLike("15_Fono", busqueda, "String");	
	  	    	request.setAttribute("clientes", clientes);
	  			
	  	    	rd = request.getRequestDispatcher("/visualizarclientes.jsp");
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
    public LetCliente() {
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

}
