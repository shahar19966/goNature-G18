package gui;
/*
 *this class holds the relative paths of fxml files
 */
public class ClientConstants {
	public static final int MAX_PEOPLE=15;
	public enum Screens{
		LOGIN_PAGE("/gui/LoginPage.fxml"),
		VISITOR_MAIN_PAGE("/gui/VisitorHomePage.fxml"),
		SUBSCRIBER_MAIN_PAGE("/gui/SubscriberHomePage.fxml"),
		GUIDE_MAIN_PAGE("/gui/GuideHomePage.fxml"),
		EMPLOYEE_MAIN_PAGE("/gui/EmployeeHomePage.fxml"),
		PARK_MANAGER_REPOTRS("/gui/ParkManagerReports.fxml"),
		DEPARTMENT_MANAGER_REPOTRS("/gui/DepartmentManagerProduceReportsPage.fxml"),
		PARK_MNG_VISIT_REPORT("/gui/ParkManagerVisitaoinReport.fxml"),
		PARK_MANAGER_PATAMETERS_UPDATE("/gui/ParameterUpdatePage.fxml"),
		SUB_AND_VISITOR_ORDER_PAGE("/gui/OrderPane.fxml"),
		VISITATION_TIME_UPDATE("/gui/VisitationTimeUpdate.fxml"),
		CAPACITY_UPDATE("/gui/CapacityUpdate.fxml"),
		DIFFERENCE_PARAMETER_UPDATE("/gui/DifferenceParameterUpdate.fxml"),
		REGISTRATION_PAGE("/gui/RegisterSubscriberAndGuide.fxml");

		
		public final String path;
		private Screens(String path) {
			this.path=path;
		}
		@Override
		public String toString() {
			return path;
		}
	}
}
