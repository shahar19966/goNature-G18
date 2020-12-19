package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entity.Employee;
import entity.Subscriber;
import entity.Visitor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ClientMainPageController implements Initializable  {
	private Object user;
	private ArrayList<Button> btnList;
    @FXML
    private AnchorPane enableDisablePane;

    @FXML
    private AnchorPane switchPane;

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
    	setSwitchPane(panesMap.get("home"));
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
    void requestsBtnClick(ActionEvent event) {

    }

    @FXML
    void reportsBtnClick(ActionEvent event) {

    }
	private Map<String, Pane> panesMap;
    public void setUser(Object user) {
    	this.user=user;
    	if(user instanceof Visitor) {
    		setVisitorAndSubscriberButtons(btnList);
    		loadVisitorScreens();
    	}
    	else if(user instanceof Subscriber) {
    		setVisitorAndSubscriberButtons(btnList);
    		loadSubscriberScreens();
    	}
    	else { //employee
    		setEmployeeButtons(btnList);
    		loadEmployeeScreens();
    	}
    	setSwitchPane(panesMap.get("home"));
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
    	case SERVICE:
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
	public void setSwitchPane(Pane toSwitch) {
		switchPane.getChildren().clear();
		switchPane.getChildren().add(toSwitch);
		AnchorPane.setLeftAnchor(toSwitch, 20.0);
		AnchorPane.setRightAnchor(toSwitch, 20.0);
		AnchorPane.setBottomAnchor(toSwitch, 10.0);
		AnchorPane.setTopAnchor(toSwitch, 0.0);
	}
	private void loadVisitorScreens() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource(ClientConstants.Screens.VISITOR_MAIN_PAGE.toString()));
		System.out.println(ClientConstants.Screens.VISITOR_MAIN_PAGE.toString());
		VBox root=null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VisitorHomePageController vmpc=fxmlLoader1.getController();
		vmpc.setId(((Visitor)user).getId());
		panesMap.put("home",root );
	}
	private void loadSubscriberScreens() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource(
				user instanceof Subscriber?ClientConstants.Screens.SUBSCRIBER_MAIN_PAGE.toString():
					ClientConstants.Screens.GUIDE_MAIN_PAGE.toString()));
		VBox root=null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panesMap.put("home",root );
	}
	private void loadEmployeeScreens() {
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource(ClientConstants.Screens.EMPLOYEE_MAIN_PAGE.toString()));
		VBox root=null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EmployeeHomePageController ehpc=fxmlLoader1.getController();
		ehpc.setLabels((Employee)user);
		panesMap.put("home",root );
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnList=new ArrayList<>();
		setBtnList(btnList);
		panesMap = new HashMap<String, Pane>();
	}

			


}
