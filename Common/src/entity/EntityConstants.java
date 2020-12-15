package entity;

public class EntityConstants{
	public enum ParkParameter {
		CAPACITY,
		DIFFERENCE,
		DURATION 
	}
	public enum EmployeeRole{
		PARK_MANAGER,
		DEP_MANAGER,
		SERIVCE,
		REGULAR
	}
	public enum OrderStatus{
		WAITING,
		PENDING_APPROVAL,
		ACTIVE,
		CANCELLED,
		DONE
	}
	public enum OrderType{
		REGULAR,
		SUBSCRIBER,
		GUIDE
	}
}

