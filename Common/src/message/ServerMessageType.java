package message;

public enum ServerMessageType {
	LOGIN,
	LOGOUT_SUCCESS,
	AVAILABLE_DATES,
	PARK_LIST,
	PARK_VISITATION_REPORT,
	PARK_CAPACITY_REPORT,
	PARK_INCOME_REPORT,
	DEPARTMENT_VISITATION_REPORT,
	DEPARTMENT_CANCELLATION_REPORT,
	SERVER_ERROR,
	ORDER_SUCCESS,
	ORDER_FAILURE,
	PARAMETER_UPDATE,
	WAITING_LIST,
	DISCOUNT_REQUEST,
	OCCASIONAL_ORDER,
	GET_ORDERS_BY_ID,
	CANCEL_ORDER,
	APPROVE_ORDER,
	ACTIVATE_ORDER_FROM_WATING_LIST,
	GET_DISCOUNT_REQUESTS_FRON_DB,
	VALIDATE_ORDER_ENTRY,
	VALIDATE_ORDER_EXIT,
	REQUESTS_PARAMETERS


}
