package com.messo.pos;
	
import java.io.IOException;

import com.messo.pos.db.DBAdapter;
import com.messo.pos.view.MainPOSController;
import com.messo.pos.view.MenuEditorController;
import com.messo.pos.view.StatisticsController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainApp extends Application {
	
	private Stage primaryStage;
	private Stage editorStage;
	
	
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
            loader.setLocation(MainApp.class.getResource("view/MainPOS.fxml"));
            root = (AnchorPane) loader.load();
            
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
	
}
