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


public class WriteExcel {

  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  private String inputFile;

  public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
  }

  public void write(ArrayList<ArrayList<String>> arreglo) throws IOException, WriteException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet("Genesis", 0);
    WritableSheet excelSheet = workbook.getSheet(0);
    createLabel(excelSheet);
    createContent(excelSheet, arreglo);

    workbook.write();
    workbook.close();
  }

  private void createLabel(WritableSheet sheet)
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
    cv.setAutosize(true);

    addCaption(sheet, 0, 1, "Fecha");
    addCaption(sheet, 1, 1, "Hora");
    addCaption(sheet, 2, 1, "Validado");
    addCaption(sheet, 3, 1, "Nombre");
    addCaption(sheet, 4, 1, "Apellido");
    addCaption(sheet, 5, 1, "Tipo Sesi�n");
    addCaption(sheet, 6, 1, "Tipo Venta");
    addCaption(sheet, 7, 1, "Precio Campa�a");
    addCaption(sheet, 8, 1, "CV");
    addCaption(sheet, 9, 1, "CD");
    addCaption(sheet, 10, 1, "10x15");
    addCaption(sheet, 11, 1, "15x21");
    addCaption(sheet, 12, 1, "20x30");
    addCaption(sheet, 13, 1, "30x40");
    addCaption(sheet, 14, 1, "Ref. Adicional");
    addCaption(sheet, 15, 1, "Cobro xReagendar");
    addCaption(sheet, 16, 1, "�Es Pre Reserva?");
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
        addCaption(sheet, 16, i, (arreglo.get(i-2).get(16)));
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