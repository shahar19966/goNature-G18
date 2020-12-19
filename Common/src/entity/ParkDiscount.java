package entity;

public class ParkDiscount {
	private String parkName;
	private String startDate;
	private String finishDate;
	private int discountAmount;
	private String discountStatus;

	public ParkDiscount(String parkName, String startDate, String finishDate,
			int discountAmount, String discountStatus, String employeeNumber) {
		this.parkName = parkName;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.discountAmount = discountAmount;
		this.discountStatus = discountStatus;
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

	public String getDiscountStatus() {
		return discountStatus;
	}


	public void setDiscountStatus(String discountStatus) {
		this.discountStatus=discountStatus;
	}

}
