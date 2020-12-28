package client;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;

import entity.Employee;
import entity.Order;
import entity.ParameterUpdate;
import entity.ParkDiscount;
import gui.GUIControl;
import gui.GuiButton;
import gui.ClientConstants.AlertType;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;
import ocsf.client.AbstractClient;

/*
 * This class controls the communication between the client and the server using handleMessageFromClientUI and handleMessageFromClient
 */
public class GoNatureClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	public static boolean awaitResponse = false;
	private GUIControl guiControl;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public GoNatureClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		guiControl = GUIControl.getInstance();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 * @throws Exception
	 */
	public void handleMessageFromServer(Object msg) {
		System.out.println("--> handleMessageFromServer");
		if (msg instanceof ServerMessage) {
			ServerMessage serverMsg = (ServerMessage) msg;
			switch (serverMsg.getType()) {
			case LOGIN:
				guiControl.setServerMsg(serverMsg);
				break;
			case SERVER_ERROR:
				if (serverMsg.getMessage() == null) {
					GUIControl.popUpError("Server encountered an error!");
					guiControl.setServerMsg(serverMsg);
				} else {
					GUIControl.popUpErrorExitOnClick((String) serverMsg.getMessage());
				}
				break;
			case PARK_LIST:
				guiControl.setServerMsg(serverMsg);
				break;
			case LOGOUT_SUCCESS:
				GUIControl.popUpMessage("Logged out");
				break;
			case PARK_VISITATION_REPORT:
				guiControl.setServerMsg(serverMsg);
				break;
			case ORDER_SUCCESS:
				guiControl.setServerMsg(serverMsg);

				break;
			case ORDER_FAILURE:
				guiControl.setServerMsg(null);
				break;
			case WAITING_LIST:
				guiControl.setServerMsg(serverMsg);
				break;
			case PARK_INCOME_REPORT:
				guiControl.setServerMsg(serverMsg);
				break;
			case AVAILABLE_DATES:
				guiControl.setServerMsg(serverMsg);
				break;
			case PARAMETER_UPDATE:
				GUIControl.popUpMessage("Parameter Update Succeeded",
						((ParameterUpdate) serverMsg.getMessage()).toString());
				break;
			case DISCOUNT_REQUEST:
				GUIControl.popUpMessage("Request sent to department manager",
						"Request successfully sent to department manager");
				break;

			case OCCASIONAL_ORDER:
				guiControl.setServerMsg(serverMsg);
				break;
			case PARK_CAPACITY_REPORT:
				guiControl.setServerMsg(serverMsg);
				break;
			case DEPARTMENT_CANCELLATION_REPORT:
          guiControl.setServerMsg(serverMsg);
				break;
			case GET_ORDERS_BY_ID:
				guiControl.setServerMsg(serverMsg);
				break;
			case DEPARTMENT_VISITATION_REPORT:
				guiControl.setServerMsg(serverMsg);
				break;
			case CANCEL_ORDER:
				guiControl.setServerMsg(serverMsg);
				break;

			case GET_DISCOUNT_REQUESTS_FROM_DB:
				guiControl.setServerMsg(serverMsg);
				break;
			case VALIDATE_ORDER_ENTRY:
				guiControl.setServerMsg(serverMsg);
				break;
			case VALIDATE_ORDER_EXIT:
				guiControl.setServerMsg(serverMsg);
				break;
			case REQUESTS_PARAMETERS:
				guiControl.setServerMsg(serverMsg);
				break;
			case DEP_MANAGER_GET_DISCOUNT_REQUESTS:
				guiControl.setServerMsg(serverMsg);
				break;
			case APPROVE_PARAMETER:
				guiControl.setServerMsg(serverMsg);
				break;
			case DECLINE_PARAMETER:
				guiControl.setServerMsg(serverMsg);
				break;
			case APPROVE_DISCOUNT:
				guiControl.setServerMsg(serverMsg);
				break;
			case DECLINE_DISCOUNT:
				guiControl.setServerMsg(serverMsg);
				break;

			}
		}
		awaitResponse = false;
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(Object message) {
		try {
			openConnection();// in order to send more than one message
			if (message instanceof ClientMessage) {
				if (((ClientMessage) message).getType().equals(ClientMessageType.DISCONNECTED)) {
					sendToServer(message);
					return;
				}
			}
			awaitResponse = true;
			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
