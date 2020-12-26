package entity;

import java.io.Serializable;

public class ParkDiscount implements Serializable {
	private String parkName;
	private String startDate;
	private String finishDate;
	private int discountAmount;
	private EntityConstants.RequestStatus discountStatus;
	private String employeeNumber;

	public ParkDiscount(String parkName, String startDate, String finishDate,
			int discountAmount, EntityConstants.RequestStatus discountStatus, String employeeNumber) {
		this.parkName = parkName;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.discountAmount = discountAmount;
		this.discountStatus = discountStatus;
		this.employeeNumber= employeeNumber;
	}

	public String getParkName() {
		return parkName;
	}


	public void setParkName(String parkName) {
		this.parkName=parkName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate=startDate;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate=finishDate;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}


	public void setDiscountAmount(int discountAmount) {
		this.discountAmount=discountAmount;
	}

	public EntityConstants.RequestStatus getDiscountStatus() {
		return discountStatus;
	}


	public void setDiscountStatus(EntityConstants.RequestStatus discountStatus) {
		this.discountStatus=discountStatus;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	@Override
	public String toString() {
		return "discount for park "+parkName+" from "+startDate+""
				+ " until "+finishDate+" amount "+ discountAmount + "% status "
				+discountStatus +" by employee: " +employeeNumber;
	
	}

}
