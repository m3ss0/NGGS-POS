package com.messo.pos.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.messo.pos.ComandaEntry;
import com.messo.pos.Items;
import com.messo.pos.SimpleLogger;
import com.messo.pos.UtilsCommon;

import javafx.beans.property.SimpleIntegerProperty;

public class DBAdapter {
	
	// JDBC driver name and database URL
	// Windows C:\Users\<utente>\...
	// Probably with little modifications is possible to shift to HSQLDB
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = UtilsCommon.getPropertyValue("db.link");  
	
	//  Database credentials 
	static final String USER = "nggs"; 
	static final String PASS = "nggs1234";
	
	final String pcName = UtilsCommon.getPropertyValue("db.pcname");  
	
	private Connection getConnection(){
		Connection conn = null;
		
		try { 
			// STEP 1: Register JDBC driver 
			Class.forName(JDBC_DRIVER); 

			//STEP 2: Open a connection 
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			conn.setAutoCommit(true);

		} catch(SQLException se) { 
			//Handle errors for JDBC 
			se.printStackTrace(); 
		} catch(Exception e) { 
			//Handle errors for Class.forName 
			e.printStackTrace(); 
		}
		
		return conn;
	}
	
	public void initialSetupTableOrders(){
		
		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();){

			String sql = "CREATE TABLE ORDERS " + 
		            "(Id IDENTITY PRIMARY KEY," +
					" IdProd NUMERIC, " +
		            " Nome VARCHAR(30) not NULL, " + 
		            " PrezzoUnitario DOUBLE, " +  
		            " Category VARCHAR(25), " +  
		            " Quantity INTEGER, " +  
		            " Prezzo DECIMAL(10,2), " +
		            " Data TIMESTAMP, " +
		            " PcName VARCHAR(10))" ;
			stmt.execute(sql);
			
			System.out.println("Table ORDERS created...");
			
		}catch (Exception e){
			System.out.println("Table ORDERS already present");
			
		}
	}
	
	public void initialSetupTableMenu(){
		
		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();){

			String line;
			
			BufferedReader input = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("sql/menu.sql")));

			while ((line = input.readLine()) != null) {
				stmt.execute(line);
			}

			input.close();
			
			System.out.println("Table MENU created...");
			
		}catch (Exception e){
			System.out.println("Table MENU already present");
		}
	}
	
	public HashMap<String, Items> readTableMenu(){
		HashMap<String, Items> listItems = new HashMap<String, Items>();
		
		String sql = "SELECT * FROM MENU";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Items it = new Items();
				String id = rs.getString(1);
				it.setId(rs.getInt(2));
				it.setName(rs.getString(3));
				it.setPrice(rs.getDouble(4));
				it.setCategory(rs.getString(5));
				
				listItems.put(id, it);
	        }
			
			System.out.println("Read Table MENU");
			
		}catch (Exception e){
			System.out.println("Error reading table MENU: " + e.getMessage());
			
		}
		
		return listItems;
	}
	
	public boolean saveRecords(List<ComandaEntry> comanda){
		
		Double totalItem = Double.valueOf(0);
		Double grandTotal = Double.valueOf(0);

		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO ORDERS(IdProd, Nome, PrezzoUnitario, Category, Quantity, Prezzo, Data, PcName) VALUES (?,?,?,?,?,?,?,?)");){
			
			LocalDateTime ldt = LocalDateTime.now();
			
			conn.setAutoCommit(false);
			
			for (ComandaEntry entry : comanda){
				ps.setInt(1, entry.getId());
				ps.setString(2, entry.getName());
				ps.setDouble(3, entry.getPrice());
				ps.setString(4, entry.getCategory());
				ps.setInt(5, entry.getQuantity());
				totalItem = entry.getPrice()*entry.getQuantity();
				ps.setBigDecimal(6, new BigDecimal(entry.getPrice()*entry.getQuantity()));
				ps.setTimestamp(7, Timestamp.valueOf(ldt));
				ps.setString(8, pcName);
				
				grandTotal = grandTotal + totalItem;

				ps.executeUpdate();
			}
			
			conn.commit();
		
			System.out.println("Records saved in table ORDERS");
			if (UtilsCommon.logFileOn) SimpleLogger.getLogger.log("INFO", "Record salvati - Total: " + grandTotal.toString());
			
			return true;
			
		}catch (Exception e){
			System.out.println("Error saving data: " + e.getMessage());
			if (UtilsCommon.logFileOn) SimpleLogger.getLogger.log("ERROR", "Errore salvataggio - Total: " + grandTotal.toString());
			return false;
		}
	}
	
	public List<Orders> getStatistics(){
		List<Orders> list = new ArrayList<Orders>();
		
		String sql = "SELECT IDPROD,NOME,PREZZOUNITARIO,MIN(CATEGORY),SUM(QUANTITY),SUM(PREZZO),TO_CHAR(DATEADD('HOUR',-5,DATA),'dd/MM/yyyy')" +
				 " FROM ORDERS GROUP BY TO_CHAR(DATEADD('HOUR',-5,DATA),'dd/MM/yyyy'), IDPROD, NOME, PREZZOUNITARIO " +
				 " ORDER BY TO_CHAR(DATEADD('HOUR',-5,DATA),'dd/MM/yyyy') ASC ,IDPROD ASC";
		
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Items it = new Items();
				it.setId(rs.getInt(1));
				it.setName(rs.getString(2));
				it.setPrice(rs.getDouble(3));
				it.setCategory(rs.getString(4));
				
				SimpleIntegerProperty si = new SimpleIntegerProperty(rs.getInt(5));
				Double total = rs.getDouble(6);
				String dt = rs.getString(7);
				
				Orders order = new Orders(it, si, total, dt);
				
				list.add(order);
	        }
			
		}catch (Exception e){
			System.out.println("Error reading Statistics" + e.getMessage());
			
		}
		
		return list;
	}
	
	public HashMap<String,Integer> getNumOrdini(){
		HashMap<String, Integer> mapNumOrdini = new HashMap<>();
		
		String sql = "SELECT TO_CHAR(DATEADD('HOUR',-5,DATA), 'dd/MM/yyyy'), COUNT(*) FROM (SELECT DATA, COUNT(*) FROM ORDERS GROUP BY DATA) "
				+ "GROUP BY TO_CHAR(DATEADD('HOUR',-5,DATA), 'dd/MM/yyyy') ";
		
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				mapNumOrdini.put(rs.getString(1), rs.getInt(2));
	        }
			
		}catch (Exception e){
			System.out.println("Error reading NumOrders" + e.getMessage());
			
		}
		
		return mapNumOrdini;
	}
	
	public void deleteRecordByDay(String day){
		
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM ORDERS WHERE TO_CHAR(DATEADD('HOUR',-5,DATA),'dd/MM/yyyy') = '" + day + "'");){
			
			stmt.executeUpdate();
			
			System.out.println("Cancellati record per giorno " + day);
			
		}catch (Exception e){
			System.out.println("Errore Cancellazione " + e.getMessage());
		}
	}
	
	
	public void updateMenuItem(Items it){
		String sql = "UPDATE MENU SET IDPROD = ? , "
				+ "NAME = ? ,"
				+ "PREZZOUNITARIO = ? ,"
				+ "CATEGORY = ? "
				+ "WHERE ID = ?";
		
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);){
			
			ps.setInt(1, it.getId());
			ps.setString(2, it.getName());
			ps.setDouble(3, it.getPrice());
			ps.setString(4, it.getCategory());
			ps.setString(5, String.valueOf(it.getId()));
			
			ps.executeUpdate();
		
			System.out.println("UPDATE Menu ok: " + it.getName() + "|" + it.getPrice());
			
		}catch (Exception e){
			System.out.println("Error Update Menu " + e.getMessage());
			
		}
	}
	
	/*
	public void testTable(){
		Connection conn = null;
		Statement stmt = null;
		
		try{
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ORDERS(IdProd, Nome, PrezzoUnitario, Category, Quantity, Prezzo, Data) VALUES (?,?,?,?,?,?,?)");
			
			
			for(int i=0; i < 1000; i++) {
				ps.setInt(1, 1);
				ps.setString(2, "Risotto");
				ps.setDouble(3, 5.00);
				ps.setString(4, "PRIMO");
				ps.setInt(5, (new Random()).nextInt(7));
				ps.setBigDecimal(6, new BigDecimal(2*5.00));
				Calendar c = new GregorianCalendar();
				c.add(Calendar.DAY_OF_MONTH, -4);
				ps.setTimestamp(7, new Timestamp(c.getTime().getTime()));

				ps.executeUpdate();
				
				System.out.println("Example Row inserted " + i);
			}
			
			
		}catch (Exception e){
			System.out.println("Error " + e.getMessage());
			
		}finally{
			try{
				if(stmt!=null) stmt.close(); 
				if(conn!=null) conn.close();
			}catch (Exception e){}
		}
	}
	*/
	
	/*
	public static void main(String[] args){
		DBAdapter db = new DBAdapter();
//		db.testTable();
//		db.initialSetupTableMenu();
		
		try (Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO ORDERS(IdProd, Nome, PrezzoUnitario, Category, Quantity, Prezzo, Data, PcName) VALUES (?,?,?,?,?,?,?,?)");){
			conn.setAutoCommit(false);
			
			long init = System.currentTimeMillis();
			
			for(int i= 100; i<10100; i++){
				ps.setInt(1, i);
				ps.setString(2, "TEST");
				ps.setDouble(3, 1.00);
				ps.setString(4, "CATEGORY");
				ps.setInt(5, 1);
				ps.setBigDecimal(6, new BigDecimal("1"));
				ps.setTimestamp(7, null);
				ps.setString(8, "pc1");

				ps.addBatch();;
			}
			
			ps.executeBatch();
			
//			conn.commit();
			
			long end = System.currentTimeMillis();
			
			System.out.println("time: " + (end - init));
		
			
		}catch (Exception e){
			System.out.println("Error Update Menu " + e.getMessage());
			
		}
	}
	*/
	

}
