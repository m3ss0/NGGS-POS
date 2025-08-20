package com.messo.pos.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

    // The button to open the QR Code scanner dialog
    @FXML
    private javafx.scene.control.Button focusQRInputButton;

	@FXML private Button buttonMinusP0 = new Button();
	@FXML private Button buttonMinusP1 = new Button();
	@FXML private Button buttonMinusP2 = new Button();
	@FXML private Button buttonMinusP3 = new Button();
	@FXML private Button buttonMinusP4 = new Button();
	@FXML private Button buttonMinusP5 = new Button();

	@FXML private Button buttonMinusS0 = new Button();
	@FXML private Button buttonMinusS1 = new Button();
	@FXML private Button buttonMinusS2 = new Button();
	@FXML private Button buttonMinusS3 = new Button();
	@FXML private Button buttonMinusS4 = new Button();
	@FXML private Button buttonMinusS5 = new Button();
	@FXML private Button buttonMinusS6 = new Button();
	@FXML private Button buttonMinusS7 = new Button();
	@FXML private Button buttonMinusS8 = new Button();

	@FXML private Button buttonMinusF0 = new Button();
	@FXML private Button buttonMinusF1 = new Button();
	@FXML private Button buttonMinusF2 = new Button();
	@FXML private Button buttonMinusF3 = new Button();
	@FXML private Button buttonMinusF4 = new Button();
	@FXML private Button buttonMinusF5 = new Button();

	@FXML private Button buttonMinusB0 = new Button();
	@FXML private Button buttonMinusB1 = new Button();
	@FXML private Button buttonMinusB2 = new Button();
	@FXML private Button buttonMinusB3 = new Button();
	@FXML private Button buttonMinusB4 = new Button();
	@FXML private Button buttonMinusB5 = new Button();
	@FXML private Button buttonMinusB6 = new Button();
	@FXML private Button buttonMinusB7 = new Button();
	@FXML private Button buttonMinusB8 = new Button();
	@FXML private Button buttonMinusB9 = new Button();
	@FXML private Button buttonMinusB10 = new Button();
	@FXML private Button buttonMinusB11 = new Button();
	@FXML private Button buttonMinusB12 = new Button();
	@FXML private Button buttonMinusB13 = new Button();
	@FXML private Button buttonMinusB14 = new Button();
	@FXML private Button buttonMinusB15 = new Button();
	@FXML private Button buttonMinusB16 = new Button();
	@FXML private Button buttonMinusB17 = new Button();

    @FXML private Pane buttonPlusP0 = new Pane();
    @FXML private Pane buttonPlusP1 = new Pane();
    @FXML private Pane buttonPlusP2 = new Pane();
    @FXML private Pane buttonPlusP3 = new Pane();
    @FXML private Pane buttonPlusP4 = new Pane();
    @FXML private Pane buttonPlusP5 = new Pane();

    @FXML private Pane buttonPlusS0 = new Pane();
    @FXML private Pane buttonPlusS1 = new Pane();
    @FXML private Pane buttonPlusS2 = new Pane();
    @FXML private Pane buttonPlusS3 = new Pane();
    @FXML private Pane buttonPlusS4 = new Pane();
    @FXML private Pane buttonPlusS5 = new Pane();
    @FXML private Pane buttonPlusS6 = new Pane();
    @FXML private Pane buttonPlusS7 = new Pane();
    @FXML private Pane buttonPlusS8 = new Pane();

    @FXML private Pane buttonPlusF0 = new Pane();
    @FXML private Pane buttonPlusF1 = new Pane();
    @FXML private Pane buttonPlusF2 = new Pane();
    @FXML private Pane buttonPlusF3 = new Pane();
    @FXML private Pane buttonPlusF4 = new Pane();
    @FXML private Pane buttonPlusF5 = new Pane();

    @FXML private Pane buttonPlusB0 = new Pane();
    @FXML private Pane buttonPlusB1 = new Pane();
    @FXML private Pane buttonPlusB2 = new Pane();
    @FXML private Pane buttonPlusB3 = new Pane();
    @FXML private Pane buttonPlusB4 = new Pane();
    @FXML private Pane buttonPlusB5 = new Pane();
    @FXML private Pane buttonPlusB6 = new Pane();
    @FXML private Pane buttonPlusB7 = new Pane();
    @FXML private Pane buttonPlusB8 = new Pane();
    @FXML private Pane buttonPlusB9 = new Pane();
    @FXML private Pane buttonPlusB10 = new Pane();
    @FXML private Pane buttonPlusB11 = new Pane();
    @FXML private Pane buttonPlusB12 = new Pane();
    @FXML private Pane buttonPlusB13 = new Pane();
    @FXML private Pane buttonPlusB14 = new Pane();
    @FXML private Pane buttonPlusB15 = new Pane();
    @FXML private Pane buttonPlusB16 = new Pane();
    @FXML private Pane buttonPlusB17 = new Pane();


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        try {
            Font.loadFont(getClass().getResourceAsStream("/images/fa-solid-900.ttf"), 10);
            System.out.println("FontAwesome loaded correctly");
        } catch (Exception e) {
            System.err.println("FontAwesome errors");
        }

        spinnerP0.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelP0.setText(menu.getMenu().get("1").getName());
        LabelP0t.setText(UtilsCommon.formatCifre(menu.getMenu().get("1").getPrice()));
        if (menu.getMenu().get("1").getName().equals("xxx")) spinnerP0.setDisable(true);

		if(!LabelP0.getText().equals("xxx")) {
			buttonPlusP0.setOnMouseClicked(event -> addRemoveComanda("1", 1));
			buttonMinusP0.setOnMouseClicked(event -> addRemoveComanda("1", -1));
		}

        spinnerP1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelP1.setText(menu.getMenu().get("2").getName());
        LabelP1t.setText(UtilsCommon.formatCifre(menu.getMenu().get("2").getPrice()));
        if (menu.getMenu().get("2").getName().equals("xxx")) spinnerP1.setDisable(true);

		if(!LabelP1.getText().equals("xxx")) {
			buttonPlusP1.setOnMouseClicked(event -> addRemoveComanda("2", 1));
			buttonMinusP1.setOnMouseClicked(event -> addRemoveComanda("2", -1));
		}

        spinnerP2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelP2.setText(menu.getMenu().get("3").getName());
        LabelP2t.setText(UtilsCommon.formatCifre(menu.getMenu().get("3").getPrice()));
        if (menu.getMenu().get("3").getName().equals("xxx")) spinnerP2.setDisable(true);

		if(!LabelP2.getText().equals("xxx")) {
			buttonPlusP2.setOnMouseClicked(event -> addRemoveComanda("3", 1));
			buttonMinusP2.setOnMouseClicked(event -> addRemoveComanda("3", -1));
		}

        spinnerP3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelP3.setText(menu.getMenu().get("4").getName());
        LabelP3t.setText(UtilsCommon.formatCifre(menu.getMenu().get("4").getPrice()));
        if (menu.getMenu().get("4").getName().equals("xxx")) spinnerP3.setDisable(true);

		if(!LabelP3.getText().equals("xxx")) {
			buttonPlusP3.setOnMouseClicked(event -> addRemoveComanda("4", 1));
			buttonMinusP3.setOnMouseClicked(event -> addRemoveComanda("4", -1));
		}

        spinnerP4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelP4.setText(menu.getMenu().get("5").getName());
        LabelP4t.setText(UtilsCommon.formatCifre(menu.getMenu().get("5").getPrice()));
        if (menu.getMenu().get("5").getName().equals("xxx")) spinnerP4.setDisable(true);

		if(!LabelP4.getText().equals("xxx")) {
			buttonPlusP4.setOnMouseClicked(event -> addRemoveComanda("5", 1));
			buttonMinusP4.setOnMouseClicked(event -> addRemoveComanda("5", -1));
		}

        spinnerP5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelP5.setText(menu.getMenu().get("6").getName());
        LabelP5t.setText(UtilsCommon.formatCifre(menu.getMenu().get("6").getPrice()));
        if (menu.getMenu().get("6").getName().equals("xxx")) spinnerP5.setDisable(true);

		if(!LabelP5.getText().equals("xxx")) {
			buttonPlusP5.setOnMouseClicked(event -> addRemoveComanda("6", 1));
			buttonMinusP5.setOnMouseClicked(event -> addRemoveComanda("6", -1));
		}

        spinnerS0.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS0.setText(menu.getMenu().get("10").getName());
        LabelS0t.setText(UtilsCommon.formatCifre(menu.getMenu().get("10").getPrice()));
        if (menu.getMenu().get("10").getName().equals("xxx")) spinnerS0.setDisable(true);

		if(!LabelS0.getText().equals("xxx")) {
			buttonPlusS0.setOnMouseClicked(event -> addRemoveComanda("10", 1));
			buttonMinusS0.setOnMouseClicked(event -> addRemoveComanda("10", -1));
		}

        spinnerS1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS1.setText(menu.getMenu().get("11").getName());
        LabelS1t.setText(UtilsCommon.formatCifre(menu.getMenu().get("11").getPrice()));
        if (menu.getMenu().get("11").getName().equals("xxx")) spinnerS1.setDisable(true);

		if(!LabelS1.getText().equals("xxx")) {
			buttonPlusS1.setOnMouseClicked(event -> addRemoveComanda("11", 1));
			buttonMinusS1.setOnMouseClicked(event -> addRemoveComanda("11", -1));
		}

        spinnerS2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS2.setText(menu.getMenu().get("12").getName());
        LabelS2t.setText(UtilsCommon.formatCifre(menu.getMenu().get("12").getPrice()));
        if (menu.getMenu().get("12").getName().equals("xxx")) spinnerS2.setDisable(true);

		if(!LabelS2.getText().equals("xxx")) {
			buttonPlusS2.setOnMouseClicked(event -> addRemoveComanda("12", 1));
			buttonMinusS2.setOnMouseClicked(event -> addRemoveComanda("12", -1));
		}

        spinnerS3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS3.setText(menu.getMenu().get("13").getName());
        LabelS3t.setText(UtilsCommon.formatCifre(menu.getMenu().get("13").getPrice()));
        if (menu.getMenu().get("13").getName().equals("xxx")) spinnerS3.setDisable(true);

		if(!LabelS3.getText().equals("xxx")) {
			buttonPlusS3.setOnMouseClicked(event -> addRemoveComanda("13", 1));
			buttonMinusS3.setOnMouseClicked(event -> addRemoveComanda("13", -1));
		}

        spinnerS4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS4.setText(menu.getMenu().get("14").getName());
        LabelS4t.setText(UtilsCommon.formatCifre(menu.getMenu().get("14").getPrice()));    	
        if (menu.getMenu().get("14").getName().equals("xxx")) spinnerS4.setDisable(true);

		if(!LabelS4.getText().equals("xxx")) {
			buttonPlusS4.setOnMouseClicked(event -> addRemoveComanda("14", 1));
			buttonMinusS4.setOnMouseClicked(event -> addRemoveComanda("14", -1));
		}

        spinnerS5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS5.setText(menu.getMenu().get("15").getName());
        LabelS5t.setText(UtilsCommon.formatCifre(menu.getMenu().get("15").getPrice()));
        if (menu.getMenu().get("15").getName().equals("xxx")) spinnerS5.setDisable(true);

		if(!LabelS5.getText().equals("xxx")) {
			buttonPlusS5.setOnMouseClicked(event -> addRemoveComanda("15", 1));
			buttonMinusS5.setOnMouseClicked(event -> addRemoveComanda("15", -1));
		}

        spinnerS6.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS6.setText(menu.getMenu().get("16").getName());
        LabelS6t.setText(UtilsCommon.formatCifre(menu.getMenu().get("16").getPrice()));
        if (menu.getMenu().get("16").getName().equals("xxx")) spinnerS6.setDisable(true);

		if(!LabelS6.getText().equals("xxx")) {
			buttonPlusS6.setOnMouseClicked(event -> addRemoveComanda("16", 1));
			buttonMinusS6.setOnMouseClicked(event -> addRemoveComanda("16", -1));
		}

        spinnerS7.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS7.setText(menu.getMenu().get("17").getName());
        LabelS7t.setText(UtilsCommon.formatCifre(menu.getMenu().get("17").getPrice()));
        if (menu.getMenu().get("17").getName().equals("xxx")) spinnerS7.setDisable(true);

		if(!LabelS7.getText().equals("xxx")) {
			buttonPlusS7.setOnMouseClicked(event -> addRemoveComanda("17", 1));
			buttonMinusS7.setOnMouseClicked(event -> addRemoveComanda("17", -1));
		}

        spinnerS8.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelS8.setText(menu.getMenu().get("18").getName());
        LabelS8t.setText(UtilsCommon.formatCifre(menu.getMenu().get("18").getPrice()));
        if (menu.getMenu().get("18").getName().equals("xxx")) spinnerS8.setDisable(true);

		if(!LabelS8.getText().equals("xxx")) {
			buttonPlusS8.setOnMouseClicked(event -> addRemoveComanda("18", 1));
			buttonMinusS8.setOnMouseClicked(event -> addRemoveComanda("18", -1));
		}

        spinnerF0.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelF0.setText(menu.getMenu().get("20").getName());
        LabelF0t.setText(UtilsCommon.formatCifre(menu.getMenu().get("20").getPrice()));
        if (menu.getMenu().get("20").getName().equals("xxx")) spinnerF0.setDisable(true);

		if(!LabelF0.getText().equals("xxx")) {
			buttonPlusF0.setOnMouseClicked(event -> addRemoveComanda("20", 1));
			buttonMinusF0.setOnMouseClicked(event -> addRemoveComanda("20", -1));
		}

        spinnerF1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelF1.setText(menu.getMenu().get("21").getName());
        LabelF1t.setText(UtilsCommon.formatCifre(menu.getMenu().get("21").getPrice()));
        if (menu.getMenu().get("21").getName().equals("xxx")) spinnerF1.setDisable(true);

		if(!LabelF1.getText().equals("xxx")) {
			buttonPlusF1.setOnMouseClicked(event -> addRemoveComanda("21", 1));
			buttonMinusF1.setOnMouseClicked(event -> addRemoveComanda("21", -1));
		}

        spinnerF2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelF2.setText(menu.getMenu().get("22").getName());
        LabelF2t.setText(UtilsCommon.formatCifre(menu.getMenu().get("22").getPrice()));
        if (menu.getMenu().get("22").getName().equals("xxx")) spinnerF2.setDisable(true);

		if(!LabelF2.getText().equals("xxx")) {
			buttonPlusF2.setOnMouseClicked(event -> addRemoveComanda("22", 1));
			buttonMinusF2.setOnMouseClicked(event -> addRemoveComanda("22", -1));
		}

        spinnerF3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelF3.setText(menu.getMenu().get("23").getName());
        LabelF3t.setText(UtilsCommon.formatCifre(menu.getMenu().get("23").getPrice()));
        if (menu.getMenu().get("23").getName().equals("xxx")) spinnerF3.setDisable(true);

		if(!LabelF3.getText().equals("xxx")) {
			buttonPlusF3.setOnMouseClicked(event -> addRemoveComanda("23", 1));
			buttonMinusF3.setOnMouseClicked(event -> addRemoveComanda("23", -1));
		}

        spinnerF4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelF4.setText(menu.getMenu().get("24").getName());
        LabelF4t.setText(UtilsCommon.formatCifre(menu.getMenu().get("24").getPrice()));    	
        if (menu.getMenu().get("24").getName().equals("xxx")) spinnerF4.setDisable(true);

		if(!LabelF4.getText().equals("xxx")) {
			buttonPlusF4.setOnMouseClicked(event -> addRemoveComanda("24", 1));
			buttonMinusF4.setOnMouseClicked(event -> addRemoveComanda("24", -1));
		}

        spinnerF5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelF5.setText(menu.getMenu().get("25").getName());
        LabelF5t.setText(UtilsCommon.formatCifre(menu.getMenu().get("25").getPrice()));    	
        if (menu.getMenu().get("25").getName().equals("xxx")) spinnerF5.setDisable(true);

		if(!LabelF5.getText().equals("xxx")) {
			buttonPlusF5.setOnMouseClicked(event -> addRemoveComanda("25", 1));
			buttonMinusF5.setOnMouseClicked(event -> addRemoveComanda("25", -1));
		}

        spinnerB0.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB0.setText(menu.getMenu().get("30").getName());
        LabelB0t.setText(UtilsCommon.formatCifre(menu.getMenu().get("30").getPrice()));
        if (menu.getMenu().get("30").getName().equals("xxx")) spinnerB0.setDisable(true);

		if(!LabelB0.getText().equals("xxx")) {
			buttonPlusB0.setOnMouseClicked(event -> addRemoveComanda("30", 1));
			buttonMinusB0.setOnMouseClicked(event -> addRemoveComanda("30", -1));
		}

        spinnerB1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB1.setText(menu.getMenu().get("31").getName());
        LabelB1t.setText(UtilsCommon.formatCifre(menu.getMenu().get("31").getPrice()));
        if (menu.getMenu().get("31").getName().equals("xxx")) spinnerB1.setDisable(true);

		if(!LabelB1.getText().equals("xxx")) {
			buttonPlusB1.setOnMouseClicked(event -> addRemoveComanda("31", 1));
			buttonMinusB1.setOnMouseClicked(event -> addRemoveComanda("31", -1));
		}

        spinnerB2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB2.setText(menu.getMenu().get("32").getName());
        LabelB2t.setText(UtilsCommon.formatCifre(menu.getMenu().get("32").getPrice()));
        if (menu.getMenu().get("32").getName().equals("xxx")) spinnerB2.setDisable(true);

		if(!LabelB2.getText().equals("xxx")) {
			buttonPlusB2.setOnMouseClicked(event -> addRemoveComanda("32", 1));
			buttonMinusB2.setOnMouseClicked(event -> addRemoveComanda("32", -1));
		}		

        spinnerB3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB3.setText(menu.getMenu().get("33").getName());
        LabelB3t.setText(UtilsCommon.formatCifre(menu.getMenu().get("33").getPrice()));
        if (menu.getMenu().get("33").getName().equals("xxx")) spinnerB3.setDisable(true);

		if(!LabelB3.getText().equals("xxx")) {
			buttonPlusB3.setOnMouseClicked(event -> addRemoveComanda("33", 1));
			buttonMinusB3.setOnMouseClicked(event -> addRemoveComanda("33", -1));
		}

        spinnerB4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB4.setText(menu.getMenu().get("34").getName());
        LabelB4t.setText(UtilsCommon.formatCifre(menu.getMenu().get("34").getPrice()));
        if (menu.getMenu().get("34").getName().equals("xxx")) spinnerB4.setDisable(true);

		if(!LabelB4.getText().equals("xxx")) {
			buttonPlusB4.setOnMouseClicked(event -> addRemoveComanda("34", 1));
			buttonMinusB4.setOnMouseClicked(event -> addRemoveComanda("34", -1));
		}

        spinnerB5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB5.setText(menu.getMenu().get("35").getName());
        LabelB5t.setText(UtilsCommon.formatCifre(menu.getMenu().get("35").getPrice()));
        if (menu.getMenu().get("35").getName().equals("xxx")) spinnerB5.setDisable(true);

		if(!LabelB5.getText().equals("xxx")) {
			buttonPlusB5.setOnMouseClicked(event -> addRemoveComanda("35", 1));
			buttonMinusB5.setOnMouseClicked(event -> addRemoveComanda("35", -1));
		}

        spinnerB6.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB6.setText(menu.getMenu().get("36").getName());
        LabelB6t.setText(UtilsCommon.formatCifre(menu.getMenu().get("36").getPrice()));
        if (menu.getMenu().get("36").getName().equals("xxx")) spinnerB6.setDisable(true);

		if(!LabelB6.getText().equals("xxx")) {
			buttonPlusB6.setOnMouseClicked(event -> addRemoveComanda("36", 1));
			buttonMinusB6.setOnMouseClicked(event -> addRemoveComanda("36", -1));
		}

        spinnerB7.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB7.setText(menu.getMenu().get("37").getName());
        LabelB7t.setText(UtilsCommon.formatCifre(menu.getMenu().get("37").getPrice()));
        if (menu.getMenu().get("37").getName().equals("xxx")) spinnerB7.setDisable(true);

		if(!LabelB7.getText().equals("xxx")) {
			buttonPlusB7.setOnMouseClicked(event -> addRemoveComanda("37", 1));
			buttonMinusB7.setOnMouseClicked(event -> addRemoveComanda("37", -1));
		}

        spinnerB8.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB8.setText(menu.getMenu().get("38").getName());
        LabelB8t.setText(UtilsCommon.formatCifre(menu.getMenu().get("38").getPrice()));
        if (menu.getMenu().get("38").getName().equals("xxx")) spinnerB8.setDisable(true);

		if(!LabelB8.getText().equals("xxx")) {
			buttonPlusB8.setOnMouseClicked(event -> addRemoveComanda("38", 1));
			buttonMinusB8.setOnMouseClicked(event -> addRemoveComanda("38", -1));
		}

        spinnerB9.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB9.setText(menu.getMenu().get("39").getName());
        LabelB9t.setText(UtilsCommon.formatCifre(menu.getMenu().get("39").getPrice()));
        if (menu.getMenu().get("39").getName().equals("xxx")) spinnerB9.setDisable(true);

		if(!LabelB9.getText().equals("xxx")) {
			buttonPlusB9.setOnMouseClicked(event -> addRemoveComanda("39", 1));
			buttonMinusB9.setOnMouseClicked(event -> addRemoveComanda("39", -1));
		}

        spinnerB10.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB10.setText(menu.getMenu().get("40").getName());
        LabelB10t.setText(UtilsCommon.formatCifre(menu.getMenu().get("40").getPrice()));
        if (menu.getMenu().get("40").getName().equals("xxx")) spinnerB10.setDisable(true);

		if(!LabelB10.getText().equals("xxx")) {
			buttonPlusB10.setOnMouseClicked(event -> addRemoveComanda("40", 1));
			buttonMinusB10.setOnMouseClicked(event -> addRemoveComanda("40", -1));
		}

        spinnerB11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB11.setText(menu.getMenu().get("41").getName());
        LabelB11t.setText(UtilsCommon.formatCifre(menu.getMenu().get("41").getPrice()));
        if (menu.getMenu().get("41").getName().equals("xxx")) spinnerB11.setDisable(true);

		if(!LabelB11.getText().equals("xxx")) {
			buttonPlusB11.setOnMouseClicked(event -> addRemoveComanda("41", 1));
			buttonMinusB11.setOnMouseClicked(event -> addRemoveComanda("41", -1));
		}

        spinnerB12.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB12.setText(menu.getMenu().get("42").getName());
        LabelB12t.setText(UtilsCommon.formatCifre(menu.getMenu().get("42").getPrice()));
        if (menu.getMenu().get("42").getName().equals("xxx")) spinnerB12.setDisable(true);

		if(!LabelB12.getText().equals("xxx")) {
			buttonPlusB12.setOnMouseClicked(event -> addRemoveComanda("42", 1));
			buttonMinusB12.setOnMouseClicked(event -> addRemoveComanda("42", -1));
		}

        spinnerB13.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB13.setText(menu.getMenu().get("43").getName());
        LabelB13t.setText(UtilsCommon.formatCifre(menu.getMenu().get("43").getPrice()));
        if (menu.getMenu().get("43").getName().equals("xxx")) spinnerB13.setDisable(true);

		if(!LabelB13.getText().equals("xxx")) {
			buttonPlusB13.setOnMouseClicked(event -> addRemoveComanda("43", 1));
			buttonMinusB13.setOnMouseClicked(event -> addRemoveComanda("43", -1));
		}

        spinnerB14.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB14.setText(menu.getMenu().get("44") != null ? menu.getMenu().get("44").getName() : "xxx");
        LabelB14t.setText(UtilsCommon.formatCifre(menu.getMenu().get("44").getPrice()));
        if (menu.getMenu().get("44").getName().equals("xxx")) spinnerB14.setDisable(true);

		if(!LabelB14.getText().equals("xxx")) {
			buttonPlusB14.setOnMouseClicked(event -> addRemoveComanda("44", 1));
			buttonMinusB14.setOnMouseClicked(event -> addRemoveComanda("44", -1));
		}

        spinnerB15.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB15.setText(menu.getMenu().get("45") != null ? menu.getMenu().get("45").getName() : "xxx");
        LabelB15t.setText(UtilsCommon.formatCifre(menu.getMenu().get("45").getPrice()));
        if (menu.getMenu().get("45").getName().equals("xxx")) spinnerB15.setDisable(true);

		if(!LabelB15.getText().equals("xxx")) {
			buttonPlusB15.setOnMouseClicked(event -> addRemoveComanda("45", 1));
			buttonMinusB15.setOnMouseClicked(event -> addRemoveComanda("45", -1));
		}

        spinnerB16.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB16.setText(menu.getMenu().get("46") != null ? menu.getMenu().get("46").getName() : "xxx");
        LabelB16t.setText(UtilsCommon.formatCifre(menu.getMenu().get("46").getPrice()));
        if (menu.getMenu().get("46").getName().equals("xxx")) spinnerB16.setDisable(true);

		if(!LabelB16.getText().equals("xxx")) {
			buttonPlusB16.setOnMouseClicked(event -> addRemoveComanda("46", 1));
			buttonMinusB16.setOnMouseClicked(event -> addRemoveComanda("46", -1));
		}

        spinnerB17.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        LabelB17.setText(menu.getMenu().get("47") != null ? menu.getMenu().get("47").getName() : "xxx");
        LabelB17t.setText(UtilsCommon.formatCifre(menu.getMenu().get("47").getPrice()));
        if (menu.getMenu().get("47").getName().equals("xxx")) spinnerB17.setDisable(true);

		if(!LabelB17.getText().equals("xxx")) {
			buttonPlusB17.setOnMouseClicked(event -> addRemoveComanda("47", 1));
			buttonMinusB17.setOnMouseClicked(event -> addRemoveComanda("47", -1));
		}

        
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

    public void addRemoveComanda(String id, int qtyToAdd) {
        ObservableMap<Integer, ComandaEntry> lista = comanda.getLista();

        Items item = menu.getMenu().get(id);

        if (lista.containsKey(item.getId())) {
            // Item already exists, update its quantity
            ComandaEntry existingEntry = lista.get(item.getId());
            int currentQty = existingEntry.getQuantity();
            existingEntry.setQuantity(currentQty + qtyToAdd);
            int newQty = currentQty + qtyToAdd;

            if (newQty > 0) {
                comanda.add(item.getId(), existingEntry);
            } else {
                comanda.add(item.getId(), existingEntry);
            }
            
        } else {
            // Item does not exist, create a new entry
            if (qtyToAdd > 0) {
                ComandaEntry newEntry = new ComandaEntry(item, new SimpleIntegerProperty(qtyToAdd));
                comanda.add(item.getId(),newEntry);
            }
        }

        total.setText("€ " + UtilsCommon.formatCifre(comanda.getTotale()));
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

        // Azzera automaticamente dopo la stampa
        if (UtilsCommon.getPropertyValue("system.autoAzzera").equals("1")) {
            handleAzzera();
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

        azzeraComanda();

    }

    public void azzeraComanda(){
        comanda = new Comanda();
        obsList.clear();
        alreadyPrinted = false;

        total.setText("€ " + UtilsCommon.formatCifre(0.00));
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

    /**
     * Handles the action to open the QR Code scanner dialog.
     * When the dialog closes, it retrieves and processes the scanned data.
     */
    @FXML
    private void handleScanQRCode() {
        System.out.println("handleScanQRCode called.");
        Stage ownerStage = (Stage) focusQRInputButton.getScene().getWindow();
        try {
            QRCodeScannerDialog dialog = new QRCodeScannerDialog(ownerStage);
            System.out.println("QRCodeScannerDialog instantiated.");
            
            // Show the dialog and wait for it to be closed
            dialog.showAndWait();
            System.out.println("Dialog closed.");

            // After the dialog closes, retrieve the scanned data
            String scannedData = dialog.getScannedData();
            if (scannedData != null && !scannedData.trim().isEmpty()) {
                System.out.println("Scanned Data: " + scannedData);
                // Process the scanned QR code data here
                //showInformationAlert("QR Code Scanned", "Scanned Data: " + scannedData);

                //Azzera il precedente ordine
                handleAzzera();

                //read input and add items to comanda
                parseItemsQRCode(scannedData);

                // Example: You could now use 'scannedData' to look up an item,
                // add it to the cart, etc.
            } else {
                System.out.println("No QR code was scanned or the dialog was cancelled.");
                showInformationAlert("QR Code Scan Cancelled", "No QR code was scanned or the dialog was closed.");
            }
        } catch (Exception e) {
            System.err.println("Error opening QR Code scanner dialog: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Error", "Could not open QR Code scanner. Check console for details.");
        }
    }

    /**
     * Shows an information alert dialog.
     * @param title The title of the alert.
     * @param message The message content of the alert.
     */
    private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog.
     * @param title The title of the alert.
     * @param message The message content of the alert.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void parseItemsQRCode(String input) {
        Map<Integer, Integer> items = new HashMap<>();
        
        // Split by semicolon
        String[] parts = input.split(";");
        
        for (String part : parts) {
            part = part.trim();
            
            // Only process parts starting with "I-"
            if (part.startsWith("I-")) {
                String[] idAndQty = part.split("=");
                if (idAndQty.length == 2) {
                    try {
                        int id = Integer.parseInt(idAndQty[0].substring(2)); // after "I-"
                        int qty = Integer.parseInt(idAndQty[1]);
                        //items.put(id, qty);
                        aggiungiComanda(String.valueOf(id),qty);
                    } catch (Exception e) {
                        System.err.println("Error with QR Code scanning: " + e.getMessage());
                        showErrorAlert("Error", "Could not read from QR Code.");
                    }
                }
            }
        }
    }
}
