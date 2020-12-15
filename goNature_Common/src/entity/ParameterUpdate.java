package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ParameterUpdate {
	private EntityConstants.ParkParameter parameter;
	private SimpleIntegerProperty newValue;
	private SimpleStringProperty parkName;
	public ParameterUpdate(EntityConstants.ParkParameter parameter, int newValue,String parkName) {
		this.parameter = parameter;
		this.newValue = new SimpleIntegerProperty(newValue);
		this.parkName=new SimpleStringProperty(parkName);
	}
	public String getParameter() {
		return parameter.toString();
	}
	public void setParameter(EntityConstants.ParkParameter parameter) {
		this.parameter=parameter;
	}
	public EntityConstants.ParkParameter getParameterEnum() {
		return parameter;
	}
	public int getNewValue() {
		return newValue.getValue();
	}
	public SimpleIntegerProperty getNewValueProperty() {
		return newValue;
	}
	public void setNewValue(int newValue) {
		this.newValue.set(newValue);
	}
	public void setParkName(String parkName) {
		this.parkName.set(parkName);
	}
	public String getParkName() {
		return parkName.getValue();
	}
	public SimpleStringProperty getParkNameProperty() {
		return parkName;
	}

}
