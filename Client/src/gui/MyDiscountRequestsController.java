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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
	import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import message.ClientMessage;
import message.ClientMessageType;

	public class MyDiscountRequestsController implements Initializable {
		private ObservableList<String> parkNameObservableList = FXCollections.observableArrayList();
		private ObservableList<ParkDiscount> discountRequestsObservableList = FXCollections.observableArrayList();
		private Integer discountNumberCol=0;
		GUIControl guiControl = GUIControl.getInstance();

	    @FXML
	    private GridPane mainPane;

	    @FXML
	    private TableView<ParkDiscount> discountTableView;

	    @FXML
	    private TableColumn<ParkDiscount, Integer> discountParkNumberCol;

	    @FXML
	    private TableColumn<ParkDiscount, String> parkNameCol;

	    @FXML
	    private TableColumn<ParkDiscount, String> startDateCol;

	    @FXML
	    private TableColumn<ParkDiscount, String> finishDateCol;

	    @FXML
	    private TableColumn<ParkDiscount, Integer> discountAmountCol;

	    @FXML
	    private TableColumn<ParkDiscount, EntityConstants.RequestStatus> discountStatusCol;

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

	    
	    //Initialize the page to show park manager's parks ,the date of today,disable past dates
	    //and default amount of discount
	    //also shows a list of his discount requests
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
			discountStartDatePicker.setDayCellFactory(picker -> new DateCell() {//disable past dates
			        public void updateItem(LocalDate date, boolean empty) {
			            super.updateItem(date, empty);
			            LocalDate today = LocalDate.now();
			            if(date.isBefore(today)) {
			            setDisable(empty || date.compareTo(today) < 0 );
			            setStyle("-fx-background-color: #ffc0cb;");
			            }

			        }
			    });
			discountStartDatePicker.setValue(LocalDate.now());
			discountFinishDatePicker.setDayCellFactory(picker -> new DateCell() {//disable past dates
		        public void updateItem(LocalDate date, boolean empty) {
		            super.updateItem(date, empty);
		            LocalDate today = LocalDate.now();
		            if(date.isBefore(today)) {
		            setDisable(empty || date.compareTo(today) < 0 );
		            setStyle("-fx-background-color: #ffc0cb;");
		            }
		        }
		    });
			discountFinishDatePicker.setValue(LocalDate.now());
			guiControl.sendToServer(new ClientMessage(ClientMessageType.GET_DISCOUNT_REQUESTS_FRON_DB,((Employee) guiControl.getUser()).getEmployeeNumber() ));
			List<ParkDiscount> discountRequestsarr = (List<ParkDiscount>) guiControl.getServerMsg().getMessage();
			for( ParkDiscount pd:discountRequestsarr)	
			{
				discountRequestsObservableList.add(pd);
			}
		//	discountParkNumberCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, Integer>("discountParkNumber"));
			parkNameCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("parkName"));
			startDateCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("startDate"));
			finishDateCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, String>("finishDate"));
			discountAmountCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, Integer>("discountAmount"));
			discountStatusCol.setCellValueFactory(new PropertyValueFactory<ParkDiscount, EntityConstants.RequestStatus>("discountStatus"));
			discountTableView.setItems(discountRequestsObservableList);

		}

	}


