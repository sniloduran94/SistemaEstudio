package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import conexion.JavaMail;
import conexion.SQLS_conexion;
import conexion.Text2PDF;
import conexion.UserHomeApplet;
import modelo.Campania;
import modelo.Canal_Venta;
import modelo.Evento;
import modelo.Trabajador;
import modelo.Vendedor;
import modelo.Cliente;

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
		     
		    if (llegoSolicitud.equals("AnularEvento")) { 
		    	
		    	System.out.println("BANDERA 1 - ANULAR EVENTO");
		    	 
		    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de evento es "+ trab.getNombre());	    	
    	    	

		    	String llegoIdEvento = request.getParameter("39_Id_Evento");
		    	String llegoMotivoAnulacion = request.getParameter("39_Motivo_Anulacion");
    	    	                   
		       			       	
		        int corroboracion = gd.EliminarEvento(Integer.parseInt(llegoIdEvento));
		    	int resultado = gd.ActualizarEventoMotivoAnulacion(Integer.parseInt(llegoIdEvento), llegoMotivoAnulacion);
		        
				//int corroboracion2 = gd.EliminarSesionAuxiliar(eventoamodificar.getId_Auxiliar());
		    	
				//if(corroboracion>0 && corroboracion2>0){

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

		    if (llegoSolicitud.equals("NuevoEvento")) {
		    	 
		    	Trabajador trab = (Trabajador) sesion.getAttribute("usuario");    	    	
    	    	System.out.println("El usuario en la solicitud de evento es "+ trab.getNombre());	    	
    	    	
		    	String llegoFormaPago = request.getParameter("39_Forma_Pago");
		    		System.out.println("llegoFormaPago  "+ llegoFormaPago);
		    	String llegoMovimiento = request.getParameter("39_Movimiento");
		    		System.out.println("llegoMovimiento  "+ llegoMovimiento);
		    	int llegoValor = Integer.parseInt(request.getParameter("39_Valor"));		    	
		    		System.out.println("llegoValor  "+ llegoValor);
		    	String llegoItem = request.getParameter("39_Item");
		    		System.out.println("llegoItem  "+ llegoItem);
		    	String llegoDescripcion = request.getParameter("17_Descripcion");
		    		System.out.println("llegoDescripcion  "+ llegoDescripcion);
		    	double llegoNumeroBoleta = 0;
		    	if(!request.getParameter("39_Numero_Boleta").equals("")){
		    		llegoNumeroBoleta = Double.parseDouble(request.getParameter("39_Numero_Boleta")); //Integer.parseInt(request.getParameter("39_Numero_Boleta"));
		    	}		    	
		    	String llegoTipoDoc = request.getParameter("39_Tipo_Doc");
		    		System.out.println("llegoTipoDoc  "+ llegoTipoDoc);
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
    	    	
    	    	
    	    	 if(request.getParameter("39_Numero_Boleta").equals("0")){
    	    		ev.setNumero_Boleta(-1);
    	    	}else{
    	    		ev.setNumero_Boleta(llegoNumeroBoleta);
    	    	}
    	    	
    	    	ev.setMovimiento(llegoMovimiento);    
    	    	ev.setTipo_Doc(llegoTipoDoc);
		       	
		       	int resultado = gd.IngresarEvento(ev);
		       	
		       	if(resultado>0){
					String mensaje = "Evento ingresado correctamente";
				
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "success");		       		
	    	    	System.out.println("Resultado de evento  "+ mensaje);	    	
		       	}else{
		       		String mensaje = "Se produjo un error en la inserción";
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "success");
	    	    	System.out.println("Resultado de evento  "+ mensaje);	    	
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
    	    	
    	    	Trabajador usuario =  (Trabajador) sesion.getAttribute("usuario");
    			System.out.println("Nombre en LetEvento - Cambiar Evento: "+ usuario.getNombre());
    		
    			String llegoAnular = request.getParameter("AnularEvento");
    			String llegoImprimir = request.getParameter("ImprimirEvento");
    			
    			//System.out.println(llegoAnular);
    			
    			if((llegoAnular!=null)||(llegoImprimir!=null)){  
    				if((llegoAnular!=null)){ 

    			    	String llegoIdEvento = request.getParameter("39_Id_Evento");
    			    	String llegoMotivoAnulacion = request.getParameter("39_Motivo_Anulacion");

    			    	trab = (Trabajador) sesion.getAttribute("usuario");

					    int corroboracion = gd.EliminarEvento(eventoamodificar.getId_Evento());
    			    	int resultado = gd.ActualizarEventoMotivoAnulacion(eventoamodificar.getId_Evento(), llegoMotivoAnulacion);

						if(corroboracion>0){

// Mantencion Evolutiva: Enviar correo señalando el motivo de anulación
							
	    	    			JavaMail mail = new JavaMail();

	    	    			Vendedor Vend = gd.getVendedoresSinId("38_Id_Vendedor", Integer.toString(mail.IdVendedor), "Int").get(0);
	    	    			String logoIcono = Vend.getLogo_Icono();
	    	    			
	    	    			String AsuntoDeCorreo = "Anulación de Evento ["+llegoIdEvento+"]";


	    	    	    	ArrayList<Trabajador> trab2 = gd.getTrabajadoresFiltro("04_Email", "jonathan.herrera@advancing.cl", "String");
	    	    	    	Trabajador trabRecibeMailAnulacion = trab2.get(0); 
	    	    	    	
	    	    			String correo = trabRecibeMailAnulacion.getEmail();
	    	    			String correo2 = usuario.getEmail();
	    	    			String correo3 = "salomon.nilo@advancing.cl"; 

	    	    			String MensajeDeCorreo = "Estimado/a <strong>" + trabRecibeMailAnulacion.getNombre()+" "+trabRecibeMailAnulacion.getApellido_Pat() + ":</strong> <br><br><br>"
	    	    					+ " Se ha <strong>anulado el evento "+llegoIdEvento+"</strong>.<br><br>"
	    	    					+ "<br><br> Motivo Anulación: " + llegoMotivoAnulacion + "<br><br>"
	    	    					+". </strong><br><br><center><img src=\""+logoIcono+"\"/></center>" ;
	    	    			
	    	    			String correoEnvia = Vend.getMail();
	    	    	    	String nombreEnvia = Vend.getVendedor();
	    	    	    	String clave = Vend.getMail_PW();

	    	    			mail.mandarCorreo(correo, correo2, correo3, MensajeDeCorreo, AsuntoDeCorreo, correoEnvia, nombreEnvia, clave, false);

// FIN Mantencion Evolutiva: Enviar correo señalando el motivo de anulación

							String mensaje = "Evento " +llegoIdEvento+" anulado correctamente";

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

    	    	//Obtención del usuario
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
				tiquete.escribir("Fono "+vend.getTelefono());
				tiquete.escribir("");
				tiquete.escribir(String.format("%16s %23s", "N° Ticket      :", ev.get(1)));
				tiquete.escribir("Fecha          : "+ev.get(2).substring(0, 10));
				tiquete.escribir("Fotógrafo      : "+ev.get(3));
				tiquete.escribir("Vendedor       : "+ev.get(4));
				tiquete.escribir("Cliente        : "+ev.get(5)+ " "+ev.get(6));
				tiquete.escribir("Canal de venta : "+ev.get(7));
				tiquete.escribir("Tipo sesión    : "+ev.get(8));

				if(Integer.parseInt(ev.get(10))>0 || Integer.parseInt(ev.get(11))>0 || Integer.parseInt(ev.get(12))>0 || Integer.parseInt(ev.get(13))>0  ){
					tiquete.escribir("________________________________________");
					tiquete.escribir("        DETALLE SESIÓN COMPRADA         "); 
					tiquete.escribir("CANTIDAD    DETALLE ARTÍCULO      TAMAÑO"); 
				}
				
				if(Integer.parseInt(ev.get(10))>0){
					tiquete.escribir(ev.get(10)+"           Fotografía 10x15     10x15cm");  
				}
				if(Integer.parseInt(ev.get(11))>0){
					tiquete.escribir(ev.get(11)+"           Fotografía 15x21     15x21cm");   
				}
				if(Integer.parseInt(ev.get(12))>0){
					tiquete.escribir(ev.get(12)+"           Ampliación 20x30     20x30cm");    
				}
				if(Integer.parseInt(ev.get(13))>0){
					tiquete.escribir(ev.get(13)+"           Ampliación 30x40     30x40cm");    
				}	
				
				if(Integer.parseInt(ev.get(25))>0 || Integer.parseInt(ev.get(29))>0 ||  Integer.parseInt(ev.get(17))>0 || Integer.parseInt(ev.get(21))>0 || Integer.parseInt(ev.get(22))>0 || Integer.parseInt(ev.get(23))>0 || Integer.parseInt(ev.get(24))>0 || ev.get(20) != null || Integer.parseInt(ev.get(20))>0 || ev.get(18) != null){
					tiquete.escribir("________________________________________");  
					tiquete.escribir("                ADICIONALES             ");  
					tiquete.escribir(String.format("%2s %-18s %6s %8s", "CANT.","DETALLE ARTICULO", "TAMAÑO","MONTO"));
					tiquete.escribir(""); 
				}
				if(ev.get(25) != null && !ev.get(25).equals("null") && Integer.parseInt(ev.get(25))* Integer.parseInt(ev.get(26)) > 0){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", ev.get(25), "Pers. Adicional", "",Integer.parseInt(ev.get(25))* Integer.parseInt(ev.get(26))));
				}
				if(ev.get(29) != null && !ev.get(29).equals("null") && Integer.parseInt(ev.get(29))* Integer.parseInt(ev.get(36)) > 0){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", ev.get(29), "Cargo Reagendar", "",Integer.parseInt(ev.get(29))* Integer.parseInt(ev.get(36))));
				}
				
				if(Integer.parseInt(ev.get(17))>0){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", "1", "CD Completo", "",ev.get(17)));
				}
				if(ev.get(18) != null && !ev.get(18).equals("null") && !ev.get(18).equals("-1")){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", "1", "Extras","",ev.get(18)));
				}
				if(ev.get(20) != null && !ev.get(20).equals("null") && Integer.parseInt(ev.get(20))>0){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", "1", "Descuento","",ev.get(20) ));
				}				
				if(Integer.parseInt(ev.get(21))>0){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", ev.get(21), "Fotografía 10x15","10x15","" ));
				}
				if(Integer.parseInt(ev.get(22))>0){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", ev.get(22), "Fotografía 15x21","15x21","" ));
				}
				if(Integer.parseInt(ev.get(23))>0){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", ev.get(23), "Ampliación 20x30","20x30","" ));
				}
				if(Integer.parseInt(ev.get(24))>0){
					tiquete.escribir(String.format("%2s %-20s %6s %9s", ev.get(24), "Ampliación 30x40","30x40","" ));
				}
				if(Integer.parseInt(ev.get(25))>0 || Integer.parseInt(ev.get(29))>0 ||  Integer.parseInt(ev.get(17))>0 || Integer.parseInt(ev.get(21))>0 || Integer.parseInt(ev.get(22))>0 || Integer.parseInt(ev.get(23))>0 || Integer.parseInt(ev.get(24))>0 || ev.get(20) != null || Integer.parseInt(ev.get(20))>0 || ev.get(18) != null){
					tiquete.escribir("");
					int TotalAD = M(Integer.parseInt(ev.get(25)))* M(Integer.parseInt(ev.get(26))) +
								  M(Integer.parseInt(ev.get(29)))* M(Integer.parseInt(ev.get(36))) +
							      M(Integer.parseInt(ev.get(17))) + M(Integer.parseInt(ev.get(18))) - M(Integer.parseInt(ev.get(20)));
					tiquete.escribir(String.format("%2s %-20s %6s %9s", "", "Total Adicionales","",TotalAD));
				}
				
				tiquete.escribir("________________________________________"); 
				tiquete.escribir("");
				tiquete.escribir(    "Valor Sesión Base......... : "+String.format("%11s\r\n", ev.get(14))); 
				if(ev.get(15) == null || ev.get(15).equals("null") || ev.get(15).equals("-1")){
					if(ev.get(15).equals("-1")){
						tiquete.escribir("Valor Abonado............. :           0"); 
					}else{
						tiquete.escribir("Valor Abonado............. :        N/A");  
					}
				}else{
					tiquete.escribir("Valor Abonado............. : "+String.format("%11s\r\n", ev.get(15))); 
				}
				tiquete.escribir("________________________________________"); 
				tiquete.escribir("Pendiente por pagar....... : "+String.format("%11s\r\n", ev.get(16))); 
				tiquete.escribir("");
				if(Integer.parseInt(ev.get(25))>0 || Integer.parseInt(ev.get(29))>0 ||  Integer.parseInt(ev.get(17))>0 || Integer.parseInt(ev.get(21))>0 || Integer.parseInt(ev.get(22))>0 || Integer.parseInt(ev.get(23))>0 || Integer.parseInt(ev.get(24))>0 || ev.get(20) != null || Integer.parseInt(ev.get(20))>0 || ev.get(18) != null){
					int TotalAD = M(Integer.parseInt(ev.get(25)))* M(Integer.parseInt(ev.get(26))) +
							  M(Integer.parseInt(ev.get(29)))* M(Integer.parseInt(ev.get(36))) +
						      M(Integer.parseInt(ev.get(17))) + M(Integer.parseInt(ev.get(18))) - M(Integer.parseInt(ev.get(20)));
					tiquete.escribir(String.format("Total Adicionales"+String.format("%23s\r\n", TotalAD ))); 
				}
				tiquete.escribir("________________________________________"); 
				tiquete.escribir("TOTAL POR PAGAR"+String.format("%25s\r\n", ev.get(31))); 
				tiquete.escribir("________________________________________"); 
				
				tiquete.escribir("Forma de pago:    "+String.format("%22s", ev.get(30)));
				tiquete.escribir("****************************************");
				//tiquete.escribir("Seleccione sus fotos en:                ");
				//tiquete.escribir(this.insertPeriodically("http://www.fotoexpressiones.com/selecciondefotos.html", "\r\n", 40));
				//tiquete.escribir(this.insertPeriodically("O ingresando a www.fotoexpressiones.com, pestaña \"SELECCION\"", "\r\n", 40));
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
					   							
					String fmt = "//192.168.100.4/TicketsEstudios/ticket"+ev.get(1)+".pdf";
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
					
					String filePath = "//192.168.100.4/TicketsEstudios/";
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
			        gd.ActualizarEventoImpreso(llegoEvento);
			        
			        inStream.close();
			        outStream.close(); 
			        rd.forward(request, response);
			        
						
				}catch(Exception e){
					System.out.println(e.getMessage());
					String mensaje = "<strong>¡Se produjo un error!</strong><br><br>"
							+ "Descripción: "+e.getMessage();
					
					request.setAttribute("mensaje", mensaje);
					request.setAttribute("tipomensaje", "danger");

					rd.forward(request, response);
				}
				
				
				
				ArrayList<ArrayList<Object>> eventos2 = (ArrayList<ArrayList<Object>>)gd.getEventosSinId("");
				request.setAttribute("eventos", eventos2);
				
		    	rd = request.getRequestDispatcher("/visualizareventos.jsp");
    			rd.forward(request, response);
		    }
		    
		    
		    if(llegoSolicitud.equals("ImprimirEventoSimple")){
	   	    	
    	    	String llegoEvento = (request.getParameter("39_Id_Evento"));
    	    	
    	    	ArrayList<Object> eve = (ArrayList<Object>) gd.getEventosSinId(" [39_Id_Evento] ="+llegoEvento).get(0); 
    	    	Evento ev = (Evento) eve.get(0);
    	    	Vendedor vend = (Vendedor)gd.getVendedoresSinId("", "", "").get(0);
    	    	
    	    	//Obtención del usuario
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
				tiquete.escribir("Fono "+vend.getTelefono());
				tiquete.escribir("");
				tiquete.escribir(String.format("%16s %23s", "N° Ticket      :", ev.getNumero_Boleta()));
				tiquete.escribir("Fecha          : "+ev.getFecha());
				tiquete.escribir("Tipo           : "+ev.getTipo_Doc());

				
				
				if(ev.getValor()>0 ){
					tiquete.escribir("________________________________________");  
					tiquete.escribir("                ADICIONALES             ");  
					tiquete.escribir(String.format("%-31s %8s", "DETALLE ARTICULO","MONTO"));
					tiquete.escribir(""); 
					tiquete.escribir(String.format("%-31s %8s", ev.getDescripcion(),ev.getValor()));
				}
								
				tiquete.escribir("________________________________________"); 
				tiquete.escribir("");
				tiquete.escribir("Pendiente por pagar....... : "+String.format("%11s\r\n",ev.getValor())); 
				tiquete.escribir("");
				if(ev.getValor()>0){
					tiquete.escribir(String.format("Total Adicionales"+String.format("%23s\r\n", ev.getValor() ))); 
				}
				tiquete.escribir("________________________________________"); 
				tiquete.escribir("TOTAL POR PAGAR"+String.format("%25s\r\n", ev.getValor())); 
				tiquete.escribir("________________________________________"); 
				
				tiquete.escribir("Forma de pago:    "+String.format("%22s", ev.getForma_Pago()));
				tiquete.escribir("****************************************");
				//tiquete.escribir("Seleccione sus fotos en:                ");
				//tiquete.escribir(this.insertPeriodically("http://www.fotoexpressiones.com/selecciondefotos.html", "\r\n", 40));
				//tiquete.escribir(this.insertPeriodically("O ingresando a www.fotoexpressiones.com, pestaña \"SELECCION\"", "\r\n", 40));
				//Esto es para escribir una linea divisoria

				//tiquete.setRojo();
				//tiquete.setNegro();

				//tiquete.correr(10);//Esto baja 10 lineas en blanco
				//tiquete.cortar();//Esto corta el papel de la impresora
				tiquete.cerrarDispositivo();//Cierra el dispositivo y aplica el texto
				 
				Text2PDF.GenerarPDF("ticket"+ev.getNumero_Boleta()+".pdf"); 
				
				//Mandar PDF
				try{
					String home = System.getProperty("user.home");
					String dir = System.getProperty("user.dir");
				
					UserHomeApplet dirUsu = new UserHomeApplet(); // System.out.println("Prop "+dirUsu.getUserHome());
					   							
					String fmt = "//192.168.100.4/TicketsEstudios/ticket"+ev.getNumero_Boleta()+".pdf";
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
					
					rd= getServletContext().getRequestDispatcher("/DownloadFileServlet?fileName=ticket"+ev.getNumero_Boleta()+".pdf"); 
					
					response.setContentType("application/pdf");			
					response.setHeader("Content-Disposition", "attachment; filename=\"ticket"+ev.getNumero_Boleta()+".pdf\"");
						
					rd.include(request, response); 
					
					String filePath = "//192.168.100.4/TicketsEstudios/";
			        String fileName = "ticket"+ev.getNumero_Boleta()+".pdf";
			        
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
			        gd.ActualizarEventoImpreso(llegoEvento);
			        
			        inStream.close();
			        outStream.close(); 
			        rd.forward(request, response);
			        
						
				}catch(Exception e){
					System.out.println(e.getMessage());
					String mensaje = "<strong>¡Se produjo un error!</strong><br><br>"
							+ "Descripción: "+e.getMessage();
					
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

	public int M (int i){
		if(i==-1){
			return 0;
		}else{
			return i;
		}
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
