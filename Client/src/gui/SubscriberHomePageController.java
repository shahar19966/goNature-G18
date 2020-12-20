package gui;

import entity.Employee;
import entity.EntityConstants;
import entity.Subscriber;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
/*
 * controller for subscriber home page,fills labels with given subscriber's details
 */
public class SubscriberHomePageController {

    @FXML
    private Label idLabel;

    @FXML
    private Label subNumLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label familyMembersLabel;

    @FXML
    private Label isGuideLabel;
    
    public void setLabels(Subscriber sub) {
        	idLabel.setText(sub.getID());
        	subNumLabel.setText(sub.getSubscriberNumber());
        	firstNameLabel.setText(sub.getFirstName());
        	lastNameLabel.setText(sub.getLastName());
        	phoneLabel.setText(sub.getPhone());
        	emailLabel.setText(sub.getEmail());
        	familyMembersLabel.setText(String.valueOf(sub.getSubscriberFamilyMembers()));	
        	isGuideLabel.setText(sub.getIsGuide()?"Yes":"No");
    }
}
