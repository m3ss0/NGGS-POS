package com.messo.pos;
	
import java.io.IOException;

import com.messo.pos.db.DBAdapter;
import com.messo.pos.view.MainPOSController;
import com.messo.pos.view.MenuEditorController;
import com.messo.pos.view.StatisticsController;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class MainApp extends Application {
	
	private Stage primaryStage;
	private Stage editorStage;

	private static Rectangle2D screenSize = Screen.getPrimary().getBounds();
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Controllo/creazione tabelle
			DBAdapter db = new DBAdapter();
            db.initialSetupTableOrders();
            db.initialSetupTableMenu();
			
			this.primaryStage = primaryStage;
			AnchorPane root = new AnchorPane();
			FXMLLoader loader = new FXMLLoader();
			if (screenSize.getWidth() > 1250)
            	loader.setLocation(MainApp.class.getResource("view/MainPOSEnlarged.fxml"));
			else
				loader.setLocation(MainApp.class.getResource("view/MainPOS.fxml"));
            root = (AnchorPane) loader.load();

			System.out.println("Width: " + screenSize.getWidth() + " Height: " + screenSize.getHeight());
            
            this.primaryStage.getIcons().add(new Image(getClass().getResource("/images/cashier_icon.png").toString()));
            
            //Ricarica la pagina principale se si modifica il menu
            editorStage = new Stage();
            editorStage.setOnHidden(e -> start(primaryStage));
            
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("NGGS POS");
			primaryStage.show();

			MainPOSController controller = loader.getController();
            controller.setMainApp(this);
			
			SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, scene.getHeight(), scene.getWidth());
    		scene.widthProperty().addListener(sizeListener);
    		scene.heightProperty().addListener(sizeListener);
            
            if (UtilsCommon.logFileOn) SimpleLogger.getLogger.log("INFO", "Start Application" );
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop(){
		if (UtilsCommon.logFileOn) SimpleLogger.getLogger.log("INFO", "Stop Application" );
	}
	
	/**
	 * Opens a dialog to show statistics.
	 */
	public void showStatistics() {
	    try {
	        // Load the fxml file and create a new stage for the popup.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/Statistics.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Resoconto");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        dialogStage.setScene(scene);

	        StatisticsController controller = loader.getController();

	        dialogStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * Opens a dialog to edit Menu
	 */
	public void showMenuEditor() {
	    try {
	        // Load the fxml file and create a new stage for the popup.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/MenuEditor.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        editorStage.setTitle("Menu Editor");
	        editorStage.initModality(Modality.WINDOW_MODAL);
	        editorStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        editorStage.setScene(scene);

	        MenuEditorController controller = loader.getController();
	        
	        editorStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String args[]) {
		launch(args);
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	private static class SceneSizeChangeListener implements ChangeListener<Number> {
		private final Scene scene;
		private final double initHeight;
		private final double initWidth;
	
		public SceneSizeChangeListener(Scene scene, double initHeight, double initWidth) {
		  this.scene = scene;
		  this.initHeight = initHeight;
		  this.initWidth = initWidth;
		}
	
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
		  	final double newWidth  = scene.getWidth();
		  	final double newHeight = scene.getHeight();
	
			Scale scale = new Scale(newWidth/initWidth, newHeight/initHeight);
			scale.setPivotX(0);
			scale.setPivotY(0);
			scene.getRoot().getTransforms().setAll(scale);

			Pane p = (Pane) scene.getRoot();
			p.setPrefWidth (newWidth);
			p.setPrefHeight(newHeight);
		}
	}
	
}
