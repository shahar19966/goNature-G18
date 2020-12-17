package gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import message.ClientMessage;
import message.ClientMessageType;

public class LoginPageController {

    @FXML
    private AnchorPane enableDisablePane;

    @FXML
    private RadioButton idBtn;

    @FXML
    private ToggleGroup userType;

    @FXML
    private RadioButton subscriberBtn;

    @FXML
    private RadioButton employeeBtn;

    @FXML
    private TextField idTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginBtn;

    @FXML
    private VBox buttonVBox;

    @FXML
    private Label mainLabel;

    @FXML
    void loginFunc(ActionEvent event) {
    }

    @FXML
    void setEmployee(ActionEvent event) {
    	passwordTextField.setVisible(true);
    	subscriberBtn.setSelected(false);
    	idBtn.setSelected(false);
    }

    @FXML
    void setId(ActionEvent event) {
    	passwordTextField.setVisible(false);
    	subscriberBtn.setSelected(false);
    	employeeBtn.setSelected(false);
    }

    @FXML
    void setSubscriber(ActionEvent event) {
    	passwordTextField.setVisible(false);
    	idBtn.setSelected(false);
    	employeeBtn.setSelected(false);
    }

}
