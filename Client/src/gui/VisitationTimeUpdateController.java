package gui;

import entity.Employee;
import entity.EntityConstants;
import entity.ParameterUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

public class VisitationTimeUpdateController {
	GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private TextField newvalue;
    @FXML
    private Button update;

    @FXML
    private Button exit;
    @FXML
    void newValue(ActionEvent event) {

    }
    @FXML
    void exitClick(ActionEvent event) {
    	
    	    Stage stage = (Stage)exit.getScene().getWindow();
    	    stage.close();
    }

    @FXML
    void updateClick(ActionEvent event) {
    	Employee emp = (Employee) guiControl.getUser();
    	ParameterUpdate parupdate=new ParameterUpdate(EntityConstants.ParkParameter.DURATION,Integer.parseInt(newvalue.getText()),emp.getParkName());

    	guiControl.sendToServer(new ClientMessage(ClientMessageType.PARAMETER_UPDATE, parupdate));
    	newvalue.setText("");
    }

}
