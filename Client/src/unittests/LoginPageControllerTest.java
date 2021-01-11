package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.IClientServerCommunication;
import gui.LoginPageController;
import message.ServerMessage;

class LoginPageControllerTest {
	LoginPageController loginPageController;
	IClientServerCommunication clientServerCommunicationStub;
	Object returnValFromServer;
	String id;
	String pass;
	boolean checkID;
	boolean checkSubNum;
	boolean checkEmployee;
	class ClientServerCommunicationStub implements IClientServerCommunication{

		@Override
		public void popUpError(String msg) {
		}

		@Override
		public void sendToServer(Object msg) {
		}

		@Override
		public ServerMessage getServerMsg() {
			return new ServerMessage(null,returnValFromServer);
		}

		@Override
		public void setUser(Object user) {
		}
		
	}

	@BeforeEach
	void setUp() throws Exception {
		loginPageController=new LoginPageController();
		clientServerCommunicationStub=new ClientServerCommunicationStub();
		loginPageController.setClientServerCommunication(clientServerCommunicationStub);
	}
	/*
	 * validateLogin for valid input ID
	 * input:
	 * returnValFromServer="" to indicate server found the user
		id="123456789"
		pass="1234"
	 * expected= true
	 */
	@Test
	void validateLoginIDValid() {
		returnValFromServer="";
		id="123456789";
		pass="1234";
		checkID=true;
		checkSubNum=checkEmployee=false;
		boolean expected=true;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validateLogin for an ID that is too short (less than 9)
	 * input:
	 * id="12345678"
		pass="1234"
	 * expected=false
	 */
	@Test
	void validateLoginIDTooShort() {
		id="12345678";
		pass="1234";
		checkID=true;
		checkSubNum=checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for ID that has letters
	 * input:
	 * id="1234a5678"
		pass="1234"
	 * expected=false
	 */
	@Test
	void validateLoginIDHasLetters() {
		id="1234a5678";
		pass="1234";
		checkID=true;
		checkSubNum=checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for ID that is empty
	 * input:
	 * 	id=""
		pass="1234"
	 * expected=false
	 */
	@Test
	void validateLoginIDEmpty() {
		id="";
		pass="1234";
		checkID=true;
		checkSubNum=checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for ID that was not found in tables
	 * input:
	 * 	returnValFromServer=null  - to indicate server didn't find the user
		id="123456789"
		pass="1234"
	 * expected=false
	 */
	@Test
	void validateLoginIDNotFound() {
		returnValFromServer=null;
		id="123456789";
		pass="1234";
		checkID=true;
		checkSubNum=checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for ID that is already logged in
	 * input:
	 * 	returnValFromServer="logged in" - to indicate user already logged in
		id="123456789";
		pass="1234";
	 * expected= false
	 */
	@Test
	void validateLoginIDLoggedIn() {
		returnValFromServer="logged in";
		id="123456789";
		pass="1234";
		checkID=true;
		checkSubNum=checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for valid sub number
	 * input:
	 * 	returnValFromServer="" - to indicate server found user
		id="12345";
		pass="1234";
	 * expected=true
	 */
	@Test
	void validateLoginSubNumValid() {
		returnValFromServer="";
		id="12345";
		pass="1234";
		checkID=false;
		checkSubNum=true;
		checkEmployee=false;
		boolean expected=true;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for sub number that has letters
	 * input:
	 * 	id="1234a5"
		pass="1234";
	 * expected=false
	 */
	@Test
	void validateLoginSubNumHasLetters() {
		id="1234a5";
		pass="1234";
		checkID=false;
		checkSubNum=true;
		checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for sub number that is empty
	 * input:
	 * 	id=""
		pass="1234"
	 * expected=false
	 */
	@Test
	void validateLoginSubNumEmpty() {
		id="";
		pass="1234";
		checkID=false;
		checkSubNum=true;
		checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for sub number that was not found in tables
	 * input:
	 * 	returnValFromServer=null - to indicate server didn't find user
		id="123456789";
		pass="1234";
	 * expected=false
	 */
	@Test
	void validateLoginSubNumNotFound() {
		returnValFromServer=null;
		id="123456789";
		pass="1234";
		checkID=false;
		checkSubNum=true;
		checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for sub number that is already logged in
	 * input:
	 * 	returnValFromServer="logged in" - to indicate user already logged in
		id="123456789";
		pass="1234";
	 * expected= false
	 */
	@Test
	void validateLoginSubNumLoggedIn() {
		returnValFromServer="logged in";
		id="123456789";
		pass="1234";
		checkID=false;
		checkSubNum=true;
		checkEmployee=false;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for valid employee
	 * 	returnValFromServer="" - to indicate employee was found in server
		id="123";
		pass="1234";
	 * expected=true
	 */
	@Test
	void validateLoginEmployeeValid() {
		returnValFromServer="";
		id="123";
		pass="1234";
		checkID=false;
		checkSubNum=false;
		checkEmployee=true;
		boolean expected=true;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for employee number that has letters
	 * input:
	 * 	id="1234a5";
		pass="1234";
	 * expected=false
	 */
	@Test
	void validateLoginEmployeeNumHasLetters() {
		id="1234a5";
		pass="1234";
		checkID=false;
		checkSubNum=false;
		checkEmployee=true;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for employee number that is empty
	 * input:
	 * 	id="";
		pass="1234";
	 * expected=false
	 */
	@Test
	void validateLoginEmployeeNumEmpty() {
		id="";
		pass="1234";
		checkID=false;
		checkSubNum=false;
		checkEmployee=true;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for employee password that is empty
	 * input:
	 * 	id="1234";
		pass="";
	 * expected=false
	 */
	@Test
	void validateLoginEmployeePasswordEmpty() {
		id="1234";
		pass="";
		checkID=false;
		checkSubNum=false;
		checkEmployee=true;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for employee that was not found in tables (wrong pass or wrong employee num)
	 * input:
	 * 	returnValFromServer=null - to indicate server didn't find employee
		id="123456789";
		pass="1234";
	 * expected=false
	 */
	@Test
	void validateLoginEmployeeNotFound() {
		returnValFromServer=null;
		id="123456789";
		pass="1234";
		checkID=false;
		checkSubNum=false;
		checkEmployee=true;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}
	/*
	 * validate login for employee that is already logged in
	 * input:
	 * 	returnValFromServer="logged in" - to indicate employee already logged in
		id="123456789";
		pass="1234";
	 * expected= false
	 */
	@Test
	void validateLoginEmployeeLoggedIn() {
		returnValFromServer="logged in";
		id="123456789";
		pass="1234";
		checkID=false;
		checkSubNum=false;
		checkEmployee=true;
		boolean expected=false;
		boolean result=loginPageController.validateLogin(id, pass, checkID, checkSubNum, checkEmployee);
		assertEquals(result,expected);
	}

}
