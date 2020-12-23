package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VisitationTimeUpdateController {

    @FXML
    private Button update;

    @FXML
    private Button exit;

    @FXML
    void exitClick(ActionEvent event) {
    	
    	    Stage stage = (Stage)exit.getScene().getWindow();
    	    stage.close();
    }

    @FXML
    void updateClick(ActionEvent event) {

    }

}
