package by.segg3r.messages.client;

import java.io.Serializable;

import by.segg3r.messaging.Message;

public class ClientMessage extends Message implements Serializable {

	private static final long serialVersionUID = 3705021451483906357L;

	private long clientId;

	public ClientMessage() {
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

}
