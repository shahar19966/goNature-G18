package gui;

import entity.Employee;
import entity.EntityConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
/*
 * controller for employee home page, sets the labels according to the given employee
 */
public class EmployeeHomePageController {

    @FXML
    private Label idLabel;

    @FXML
    private Label employeeNumLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label parkOrDepLabel;
    
    public void setLabels(Employee emp) {
    	idLabel.setText(emp.getID());
    	employeeNumLabel.setText(emp.getEmployeeNumber());
    	firstNameLabel.setText(emp.getFirstName());
    	lastNameLabel.setText(emp.getLastName());
    	emailLabel.setText(emp.getEmail());
    	roleLabel.setText(emp.getRole());
    	parkOrDepLabel.setText(emp.getRoleEnum().equals(EntityConstants.EmployeeRole.DEP_MANAGER)?"Department":emp.getParkName());
    }

}
