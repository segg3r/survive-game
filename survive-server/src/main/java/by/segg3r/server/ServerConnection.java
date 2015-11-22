package by.segg3r.server;

import java.net.Socket;

import by.segg3r.messaging.Connection;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.MessageTarget;
import by.segg3r.messaging.exception.MessageSendingException;

public class ServerConnection extends Connection<ServerState> {

	public ServerConnection(Socket socket, MessageInputStream in, MessageOutputStream out,
			MessageProcessor messageProcessor, ServerState serverState) {
		super(socket, in, out, messageProcessor, serverState);
	}
	
	@Override
	protected void processResponseMessage(Message message) throws MessageSendingException {
		MessageTarget target = message.getTarget();
		if (target == MessageTarget.SINGLE) {
			sendMessage(message);
		} else if (target == MessageTarget.ALL) {
			getState().getConnectionPool().sendAll(message);
		}
	}
	
	@Override
	public void stop() {
		super.stop();
		getState().getConnectionPool().removeConnection(this);
	}

}
