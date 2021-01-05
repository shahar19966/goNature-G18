package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import entity.EntityConstants.OrderStatus;
import entity.EntityConstants.OrderType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order implements Serializable {
	private String id;
	private String parkName;
	private String orderNum;
	private String orderCreationDate;
	private int numOfVisitors;
	private EntityConstants.OrderStatus status;
	private EntityConstants.OrderType type;
	private String dateOfOrder;
	private String timeOfOrder;
	private int price;
	private String email;
	private String phone;

	public Order(String id, String parkName, String orderNum, String orderCreationDate, int numOfVisitors,
			OrderStatus status, OrderType type, String dateOfOrder, String timeOfOrder, int price, String email,
			String phone) {
		this.id = id;
		this.parkName = parkName;
		this.orderNum = orderNum;
		this.orderCreationDate = orderCreationDate;
		this.numOfVisitors = numOfVisitors;
		this.status = status;
		this.type = type;
		this.dateOfOrder = dateOfOrder;
		if (timeOfOrder.split(":").length == 2)
			timeOfOrder = timeOfOrder + ":00";
		this.timeOfOrder = timeOfOrder;
		this.price = price;
		this.email = email;
		this.phone = phone;
	}

	/*
	 * for validation
	 */
	public Order(String id, String parkName, int numOfVisitors, OrderType type, String dateOfOrder, String timeOfOrder,
			int price, String email, String phone) {
		this.id = id;
		this.parkName = parkName;
		this.numOfVisitors = numOfVisitors;
		this.type = type;
		this.dateOfOrder = dateOfOrder;
		if (timeOfOrder.split(":").length == 2)
			timeOfOrder = timeOfOrder + ":00";
		this.timeOfOrder = timeOfOrder;
		this.price = price;
		this.email = email;
		this.phone = phone;
		this.orderCreationDate = null;
	}

	public void setTimeOfOrder(int hour) {
		if (hour < 10)
			timeOfOrder = "0" + hour + ":00:00";
		else
			timeOfOrder = hour + ":00:00";
	}

	public void setOrderCreationDate(String orderCreationDate) {
		this.orderCreationDate = orderCreationDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public String getId() {
		return id;
	}

	public String getParkName() {
		return parkName;
	}

	public String getDateOfOrder() {
		return dateOfOrder;
	}

	public String getTimeOfOrder() {
		return timeOfOrder;
	}

	public int getNumOfVisitors() {
		return numOfVisitors;
	}

	public int getPrice() {
		return price;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public void setNumOfVisitors(int numOfVisitors) {
		this.numOfVisitors = numOfVisitors;
	}

	public EntityConstants.OrderStatus getStatus() {
		return status;
	}

	public void setStatus(EntityConstants.OrderStatus status) {
		this.status = status;
	}

	public EntityConstants.OrderType getType() {
		return type;
	}

	public void setType(EntityConstants.OrderType type) {
		this.type = type;
	}

	public void setDateOfOrder(String dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public void setTimeOfOrder(String timeOfOrder) {
		this.timeOfOrder = timeOfOrder;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	@Override
	public String toString() {
		StringBuilder orderDetails = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		orderDetails.append("Order Number: " + orderNum + "\n");
		orderDetails.append("ID: " + id + "\n");
		orderDetails.append("Park Name: " + parkName + "\n");
		orderDetails.append("Number Of Visitors: " + numOfVisitors + "\n");
		orderDetails.append("Type: " + type.name() + "\n");
		String[] datearr = dateOfOrder.split("-");
		orderDetails.append("Date: " + datearr[2] + "-" + datearr[1] + "-" + datearr[0] + "\n");
		orderDetails.append("Time: " + timeOfOrder + "\n");
		if (orderCreationDate != null)
			orderDetails.append(
					"Creation Date: " + LocalDateTime.parse(orderCreationDate, formatter).format(formatter2) + "\n");
		orderDetails.append("Email: " + email + "\n");
		orderDetails.append("Phone: " + phone + "\n");
		orderDetails.append("Total Price: " + price);
		return orderDetails.toString();
	}

}
