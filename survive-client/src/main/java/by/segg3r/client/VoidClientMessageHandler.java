package by.segg3r.client;

import java.util.Collection;
import java.util.Collections;

import by.segg3r.game.SurviveGame;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageHandlingException;

public abstract class VoidClientMessageHandler<MessageType extends Message> extends ClientMessageHandler<MessageType> {

	public VoidClientMessageHandler(Class<MessageType> messageClass) {
		super(messageClass);
	}

	@Override
	public Collection<Message> handleClientMessage(SurviveGame game)
			throws MessageHandlingException {
		handleVoidClientMessage(game);
		return Collections.emptyList();
	}

	protected abstract void handleVoidClientMessage(SurviveGame game) throws MessageHandlingException;
	
}
