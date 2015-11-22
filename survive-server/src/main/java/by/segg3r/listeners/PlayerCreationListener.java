package by.segg3r.listeners;

import org.springframework.stereotype.Component;

import by.segg3r.messages.PlayerCreationMessage;
import by.segg3r.messaging.Connection;
import by.segg3r.messaging.ConnectionListener;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageSendingException;

@Component
public class PlayerCreationListener implements ConnectionListener {

	@Override
	public void trigger(Connection connection) throws MessageSendingException {
		Message playerCreationMessage = new PlayerCreationMessage();
		connection.sendMessage(playerCreationMessage);
	}

}
