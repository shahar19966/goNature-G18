package gui;

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Employee;
import entity.EntityConstants;
import entity.ParameterUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

public class CapacityUpdateController implements Initializable{
	GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private TextField newvalue;

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
    void newValue(ActionEvent event) {

    }
 

    @FXML
    void updateClick(ActionEvent event) {
    	Employee emp = (Employee) guiControl.getUser();
ParameterUpdate parupdate=new ParameterUpdate(EntityConstants.ParkParameter.CAPACITY,Integer.parseInt(newvalue.getText()),emp.getParkName());

guiControl.sendToServer(new ClientMessage(ClientMessageType.PARAMETER_UPDATE, parupdate));
newvalue.setText("");
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}


}
