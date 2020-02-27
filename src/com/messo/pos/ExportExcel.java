package com.messo.pos;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.messo.pos.db.Orders;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ExportExcel {
	
	private HSSFWorkbook wb;
	private Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	
	public ExportExcel(){
		this.wb = new HSSFWorkbook();
		setExcelStyles();
	}
	
	public void createExcelSheet(String day, List<Orders> list, Integer numOrdini){
		
		try{
			String safeDay = day.replace("/", "-");
			HSSFSheet sheet = wb.createSheet(safeDay);
			sheet.setColumnWidth(0, 26*256);
			sheet.setColumnWidth(1, 14*256);
			sheet.setColumnWidth(2, 14*256);
			sheet.setColumnWidth(3, 15*256);
			insertHeader(sheet.createRow(0));
			for(int dataIndex = 0; dataIndex < list.size() ; dataIndex++){
				HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
				insertRow(list.get(dataIndex), numOrdini, row);
			}
		} catch(NullPointerException e){
			System.out.println("Errore in creazione Excel " + e.getMessage());
		}	
	}
	
	public void insertHeader(HSSFRow row) {
		
        row.createCell(0).setCellStyle(styles.get("header"));
        row.getCell(0).setCellValue("PIATTO");
		row.createCell(1).setCellStyle(styles.get("header"));
		row.getCell(1).setCellValue("PREZZO");		
		row.createCell(2).setCellStyle(styles.get("header"));
		row.getCell(2).setCellValue("QUANTITA'");
		row.createCell(3).setCellStyle(styles.get("header"));
		row.getCell(3).setCellValue("TOTALE");
	}
	
	public void insertRow (Orders data, Integer numOrdini, HSSFRow row) {
		Orders rowData = data;
		
		if (!rowData.getName().equalsIgnoreCase("TOTALE")){
			row.createCell(0).setCellValue(new HSSFRichTextString(rowData.getName()));
			row.getCell(0).setCellStyle(styles.get("normal"));
			row.createCell(1).setCellValue(rowData.getPrice());
			row.getCell(1).setCellStyle(styles.get("currency"));
			row.createCell(2).setCellValue(rowData.getQuantity());
			row.getCell(2).setCellStyle(styles.get("normal"));
			row.createCell(3).setCellValue(rowData.getPriceTotal());
			row.getCell(3).setCellStyle(styles.get("currency"));
		}else{
			//In caso di totale scrive in grassetto
			row.setRowNum(row.getRowNum() + 1 );
			row.createCell(0).setCellValue(new HSSFRichTextString(rowData.getName()));
			row.getCell(0).setCellStyle(styles.get("total"));
			row.createCell(3).setCellValue(rowData.getPriceTotal());
			row.getCell(3).setCellStyle(styles.get("currencyTotal"));
			row.setRowNum(row.getRowNum() + 1 );
			row.createCell(0).setCellValue("Numero Ordini: " + numOrdini);
			row.getCell(0).setCellStyle(styles.get("total"));
		}
	}
	
	public void salvaExcel(Stage stage){
		try{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save file");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel File", "*.xls"));
			fileChooser.setInitialFileName("Resoconto.xls");
			File ff = fileChooser.showSaveDialog(stage);
			FileOutputStream out = new FileOutputStream(ff);
			wb.write(out);
			out.close();
		} catch (Exception e){
			System.out.println("Errore in salvataggio Excel");
		}
	}
	
	private void setExcelStyles(){
		
		HSSFFont font= wb.createFont();
	    font.setFontHeightInPoints((short)11);
	    font.setFontName("Tahoma");
	    font.setColor(IndexedColors.BLACK.getIndex());
	    font.setBold(true);
	    font.setItalic(false);
	    
	    CellStyle styleHeader;
        styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setFont(font);
        styleHeader.setBorderBottom(BorderStyle.MEDIUM);
        this.styles.put("header", styleHeader);

        HSSFFont font2= wb.createFont();
	    font2.setFontHeightInPoints((short)11);
	    font2.setFontName("Tahoma");
	    font2.setColor(IndexedColors.BLACK.getIndex());
	    font2.setBold(false);
	    
	    CellStyle styleNormal;
        styleNormal = wb.createCellStyle();
        styleNormal.setFont(font2);
        this.styles.put("normal", styleNormal);
        
        CellStyle styleCurrency;
        styleCurrency = wb.createCellStyle();
        styleCurrency.setFont(font2);
        HSSFDataFormat cf = wb.createDataFormat();
        styleCurrency.setDataFormat(cf.getFormat("€ #,##0.00"));
        this.styles.put("currency", styleCurrency);
        
        CellStyle styleTotal;
        styleTotal = wb.createCellStyle();
        styleTotal.setFont(font);
        this.styles.put("total", styleTotal);
        
        CellStyle styleCurrencyTotal;
        styleCurrencyTotal = wb.createCellStyle();
        styleCurrencyTotal.setFont(font);
        styleCurrencyTotal.setDataFormat(cf.getFormat("€ #,##0.00"));
        this.styles.put("currencyTotal", styleCurrencyTotal);
	}
	
	

}
