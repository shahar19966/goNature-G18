package application;
	
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import entity.Order;
import gui.ServerScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import message.ServerMessage;
import message.ServerMessageType;
import mysql.MySQLConnection;
import server.GoNatureServer;

/**
 * main class for running the application,opens main server screen
 */
public class ServerMain extends Application {
	public static ServerScreenController guiController;
	public static GoNatureServer server;
	final public static int DEFAULT_PORT = 5555;
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("/gui/ServerScreen.fxml"));
		Parent root = loader.load();
		guiController = loader.getController();
		Scene serverScene = new Scene(root);
		primaryStage.setScene(serverScene);
		primaryStage.setOnCloseRequest(e -> stopServer());//make sure safe shutdown
		primaryStage.show();
	}
	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	/**
	 * static method that upon being called,sets up a server connection to default port and connects to database
	 */
	public static boolean runServer(){
	    	if(server==null)
	    		server = new GoNatureServer(DEFAULT_PORT);
	        try 
	        {
	        	server.listen(); //Start listening for connections
	        	guiController.serverConnected();
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	          showError("Server encountered an issue and cannot run,please try again later");
	          return false;
	        }
	        try {
	        	 MySQLConnection.connectToDB();
	        	 guiController.dataBaseConnected();
	        }catch(Exception e) {
	        	showError("Could not connect to SQL, please try again later");
	        	server.stopListening();
	        	return false;
	        }
	        runThreads();
	        return true;
	       
	       
	}
	/**
	 * static method that upon being called sends a crash message to all clients and exits the application
	 */
	public static void stopServer() {
		if(server!=null)
			server.sendToAllClients(new ServerMessage(ServerMessageType.SERVER_ERROR,"Server crashed!\nSorry for the inconvenience\nClick 'OK' to exit..."));
		System.exit(0);
		
	}
	private static void runThreads() {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

		scheduledThreadPool.scheduleAtFixedRate(() -> {
			try {
				List<Order> orderList=MySQLConnection.sendSmsToActiveOrders();
				server.sendToAllClients(new ServerMessage(ServerMessageType.FINAL_APPROVAL_EMAIL_AND_SMS,orderList));
			} catch (SQLException e) {
				showError("FAILED TO SEND CLIENTS SMS AND EMAILS");
			}
		}, 0, 1, TimeUnit.HOURS);
		scheduledThreadPool.scheduleAtFixedRate(() -> {
			try {
				List<List<Order>> twoOrderList=MySQLConnection.sendSmsToCancelOrders();
				server.sendToAllClients(new ServerMessage(ServerMessageType.CANCEL_EMAIL_AND_SMS,twoOrderList.get(0)));
				if(twoOrderList.get(1)!=null)
					server.sendToAllClients(new ServerMessage(ServerMessageType.WAITING_LIST_APPROVAL_EMAIL_AND_SMS,twoOrderList.get(1)));
			} catch (NumberFormatException | SQLException | ParseException e) {
				showError("FAILED TO SEND CLIENTS SMS AND EMAILS");
			}
		}, 0, 30, TimeUnit.MINUTES);
		scheduledThreadPool.scheduleAtFixedRate(() -> {
			try {
				MySQLConnection.checkIfParksFull();
			} catch ( SQLException e) {
				showError("FAILED TO CHECK IF PARKS FULL");
			}
		}, 0, 1, TimeUnit.HOURS);
	}
	private static void showError(String msg) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("");
				alert.setContentText(msg);
				alert.showAndWait();
			});
	}
}
