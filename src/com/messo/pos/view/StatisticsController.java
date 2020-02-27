package com.messo.pos.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import com.messo.pos.ExportExcel;
import com.messo.pos.Items;
import com.messo.pos.PasswordDialog;
import com.messo.pos.SimpleLogger;
import com.messo.pos.UtilsCommon;
import com.messo.pos.db.DBAdapter;
import com.messo.pos.db.Orders;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class StatisticsController {
	
	private String passwordAdmin = UtilsCommon.getPropertyValue("admin-password");
	
	@FXML private TableView<Orders> ordersTable;
    @FXML private TableColumn<Orders, String> tColumn1;
    @FXML private TableColumn<Orders, String> tColumn2;
    @FXML private TableColumn<Orders, String> tColumn3;
    @FXML private TableColumn<Orders, String> tColumn4;
    
    @FXML private ComboBox<String> comboBox;
    
    ObservableList<Orders> listO = null;
    
    DBAdapter db = new DBAdapter();
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    @FXML private CategoryAxis xAxis;
	@FXML private NumberAxis yAxis;
    @FXML private BarChart<String,Double> bc;
    
    @FXML private Label numOrdini = new Label();
    
    HashMap<String,Integer> mapNumOrdini = new HashMap<String,Integer>();
    Integer intNumOrdini = 0;
    
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		List<Orders> listOrders = db.getStatistics();
		listO = FXCollections.observableArrayList(listOrders);
		
		mapNumOrdini = db.getNumOrdini();
		
		ordersTable.setItems(listO);
		tColumn1.setText("PIATTO");
		tColumn1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
		tColumn2.setText("PREZZO");
		tColumn2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(UtilsCommon.formatCifre(cellData.getValue().getPrice())));
		tColumn3.setText("QUANTITA");
		tColumn3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getQuantity().toString()));
		tColumn4.setText("TOTALE");
		tColumn4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(UtilsCommon.formatCifre(cellData.getValue().getPriceTotal())));
		
		comboBox.getSelectionModel().selectedIndexProperty().addListener((observable,oldValue,newValue) -> cambioCombo(newValue));
		updateComboBox();
	}
	
	private void cambioCombo(Number newValue){
		String selected = comboBox.getSelectionModel().getSelectedItem();
		ordersTable.setItems(populateList(selected));
		barChartDraw(selected);
	}
	
	private ObservableList<Orders> populateList(String selected){
		ObservableList<Orders> calcList = FXCollections.observableArrayList(listO);
		if (!selected.equals("TOTALE")) {
			FilteredList<Orders> filteredList = new FilteredList<Orders>(calcList);
			filteredList.setPredicate(e -> e.getData().equals(selected));
			calcList.add(addTotal(filteredList, selected));
			intNumOrdini = mapNumOrdini.get(selected);
			numOrdini.setText(String.valueOf(intNumOrdini));
			return filteredList;
		} else {
			ObservableList<Orders> listTot = FXCollections.observableArrayList(convertGrandTotal(listO));
			listTot.add(addTotal(listTot, selected));
			SortedList<Orders> sl = new SortedList<Orders>(listTot);
			sl.setComparator((e1,e2) -> e1.getId()-e2.getId());
			intNumOrdini = mapNumOrdini.values().stream().mapToInt(Integer::intValue).sum();
			numOrdini.setText(String.valueOf(intNumOrdini));
			return sl;
		}
	}
	
	//Aggiunge la riga TOTALE in fondo alla pagina, TOTALE giornaliero
	private Orders addTotal(ObservableList<Orders> filteredList, String selected){
		 //set pseudo class last row for Total
		 PseudoClass lastRow = PseudoClass.getPseudoClass("last-row");
		 ordersTable.setRowFactory(tv -> new TableRow<Orders>(){
	            @Override
	            public void updateIndex(int index) {
	                super.updateIndex(index);
	                pseudoClassStateChanged(lastRow, index >=0 && index == ordersTable.getItems().size() - 1);
	            }
	     });
		 
		 
		Items it = new Items();
		it.setId(100);
		it.setName("TOTALE");
		it.setPrice(0.0);
		it.setCategory(null);
		
		Double total = filteredList.stream().mapToDouble(Orders::getPriceTotal).sum();
		
		Orders orderTotal = new Orders(it, new SimpleIntegerProperty(0), total, selected);
		return orderTotal;
		
	}
	
	//Scrive la pagina del TOTALE
	private List<Orders> convertGrandTotal(List<Orders> list){
		Map<List<String>, List<Orders>> map = list.stream().collect(Collectors.groupingBy(x -> {return new ArrayList<String>(Arrays.<String>asList(Integer.toString(x.getId()),x.getName(),String.valueOf(x.getPrice())));}));
		List<Orders> newList = new ArrayList<Orders>();
		for (Entry<List<String>, List<Orders>> ent : map.entrySet()){
			Items it = new Items();
			it.setId(ent.getValue().get(0).getId());
			it.setName(ent.getValue().get(0).getName());
			it.setPrice(ent.getValue().get(0).getPrice());
			it.setCategory(null);
			Double total = ent.getValue().stream().mapToDouble(e -> e.getPriceTotal()).sum();
			Integer quantity = ent.getValue().stream().mapToInt(e -> e.getQuantity()).sum();
			Orders orderTotal = new Orders(it, new SimpleIntegerProperty(quantity), total, "");
			newList.add(orderTotal);
		}
		
		return newList;
	}
	
	private void barChartDraw(String selected){
		bc.getData().clear();
		ObservableList<Orders> calcList = FXCollections.observableArrayList(listO);
		//Build an map (LinkedHashMap) ordered by the element id in the list, so the first category always comes first
		Map<String, Double>	mapFiltered = calcList.stream().sorted((a1,a2) -> a1.getId() - a2.getId()).filter(e -> (!selected.equals("TOTALE")) ? e.getData().equals(selected) : true).collect(Collectors.groupingBy(Orders::getCategory, LinkedHashMap::new, Collectors.summingDouble(Orders::getPriceTotal)));
		XYChart.Series<String,Double> series1 = new XYChart.Series<String,Double>();
		/*
		series1.getData().add(new XYChart.Data<String,Double>("PRIMO", mapFiltered.get("PRIMO") != null ? mapFiltered.get("PRIMO") : 0.0));
		series1.getData().add(new XYChart.Data<String,Double>("SECONDO", mapFiltered.get("SECONDO") != null ? mapFiltered.get("SECONDO") : 0.0));
		series1.getData().add(new XYChart.Data<String,Double>("FRITTO", mapFiltered.get("FRITTO") != null ? mapFiltered.get("FRITTO") : 0.0));
		series1.getData().add(new XYChart.Data<String,Double>("BEVANDE", mapFiltered.get("BEVANDE") != null ? mapFiltered.get("BEVANDE") : 0.0));
		*/
		mapFiltered.forEach((category, sum) -> series1.getData().add(new XYChart.Data<String,Double>(category, sum != null ? sum : 0.0)));
		
		bc.getData().addAll(series1);
		bc.setAnimated(true);
	}
	
	@FXML
	private void handleCancellaGiorno(){
		String selected = comboBox.getSelectionModel().getSelectedItem();
		if (selected.equals("TOTALE")) {
			showAlert("WARNING","Non è possibile cancellare i record per TOTALE.\nCancellare i record dei singoli giorni.");
			return;
		}
		if (showAlert("CONFIRMATION","Eliminare i record per il giorno " + selected + " ? ").get().equals(ButtonType.OK)){
			db.deleteRecordByDay(selected);
			showAlert("INFORMATION","Record cancellati.\nLa finestra verrà chiusa");
			Stage stage = (Stage) comboBox.getScene().getWindow();
			stage.close();
			
			if (UtilsCommon.logFileOn) SimpleLogger.getLogger.log("INFO", "Cancellati i record del giorno: " + selected);
		}
	}
	
	@FXML
	private void handleEsportaExcel(){
		if (showAlert("CONFIRMATION","Vuoi esportare il resoconto ? ").get().equals(ButtonType.OK)){
			ExportExcel ee = new ExportExcel();
			for (String item : comboBox.getItems()){
				ee.createExcelSheet(item, populateList(item), intNumOrdini);
			}
			Stage stage = (Stage) comboBox.getScene().getWindow();
			ee.salvaExcel(stage);
			
			if (UtilsCommon.logFileOn) SimpleLogger.getLogger.log("INFO", "Excel esportato");
		}
	}
	
	private void updateComboBox(){
		comboBox.setItems(FXCollections.observableArrayList(listO.stream().map(e -> e.getData()).distinct().sorted((a1,a2) -> { if (LocalDate.parse(a1,formatter).isAfter(LocalDate.parse(a2,formatter))) return 1; else return -1;}).collect(Collectors.toList())));
		comboBox.getItems().add("TOTALE");
		comboBox.getSelectionModel().select(0);
	}
	
	// Show a Warning Alert without Header Text
    private Optional<ButtonType> showAlert(String type, String message) {
        Alert alert = new Alert(AlertType.valueOf(type));
        alert.setTitle("Attenzione");
 
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(message);
 
        return alert.showAndWait();
    }
    
    @FXML
    /**
	 * Password Dialog
	 */
	private void showPasswordDialog() {
    	try {
    		PasswordDialog pd = new PasswordDialog(passwordAdmin);
    		Optional<String> password = pd.showAndWait();
    		password.ifPresent(pass -> {
    			if (passwordAdmin.equalsIgnoreCase(pass)) handleCancellaGiorno();
    		});

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
    
}
