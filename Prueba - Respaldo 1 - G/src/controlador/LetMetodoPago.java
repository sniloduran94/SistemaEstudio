package controlador;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import conexion.JavaMail;
import conexion.SQLS_conexion;
import modelo.Campania;
import modelo.Canal_Venta;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Comuna;
import modelo.Metodo_Pago;
import modelo.Tipo_Sesion;
import modelo.Trabajador;

/**
 * Servlet implementation class LetReserva
 */
@WebServlet("/LetMetodoPago")
public class LetMetodoPago extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetMetodoPago() {
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
    	    
    	    if (llegoSolicitud.equals("NuevoMetodoPago")) {
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de canal de venta es "+ trab.getNombre());
    	    	
    	    	Metodo_Pago cv = new Metodo_Pago();   	    	
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	String llegoNombre = request.getParameter("37_Nombre");
    	    	String llegoDescripcion = request.getParameter("37_Descripcion");

    	    	cv.setNombre(llegoNombre);
    			cv.setDescripcion(llegoDescripcion);
    			
    			gd.IngresarMetodoPago(cv);
    			
    	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
    	    	rd.forward(request, response);
    	    }
    	    
    	    if (llegoSolicitud.equals("CambiarMetodoPago")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	  
    	    	
    	    	String llegoIdMetodoPago = (request.getParameter("37_Id_Metodo_Pago"));
    	    	System.out.println("Id Metodo Pago: "+llegoIdMetodoPago);
    	    	    	    	
    	    	Metodo_Pago mp = (Metodo_Pago)gd.getMetodoPagosFiltro("37_Id_Metodo_Pago", llegoIdMetodoPago, "String").get(0);
    	    	
    	    	//Obtención del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetCliente - Cambiar Cliente: "+ usuario.getNombre());
    		
    			//Opciones - Modificar o eliminar
    			String llegoEliminar = request.getParameter("EliminarMetodoPago");
    			String llegoModificar = request.getParameter("ModificarMetodoPago");
    			
    			System.out.println(llegoEliminar);
    			
    			if((llegoEliminar!=null)||(llegoModificar!=null)){
    				if((llegoEliminar!=null)){
    					//Caso de Eliminar una reserva	
    					int corroboracion = gd.EliminarMetodoPago(Integer.parseInt(llegoIdMetodoPago));
    					
    					if(corroboracion>0){
    						String mensaje = "Método de pago eliminado correctamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "success");
    						
    						ArrayList<Metodo_Pago> metodo_pago = (ArrayList<Metodo_Pago>)gd.getMetodoPagosFiltro("", "", "");	
    						request.setAttribute("metodo_pago", metodo_pago);
    						
    				    	rd = request.getRequestDispatcher("/visualizarmetodopago.jsp");
    					}else{
    						String mensaje = "ERROR! Intente eliminar nuevamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "danger");
    						
    						ArrayList<Metodo_Pago> metodo_pago = (ArrayList<Metodo_Pago>)gd.getMetodoPagosFiltro("", "", "");	
    						request.setAttribute("metodo_pago", metodo_pago);
    						
    				    	rd = request.getRequestDispatcher("/visualizarmetodopago.jsp");
    					}
    				}
    				if((llegoModificar!=null)&&(!(llegoModificar.equals("")))){
    					//Caso de Modificar una reserva	
    					request.setAttribute("metodo_pago", mp);
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");
    					    				  					    					
    					rd = request.getRequestDispatcher("/modificarmetododepago.jsp");
    				}
    			}
    			rd.forward(request, response);
    	  }
    	    
    	  if (llegoSolicitud.equals("ModificarMetodoPago")) {
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de canal de venta es "+ trab.getNombre());
    	    	    	    	
    	    	Metodo_Pago cv = new Metodo_Pago();   	    	
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	int llegoIdMetodoPago = Integer.parseInt(request.getParameter("37_Id_Metodo_Pago"));
    	    	String llegoNombre = request.getParameter("37_Nombre");
    	    	String llegoDescripcion = request.getParameter("37_Descripcion");
    	    	
    	    	cv.setId_Metodo_Pago(llegoIdMetodoPago);
    			cv.setNombre(llegoNombre);
    			cv.setDescripcion(llegoDescripcion);
    			    			
    			gd.ActualizarMetodoPago(cv);
    			
    			ArrayList<Metodo_Pago> metodo_pago = (ArrayList<Metodo_Pago>)gd.getMetodoPagosFiltro("", "", "");	
    			request.setAttribute("metodo_pago", metodo_pago);
    			
    	    	rd = request.getRequestDispatcher("/visualizarmetodopago.jsp");
    	    	rd.forward(request, response);
    	   }  
    	  
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

}
