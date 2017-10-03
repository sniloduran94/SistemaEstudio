package conexion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.BorderLineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.format.Border;
import jxl.format.Colour;
import jxl.format.Pattern;


public class WriteExcelSesion {

  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  private WritableCellFormat timesYellow;
  private String inputFile;

  public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
  }

  public void write(ArrayList<ArrayList<String>> arreglo) throws IOException, WriteException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet("Expressiones", 0);
    WritableSheet excelSheet = workbook.getSheet(0);
    createLabel(excelSheet, arreglo.size());
    createContent(excelSheet, arreglo);

    workbook.write();
    workbook.close();
  }

  private void createLabel(WritableSheet sheet, int largo)
      throws WriteException {
    // Lets create a times font
    WritableFont times11pt = new WritableFont(WritableFont.TAHOMA, 11);
    // Define the cell format
    times = new WritableCellFormat(times11pt);
    // Lets automatically wrap the cells
    times.setWrap(true);

    // create create a bold font with unterlines
    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD, false,
        UnderlineStyle.NO_UNDERLINE);
    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
    // Lets automatically wrap the cells
    timesBoldUnderline.setWrap(true);
    timesBoldUnderline.setBorder(Border.ALL,BorderLineStyle.DOUBLE);
    timesBoldUnderline.setBackground(Colour.TEAL);
        
    CellView cv = new CellView();
    cv.setFormat(times);
    cv.setFormat(timesBoldUnderline);
    cv.setAutosize(false);

    addCaption(sheet, 0, 0, "Reservas");	
    addCaption(sheet, 18, 0, "Sesiones");
    for (int i = 1; i < largo+2; i++) {
    	addCaption(sheet, 18, i, "");
    }
    
    int col=0;
    addCaption(sheet, col++, 1, "Dia");
    addCaption(sheet, col++, 1, "Mes");
    addCaption(sheet, col++, 1, "Año");
    
    addCaption(sheet, col++, 1, "Hora");
    addCaption(sheet, col++, 1, "Validado");
    addCaption(sheet, col++, 1, "Nombre");
    addCaption(sheet, col++, 1, "Apellido");
    addCaption(sheet, col++, 1, "Tipo Sesión");
    addCaption(sheet, col++, 1, "Tipo Venta");
    addCaption(sheet, col++, 1, "Precio Campaña");
    addCaption(sheet, col++, 1, "CV");
    addCaption(sheet, col++, 1, "CD");
    addCaption(sheet, col++, 1, "10x15");
    addCaption(sheet, col++, 1, "15x21");
    addCaption(sheet, col++, 1, "20x30");
    addCaption(sheet, col++, 1, "30x40");
    addCaption(sheet, col++, 1, "Ref. Adicional");
    addCaption(sheet, col++, 1, "Cobro por reagendar");
       
    col++;
    //Sesión
    addCaption(sheet, col++, 1, "¿Asistio?");
    addCaption(sheet, col++, 1, "N° Ticket");
    addCaption(sheet, col++, 1, "Valor por cobrar");
    addCaption(sheet, col++, 1, "CD");
    addCaption(sheet, col++, 1, "Extras");
    addCaption(sheet, col++, 1, "Persona adicional");
    addCaption(sheet, col++, 1, "Recargo por reagendar");
    addCaption(sheet, col++, 1, "Monto extra");
    addCaption(sheet, col++, 1, "Fotógrafo");
    addCaption(sheet, col++, 1, "Fotos Seleccionadas");
    addCaption(sheet, col++, 1, "Fecha de entrega");
    addCaption(sheet, col++, 1, "Entregado");
    addCaption(sheet, col++, 1, "Fecha envío a imprimir");
    addCaption(sheet, col++, 1, "Monto impresión");
    addCaption(sheet, col++, 1, "N° Factura");
    addCaption(sheet, col++, 1, "Dif. 10x15");
    addCaption(sheet, col++, 1, "Dif. 15x21");
    addCaption(sheet, col++, 1, "Dif. 20x30");
    addCaption(sheet, col++, 1, "Dif. 30x40");
    
  }

  private void createContent(WritableSheet sheet, ArrayList<ArrayList<String>> arreglo) throws WriteException,
  RowsExceededException {
  
// Lets create a times font
    WritableFont tahoma10pt = new WritableFont(WritableFont.TAHOMA, 10);
    // Define the cell format
    times = new WritableCellFormat(tahoma10pt);
    // Lets automatically wrap the cells
    times.setWrap(true);

    // create create a bold font with unterlines
    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD, false,
        UnderlineStyle.NO_UNDERLINE);
    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
    // Lets automatically wrap the cells
    timesBoldUnderline.setWrap(true);
    timesBoldUnderline.setBorder(Border.ALL,BorderLineStyle.THIN);
    
    CellView cv = new CellView();
    
    for (int i = 2; i < arreglo.size()+2; i++) {
  // Columnas de excel
    int col = 0;
    
	addNumber(sheet, col++, i, (String)(arreglo.get(i-2).get(0)).substring(0,2));
	addNumber(sheet, col++, i, (String)(arreglo.get(i-2).get(0)).substring(3,5));
	addNumber(sheet, col++, i, (String)(arreglo.get(i-2).get(0)).substring(6,10));
    
	//addCaption(sheet, col++, i, (arreglo.get(i-2).get(0)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(1)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(2)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(3)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(4)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(5)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(6)));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(7))));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(8)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(9)));
    addNumber(sheet, col++, i, (arreglo.get(i-2).get(10)));
    addNumber(sheet, col++, i, (arreglo.get(i-2).get(11)));
    addNumber(sheet, col++, i, (arreglo.get(i-2).get(12)));
    addNumber(sheet, col++, i, (arreglo.get(i-2).get(13)));
    addNumber(sheet, col++, i, (arreglo.get(i-2).get(14)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(15)));
    
    col++;
    //Sesion
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(16)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(17)));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(18))));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(19))));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(20))));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(21))));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(22))));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(23))));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(24)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(25)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(26)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(27)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(28)));
    addNumber(sheet, col++, i, (arreglo.get(i-2).get(29)));
    addCaption(sheet, col++, i, (arreglo.get(i-2).get(30)));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(31))));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(32))));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(33))));
    addNumber(sheet, col++, i, ((arreglo.get(i-2).get(34))));
    
}
}

  private void addCaption(WritableSheet sheet, int column, int row, String s)
      throws RowsExceededException, WriteException {
    Label label;
    label = new Label(column, row, s, timesBoldUnderline);
    sheet.addCell(label);
  }

  private void addNumber(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException {
    if(isNumeric(s)){
	    Number number;
	    number = new Number(column, row, Integer.parseInt(s), timesBoldUnderline);
	    sheet.addCell(number);
    }
    if(s.equals("-") || s.equals("")){
    	 Number number;
 	    number = new Number(column, row, 0, timesBoldUnderline);
 	    sheet.addCell(number);
    }
  }
  
  public static boolean isNumeric(String str)  
  {  
    try  
    {  
      double d = Double.parseDouble(str);  
    }  
    catch(NumberFormatException nfe)  
    {  
      return false;  
    }  
    return true;  
  }

  private void addLabel(WritableSheet sheet, int column, int row, String s)
      throws WriteException, RowsExceededException {
    Label label;
    label = new Label(column, row, s, times);
    sheet.addCell(label);
  }
} 