package by.segg3r.listeners;

import org.springframework.stereotype.Component;

import by.segg3r.messages.PlayerCreationMessage;
import by.segg3r.messaging.Message;
import by.segg3r.server.ServerConnection;

@Component
public class PlayerCreationListener implements ServerConnectionListener {

	@Override
	public void trigger(ServerConnection connection) throws Exception {
		Message playerCreationMessage = new PlayerCreationMessage();
		connection.sendMessage(playerCreationMessage);
	}

}