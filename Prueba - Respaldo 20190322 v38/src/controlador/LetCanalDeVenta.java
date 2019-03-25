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
import modelo.Canal_Venta;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Comuna;
import modelo.Trabajador;

/**
 * Servlet implementation class LetReserva
 */
@WebServlet("/LetCanalDeVenta")
public class LetCanalDeVenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetCanalDeVenta() {
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
    	    
    	    if (llegoSolicitud.equals("NuevoCanalDeVenta")) {
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de canal de venta es "+ trab.getNombre());
    	    	    	    	
    	    	Canal_Venta cv = new Canal_Venta();   	    	
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	String llegoNombre = request.getParameter("14_Canal_Venta");
    	    	String llegoDescripcion = request.getParameter("14_Descripcion");
    	    	String llegoRequiereCupon = request.getParameter("14_Requiere_Cupon");
    	    	System.out.println(llegoRequiereCupon);

    			cv.setCanal(llegoNombre);
    			cv.setDescripcion(llegoDescripcion);
    			if(llegoRequiereCupon!=null && llegoRequiereCupon.equals("on")){
    				cv.setRequiere_Cupon(true);
    			}else{
    				cv.setRequiere_Cupon(false);
    			}
    			cv.setId_Estado(1);
    			
    			gd.IngresarCanalDeVenta(cv);
    			
    	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
    	    	rd.forward(request, response);
    	    }
    	    
    	    if (llegoSolicitud.equals("CambiarCanalDeVenta")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	  
    	    	
    	    	String llegoCanal = (request.getParameter("14_Id_Canal_Venta"));
    	    	System.out.println("Id Canal: "+llegoCanal);
    	    	
    	    	ArrayList<Canal_Venta> cvs = gd.getCanalesVentasFiltro("14_Id_Canal_Venta", llegoCanal, "Int");
    	    	Canal_Venta canalamodificar = cvs.get(0); 
    	    	
    	    	//Obtención del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetCliente - Cambiar Cliente: "+ usuario.getNombre());
    		
    			//Opciones - Modificar o eliminar
    			String llegoModificar = request.getParameter("ModificarCanalDeVenta");
    			String llegoEliminar = request.getParameter("EliminarCanalDeVenta");
    			
    			System.out.println(llegoModificar);
    			System.out.println(llegoEliminar);
    			
    			if((llegoModificar!=null)||(llegoEliminar!=null)){
    				if((llegoModificar!=null)){
    					//Caso de Modificar una cliente	
    					request.setAttribute("canaldeventa", canalamodificar);
    					
    					System.out.println("Descripcion "+ canalamodificar.getDescripcion());
    					
    					trab = (Trabajador) sesion.getAttribute("usuario");    					
    					    					
    					rd = request.getRequestDispatcher("/modificarcanaldeventa.jsp");
    				}
    				if((llegoEliminar!=null)){
    					//Caso de Eliminar una reserva	
    					int corroboracion = gd.EliminarCanalDeVenta(canalamodificar.getId_Canal_Venta());
    					
    					if(corroboracion>0){
    						String mensaje = "Canal de venta eliminado correctamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "success");
    						
    						ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
    						request.setAttribute("canalesdeventa", canalesdeventa);
    						
    				    	rd = request.getRequestDispatcher("/visualizarcanalesdeventa.jsp");
    					}else{
    						String mensaje = "ERROR! Intente eliminar nuevamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "danger");
    						
    						ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
    						request.setAttribute("canalesdeventa", canalesdeventa);
    						
    				    	rd = request.getRequestDispatcher("/visualizarcanalesdeventa.jsp");
    					}
    				}
    			}
    			rd.forward(request, response);
    	  }
    	    
    	  if (llegoSolicitud.equals("ModificarCanalDeVenta")) {
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de canal de venta es "+ trab.getNombre());
    	    	    	    	
    	    	Canal_Venta cv = new Canal_Venta();   	    	
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	int llegoIdCanal = Integer.parseInt(request.getParameter("14_Id_Canal_Venta"));
    	    	String llegoNombre = request.getParameter("14_Canal_Venta");
    	    	String llegoDescripcion = request.getParameter("14_Descripcion");
    	    	String llegoRequiereCupon = request.getParameter("14_Requiere_Cupon");
    	    	
    	    	cv.setId_Canal_Venta(llegoIdCanal);
    			cv.setCanal(llegoNombre);
    			cv.setDescripcion(llegoDescripcion);
    			if(llegoRequiereCupon!=null && llegoRequiereCupon.equals("on")){
    				cv.setRequiere_Cupon(true);
    			}else{
    				cv.setRequiere_Cupon(false);
    			}
    			cv.setId_Estado(1);
    			
    			gd.ActualizarCanalDeVenta(cv);
    			
    	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
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
