package by.segg3r.messages.client;

import java.io.Serializable;

import by.segg3r.data.Position;
import by.segg3r.messaging.Message;

public class ClientPlayerMovementMessage extends Message implements Serializable {

	private static final long serialVersionUID = -2241753053962642398L;

	private Position destination;

	public ClientPlayerMovementMessage(Position destination) {
		super();
		this.destination = destination;
	}

	public Position getDestination() {
		return destination;
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

}
