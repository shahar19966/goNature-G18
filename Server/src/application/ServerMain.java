package application;
	
import gui.ServerScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mysql.MySQLConnection;


public class ServerMain extends Application {
	public static ServerScreenController guiController;
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
	
	
	public static void runServer(){
	    	
	        GoNatureServer sv = new GoNatureServer(DEFAULT_PORT);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	        guiController.serverConnected();
	        MySQLConnection.ConnectToDB();
	        guiController.dataBaseConnected();
	}
	public static void stopServer() {
		System.exit(0);
		
	}
}
