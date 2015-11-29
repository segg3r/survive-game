package by.segg3r.messages.server;

import java.io.Serializable;

import by.segg3r.data.Position;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageTarget;

public class ServerPlayerMovementMessage extends Message implements
		Serializable {

	private static final long serialVersionUID = 5494028427682420770L;

	private long objectId;
	private Position destination;

	public ServerPlayerMovementMessage(long objectId, Position destination) {
		super(MessageTarget.ALL);
		this.objectId = objectId;
		this.destination = destination;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public Position getDestination() {
		return destination;
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

}
