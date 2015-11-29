package by.segg3r.client.handlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import by.segg3r.client.VoidClientMessageHandler;
import by.segg3r.data.Position;
import by.segg3r.game.SurviveGame;
import by.segg3r.game.exception.GameObjectNotFoundException;
import by.segg3r.game.objects.ClientGameObject;
import by.segg3r.messages.server.ServerPlayerMovementMessage;
import by.segg3r.messaging.exception.MessageHandlingException;

@Component
public class ServerPlayerMovementMessageHandler extends
		VoidClientMessageHandler<ServerPlayerMovementMessage> {

	private static final Logger LOG = LogManager.getLogger(ServerPlayerMovementMessageHandler.class);
	
	public ServerPlayerMovementMessageHandler() {
		super(ServerPlayerMovementMessage.class);
	}

	@Override
	protected void handleVoidClientMessage(SurviveGame game)
			throws MessageHandlingException {
		ServerPlayerMovementMessage message = getMessage();
		long playerId = message.getObjectId();
		Position destination = message.getDestination();

		try {
			ClientGameObject clientGameObject = game.getCurrentRoom()
					.findGameObject(playerId);
			clientGameObject.setDestination(destination.getX(),
					destination.getY());
		} catch (GameObjectNotFoundException e) {
			LOG.error(e);
			throw new MessageHandlingException(e);
		}
	}

}
