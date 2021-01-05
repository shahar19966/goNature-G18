package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
/**
 *  controller for the order details page
 *
 */

public class OrderDetailsController  implements Initializable { 

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
    	OrderPaneController opc=new OrderPaneController();
    	Order order=opc.getOrderDetailes();
    	idLabel.setText(order.getId());
    	ParkNameLabel.setText(order.getParkName());
       TotalPriceLabel.setText(String.valueOf(order.getPrice()));
    	typeLabel.setText(order.getType().toString());
    	timeLabel.setText(order.getTimeOfOrder());
    	emailLabel.setText(order.getEmail());
    	orderNumberLabel.setText(order.getOrderNum());
    	numberOfVisitorsLabel.setText(String.valueOf(order.getNumOfVisitors()));
    	dateLabel.setText(order.getDateOfOrder());
    	phoneLabel.setText(order.getPhone());
    	
    	
    }
}