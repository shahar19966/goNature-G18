package application;
	
import client.GoNatureClient;
import gui.LoginPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;


public class ClientMain extends Application {
	private final int DEFAULT_PORT=5555;
	GoNatureClient client;
	LoginPageController mainController;
	@Override
	public void start(Stage primaryStage) {
		try {
			client=new GoNatureClient("localhost",DEFAULT_PORT);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/LoginPage.fxml"));
			AnchorPane root = fxmlLoader.load();
			mainController = (LoginPageController)fxmlLoader.getController();
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
