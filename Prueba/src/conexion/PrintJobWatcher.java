package conexion;

import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

class PrintJobWatcher 
{
   boolean done = false;  
   PrintJobWatcher(DocPrintJob job)
   {
      job.addPrintJobListener(new PrintJobAdapter()
     {
         public void printJobCanceled(PrintJobEvent pje)
         {
            synchronized (PrintJobWatcher.this)
            {
               done = true;
               PrintJobWatcher.this.notify();
            }
         }
         public void printJobCompleted(PrintJobEvent pje) 
        {
           synchronized (PrintJobWatcher.this)
          {
             done = true;
             PrintJobWatcher.this.notify();
          }
      }
      public void printJobFailed(PrintJobEvent pje) 
     {
         synchronized (PrintJobWatcher.this)
        {
           done = true;
           PrintJobWatcher.this.notify();
        }
     }
     public void printJobNoMoreEvents(PrintJobEvent pje)
     {
         synchronized (PrintJobWatcher.this)
         {
            done = true;
            PrintJobWatcher.this.notify();
         }
      }
   }  ); // this really should not be here
 }
 // what's going on here? No braces are matching before a new method is called  
// LOL I give up! 
public synchronized void waitForDone() 
{
   try 
   {
      while (!done)
      {
        wait();
      } 
 
    } 
catch (InterruptedException e) 
{
}
 
 
}
 
}
