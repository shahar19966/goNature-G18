package gui;

	import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import entity.Employee;
import entity.EntityConstants;
import entity.Park;
import entity.ParkDiscount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.DatePicker;
	import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
	import javafx.scene.layout.GridPane;
import message.ClientMessage;
import message.ClientMessageType;

	public class MyDiscountRequestsController implements Initializable {
		private ObservableList<String> parkNameObservableList = FXCollections.observableArrayList();
		GUIControl guiControl = GUIControl.getInstance();

	    @FXML
	    private GridPane mainPane;

	    @FXML
	    private TableView<?> discountTableView;

	    @FXML
	    private TableColumn<?, ?> discountParkNumberCol;

	    @FXML
	    private TableColumn<?, ?> parkNameCol;

	    @FXML
	    private TableColumn<?, ?> startDateCol;

	    @FXML
	    private TableColumn<?, ?> finishDateCol;

	    @FXML
	    private TableColumn<?, ?> discountAmountCol;

	    @FXML
	    private TableColumn<?, ?> discountStatusCol;

	    @FXML
	    private ComboBox<String> parkNameComboBox;

	    @FXML
	    private DatePicker discountStartDatePicker;

	    @FXML
	    private DatePicker discountFinishDatePicker;

	    @FXML
	    private Spinner<Integer> discountAmountSpinner;

	    @FXML
	    private Button addButton;

	    @FXML
	    private Button createButton;

	    @FXML
	    void addFunc(ActionEvent event) {
	    	ParkDiscount newDiscountRequest= new ParkDiscount
	    			(parkNameComboBox.getValue(), discountStartDatePicker.getValue().toString(),
	    					discountFinishDatePicker.getValue().toString(), discountAmountSpinner.getValue(),
	    					EntityConstants.RequestStatus.WAITING, ((Employee) guiControl.getUser()).getEmployeeNumber());
	    	guiControl.sendToServer(new ClientMessage(ClientMessageType.DISCOUNT_REQUEST, newDiscountRequest));
	    }

	    @FXML
	    void createDiscountRequest(ActionEvent event) {

	    }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			guiControl.sendToServer(new ClientMessage(ClientMessageType.GET_PARKS, null));
			List<Park> parkArr = (List<Park>) guiControl.getServerMsg().getMessage();
			for (Park p : parkArr)
				if(p.getParkName().equals(((Employee) guiControl.getUser()).getParkName()))
				parkNameObservableList.add(p.getParkName());
			parkNameComboBox.setItems(parkNameObservableList);
			parkNameComboBox.getSelectionModel().select(parkNameObservableList.get(0));
			discountAmountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 20));
			discountStartDatePicker.setValue(LocalDate.now());
			discountFinishDatePicker.setValue(LocalDate.now());
			
		}

	}


