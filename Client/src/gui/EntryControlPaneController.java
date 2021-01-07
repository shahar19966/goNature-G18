package gui;

import entity.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import message.ClientMessage;
import message.ClientMessageType;
/**
 * Controller for the entry control screen
 */
public class EntryControlPaneController {
	private GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private TextField idNumTextField;

    @FXML
    private TextField amountOfVisitorsTextField;

    @FXML
    private Button validateButton;

    /**
     * 
     * @param event
     * This method is called when validateButton is clicked
     * It enters Visitor with id and visitor amount to the park
     * It pops an alert if it was successful or not
     */
    @FXML
    void validateOrder(ActionEvent event) {
    	Employee emp=(Employee)guiControl.getUser();
    	if( !idNumTextField.getText().matches("[0-9]+") || idNumTextField.getText().length()!=9) 
    		GUIControl.popUpError("Invalid ID, please enter a 9 digit ID number");
    	else if(!amountOfVisitorsTextField.getText().matches("^[1-9][0-9]*$"))
    		GUIControl.popUpError("Invalid amount,please enter a valid amount (more than zero)");
    	else {
    		String[] idVisitorsAndParkName={idNumTextField.getText(),amountOfVisitorsTextField.getText(),emp.getParkName()};
        	guiControl.sendToServer(new ClientMessage(ClientMessageType.VALIDATE_ORDER_ENTRY,idVisitorsAndParkName));
        	if(guiControl.getServerMsg().getMessage() instanceof Integer) {
        		Integer sumToPay=(Integer)guiControl.getServerMsg().getMessage();
        		if(sumToPay.intValue()==-1)
        			GUIControl.popUpError("Amount of people entered is bigger than the amount of people in the order!");
        		else
        			GUIControl.popUpMessage("Entry Registered","Your order has been validated and you are free to enter.\nTotal amount is:"+sumToPay+" NIS.\n"
        				+ "Thank you for choosing GoNature!");
        	}
        	else
        		GUIControl.popUpError("No order exists for this identification number");
    	}
    	idNumTextField.setText("");
    	amountOfVisitorsTextField.setText("");
    	

    }

}
