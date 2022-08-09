package com.messo.pos;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import javafx.concurrent.Task;

public class PrinterSetup extends Task<Void>{
	
	double margin = 15.0;
	double sizeX = 216.0;  // 8 cm larghezza foglio - 8/2.539*72 circa
	double initSizeY = 170; //sembra che sizeY sia ininfluente, quando finiscono gli elementi da stampare termina il foglio
	
	private final Menu menu;
	private final List<ComandaEntry> list;
	
	public PrinterSetup(Menu menu, List<ComandaEntry> list){
		this.menu = menu;
		this.list = list;
	}
	
	public void printerPrint(Menu menu, List<ComandaEntry> listComanda) {

//		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
//		DocPrintJob printerJob = defaultService.createPrintJob();

		// Create a new PrinterJob object
		PrinterJob printJob = PrinterJob.getPrinterJob();

		// Viene creato un nuovo Book per ogni ricevuta perchè se venisse usato un solo Book
		// avrebbe le stesse dimensioni per tutte le ricevute del Book 
		Book book = null;

		PageFormat documentPageFormat = new PageFormat();
		documentPageFormat.setOrientation(PageFormat.PORTRAIT);
		
		List<ComandaEntry> listFiltered;
		LinkedHashSet<String> categoriesMenu = menu.getMenu().values().stream().sorted((a1,a2) -> a1.getId() - a2.getId())
				.map(Items::getCategory).collect(Collectors.toCollection(LinkedHashSet::new));
		Long counterCategInComanda = listComanda.stream().map(e -> e.getCategory()).distinct().count();
		int i = 1;
		for (String category : categoriesMenu){
			listFiltered = listComanda.stream().sorted((a1,a2) -> a1.getId() - a2.getId())
					.filter(e -> e.getCategory().equals(category)).collect(Collectors.toList());
			
			//La pagina viene definita per ogni singola ricevuta
			Paper paper = new Paper();
			// 18 rappresenta altezza di ogni riga se si aumenta la dimensione del carattere è da modificare anche questo valore
			paper.setSize(sizeX, (double) (initSizeY + listFiltered.size()*18.0));  
			paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight() - margin * 2);
			documentPageFormat.setPaper(paper);
			
			if (listFiltered.size() > 0){
				book = new Book();
				book.append(new PrintableReceipt(listFiltered),documentPageFormat);
				printJob.setPageable(book);

				try {
					updateMessage("Stampa ricevuta "+ i + "/" + counterCategInComanda + " -> " + category);
					updateProgress(i++, counterCategInComanda);
					printJob.print();
				} catch (Exception PrintException) {
					PrintException.printStackTrace();
				}
			}
		}

		//--- Tell the printJob to use the book as the pageable object
//		printJob.setPageable(book);

		//--- Show the print dialog box. If the user click the
		//--- print button we then proceed to print else we cancel
		//--- the process.
		//		    if (printJob.printDialog()) {
//		try {
//			printJob.print();
//		} catch (Exception PrintException) {
//			PrintException.printStackTrace();
//		}
		//		    }
	}
	
	@Override
	public Void call(){
		printerPrint(menu, list);
		return null;
	}
	 
}
