package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entity.Employee;
import entity.Subscriber;
import entity.Visitor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientMainPageController implements Initializable  {
	private Object user;
	private ArrayList<Button> btnList;
    @FXML
    private AnchorPane enableDisablePane;

    @FXML
    private AnchorPane fillPane;

    @FXML
    private VBox buttonVBox;

    @FXML
    private Button orderBtn;

    @FXML
    private Button entryControlBtn;

    @FXML
    private Button exitControlBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    private Button orderTrakingBtn;

    @FXML
    private Button requestsBtn;

    @FXML
    private Button parametersBtn;

    @FXML
    private Button discountBtn;

    @FXML
    private Button registrationBtn;

    @FXML
    private Label mainLabel;

    @FXML
    private VBox alertPane;

    @FXML
    private Label alertTitle;

    @FXML
    private ScrollPane alertDesPane;

    @FXML
    private AnchorPane alertBody;

    @FXML
    private HBox alertButtons;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logOutClick;

    @FXML
    void EntryControlBtnClick(ActionEvent event) {

    }

    @FXML
    void discountBtnClick(ActionEvent event) {

    }

    @FXML
    void exitControlBtnClick(ActionEvent event) {

    }

    @FXML
    void homeBtnClick(ActionEvent event) {

    }

    @FXML
    void logOutBtnClick(ActionEvent event) {

    }

    @FXML
    void orderBtnClick(ActionEvent event) {

    }

    @FXML
    void orderTrakingBtnClick(ActionEvent event) {

    }

    @FXML
    void parametersBtnClick(ActionEvent event) {

    }

    @FXML
    void registrationBtnClick(ActionEvent event) {

    }

    @FXML
    void reguestsBtnClick(ActionEvent event) {

    }

    @FXML
    void reportsBtnClick(ActionEvent event) {

    }
    public void setUser(Object user) {
    	this.user=user;
    	if(user instanceof Visitor) {
    		setVisitorAndSubscriberButtons(btnList);
    	}
    	else if(user instanceof Subscriber) {
    		setVisitorAndSubscriberButtons(btnList);
    	}
    	else { //employee
    		setEmployeeButtons(btnList);
    	}
    }
    private void setBtnList(List<Button> list) {
    	list.add(orderBtn);
    	list.add(entryControlBtn);
    	list.add(exitControlBtn);
    	list.add(orderTrakingBtn);
    	list.add(parametersBtn);
    	list.add(registrationBtn);
    	list.add(requestsBtn);
    	list.add(reportsBtn);
    	list.add(discountBtn);
    }
    private void setVisitorAndSubscriberButtons(List<Button> list) {
    	list.remove(orderBtn);
    	list.remove(orderTrakingBtn);
    	for(Button b:list) {
    		b.setVisible(false);
    		b.setManaged(false);
    	}
    }
    private void setEmployeeButtons(List<Button> list) {
    	Employee emp=(Employee)user;
    	switch(emp.getRoleEnum()) {
    	case PARK_MANAGER:
    		list.remove(parametersBtn);
    		list.remove(discountBtn);
    		list.remove(reportsBtn);
    		break;
    	case DEP_MANAGER:
    		list.remove(reportsBtn);
    		list.remove(requestsBtn);
    		break;
    	case SERIVCE:
    		list.remove(registrationBtn);
			break;
    	case REGULAR:
    		list.remove(orderBtn);
    		list.remove(entryControlBtn);
    		list.remove(exitControlBtn);
			break;
    	}
    	for(Button b:list) {
    		b.setVisible(false);
    		b.setManaged(false);
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnList=new ArrayList<>();
		setBtnList(btnList);
	}

}
