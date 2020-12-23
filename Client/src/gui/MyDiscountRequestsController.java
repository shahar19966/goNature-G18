package gui;

	import javafx.event.ActionEvent;
import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.DatePicker;
	import javafx.scene.control.Spinner;
	import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
	import javafx.scene.layout.GridPane;

	public class MyDiscountRequestsController {

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
	    private ComboBox<?> parkNameComboBox;

	    @FXML
	    private DatePicker discountStartDatePicker;

	    @FXML
	    private DatePicker discountFinishDatePicker;

	    @FXML
	    private Spinner<?> discountAmountSpinner;

	    @FXML
	    private Button addButton;

	    @FXML
	    private Button createButton;

	    @FXML
	    void addFunc(ActionEvent event) {

	    }

	    @FXML
	    void createDiscountRequest(ActionEvent event) {

	    }

	}


