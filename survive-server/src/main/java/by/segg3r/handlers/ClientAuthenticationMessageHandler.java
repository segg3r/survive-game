package by.segg3r.handlers;

import java.util.Collection;
import java.util.Collections;

import org.springframework.stereotype.Component;

import by.segg3r.data.GameObject;
import by.segg3r.messages.client.ClientAuthenticationMessage;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.connection.listeners.ListenerType;
import by.segg3r.messaging.connection.listeners.Listeners;
import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.server.PlayerService;
import by.segg3r.server.ServerConnection;
import by.segg3r.server.ServerMessageHandler;

@Component
public class ClientAuthenticationMessageHandler extends
		ServerMessageHandler<ClientAuthenticationMessage> {

	public ClientAuthenticationMessageHandler() {
		super(ClientAuthenticationMessage.class);
	}

	@Override
	public Collection<Message> handle(ServerConnection serverConnection,
			ClientAuthenticationMessage message)
			throws MessageHandlingException {
		try {
			String login = message.getLogin();
			String password = message.getPassword();

			PlayerService playerService = getPlayerService();
			GameObject player = playerService.authenticate(login, password);
			serverConnection.setPlayer(player);
			
			Listeners<ServerConnection> listeners = getListeners();
			listeners.trigger(ListenerType.PLAYER_AUTHENTICATED, serverConnection);

			return Collections.emptyList();
		} catch (Exception e) {
			throw new MessageHandlingException(e);
		}
	}

}
