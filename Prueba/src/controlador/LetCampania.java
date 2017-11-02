package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import modelo.Trabajador;

/**
 * Servlet implementation class LetCampania
 */
@WebServlet("/LetCampania")
public class LetCampania extends HttpServlet {
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
		    
		    if (llegoSolicitud.equals("NuevoCampania")) {
		    	String llegoNombre = request.getParameter("17_Nombre");
		    	int llegoIdCanalVenta = Integer.parseInt(request.getParameter("14_Id_Canal_Venta"));
		    	String llegoInicioVigencia = request.getParameter("17_Inicio_Vigencia");		    	
		    	String llegoFinVigencia = request.getParameter("17_Fin_Vigencia");
		    	int llegoPrecio = Integer.parseInt(request.getParameter("17_Precio")) ;
		    	int llegoMaxPersonas = Integer.parseInt(request.getParameter("17_Maximo_Personas"));
		    	String llegoDescripcion = request.getParameter("17_Descripcion");
		    	String llegoPoseeCD = request.getParameter("17_Posee_CD");
		    	int llegoCantFotosCD = 0;
		    	if(request.getParameter("17_Cant_Fotos_CD").equals("")){
		    		llegoCantFotosCD = 0;
		    	}else{
		    		llegoCantFotosCD = Integer.parseInt(request.getParameter("17_Cant_Fotos_CD"));
		    	}
		    	int llegoCant10x15 = Integer.parseInt(request.getParameter("17_Cant_10x15"));
		    	int llegoCant15x21 = Integer.parseInt(request.getParameter("17_Cant_15x21")); 
		    	int llegoCant20x30 = Integer.parseInt(request.getParameter("17_Cant_20x30")); 
		    	int llegoCant30x40 = Integer.parseInt(request.getParameter("17_Cant_30x40"));
		    	int llegoPrecioAdicional = Integer.parseInt(request.getParameter("17_Precio_Adicional"));
		    	int llegoPrecioReagendamiento = Integer.parseInt(request.getParameter("17_Precio_Reagendamiento"));
		    	String llegoDescripcionAdicional = request.getParameter("17_Descripcion_Adicional");
		    	int llegoAbono = Integer.parseInt(request.getParameter("17_Abono"));
		    	
		    	Campania cc = new Campania();
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
		       	
		       	int resultado = gd.IngresarCampania(cc);
		       	
		       	if(resultado>0){
					String mensaje = "Campaña ingresada correctamente";
				
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "success");		       		
		       	}else{
		       		String mensaje = "Se produjo un error en la inserción";
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "success");
		       	}
		       	
		       	ArrayList<ArrayList<Object>> campanias = (ArrayList<ArrayList<Object>>)gd.getCampañasSinId("", "", "");	
				request.setAttribute("campanias", campanias);
				

				ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
				request.setAttribute("canalesventas", canalesdeventa);
				
		    	rd = request.getRequestDispatcher("/visualizarcampanias.jsp");
		    	rd.forward(request, response);
		       	
		    }
		    
		    
		    if (llegoSolicitud.equals("CambiarCampania")) {
      	    	
    	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	  
    	    	
    	    	String llegoCampania = (request.getParameter("17_Id_Campania"));
    	    	System.out.println("Id Campania: "+llegoCampania);
    	    	
    	    	ArrayList<Campania> camp = gd.getCampañasFiltro("17_ID_CAMPANIA", llegoCampania, "Int");
    	    	Campania campaniaamodificar = camp.get(0); 
    	    	
    	    	//Obtención del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetCampania - Cambiar Campania: "+ usuario.getNombre());
    		
    			//Opciones - Modificar o eliminar
    			String llegoModificar = request.getParameter("ModificarCampania");
    			String llegoEliminar = request.getParameter("EliminarCampania");
    			
    			System.out.println(llegoModificar);
    			System.out.println(llegoEliminar);
    			
    			if((llegoModificar!=null)||(llegoEliminar!=null)){
    				if(llegoModificar!=null){
    					//Caso de Modificar una campaña	
    					request.setAttribute("campania", campaniaamodificar);

    					request.setAttribute("canalesventas", (ArrayList<Canal_Venta>)(gd.getCanalesVentas()));
    			   
    					rd = request.getRequestDispatcher("/modificarcampania.jsp");
    				}
    			}
    			if((llegoEliminar!=null)){
					//Caso de Eliminar una campaña	
					int corroboracion = gd.EliminarCampania(campaniaamodificar.getId_Campania());
					
					if(corroboracion>0){
						String mensaje = "Campaña eliminada correctamente";
						
						request.setAttribute("mensaje", mensaje);
						request.setAttribute("tipomensaje", "success");
						
						ArrayList<ArrayList<Object>> campanias = (ArrayList<ArrayList<Object>>)gd.getCampañasSinId("", "", "");	
						request.setAttribute("campanias", campanias);
						
						ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
						request.setAttribute("canalesventas", canalesdeventa);
						
				    	rd = request.getRequestDispatcher("/visualizarcampanias.jsp");
					}else{
						String mensaje = "ERROR! Intente eliminar nuevamente<br><br>Revise las dependencias de esta campaña (Reservas asociadas, etc)";
						
						request.setAttribute("mensaje", mensaje);
						request.setAttribute("tipomensaje", "danger");
						
						ArrayList<ArrayList<Object>> campanias = (ArrayList<ArrayList<Object>>)gd.getCampañasSinId("", "", "");	
						request.setAttribute("campanias", campanias);
						

						ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
						request.setAttribute("canalesventas", canalesdeventa);
						
				    	rd = request.getRequestDispatcher("/visualizarcampanias.jsp");
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
		    
		    if (llegoSolicitud.equals("FiltroCampania")) {
		    	
		    	Trabajador usuario =  null;
		    	usuario = (Trabajador) sesion.getAttribute("usuario");
				System.out.println("Nombre en LetSesion - Visualizar Campanias: "+ usuario.getNombre());
		    	
				this.InvalidarFiltros();
    			
				String CondicionDeBusqueda = "";
	  			boolean and = false;
				
    			String llegoCanal = "";
    			String llegoNombre = "";
    			
    			llegoCanal = request.getParameter("14_Id_Canal_Venta");
    			llegoNombre = request.getParameter("17_Nombre");
    			
    			if(!llegoCanal.equals("") && !llegoCanal.equals("0")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [14_Canal_Venta].[14_Id_Canal_Venta] = "+llegoCanal+" ";
					and = true;
				}
    			if(!llegoNombre.equals("")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [17_Nombre] LIKE '%"+llegoNombre+"%' ";
					and = true;
				}
    			if(!CondicionDeBusqueda.isEmpty()){
					CondicionDeBusqueda = "AND "+CondicionDeBusqueda;
				}
    			
    			/*
				if(request.getParameter("14_Id_Canal_Venta")!=null){
    	  			sesion.setAttribute("14_Id_Canal_Venta", request.getParameter("14_Id_Canal_Venta"));
				}
				
				if(request.getParameter("17_Nombre")!=null){
    	  			sesion.setAttribute("17_Nombre", request.getParameter("17_Nombre"));
				}*/
    			
				
		    	ArrayList<ArrayList<Object>> campanias = (ArrayList<ArrayList<Object>>)gd.getCampañasSinId(CondicionDeBusqueda);	
				request.setAttribute("campanias", campanias);
				
				ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
				request.setAttribute("canalesventas", canalesdeventa);
							
		    	rd = request.getRequestDispatcher("/visualizarcampanias.jsp");
		    	rd.forward(request, response);
		    }
		    
		    if (llegoSolicitud.equals("FiltroEstadistica")) {
		    	
		    	Trabajador usuario =  null;
		    	boolean and = false;
		    	usuario = (Trabajador) sesion.getAttribute("usuario");
				System.out.println("Nombre en LetCampania - Estadistica Campanias: "+ usuario.getNombre());
		    	
				String llegoFechaInicio = request.getParameter("Inicio");
				String llegoFechaFin = request.getParameter("Fin");
				String llegoCampania = request.getParameter("17_Nombre");
				String llegoPrecio = request.getParameter("17_Precio");
				String llegoCanalVenta = request.getParameter("14_Id_Canal_Venta");
								
				String CondicionDeBusqueda = " WHERE ";
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String Fecha12 = null;
				
				if(!(llegoFechaInicio.equals(""))){
					java.util.Date DiaSiguiente = (java.util.Date) sdf.parse(llegoFechaInicio);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DiaSiguiente); // Configuramos la fecha que se recibe
					calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
					Fecha12 = sdf.format(calendar.getTime());	
				}
						
				if((!(llegoFechaInicio.equals("")))||(!(llegoFechaFin.equals("")))){
					if((!(llegoFechaInicio.equals("")))&&(llegoFechaFin.equals(""))){
						 CondicionDeBusqueda += " [16_FECHA] BETWEEN '"+llegoFechaInicio+"' AND '"+Fecha12+"' ";
					}
					if((!(llegoFechaFin.equals("")))&&(!(llegoFechaInicio.equals("")))){
						CondicionDeBusqueda += " [16_FECHA] BETWEEN '"+llegoFechaInicio+"' AND '"+llegoFechaFin+"' ";
					} 
					and = true;
				}else{
					CondicionDeBusqueda += " ";
				}
				
				if(!llegoCampania.equals("")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [17_Campania].[17_Nombre] LIKE '%"+llegoCampania+"%' ";
					and = true;
				}
								
				if(!llegoPrecio.equals("")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [17_Campania].[17_Precio] = "+llegoPrecio+" ";
					and = true;
				}
				
				if(!llegoCanalVenta.equals("") && !llegoCanalVenta.equals("0")){
					CondicionDeBusqueda += (and)?" AND ":"";
					CondicionDeBusqueda += " [14_Canal_Venta].[14_Id_Canal_Venta] = "+llegoCanalVenta+" ";
					and = true;
				}
					
				this.InvalidarFiltros();
				
				ArrayList<ArrayList<String>> campanias;
				if(and){
					campanias = (ArrayList<ArrayList<String>>)gd.EstadisticaCampania(CondicionDeBusqueda);	
				}else{
					campanias = (ArrayList<ArrayList<String>>)gd.EstadisticaCampania("");	
				}
				
				request.setAttribute("campanias", campanias);
				
				ArrayList<Canal_Venta> canalesdeventa = (ArrayList<Canal_Venta>)gd.getCanalesVentas();	
				request.setAttribute("canalesventas", canalesdeventa);
				
		    	rd = request.getRequestDispatcher("/visualizarestadisticascampanias.jsp");
		    	rd.forward(request, response);
		    }
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetCampania() {
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
