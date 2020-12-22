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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
/*
 * this class is the controller of the client's main page after logging in
 * initially it consists of all user's buttons,but upon being created it removes every button that doesn't concern the connected user
 * additionally it holds the functionality of the client main frames
 */
public class ClientMainPageController implements Initializable  {
	private Object user;
	private GUIControl guiControl=GUIControl.getInstance();
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
    	guiControl.logOut();
    	guiControl.openLogInPage();
    }

    @FXML
    void orderBtnClick(ActionEvent event) {
    	loadSubAndVisitorOrder();
    	setSwitchPane(panesMap.get("order"));
    }

    @FXML
    void orderTrakingBtnClick(ActionEvent event) {

    }

    @FXML
    void parametersBtnClick(ActionEvent event) {//liron
    	loadParkManagerParametersUpdate();
    	setSwitchPane(panesMap.get("parameters"));
    	
    }

    @FXML
    void registrationBtnClick(ActionEvent event) {

    }

    @FXML
    void requestsBtnClick(ActionEvent event) {

    }

    @FXML
    void reportsBtnClick(ActionEvent event) {

    	Employee emp=(Employee)user;
    	if(emp.getRole().equals("PARK_MANAGER"))
    		loadParkManagerReports();
    	else
    		loadDepartmentManagerReports();
    	setSwitchPane(panesMap.get("reports"));
   

    }
	private Map<String, Pane> panesMap;
	/*
	 * method called to set the user currently connected to the client and show his main screen and buttons
	 */
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
    /*
     * method that adds every button loaded into a button list
     */
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
    /*
     * method that removes every button that doesn't concern a visitor and a subscriber from display
     */
    private void setVisitorAndSubscriberButtons(List<Button> list) {
    	list.remove(orderBtn);
    	list.remove(orderTrakingBtn);
    	for(Button b:list) {
    		b.setVisible(false);
    		b.setManaged(false);
    	}
    }
    /*
     * method that removes every button that doesn't concern a certain employee type (decided by the type of employee that connected)
     */
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
    /*
     * method that takes a pane and displays it and it's content in the small window set for it in the client display
     */
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
	
	private void loadParkManagerReports() {
		FXMLLoader fxmlLoader1 =new FXMLLoader(getClass().getResource(ClientConstants.Screens.PARK_MANAGER_REPOTRS.toString()));
		VBox root=null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ParkManagerReportsController pmrc=fxmlLoader1.getController();
		panesMap.put("reports",root );
	}
	private void loadParkManagerParametersUpdate()//liron
	{
		FXMLLoader fxmlLoader1 =new FXMLLoader(getClass().getResource(ClientConstants.Screens.PARK_MANAGER_PATAMETERS_UPDATE.toString()));
		VBox root=null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		panesMap.put("parameters",root );
	}
	
	private void loadDepartmentManagerReports() {
		FXMLLoader fxmlLoader1 =new FXMLLoader(getClass().getResource(ClientConstants.Screens.DEPARTMENT_MANAGER_REPOTRS.toString()));
		VBox root=null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	DepartmentManagerReportsController dmrc=fxmlLoader1.getController();
		panesMap.put("reports",root );
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
		SubscriberHomePageController shpc=fxmlLoader1.getController();
		shpc.setLabels((Subscriber)user);
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
	private void loadSubAndVisitorOrder() {
		FXMLLoader fxmlLoader1 =new FXMLLoader(getClass().getResource(ClientConstants.Screens.SUB_AND_VISITOR_ORDER_PAGE.toString()));
		GridPane root=null;
		try {
			root = fxmlLoader1.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	DepartmentManagerReportsController dmrc=fxmlLoader1.getController();
		panesMap.put("order",root );
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnList=new ArrayList<>();
		setBtnList(btnList);
		panesMap = new HashMap<String, Pane>();
	}

			


}
