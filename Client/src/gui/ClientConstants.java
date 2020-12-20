package gui;

public class ClientConstants {
	public enum Screens{
		LOGIN_PAGE("/gui/LoginPage.fxml"),
		VISITOR_MAIN_PAGE("/gui/VisitorHomePage.fxml"),
		SUBSCRIBER_MAIN_PAGE("/gui/SubscriberHomePage.fxml"),
		GUIDE_MAIN_PAGE("/gui/GuideHomePage.fxml"),
		EMPLOYEE_MAIN_PAGE("/gui/EmployeeHomePage.fxml"),
		PARK_MANAGER_REPOTRS("/gui/ParkManagerReportsController"),
		DEPARTMENT_MANAGER_REPOTRS("/gui/ParkManagerReportsController");
		
		
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
