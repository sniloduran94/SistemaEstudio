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
    addCaption(sheet, 16, 0, "Sesiones");
    for (int i = 1; i < largo+2; i++) {
    	addCaption(sheet, 16, i, "");
    }
    
    
    addCaption(sheet, 0, 1, "Fecha");
    addCaption(sheet, 1, 1, "Hora");
    addCaption(sheet, 2, 1, "Validado");
    addCaption(sheet, 3, 1, "Nombre");
    addCaption(sheet, 4, 1, "Apellido");
    addCaption(sheet, 5, 1, "Tipo Sesión");
    addCaption(sheet, 6, 1, "Tipo Venta");
    addCaption(sheet, 7, 1, "Precio Campaña");
    addCaption(sheet, 8, 1, "CV");
    addCaption(sheet, 9, 1, "CD");
    addCaption(sheet, 10, 1, "10x15");
    addCaption(sheet, 11, 1, "15x21");
    addCaption(sheet, 12, 1, "20x30");
    addCaption(sheet, 13, 1, "30x40");
    addCaption(sheet, 14, 1, "Ref. Adicional");
    addCaption(sheet, 15, 1, "Cobro por reagendar");
    

    
    //Sesión
    addCaption(sheet, 17, 1, "¿Asistio?");
    addCaption(sheet, 18, 1, "N° Ticket");
    addCaption(sheet, 19, 1, "Valor por cobrar");
    addCaption(sheet, 20, 1, "CD");
    addCaption(sheet, 21, 1, "Extras");
    addCaption(sheet, 22, 1, "Persona adicional");
    addCaption(sheet, 23, 1, "Recargo por reagendar");
    addCaption(sheet, 24, 1, "Monto extra");
    addCaption(sheet, 25, 1, "Fotógrafo");
    addCaption(sheet, 26, 1, "Fotos Seleccionadas");
    addCaption(sheet, 27, 1, "Fecha de entrega");
    addCaption(sheet, 28, 1, "Entregado");
    addCaption(sheet, 29, 1, "Fecha envío a imprimir");
    addCaption(sheet, 30, 1, "Monto impresión");
    addCaption(sheet, 31, 1, "N° Factura");
    addCaption(sheet, 32, 1, "Dif. 10x15");
    addCaption(sheet, 33, 1, "Dif. 15x21");
    addCaption(sheet, 34, 1, "Dif. 20x30");
    addCaption(sheet, 35, 1, "Dif. 30x40");
    
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
	addCaption(sheet, 0, i, (arreglo.get(i-2).get(0)));
    addCaption(sheet, 1, i, (arreglo.get(i-2).get(1)));
    addCaption(sheet, 2, i, (arreglo.get(i-2).get(2)));
    addCaption(sheet, 3, i, (arreglo.get(i-2).get(3)));
    addCaption(sheet, 4, i, (arreglo.get(i-2).get(4)));
    addCaption(sheet, 5, i, (arreglo.get(i-2).get(5)));
    addCaption(sheet, 6, i, (arreglo.get(i-2).get(6)));
    addNumber(sheet, 7, i, ((arreglo.get(i-2).get(7))));
    addCaption(sheet, 8, i, (arreglo.get(i-2).get(8)));
    addCaption(sheet, 9, i, (arreglo.get(i-2).get(9)));
    addNumber(sheet, 10, i, (arreglo.get(i-2).get(10)));
    addNumber(sheet, 11, i, (arreglo.get(i-2).get(11)));
    addNumber(sheet, 12, i, (arreglo.get(i-2).get(12)));
    addNumber(sheet, 13, i, (arreglo.get(i-2).get(13)));
    addNumber(sheet, 14, i, (arreglo.get(i-2).get(14)));
    addCaption(sheet, 15, i, (arreglo.get(i-2).get(15)));
    
    //Sesion
    addCaption(sheet, 17, i, (arreglo.get(i-2).get(16)));
    addCaption(sheet, 18, i, (arreglo.get(i-2).get(17)));
    addNumber(sheet, 19, i, ((arreglo.get(i-2).get(18))));
    addNumber(sheet, 20, i, ((arreglo.get(i-2).get(19))));
    addNumber(sheet, 21, i, ((arreglo.get(i-2).get(20))));
    addNumber(sheet, 22, i, ((arreglo.get(i-2).get(21))));
    addNumber(sheet, 23, i, ((arreglo.get(i-2).get(22))));
    addNumber(sheet, 24, i, ((arreglo.get(i-2).get(23))));
    addCaption(sheet, 25, i, (arreglo.get(i-2).get(24)));
    addCaption(sheet, 26, i, (arreglo.get(i-2).get(25)));
    addCaption(sheet, 27, i, (arreglo.get(i-2).get(26)));
    addCaption(sheet, 28, i, (arreglo.get(i-2).get(27)));
    addCaption(sheet, 29, i, (arreglo.get(i-2).get(28)));
    addNumber(sheet, 30, i, (arreglo.get(i-2).get(29)));
    addCaption(sheet, 31, i, (arreglo.get(i-2).get(30)));
    addNumber(sheet, 32, i, ((arreglo.get(i-2).get(31))));
    addNumber(sheet, 33, i, ((arreglo.get(i-2).get(32))));
    addNumber(sheet, 34, i, ((arreglo.get(i-2).get(33))));
    addNumber(sheet, 35, i, ((arreglo.get(i-2).get(34))));
    
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