package entity;

public class Parks {
	private String parkName;
	private int maxVisitors;
	private int diffFromMax;
	private int visitDuration;
	private int currentVisitors;

	public Parks(String parkName, int maxVisitors, int diffFromMax, int visitDuration,
			int currentVisitors) {
		this.parkName = parkName;
		this.maxVisitors = maxVisitors;
		this.diffFromMax = diffFromMax;
		this.visitDuration =visitDuration;
		this.currentVisitors = currentVisitors;
	}

	public String getParkName() {
		return parkName;
	}



	public void setParkName(String parkName) {
		this.parkName=parkName;
	}

	public int getParkMaxVisitorsDefault() {
		return maxVisitors;
	}


	public void setParkMaxVisitorsDefault(int maxVisitors) {
		this.maxVisitors=maxVisitors;
	}

	public int getParkDiffFromMax() {
		return diffFromMax;
	}

	public void setParkDiffFromMax(int diffFromMax) {
		this.diffFromMax=diffFromMax;
	}

	public int getParkVisitDuration() {
		return visitDuration;
	}

	public void setParkVisitDuration(int visitDuration) {
		this.visitDuration=visitDuration;
	}

	public int getParkCurrentVisitors() {
		return currentVisitors;
	}

	public void setParkCurrentVisitors(int currentVisitors) {
		this.currentVisitors=currentVisitors;
	}
}
