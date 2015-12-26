package by.segg3r.server;

import java.net.Socket;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.segg3r.data.GameObject;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.MessageTarget;
import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.connection.ConnectionPool;
import by.segg3r.messaging.connection.listeners.ListenerType;
import by.segg3r.messaging.connection.listeners.Listeners;
import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.messaging.exception.MessageSendingException;
import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;

public class ServerConnection extends Connection {

	private static final Logger LOG = LogManager
			.getLogger(ServerConnection.class);

	private ConnectionPool connectionPool;
	private Listeners<ServerConnection> listeners;
	private MessageProcessor<ServerConnection> messageProcessor;

	private GameObject player = null;

	public ServerConnection(Socket socket, MessageInputStream in,
			MessageOutputStream out,
			MessageProcessor<ServerConnection> messageProcessor,
			ConnectionPool connectionPool, Listeners<ServerConnection> listeners) {
		super(socket, in, out);
		this.messageProcessor = messageProcessor;
		this.connectionPool = connectionPool;
		this.listeners = listeners;
	}

	@Override
	protected Collection<Message> processMessage(Message message)
			throws UnrecognizedMessageTypeException, MessageHandlingException {
		return messageProcessor.process(this, message);
	}

	@Override
	protected void processResponseMessage(Message message)
			throws MessageSendingException {
		MessageTarget target = message.getTarget();
		if (target == MessageTarget.SINGLE) {
			sendMessage(message);
		} else if (target == MessageTarget.ALL) {
			connectionPool.sendAll(message);
		} else if (target == MessageTarget.ALL_BUT_ONE) {
			connectionPool.sendAllButOne(this, message);
		}
	}

	@Override
	public void stop() {
		super.stop();

		try {
			listeners.trigger(ListenerType.PLAYER_DISCONNECTED, this);
		} catch (Exception e) {
			LOG.error("Error trigger player disconnected triggers", e);
		}

		connectionPool.removeConnection(this);
	}

	public GameObject getPlayer() {
		return player;
	}

	public void setPlayer(GameObject player) {
		this.player = player;
	}

}
