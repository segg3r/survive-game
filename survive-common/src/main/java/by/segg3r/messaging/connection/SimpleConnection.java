package by.segg3r.messaging.connection;

import java.net.Socket;
import java.util.Collection;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;

public class SimpleConnection extends Connection {

	private MessageProcessor<SimpleConnection> messageProcessor;

	public SimpleConnection(Socket socket, MessageInputStream in,
			MessageOutputStream out, MessageProcessor<SimpleConnection> messageProcessor) {
		super(socket, in, out);
		this.messageProcessor = messageProcessor;
	}

	@Override
	protected Collection<Message> processMessage(Message message)
			throws UnrecognizedMessageTypeException, MessageHandlingException {
		return messageProcessor.process(this, message);
	}

}
