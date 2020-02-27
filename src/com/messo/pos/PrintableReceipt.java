package com.messo.pos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*La classe rappresenta la ricevuta da stampare*/

public class PrintableReceipt implements Printable{
	
	private SimpleDateFormat sdf = new SimpleDateFormat(UtilsCommon.getPropertyValue("receipt.title.dateFormat"));
	
	private List<ComandaEntry> listCom;
	
	double titleY = 60;
	int spaceToList = 110;
	
	public PrintableReceipt(List<ComandaEntry> list){
		listCom = list;
	}

	public int print(Graphics g, PageFormat pageFormat, int page) {

		//--- Create the Graphics2D object
		Graphics2D g2d = (Graphics2D) g;

		//--- Translate the origin to 0,0 for the top left corner
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		//--- Set the default drawing color to black
		g2d.setPaint(Color.black);

		//--- Print the title
		String titleText = UtilsCommon.getPropertyValue("receipt.title");
		Font titleFont = new Font("Comic Sans MS", Font.BOLD, Integer.parseInt(UtilsCommon.getPropertyValue("receipt.title.font.size")));
		g2d.setFont(titleFont);

		//--- Compute the horizontal center of the page
		FontMetrics fontMetrics = g2d.getFontMetrics();
		double titleX = (pageFormat.getImageableWidth() / 2) - (fontMetrics.stringWidth(titleText) / 2);

		g2d.drawString(titleText, (int) titleX, (int) titleY);

		String dateText = sdf.format(new Date());
		Font dateFont = new Font("Tahoma", Font.BOLD, 12);
		g2d.setFont(dateFont);

		//--- Compute the horizontal center of the page
		fontMetrics = g2d.getFontMetrics();
		double dateX = (pageFormat.getImageableWidth() / 2) - (fontMetrics.stringWidth(dateText) / 2);
		double dateY = titleY + fontMetrics.getHeight();
		g2d.drawString(dateText, (int) dateX, (int) dateY);

		Font orderFont = new Font("Tahoma", Font.PLAIN, Integer.parseInt(UtilsCommon.getPropertyValue("receipt.orderlist.font.size")));
		g2d.setFont(orderFont);
		FontMetrics fm = g2d.getFontMetrics();
		int i = 0;
		for (ComandaEntry com : listCom){
			g2d.drawString(com.getName(), (int) pageFormat.getImageableX(), spaceToList + (fm.getHeight() * i));
			g2d.drawString(com.getQuantity().toString(), (int) (pageFormat.getImageableWidth() - fm.stringWidth(com.getQuantity().toString())), spaceToList + (fm.getHeight() * i));
			g2d.draw(new Line2D.Double(pageFormat.getImageableX(), spaceToList + (fm.getHeight() * i) + 2 , pageFormat.getImageableWidth(), spaceToList + (fm.getHeight() * i) + 2));
			i++;
		}
		//aggiungo spazio vuoto
		g2d.drawString("~~", (int) (pageFormat.getImageableWidth() / 2) - (fontMetrics.stringWidth("~~") / 2), spaceToList + (fm.getHeight() * listCom.size()) + 30);
	
		
		return (PAGE_EXISTS);
	}

}
