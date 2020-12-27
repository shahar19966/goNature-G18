package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import message.ClientMessage;
import message.ClientMessageType;

public class ExitControlPaneController {
	private GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private TextField idNumTextField;

    @FXML
    private Button validateBtn;

    @FXML
    void validateOrder(ActionEvent event) {
    	String id=idNumTextField.getText();
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.VALIDATE_ORDER_EXIT,id));
    	if(guiControl.getServerMsg().getMessage() instanceof Integer) {
    		Integer sumToPay=(Integer)guiControl.getServerMsg().getMessage();
    		guiControl.popUpMessage("Order Validated","Your order has been validated.\nTotal amount is:"+sumToPay+" NIS.");
    	}
    	else
    		guiControl.popUpError("Invalid order!");
    	idNumTextField.setText("");
    		
    }

}
