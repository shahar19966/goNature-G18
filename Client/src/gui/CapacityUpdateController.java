package gui;

import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
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
/**
 * 
 * This class responsible for the park capacity update for the park manager
 * 
 */
public class CapacityUpdateController {
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
	void exitClick(ActionEvent event) {
		Stage stage = (Stage)exit.getScene().getWindow();
		stage.close();
	}
	@FXML
	void newValue(ActionEvent event) {

	}

	/**
	 * 
	 * When update button pressed, the capacity request save and send to the server.
	 * Depending on the server response, shows alert with message if the new capacity was valid or not.
	 */
	@FXML
	void updateClick(ActionEvent event) {

		if(!isValidCapacity())
			return;
		else {	
			Employee emp = (Employee) guiControl.getUser();
			ParameterUpdate parupdate=new ParameterUpdate(EntityConstants.ParkParameter.CAPACITY,Integer.parseInt(newvalue.getText()),emp.getParkName());
			guiControl.sendToServer(new ClientMessage(ClientMessageType.PARAMETER_UPDATE, parupdate));
			newvalue.setText("");



		}
	}
	/**
	 * 
	 * A method for input checks for the newvalue field
	 * 
	 */
	public boolean isValidCapacity()
	{
		//check if the new capacity contains only digits
		if (!newvalue.getText().matches("[0-9]+")) {
			GUIControl.popUpError("Capacity can only contain digits");
			newvalue.setText("");
			return false;
		}
		//check if the the new capacity is not negative 
		if(Integer.parseInt(newvalue.getText())<0) {
			GUIControl.popUpError("Capacity can't be less than 0, please enter valid value");
			newvalue.setText("");
			return false;
		}

		return true;
	}



}
