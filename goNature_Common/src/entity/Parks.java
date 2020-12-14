package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Parks {
	private SimpleStringProperty parkName;
	private SimpleIntegerProperty parkMaxVisitorsDefault;
	private SimpleIntegerProperty parkDiffFromMax;
	private SimpleIntegerProperty parkVisitDuration;
	private SimpleIntegerProperty parkCurrentVisitors;

	public Parks(String parkName, int parkMaxVisitorsDefault, int parkDiffFromMax, int parkVisitDuration,
			int parkCurrentVisitors) {
		this.parkName = new SimpleStringProperty(parkName);
		this.parkMaxVisitorsDefault = new SimpleIntegerProperty(parkMaxVisitorsDefault);
		this.parkDiffFromMax = new SimpleIntegerProperty(parkDiffFromMax);
		this.parkVisitDuration = new SimpleIntegerProperty(parkVisitDuration);
		this.parkCurrentVisitors = new SimpleIntegerProperty(parkCurrentVisitors);
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

	public int getParkMaxVisitorsDefault() {
		return parkMaxVisitorsDefault.getValue();
	}

	public SimpleIntegerProperty getParkMaxVisitorsDefaultProperty() {
		return parkMaxVisitorsDefault;
	}

	public void setParkMaxVisitorsDefault(int parkMaxVisitorsDefault) {
		this.parkMaxVisitorsDefault.set(parkMaxVisitorsDefault);
	}

	public int getParkDiffFromMax() {
		return parkDiffFromMax.getValue();
	}

	public SimpleIntegerProperty getParkDiffFromMaxProperty() {
		return parkDiffFromMax;
	}

	public void setParkDiffFromMax(int parkDiffFromMax) {
		this.parkDiffFromMax.set(parkDiffFromMax);
	}

	public int getParkVisitDuration() {
		return parkVisitDuration.getValue();
	}

	public SimpleIntegerProperty getParkVisitDurationProperty() {
		return parkVisitDuration;
	}

	public void setParkVisitDuration(int parkVisitDuration) {
		this.parkVisitDuration.set(parkVisitDuration);
	}

	public int getParkCurrentVisitors() {
		return parkCurrentVisitors.getValue();
	}

	public SimpleIntegerProperty getParkCurrentVisitorsProperty() {
		return parkCurrentVisitors;
	}

	public void setParkCurrentVisitors(int parkCurrentVisitors) {
		this.parkCurrentVisitors.set(parkCurrentVisitors);
	}
}
