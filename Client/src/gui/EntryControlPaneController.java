package gui;

import entity.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import message.ClientMessage;
import message.ClientMessageType;

public class EntryControlPaneController {
	private GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private TextField idNumTextField;

    @FXML
    private TextField amountOfVisitorsTextField;

    @FXML
    private Button validateButton;

    @FXML
    void validateOrder(ActionEvent event) {
    	Employee emp=(Employee)guiControl.getUser();
    	String[] idVisitorsAndParkName={idNumTextField.getText(),amountOfVisitorsTextField.getText(),emp.getParkName()};
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.VALIDATE_ORDER_ENTRY,idVisitorsAndParkName));
    	if(guiControl.getServerMsg().getMessage() instanceof Integer) {
    		Integer sumToPay=(Integer)guiControl.getServerMsg().getMessage();
    		GUIControl.popUpMessage("Entry Registered","Your order has been validated and you are free to enter.\nTotal amount is:"+sumToPay+" NIS.\n"
    				+ "Thank you for choosing GoNature!");
    	}
    	else
    		GUIControl.popUpError("No order exists for this identification number");
    	idNumTextField.setText("");
    	amountOfVisitorsTextField.setText("");
    	

    }

}
