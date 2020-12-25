package gui;

import javafx.scene.control.TextField;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import entity.Employee;
import entity.Order;
import entity.EntityConstants.OrderType;
import gui.ClientConstants.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import message.ClientMessage;
import message.ClientMessageType;

public class OrderRegularEmployeeController implements Initializable {
	GUIControl guiControl = GUIControl.getInstance();
	@FXML
	private GridPane mainPane;

	@FXML
	private RadioButton idBtn;

	@FXML
	private ToggleGroup userType;

	@FXML
	private RadioButton subscriberBtn;

	@FXML
	private RadioButton guideBtn;

	@FXML
	private Spinner<Integer> peopleAmount;

	@FXML
	private Button orderButton;

	@FXML
	private TextField idText;

	@FXML
	void setId(ActionEvent event) {
		setSpinerForIdAndSubscriber();
		idText.setPromptText("Enter ID Number");
		idText.setText("");
	}

	@FXML
	void setSGuide(ActionEvent event) {
		setSpinerForGuide();
		idText.setPromptText("Enter Subscriber Number");
		idText.setText("");
	}

	@FXML
	void setSubscriber(ActionEvent event) {
		setSpinerForIdAndSubscriber();
		idText.setPromptText("Enter Subscriber Number");
		idText.setText("");
	}

	@FXML
	void orderFunc(ActionEvent event) {
		if (!validate())
			return;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date now = new Date();
		System.out.println(dateFormat.format(now));
		System.out.println(timeFormat.format(now));
		Order order = new Order(idText.getText(), ((Employee) guiControl.getUser()).getParkName(),
				peopleAmount.getValue(), null, dateFormat.format(now), timeFormat.format(now), -1, "");
		if (idBtn.isSelected())
			order.setType(OrderType.REGULAR);
		if (subscriberBtn.isSelected())
			order.setType(OrderType.SUBSCRIBER);
		if (guideBtn.isSelected())
			order.setType(OrderType.GUIDE);
		guiControl.sendToServer(new ClientMessage(ClientMessageType.OCCASIONAL_ORDER, order));
		if (guiControl.getServerMsg().getMessage() == null)
			GUIControl.popUpError("Park " + order.getParkName() + " is Full");
		else {
			if (guiControl.getServerMsg().getMessage() instanceof String)
				GUIControl.popUpError((String) guiControl.getServerMsg().getMessage());
			else
				guiControl.getClientMainPageController().showAlertWithOkButton(AlertType.Success, "Order Succeeded",
						((Order) guiControl.getServerMsg().getMessage()).toString(), null);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idText.setPromptText("Enter ID Number");
		peopleAmount.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.4));
		idText.setText("");
		peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
		peopleAmount.getValueFactory().setValue(1);
	}

	private void setSpinerForIdAndSubscriber() {
		int val = peopleAmount.getValue();
		peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
		peopleAmount.getValueFactory().setValue(val);
	}

	private void setSpinerForGuide() {
		int val = peopleAmount.getValue();
		peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, ClientConstants.MAX_PEOPLE));
		if (val == 1)
			peopleAmount.getValueFactory().setValue(2);
		if (val > ClientConstants.MAX_PEOPLE)
			peopleAmount.getValueFactory().setValue(ClientConstants.MAX_PEOPLE);

	}

	private boolean validate() {
		if (idText.getText().length() == 0) {
			GUIControl.popUpError(
					"Empty identification field\nPlease choose your desired identification and fill in your credentials");
			return false;
		}
		for (char ch : idText.getText().toCharArray()) {
			if (ch < '0' || ch > '9') {
				GUIControl.popUpError("Identifaction is only numbers");
				return false;
			}
		}
		if (idBtn.isSelected()) {
			if (idText.getText().length() != 9) {
				GUIControl.popUpError("ID must consist of 9 digits");
				return false;
			}
		}
		return true;
	}
}
