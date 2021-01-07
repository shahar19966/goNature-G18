package client;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import entity.Employee;
import entity.Order;
import entity.ParameterUpdate;
import entity.ParkDiscount;
import entity.Subscriber;
import entity.Visitor;
import gui.GUIControl;
import gui.GuiButton;
import gui.ClientConstants.AlertType;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;
import ocsf.client.AbstractClient;

/**
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
		System.out.print("--> handleMessageFromServer : ");
		if (msg instanceof ServerMessage) {
			ServerMessage serverMsg = (ServerMessage) msg;
			System.out.println(serverMsg.getType());
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
			case LOGOUT_SUCCESS:
				GUIControl.popUpMessage("Logged out");
				break;
			case ORDER_FAILURE:
				guiControl.setServerMsg(null);
				break;
			case PARAMETER_UPDATE:
				GUIControl.popUpMessage("Parameter Update Sent",
						((ParameterUpdate) serverMsg.getMessage()).toString());
				break;
			case DISCOUNT_REQUEST:
				GUIControl.popUpMessage("Request sent to department manager",
						"Request successfully sent to department manager");
				break;
			case FINAL_APPROVAL_EMAIL_AND_SMS:
				List<Order> orderToApproveList  = (List<Order>) serverMsg.getMessage();
				if (guiControl.getUser() instanceof Subscriber) {
					Subscriber sub = (Subscriber) guiControl.getUser();
					for(Order order:orderToApproveList)
						if(sub.getID().equals(order.getId()))
						GUIControl.popUpMessage("SMS AND EMAIL SIMULATION-Waiting Approval",
								"Your order is waiting for approval,please go "
										+ "to Order Tracking and approve your order\n Order details:\n"
										+ order.toString()+"\nSent to email: "+order.getEmail()+"\nSent to Phone number: "+order.getPhone());
				} else if (guiControl.getUser() instanceof Visitor) {
					Visitor visitor = (Visitor) guiControl.getUser();
					for(Order order:orderToApproveList)
						if(visitor.getId().equals(order.getId()))
						GUIControl.popUpMessage("SMS AND EMAIL SIMULATION-Waiting Approval",
								"Your order is waiting for approval,please go "
										+ "to Order Tracking and approve your order\n Order details:\n"
										+ order.toString()+"\nSent to email: "+order.getEmail()+"\nSent to Phone number: "+order.getPhone());
				}
				return; // this message should not interfere with the client in case he's waiting for
						// server's response
			case WAITING_LIST_APPROVAL_EMAIL_AND_SMS:
				List<Order> orderToApproveFromWaitingList  = (List<Order>) serverMsg.getMessage();
				if (guiControl.getUser() instanceof Subscriber) {
					Subscriber sub = (Subscriber) guiControl.getUser();
					for(Order order:orderToApproveFromWaitingList)
						if(sub.getID().equals(order.getId()))
						GUIControl.popUpMessage("SMS AND EMAIL SIMULATION-Waiting Approval From Waiting List",
								"Your order is waiting for approval from waiting list,please go "
										+ "to Order Tracking and approve your order\n Order details:\n"
										+ order.toString()+"\nSent to email: "+order.getEmail()+"\nSent to Phone number: "+order.getPhone());
				} else if (guiControl.getUser() instanceof Visitor) {
					Visitor visitor = (Visitor) guiControl.getUser();
					for(Order order:orderToApproveFromWaitingList)
						if(visitor.getId().equals(order.getId()))
						GUIControl.popUpMessage("SMS AND EMAIL SIMULATION-Waiting Approval From Waiting List",
								"Your order is waiting for approval from waiting list,please go "
										+ "to Order Tracking and approve your order\n Order details:\n"
										+ order.toString()+"\nSent to email: "+order.getEmail()+"\nSent to Phone number: "+order.getPhone());
				}
				return;
			case CANCEL_EMAIL_AND_SMS:
				List<Order> orderList = (List<Order>) serverMsg.getMessage();
				if (guiControl.getUser() instanceof Subscriber) {
					Subscriber sub = (Subscriber) guiControl.getUser();
					for(Order order:orderList)
						if(sub.getID().equals(order.getId()))
						GUIControl.popUpMessage("SMS AND EMAIL SIMULATION-Cancelled Order",
								"You haven't approved your order in the given time space, "
										+ "therefore it was cancelled.\n Order details:\n" +order.toString()
										+"\nSent to email: "+order.getEmail()+"\nSent to Phone number: "+order.getPhone());
				} else if (guiControl.getUser() instanceof Visitor) {
					Visitor visitor = (Visitor) guiControl.getUser();
					for(Order order:orderList)
						if(visitor.getId().equals(order.getId()))
						GUIControl.popUpMessage("SMS AND EMAIL SIMULATION-Cancelled Order",
								"You haven't approved your order in the given time space, "
										+ "therefore it was cancelled.\n Order details:\n" + order.toString()
										+"\nSent to email: "+order.getEmail()+"\nSent to Phone number: "+order.getPhone());
				}
				return; // this message should not interfere with the client in case he's waiting for
						// server's response
			default:
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
