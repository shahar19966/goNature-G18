package entity;

public class ParameterUpdate {
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
}
