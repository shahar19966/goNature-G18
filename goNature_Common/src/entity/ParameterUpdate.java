package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ParameterUpdate {
	private SimpleStringProperty parameter;
	private SimpleIntegerProperty newValue;
	private SimpleStringProperty parkName;
	public ParameterUpdate(String parameter, int newValue,String parkName) {
		this.parameter = new SimpleStringProperty(parameter);
		this.newValue = new SimpleIntegerProperty(newValue);
		this.parkName=new SimpleStringProperty(parkName);
	}
	public String getParameter() {
		return parameter.getValue();
	}
	public void setParameter(String parameter) {
		this.parameter.set(parameter);
	}
	public SimpleStringProperty getParameterProperty() {
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
