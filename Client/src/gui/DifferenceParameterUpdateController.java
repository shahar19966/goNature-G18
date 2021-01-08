package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Employee;
import entity.EntityConstants;
import entity.ParameterUpdate;
import entity.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;
/**
 * 
 * This class responsible for the park difference update for the park manager
 * 
 */
public class DifferenceParameterUpdateController{
	GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private TextField newvalue;
    @FXML
    private Button update;

    @FXML
    private Button exit;
/**
 * when exit button pressed, exit the window
 * @param event
 */
    @FXML
    void exitController(ActionEvent event) {
   	 Stage stage = (Stage)exit.getScene().getWindow();
	    stage.close();
    }
    @FXML
    void newValue(ActionEvent event) {

    }
    /**
	 * When update button pressed, the difference request save and send to the server.
	 * Depending on the server response, shows alert with message if the new difference was valid or not.
     * @param event
     */
    @FXML
    void updateClick(ActionEvent event) {
    	if(!isValidDifference())
    		return;
    	else {
    	Employee emp = (Employee) guiControl.getUser();
ParameterUpdate parupdate=new ParameterUpdate(EntityConstants.ParkParameter.DIFFERENCE,Integer.parseInt(newvalue.getText()),emp.getParkName());

guiControl.sendToServer(new ClientMessage(ClientMessageType.PARAMETER_UPDATE, parupdate));
newvalue.setText("");
    }
    }
    /**
	 * 
	 * A method for input checks for the newvalue field
	 * 
	 */
    public boolean isValidDifference()
    {
    	Employee emp = (Employee) guiControl.getUser();
		String parkname=emp.getParkName();
		guiControl.sendToServer(new ClientMessage(ClientMessageType.GET_PARK, parkname));
        Park park=(Park) guiControl.getServerMsg().getMessage();
        
    	//check if the new difference contains only digits
    	if (!newvalue.getText().matches("[0-9]+")) {
    		GUIControl.popUpError("Difference can only contain digits");
    		return false;
    	}
        //check if the new difference is more than the park capacity
        if(park.getParkMaxVisitorsDefault()<Integer.parseInt(newvalue.getText()))
        		{
    		GUIControl.popUpError("Difference can't be more than park capacity, please enter valid value");
    		newvalue.setText("");
    		return false;
        		}

    	//check if the the new difference is not negative 
    	if(Integer.parseInt(newvalue.getText())<0) {
    		GUIControl.popUpError("Difference can't be less than 0, please enter valid value");
    		newvalue.setText("");
    		return false;
    	}


    	return true;
    }


}
