package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entity.EntityConstants;
import entity.Order;
import entity.Park;
import entity.Subscriber;
import entity.Visitor;
import entity.EntityConstants.OrderType;
import gui.ClientConstants.AlertType;
import gui.ClientConstants.Sizes;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import message.ClientMessage;
import message.ClientMessageType;

public class OrderPaneController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();
	@FXML
	private Label orderTripLabel;

	@FXML
	private ComboBox<String> parkNameComboBox;

	@FXML
	private Spinner<Integer> peopleAmount;

	@FXML
	private GridPane mailGrid;

	@FXML
	private TextField emailText;

	@FXML
	private DatePicker date;

	@FXML
	private ComboBox<String> timeComboBox;

	@FXML
	private CheckBox guideGroupCheckBox;

	@FXML
	private GridPane guideGroupGridPane;

	@FXML
	private Button orderButton;

	@FXML
	private Button clearButton;

	private ObservableList<String> parkNameObservableList = FXCollections.observableArrayList();

	@FXML
	void changePeopleAmount(ActionEvent event) {
		int tmp = peopleAmount.getValue();
		if (guideGroupCheckBox.isSelected()) {
			peopleAmount
					.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, ClientConstants.MAX_PEOPLE));
			if (tmp != 1)
				peopleAmount.getValueFactory().setValue(tmp);
		} else {
			peopleAmount
					.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ClientConstants.MAX_PEOPLE));
			peopleAmount.getValueFactory().setValue(tmp);
		}
	}

	@FXML
	void orderFunc(ActionEvent event) {

		guiControl.sendToServer(new ClientMessage(ClientMessageType.ORDER, createOrderFromForm()));
		if (guiControl.getServerMsg() == null) {
			GuiButton cancelButton = new GuiButton("Cancel Order", AlertType.Warning, Sizes.Medium);
			cancelButton.setOnAction(e -> {
				guiControl.getClientMainPageController().hideAlert();
				
			});
			GuiButton watingListButton = new GuiButton("Enter Wating List", AlertType.Info, Sizes.Medium);
			watingListButton.setOnAction(e -> {
				guiControl.sendToServer(new ClientMessage(ClientMessageType.WAITING_LIST, createOrderFromForm()));
				guiControl.getClientMainPageController().showAlertWithOkButton(AlertType.Success, "Enter Wating List Succeeded",
						((Order) guiControl.getServerMsg().getMessage()).toString(), null);
				clearFunc(null);
			});
			GuiButton datePickerListButton = new GuiButton("Pick Another Date", AlertType.Info, Sizes.Medium);
			datePickerListButton.setOnAction(e -> {
				guiControl.getClientMainPageController().hideAlert();
				clearFunc(null);
			});
			List<Button> buttonList = new ArrayList<Button>();
			buttonList.add(cancelButton);
			buttonList.add(watingListButton);
			buttonList.add(datePickerListButton);
			guiControl.getClientMainPageController().showAlert(AlertType.Warning, "Unavailable Date", "The date you picked is unavailable.\nHow would you like to proceed?", buttonList);
		} else {
			guiControl.getClientMainPageController().showAlertWithOkButton(AlertType.Success, "Order Succeeded",
					((Order) guiControl.getServerMsg().getMessage()).toString(), null);
			clearFunc(null);
		}
		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (!(guiControl.getUser() instanceof Subscriber) || !((Subscriber) guiControl.getUser()).getIsGuide()) {
			guideGroupGridPane.setVisible(false);
		}
		parkNameComboBox.prefWidthProperty().bind(emailText.widthProperty());
		peopleAmount.prefWidthProperty().bind(timeComboBox.widthProperty());
		date.prefWidthProperty().bind(emailText.widthProperty());
		timeComboBox.prefWidthProperty().bind(emailText.widthProperty());
		peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ClientConstants.MAX_PEOPLE));
		guiControl.sendToServer(new ClientMessage(ClientMessageType.GET_PARKS, null));
		List<Park> parkArr = (List<Park>) guiControl.getServerMsg().getMessage();
		for (Park p : parkArr)
			parkNameObservableList.add(p.getParkName());
		parkNameComboBox.setItems(parkNameObservableList);
		DatePicker minDate = new DatePicker();
		Date today = new Date();
		LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		minDate.setValue(LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth()));
		final Callback<DatePicker, DateCell> dayCellFactory;
		dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.isBefore(minDate.getValue())) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}
			}
		};
		date.setDayCellFactory(dayCellFactory);
		date.setValue(localDate);
		for (int i = EntityConstants.PARK_OPEN; i <= EntityConstants.PARK_CLOSED; i++) {
			if (i < 10)
				timeComboBox.getItems().add("0" + i + ":00");
			else
				timeComboBox.getItems().add(i + ":00");
		}
		timeComboBox.getSelectionModel().selectFirst();
		parkNameComboBox.getSelectionModel().selectFirst();
		orderButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
			return !GUIControl.isEmailValid(emailText.getText());
		}, emailText.textProperty()));
	}
	private Order createOrderFromForm()
	{
		Order order = new Order(null, parkNameComboBox.getValue(), peopleAmount.getValue(), null,
				date.getValue().toString(), timeComboBox.getValue(), -1, emailText.getText());
		if (guiControl.getUser() instanceof Visitor) {
			Visitor visitor = (Visitor) guiControl.getUser();
			order.setId(visitor.getId());
			order.setType(OrderType.REGULAR);
		} else {
			Subscriber subscriber = (Subscriber) guiControl.getUser();
			order.setId(subscriber.getID());
			if (guideGroupCheckBox.isSelected())
				order.setType(OrderType.GUIDE);
			else
				order.setType(OrderType.SUBSCRIBER);
		}
		return order;
	}
	@FXML
	private void clearFunc(ActionEvent event) {
		emailText.setText("");
		parkNameComboBox.getSelectionModel().selectFirst();
		timeComboBox.getSelectionModel().selectFirst();
		guideGroupCheckBox.setSelected(false);
		peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ClientConstants.MAX_PEOPLE));
		peopleAmount.getValueFactory().setValue(1);
		Date today = new Date();
		LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		date.setValue(localDate);
	}
}
