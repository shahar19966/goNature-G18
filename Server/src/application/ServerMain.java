package application;
	
import gui.ServerScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import message.ServerMessage;
import message.ServerMessageType;
import mysql.MySQLConnection;
import server.GoNatureServer;

/*
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
	
	/*
	 * static method that upon being called,sets up a server connection to default port and connects to database
	 */
	public static void runServer(){
	    	
			server = new GoNatureServer(DEFAULT_PORT);
	        
	        try 
	        {
	        	server.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	        guiController.serverConnected();
	        MySQLConnection.ConnectToDB();
	        guiController.dataBaseConnected();
	}
	/*
	 * static method that upon being called sends a crash message to all clients and exits the application
	 */
	public static void stopServer() {
		server.sendToAllClients(new ServerMessage(ServerMessageType.SERVER_ERROR,"Server crashed!\nYour client will shut down in a few seconds"));
		System.exit(0);
		
	}
}
