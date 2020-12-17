package gui;

import client.GoNatureClient;

public class GUIControl {
//	private static GUIControl instance=new GUIControl();
	private static  GoNatureClient client;
//	private GUIControl() {}
//	public static GUIControl getInstance() {
//		return instance;
//	}
	public void setClient(GoNatureClient client) {
		this.client=client;
	}
	public static void sendToServer(Object msg) {
		client.handleMessageFromClientUI(msg);
	}

}
