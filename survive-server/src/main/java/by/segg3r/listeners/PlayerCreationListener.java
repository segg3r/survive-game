package by.segg3r.listeners;

import org.springframework.stereotype.Component;

import by.segg3r.data.Player;
import by.segg3r.messages.SinglePlayerCreationMessage;
import by.segg3r.messaging.Message;
import by.segg3r.server.ServerConnection;

@Component
public class PlayerCreationListener implements ServerConnectionListener {

	@Override
	public void trigger(ServerConnection connection) throws Exception {
		Player player = connection.getPlayer();
		Message playerCreationMessage = new SinglePlayerCreationMessage(player);
		connection.sendMessage(playerCreationMessage);
	}

}
