package conexion;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jxl.CellView;
import jxl.DateCell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.BorderLineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;

import jxl.write.DateTime;
import jxl.write.DateFormat;
import jxl.write.DateFormats;

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
  private WritableCellFormat timesBoldUnderlineDate;
  private WritableCellFormat times;
  private String inputFile;

  public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
  }

  public void write(ArrayList<ArrayList<String>> arreglo) throws IOException, WriteException, ParseException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet("Estudio", 0);
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
    
    timesBoldUnderlineDate = new jxl.write.WritableCellFormat(new jxl.write.DateFormat("m/d/yyyy"));
    timesBoldUnderlineDate.setWrap(true);
    timesBoldUnderlineDate.setBorder(Border.ALL,BorderLineStyle.DOUBLE);
    timesBoldUnderlineDate.setBackground(Colour.TEAL);
    
    CellView cv = new CellView();
    cv.setFormat(times);
    cv.setFormat(timesBoldUnderline);
    cv.setAutosize(true);

    int col = 0;
    addCaption(sheet, col++, 1, "Dia");
    addCaption(sheet, col++, 1, "Mes");
    addCaption(sheet, col++, 1, "Año");
    //
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
    addCaption(sheet, col++, 1, "Cobro xReagendar");
    addCaption(sheet, col++, 1, "¿Es Pre Reserva?");
  }

  private void createContent(WritableSheet sheet, ArrayList<ArrayList<String>> arreglo) throws WriteException,
      RowsExceededException, ParseException {
	  
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
    	try{
    	if(arreglo.get(i-2).get(0)!=null &&  !(arreglo.get(i-2).get(0)).equals("null")){
    		System.out.println((arreglo.get(i-2).get(0)));
	    	addNumber(sheet, col++, i, (String)(arreglo.get(i-2).get(0)).substring(0,2));
	    	addNumber(sheet, col++, i, (String)(arreglo.get(i-2).get(0)).substring(3,5));
	    	addNumber(sheet, col++, i, (String)(arreglo.get(i-2).get(0)).substring(6,10));
    	}else{
    		addCaption(sheet, col++, i, (arreglo.get(i-2).get(0)));
    		addCaption(sheet, col++, i, "");
	    	addCaption(sheet, col++, i, "");
    	}}
    	catch(Exception e){
    		System.out.println("Error: "+e.getMessage());
    	}
    	//fin fecha
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
        addCaption(sheet, col++, i, (arreglo.get(i-2).get(16)));
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
  
  private void addDate(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException, ParseException {
	  System.out.println("Agregando fecha: "+s);  
	  	if(s.equals("-") || s.equals("") || s.equals("null")){
	    	Label label;
	    	label = new Label(column, row, s, timesBoldUnderline);
	    	sheet.addCell(label);
	    }else{
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    	WritableCell cell = new jxl.write.DateTime(column, row, sdf.parse(s), timesBoldUnderlineDate);
	    	sheet.addCell(cell);
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
/*
  private void addLabel(WritableSheet sheet, int column, int row, String s)
      throws WriteException, RowsExceededException {
    Label label;
    label = new Label(column, row, s, times);
    sheet.addCell(label);
  }*/
} 