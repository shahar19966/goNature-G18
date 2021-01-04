package gui;

import entity.Employee;
import entity.EntityConstants;
import entity.Park;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import message.ClientMessage;
import message.ClientMessageType;
/*
 * controller for employee home page, sets the labels according to the given employee
 */
public class EmployeeHomePageController {
	GUIControl guiControl=GUIControl.getInstance();

    @FXML
    private Label idLabel;

    @FXML
    private Label employeeNumLabel;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label titelName;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label parkOrDepLabel;

    @FXML
    private Label visitorsAmountLabel;

    @FXML
    private Label visitorsAmountNumberLabel;
    @FXML
    private Label parkTextLabel;
    
    public void setLabels(Employee emp) {
    	idLabel.setText(emp.getID());
    	employeeNumLabel.setText(emp.getEmployeeNumber());
    	firstNameLabel.setText(emp.getFirstName());
    	lastNameLabel.setText(emp.getLastName());
    	emailLabel.setText(emp.getEmail());
    	roleLabel.setText(emp.getRole());
    	titelName.setText(emp.getFirstName());
    	if(emp.getRoleEnum().equals(EntityConstants.EmployeeRole.DEP_MANAGER)) {
    		parkTextLabel.setVisible(false);
    		parkTextLabel.setManaged(false);
    		roleLabel.setLayoutY(parkOrDepLabel.getLayoutY());
    	}
    	else
    		parkOrDepLabel.setText(emp.getParkName());
    	if(emp.getRoleEnum().equals(EntityConstants.EmployeeRole.PARK_MANAGER)||emp.getRoleEnum().equals(EntityConstants.EmployeeRole.REGULAR)) {
    		guiControl.sendToServer(new ClientMessage(ClientMessageType.GET_PARK,emp.getParkName()));
    		Park park;
    		if(guiControl.getServerMsg().getMessage() instanceof Park) {
    			park=(Park)guiControl.getServerMsg().getMessage();
    			visitorsAmountNumberLabel.setText(String.valueOf(park.getParkCurrentVisitors()));
    		}	
    	}
    	else {
    		visitorsAmountLabel.setVisible(false);
    		visitorsAmountNumberLabel.setVisible(false);
    	}
    }

}
