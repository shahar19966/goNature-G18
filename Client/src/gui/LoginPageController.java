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
import message.ServerMessage;
/**
 * controller for the login page
 */
public class LoginPageController {
	GUIControl guiControl=GUIControl.getInstance();
	IClientServerCommunication clientServerCommunication=new ClientServerCommunication();
	class ClientServerCommunication implements IClientServerCommunication{

		@Override
		public void popUpError(String msg) {
			GUIControl.popUpError(msg);			
		}

		@Override
		public void sendToServer(Object msg) {
			guiControl.sendToServer(msg);
			
		}

		@Override
		public ServerMessage getServerMsg() {
			return guiControl.getServerMsg();
		}

		@Override
		public void setUser(Object user) {
			guiControl.setUser(user);		
		}
		
	}
    @FXML
    private AnchorPane mainPane;

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
    /**
     * method called upon clicking the login button, calls validateLogin() and incase it's validated opens up the user's main screen to display
     */
    @FXML
    void loginFunc(ActionEvent event) throws IOException {
    	if(validateLogin(idTextField.getText(),passwordTextField.getText(),idBtn.isSelected(),subscriberBtn.isSelected(),employeeBtn.isSelected())) {
    		Stage primaryStage=guiControl.getStage();
    		primaryStage.hide();
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ClientMainPage.fxml"));
    		AnchorPane root = fxmlLoader.load();
    		ClientMainPageController cmpc = (ClientMainPageController)fxmlLoader.getController();
    		guiControl.setClientMainPageController(cmpc);
    		cmpc.setUser(guiControl.getUser());
			Scene scene = new Scene (root);
			scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
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
    /**
     * method that asks the server for a certain user given the fields that were selected and filled
     * @return true if user was found (and holds the object of the user in GUIControl) or false if user wasn't found in database
     */
    public boolean validateLogin(String id,String password,boolean checkID,boolean checkSubNum,boolean checkEmployee) {
    	ClientMessage msg=null;
    	if(id.isEmpty()) {
    		clientServerCommunication.popUpError("Empty identification field\nPlease choose your desired identification and fill in your credentials");
			return false;
    	}
    	if( !id.matches("[0-9]+")) {
    		clientServerCommunication.popUpError("Invalid credentials!\nOnly digits are permitted");
			return false;
    	}
    	if(checkID) { 
    		if(id.length()!=9) {
    			clientServerCommunication.popUpError("ID must consist of 9 digits");
    			return false;
    		}	
    		msg=new ClientMessage(ClientMessageType.LOGIN_VISITOR,id);
    	}
    	else if(checkSubNum)
    		msg=new ClientMessage(ClientMessageType.LOGIN_SUBSCRIBER,id);
    	else if(checkEmployee) {
    		if(password.isEmpty()) {
    			clientServerCommunication.popUpError("Empty password field\nPlease fill in your password");
    			return false;
    		}
    		String[] idAndPassword={id,password};
    		msg=new ClientMessage(ClientMessageType.LOGIN_EMPLOYEE,idAndPassword);
    	}
    	else {};
    	clientServerCommunication.sendToServer(msg);
    	if(clientServerCommunication.getServerMsg().getMessage()==null) {
    		clientServerCommunication.popUpError("Invalid information,please try again");
    		return false;
    	}
    	else if(clientServerCommunication.getServerMsg().getMessage().equals("logged in")) {
    		clientServerCommunication.popUpError("This user is already logged in");
    		return false;
    	}
    	clientServerCommunication.setUser(clientServerCommunication.getServerMsg().getMessage());	
    	return true;
    }
    public void setClientServerCommunication(IClientServerCommunication clientServerCommunication) {
    	this.clientServerCommunication=clientServerCommunication;
    }

}
