package gui;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import entity.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * controller for the order details page
 *
 */

public class OrderDetailsController implements Initializable {

	@FXML
	private Label idLabel;

	@FXML
	private Label orderNumberLabel;

	@FXML
	private Label emailLabel;

	@FXML
	private Label dateLabel;

	@FXML
	private Label timeLabel;

	@FXML
	private Label typeLabel;

	@FXML
	private Label TotalPriceLabel;

	@FXML
	private Label ParkNameLabel;

	@FXML
	private Label numberOfVisitorsLabel;
	@FXML
	private Label phoneLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
/**
 * 
 * @param order
 * This method fills the AnchorPane with Order order
 */
	public void getOrderDetailes(Order order) {
		idLabel.setText(order.getId());
		ParkNameLabel.setText(order.getParkName());
		TotalPriceLabel.setText(String.valueOf(order.getPrice()));
		typeLabel.setText(order.getType().name());
		timeLabel.setText(order.getTimeOfOrder().substring(0, 5));
		emailLabel.setText(order.getEmail());
		orderNumberLabel.setText(order.getOrderNum());
		numberOfVisitorsLabel.setText(String.valueOf(order.getNumOfVisitors()));
		String[] datearr = order.getDateOfOrder().split("-");
		dateLabel.setText(datearr[2] + "-" + datearr[1] + "-" + datearr[0]);
		phoneLabel.setText(order.getPhone());
	}
}