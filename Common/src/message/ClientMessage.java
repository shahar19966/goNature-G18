package message;

import java.io.Serializable;

public class ClientMessage implements Serializable {
	private ClientMessageType type;
	private Object message;
	public ClientMessage(ClientMessageType type, Object message) {
		this.type = type;
		this.message = message;
	}
	public ClientMessageType getType() {
		return type;
	}
	public void setType(ClientMessageType type) {
		this.type = type;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return getType().toString() +" " + message;
	}
}
