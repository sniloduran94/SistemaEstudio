package conexion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
 
import javax.print.CancelablePrintJob;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.SimpleDoc;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
 
 
public class Impresora2 {
 
  public void Imprimir () throws Exception {
 

	File f = new File("C:/Users/Administrator/Documents/Eclipse v2/eclipse/pantalla.txt");
	    
    OutputStream fos = new BufferedOutputStream(new FileOutputStream(f));
 
    System.out.println("fos : " + fos);
 
    DocFlavor flavor = DocFlavor.INPUT_STREAM.TEXT_PLAIN_UTF_16;
 
   // DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
 
    System.out.println("flavor : " + flavor);
 
       
    InputStream is = new BufferedInputStream(new FileInputStream(f));
        
    System.out.println("is : " + is);
 
    StreamPrintServiceFactory[] factories = StreamPrintServiceFactory
 
        .lookupStreamPrintServiceFactories(null, DocFlavor.INPUT_STREAM.POSTSCRIPT.getMimeType());
 
     //StreamPrintServiceFactory[] factories = StreamPrintServiceFactory
 
    	    //    .lookupStreamPrintServiceFactories(flavor, DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType());
 
    if (factories.length == 0) {
        System.out.println("Unable to print files of type: " );
 
      }
 
 
    System.out.println("factories : " + factories);
 
    System.out.println("DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType() : "  + DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType());
 
 
    StreamPrintService service = factories[0].getPrintService(fos);
 
 
    System.out.println("service : " + service);
 
    DocPrintJob job = service.createPrintJob();
 
    System.out.println("job : " + job);
 
    Doc doc = new SimpleDoc(is, flavor, null);
 
    System.out.println("doc: " + doc);
 
 
 PrintJobWatcher pjDone = new PrintJobWatcher(job);
 
    System.out.println("pjDone : " + pjDone);
 
    if (job instanceof CancelablePrintJob) {
 
      CancelablePrintJob cancelJob = (CancelablePrintJob) job;
 
      System.out.println("cancelJob : " + cancelJob);
 
      try {
 
        cancelJob.cancel();
 
      } catch (PrintException e) {
 
      }
    }
 
    job.print(doc, null);
 
    pjDone.waitForDone();
 
    is.close();
  }
}