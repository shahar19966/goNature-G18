package unittests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Employee;
import entity.EntityConstants.EmployeeRole;
import entity.Subscriber;
import entity.Visitor;
import mysql.MySQLConnection;
import server.GoNatureServer;

class GoNatureServerTest {
	private String username;
	private String password;
	private GoNatureServer server;
	private Visitor visitor;
	private Subscriber sub;
	private Employee emp;
	@BeforeAll
	public static void openConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		MySQLConnection.connectToDB();
	}
	@BeforeEach
	void setUp() throws Exception {
		server=new GoNatureServer(5555);
		visitor=new Visitor("123456789");
		sub=new Subscriber("1018", "203432312", "Yosi", "Moshe", "0501234567", "jojo@gmail.com", 3, "9000039123021", false);
		emp=new Employee("000", "000000000", "momo", "popo", "jiji@gmail.com",EmployeeRole.REGULAR, "park");
	}
	@AfterEach
	void tearDown() throws Exception {
		server.close();
	}

	/*
	 * logInVisitor when visitor is valid (not logged in,every visitor is considered valid in GoNature)
	 * input= "123456789" id 
	 * expected= the visitor returned
	 */
	@Test
	void logInVisitorValidInput() {
		username="123456789";
		Object result;
		Object expected=visitor;
		try {
			result=server.loginVisitor(username);
			assertTrue(expected.equals(result));
			assertTrue(server.userList.contains(result));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInVisitor when visitor is not in DB
	 * input= "987654321" id 
	 * expected= the visitor returned
	 */
	@Test
	void logInVisitorNotInDataBase() {
		username="987654321";
		Object result;
		Object expected=new Visitor(username);
		try {
			assertFalse(MySQLConnection.visitorExists(username));
			result=server.loginVisitor(username);
			assertTrue(MySQLConnection.visitorExists(username));
			assertTrue(expected.equals(result));
			assertTrue(server.userList.contains(result));
			MySQLConnection.deleteVisitor(username);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInVisitor when visitor is already logged in
	 * input= "123456789" id after visitor is already logged in
	 * expected="logged in"
	 */
	@Test
	void logInVisitorAlreadyLoggedIn() {
		username="123456789";
		Object result;
		Object expected="logged in";
		try {
			server.loginVisitor(username);
			result=server.loginVisitor(username);
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInSub when input is valid
	 * input= "1018" subNum , validLogInEntry=true
	 * expected=the subscriber returned
	 */
	@Test
	void logInSubValidInput() {
		username="1018";
		Object result;
		Object expected=sub;
		try {
			result=server.loginSubscriber(username);
			assertTrue(server.userList.contains(result));
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInSub when input is invalid (subscriber not in tables)
	 * input= "0123" subNum ,
	 * expected=null
	 */
	@Test
	void logInSubInvalidInput() {
		username="0123";
		Object result;
		Object expected=null;
		try {
			result=server.loginSubscriber(username);
			assertFalse(server.userList.contains(result));
			assertTrue(result==expected);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInSub when subscriber already logged in
	 * input= "1018" subNum , subscriber already logged in
	 * expected="logged in"
	 */
	@Test
	void logInSubValidInputAlreadyLoggedIn() {
		username="1018";
		Object result;
		Object expected="logged in";
		try {
			server.loginSubscriber(username);
			result=server.loginSubscriber(username);
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInEmployee when employee is valid
	 * input= "1234" empNum, "12345" password , validLogInEntry=true
	 * expected=the employee returned
	 */
	@Test
	void logInEmployeeValidInput() {
		username="000";
		password="1234";
		Object result;
		Object expected=emp;
		try {
			result=server.loginEmployee(new String[] {username,password});
			assertTrue(server.userList.contains(result));
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInEmployee when the employee is invalid (the combination of employee number+password is not in tables)
	 * input= "001" empNum, "1234" password
	 * expected=null
	 */
	@Test
	void logInEmployeeInvalidEmployeeNum() {
		username="001";
		password="1234";
		Object result;
		Object expected=null;
		try {
			result=server.loginEmployee(new String[] {username,password});
			assertFalse(server.userList.contains(result));
			assertTrue(result==expected);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInEmployee when the employee is invalid (the combination of employee number+password is not in tables)
	 * input= "000" empNum, "12345" password
	 * expected=null
	 */
	@Test
	void logInEmployeeInvalidPassword() {
		username="000";
		password="12345";
		Object result;
		Object expected=null;
		try {
			result=server.loginEmployee(new String[] {username,password});
			assertFalse(server.userList.contains(result));
			assertTrue(result==expected);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInEmployee when the employee is already logged in
	 * input= "000" empNum, "1234" password, employee already logged in
	 * expected="logged in"
	 */
	@Test
	void logInEmployeeValidInputAlreadyLoggedIn() {
		username="000";
		password="1234";
		Object result;
		Object expected="logged in";
		try {
			server.loginEmployee(new String[] {username,password});
			result=server.loginEmployee(new String[] {username,password});
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

}
