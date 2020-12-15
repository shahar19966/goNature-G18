//// This file contains material supporting section 3.7 of the textbook:
//// "Object Oriented Software Engineering" and is issued under the open-source
//// license found at www.lloseng.com 
//package application;
//
//import java.io.IOException;
//
//import gui.ServerScreenController;
//import mysql.MySQLConnection;
//import ocsf.server.AbstractServer;
//import ocsf.server.ConnectionToClient;
//
///**
// * This class overrides some of the methods in the abstract superclass in order
// * to give more functionality to the server.
// *
// * @author Dr Timothy C. Lethbridge
// * @author Dr Robert Lagani&egrave;re
// * @author Fran&ccedil;ois B&eacute;langer
// * @author Paul Holden
// * @version July 2000
// */
//
//public class GoNatureServer extends AbstractServer {
//	// Class variables *****************
//
//	/**
//	 * The default port to listen on.
//	 */
//	// final public static int DEFAULT_PORT = 5555;
//
//	// Constructors ******************
//
//	/**
//	 * Constructs an instance of the echo server.
//	 *
//	 * @param port The port number to connect on.
//	 * 
//	 */
//	private MySQLConnection goNatureDB;
//
//	public GoNatureServer(int port) {
//		super(port);
//	}
//
//	// Instance methods ****************
//
//	/**
//	 * This method handles any messages received from the client.
//	 *
//	 * @param msg    The message received from the client.
//	 * @param client The connection from which the message originated.
//	 * @param
//	 */
//	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
//
//		System.out.println("Message received: " + msg + " from " + client);
//		String splitString[] = msg.toString().split(" ");
//		switch (splitString[0]) {
//		case "start":
//			this.sendToAllClients(mysqlConnection.returnAllData());
//			break;
//		case "update":
//			boolean succeeded = mysqlConnection.updateEmail(splitString[1], splitString[2]);
//			sendToAllClients(succeeded ? "success" : "failed");
//			break;
//		case "disconnect":
//			try {
//				client.sendToClient("");
//				client.close();
//				ServerUI.aFrame.delClient(client, getNumberOfClients());
//			} catch (IOException e) {
//			}
//		default:
//			break;
//		}
//
//	}
//
//	/**
//	 * This method overrides the one in the superclass. Called when the server
//	 * starts listening for connections.
//	 */
//	protected void serverStarted() {
//		System.out.println("Server listening for connections on port " + getPort());
//	}
//
//	/**
//	 * This method overrides the one in the superclass. Called when the server stops
//	 * listening for connections.
//	 */
//	protected void serverStopped() {
//		System.out.println("Server has stopped listening for connections.");
//	}
//
//	@Override
//	protected void clientConnected(ConnectionToClient client) {
//		ServerUI.aFrame.addClient(client);
//	}
//
//	@Override
//	synchronized protected void clientDisconnected(ConnectionToClient client) {
//		ServerUI.aFrame.delClient(client, getNumberOfClients());
//	}
//}
////End of EchoServer class