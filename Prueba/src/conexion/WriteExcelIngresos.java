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


public class WriteExcelIngresos {

  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  private String inputFile;

public void setOutputFile(String inputFile) {
  this.inputFile = inputFile;
  }

  public void write(ArrayList<ArrayList<String>> arreglo,ArrayList<ArrayList<String>> egresos,ArrayList<ArrayList<String>> resumen) throws IOException, WriteException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();
 
    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet("Ingresos", 0);
    WritableSheet excelSheet = workbook.getSheet(0);
    createLabel(excelSheet);
    createContent(excelSheet, arreglo);
    
    workbook.createSheet("Egresos", 1); 
    WritableSheet excelSheet2 = workbook.getSheet(1);
    createLabelEgresos(excelSheet2);
    createContentEgresos(excelSheet2, egresos);
    
    workbook.createSheet("Resumen", 2); 
    WritableSheet excelSheet3 = workbook.getSheet(2);
    createLabelResumen(excelSheet3);
    createContentResumen(excelSheet3, resumen);
    

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
    addCaption(sheet, 1, 1, "N° Comprobante");
    addCaption(sheet, 2, 1, "Detalle");
    addCaption(sheet, 3, 1, "Forma de pago");
    addCaption(sheet, 4, 1, "Valor");
    addCaption(sheet, 5, 1, "Acumulado");
  }

  private void createLabelEgresos(WritableSheet sheet)
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
	    addCaption(sheet, 1, 1, "N° Comprobante");
	    addCaption(sheet, 2, 1, "Tipo Doc");
	    addCaption(sheet, 3, 1, "Concepto");
	    addCaption(sheet, 4, 1, "Detalle");
	    addCaption(sheet, 5, 1, "Valor");
	    addCaption(sheet, 6, 1, "Acumulado");
	  }

  private void createLabelResumen(WritableSheet sheet)
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
	    addCaption(sheet, 1, 1, "S.Inicial EFF");
	    addCaption(sheet, 2, 1, "Tarjeta");
	    addCaption(sheet, 3, 1, "Transferencia");
	    addCaption(sheet, 4, 1, "Efectivo");
	    addCaption(sheet, 5, 1, "Gasto");
	    addCaption(sheet, 6, 1, "S.Final EFF");
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
        addNumber(sheet, 4, i, (arreglo.get(i-2).get(4)));
        addNumber(sheet, 5, i, ((arreglo.get(i-2).get(5))));
    }
  }

  private void createContentEgresos(WritableSheet sheet, ArrayList<ArrayList<String>> arreglo) throws WriteException,
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
		    addNumber(sheet, 5, i, ((arreglo.get(i-2).get(5))));
		    addNumber(sheet, 6, i, ((arreglo.get(i-2).get(6))));
		}
}
  
  private void createContentResumen(WritableSheet sheet, ArrayList<ArrayList<String>> arreglo) throws WriteException,
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
			addNumber(sheet, 1, i, (arreglo.get(i-2).get(1)));
		    addNumber(sheet, 2, i, (arreglo.get(i-2).get(2)));
		    addNumber(sheet, 3, i, (arreglo.get(i-2).get(3)));
		    addNumber(sheet, 4, i, (arreglo.get(i-2).get(4)));
		    addNumber(sheet, 5, i, ((arreglo.get(i-2).get(5))));
		    addNumber(sheet, 6, i, ((arreglo.get(i-2).get(6))));
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