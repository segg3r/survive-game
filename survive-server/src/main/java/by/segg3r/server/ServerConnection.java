package by.segg3r.server;

import java.net.Socket;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.segg3r.data.GameObject;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageInterceptor;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.MessageTarget;
import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.connection.ConnectionPool;
import by.segg3r.messaging.connection.listeners.ListenerType;
import by.segg3r.messaging.connection.listeners.Listeners;
import by.segg3r.messaging.exception.MessageSendingException;

public class ServerConnection extends Connection {

	private static final Logger LOG = LogManager.getLogger(ServerConnection.class);
	
	private List<MessageInterceptor<ServerConnection>> messageInterceptors;
	private ConnectionPool connectionPool;
	private GameObject player;
	private Listeners<ServerConnection> listeners;
	
	public ServerConnection(Socket socket, MessageInputStream in,
			MessageOutputStream out, MessageProcessor messageProcessor,
			ConnectionPool connectionPool,
			List<MessageInterceptor<ServerConnection>> messageInterceptors,
			GameObject player,
			Listeners<ServerConnection> listeners) {
		super(socket, in, out, messageProcessor);
		this.connectionPool = connectionPool;
		this.messageInterceptors = messageInterceptors;
		this.player = player;
		this.listeners = listeners;
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
	protected void preprocessMessage(Message message) {
		for (MessageInterceptor<ServerConnection> messageInterceptor : messageInterceptors) {
			messageInterceptor.intercept(message, this);
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

	public List<MessageInterceptor<ServerConnection>> getMessageInterceptors() {
		return messageInterceptors;
	}

	public void setMessageInterceptors(
			List<MessageInterceptor<ServerConnection>> messageInterceptors) {
		this.messageInterceptors = messageInterceptors;
	}

}
