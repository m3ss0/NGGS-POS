package com.messo.pos.view;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import com.messo.pos.Comanda;
import com.messo.pos.ComandaEntry;
import com.messo.pos.Items;
import com.messo.pos.MainApp;
import com.messo.pos.Menu;
import com.messo.pos.PasswordDialog;
import com.messo.pos.PrinterSetup;
import com.messo.pos.UtilsCommon;
import com.messo.pos.db.DBAdapter;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainPOSController {
	
	private String passwordAdmin = UtilsCommon.getPropertyValue("admin-password");
	
	private MainApp mainApp;
	private Comanda comanda = new Comanda();
	private Menu menu = new Menu();
	private boolean alreadyPrinted = false;
	
	private ScaleTransition trans = new ScaleTransition();
	
	private ObservableList<ComandaEntry> obsList = FXCollections.observableArrayList(comanda.getLista().values());
	
	@FXML
	private Label total = new Label("€ 0.00");
	
	/*PRIMI*/
	@FXML private Spinner<Integer> spinnerP0;
	@FXML private Label LabelP0 = new Label();
	@FXML private Label LabelP0t = new Label();
	
	@FXML private Spinner<Integer> spinnerP1;
	@FXML private Label LabelP1 = new Label();
	@FXML private Label LabelP1t = new Label();
	
	@FXML private Spinner<Integer> spinnerP2;
	@FXML private Label LabelP2 = new Label();
	@FXML private Label LabelP2t = new Label();
	
	@FXML private Spinner<Integer> spinnerP3;
	@FXML private Label LabelP3 = new Label();
	@FXML private Label LabelP3t = new Label();
	
	@FXML private Spinner<Integer> spinnerP4;
	@FXML private Label LabelP4 = new Label();
	@FXML private Label LabelP4t = new Label();
	
	@FXML private Spinner<Integer> spinnerP5;
	@FXML private Label LabelP5 = new Label();
	@FXML private Label LabelP5t = new Label();
	
	/*SECONDI*/
	@FXML private Spinner<Integer> spinnerS0;
	@FXML private Label LabelS0 = new Label();
	@FXML private Label LabelS0t = new Label();
	
	@FXML private Spinner<Integer> spinnerS1;
	@FXML private Label LabelS1 = new Label();
	@FXML private Label LabelS1t = new Label();
	
	@FXML private Spinner<Integer> spinnerS2;
	@FXML private Label LabelS2 = new Label();
	@FXML private Label LabelS2t = new Label();
	
	@FXML private Spinner<Integer> spinnerS3;
	@FXML private Label LabelS3 = new Label();
	@FXML private Label LabelS3t = new Label();
	
	@FXML private Spinner<Integer> spinnerS4;
	@FXML private Label LabelS4 = new Label();
	@FXML private Label LabelS4t = new Label();
	
	@FXML private Spinner<Integer> spinnerS5;
	@FXML private Label LabelS5 = new Label();
	@FXML private Label LabelS5t = new Label();
	
	@FXML private Spinner<Integer> spinnerS6;
	@FXML private Label LabelS6 = new Label();
	@FXML private Label LabelS6t = new Label();
	
	@FXML private Spinner<Integer> spinnerS7;
	@FXML private Label LabelS7 = new Label();
	@FXML private Label LabelS7t = new Label();
	
	@FXML private Spinner<Integer> spinnerS8;
	@FXML private Label LabelS8 = new Label();
	@FXML private Label LabelS8t = new Label();
	
	/*FRITTO*/
	@FXML private Spinner<Integer> spinnerF0;
	@FXML private Label LabelF0 = new Label();
	@FXML private Label LabelF0t = new Label();
	
	@FXML private Spinner<Integer> spinnerF1;
	@FXML private Label LabelF1 = new Label();
	@FXML private Label LabelF1t = new Label();
	
	@FXML private Spinner<Integer> spinnerF2;
	@FXML private Label LabelF2 = new Label();
	@FXML private Label LabelF2t = new Label();
	
	@FXML private Spinner<Integer> spinnerF3;
	@FXML private Label LabelF3 = new Label();
	@FXML private Label LabelF3t = new Label();
	
	@FXML private Spinner<Integer> spinnerF4;
	@FXML private Label LabelF4 = new Label();
	@FXML private Label LabelF4t = new Label();
	
	@FXML private Spinner<Integer> spinnerF5;
	@FXML private Label LabelF5 = new Label();
	@FXML private Label LabelF5t = new Label();
	
	/*BEVANDE*/
	@FXML private Spinner<Integer> spinnerB0;
	@FXML private Label LabelB0 = new Label();
	@FXML private Label LabelB0t = new Label();
	@FXML private Spinner<Integer> spinnerB1;
	@FXML private Label LabelB1 = new Label();
	@FXML private Label LabelB1t = new Label();
	@FXML private Spinner<Integer> spinnerB2;
	@FXML private Label LabelB2 = new Label();
	@FXML private Label LabelB2t = new Label();
	@FXML private Spinner<Integer> spinnerB3;
	@FXML private Label LabelB3 = new Label();
	@FXML private Label LabelB3t = new Label();
	@FXML private Spinner<Integer> spinnerB4;
	@FXML private Label LabelB4 = new Label();
	@FXML private Label LabelB4t = new Label();
	@FXML private Spinner<Integer> spinnerB5;
	@FXML private Label LabelB5 = new Label();
	@FXML private Label LabelB5t = new Label();
	@FXML private Spinner<Integer> spinnerB6;
	@FXML private Label LabelB6 = new Label();
	@FXML private Label LabelB6t = new Label();
	@FXML private Spinner<Integer> spinnerB7;
	@FXML private Label LabelB7 = new Label();
	@FXML private Label LabelB7t = new Label();
	@FXML private Spinner<Integer> spinnerB8;
	@FXML private Label LabelB8 = new Label();
	@FXML private Label LabelB8t = new Label();
	@FXML private Spinner<Integer> spinnerB9;
	@FXML private Label LabelB9 = new Label();
	@FXML private Label LabelB9t = new Label();
	@FXML private Spinner<Integer> spinnerB10;
	@FXML private Label LabelB10 = new Label();
	@FXML private Label LabelB10t = new Label();
	@FXML private Spinner<Integer> spinnerB11;
	@FXML private Label LabelB11 = new Label();
	@FXML private Label LabelB11t = new Label();
	@FXML private Spinner<Integer> spinnerB12;
	@FXML private Label LabelB12 = new Label();
	@FXML private Label LabelB12t = new Label();
	@FXML private Spinner<Integer> spinnerB13;
	@FXML private Label LabelB13 = new Label();
	@FXML private Label LabelB13t = new Label();
	@FXML private Spinner<Integer> spinnerB14;
	@FXML private Label LabelB14 = new Label();
	@FXML private Label LabelB14t = new Label();
	@FXML private Spinner<Integer> spinnerB15;
	@FXML private Label LabelB15 = new Label();
	@FXML private Label LabelB15t = new Label();
	@FXML private Spinner<Integer> spinnerB16;
	@FXML private Label LabelB16 = new Label();
	@FXML private Label LabelB16t = new Label();
	@FXML private Spinner<Integer> spinnerB17;
	@FXML private Label LabelB17 = new Label();
	@FXML private Label LabelB17t = new Label();
	
	@FXML private ImageView imageView;
	
	@FXML private TextField contanteResto = new TextField("0");
	@FXML private TextField restoCalcolato = new TextField("0.00");
	
	
	@FXML private TableView<ComandaEntry> comandaTable;
    @FXML private TableColumn<ComandaEntry, String> comandaColumn1;
    @FXML private TableColumn<ComandaEntry, String> comandaColumn2;
    
    @FXML private Label LabelAlert = new Label();
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    	spinnerP0.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelP0.setText(menu.getMenu().get("1").getName());
    	LabelP0t.setText(UtilsCommon.formatCifre(menu.getMenu().get("1").getPrice()));
    	if (menu.getMenu().get("1").getName().equals("xxx")) spinnerP0.setDisable(true);
    	
    	spinnerP1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelP1.setText(menu.getMenu().get("2").getName());
    	LabelP1t.setText(UtilsCommon.formatCifre(menu.getMenu().get("2").getPrice()));
    	if (menu.getMenu().get("2").getName().equals("xxx")) spinnerP1.setDisable(true);
    	
    	spinnerP2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelP2.setText(menu.getMenu().get("3").getName());
    	LabelP2t.setText(UtilsCommon.formatCifre(menu.getMenu().get("3").getPrice()));
    	if (menu.getMenu().get("3").getName().equals("xxx")) spinnerP2.setDisable(true);
    	
    	spinnerP3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelP3.setText(menu.getMenu().get("4").getName());
    	LabelP3t.setText(UtilsCommon.formatCifre(menu.getMenu().get("4").getPrice()));
    	if (menu.getMenu().get("4").getName().equals("xxx")) spinnerP3.setDisable(true);
    	
    	spinnerP4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelP4.setText(menu.getMenu().get("5").getName());
    	LabelP4t.setText(UtilsCommon.formatCifre(menu.getMenu().get("5").getPrice()));
    	if (menu.getMenu().get("5").getName().equals("xxx")) spinnerP4.setDisable(true);
    	
    	spinnerP5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelP5.setText(menu.getMenu().get("6").getName());
    	LabelP5t.setText(UtilsCommon.formatCifre(menu.getMenu().get("6").getPrice()));
    	if (menu.getMenu().get("6").getName().equals("xxx")) spinnerP5.setDisable(true);
    	
    	spinnerS0.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS0.setText(menu.getMenu().get("10").getName());
    	LabelS0t.setText(UtilsCommon.formatCifre(menu.getMenu().get("10").getPrice()));
    	if (menu.getMenu().get("10").getName().equals("xxx")) spinnerS0.setDisable(true);
    	
    	spinnerS1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS1.setText(menu.getMenu().get("11").getName());
    	LabelS1t.setText(UtilsCommon.formatCifre(menu.getMenu().get("11").getPrice()));
    	if (menu.getMenu().get("11").getName().equals("xxx")) spinnerS1.setDisable(true);
    	
    	spinnerS2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS2.setText(menu.getMenu().get("12").getName());
    	LabelS2t.setText(UtilsCommon.formatCifre(menu.getMenu().get("12").getPrice()));
    	if (menu.getMenu().get("12").getName().equals("xxx")) spinnerS2.setDisable(true);
    	
    	spinnerS3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS3.setText(menu.getMenu().get("13").getName());
    	LabelS3t.setText(UtilsCommon.formatCifre(menu.getMenu().get("13").getPrice()));
    	if (menu.getMenu().get("13").getName().equals("xxx")) spinnerS3.setDisable(true);
    	
    	spinnerS4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS4.setText(menu.getMenu().get("14").getName());
    	LabelS4t.setText(UtilsCommon.formatCifre(menu.getMenu().get("14").getPrice()));
    	if (menu.getMenu().get("14").getName().equals("xxx")) spinnerS4.setDisable(true);
    	
    	spinnerS5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS5.setText(menu.getMenu().get("15").getName());
    	LabelS5t.setText(UtilsCommon.formatCifre(menu.getMenu().get("15").getPrice()));
    	if (menu.getMenu().get("15").getName().equals("xxx")) spinnerS5.setDisable(true);
    	
    	spinnerS6.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS6.setText(menu.getMenu().get("16").getName());
    	LabelS6t.setText(UtilsCommon.formatCifre(menu.getMenu().get("16").getPrice()));
    	if (menu.getMenu().get("16").getName().equals("xxx")) spinnerS6.setDisable(true);
    	
    	spinnerS7.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS7.setText(menu.getMenu().get("17").getName());
    	LabelS7t.setText(UtilsCommon.formatCifre(menu.getMenu().get("17").getPrice()));
    	if (menu.getMenu().get("17").getName().equals("xxx")) spinnerS7.setDisable(true);
    	
    	spinnerS8.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelS8.setText(menu.getMenu().get("18").getName());
    	LabelS8t.setText(UtilsCommon.formatCifre(menu.getMenu().get("18").getPrice()));
    	if (menu.getMenu().get("18").getName().equals("xxx")) spinnerS8.setDisable(true);
    	
    	spinnerF0.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelF0.setText(menu.getMenu().get("20").getName());
    	LabelF0t.setText(UtilsCommon.formatCifre(menu.getMenu().get("20").getPrice()));
    	if (menu.getMenu().get("20").getName().equals("xxx")) spinnerF0.setDisable(true);
    	
    	spinnerF1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelF1.setText(menu.getMenu().get("21").getName());
    	LabelF1t.setText(UtilsCommon.formatCifre(menu.getMenu().get("21").getPrice()));
    	if (menu.getMenu().get("21").getName().equals("xxx")) spinnerF1.setDisable(true);
    	
    	spinnerF2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelF2.setText(menu.getMenu().get("22").getName());
    	LabelF2t.setText(UtilsCommon.formatCifre(menu.getMenu().get("22").getPrice()));
    	if (menu.getMenu().get("22").getName().equals("xxx")) spinnerF2.setDisable(true);
    	
    	spinnerF3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelF3.setText(menu.getMenu().get("23").getName());
    	LabelF3t.setText(UtilsCommon.formatCifre(menu.getMenu().get("23").getPrice()));
    	if (menu.getMenu().get("23").getName().equals("xxx")) spinnerF3.setDisable(true);
    	
    	spinnerF4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelF4.setText(menu.getMenu().get("24").getName());
    	LabelF4t.setText(UtilsCommon.formatCifre(menu.getMenu().get("24").getPrice()));    	
    	if (menu.getMenu().get("24").getName().equals("xxx")) spinnerF4.setDisable(true);
    	
    	spinnerF5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelF5.setText(menu.getMenu().get("25").getName());
    	LabelF5t.setText(UtilsCommon.formatCifre(menu.getMenu().get("25").getPrice()));    	
    	if (menu.getMenu().get("25").getName().equals("xxx")) spinnerF5.setDisable(true);
    	
    	spinnerB0.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB0.setText(menu.getMenu().get("30").getName());
    	LabelB0t.setText(UtilsCommon.formatCifre(menu.getMenu().get("30").getPrice()));
    	if (menu.getMenu().get("30").getName().equals("xxx")) spinnerB0.setDisable(true);
    	
    	spinnerB1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB1.setText(menu.getMenu().get("31").getName());
    	LabelB1t.setText(UtilsCommon.formatCifre(menu.getMenu().get("31").getPrice()));
    	if (menu.getMenu().get("31").getName().equals("xxx")) spinnerB1.setDisable(true);
    	
    	spinnerB2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB2.setText(menu.getMenu().get("32").getName());
    	LabelB2t.setText(UtilsCommon.formatCifre(menu.getMenu().get("32").getPrice()));
    	if (menu.getMenu().get("32").getName().equals("xxx")) spinnerB2.setDisable(true);
    	
    	spinnerB3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB3.setText(menu.getMenu().get("33").getName());
    	LabelB3t.setText(UtilsCommon.formatCifre(menu.getMenu().get("33").getPrice()));
    	if (menu.getMenu().get("33").getName().equals("xxx")) spinnerB3.setDisable(true);
    	
    	spinnerB4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB4.setText(menu.getMenu().get("34").getName());
    	LabelB4t.setText(UtilsCommon.formatCifre(menu.getMenu().get("34").getPrice()));
    	if (menu.getMenu().get("34").getName().equals("xxx")) spinnerB4.setDisable(true);
    	
    	spinnerB5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB5.setText(menu.getMenu().get("35").getName());
    	LabelB5t.setText(UtilsCommon.formatCifre(menu.getMenu().get("35").getPrice()));
    	if (menu.getMenu().get("35").getName().equals("xxx")) spinnerB5.setDisable(true);
    	
    	spinnerB6.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB6.setText(menu.getMenu().get("36").getName());
    	LabelB6t.setText(UtilsCommon.formatCifre(menu.getMenu().get("36").getPrice()));
    	if (menu.getMenu().get("36").getName().equals("xxx")) spinnerB6.setDisable(true);
    	
    	spinnerB7.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB7.setText(menu.getMenu().get("37").getName());
    	LabelB7t.setText(UtilsCommon.formatCifre(menu.getMenu().get("37").getPrice()));
    	if (menu.getMenu().get("37").getName().equals("xxx")) spinnerB7.setDisable(true);
    	
    	spinnerB8.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB8.setText(menu.getMenu().get("38").getName());
    	LabelB8t.setText(UtilsCommon.formatCifre(menu.getMenu().get("38").getPrice()));
    	if (menu.getMenu().get("38").getName().equals("xxx")) spinnerB8.setDisable(true);
    	
    	spinnerB9.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB9.setText(menu.getMenu().get("39").getName());
    	LabelB9t.setText(UtilsCommon.formatCifre(menu.getMenu().get("39").getPrice()));
    	if (menu.getMenu().get("39").getName().equals("xxx")) spinnerB9.setDisable(true);
    	
    	spinnerB10.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB10.setText(menu.getMenu().get("40").getName());
    	LabelB10t.setText(UtilsCommon.formatCifre(menu.getMenu().get("40").getPrice()));
    	if (menu.getMenu().get("40").getName().equals("xxx")) spinnerB10.setDisable(true);
    	
    	spinnerB11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB11.setText(menu.getMenu().get("41").getName());
    	LabelB11t.setText(UtilsCommon.formatCifre(menu.getMenu().get("41").getPrice()));
    	if (menu.getMenu().get("41").getName().equals("xxx")) spinnerB11.setDisable(true);
    	
    	spinnerB12.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB12.setText(menu.getMenu().get("42").getName());
    	LabelB12t.setText(UtilsCommon.formatCifre(menu.getMenu().get("42").getPrice()));
    	if (menu.getMenu().get("42").getName().equals("xxx")) spinnerB12.setDisable(true);
    	
    	spinnerB13.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB13.setText(menu.getMenu().get("43").getName());
    	LabelB13t.setText(UtilsCommon.formatCifre(menu.getMenu().get("43").getPrice()));
    	if (menu.getMenu().get("43").getName().equals("xxx")) spinnerB13.setDisable(true);
    	
    	spinnerB14.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB14.setText(menu.getMenu().get("44") != null ? menu.getMenu().get("44").getName() : "xxx");
    	LabelB14t.setText(UtilsCommon.formatCifre(menu.getMenu().get("44").getPrice()));
    	if (menu.getMenu().get("44").getName().equals("xxx")) spinnerB14.setDisable(true);
    	
    	spinnerB15.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB15.setText(menu.getMenu().get("45") != null ? menu.getMenu().get("45").getName() : "xxx");
    	LabelB15t.setText(UtilsCommon.formatCifre(menu.getMenu().get("45").getPrice()));
    	if (menu.getMenu().get("45").getName().equals("xxx")) spinnerB15.setDisable(true);
    	
    	spinnerB16.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB16.setText(menu.getMenu().get("46") != null ? menu.getMenu().get("46").getName() : "xxx");
    	LabelB16t.setText(UtilsCommon.formatCifre(menu.getMenu().get("46").getPrice()));
    	if (menu.getMenu().get("46").getName().equals("xxx")) spinnerB16.setDisable(true);
    	
    	spinnerB17.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30));
    	LabelB17.setText(menu.getMenu().get("47") != null ? menu.getMenu().get("47").getName() : "xxx");
    	LabelB17t.setText(UtilsCommon.formatCifre(menu.getMenu().get("47").getPrice()));
    	if (menu.getMenu().get("47").getName().equals("xxx")) spinnerB17.setDisable(true);
    	
    	spinnerP0.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("1", newValue));
    	spinnerP1.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("2", newValue));
    	spinnerP2.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("3", newValue));
    	spinnerP3.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("4", newValue));
    	spinnerP4.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("5", newValue));
    	spinnerP5.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("6", newValue));
    	spinnerS0.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("10", newValue));
    	spinnerS1.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("11", newValue));
    	spinnerS2.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("12", newValue));
    	spinnerS3.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("13", newValue));
    	spinnerS4.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("14", newValue));
    	spinnerS5.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("15", newValue));
    	spinnerS6.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("16", newValue));
    	spinnerS7.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("17", newValue));
    	spinnerS8.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("18", newValue));
    	spinnerF0.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("20", newValue));
    	spinnerF1.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("21", newValue));
    	spinnerF2.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("22", newValue));
    	spinnerF3.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("23", newValue));
    	spinnerF4.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("24", newValue));
    	spinnerF5.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("25", newValue));
    	spinnerB0.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("30", newValue));
    	spinnerB1.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("31", newValue));
    	spinnerB2.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("32", newValue));
    	spinnerB3.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("33", newValue));
    	spinnerB4.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("34", newValue));
    	spinnerB5.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("35", newValue));
    	spinnerB6.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("36", newValue));
    	spinnerB7.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("37", newValue));
    	spinnerB8.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("38", newValue));
    	spinnerB9.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("39", newValue));
    	spinnerB10.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("40", newValue));
    	spinnerB11.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("41", newValue));
    	spinnerB12.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("42", newValue));
    	spinnerB13.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("43", newValue));
    	spinnerB14.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("44", newValue));
    	spinnerB15.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("45", newValue));
    	spinnerB16.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("46", newValue));
    	spinnerB17.valueProperty().addListener((observable,oldValue,newValue) -> aggiungiComanda("47", newValue));
    	
    	comandaTable.setItems(null);

    	Image image = new Image(getClass().getResource("/images/nggs.jpg").toString());
        imageView.setImage(image);
        
        comandaTable.setItems(obsList);
    	comandaColumn1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
    	comandaColumn2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getQuantity())));
    	
    	// force the field to be numeric only
    	contanteResto.textProperty().addListener((observable,oldValue,newValue) -> calcolaResto(newValue));
    	
    	addEventFilterToSpinner();
    	
    	LabelAlert.setVisible(false);
    	
    }
    
    
    private void aggiungiComanda(String id, Integer newValue){
    	Items it = menu.getMenu().get(id);
    	ComandaEntry com = new ComandaEntry(it, new SimpleIntegerProperty(newValue));
    	comanda.add(it.getId(), com);
    	total.setText("€ " + UtilsCommon.formatCifre(comanda.getTotale()));
    	System.out.println("Comanda: " + com.getName()+"|" + com.getPrice() + "|" + com.getQuantity());
    	obsList.setAll(comanda.getLista().values().stream().sorted((a1,a2) -> a1.getId() - a2.getId()).collect(Collectors.toList()));
    	calcolaContanteNecessario();
    	animateTotal();
    }
    
    public void Stampa(){
    	if (alreadyPrinted) {
    		showWarningAlert("Ricevute già stampate.\nE' necessario azzerare prima di continuare!");
    		return;
    	}
    	if (comanda.getLista().size() > 0) {
    		Stage progressStage = new Stage();
    		PrinterSetup ps = new PrinterSetup(menu, new ArrayList<ComandaEntry>(comanda.getLista().values()));
    		showProgressDialog(progressStage, ps);
    		new Thread(ps).start();
    		ps.setOnSucceeded(t -> {progressStage.close();});;
    		ps.setOnFailed(t -> {progressStage.close();});;

//    		Stampa senza thread
//    		ps.printerPrint(menu, new ArrayList<ComandaEntry>(comanda.getLista().values()));
    		
    		DBAdapter db = new DBAdapter();
        	if (db.saveRecords(new ArrayList<ComandaEntry>(comanda.getLista().values()))) LabelAlert.setVisible(false);
        	else LabelAlert.setVisible(true);
        	
        	alreadyPrinted = true;
    	} else {
    		showWarningAlert("E' necessario selezionare almeno un ordine...");
    	}
    }
    
    private void showProgressDialog(Stage dialogStage, PrinterSetup printer){
        dialogStage.initStyle(StageStyle.DECORATED);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.NONE);
        dialogStage.setTitle("Printing...");
        final Label progressLabel = new Label();
        progressLabel.textProperty().bind(printer.messageProperty());
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(250);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(printer.progressProperty());
//        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
//        ProgressIndicator progressIndicator = new ProgressIndicator();
//        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        final VBox vb = new VBox();
        vb.setSpacing(5);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(progressLabel, progressBar);
        Scene scene = new Scene(vb);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
    
    private void animateTotal(){
    	trans.setAutoReverse(true);
    	trans.setDuration(Duration.millis(150));
    	trans.setNode(total);
    	trans.setToX(1.2);
    	trans.setCycleCount(2);
    	trans.setToY(1.2);
    	trans.play();
    }
    
    // Show a Warning Alert without Header Text
    private void showWarningAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
 
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    
    //mostra la pagina del resoconto
    @FXML
    private void handleShowStatistics(){
    	mainApp.showStatistics();
    }
    
    //mostra la pagina del editor menu
    @FXML
    private void handleShowMenuEditor(){
    	mainApp.showMenuEditor();
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
    			if (passwordAdmin.equalsIgnoreCase(pass)) handleShowMenuEditor();
    		});

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
    
    @FXML
    public void handleAzzera(){
    	spinnerP0.getValueFactory().setValue(0);
    	spinnerP1.getValueFactory().setValue(0);
    	spinnerP2.getValueFactory().setValue(0);
    	spinnerP3.getValueFactory().setValue(0);
    	spinnerP4.getValueFactory().setValue(0);
    	spinnerP5.getValueFactory().setValue(0);
    	spinnerS0.getValueFactory().setValue(0);
    	spinnerS1.getValueFactory().setValue(0);
    	spinnerS2.getValueFactory().setValue(0);
    	spinnerS3.getValueFactory().setValue(0);
    	spinnerS4.getValueFactory().setValue(0);
    	spinnerS5.getValueFactory().setValue(0);
    	spinnerS6.getValueFactory().setValue(0);
    	spinnerS7.getValueFactory().setValue(0);
    	spinnerS8.getValueFactory().setValue(0);
    	spinnerF0.getValueFactory().setValue(0);
    	spinnerF1.getValueFactory().setValue(0);
    	spinnerF2.getValueFactory().setValue(0);
    	spinnerF3.getValueFactory().setValue(0);
    	spinnerF4.getValueFactory().setValue(0);
    	spinnerF5.getValueFactory().setValue(0);
    	spinnerB0.getValueFactory().setValue(0);
    	spinnerB1.getValueFactory().setValue(0);
    	spinnerB2.getValueFactory().setValue(0);
    	spinnerB3.getValueFactory().setValue(0);
    	spinnerB4.getValueFactory().setValue(0);
    	spinnerB5.getValueFactory().setValue(0);
    	spinnerB6.getValueFactory().setValue(0);
    	spinnerB7.getValueFactory().setValue(0);
    	spinnerB8.getValueFactory().setValue(0);
    	spinnerB9.getValueFactory().setValue(0);
    	spinnerB10.getValueFactory().setValue(0);
    	spinnerB11.getValueFactory().setValue(0);
    	spinnerB12.getValueFactory().setValue(0);
    	spinnerB13.getValueFactory().setValue(0);
    	spinnerB14.getValueFactory().setValue(0);
    	spinnerB15.getValueFactory().setValue(0);
    	spinnerB16.getValueFactory().setValue(0);
    	spinnerB17.getValueFactory().setValue(0);
    	
    	comanda = new Comanda();
    	obsList.clear();
    	alreadyPrinted = false;
    }
    
	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
    
    /*menu*/
    public void handleClose(){
    	Platform.exit();
    }
    
    public void handleAbout(){
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Creato per NGGS - Nuovo Gruppo Giovani Soave");
        
        alert.showAndWait();
    }
    
    private void calcolaResto(String newValue){
    	if (!newValue.matches("^[0-9]{1,4}([,\\.][0-9]{1,2})?$")) {
    		contanteResto.setText(newValue.replaceAll("[^\\d|^\\.|^,]", ""));
    	} else if (!contanteResto.textProperty().getValueSafe().equals("")){
    		Double contante = Double.valueOf(contanteResto.textProperty().getValue().replaceAll(",", ".")); 
    		if (contante >= comanda.getTotale()){
    			Double resto = contante - comanda.getTotale();
    			restoCalcolato.textProperty().setValue(UtilsCommon.formatCifre(resto));
    		} else restoCalcolato.textProperty().setValue("");
    	} else {
    		restoCalcolato.textProperty().setValue("");
    	}
    }
    
    private void calcolaContanteNecessario(){
    	contanteResto.clear();
    	contanteResto.setText(String.valueOf((int) Math.ceil(comanda.getTotale()/10) * 10));
    }
    
    private void addEventFilterToSpinner(){
    	spinnerP0.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerP1.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerP2.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerP3.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerP4.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerP5.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS0.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS1.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS2.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS3.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS4.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS5.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS6.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS7.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerS8.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerF0.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerF1.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerF2.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerF3.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerF4.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerF5.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB0.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB1.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB2.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB3.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB4.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB5.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB6.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB7.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB8.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB9.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB10.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB11.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB12.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB13.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB14.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB15.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB16.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    	spinnerB17.addEventFilter(MouseEvent.MOUSE_PRESSED, ae -> checkEventAlreadyPrinted(ae));
    }
    
    private void checkEventAlreadyPrinted(MouseEvent me){
    	if (alreadyPrinted){
    		showWarningAlert("Ricevute già stampate.\nE' necessario azzerare prima di continuare!"); 
    		me.consume();
    	}
    }

}
