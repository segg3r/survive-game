package by.segg3r.messages.client;

import by.segg3r.data.Position;

public class ClientPlayerMovementMessage extends ClientMessage {

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
