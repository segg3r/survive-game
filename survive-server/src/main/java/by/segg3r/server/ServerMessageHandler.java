package by.segg3r.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageHandler;
import by.segg3r.messaging.connection.listeners.Listeners;

public abstract class ServerMessageHandler<MessageType extends Message> extends
		MessageHandler<ServerConnection, MessageType> {

	@Autowired
	private PlayerService playerService;
	@Value("#{listeners}")
	private Listeners<ServerConnection> listeners;

	public ServerMessageHandler(Class<MessageType> messageClass) {
		super(messageClass);
	}

	public PlayerService getPlayerService() {
		return playerService;
	}

	public Listeners<ServerConnection> getListeners() {
		return listeners;
	}

}
