package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import conexion.Impresora;
import conexion.Impresora2;
import conexion.SQLS_conexion;
import conexion.Text2PDF;
import conexion.UserHomeApplet;
import conexion.WriteExcel;
import modelo.Campania;
import modelo.Canal_Venta;
import modelo.Evento;
import modelo.Trabajador;
import modelo.Vendedor;

/**
 * Servlet implementation class LetCampania
 */
@WebServlet("/LetEvento")
public class LetEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Trabajador trab = null;
	HttpSession sesion; 
	
	protected void procesamientoPeticion(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
		
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
		       		String mensaje = "Se produjo un error en la inserci�n";
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
    	    	
    	    	//Obtenci�n del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetEvento - Cambiar Evento: "+ usuario.getNombre());
    		
    			//Opciones - Modificar o eliminar
    			//String llegoModificar = request.getParameter("");
    			String llegoAnular = request.getParameter("AnularEvento");
    			String llegoImprimir = request.getParameter("ImprimirEvento");
    			
    			System.out.println(llegoAnular);
    			
    			if((llegoAnular!=null)||(llegoImprimir!=null)){  
    				if((llegoAnular!=null)){ 
						//Caso de Eliminar una campa�a	 
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
    				if((llegoImprimir!=null)){  
    					
    					ArrayList<String> evento = gd.getEvento(" AND [39_Id_Evento] = "+llegoEvento);  
        				request.setAttribute("evento", evento);  					
				    	rd = request.getRequestDispatcher("/imprimirevento.jsp");  
        			}
    			}
    			rd.forward(request, response);  
		    }
		    
		    if(llegoSolicitud.equals("ImprimirEvento")){
		    	   	    	
    	    	String llegoEvento = (request.getParameter("39_Id_Evento"));
    	    	
    	    	ArrayList<String> ev = (ArrayList<String>) gd.getEvento(" AND [39_Id_Evento] ="+llegoEvento); 
    	    	Vendedor vend = (Vendedor)gd.getVendedoresSinId("", "", "").get(0);
    	    	
    	    	//Obtenci�n del usuario
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetEvento - Imprimir Evento: "+ usuario.getNombre());
    		    			
		    	Impresora tiquete=new Impresora();  

				tiquete.setDispositivo("ticket.txt");			
				for(int i=1;i<10;i++){
					tiquete.escribir("");
				}
				tiquete.escribir(vend.getVendedor());
				
				tiquete.escribir(this.insertPeriodically(vend.getDireccion(), "\r\n", 40));
				tiquete.escribir(vend.getMail()); 
				tiquete.escribir(vend.getWeb());
				tiquete.escribir("");
				tiquete.escribir(String.format("%16s %23s", "N� Ticket      :", ev.get(1)));
				tiquete.escribir("Fecha          : "+ev.get(2).substring(0, 10));
				tiquete.escribir("Fot�grafo      : "+ev.get(3));
				tiquete.escribir("Vendedor       : "+ev.get(4));
				tiquete.escribir("Cliente        : "+ev.get(5)+ " "+ev.get(6));
				tiquete.escribir("Canal de venta : "+ev.get(7));
				tiquete.escribir("Tipo sesi�n    : "+ev.get(8));

				if(Integer.parseInt(ev.get(10))>0 || Integer.parseInt(ev.get(11))>0 || Integer.parseInt(ev.get(12))>0 || Integer.parseInt(ev.get(13))>0  ){
					tiquete.escribir("________________________________________");
					tiquete.escribir("        DETALLE SESI�N COMPRADA         "); 
					tiquete.escribir("CANTIDAD    DETALLE ART�CULO      TAMA�O"); 
				}
				
				if(Integer.parseInt(ev.get(10))>0){
					tiquete.escribir(ev.get(10)+"          Fotograf�a 10x15      10x15cm");  
				}
				if(Integer.parseInt(ev.get(11))>0){
					tiquete.escribir(ev.get(11)+"          Fotograf�a 15x21      15x21cm");   
				}
				if(Integer.parseInt(ev.get(12))>0){
					tiquete.escribir(ev.get(12)+"          Ampliaci�n 20x30      20x30cm");    
				}
				if(Integer.parseInt(ev.get(13))>0){
					tiquete.escribir(ev.get(13)+"          Ampliaci�n 30x40      30x40cm");    
				}	
				
				if(Integer.parseInt(ev.get(17))>0 || Integer.parseInt(ev.get(21))>0 || Integer.parseInt(ev.get(22))>0 || Integer.parseInt(ev.get(23))>0 || Integer.parseInt(ev.get(24))>0){
					tiquete.escribir("________________________________________");  
					tiquete.escribir("                ADICIONALES             ");  
					tiquete.escribir(String.format("%2s %18s %6s %8s", "CANT.","DETALLE ARTICULO", "TAMA�O","MONTO"));
					tiquete.escribir("");
				}
				
				if(Integer.parseInt(ev.get(17))>0){
					tiquete.escribir(String.format("%2s %20s %6s %9s", "", "CD Completo", "Digital",ev.get(17)));
				}
				if(Integer.parseInt(ev.get(21))>0){
					tiquete.escribir(String.format("%2s %20s %6s %9s", ev.get(21), "Fotograf�a 10x15","10x15","X" ));
				}
				if(Integer.parseInt(ev.get(22))>0){
					tiquete.escribir(String.format("%2s %20s %6s %9s", ev.get(22), "Fotograf�a 15x21","15x21","X" ));
				}
				if(Integer.parseInt(ev.get(23))>0){
					tiquete.escribir(String.format("%2s %20s %6s %9s", ev.get(23), "Ampliaci�n 20x30","20x30","X" ));
				}
				if(Integer.parseInt(ev.get(24))>0){
					tiquete.escribir(String.format("%2s %20s %6s %9s", ev.get(24), "Ampliaci�n 30x40","30x40","X" ));
				}
				if(ev.get(20) != null && !ev.get(20).equals("null") && Integer.parseInt(ev.get(20))>0){
					tiquete.escribir(String.format("%2s %20s %6s %9s", "", "Descuento","",ev.get(20) ));
				}
				tiquete.escribir("________________________________________"); 
				tiquete.escribir("Valor Sesi�n Base  : "+ev.get(14));  
				if(ev.get(15) == null || ev.get(15).equals("null")){
					tiquete.escribir("Valor Abonado      : "+ev.get(15)); 
				}else{
					tiquete.escribir("Valor Abonado      : -"); 
				}
				tiquete.escribir("________________________________________"); 
				tiquete.escribir("Subtotal por pagar : "+ev.get(16)); 
				tiquete.escribir("Valor extras       : "+ev.get(19));
				tiquete.escribir("________________________________________"); 
				tiquete.escribir("TOTAL "+String.format("%34s\r\n", ev.get(31))); 
				tiquete.escribir("________________________________________"); 
				
				tiquete.escribir("Forma de pago:    "+String.format("%22s", ev.get(30)));
				tiquete.escribir("****************************************");
				tiquete.escribir("Seleccione sus fotos en:                ");
				tiquete.escribir(this.insertPeriodically("http://www.fotoexpressiones.com/selecciondefotos.html", "\r\n", 40));
				tiquete.escribir(this.insertPeriodically("O ingresando a www.fotoexpressiones.com, pesta�a \"SELECCION\"", "\r\n", 40));
				//Esto es para escribir una linea divisoria

				//tiquete.setRojo();
				//tiquete.setNegro();

				//tiquete.correr(10);//Esto baja 10 lineas en blanco
				//tiquete.cortar();//Esto corta el papel de la impresora
				tiquete.cerrarDispositivo();//Cierra el dispositivo y aplica el texto
				 
				Text2PDF.GenerarPDF("ticket"+ev.get(1)+".pdf"); 
				
				//Mandar PDF
				try{
					String home = System.getProperty("user.home");
					String dir = System.getProperty("user.dir");
				
					UserHomeApplet dirUsu = new UserHomeApplet(); // System.out.println("Prop "+dirUsu.getUserHome());
					   							
					String fmt = home+"/Downloads/ticket"+ev.get(1)+".pdf";
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
					
					rd= getServletContext().getRequestDispatcher("/DownloadFileServlet?fileName=ticket"+ev.get(1)+".pdf"); 
					
					response.setContentType("application/pdf");			
					response.setHeader("Content-Disposition", "attachment; filename=\"ticket"+ev.get(1)+".pdf\"");
						
					rd.include(request, response); 
					
					String filePath = home + "/Downloads";
			        String fileName = "ticket"+ev.get(1)+".pdf";
			        
			        File downloadFile = new File(filePath, fileName);
			        if(!downloadFile.exists()){
			        	
			        	return ;
			        }
			               
			        FileInputStream inStream = new FileInputStream(downloadFile);
			         
			        // if you want to use a relative path to context root:
			        String relativePath = getServletContext().getRealPath("");
			        System.out.println("relativePath = " + relativePath);
			         
			        // obtains ServletContext
			        ServletContext context = getServletContext();
			         
			        // gets MIME type of the file
			        String mimeType = context.getMimeType(filePath);
			        if (mimeType == null) {        
			            // set to binary type if MIME mapping not found
			            mimeType = "application/pdf";
			        }
			        System.out.println("MIME type: " + mimeType);
			         
			        // modifies response
				    response.setContentType(mimeType);
				    response.setContentLength((int) downloadFile.length());
				      
				       // forces download
			       String headerKey = "Content-Disposition";
			       String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
				        
			        response.setContentType("application/pdf");
			        response.setHeader(headerKey, headerValue);
			                 
			        // obtains response's output stream
			        OutputStream outStream = response.getOutputStream();
			         
			        byte[] buffer = new byte[4096];
			        int bytesRead = -1;
			         
			        while ((bytesRead = inStream.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			         
			        //Marcar como ticket impreso
			        //gd.ActualizarEventoImpreso(llegoEvento);
			        
			        inStream.close();
			        outStream.close(); 
			        rd.forward(request, response);
			        
						
				}catch(Exception e){
					System.out.println(e.getMessage());
					String mensaje = "<strong>�Se produjo un error!</strong><br><br>"
							+ "Descripci�n: "+e.getMessage();
					
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "danger");

					rd.forward(request, response);
				}
				
				
				
				ArrayList<ArrayList<Object>> eventos2 = (ArrayList<ArrayList<Object>>)gd.getEventosSinId("");
				request.setAttribute("eventos", eventos2);
				
		    	rd = request.getRequestDispatcher("/visualizareventos.jsp");
    			rd.forward(request, response);
		    }
		    
		    if (llegoSolicitud.equals("ModificarCampania")) {
	  	    	
	  	    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
	  	    	System.out.println("El usuario en la solicitud de modificacion es "+ trab.getNombre());
	  	    	
	  	    //Obtencion de par�metros de formulario
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
	  			
	  			String mensaje = "Campa�a modificada correctamente";
				
				request.setAttribute("mensaje", mensaje);
				request.setAttribute("tipomensaje", "success");
							
				ArrayList<ArrayList<Object>> campanias = (ArrayList<ArrayList<Object>>)gd.getCampa�asSinId("", "", "");	
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
            try {
				procesamientoPeticion(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (ParseException e) {
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
	
	public String insertPeriodically(
		    String text, String insert, int period)
		{
		    StringBuilder builder = new StringBuilder(
		         text.length() + insert.length() * (text.length()/period)+1);

		    int index = 0;
		    String prefix = "";
		    while (index < text.length())
		    {
		        // Don't put the insert in the very first iteration.
		        // This is easier than appending it *after* each substring
		        builder.append(prefix);
		        prefix = insert;
		        builder.append(text.substring(index, 
		            Math.min(index + period, text.length())));
		        index += period;
		    }
		    return builder.toString();
		}
	
}
