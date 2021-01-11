package gui;

import message.ServerMessage;

public interface IClientServerCommunication {
	public void popUpError(String msg);
	public void sendToServer(Object msg);
	public ServerMessage getServerMsg();
	public void setUser(Object user) ;
}
