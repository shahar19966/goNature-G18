package gui;

import java.util.regex.Pattern;

import client.GoNatureClient;
import entity.Employee;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;
/**
 * service class for the client with various service methods
 * this class is implemented as a singleton so that every instance of it holds the same values.
 */
public class GUIControl {
	private static GUIControl instance=new GUIControl();
	private  GoNatureClient client;
	private  Object currentUser;
	private  Stage primaryStage;
	private  ServerMessage serverMsg;
	private ClientMainPageController cmpc;
	private GUIControl() {}
	public static GUIControl getInstance() {
		return instance;
	}
	public void setClientMainPageController(ClientMainPageController cmpc)
	{
		this.cmpc=cmpc;
	}
	public ClientMainPageController getClientMainPageController()
	{
		return cmpc;
	}
	public  void setClient(GoNatureClient client) {
		this.client=client;
	}
	public  void setStage(Stage stage) {
		primaryStage=stage;
	}
	public Stage getStage() {
		return primaryStage;
	}
	public void sendToServer(Object msg) {
		client.handleMessageFromClientUI(msg);
	}
	public void setServerMsg(Object msg) {
		serverMsg=(ServerMessage)msg;
	}
	public ServerMessage getServerMsg() {
		return serverMsg;
	}
	public void setUser(Object user) {
		currentUser=user;
	}
	public Object getUser() {
		return currentUser;
	}
	public void disconnect() {
		ClientMessage cMsg=new ClientMessage(ClientMessageType.DISCONNECTED,currentUser);
		sendToServer(cMsg);
		currentUser=null;
	}
	public void logOut() {
		ClientMessage cMsg=new ClientMessage(ClientMessageType.LOGOUT,currentUser);
		sendToServer(cMsg);
		currentUser=null;
	}
	public void openLogInPage() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ClientConstants.Screens.LOGIN_PAGE.toString()));
			AnchorPane root = fxmlLoader.load();
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
	public static void popUpError(String msg) {
		Platform.runLater(() -> { 
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.showAndWait();
		});
	}
	public static void popUpErrorExitOnClick(String msg) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.setOnCloseRequest(e->{
				System.exit(0);
			});
			alert.showAndWait();
		});
	}
	public static void popUpMessage(String msg) {
		popUpMessage("Message",msg);
	}
	public static void popUpMessage(String title,String msg)
	{
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(title);
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.showAndWait();
		});
	}
	public static boolean isEmailValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}
	


}
