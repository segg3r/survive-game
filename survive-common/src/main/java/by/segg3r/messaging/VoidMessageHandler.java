package by.segg3r.messaging;

import java.util.Collection;
import java.util.Collections;

import by.segg3r.messaging.exception.MessageHandlingException;

public abstract class VoidMessageHandler<MessageType extends Message> extends MessageHandler<MessageType> {

	public VoidMessageHandler(Class<MessageType> messageClass) {
		super(messageClass);
	}

	@Override
	public Collection<Message> handle(MessageType message)
			throws MessageHandlingException {
		handleVoidMessage(message);
		return Collections.emptyList();
	}
	
	protected abstract void handleVoidMessage(MessageType message) throws MessageHandlingException;

}
