package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * controller for visitor home page,fills labels with given visitor's details
 */
public class VisitorHomePageController {

    @FXML
    private Label idLabel;
    public void setId(String id) {
    	idLabel.setText(id);
    }
}
