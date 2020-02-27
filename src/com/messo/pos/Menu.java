package com.messo.pos;

import java.util.HashMap;

import com.messo.pos.db.DBAdapter;

public class Menu {
	
	private HashMap<String, Items> listItems;
	private DBAdapter db = new DBAdapter();
	
	public HashMap<String, Items> getMenu(){
		return listItems;
	}
	
	public Menu(){
		try{
			listItems = db.readTableMenu();
			
			/* Inserisce un default se non legge da DB
			if (listItems.size() == 0){
				listItems.put("1", new Items(1,"Risotto",5.00,"PRIMO"));
				listItems.put("2", new Items(2,"Maccheroni",6.00,"PRIMO"));
				listItems.put("3", new Items(3,"Bis Maccheroni",7.50,"PRIMO"));
				listItems.put("4", new Items(4,"Insalata Farro",6.00,"PRIMO"));
				listItems.put("5", new Items(5,"Insalata Farro Veg",5.50,"PRIMO"));
				listItems.put("6", new Items(6,"xxx",0.00,"PRIMO"));
				
				listItems.put("10", new Items(10,"Stinco di maiale",9.00,"SECONDO"));
				listItems.put("11", new Items(11,"Picanha",8.00,"SECONDO"));
				listItems.put("12", new Items(12,"Prosciutto Praga",7.00,"SECONDO"));
				listItems.put("13", new Items(13,"Hamburger",7.50,"SECONDO"));
				listItems.put("14", new Items(14,"Luccio in salsa",7.50,"SECONDO"));
				listItems.put("15", new Items(15,"Verdure Grigliate",4.00,"SECONDO"));
				listItems.put("16", new Items(16,"xxx",0.00,"SECONDO"));
				listItems.put("17", new Items(17,"xxx",0.00,"SECONDO"));
				listItems.put("18", new Items(18,"xxx",0.00,"SECONDO"));
				
				listItems.put("20", new Items(20,"Patatine",2.00,"FRITTO"));
				listItems.put("21", new Items(21,"Fritto Misto",9.50,"FRITTO"));
				listItems.put("22", new Items(22,"xxx",0.00,"FRITTO"));
				listItems.put("23", new Items(23,"xxx",0.00,"FRITTO"));
				listItems.put("24", new Items(24,"xxx",0.00,"FRITTO"));
				listItems.put("25", new Items(25,"xxx",0.00,"FRITTO"));
				
				listItems.put("30", new Items(30,"Birra 1 litro",13.00,"BEVANDE"));
				listItems.put("31", new Items(31,"Ricarica birra 1 l",8.00,"BEVANDE"));
				listItems.put("32", new Items(32,"Birra media",4.00,"BEVANDE"));
				listItems.put("33", new Items(33,"Birra piccola",2.00,"BEVANDE"));
				listItems.put("34", new Items(34,"Vino 1 litro",5.00,"BEVANDE"));
				listItems.put("35", new Items(35,"Vino 1/2 litro",2.50,"BEVANDE"));
				listItems.put("36", new Items(36,"Acqua 1/2 litro",1.00,"BEVANDE"));
				listItems.put("37", new Items(37,"Coca Cola spina",2.00,"BEVANDE"));
				listItems.put("38", new Items(38,"Lemon Soda",1.50,"BEVANDE"));
				listItems.put("39", new Items(39,"Mignon",1.00,"BEVANDE"));
				listItems.put("40",new Items(40,"Caff�",1.00,"BEVANDE"));
				listItems.put("41",new Items(41,"Vino bicchiere",1.00,"BEVANDE"));
				listItems.put("42",new Items(42,"Birra gluten free",3.00,"BEVANDE"));
				listItems.put("43",new Items(43,"Sbrisolona",5.00,"BEVANDE"));
				listItems.put("44",new Items(44,"Sbrisolona ciocc",6.00,"BEVANDE"));
				listItems.put("45",new Items(45,"xxx",0.00,"BEVANDE"));
				listItems.put("46",new Items(46,"xxx",0.00,"BEVANDE"));
				listItems.put("47",new Items(47,"xxx",0.00,"BEVANDE"));
			}
			*/
		} catch (Exception e){}
	}

}
