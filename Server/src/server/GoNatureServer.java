// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.ServerMain;
import entity.Employee;
import entity.Order;
import entity.ParameterUpdate;
import message.ClientMessage;
import message.ServerMessage;
import message.ServerMessageType;
import mysql.MySQLConnection;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
/*
 * GoNatureServer is responsible to handling client messages and sending
 * messages to client
 */
public class GoNatureServer extends AbstractServer {
	// Class variables *****************
	private MySQLConnection goNatureDB;
	private ArrayList<Object> userList;

	// Constructors ******************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	public GoNatureServer(int port) {
		super(port);
		userList = new ArrayList<>();
	}

	// Instance methods ****************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Object returnVal = null;
		ServerMessageType type = null;
		try {
			if (msg instanceof ClientMessage) {
				ClientMessage clientMsg = (ClientMessage) msg;
				switch (clientMsg.getType()) {
				case DISCONNECTED:
					if (clientMsg.getMessage() != null)
						userList.remove(clientMsg.getMessage());
					ServerMain.guiController.disconnectClient(client);
					break;
				case LOGOUT:
					if (clientMsg.getMessage() != null)
						userList.remove(clientMsg.getMessage());
					returnVal = null;
					type = ServerMessageType.LOGOUT_SUCCESS;
					break;
				case LOGIN_VISITOR:
					returnVal = MySQLConnection.validateVisitor((String) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal)) // user already logged in
						returnVal = "logged in";
					else if (returnVal != null) // user isn't already logged in and was found in the database
						userList.add(returnVal);
					break;
				case LOGIN_SUBSCRIBER:
					returnVal = MySQLConnection.validateSubscriber((String) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal))
						returnVal = "logged in";
					else if (returnVal != null)
						userList.add(returnVal);
					break;
				case LOGIN_EMPLOYEE:
					returnVal = MySQLConnection.validateEmployee((String[]) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal))
						returnVal = "logged in";
					else if (returnVal != null)
						userList.add(returnVal);
					break;
				case GET_PARKS:
					returnVal = MySQLConnection.getParks();
					type = ServerMessageType.PARK_LIST;
				case CONNECTION:
					break;
				case VISITOR_REPORT:
					returnVal = MySQLConnection.getVisitorReport();
					type = ServerMessageType.PARK_VISITATION_REPORT;
					break;
				case ORDER:
					returnVal = MySQLConnection.createOrder((Order)clientMsg.getMessage());
					if(returnVal==null)
						type=ServerMessageType.ORDER_FAILURE;
					else
						type=ServerMessageType.ORDER_SUCCESS;
					break;
				case PARAMETER_UPDATE://liron
					returnVal = MySQLConnection.createParameterUpdate((ParameterUpdate)clientMsg.getMessage());
					type=ServerMessageType.PARAMETER_UPDATE;
					break;
				}
			}
		} catch (Exception e) {
			try {
				client.sendToClient(new ServerMessage(ServerMessageType.SERVER_ERROR, null));
				e.printStackTrace();
			} catch (IOException e1) {
			}
		}

		System.out.println("Message received: " + msg + " from " + client);
		try {
			client.sendToClient(new ServerMessage(type, returnVal));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		ServerMain.guiController.connectClient(client);
	}

	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		ServerMain.guiController.disconnectClient(client);
	}
}
=======
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.ServerMain;
import entity.Employee;
import entity.Order;
import message.ClientMessage;
import message.ServerMessage;
import message.ServerMessageType;
import mysql.MySQLConnection;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
/*
 * GoNatureServer is responsible to handling client messages and sending
 * messages to client
 */
public class GoNatureServer extends AbstractServer {
	// Class variables *****************
	private MySQLConnection goNatureDB;
	private ArrayList<Object> userList;

	// Constructors ******************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	public GoNatureServer(int port) {
		super(port);
		userList = new ArrayList<>();
	}

	// Instance methods ****************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Object returnVal = null;
		ServerMessageType type = null;
		try {
			if (msg instanceof ClientMessage) {
				ClientMessage clientMsg = (ClientMessage) msg;
				switch (clientMsg.getType()) {
				case DISCONNECTED:
					if (clientMsg.getMessage() != null)
						userList.remove(clientMsg.getMessage());
					ServerMain.guiController.disconnectClient(client);
					break;
				case LOGOUT:
					if (clientMsg.getMessage() != null)
						userList.remove(clientMsg.getMessage());
					returnVal = null;
					type = ServerMessageType.LOGOUT_SUCCESS;
					break;
				case LOGIN_VISITOR:
					returnVal = MySQLConnection.validateVisitor((String) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal)) // user already logged in
						returnVal = "logged in";
					else if (returnVal != null) // user isn't already logged in and was found in the database
						userList.add(returnVal);
					break;
				case LOGIN_SUBSCRIBER:
					returnVal = MySQLConnection.validateSubscriber((String) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal))
						returnVal = "logged in";
					else if (returnVal != null)
						userList.add(returnVal);
					break;
				case LOGIN_EMPLOYEE:
					returnVal = MySQLConnection.validateEmployee((String[]) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal))
						returnVal = "logged in";
					else if (returnVal != null)
						userList.add(returnVal);
					break;
				case GET_PARKS:
					returnVal = MySQLConnection.getParks();
					type = ServerMessageType.PARK_LIST;
				case CONNECTION:
					break;
				case VISITOR_REPORT:
					returnVal = MySQLConnection.getVisitorReport();
					type = ServerMessageType.PARK_VISITATION_REPORT;
					break;
				case ORDER:
					returnVal = MySQLConnection.createOrder((Order) clientMsg.getMessage());
					if (returnVal == null)
						type = ServerMessageType.ORDER_FAILURE;
					else
						type = ServerMessageType.ORDER_SUCCESS;
					break;
				case WAITING_LIST:
					returnVal = MySQLConnection.enterWaitingist((Order) clientMsg.getMessage());
					type = ServerMessageType.WAITING_LIST;
					break;
				case INCOME_REPORT:
					returnVal = MySQLConnection.getIncomeReport((String) (clientMsg.getMessage()));
					type = ServerMessageType.PARK_INCOME_REPORT;
					break;
				}
			}
		} catch (Exception e) {
			try {
				client.sendToClient(new ServerMessage(ServerMessageType.SERVER_ERROR, null));
				e.printStackTrace();
			} catch (IOException e1) {
			}
		}

		System.out.println("Message received: " + msg + " from " + client);
		try {
			client.sendToClient(new ServerMessage(type, returnVal));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		ServerMain.guiController.connectClient(client);
	}

	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		ServerMain.guiController.disconnectClient(client);
	}
}
//End of EchoServer class