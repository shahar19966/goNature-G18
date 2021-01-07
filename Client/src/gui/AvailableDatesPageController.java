package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import entity.Order;
import gui.ClientConstants.AlertType;
import gui.ClientConstants.Sizes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import message.ClientMessage;
import message.ClientMessageType;

/**
 * 
 * @author Aviad This is The Controller for AvailableDatesPage fxml
 *
 */
public class AvailableDatesPageController implements Initializable {

	GUIControl guiControl = GUIControl.getInstance();
	@FXML
	private GridPane gridPane;

	@FXML
	private Label titleLabel;

	@FXML
	private HBox mainHBox;
	private VBox orderDetails;
	private OrderDetailsController orderDetailsController;

	/**
	 * 
	 * @param dateMap
	 * @param orderDes Update the Table from available time and dates for park of
	 *                 orderDes park requested given by dateMap. Every hour that is
	 *                 not full is presented as a Button.
	 */
	public void setTable(Map<String, List<String>> dateMap, List<Object> orderDes) {
		Set keySet = dateMap.keySet();
		String[] dates = Arrays.copyOf(keySet.toArray(), keySet.toArray().length, String[].class);
		int i = 0;
		int count = 0;
		for (Node node : mainHBox.getChildren()) {
			VBox mainVBox = (VBox) node;
			Label title = (Label) mainVBox.getChildren().get(0);
			String[] datearr = dates[i].split("-");
			title.setText(datearr[2] + "-" + datearr[1] + "-" + datearr[0]);
			VBox secondaryVBox = (VBox) mainVBox.getChildren().get(1);
			for (String hour : dateMap.get(dates[i])) {
				final String nextDate = dates[i];
				String niceHour = hour.substring(0, 5);
				GuiButton button = new GuiButton(niceHour, AlertType.Info, Sizes.Medium);
				button.setOnAction(e -> {
					Order order = (Order) orderDes.get(0);
					order.setDateOfOrder(nextDate);
					order.setTimeOfOrder(hour);
					guiControl.sendToServer(new ClientMessage(ClientMessageType.ORDER, orderDes));
					if (null != orderDetails && null != orderDetailsController) {
						orderDetailsController.getOrderDetailes((Order) guiControl.getServerMsg().getMessage());
						guiControl.getClientMainPageController().showAlertWithOkButton(AlertType.Success,
								"Order Succeeded", orderDetails, null);
					} else
						guiControl.getClientMainPageController().showAlertWithOkButton(AlertType.Success,
								"Order Succeeded", ((Order) guiControl.getServerMsg().getMessage()).toString(), null);
				});
				secondaryVBox.getChildren().add(button);
				count++;
			}
			i++;

		}
		if (count == 0)
			GUIControl.popUpError("The park is full for te next seven days.");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderDetails.fxml"));
			orderDetails = fxmlLoader.load();
			orderDetailsController = (OrderDetailsController) fxmlLoader.getController();
		} catch (IOException e) {
			orderDetails = null;
			orderDetailsController = null;
		}

		mainHBox.prefWidthProperty().bind(gridPane.widthProperty());
		for (Node node : mainHBox.getChildren()) {
			VBox mainVBox = (VBox) node;
			VBox secondaryVBox = (VBox) mainVBox.getChildren().get(1);
			secondaryVBox.prefHeightProperty().bind(mainHBox.heightProperty().subtract(40));
			secondaryVBox.minWidthProperty().bind(mainHBox.widthProperty().divide(7));
		}
	}

}
