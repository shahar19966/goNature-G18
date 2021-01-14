package entity;

import java.io.Serializable;

/**
 * class with all the parameters we need for reports related to visitors
 * 
 */
public class VisitorReport implements Serializable {

	private String namePark;
	private int time;
	private int countSubscriber, countGuid, countRegular, countCancellations, countNotRealized, price;
	private double avgSubscriber, avgRegular, avgGuid;

	public VisitorReport(String namePark) {
		this.namePark = namePark;
		this.countSubscriber = 0;
		this.countGuid = 0;
		this.countRegular = 0;
		this.price = 0;
		this.countCancellations = 0;
		this.countNotRealized = 0;

	}

	public VisitorReport(int time) {
		this.time = time;
		this.avgGuid = 0;
		this.avgRegular = 0;
		this.avgSubscriber = 0;

	}

	public int time() {
		return time;
	}

	public String getNamePark() {
		return namePark;
	}

	public int getPrice() {
		return price;
	}

	public int getCountGuid() {
		return countGuid;
	}

	public void setCountGuid(int countGuid) {
		this.countGuid = countGuid;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setCountCancellations(int countCancellations) {
		this.countCancellations = countCancellations;
	}

	public void setCountNotRealized(int countNotRealized) {
		this.countNotRealized = countNotRealized;
	}

	public int getCountNotRealized() {
		return countNotRealized;
	}

	public int getCountCancellations() {
		return countCancellations;
	}

	public int getCountRegular() {
		return countRegular;
	}

	public void setCountRegular(int CountRegular) {
		this.countRegular = CountRegular;
	}

	public int getCountSubscriber() {
		return countSubscriber;
	}

	public void setCountSubscriber(int countSubscriber) {
		this.countSubscriber = countSubscriber;
	}

	public void setTime(int time) {
		this.time = time;

	}

	public String timeSring() {
		return time + ":00";
	}

	public double getAvgGuid() {
		return avgGuid;
	}

	public double getAvgSubscriber() {
		return avgSubscriber;
	}

	public double getAvgRegular() {
		return avgRegular;
	}

	public void setAvgSubscriber(double avgSubscriber) {
		this.avgSubscriber = avgSubscriber;
	}

	public void setAvgRegular(double avgRegular) {
		this.avgRegular = avgRegular;
	}

	public void setAvgGuid(double avgGuid) {
		this.avgGuid = avgGuid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VisitorReport other = (VisitorReport) obj;
		if (time == other.time && avgGuid == other.avgGuid && avgRegular == other.avgRegular
				&& avgSubscriber == other.avgSubscriber) {
			return true;
		}
		return false;
	}

}
