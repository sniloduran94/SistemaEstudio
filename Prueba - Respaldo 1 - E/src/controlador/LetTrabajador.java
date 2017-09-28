package controlador;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Comuna;
import modelo.Trabajador;

/**
 * Servlet implementation class LetTrabajador
 */
@WebServlet("/LetTrabajador")
public class LetTrabajador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion; 
	
	protected void procesamientoPeticion(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
		
			String llegoSolicitud = request.getParameter("opcion");
		    
		    SQLS_conexion gd = new SQLS_conexion();
		    
		    RequestDispatcher rd = null;
		    
		    sesion = request.getSession();
		    
		    if (llegoSolicitud.equals("NuevoTrabajador")) {
		    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de reserva es "+ trab.getNombre());
    	    	
    	    	Trabajador tra = new Trabajador();   	    	
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	String llegoRut = request.getParameter("04_Rut_Trabajador");
    	    	if(!llegoRut.equals("")){
    	    		int IntllegoRut = Integer.parseInt(llegoRut);
    	    		tra.setRut(IntllegoRut);
    	    	}else{
    	    		tra.setRut(-1);
    	    	}
    	    	String llegoNombre = request.getParameter("04_Nombre");
    	    	String llegoApellidoPat = request.getParameter("04_Apellido_Pat");
    	    	String llegoApellidoMat = request.getParameter("04_Apellido_Mat");
    	    	String llegoContrasenia = request.getParameter("04_Contrasenia");
    	    	
    	    	String llegoEsAdmin = request.getParameter("04_EsAdmin");
    	    	String llegoMail = request.getParameter("04_Mail");
    	    	if(llegoContrasenia.equals("")){
    	    		llegoContrasenia = (llegoMail).substring(0, 3);
    	    	} 	    	
    			tra.setNombre(llegoNombre);
    			tra.setApellido_Pat(llegoApellidoPat);
    			tra.setApellido_Mat(llegoApellidoMat);
    			if(llegoEsAdmin!=null && llegoEsAdmin.equals("on")){
    				tra.setEsAdmin(1);
    			}else{
    				tra.setEsAdmin(0);
    			}
    			tra.setContrasenia(LetTrabajador.Encriptar(llegoContrasenia));
    			
    			tra.setEmail(llegoMail);
    			tra.setId_Estado(1);
    			
    			gd.IngresarTrabajador(tra);
    			
    	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
    	    	rd.forward(request, response);
		    }
		    
		    if (llegoSolicitud.equals("CambiarTrabajador")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	  
    	    	
    	    	String llegoTrabajador = (request.getParameter("04_Id_Trabajador"));
    	    	System.out.println("Id Trabajador: "+llegoTrabajador);
    	    	
    	    	ArrayList<Trabajador> cli = gd.getTrabajadoresFiltro("04_ID_TRABAJADOR", llegoTrabajador, "Int");
    	    	Trabajador trabajadoramodificar = cli.get(0); 
    	    	
    	    	//Obtención del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetCliente - Cambiar Trabajador: "+ usuario.getNombre());
    		
    			//Opciones - Modificar o eliminar
    			String llegoModificar = request.getParameter("ModificarTrabajador");
    			String llegoEliminar = request.getParameter("EliminarTrabajador");
    			String llegoCambiar = request.getParameter("CambiarContrasenia");
    			
    			System.out.println(llegoModificar);
    			System.out.println(llegoEliminar);
    			System.out.println(llegoCambiar);
    			
    			
    			if((llegoModificar!=null)||(llegoEliminar!=null)||(llegoCambiar!=null)){
    				if((llegoModificar!=null)){
    					//Caso de Modificar una cliente	
    					request.setAttribute("trabajador", trabajadoramodificar);
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");
    					
    					rd = request.getRequestDispatcher("/modificartrabajador.jsp");
    				}
    				if((llegoEliminar!=null)){
    					//Caso de Eliminar una reserva	
    					int corroboracion = gd.EliminarTrabajador(trabajadoramodificar.getId_Trabajador());
    					
    					if(corroboracion>0){
    						String mensaje = "Trabajador eliminado correctamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "success");
    						
    						ArrayList<ArrayList<Object>> trabajadores = (ArrayList<ArrayList<Object>>)gd.getTrabajadoresSinId("", "", "");	
    						request.setAttribute("trabajadores", trabajadores);
    						
    				    	rd = request.getRequestDispatcher("/visualizartrabajadores.jsp");
    					}else{
    						String mensaje = "ERROR! Intente eliminar nuevamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "danger");
    						
    						ArrayList<ArrayList<Object>> trabajadores = (ArrayList<ArrayList<Object>>)gd.getTrabajadoresSinId("", "", "");	
    						request.setAttribute("trabajadores", trabajadores);
    						
    				    	rd = request.getRequestDispatcher("/visualizartrabajadores.jsp");
    					}
    				}/*
    				if((llegoCambiar!=null)){
    					//Caso Cambiar Contrasenia
    					clienteamodificar.setConstrasenia(this.Desencriptar(clienteamodificar.getConstrasenia()));
    					request.setAttribute("cliente", clienteamodificar);
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");
    					
    					rd = request.getRequestDispatcher("/contraseniacliente.jsp");
    					
    				}*/
    			}
    			rd.forward(request, response);
    	  }
		    
		    /*
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
		    
		  */
		  if (llegoSolicitud.equals("ModificarTrabajador")) {
	  	    	
	  	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
	  	    	System.out.println("El usuario en la solicitud de modificacion es "+ trab.getNombre());
	  	    	
	  	    //Obtencion de parámetros de formulario
	  	    	
	  	    	Trabajador tra = new Trabajador();
	  	    	int llegoIdTrabajador = Integer.parseInt(request.getParameter("04_Id_Trabajador"));
	  			String llegoRut = request.getParameter("04_Rut_Trabajador");
    	    	if(!llegoRut.equals("")){
    	    		int IntllegoRut = Integer.parseInt(llegoRut);
    	    		tra.setRut(IntllegoRut);
    	    	}else{
    	    		tra.setRut(-1);
    	    	}
    	    	String llegoNombre = request.getParameter("04_Nombre");
    	    	String llegoApellidoPat = request.getParameter("04_Apellido_Pat");
    	    	String llegoApellidoMat = request.getParameter("04_Apellido_Mat");
    	    	String llegoEsAdmin = request.getParameter("04_EsAdmin");
    	    	String llegoMail = request.getParameter("04_Mail");
    	    	
    	    	tra.setId_Trabajador(llegoIdTrabajador);
    	    	tra.setNombre(llegoNombre);
    	    	tra.setApellido_Pat(llegoApellidoPat);
    	    	tra.setApellido_Mat(llegoApellidoMat);
    	    	if(llegoEsAdmin!=null && llegoEsAdmin.equals("on")){
    				tra.setEsAdmin(1);
    			}else{
    				tra.setEsAdmin(0);
    			}
    	    	tra.setEmail(llegoMail);
	  			
	  			gd.ActualizarTrabajador(tra);
	  			
	  			String mensaje = "Trabajador modificado correctamente";
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "success");
							
				ArrayList<ArrayList<Object>> trabajadores = (ArrayList<ArrayList<Object>>)gd.getTrabajadoresSinId("", "", "");	
				request.setAttribute("trabajadores", trabajadores);
				
		    	rd = request.getRequestDispatcher("/visualizartrabajadores.jsp");
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
    public LetTrabajador() {
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
