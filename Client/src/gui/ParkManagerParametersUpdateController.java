package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ParkManagerParametersUpdateController {

    @FXML
    private Button updateCapacity;

    @FXML
    private Button updateDifference;

    @FXML
    private Button updateVisitationTime;

    @FXML 
    void capacityUpdate(ActionEvent event) {

    }

    @FXML
    void differenceUpdate(ActionEvent event) {

    }

    @FXML
    void visitationTimeUpdate(ActionEvent event) {
    	Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.VISITATION_TIME_UPDATE.toString()));
            Stage stage = new Stage();
            stage.setTitle("park manager visitation time update");
            stage.setScene(new Scene(root,688, 438));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
    }

}





