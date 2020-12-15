package message;

import java.io.Serializable;

public class ServerMessage implements Serializable{
	private ServerMessageType type;
	private Object message;
	public ServerMessage(ServerMessageType type, Object message) {
		this.type = type;
		this.message = message;
	}
	public ServerMessageType getType() {
		return type;
	}
	public void setType(ServerMessageType type) {
		this.type = type;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	

}
