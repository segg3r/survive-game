package by.segg3r.client.handlers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Component;

import by.segg3r.client.ClientMessageHandler;
import by.segg3r.game.SurviveGame;
import by.segg3r.messages.client.ClientAuthenticationMessage;
import by.segg3r.messages.server.ServerRequestAuthenticationMessage;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageHandlingException;

@Component
public class ServerAuthenticationRequestMessageHandler extends
		ClientMessageHandler<ServerRequestAuthenticationMessage> {

	private static final String ADMIN = "admin";

	public ServerAuthenticationRequestMessageHandler() {
		super(ServerRequestAuthenticationMessage.class);
	}

	@Override
	public Collection<Message> handleClientMessage(SurviveGame game)
			throws MessageHandlingException {
		ClientAuthenticationMessage message = new ClientAuthenticationMessage(
				ADMIN, ADMIN);
		return Arrays.asList(message);
	}

}
