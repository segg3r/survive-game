package by.segg3r.messaging;

import java.io.InputStream;
import java.io.ObjectInputStream;

import by.segg3r.messaging.exception.MessageReceievingException;

public class MessageInputStream extends ObjectInputStream {

	public MessageInputStream() throws Exception { }
	
	public MessageInputStream(InputStream in) throws Exception {
		super(in);
	}

	public synchronized Message readMessage() throws MessageReceievingException {
		try {
			Object object = readObjectFromStream();
			Message message = (Message) object;
			return message;
		} catch (Exception e) {
			throw new MessageReceievingException("Error receiving message", e);
		}
	}
	
	public synchronized Object readObjectFromStream() throws Exception {
		return readObject();
	}

}
