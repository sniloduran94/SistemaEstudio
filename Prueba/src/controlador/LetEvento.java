package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import conexion.SQLS_conexion;
import modelo.Campania;
import modelo.Canal_Venta;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Evento;
import modelo.Trabajador;

/**
 * Servlet implementation class LetCampania
 */
@WebServlet("/LetEvento")
public class LetEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion; 
	
	protected void procesamientoPeticion(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException,
           	SQLException, ParseException, java.text.ParseException {
		
			String llegoSolicitud = request.getParameter("opcion");
		    
		    SQLS_conexion gd = new SQLS_conexion();
		    
		    RequestDispatcher rd = null;
		    
		    sesion = request.getSession();
		     
		    if (llegoSolicitud.equals("NuevoEvento")) {
		    	 
		    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de evento es "+ trab.getNombre());	    	
    	    	
		    	String llegoFormaPago = request.getParameter("39_Forma_Pago");
		    	String llegoMovimiento = request.getParameter("39_Movimiento");
		    	int llegoValor = Integer.parseInt(request.getParameter("39_Valor"));		    	
		    	String llegoItem = request.getParameter("39_Item");
		    	String llegoDescripcion = request.getParameter("17_Descripcion");
		    	int llegoNumeroBoleta = 0;
		    	if(!request.getParameter("39_Numero_Boleta").equals("")){
		    		llegoNumeroBoleta = Integer.parseInt(request.getParameter("39_Numero_Boleta"));
		    	}		    	
		    	String llegoTipoDoc = request.getParameter("39_Tipo_Doc");
		    	
		    	Evento ev = new Evento ();
    	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    	    	Date ahora = new Date();
    	    	
    	    	ev.setFecha(sdf.format(ahora));
    	    	
    	    	ev.setForma_Pago(llegoFormaPago);
    	    	if(llegoMovimiento.equals("Egreso")){
        	    	ev.setValor(llegoValor*-1);
    	    	}else{
        	    	ev.setValor(llegoValor);
    	    	}
    	    	ev.setTrabajador(trab.getId_Trabajador());
    	    	ev.setItem(llegoItem);
    	    	ev.setDescripcion(llegoDescripcion);
    	    	ev.setEstado(1); 
    	    	 if(!request.getParameter("39_Numero_Boleta").equals("")){
    	    		ev.setNumero_Boleta(llegoNumeroBoleta);
    	    	}
    	    	ev.setMovimiento(llegoMovimiento);    
    	    	ev.setTipo_Doc(llegoTipoDoc);
		       	
		       	int resultado = gd.IngresarEvento(ev);
		       	
		       	if(resultado>0){
					String mensaje = "Evento ingresado correctamente";
				
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "success");		       		
		       	}else{
		       		String mensaje = "Se produjo un error en la inserción";
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "success");
		       	}
		       	
		    	ArrayList<ArrayList<Object>> eventos = (ArrayList<ArrayList<Object>>)gd.getEventosSinId("");	
				request.setAttribute("eventos", eventos);
				
		    	rd = request.getRequestDispatcher("/visualizareventos.jsp");
		    	rd.forward(request, response);  	
		    }		    
		    
		    if (llegoSolicitud.equals("CambiarEvento")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	  
    	    	
    	    	String llegoEvento = (request.getParameter("39_Id_Evento"));
    	    	
    	    	ArrayList<ArrayList<Object>> eventos= (ArrayList<ArrayList<Object>>) gd.getEventosSinId(" [39_Id_Evento] ="+llegoEvento);
    	    	Evento eventoamodificar = (Evento)eventos.get(0).get(0); 
    	    	
    	    	//Obtención del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetEvento - Cambiar Evento: "+ usuario.getNombre());
    		
    			//Opciones - Modificar o eliminar
    			//String llegoModificar = request.getParameter("");
    			String llegoAnular = request.getParameter("AnularEvento");
    			
    			System.out.println(llegoAnular);
    			
    			if((llegoAnular!=null)){
					//Caso de Eliminar una campaña	
					int corroboracion = gd.EliminarEvento(eventoamodificar.getId_Evento());
					//int corroboracion2 = gd.EliminarSesionAuxiliar(eventoamodificar.getId_Auxiliar());
					
					//if(corroboracion>0 && corroboracion2>0){
					System.out.println(corroboracion);
					if(corroboracion>0){
						String mensaje = "Evento anulado correctamente";
						
						request.setAttribute("mensaje", mensaje);
						request.setAttribute("tipomensaje", "success");
						
						ArrayList<ArrayList<Object>> eventos2 = (ArrayList<ArrayList<Object>>)gd.getEventosSinId("");	
						request.setAttribute("eventos", eventos2);
						
				    	rd = request.getRequestDispatcher("/visualizareventos.jsp");
					}else{
						String mensaje = "ERROR! Intente anular nuevamente";
						
						request.setAttribute("mensaje", mensaje);
						request.setAttribute("tipomensaje", "danger");
						
						ArrayList<ArrayList<Object>> eventos2 = (ArrayList<ArrayList<Object>>)gd.getEventosSinId("");
						request.setAttribute("eventos", eventos2);
						
				    	rd = request.getRequestDispatcher("/visualizareventos.jsp");
					}
				}
    			rd.forward(request, response);
		    }
		    
		    if (llegoSolicitud.equals("ModificarCampania")) {
	  	    	
	  	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
	  	    	System.out.println("El usuario en la solicitud de modificacion es "+ trab.getNombre());
	  	    	
	  	    //Obtencion de parámetros de formulario
	  	    	int llegoIdCampania = Integer.parseInt(request.getParameter("17_Id_Campania"));
	  	    	
	  	    	String llegoNombre = request.getParameter("17_Nombre");
		    	int llegoIdCanalVenta = Integer.parseInt(request.getParameter("14_Id_Canal_Venta"));
		    	String llegoInicioVigencia = request.getParameter("17_Inicio_Vigencia");		    
		    	String llegoFinVigencia = request.getParameter("17_Fin_Vigencia");
		    	
		    	
		    	int llegoPrecio = Integer.parseInt(request.getParameter("17_Precio")) ;
		    	int llegoMaxPersonas = Integer.parseInt(request.getParameter("17_Maximo_Personas"));
		    	String llegoDescripcion = request.getParameter("17_Descripcion");
		    	String llegoPoseeCD = request.getParameter("17_Posee_CD");
		    	int llegoCantFotosCD = Integer.parseInt(request.getParameter("17_Cant_Fotos_CD"));
		    	int llegoCant10x15 = Integer.parseInt(request.getParameter("17_Cant_10x15"));
		    	int llegoCant15x21 = Integer.parseInt(request.getParameter("17_Cant_15x21")); 
		    	int llegoCant20x30 = Integer.parseInt(request.getParameter("17_Cant_20x30")); 
		    	int llegoCant30x40 = Integer.parseInt(request.getParameter("17_Cant_30x40")); 
		    	int llegoPrecioAdicional = Integer.parseInt(request.getParameter("17_Precio_Adicional"));
		    	int llegoPrecioReagendamiento = Integer.parseInt(request.getParameter("17_Precio_Reagendamiento"));
		    	String llegoDescripcionAdicional = request.getParameter("17_Descripcion_Adicional");
	  			int llegoAbono = Integer.parseInt(request.getParameter("17_Abono")); 
	  			
		    	Campania cc = new Campania();
		    	cc.setId_Campania(llegoIdCampania);
		    	cc.setNombre(llegoNombre);
		    	cc.setId_Canal_Venta(llegoIdCanalVenta);
		    	cc.setInicio_Vigencia(gd.ConvertirDia(llegoInicioVigencia));
		    	cc.setFin_Vigencia(gd.ConvertirDia(llegoFinVigencia));
		    	cc.setPrecio(llegoPrecio);
		    	cc.setMaximo_Personas(llegoMaxPersonas);
		    	cc.setDescripcion(llegoDescripcion);
		       	if((llegoPoseeCD!=null)&&(llegoPoseeCD.equals("on"))){
    				cc.setCD(true);
    				cc.setCant_Fotos_CD(llegoCantFotosCD);
    			}else{
    				cc.setCD(false);
    				cc.setCant_Fotos_CD(0);
    			}
		       	cc.setCant_10x15(llegoCant10x15);
		       	cc.setCant_15x21(llegoCant15x21);
		       	cc.setCant_20x30(llegoCant20x30);
		       	cc.setCant_30x40(llegoCant30x40);
		       	cc.setPrecio_Adicional(llegoPrecioAdicional);
		       	cc.setPrecio_Reagendamiento(llegoPrecioReagendamiento);
		       	cc.setDescripcion_Adicional(llegoDescripcionAdicional);
		       	cc.setAbono(llegoAbono);
		       	
		       	System.out.println("Valor abono: "+ cc.getAbono());
    				  			
	  			gd.ActualizarCampania(cc);
	  			
	  			String mensaje = "Campaña modificada correctamente";
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "success");
							
				ArrayList<ArrayList<Object>> campanias = (ArrayList<ArrayList<Object>>)gd.getCampañasSinId("", "", "");	
				request.setAttribute("campanias", campanias);
				
				ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
				request.setAttribute("canalesventas", canalesdeventa);
				
		    	rd = request.getRequestDispatcher("/visualizarcampanias.jsp");
		    	rd.forward(request, response);
	    	  }
		    		    
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetEvento() {
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
