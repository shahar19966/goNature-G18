package unittests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Employee;
import entity.EntityConstants;
import entity.EntityConstants.EmployeeRole;
import entity.Subscriber;
import entity.Visitor;
import entity.VisitorReport;
import mysql.MySQLConnection;
import server.GoNatureServer;

class GoNatureServerTest {
	private String username;
	private String password;
	private GoNatureServer server;
	private Visitor visitor;
	private Subscriber sub;
	private Employee emp;
	private Map<Integer, VisitorReport> visitReportMap = new LinkedHashMap<Integer, VisitorReport>();
	private Map<Integer, VisitorReport> emptyVisitReportMap = new LinkedHashMap<Integer, VisitorReport>();

	private String parkName;
	private VisitorReport visitReport;

	@BeforeAll
	public static void openConnection()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		MySQLConnection.connectToDB();
	}

	@BeforeEach
	void setUp() throws Exception {
		server = new GoNatureServer(5555);
		visitor = new Visitor("123456789");
		sub = new Subscriber("1018", "203432312", "Yosi", "Moshe", "0501234567", "jojo@gmail.com", 3, "9000039123021",
				false);
		emp = new Employee("000", "000000000", "momo", "popo", "jiji@gmail.com", EmployeeRole.REGULAR, "park");
		// visitReportMap = new LinkedHashMap<Integer, VisitorReport>();
		visitReport = new VisitorReport(8);
		visitReport.setAvgRegular(2.0000);
		visitReport.setAvgGuid(7.0000);
		visitReportMap.put(8, visitReport);
		///////////////////////////////////
		visitReport = new VisitorReport(9);
		visitReport.setAvgRegular(2.5000);
		visitReport.setAvgSubscriber(1.0000);
		visitReportMap.put(9, visitReport);
		///////////////////////////////////
		visitReport = new VisitorReport(10);
		visitReport.setAvgRegular(1.3333);
		visitReport.setAvgGuid(3.0000);
		visitReport.setAvgSubscriber(3.2500);
		visitReportMap.put(10, visitReport);
		///////////////////////////////////
		visitReport = new VisitorReport(11);
		visitReport.setAvgRegular(4.3333);
		visitReportMap.put(11, visitReport);
		///////////////////////////////////
		visitReport = new VisitorReport(12);
		visitReport.setAvgRegular(0.5000);
		visitReportMap.put(12, visitReport);
		///////////////////////////////////
		visitReportMap.put(13, new VisitorReport(13));
		///////////////////////////////////
		visitReportMap.put(14, new VisitorReport(14));
		///////////////////////////////////
		visitReport = new VisitorReport(15);
		visitReport.setAvgRegular(4.5000);
		visitReportMap.put(15, visitReport);
		///////////////////////////////////
		visitReportMap.put(16, new VisitorReport(16));
		///////////////////////////////////
		visitReport = new VisitorReport(17);
		visitReport.setAvgSubscriber(4.0000);
		visitReportMap.put(17, visitReport);
		////////////////////////////////////
		for (int i = EntityConstants.PARK_OPEN; i <= EntityConstants.PARK_CLOSED; i++) {
			emptyVisitReportMap.put(i, new VisitorReport(i));
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		server.close();
	}

	/*
	 * logInVisitor when visitor is valid (not logged in,every visitor is considered
	 * valid in GoNature) input= "123456789" id expected= the visitor returned
	 */
	@Test
	void logInVisitorValidInput() {
		username = "123456789";
		Object result;
		Object expected = visitor;
		try {
			result = server.loginVisitor(username);
			assertTrue(expected.equals(result));
			assertTrue(server.userList.contains(result));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/*
	 * logInVisitor when visitor is not in DB input= "987654321" id expected= the
	 * visitor returned
	 */
	@Test
	void logInVisitorNotInDataBase() {
		username = "987654321";
		Object result;
		Object expected = new Visitor(username);
		try {
			assertFalse(MySQLConnection.visitorExists(username));
			result = server.loginVisitor(username);
			assertTrue(MySQLConnection.visitorExists(username));
			assertTrue(expected.equals(result));
			assertTrue(server.userList.contains(result));
			MySQLConnection.deleteVisitor(username);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/*
	 * logInVisitor when visitor is already logged in input= "123456789" id after
	 * visitor is already logged in expected="logged in"
	 */
	@Test
	void logInVisitorAlreadyLoggedIn() {
		username = "123456789";
		Object result;
		Object expected = "logged in";
		try {
			server.loginVisitor(username);
			result = server.loginVisitor(username);
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/*
	 * logInSub when input is valid input= "1018" subNum , validLogInEntry=true
	 * expected=the subscriber returned
	 */
	@Test
	void logInSubValidInput() {
		username = "1018";
		Object result;
		Object expected = sub;
		try {
			result = server.loginSubscriber(username);
			assertTrue(server.userList.contains(result));
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/*
	 * logInSub when input is invalid (subscriber not in tables) input= "0123"
	 * subNum , expected=null
	 */
	@Test
	void logInSubInvalidInput() {
		username = "0123";
		Object result;
		Object expected = null;
		try {
			result = server.loginSubscriber(username);
			assertFalse(server.userList.contains(result));
			assertTrue(result == expected);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/*
	 * logInSub when subscriber already logged in input= "1018" subNum , subscriber
	 * already logged in expected="logged in"
	 */
	@Test
	void logInSubValidInputAlreadyLoggedIn() {
		username = "1018";
		Object result;
		Object expected = "logged in";
		try {
			server.loginSubscriber(username);
			result = server.loginSubscriber(username);
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/*
	 * logInEmployee when employee is valid input= "1234" empNum, "12345" password ,
	 * validLogInEntry=true expected=the employee returned
	 */
	@Test
	void logInEmployeeValidInput() {
		username = "000";
		password = "1234";
		Object result;
		Object expected = emp;
		try {
			result = server.loginEmployee(new String[] { username, password });
			assertTrue(server.userList.contains(result));
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/*
	 * logInEmployee when the employee is invalid (the combination of employee
	 * number+password is not in tables) input= "001" empNum, "1234" password
	 * expected=null
	 */
	@Test
	void logInEmployeeInvalidEmployeeNum() {
		username = "001";
		password = "1234";
		Object result;
		Object expected = null;
		try {
			result = server.loginEmployee(new String[] { username, password });
			assertFalse(server.userList.contains(result));
			assertTrue(result == expected);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/*
	 * logInEmployee when the employee is invalid (the combination of employee
	 * number+password is not in tables) input= "000" empNum, "12345" password
	 * expected=null
	 */
	@Test
	void logInEmployeeInvalidPassword() {
		username = "000";
		password = "12345";
		Object result;
		Object expected = null;
		try {
			result = server.loginEmployee(new String[] { username, password });
			assertFalse(server.userList.contains(result));
			assertTrue(result == expected);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/**
	 * logInEmployee when the employee is already logged in input= "000" empNum,
	 * "1234" password, employee already logged in expected="logged in"
	 */
	@Test
	void logInEmployeeValidInputAlreadyLoggedIn() {
		username = "000";
		password = "1234";
		Object result;
		Object expected = "logged in";
		try {
			server.loginEmployee(new String[] { username, password });
			result = server.loginEmployee(new String[] { username, password });
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/**
	 * parkMenegerVisitReport test when the name park is existing. excepted:
	 * Map<Integer, VisitorReport> with the values from data base. result:
	 * visitReportMap
	 */
	@Test
	void parkMenegerVisitReportExistingParkTest() {
		parkName = "1";
		Map<Integer, VisitorReport> result;
		Map<Integer, VisitorReport> expected = visitReportMap;
		try {
			result = (Map<Integer, VisitorReport>) server.parkMenegerVisitReport(parkName);
			for (VisitorReport vrExcepted : expected.values()) {
				// for (VisitorReport vrResult : result.values()) {
				if (!(vrExcepted.equals(result.get(vrExcepted.time())))) {
					assertFalse(true);
				}
			}
			// }
			assertTrue(true);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/**
	 * parkMenegerVisitReport test when the name park is not existing. excepted:
	 * MySQLException result: visitReportMap
	 */
	@Test
	void parkMenegerVisitReportNotExistingParkTest() {
		parkName = "5";
		Map<Integer, VisitorReport> result;
		Map<Integer, VisitorReport> expected = emptyVisitReportMap;
		try {
			result = (Map<Integer, VisitorReport>) server.parkMenegerVisitReport(parkName);
			for (VisitorReport vrExcepted : expected.values()) {
				if (!(vrExcepted.equals(result.get(vrExcepted.time())))) {
					assertFalse(true);
				}
			}
			// }
			assertTrue(true);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

	/**
	 * parkMenegerVisitReport test when the name park is null. excepted:
	 * MySQLException result: visitReportMap
	 */
	@Test
	void parkMenegerVisitReportParkNameIsNullTest() {
		parkName = null;
		Map<Integer, VisitorReport> result;
		Map<Integer, VisitorReport> expected = emptyVisitReportMap;
		try {
			result = (Map<Integer, VisitorReport>) server.parkMenegerVisitReport(parkName);
			for (VisitorReport vrExcepted : expected.values()) {
				if (!(vrExcepted.equals(result.get(vrExcepted.time())))) {
					assertFalse(true);
				}
			}
			// }
			assertTrue(true);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

}
