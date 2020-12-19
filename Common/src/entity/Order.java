package entity;

import java.io.Serializable;

import entity.EntityConstants.OrderStatus;
import entity.EntityConstants.OrderType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order implements Serializable{
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
	public Order(String id, String parkName, String orderNum,
			String orderCreationDate, int numOfVisitors, OrderStatus status,
			OrderType type, String dateOfOrder, String timeOfOrder,
			int price) {
		this.id=id;
		this.parkName = parkName;
		this.orderNum = orderNum;
		this.orderCreationDate = orderCreationDate;
		this.numOfVisitors =numOfVisitors;
		this.status = status;
		this.type = type;
		this.dateOfOrder =dateOfOrder; 
		this.timeOfOrder =timeOfOrder; 
		this.price =price;
	}

	public void setId(String id) {
		this.id=id;
	}

	public void setParkName(String parkName) {
		this.parkName=parkName;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum=orderNum;
	}

	public void setOrderCreationDate(String orderCreationDate) {
		this.orderCreationDate=orderCreationDate;
	}

	public void setNumOfVisitors(int numOfVisitors) {
		this.numOfVisitors=numOfVisitors;
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
		this.dateOfOrder=dateOfOrder;
	}

	public void setTimeOfOrder(String timeOfOrder) {
		this.timeOfOrder=timeOfOrder;
	}
	public void setPrice(int price) {
		this.price=price;
	}
	

}
