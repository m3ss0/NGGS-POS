package com.messo.pos.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QRCodeScannerDialog extends Stage {

    private String scannedData;
    private TextField inputField;

    public QRCodeScannerDialog(Stage owner) {
        initOwner(owner); // Set the owner stage
        initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
        initStyle(StageStyle.UTILITY); // A simple, undecorated window
        setTitle("Scan QR Code");

        Label instructionLabel = new Label("Please scan the QR code now:");
        instructionLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        inputField = new TextField();
        inputField.setPromptText("Scanning...");
        inputField.setEditable(true); // Must be editable to receive keyboard input
        inputField.setFocusTraversable(false); // Not reachable by tab key, only direct focus or scanner input
        inputField.setStyle("-fx-font-size: 10px; -fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 8px;");

        // Set action for when the scanner finishes typing (usually presses Enter)
        inputField.setOnAction(event -> {
            scannedData = inputField.getText();
            close(); // Close the dialog once data is scanned
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(instructionLabel, inputField);
        layout.setStyle("-fx-background-color: white; -fx-border-color: #007bff; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;");


        Scene scene = new Scene(layout, 300, 150);
        setScene(scene); // Ensure the scene is set before accessing it

        // Now that the scene is set, we can attach listeners that depend on it
        // Handle closing via ESC key
        scene.setOnKeyPressed(event -> { // Changed from this.getScene() to scene
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                scannedData = null; // Clear scanned data if dialog is cancelled
                close();
            }
        });

        // Set focus to the input field as soon as the dialog is shown
        // This is crucial for the scanner to immediately input data
        this.setOnShown(event -> {
            Platform.runLater(() -> inputField.requestFocus());
        });
    }

    // Method to retrieve the scanned data
    public String getScannedData() {
        return scannedData;
    }
}
