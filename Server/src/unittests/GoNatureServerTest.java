package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Employee;
import entity.EntityConstants.EmployeeRole;
import entity.Order;
import entity.Subscriber;
import entity.Visitor;
import entity.VisitorReport;
import message.ServerMessage;
import mysql.IMySQLConnection;
import server.GoNatureServer;

class GoNatureServerTest {
	private boolean validLogInEntry;
	private IMySQLConnection db;
	private GoNatureServer server;
	private Visitor visitor;
	private Subscriber sub;
	private Employee emp;
	class MySQLConnectionStub implements IMySQLConnection{
		@Override
		public Visitor validateVisitor(String id) throws SQLException {
			if(validLogInEntry)
				return visitor;
			return null;
		}

		@Override
		public Subscriber validateSubscriber(String subNum) throws SQLException {
			if(validLogInEntry)
				return sub;
			return null;
		}

		@Override
		public Employee validateEmployee(String[] employeeNumberAndPassword) throws SQLException {
			if(validLogInEntry)
				return emp;
			return null;
		}

		@Override
		public Map<Integer, VisitorReport> getVisitionReport(String namePark) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ServerMessage registerSubscriber(Subscriber subscriber) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object OccasionalcreateOrder(Order order) throws SQLException, NumberFormatException, ParseException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	@BeforeEach
	void setUp() throws Exception {
		db=new MySQLConnectionStub();
		server=new GoNatureServer(5555,db);
		visitor=new Visitor("123456789");
		sub=new Subscriber("1234", "123456789", "Yosi", "Moshe", "0501234567", "jojo@gmail.com", 3, "9000039123021", false);
		emp=new Employee("123", "123456710", "momo", "popo", "jiji@gmail.com",EmployeeRole.REGULAR, "park");
	}
	@AfterEach
	void tearDown() throws Exception {
		server.close();
	}
	/*
	 * logInVisitor when visitor is valid (not logged in,every visitor is considered valid in GoNature)
	 * input= "1234567" id , validLogInEntry=true
	 * expected= the visitor returned
	 */
	@Test
	void logInVisitorValidInput() {
		validLogInEntry=true;
		Object result;
		Object expected=visitor;
		try {
			result=server.loginVisitor("1234567");
			assertTrue(expected.equals(result));
			assertTrue(server.userList.contains(result));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInVisitor when visitor is not valid (not supposed to happen in GoNature but just incase)
	 * input= "1234567" id , validLogInEntry=false
	 * expected= null
	 */
	@Test
	void logInVisitorInvalidInput() {
		validLogInEntry=false;
		Object result;
		Object expected=null;
		try {
			result=server.loginVisitor("1234567");
			assertTrue(result==null);
			assertTrue(!server.userList.contains(result));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInVisitor when visitor is already logged in
	 * input= "1234567" id after visitor is already logged in
	 * expected="logged in"
	 */
	@Test
	void logInVisitorAlreadyLoggedIn() {
		validLogInEntry=true;
		Object result;
		Object expected="logged in";
		try {
			server.loginVisitor("1234567");
			result=server.loginVisitor("1234567");
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInSub when input is valid
	 * input= "1234" subNum , validLogInEntry=true
	 * expected=the subscriber returned
	 */
	@Test
	void logInSubValidInput() {
		validLogInEntry=true;
		Object result;
		Object expected=sub;
		try {
			result=server.loginSubscriber("1234");
			assertTrue(server.userList.contains(result));
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInSub when input is invalid (subscriber not in tables)
	 * input= "1234" subNum , validLogInEntry=false
	 * expected=null
	 */
	@Test
	void logInSubInvalidInput() {
		validLogInEntry=false;
		Object result;
		Object expected=null;
		try {
			result=server.loginSubscriber("1234");
			assertFalse(server.userList.contains(result));
			assertTrue(result==expected);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInSub when subscriber already logged in
	 * input= "1234" subNum , validLogInEntry=true, sub is already logged in
	 * expected="logged in"
	 */
	@Test
	void logInSubValidInputAlreadyLoggedIn() {
		validLogInEntry=true;
		Object result;
		Object expected="logged in";
		try {
			server.loginSubscriber("1234");
			result=server.loginSubscriber("1234");
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
		validLogInEntry=true;
		Object result;
		Object expected=emp;
		try {
			result=server.loginEmployee(new String[] {"1234","12345"});
			assertTrue(server.userList.contains(result));
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInEmployee when the employee is invalid (the combination of employee number+password is not in tables)
	 * input= "1234" empNum, "12345" password , validLogInEntry=false
	 * expected=null
	 */
	@Test
	void logInEmployeeInvalidInput() {
		validLogInEntry=false;
		Object result;
		Object expected=null;
		try {
			result=server.loginEmployee(new String[] {"1234","12345"});
			assertFalse(server.userList.contains(result));
			assertTrue(result==expected);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	/*
	 * logInEmployee when the employee is already logged in
	 * input= "1234" empNum, "12345" password , validLogInEntry=true, employee already logged in
	 * expected="logged in"
	 */
	@Test
	void logInEmployeeValidInputAlreadyLoggedIn() {
		validLogInEntry=true;
		Object result;
		Object expected="logged in";
		try {
			server.loginEmployee(new String[] {"1234","12345"});
			result=server.loginEmployee(new String[] {"1234","12345"});
			assertTrue(result.equals(expected));
		} catch (SQLException e) {
			assertTrue(false);
		}
	}

}
