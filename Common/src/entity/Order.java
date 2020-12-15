package entity;

import java.io.Serializable;

import entity.EntityConstants.OrderStatus;
import entity.EntityConstants.OrderType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order implements Serializable{
	private SimpleStringProperty id;
	private SimpleStringProperty parkName;
	private SimpleStringProperty orderNum;
	private SimpleStringProperty orderCreationDate;
	private SimpleIntegerProperty numOfVisitors;
	private EntityConstants.OrderStatus status;
	private EntityConstants.OrderType type;
	private SimpleStringProperty dateOfOrder;
	private SimpleStringProperty timeOfOrder;
	private SimpleIntegerProperty actualNumOfVisitors;
	public Order(String id, String parkName, String orderNum,
			String orderCreationDate, int numOfVisitors, OrderStatus status,
			OrderType type, String dateOfOrder, String timeOfOrder,
			int actualNumOfVisitors) {
		this.id=new SimpleStringProperty(id);
		this.parkName = new SimpleStringProperty(parkName);
		this.orderNum = new SimpleStringProperty(orderNum);
		this.orderCreationDate = new SimpleStringProperty(orderCreationDate);
		this.numOfVisitors =new SimpleIntegerProperty(numOfVisitors);
		this.status = status;
		this.type = type;
		this.dateOfOrder =new SimpleStringProperty(dateOfOrder); 
		this.timeOfOrder =new SimpleStringProperty(timeOfOrder); 
		this.actualNumOfVisitors =new SimpleIntegerProperty(actualNumOfVisitors);
	}
	public SimpleStringProperty getIdProperty() {
		return id;
	}
	public void setId(String id) {
		this.id.setValue(id);
	}
	public SimpleStringProperty getParkNameProperty() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName.setValue(parkName);
	}
	public SimpleStringProperty getOrderNumProperty() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum.setValue(orderNum);
	}
	public SimpleStringProperty getOrderCreationDateProperty() {
		return orderCreationDate;
	}
	public void setOrderCreationDate(String orderCreationDate) {
		this.orderCreationDate.setValue(orderCreationDate);
	}
	public SimpleIntegerProperty getNumOfVisitorsProperty() {
		return numOfVisitors;
	}
	public void setNumOfVisitors(int numOfVisitors) {
		this.numOfVisitors.setValue(numOfVisitors);
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
	public SimpleStringProperty getDateOfOrderProperty() {
		return dateOfOrder;
	}
	public void setDateOfOrder(String dateOfOrder) {
		this.dateOfOrder.setValue(dateOfOrder);
	}
	public SimpleStringProperty getTimeOfOrderProperty() {
		return timeOfOrder;
	}
	public void setTimeOfOrder(String timeOfOrder) {
		this.timeOfOrder.setValue(timeOfOrder);
	}
	public SimpleIntegerProperty getActualNumOfVisitorsProperty() {
		return actualNumOfVisitors;
	}
	public void setActualNumOfVisitors(int actualNumOfVisitors) {
		this.actualNumOfVisitors.setValue(actualNumOfVisitors);
	}
	

}
