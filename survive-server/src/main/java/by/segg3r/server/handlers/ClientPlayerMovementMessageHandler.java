package by.segg3r.server.handlers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Component;

import by.segg3r.data.Position;
import by.segg3r.messages.client.ClientPlayerMovementMessage;
import by.segg3r.messages.server.ServerPlayerMovementMessage;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.server.GameObjectService;
import by.segg3r.server.ServerMessageHandler;

@Component
public class ClientPlayerMovementMessageHandler extends
		ServerMessageHandler<ClientPlayerMovementMessage> {

	public ClientPlayerMovementMessageHandler() {
		super(ClientPlayerMovementMessage.class);
	}

	@Override
	public Collection<Message> handle(ClientPlayerMovementMessage message)
			throws MessageHandlingException {
		long clientId = message.getClientId();
		Position destination = message.getDestination();

		GameObjectService gameObjectService = getGameObjectService();
		gameObjectService.changeObjectPosition(message.getClientId(),
				destination);

		ServerPlayerMovementMessage serverPlayerMovementMessage = new ServerPlayerMovementMessage(
				clientId, destination);
		return Arrays.asList(serverPlayerMovementMessage);
	}

}
