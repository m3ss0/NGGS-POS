package com.messo.pos.view;

import java.util.HashMap;
import java.util.Optional;

import com.messo.pos.Items;
import com.messo.pos.Menu;
import com.messo.pos.SimpleLogger;
import com.messo.pos.UtilsCommon;
import com.messo.pos.db.DBAdapter;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MenuEditorController {
	
	@FXML private TableView<Items> menuTable;
    @FXML private TableColumn<Items, String> tColumn1;
    @FXML private TableColumn<Items, String> tColumn2;
    @FXML private TableColumn<Items, String> tColumn3;
    
    @FXML private TextField fieldName = new TextField("");
    @FXML private TextField fieldPrice = new TextField("");
    @FXML private CheckBox checkDisabilita = new CheckBox();
    
    HashMap<String, Items> mapMenu = null;
    ObservableList<Items> listItemsMenu = null;
    SortedList<Items> sortedItemsMenu = null;
    
    DBAdapter db = new DBAdapter();
    private Menu menu = new Menu();
    
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		mapMenu = menu.getMenu();
		listItemsMenu = FXCollections.observableArrayList(mapMenu.values());
		sortedItemsMenu = listItemsMenu.sorted((x,y) -> {return x.getId() - y.getId();});
		
		menuTable.setItems(sortedItemsMenu);
		tColumn1.setText("PIATTO");
		tColumn1.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		tColumn2.setText("PREZZO");
		tColumn2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(UtilsCommon.formatCifre(cellData.getValue().getPrice())));
		tColumn3.setText("CAT");
		tColumn3.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
		
		menuTable.setOnMouseClicked(e -> onClickEvent());
		checkDisabilita.setOnMouseClicked(e -> onCheckboxClick());
		checkDisabilita.setAllowIndeterminate(false);
		
	}
	
	private void onClickEvent(){
		Items it = menuTable.getSelectionModel().getSelectedItem();
		fieldName.setText(it.getName());
		fieldPrice.setText(UtilsCommon.formatCifre(it.getPrice()));
		
		if (it.getName().equals("xxx")){
			checkDisabilita.setSelected(true);
			fieldName.setDisable(true);
			fieldPrice.setDisable(true);
		} else {
			checkDisabilita.setSelected(false);
			fieldName.setDisable(false);
			fieldPrice.setDisable(false);
		}
	}
	
	private void onCheckboxClick(){
		Items it = menuTable.getSelectionModel().getSelectedItem();
		
		if (checkDisabilita.isSelected()) {
			checkDisabilita.setSelected(true);
			fieldName.setDisable(true);
			fieldPrice.setDisable(true);
			fieldName.setText("xxx");
			fieldPrice.setText("0,00");
		} else {
			checkDisabilita.setSelected(false);
			fieldName.setDisable(false);
			fieldPrice.setDisable(false);
			fieldName.setText(it.getName());
			fieldPrice.setText(UtilsCommon.formatCifre(it.getPrice()));
		}
	}
	
	@FXML
	private void handleSave(){
		Items it = menuTable.getSelectionModel().getSelectedItem();
		int index = menuTable.getSelectionModel().getSelectedIndex();
		
		if (index == -1) {
			showAlert("WARNING", "Non è stato selezionato alcun piatto dalla lista.");
			return;
		}
		
		//Log
		if (UtilsCommon.logFileOn) SimpleLogger.getLogger.log("INFO", "Menu modified - old: " + it.getId() + "|" + it.getName() + "|" + it.getPrice());
		
		listItemsMenu.remove(it);
		it.setName(fieldName.getText());
		it.setPrice(Double.valueOf(fieldPrice.getText().replace(",",".")));
		
		listItemsMenu.add(it);
		menuTable.refresh();
		menuTable.getSelectionModel().select(index);
		
		//Update DB
		db.updateMenuItem(it);
		
		//Log
		if (UtilsCommon.logFileOn) SimpleLogger.getLogger.log("INFO", "Menu modified - new: " + it.getId() + "|" + it.getName() + "|" + it.getPrice());
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
}
