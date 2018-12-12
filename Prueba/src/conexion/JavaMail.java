package conexion;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class JavaMail {  
	String version = "v34";
	
	//String directorio = "C:\\Users\\Administrator\\Documents\\Estudio Fotográfico\\WORKSPACE\\Prueba";
	String directorio = "C:\\Users\\Administrator\\Documents\\Estudio Fotográfico\\WORKSPACE\\EstudioFotografico"+version;
	//String directorio = "C:\\Users\\Administrator\\Documents\\Estudio Fotográfico\\WORKSPACE\\EstudioFotograficoKids"+version;
	//String directorio = "C:\\Users\\Administrator\\Documents\\Estudio Fotográfico\\WORKSPACE\\GenesisEstudio"+version;
	//String directorio = "C:\\Users\\Administrator\\Documents\\Estudio Fotográfico\\WORKSPACE\\ElOtroEstudio"+version;
	//Remitentes si la reserva es del día de hoy
	
	//Prueba
	   public ArrayList<String> CorreosHoy = new ArrayList<String>(Arrays.asList("contacto@genesisestudio.cl", "reservas@genesisestudio.cl", "consultorqad@gmail.com", "salomon.nilo@advancing.cl"));
	   public int IdVendedor = 2;
	//Expressiones
	//				public ArrayList<String> CorreosHoy = new ArrayList<String>(Arrays.asList("central@genesisestudio.cl", "fotografia_2@fotoexpressiones.com", "contacto@fotoexpressiones.com", "reservas@fotoexpressiones.com"));
	//			    public int IdVendedor = 2;
	//Kids
	//				public ArrayList<String> CorreosHoy = new ArrayList<String>(Arrays.asList("central@genesisestudio.cl", "fotografia_2@fotoexpressiones.com", "contacto@fotoexpressiones.com", "reservas@fotoexpressiones.com"));
    //	    		public int IdVendedor = 5;
	//Genesis
	//				public ArrayList<String> CorreosHoy = new ArrayList<String>(Arrays.asList("central@genesisestudio.cl", "fotografia_2@fotoexpressiones.com", "contacto@genesisestudio.cl", "reservas@genesisestudio.cl"));
	//	    		public int IdVendedor = 3;
	//ElOtroEstudio
	//				public ArrayList<String> CorreosHoy = new ArrayList<String>(Arrays.asList("central@genesisestudio.cl", "fotografia_2@fotoexpressiones.com", "contacto@elotroestudio.cl", "reservas@elotroestudio.cl"));
	//	    		public int IdVendedor = 4;
	
 public void mandarCorreo(String Correo1, String Correo2, String Correo3, String Mensaje, String Asunto, String correoEnvia, String NombreCorreoEnvia, String correoClave, boolean esParaHoy){  
	  // El correo gmail de envío  
	  
	  // La configuración para enviar correo  
	  Properties properties = new Properties();  
	  properties.put("mail.smtp.host", "smtpout.secureserver.net"); //  ------------------------------------------
	  properties.put("mail.smtp.starttls.enable", "false");  
	  properties.put("mail.smtp.port", "3535"); //3535  
	  properties.put("mail.smtp.auth", "true");  
	  properties.put("mail.user", correoEnvia);   
	  properties.put("mail.password", correoClave);  
	  
	  // Obtener la sesion  
	  Session session = Session.getInstance(properties, null);  
	  
	  try {  
	   // Crear el cuerpo del mensaje  
	   MimeMessage mimeMessage = new MimeMessage(session);  
	  
	   // Agregar quien envía el correo  
	   mimeMessage.setFrom(new InternetAddress(correoEnvia, NombreCorreoEnvia));  
	     
	   // Los destinatarios
	   InternetAddress[] internetAddresses = {  
		     new InternetAddress(Correo1),   
		     new InternetAddress(Correo2),  
		     new InternetAddress(Correo3)};  
	   
	   InternetAddress[] internetAddresses2 = {  
			     new InternetAddress(CorreosHoy.get(0)),   
			     new InternetAddress(CorreosHoy.get(1)),  
			     new InternetAddress(CorreosHoy.get(2)),  
			     new InternetAddress(CorreosHoy.get(3))}; 
	  
	   // Agregar los destinatarios al mensaje  
	   
	   if(esParaHoy){
		   mimeMessage.setRecipients(Message.RecipientType.TO,  
				     internetAddresses2);  

		   // Agregar el asunto al correo  
		   mimeMessage.setSubject("Nuevo agendamiento para hoy");  
	   }else{
		   mimeMessage.setRecipients(Message.RecipientType.TO,  
				   internetAddresses);  

		   // Agregar el asunto al correo  
		   mimeMessage.setSubject(Asunto);
	   }
	  
	   // Creo la parte del mensaje  
	   MimeBodyPart mimeBodyPart = new MimeBodyPart();    
	   	   
	   //ConvertUrlToString Plantilla = new ConvertUrlToString();
	   //String Mail = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head>    <meta charset=\"UTF-8\">    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">    <meta name=\"x-apple-disable-message-reformatting\">    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">    <meta content=\"telephone=no\" name=\"format-detection\">    <title></title>    <!--[if (mso 16)]>    <style type=\"text/css\">    a {text-decoration: none;}    </style>    <![endif]-->    <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--></head><body>    <div class=\"es-wrapper-color\">        <!--[if gte mso 9]>			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">				<v:fill type=\"tile\" src=\"\" color=\"#cfe2f3\"></v:fill>			</v:background>		<![endif]-->        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-wrapper\" width=\"100%\">            <tbody>                <tr>                    <td valign=\"top\" class=\"esd-email-paddings\">                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header esd-header-popover\" align=\"center\">                            <tbody>                                <tr>                                    <td class=\"esd-stripe\" align=\"center\">                                        <table class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">                                            <tbody>                                                <tr>                                                    <td class=\"esd-structure es-p20b es-p20r es-p20l\" align=\"left\">                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                            <tbody>                                                                <tr>                                                                    <td width=\"560\" class=\"esd-container-frame\" align=\"center\" valign=\"top\">                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                            <tbody>                                                                                <tr>                                                                                    <td align=\"center\" class=\"esd-block-image\">                                                                                        <a href=\"http://stripo.email\" target=\"_blank\"><img src=\"https://hpy.stripocdn.email/content/guids/cab_pub_6364d3f0f495b6ab9dcf8d3b5c6e0b01/images/6791493239462422.png\" alt=\"\" width=\"124\"></a>                                                                                    </td>                                                                                </tr>                                                                            </tbody>                                                                        </table>                                                                    </td>                                                                </tr>                                                            </tbody>                                                        </table>                                                    </td>                                                </tr>                                            </tbody>                                        </table>                                    </td>                                </tr>                            </tbody>                        </table>                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\">                            <tbody>                                <tr>                                    <td class=\"esd-stripe\" align=\"center\">                                        <table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">                                            <tbody>                                                <tr>                                                    <td class=\"esd-structure es-p20\" align=\"left\">                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                            <tbody>                                                                <tr>                                                                    <td width=\"560\" class=\"esd-container-frame\" align=\"center\" valign=\"top\">                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                            <tbody>                                                                                <tr>                                                                                    <td align=\"left\" class=\"esd-block-text\">                                                                                        <!--Texto a enviar-->                                                                                    </td>                                                                                </tr>                                                                            </tbody>                                                                        </table>                                                                    </td>                                                                </tr>                                                            </tbody>                                                        </table>                                                    </td>                                                </tr>                                            </tbody>                                        </table>                                    </td>                                </tr>                            </tbody>                        </table>                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\">                            <tbody>                                <tr>                                    <td class=\"esd-stripe\" align=\"center\">                                        <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">                                            <tbody>                                                <tr>                                                    <td class=\"esd-structure\" align=\"left\">                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                            <tbody>                                                                <tr>                                                                    <td width=\"600\" class=\"esd-container-frame\" align=\"center\" valign=\"top\">                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                            <tbody>                                                                                <tr>                                                                                    <td align=\"center\" class=\"esd-block-spacer\" height=\"40\" bgcolor=\"#f6f6f6\"> </td>                                                                                </tr>                                                                            </tbody>                                                                        </table>                                                                    </td>                                                                </tr>                                                            </tbody>                                                        </table>                                                    </td>                                                </tr>                                            </tbody>                                        </table>                                    </td>                                </tr>                            </tbody>                        </table>                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\">                            <tbody>                                <tr>                                    <td class=\"esd-stripe\" align=\"center\">                                        <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">                                            <tbody>                                                <tr>                                                    <td class=\"esd-structure\" align=\"left\">                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                            <tbody>                                                                <tr>                                                                    <td width=\"600\" class=\"esd-container-frame\" align=\"center\" valign=\"top\">                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                            <tbody>                                                                                <tr>                                                                                    <td align=\"center\" class=\"esd-block-spacer\" height=\"167\"> </td>                                                                                </tr>                                                                            </tbody>                                                                        </table>                                                                    </td>                                                                </tr>                                                            </tbody>                                                        </table>                                                    </td>                                                </tr>                                            </tbody>                                        </table>                                    </td>                                </tr>                            </tbody>                        </table>                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\">                            <tbody>                                <tr>                                    <td class=\"esd-stripe\" align=\"center\">                                        <table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">                                            <tbody>                                                <tr>                                                    <td class=\"esd-structure\" align=\"left\">                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                            <tbody>                                                                <tr>                                                                    <td width=\"600\" class=\"esd-container-frame\" align=\"center\" valign=\"top\">                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                            <tbody>                                                                                <tr>                                                                                    <td align=\"center\" class=\"esd-block-spacer\" height=\"40\" bgcolor=\"#f6f6f6\"> </td>                                                                                </tr>                                                                            </tbody>                                                                        </table>                                                                    </td>                                                                </tr>                                                            </tbody>                                                        </table>                                                    </td>                                                </tr>                                            </tbody>                                        </table>                                    </td>                                </tr>                            </tbody>                        </table>                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer esd-footer-popover\" align=\"center\">                            <tbody>                                <tr>                                    <td class=\"esd-stripe\" align=\"center\">                                        <table class=\"es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">                                            <tbody>                                                <tr>                                                    <td class=\"esd-structure es-p30t es-p20r es-p40l\" align=\"left\">                                                        <!--[if mso]><table width=\"540\"><tr><td width=\"352\" valign=\"top\"><![endif]-->                                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\">                                                            <tbody>                                                                <tr>                                                                    <td width=\"352\" class=\"es-m-p0r es-m-p20b esd-container-frame\" valign=\"top\" align=\"center\">                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                            <tbody>                                                                                <tr>                                                                                    <td align=\"center\" class=\"esd-block-text\">                                                                                        <p>©Copyright 2018. Todos los derechos reservados.</p>                                                                                    </td>                                                                                </tr>                                                                            </tbody>                                                                        </table>                                                                    </td>                                                                </tr>                                                            </tbody>                                                        </table>                                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"168\" valign=\"top\"><![endif]-->                                                        <table cellpadding=\"0\" cellspacing=\"0\" align=\"right\">                                                            <tbody>                                                                <tr>                                                                    <td width=\"168\" align=\"left\" class=\"esd-container-frame\">                                                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">                                                                            <tbody>                                                                                <tr>                                                                                    <td align=\"center\" class=\"esd-empty-container\" style=\"display: none;\"></td>                                                                                </tr>                                                                            </tbody>                                                                        </table>                                                                    </td>                                                                </tr>                                                            </tbody>                                                        </table>                                                        <!--[if mso]></td></tr></table><![endif]-->                                                    </td>                                                </tr>                                            </tbody>                                        </table>                                    </td>                                </tr>                            </tbody>                        </table>                    </td>                </tr>            </tbody>        </table>    </div></body></html>";
	   //Mail = Mail.replace("<!--Texto a enviar-->", Mensaje); 
	   //System.out.println(Mail); 
	   mimeBodyPart.setText(Mensaje, "ISO-8859-1", "html");  
	  
	   //Verificamos a qué estudio pertenece
	   String imagen = "LogoMailExpressiones.png"; 
	   
	   switch(NombreCorreoEnvia){
	   	case "Genesis Estudio":
	   		imagen = "LogoMailGenesis.png";
	   		break;
	   	case "Expressiones Foto Estudio":
	   		imagen = "LogoMailExpressiones.png";
	   		break;
	   	case "El Otro Estudio":
	   		imagen = "LogoMailElOtroEstudio.png";
	   		break;
	   	case "Expressiones Foto Estudio Kids":
	   		imagen = "LogoMailExpressionesKids.png";
	   		break;
	   }
	   
	   //Adjuntamos la imagen del estudio
	   BodyPart adjunto = new MimeBodyPart();
	   adjunto.setDataHandler(new DataHandler(getImage(imagen)));
	   adjunto.setFileName("LogoLetras.png");
	   	   
	   //URL url = getClass().getResource("LogoMail.png");
	   //File file = new File(url.getPath());
	   File file ;
	     
	   file = new File(directorio+"\\WebContent\\images\\"+imagen);
	   
	   MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
	   DataSource source = new FileDataSource(file);
	   mimeBodyPart2.setDataHandler(new DataHandler(source));
	   mimeBodyPart2.setFileName(imagen);
	   mimeBodyPart2.setDisposition(MimeBodyPart.INLINE);
	   mimeBodyPart2.setHeader("Content-Transfer-Encoding", "base64");
	   
	   // Crear el multipart para agregar la parte del mensaje anterior  
	   Multipart multipart = new MimeMultipart();
	   multipart.addBodyPart(mimeBodyPart);
	   multipart.addBodyPart(mimeBodyPart2);
	  
	   // Agregar el multipart al cuerpo del mensaje  
	   mimeMessage.setContent(multipart, "text/html");  
	  
	   // Enviar el mensaje  
	   Transport transport = session.getTransport("smtp");  
	   transport.connect(correoEnvia, correoClave);  
	   transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());  //	-----------------------------------
	    	   
	   if(esParaHoy){   
		   this.mandarCorreo(Correo1, Correo2, Correo3, Mensaje, Asunto, correoEnvia, NombreCorreoEnvia, correoClave, false);
	   } 
	   transport.close();   
	  
	  } catch (Exception ex) {  
	   ex.printStackTrace();  
	  }  
	  System.out.println("Correo enviado");  
	 }  
  	
 	private static DataSource getImage(String imagen) {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    if (classLoader == null) {
	        classLoader = JavaMail.class.getClassLoader();
	    }
	    //DataSource ds = new URLDataSource(classLoader.getResource("/LogoLetras.png"));
	    DataSource ds = new URLDataSource(classLoader.getResource(imagen));
	    
	    return ds;
	}


}  

