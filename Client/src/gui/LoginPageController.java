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
	GUIControl guiControl=GUIControl.getInstance();
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
    		Stage primaryStage=guiControl.getStage();
    		primaryStage.hide();
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ClientMainPage.fxml"));
    		AnchorPane root = fxmlLoader.load();
    		ClientMainPageController cmpc = (ClientMainPageController)fxmlLoader.getController();
    		cmpc.setUser(guiControl.getUser());
			Scene scene = new Scene (root);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e->{
				guiControl.disconnect();
			});
			primaryStage.show();
    	}   
    }

    @FXML
    void setEmployee(ActionEvent event) {
    	idTextField.setPromptText("Enter Employee Number");
    	idTextField.setText("");
    	passwordTextField.setVisible(true);
    	passwordTextField.setText("");
    	subscriberBtn.setSelected(false);
    	idBtn.setSelected(false);
    }

    @FXML
    void setId(ActionEvent event) {
    	idTextField.setPromptText("Enter ID Number");
    	idTextField.setText("");
    	passwordTextField.setVisible(false);
    	subscriberBtn.setSelected(false);
    	employeeBtn.setSelected(false);
    }

    @FXML
    void setSubscriber(ActionEvent event) {
    	idTextField.setPromptText("Enter Subscriber Number");
    	idTextField.setText("");
    	passwordTextField.setVisible(false);
    	idBtn.setSelected(false);
    	employeeBtn.setSelected(false);
    }
    private boolean validateLogin() {
    	ClientMessage msg=null;
    	if(idBtn.isSelected()) { 
    		if(idTextField.getText().length()!=9 || !idTextField.getText().matches("[0-9]+")) {
    			GUIControl.popUpError("ID must consist of 9 digits");
    			return false;
    		}	
    		msg=new ClientMessage(ClientMessageType.LOGIN_VISITOR,idTextField.getText());
    	}
    	else if(subscriberBtn.isSelected())
    		msg=new ClientMessage(ClientMessageType.LOGIN_SUBSCRIBER,idTextField.getText());
    	else if(employeeBtn.isSelected()) {
    		String[] idAndPassword={idTextField.getText(),passwordTextField.getText()};
    		msg=new ClientMessage(ClientMessageType.LOGIN_EMPLOYEE,idAndPassword);
    	}
    	else {};
    	guiControl.sendToServer(msg);
    	if(guiControl.getServerMsg().getMessage()==null) {
    		GUIControl.popUpError("Invalid information,please try again");
    		return false;
    	}
    	else if(guiControl.getServerMsg().getMessage().equals("logged in")) {
    		GUIControl.popUpError("This user is already logged in");
    		return false;
    	}
    	guiControl.setUser(guiControl.getServerMsg().getMessage());	
    	return true;
    }

}
