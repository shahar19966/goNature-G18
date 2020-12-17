package gui;

import client.GoNatureClient;
import javafx.stage.Stage;
import message.ServerMessage;

public class GUIControl {
//	private static GUIControl instance=new GUIControl();
	private static  GoNatureClient client;
	private static Stage primaryStage;
	private static ServerMessage serverMsg;
//	private GUIControl() {}
//	public static GUIControl getInstance() {
//		return instance;
//	}
	public void setClient(GoNatureClient client) {
		this.client=client;
	}
	public void setStage(Stage stage) {
		primaryStage=stage;
	}
	public static Stage getStage() {
		return primaryStage;
	}
	public static void sendToServer(Object msg) {
		client.handleMessageFromClientUI(msg);
	}
	public static void setServerMsg(Object msg) {
		serverMsg=(ServerMessage)msg;
	}
	public static ServerMessage getServerMsg() {
		return serverMsg;
	}
}
