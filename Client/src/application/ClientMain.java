package application;
	
import java.io.IOException;

import client.GoNatureClient;
import gui.ClientMainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class ClientMain extends Application {
	private final int DEFAULT_PORT=5555;
	GoNatureClient client;
	ClientMainPageController mainController;
	@Override
	public void start(Stage primaryStage) {
		try {
			client=new GoNatureClient("localhost",DEFAULT_PORT);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/ClientMainPage.fxml"));
			AnchorPane root = fxmlLoader.load();
			mainController = (ClientMainPageController)fxmlLoader.getController();
			Scene scene = new Scene (root);
			primaryStage.setTitle("goNature");
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e->{
				ClientMessage msg=new ClientMessage(ClientMessageType.DISCONNECTED,null);
				client.handleMessageFromClientUI(msg);
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
