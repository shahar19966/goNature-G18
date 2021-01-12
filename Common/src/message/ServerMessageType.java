package message;
/*
 * ServerMessageType is the type of the message of ServerMessage
 */
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
	GET_DISCOUNT_REQUESTS_FROM_DB,
	VALIDATE_ORDER_ENTRY,
	VALIDATE_ORDER_EXIT,
	REQUESTS_PARAMETERS,
	DEP_MANAGER_GET_DISCOUNT_REQUESTS,
	APPROVE_PARAMETER,
	DECLINE_PARAMETER,
	APPROVE_DISCOUNT,
	DECLINE_DISCOUNT,
	REGISTRATION_SUCCESSED,
	REGISTRATION_FAILED,
	FINAL_APPROVAL_EMAIL_AND_SMS,
	WAITING_LIST_APPROVAL_EMAIL_AND_SMS,
	CANCEL_EMAIL_AND_SMS,
	DISCOUNT_IS_ALREADY_EXIST,
	DISCOUNT_EXIST_BETWEEN_DATES,
	CAN_INSERT_NEW_DISCOUNT,
	GET_PARK,
	WAITING_DISCOUNT

}
