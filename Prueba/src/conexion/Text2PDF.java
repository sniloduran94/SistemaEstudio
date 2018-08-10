package conexion;

/**
 * Example written for JavaOne 2016.
 * Differences between iText 5 and iText 7 are discussed in the JavaOne talk
 * "Oops, I broke my API" by Raf Hens and Bruno Lowagie.
 * This is the iText 5 version of one of the examples.
 */
import java.io.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
 
public class Text2PDF {
	
	public static void GenerarPDF(String nombreArchivo) throws DocumentException, IOException
	{
	    String linea, FileName;
	    File InFile = null;
	    FileReader fr = null;
	    BufferedReader br = null;
 
	    //Selecciona el archivo a convertir.
	    String home = System.getProperty("user.home");
		String dir = System.getProperty("user.dir");
		
		System.out.println("C:/Users/" + System.getProperty("user.name") + "/Downloads/");
		
		UserHomeApplet dirUsu = new UserHomeApplet();
		System.out.println("Prop "+dirUsu.getUserHome());
						
		String fmt = home+"/Downloads/";
	    
	    FileName = fmt+"ticket.txt";
 
	    // Abre el archivo y crea el reader.
	    InFile = new File (FileName);
	    fr = new FileReader (InFile);
            br = new BufferedReader(fr);
 
        //Crea el documento de salida.
	    FileOutputStream archivo = new FileOutputStream(fmt + nombreArchivo); 
	    
	    System.out.println(InFile.getName());
	    System.out.println("Archivo com: "+ fmt + nombreArchivo);
	    
	    System.out.println(archivo.toString());
	    
	    Document documento = new Document();
	    PdfWriter.getInstance(documento, archivo);
	    documento.open();
	    
	    // step 4
        BaseFont bf1 = BaseFont.createFont("c:/windows/fonts/arial.ttf",
            BaseFont.CP1252, BaseFont.EMBEDDED);
        //Font font1 = new Font(bf1, 12);
        //documento.add(new Paragraph("Movie title: Moscou, Belgium", font1));
        //documento.add(new Paragraph("directed by Christophe Van Rompaey", font1));
        //documento.add(new Paragraph("Hola", font1));
        BaseFont bf2 = BaseFont.createFont("c:/windows/fonts/cour.ttf", 
            BaseFont.CP1252, BaseFont.EMBEDDED); 
        Font font2 = new Font(bf2, 12); 
        //documento.add(new Paragraph("Holaa", font2));
        BaseFont bf3 = BaseFont.createFont("c:/windows/fonts/arialbd.ttf",
            BaseFont.CP1252, BaseFont.EMBEDDED);
        //Font font3 = new Font(bf3, 12);
        int widths[] = bf3.getWidths(); 
        for (int k = 0; k < widths.length; ++k) {
            if (widths[k] != 0)
                widths[k] = 600;
        }
        bf3.setForceWidthsOutput(true);
        

        Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD); 
        Font Font4 = new Font(Font.getFamily("TIMES_ROMAN"), 12,    Font.BOLD);
        
         
        int cont = 0;
        try{
	          while((linea=br.readLine())!=null){  
	        	  if(cont>=5){
	        		  documento = AddNewLine(documento,linea, font2); 
	        	  }else{

	        		  documento.add(new Chunk(linea, Font4));
	        		  //documento = AddNewLine(documento,linea, Font4); 
	        	  }
	        	  cont++;
            }
 
	    }catch(Exception e){e.printStackTrace();
 
	    }finally{
	         // En el finally cerramos el fichero, para asegurarnos en cualquier circunstancia.
	         try{
	            if( null != fr ){
	               fr.close();
	            }
	         }catch (Exception e2){
	            e2.printStackTrace();
	         }
	      }
 
	    //Cerramos el documento PDF.
	    documento.close();
 
	}
 
 
	/**
	 * Método: AddNewLine()
	 * @param doc
	 * @param linea
	 * @return
	 */
	static public Document AddNewLine(Document doc, String linea, Font font)
	{
		try{
			String pad = "                  ";
			Paragraph paragraph = new Paragraph(pad+linea, font);
			paragraph.setMultipliedLeading(1);
			
			doc.add(paragraph);
		}catch(DocumentException de){de.printStackTrace();}
 
		return doc;
	}
  
}
