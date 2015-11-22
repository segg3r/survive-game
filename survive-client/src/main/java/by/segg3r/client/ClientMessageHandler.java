package by.segg3r.client;

import java.util.Collection;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Value;

import by.segg3r.game.SurviveGame;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.VoidMessageHandler;
import by.segg3r.messaging.exception.MessageHandlingException;

public abstract class ClientMessageHandler<MessageType extends Message> extends VoidMessageHandler<MessageType> {

	@Value("#{messageQueue}")
	private Queue<ClientMessageHandler<?>> messageQueue;
	
	private MessageType message;
	
	public ClientMessageHandler(Class<MessageType> messageClass) {
		super(messageClass);
	}

	@Override
	protected void handleVoidMessage(MessageType message)
			throws MessageHandlingException {
		this.message = message;
		messageQueue.add(this);
	}
	
	protected MessageType getMessage() {
		return this.message;
	}
	
	public abstract Collection<Message> handleClientMessage(SurviveGame game) throws MessageHandlingException;

}
