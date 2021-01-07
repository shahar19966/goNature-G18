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
    	if(!isValidVisitationTime())
    		return;
    	else {
    	Employee emp = (Employee) guiControl.getUser();
    	ParameterUpdate parupdate=new ParameterUpdate(EntityConstants.ParkParameter.DURATION,Integer.parseInt(newvalue.getText()),emp.getParkName());

    	guiControl.sendToServer(new ClientMessage(ClientMessageType.PARAMETER_UPDATE, parupdate));
    	newvalue.setText("");
    	}
    }
	/**
	 * 
	 * A method for input checks for the newvalue field
	 * 
	 */
    public boolean isValidVisitationTime()
    {
    	//check if the the new visitation time is not negative 
    	if(Integer.parseInt(newvalue.getText())<0) {
    		GUIControl.popUpError("Visitation time can't be less than 0, please enter valid value");
    		newvalue.setText("");
    		return false;
    	}
    	//check if the new visitation time contains only digits
    	if (!newvalue.getText().matches("[0-9]+")) {
    		GUIControl.popUpError("Visitation time can only contain digits");
    		return false;
    	}
    	//check if the new visitation time is more than 7 hours, according to the openning hours of the park
    	if(Integer.parseInt(newvalue.getText())>7)
    	{
    		GUIControl.popUpError("Visitation time can't be more than 7 hours, please enter valid value");
    		newvalue.setText("");
    		return false;
    	}
    	return true;
    }


}
