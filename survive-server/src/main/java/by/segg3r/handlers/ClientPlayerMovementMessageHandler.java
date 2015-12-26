package by.segg3r.handlers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Component;

import by.segg3r.data.Position;
import by.segg3r.messages.client.ClientPlayerMovementMessage;
import by.segg3r.messages.server.ServerPlayerMovementMessage;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.server.PlayerService;
import by.segg3r.server.ServerConnection;
import by.segg3r.server.ServerMessageHandler;

@Component
public class ClientPlayerMovementMessageHandler extends
		ServerMessageHandler<ClientPlayerMovementMessage> {

	public ClientPlayerMovementMessageHandler() {
		super(ClientPlayerMovementMessage.class);
	}

	@Override
	public Collection<Message> handle(ServerConnection serverConnection, ClientPlayerMovementMessage message)
			throws MessageHandlingException {
		long clientId = serverConnection.getPlayer().getId();
		Position destination = message.getDestination();

		PlayerService playerService = getPlayerService();
		playerService.changePosition(clientId,
				destination);

		ServerPlayerMovementMessage serverPlayerMovementMessage = new ServerPlayerMovementMessage(
				clientId, destination);
		return Arrays.asList(serverPlayerMovementMessage);
	}

}
