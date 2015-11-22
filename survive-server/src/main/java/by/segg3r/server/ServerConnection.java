package by.segg3r.server;

import java.net.Socket;

import by.segg3r.messaging.Connection;
import by.segg3r.messaging.ConnectionPool;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.MessageTarget;
import by.segg3r.messaging.exception.MessageSendingException;

public class ServerConnection extends Connection {

	private ConnectionPool connectionPool;

	public ServerConnection(Socket socket, MessageInputStream in,
			MessageOutputStream out, MessageProcessor messageProcessor,
			ConnectionPool connectionPool) {
		super(socket, in, out, messageProcessor);
		this.connectionPool = connectionPool;
	}

	@Override
	protected void processResponseMessage(Message message)
			throws MessageSendingException {
		MessageTarget target = message.getTarget();
		if (target == MessageTarget.SINGLE) {
			sendMessage(message);
		} else if (target == MessageTarget.ALL) {
			connectionPool.sendAll(message);
		}
	}

	@Override
	public void stop() {
		super.stop();
		connectionPool.removeConnection(this);
	}


}
