package application;
	
import java.io.IOException;

import client.GoNatureClient;
import gui.GUIControl;
import gui.LoginPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

/*
 * main class for running the application,sets up a client,connects to default port and opens the login scene
 */
public class ClientMain extends Application {
	private final int DEFAULT_PORT=5555;
	GoNatureClient client;
	GUIControl guiControl=GUIControl.getInstance();
	@Override
	public void start(Stage primaryStage) {
		try {
			client=new GoNatureClient("localhost",DEFAULT_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guiControl.setClient(client);
		guiControl.setStage(primaryStage);
		guiControl.openLogInPage();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
