package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Employee;
import entity.EntityConstants;
import entity.ParameterUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

public class DifferenceParameterUpdateController{
	GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private TextField newvalue;
    @FXML
    private Button update;

    @FXML
    private Button exit;

    @FXML
    void exitController(ActionEvent event) {
   	 Stage stage = (Stage)exit.getScene().getWindow();
	    stage.close();
    }
    @FXML
    void newValue(ActionEvent event) {

    }
    @FXML
    void updateClick(ActionEvent event) {
    	Employee emp = (Employee) guiControl.getUser();
ParameterUpdate parupdate=new ParameterUpdate(EntityConstants.ParkParameter.DIFFERENCE,Integer.parseInt(newvalue.getText()),emp.getParkName());

guiControl.sendToServer(new ClientMessage(ClientMessageType.PARAMETER_UPDATE, parupdate));
newvalue.setText("");
    }
    /*
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

    	return true;
    }


}
