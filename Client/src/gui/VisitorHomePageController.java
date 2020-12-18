package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VisitorHomePageController {

    @FXML
    private Label idLabel;
    public void setId(String id) {
    	idLabel.setText(id);
    }
}
