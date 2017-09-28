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
import modelo.Tipo_Sesion;
import modelo.Trabajador;

/**
 * Servlet implementation class LetReserva
 */
@WebServlet("/LetTipoSesion")
public class LetTipoSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetTipoSesion() {
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
    	    
    	    if (llegoSolicitud.equals("NuevoTipoSesion")) {
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de tipo de sesion es "+ trab.getNombre());
    	    	    	    	
    	    	Tipo_Sesion ts = new Tipo_Sesion ();   	    	
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	String llegoNombre = request.getParameter("34_Tipo_Sesion");
    	    		
    	    	ts.setTipo_Sesion(llegoNombre);    	    	
    			ts.setId_Estado(1);
    			
    			gd.IngresarTipoSesion(ts);
    			
    	    	rd = request.getRequestDispatcher("/indextrabajador.jsp");
    	    	rd.forward(request, response);
    	    }
    	    
    	    
    	    if (llegoSolicitud.equals("CambiarTipoSesion")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	  
    	    	
    	    	String llegoTipoSesion = (request.getParameter("34_ID_TIPO_SESION"));
    	    	System.out.println("Id TipoSesion: "+llegoTipoSesion);
    	    	
    	    	ArrayList<Tipo_Sesion> ts = gd.getTiposSesionesFiltro("34_Id_Tipo_Sesion", llegoTipoSesion, "Int");
    	    	Tipo_Sesion tipoamodificar = ts.get(0); 
    	    	
    	    	//Obtención del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetTipoSesion - Cambiar Tipo Sesion: "+ usuario.getNombre());
    		
    			//Opciones - Modificar o eliminar
    			String llegoModificar = request.getParameter("ModificarTipoSesion");
    			String llegoEliminar = request.getParameter("EliminarTipoSesion");
    			
    			System.out.println(llegoModificar);
    			System.out.println(llegoEliminar);
    			
    			if((llegoModificar!=null)||(llegoEliminar!=null)){
    				if((llegoModificar!=null)){
    					//Caso de Modificar un tipo de sesion	
    					request.setAttribute("tiposesion", tipoamodificar);
    					   					
    					trab = (Trabajador) sesion.getAttribute("usuario");    					
    					    					
    					rd = request.getRequestDispatcher("/modificartiposesion.jsp");
    				}
    				if((llegoEliminar!=null)){
    					//Caso de Eliminar un tipo de sesion	
    					int corroboracion = gd.EliminarTipoSesion(tipoamodificar.getId_Tipo_Sesion());
    					
    					if(corroboracion>0){
    						String mensaje = "Tipo de sesión eliminado correctamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "success");
    						
    						ArrayList<Tipo_Sesion> tipossesiones = (ArrayList<Tipo_Sesion>)gd.getTiposSesiones();	
    						request.setAttribute("tipossesiones", tipossesiones);
    						
    						rd = request.getRequestDispatcher("/visualizartipossesiones.jsp");
    					}else{
    						String mensaje = "ERROR! Intente eliminar nuevamente";
    						
    						request.setAttribute("mensaje", mensaje);
    						request.setAttribute("tipomensaje", "danger");
    						
    						ArrayList<Tipo_Sesion> tipossesiones = (ArrayList<Tipo_Sesion>)gd.getTiposSesiones();	
    						request.setAttribute("tipossesiones", tipossesiones);
    						
    						rd = request.getRequestDispatcher("/visualizartipossesiones.jsp");
    					}
    				}
    			}
    			rd.forward(request, response);
    	  }
    	  
    	    
    	  
    	  if (llegoSolicitud.equals("ModificarTipoSesion")) {
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de tipo de sesion"+ trab.getNombre());
    	    	    	    	
    	    	Tipo_Sesion ts = new Tipo_Sesion();   	    	
    	    	
    	    	//Obtencion de parámetros de formulario
    	    	int llegoIdTipo= Integer.parseInt(request.getParameter("34_Id_Tipo_Sesion"));
    	    	String llegoNombre = request.getParameter("34_Tipo_Sesion");
    			
    	    	ts.setId_Tipo_Sesion(llegoIdTipo);
    	    	ts.setTipo_Sesion(llegoNombre);
    	    	ts.setId_Estado(1);
    			
    			gd.ActualizarTipoSesion(ts);
    			
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
