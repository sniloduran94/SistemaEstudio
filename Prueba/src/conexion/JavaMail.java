package conexion;

import java.io.File;
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
  
	//String directorio = "C:\\Users\\Administrator\\Documents\\Estudio Fotográfico\\WORKSPACE\\Prueba";
	String directorio = "C:\\Users\\Administrator\\git\\Prueba";
	
	/**
	 * @author Salomon
	 * @param String correo1 que indica el primer correo de destino
	 * @param String correo1 que indica el segundo correo de destino
	 * @param String correo1 que indica el tercer correo de destino
	 * @param String Mensaje que indica el mensaje que se enviará en el correo
	 * Método que envía un correo a 3 correos diferentes
	 * @throws java.text.ParseException 
	 */	/*
public void mandarCorreo(String Correo1, String Correo2, String Correo3, String Mensaje){  
  // El correo gmail de envío  
  String correoEnvia = "";//"contacto@genesisestudio.cl";   			--------------------------------------------
  String claveCorreo = "";//adv2010";									--------------------------------------------
  
  // La configuración para enviar correo  
  Properties properties = new Properties();  
  //properties.put("mail.smtp.host", "smtpout.secureserver.net");  --------------------------------------------------
  properties.put("mail.smtp.starttls.enable", "false");  
  properties.put("mail.smtp.port", "3535");  
  properties.put("mail.smtp.auth", "true");  
  properties.put("mail.user", correoEnvia);  
  properties.put("mail.password", claveCorreo);  
  
  // Obtener la sesion  
  Session session = Session.getInstance(properties, null);  
  
  try {  
   // Crear el cuerpo del mensaje  
   MimeMessage mimeMessage = new MimeMessage(session);  
  
   // Agregar quien envía el correo  
   mimeMessage.setFrom(new InternetAddress(correoEnvia, "Genesis Estudio"));  
     
   // Los destinatarios
   InternetAddress[] internetAddresses = {  
	     new InternetAddress(Correo1),  
	     new InternetAddress(Correo2),  
	     new InternetAddress(Correo3)}; 
  
   // Agregar los destinatarios al mensaje  
   mimeMessage.setRecipients(Message.RecipientType.TO,  
     internetAddresses);  
  
   // Agregar el asunto al correo  
   mimeMessage.setSubject("Comprobante de reserva");  
  
   // Creo la parte del mensaje  
   MimeBodyPart mimeBodyPart = new MimeBodyPart();  
   mimeBodyPart.setText(Mensaje, "ISO-8859-1", "html");  
   
   MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
   DataSource source = new FileDataSource(new File(directorio+"\\WebContent\\images\\LogoMail.png"));
   mimeBodyPart2.setDataHandler(new DataHandler(source));
   mimeBodyPart2.setFileName("LogoLetras.png");
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
   transport.connect(correoEnvia, claveCorreo);  
   transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients()); //				------------------------------
   transport.close();  
  
  } catch (Exception ex) {  
   ex.printStackTrace();  
  } 
  System.out.println("Correo enviado");  
 }  
 */
 public void mandarCorreo(String Correo1, String Correo2, String Correo3, String Mensaje, String Asunto, String correoEnvia, String NombreCorreoEnvia, String correoClave){  
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
	  
	   // Agregar los destinatarios al mensaje  
	   mimeMessage.setRecipients(Message.RecipientType.TO,  
	     internetAddresses);  
	  
	   // Agregar el asunto al correo  
	   mimeMessage.setSubject(Asunto);  
	  
	   // Creo la parte del mensaje  
	   MimeBodyPart mimeBodyPart = new MimeBodyPart();  
	   mimeBodyPart.setText(Mensaje, "ISO-8859-1", "html");  
	  
	   //Adjuntamos la imagen del estudio
	   BodyPart adjunto = new MimeBodyPart();
	   adjunto.setDataHandler(new DataHandler(getImage()));
	   adjunto.setFileName("LogoLetras.png");
	   	   
	   //URL url = getClass().getResource("LogoMail.png");
	   //File file = new File(url.getPath());
	   File file ;/*
	   if(NombreCorreoEnvia.equals("Genesis Estudio")){
		   file = new File(directorio+"\\WebContent\\images\\LogoMailGenesis.png");
	   }else{
		   file = new File(directorio+"\\WebContent\\images\\LogoMailExpressiones.png");
	   }  */
	   
	   if(NombreCorreoEnvia.equals("Genesis Estudio")){
		   file = new File(directorio+"\\WebContent\\images\\h1g.gif");
	   }else{
		   file = new File(directorio+"\\WebContent\\images\\h1e.gif");
	   }  
	   
	   MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
	   DataSource source = new FileDataSource(file);
	   mimeBodyPart2.setDataHandler(new DataHandler(source));
	   mimeBodyPart2.setFileName("h1.gif");
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
	   transport.close();  
	  
	  } catch (Exception ex) {  
	   ex.printStackTrace();  
	  }  
	  System.out.println("Correo enviado");  
	 }  
 	
 	private static DataSource getImage() {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    if (classLoader == null) {
	        classLoader = JavaMail.class.getClassLoader();
	    }
	    //DataSource ds = new URLDataSource(classLoader.getResource("/LogoLetras.png"));
	    DataSource ds = new URLDataSource(classLoader.getResource("/LogoLetras.png"));
	    
	    return ds;
	}


}  


