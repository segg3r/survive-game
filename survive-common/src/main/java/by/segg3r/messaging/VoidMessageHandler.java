package by.segg3r.messaging;

import java.util.Collection;
import java.util.Collections;

import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.exception.MessageHandlingException;

public abstract class VoidMessageHandler<ConnectionType extends Connection, MessageType extends Message>
		extends MessageHandler<ConnectionType, MessageType> {

	public VoidMessageHandler(Class<MessageType> messageClass) {
		super(messageClass);
	}

	@Override
	public Collection<Message> handle(ConnectionType connection, MessageType message)
			throws MessageHandlingException {
		handleVoidMessage(connection, message);
		return Collections.emptyList();
	}

	protected abstract void handleVoidMessage(ConnectionType connection, MessageType message)
			throws MessageHandlingException;

}
