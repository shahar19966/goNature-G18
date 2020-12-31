package gui;

import java.awt.TexturePaint;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.sun.media.jfxmedia.events.NewFrameEvent;

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
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
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
	private ComboBox<String> startPhone;
	@FXML
	private Label payInAdvanceLabel;
	@FXML
	private CheckBox payInAdvanceCheckBox;
	@FXML
	private GridPane mainPane;
	@FXML
	private TextField finishPhone;
	@FXML
	private Button clearButton;
	private Tooltip tooltip = new Tooltip();
	private boolean orderButtonAble = false;
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
		payInAdvanceCheckBox.setSelected(false);
	}

	@FXML
	void orderFunc(ActionEvent event) {
		if (!orderButtonAble)
			return;
		List<Object> orderDes = createOrderFromForm();
		guiControl.sendToServer(new ClientMessage(ClientMessageType.ORDER, orderDes));
		if (guiControl.getServerMsg() == null) {
			GuiButton cancelButton = new GuiButton("Cancel Order", AlertType.Warning, Sizes.Medium);
			cancelButton.setOnAction(e -> {
				guiControl.getClientMainPageController().hideAlert();

			});
			GuiButton watingListButton = new GuiButton("Enter Wating List", AlertType.Info, Sizes.Medium);
			watingListButton.setOnAction(e -> {
				guiControl.sendToServer(new ClientMessage(ClientMessageType.WAITING_LIST, orderDes));
				guiControl.getClientMainPageController().showAlertWithOkButton(AlertType.Success,
						"Enter Wating List Succeeded", ((Order) guiControl.getServerMsg().getMessage()).toString(),
						null);
			});
			GuiButton datePickerListButton = new GuiButton("Pick Another Date", AlertType.Info, Sizes.Medium);
			datePickerListButton.setOnAction(e -> {
				Order order = (Order) orderDes.get(0);
				guiControl.sendToServer(new ClientMessage(ClientMessageType.PICK_AVAILABLE_DATES, order));
				displayAvailableDates((Map<String, List<String>>) guiControl.getServerMsg().getMessage(), orderDes);
			});
			List<Button> buttonList = new ArrayList<Button>();
			buttonList.add(cancelButton);
			buttonList.add(watingListButton);
			buttonList.add(datePickerListButton);
			guiControl.getClientMainPageController().showAlert(AlertType.Warning, "Unavailable Date",
					"The date you picked is unavailable.\nHow would you like to proceed?", buttonList);
		} else {
			guiControl.getClientMainPageController().showAlertWithOkButton(AlertType.Success, "Order Succeeded",
					((Order) guiControl.getServerMsg().getMessage()).toString(), null);
		}
		clearFunc(null);

	}

	private void displayAvailableDates(Map<String, List<String>> map, List<Object> orderDes) {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource(ClientConstants.Screens.AVAILABLE_DATES_PAGE.toString()));
		GridPane root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AvailableDatesPageController adpc = fxmlLoader.getController();
		adpc.setTable(map, orderDes);
		GuiButton cancelButton = new GuiButton("Cancel", AlertType.Danger, Sizes.Medium);
		cancelButton.setOnAction(e -> {
			guiControl.getClientMainPageController().hideAlert();
		});
		List<Button> buttonList = new ArrayList<>();
		buttonList.add(cancelButton);
		guiControl.getClientMainPageController().showAlert(AlertType.Info, "Smart Date Picker", root, buttonList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		parkNameComboBox.minWidthProperty().bind(mainPane.widthProperty().multiply(0.25).subtract(40));
		date.minWidthProperty().bind(mainPane.widthProperty().multiply(0.3).subtract(40));
		peopleAmount.minWidthProperty().bind(mainPane.widthProperty().multiply(1.0 / 3).subtract(60));
		timeComboBox.minWidthProperty().bind(mainPane.widthProperty().multiply(1.0 / 3).subtract(60));
		emailText.minWidthProperty().bind(mainPane.widthProperty().multiply(0.43 * 0.5).subtract(40));
		startPhone.minWidthProperty().bind(mainPane.widthProperty().multiply(0.43 * 0.5 * 0.4).subtract(40));
		finishPhone.minWidthProperty().bind(mainPane.widthProperty().multiply(0.43 * 0.5 * 0.4).subtract(60));
		if (guiControl.getUser() instanceof Subscriber && ((Subscriber) guiControl.getUser()).getIsGuide())
			guideGroupGridPane.setVisible(true);
		else
			guideGroupGridPane.setVisible(false);
		payInAdvanceLabel.visibleProperty().bind(guideGroupCheckBox.selectedProperty());
		payInAdvanceCheckBox.visibleProperty().bind(guideGroupCheckBox.selectedProperty());
		startPhone.getItems().addAll("050", "052", "053", "054", "055", "058");
		startPhone.getSelectionModel().selectFirst();
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
		LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);
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

			StringBuilder toolTipText = new StringBuilder();
			orderButtonAble = true;
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date chosenDate = format.parse(date.getValue().toString() + " " + timeComboBox.getValue() + ":00");
			if (chosenDate.before(now)) {
				orderButtonAble = false;
				toolTipText.append("You can't order a trip before " + format.format(now));
			}
			if (GUIControl.isEmailValid(emailText.getText()) == false) {
				if (!orderButtonAble)
					toolTipText.append("\n");
				if (emailText.getText().length() > 0)
					toolTipText.append(emailText.getText() + " is not a valid Email");
				else
					toolTipText.append("You have to enter Email");
				orderButtonAble = false;
			}
			if (finishPhone.getText().length() == 0) {
				if (!orderButtonAble)
					toolTipText.append("\n");
				toolTipText.append("You have to enter Phone Number");
				orderButtonAble = false;
			} else {
				if (!finishPhone.getText().matches("[0-9]+")) {
					if (!orderButtonAble)
						toolTipText.append("\n");
					toolTipText.append("Phone Number is only digits");
				}
				if (finishPhone.getText().length() != 7) {
					if (!orderButtonAble)
						toolTipText.append("\n");
					toolTipText.append("Phone Number is 10 digit with initial");
					orderButtonAble = false;
				}

			}
			orderButton.setTooltip(null);
			orderButton.setOpacity(1);
			if (!orderButton.getStyleClass().contains("logInBtn")) {
				orderButton.getStyleClass().add("logInBtn");
				orderButton.getStyleClass().remove("logInDisabledBtn");
			}
			if (!orderButton.getStyleClass().contains("button")) {
				orderButton.getStyleClass().add("button");
			}
			if (!orderButtonAble) {
				tooltip.setText(toolTipText.toString());
				orderButton.setTooltip(tooltip);
				orderButton.getStyleClass().clear();
				orderButton.getStyleClass().add("logInDisabledBtn");
				orderButton.setOpacity(0.6);
			}
			return false;
		}, emailText.textProperty(), date.valueProperty(), timeComboBox.valueProperty(), finishPhone.textProperty()));

	}

	private List<Object> createOrderFromForm() {
		List<Object> orderDesList = new ArrayList<Object>();
		Order order = new Order(null, parkNameComboBox.getValue(), peopleAmount.getValue(), null,
				date.getValue().toString(), timeComboBox.getValue(), -1, emailText.getText(),
				startPhone.getValue() + finishPhone.getText());
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

		Boolean payInAdvane = new Boolean(payInAdvanceCheckBox.isSelected());
		orderDesList.add(order);
		orderDesList.add(payInAdvane);
		return orderDesList;
	}

	@FXML
	private void clearFunc(ActionEvent event) {
		emailText.setText("");
		parkNameComboBox.getSelectionModel().selectFirst();
		timeComboBox.getSelectionModel().selectFirst();
		startPhone.getSelectionModel().selectFirst();
		finishPhone.setText("");
		guideGroupCheckBox.setSelected(false);
		payInAdvanceCheckBox.setSelected(false);
		peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ClientConstants.MAX_PEOPLE));
		peopleAmount.getValueFactory().setValue(1);
		Date today = new Date();
		LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);
		date.setValue(localDate);
	}
}
