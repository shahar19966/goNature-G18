package gui;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

public class ParkManagerReportsController {

	Stage primaryStage;
	@FXML
	private AnchorPane anchorPane;

	@FXML
	void capacityReport(ActionEvent event) {

	}

	@FXML
	void incomeReport(ActionEvent event) {

	}

	@FXML
	
	void visitationReport(ActionEvent event) {
		Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.PARK_MNG_VISIT_REPORT.toString()));
            Stage stage = new Stage();
            stage.setTitle("park manager visitation report");
            stage.setScene(new Scene(root,688, 438));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
        
	
	}
}
