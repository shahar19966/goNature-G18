package entity;

import java.io.Serializable;

public class ParameterUpdate implements Serializable {
	private EntityConstants.ParkParameter parameter;
	private int newValue;
	private String parkName;
	public ParameterUpdate(EntityConstants.ParkParameter parameter, int newValue,String parkName) {
		this.parameter = parameter;
		this.newValue = newValue;
		this.parkName=parkName;
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
		return newValue;
	}

	public void setNewValue(int newValue) {
		this.newValue=newValue;
	}
	public void setParkName(String parkName) {
		this.parkName=parkName;
	}
	public String getParkName() {
		return parkName;
	}
	@Override
	public String toString() {
		return "update request sent to department manager "+parameter+" ="+newValue;
	}
}
