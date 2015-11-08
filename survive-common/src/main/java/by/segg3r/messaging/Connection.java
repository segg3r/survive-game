package by.segg3r.messaging;

import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.segg3r.messaging.exception.MessageReceievingException;
import by.segg3r.messaging.exception.MessageSendingException;
import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;

public class Connection implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Connection.class);
	
	private boolean stopped;

	private MessageInputStream in;
	private MessageOutputStream out;
	private MessageProcessor messageProcessor;
	private ConnectionPool connectionPool;
	
	public Connection(MessageInputStream in, MessageOutputStream out,
			MessageProcessor messageProcessor, ConnectionPool connectionPool) {
		super();
		this.in = in;
		this.out = out;
		this.messageProcessor = messageProcessor;
		this.connectionPool = connectionPool;
	}

	@Override
	public void run() {
		while (!stopped) {
			try {
				Message message = in.readMessage();
				Collection<Message> response = messageProcessor.process(message);
				
				for (Message responseMessage : response) {
					MessageTarget target = responseMessage.getTarget();
					if (target == MessageTarget.SINGLE) {
						sendMessage(responseMessage);
					} else if (target == MessageTarget.ALL) {
						connectionPool.sendAll(responseMessage);
					}
				}
			} catch (UnrecognizedMessageTypeException e) {
				LOG.error("Could not recognize the message", e);
			} catch (MessageSendingException e) {
				LOG.error("Error sending response message", e);
			} catch (MessageReceievingException e) {
				LOG.error("Error receiving message", e);
			}
		}
	}
	
	public void sendMessage(Message message) throws MessageSendingException {
		out.writeMessage(message);
	}
	
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
