package by.segg3r.listeners;

import org.springframework.stereotype.Component;

import by.segg3r.messages.server.ServerRequestAuthenticationMessage;
import by.segg3r.server.ServerConnection;

@Component
public class PlayerConnectionListener extends ServerConnectionListener {

	@Override
	public void trigger(ServerConnection connection) throws Exception {
		ServerRequestAuthenticationMessage message = new ServerRequestAuthenticationMessage();
		connection.sendMessage(message);
	}

}
