package by.segg3r.messaging;

import java.io.ObjectOutputStream;
import java.io.OutputStream;

import by.segg3r.messaging.exception.MessageSendingException;

public class MessageOutputStream extends ObjectOutputStream {

	public MessageOutputStream() throws Exception {
	}

	public MessageOutputStream(OutputStream out) throws Exception {
		super(out);
	}

	public synchronized void writeMessage(Message message) throws MessageSendingException {
		try {
			writeObjectToStream(message);
		} catch (Exception e) {
			throw new MessageSendingException("Error sending message "
					+ message, e);
		}
	}

	public synchronized void writeObjectToStream(Object object) throws Exception {
		writeObject(object);
	}

}
