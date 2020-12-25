package entity;

public class EntityConstants{
	public static final int PARK_OPEN=8;
	public static final int PARK_CLOSED=17;
	public static final int TICKET_PRICE=50;
	public enum ParkParameter {
		CAPACITY,
		DIFFERENCE,
		DURATION 
	}
	public enum EmployeeRole{
		PARK_MANAGER,
		DEP_MANAGER,
		SERVICE,
		REGULAR
	}
	public enum OrderStatus{
		WAITING,
		PENDING_APPROVAL_FROM_WAITING_LIST,
		ACTIVE,
		PENDING_FINAL_APPROVAL,
		CANCELLED,
		DONE,
		APPROVED
	}
	public enum OrderType{
		REGULAR,
		SUBSCRIBER,
		GUIDE
	}
	
	
}

