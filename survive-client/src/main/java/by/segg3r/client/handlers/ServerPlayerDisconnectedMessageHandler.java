package by.segg3r.client.handlers;

import org.springframework.stereotype.Component;

import by.segg3r.client.VoidClientMessageHandler;
import by.segg3r.game.SurviveGame;
import by.segg3r.messages.server.ServerPlayerDisconnectedMessage;
import by.segg3r.messaging.exception.MessageHandlingException;

@Component
public class ServerPlayerDisconnectedMessageHandler extends
		VoidClientMessageHandler<ServerPlayerDisconnectedMessage> {

	public ServerPlayerDisconnectedMessageHandler() {
		super(ServerPlayerDisconnectedMessage.class);
	}

	@Override
	protected void handleVoidClientMessage(SurviveGame game)
			throws MessageHandlingException {
		ServerPlayerDisconnectedMessage message = getMessage();
		long playerId = message.getPlayerId();

		game.getCurrentRoom().removeGameObject(playerId);
	}

}
