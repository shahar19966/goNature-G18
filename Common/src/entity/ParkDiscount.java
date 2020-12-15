package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ParkDiscount {
	private SimpleStringProperty discountParkNumber;
	private SimpleStringProperty parkName;
	private SimpleStringProperty startDate;
	private SimpleStringProperty finishDate;
	private SimpleIntegerProperty discountAmount;
	private SimpleStringProperty discountStatus;
	private SimpleStringProperty employeeNumber;

	public ParkDiscount(String discountParkNumber, String parkName, String startDate, String finishDate,
			int discountAmount, String discountStatus, String employeeNumber) {
		this.discountParkNumber = new SimpleStringProperty(discountParkNumber);
		this.parkName = new SimpleStringProperty(parkName);
		this.startDate = new SimpleStringProperty(startDate);
		this.finishDate = new SimpleStringProperty(finishDate);
		this.discountAmount = new SimpleIntegerProperty(discountAmount);
		this.discountStatus = new SimpleStringProperty(discountStatus);
		this.employeeNumber = new SimpleStringProperty(employeeNumber);
	}

	public String getDiscountParkNumber() {
		return discountParkNumber.getValue();
	}

	public SimpleStringProperty getDiscountParkNumberProperty() {
		return discountParkNumber;
	}

	public void setDiscountParkNumber(String discountParkNumber) {
		this.discountParkNumber.set(discountParkNumber);
	}

	public String getParkName() {
		return parkName.getValue();
	}

	public SimpleStringProperty getParkNameProperty() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName.set(parkName);
	}

	public String getStartDate() {
		return startDate.getValue();
	}

	public SimpleStringProperty getStartDateProperty() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate.set(startDate);
	}

	public String getFinishDate() {
		return finishDate.getValue();
	}

	public SimpleStringProperty getFinishDateProperty() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate.set(finishDate);
	}

	public int getDiscountAmount() {
		return discountAmount.getValue();
	}

	public SimpleIntegerProperty getDiscountAmountProperty() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount.set(discountAmount);
	}

	public String getDiscountStatus() {
		return discountStatus.getValue();
	}

	public SimpleStringProperty getDiscountStatusProperty() {
		return discountStatus;
	}

	public void setDiscountStatus(String discountStatus) {
		this.discountStatus.set(discountStatus);
	}

	public String getFmployeeNumber() {
		return employeeNumber.getValue();
	}

	public SimpleStringProperty getEmployeeNumberProperty() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber.set(employeeNumber);
	}

}
