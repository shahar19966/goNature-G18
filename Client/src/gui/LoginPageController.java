package gui;

import java.io.IOException;

import entity.Visitor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    void loginFunc(ActionEvent event) throws IOException {
    	if(validateLogin()) {
    		Stage primaryStage=GUIControl.getStage();
    		primaryStage.hide();
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ClientMainPage.fxml"));
    		AnchorPane root = fxmlLoader.load();
    		ClientMainPageController cmpc = (ClientMainPageController)fxmlLoader.getController();
    		//setUser needs to be called on the user that DB returned
    		cmpc.setUser(new Visitor("123"));
			Scene scene = new Scene (root);
			primaryStage.setScene(scene);
			primaryStage.show();
    	}
    	else
    		GUIControl.popUpError("Invalid information,please try again");
    
    }

    @FXML
    void setEmployee(ActionEvent event) {
    	passwordTextField.setVisible(true);
    	passwordTextField.setText("");
    	subscriberBtn.setSelected(false);
    	idBtn.setSelected(false);
    }

    @FXML
    void setId(ActionEvent event) {
    	idTextField.setText("");
    	passwordTextField.setVisible(false);
    	subscriberBtn.setSelected(false);
    	employeeBtn.setSelected(false);
    }

    @FXML
    void setSubscriber(ActionEvent event) {
    	idTextField.setText("");
    	passwordTextField.setVisible(false);
    	idBtn.setSelected(false);
    	employeeBtn.setSelected(false);
    }
    private boolean validateLogin() {
    	ClientMessage msg=null;
    	if(idBtn.isSelected()) 
    		msg=new ClientMessage(ClientMessageType.VALIDATE_VISITOR,idTextField.getText());
    	else if(subscriberBtn.isSelected())
    		msg=new ClientMessage(ClientMessageType.VALIDATE_SUBSCRIBER,idTextField.getText());
    	else if(employeeBtn.isSelected()) {
    		String[] idAndPassword={idTextField.getText(),passwordTextField.getText()};
    		msg=new ClientMessage(ClientMessageType.VALIDATE_EMPLOYEE,idAndPassword);
    	}
    	else {};
    	GUIControl.sendToServer(msg);
    	if(GUIControl.getServerMsg().getMessage()==null)
    		return false;
    	return true;
    }

}
