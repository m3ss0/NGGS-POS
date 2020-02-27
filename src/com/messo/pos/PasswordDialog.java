package com.messo.pos;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PasswordDialog extends Dialog<String> {
	private PasswordField passwordF = new PasswordField();

	public PasswordField getPasswordField() {
		return passwordF;
	}

	public PasswordDialog(String passwordCorrect) {
		setTitle("Password");
		setHeaderText("Please enter Administrator password.");

		Label labelPwd = new Label("Password:  ");
		Label errorNotification = new Label();

		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 20, 20, 20));
		gridPane.add(labelPwd, 0, 0);
		gridPane.add(passwordF, 1, 0);
		gridPane.add(errorNotification, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node okButton = getDialogPane().lookupButton(ButtonType.OK);
		okButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		passwordF.textProperty().addListener((observable, oldValue, newValue) -> {
			okButton.setDisable(newValue.trim().isEmpty());
		});

		//Clear the error notification
		passwordF.setOnKeyPressed(ke -> {
			if (ke.getCode().equals(KeyCode.ENTER)) {
				errorNotification.setText("Your password is incorrect!");
				errorNotification.setTextFill(Color.RED);
				passwordF.clear();
			} else errorNotification.setText("");
		});
		
		getDialogPane().setContent(gridPane);

		Platform.runLater(() -> passwordF.requestFocus());
		passwordF.setPromptText("Insert password...");

		setResultConverter(pressedButton -> {
			if (pressedButton == ButtonType.OK) {
				return passwordF.getText();
			}
			return null;
		});

		okButton.addEventFilter(ActionEvent.ACTION, ae -> {
			if (!passwordF.getText().equals(passwordCorrect)) {
				errorNotification.setText("Your password is incorrect!");
				errorNotification.setTextFill(Color.RED);
				passwordF.clear();
				ae.consume(); //Escape the action - no further action
			}
		});
		
	}

}