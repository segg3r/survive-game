package by.segg3r.messaging;

import java.net.Socket;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.segg3r.messaging.exception.MessageReceievingException;
import by.segg3r.messaging.exception.MessageSendingException;
import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;

public class Connection<StateType> implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Connection.class);

	private boolean stopped;

	private Socket socket;
	private MessageInputStream in;
	private MessageOutputStream out;
	private MessageProcessor messageProcessor;
	private StateType state;

	public Connection(Socket socket, MessageInputStream in,
			MessageOutputStream out, MessageProcessor messageProcessor,
			StateType state) {
		super();
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.messageProcessor = messageProcessor;
		this.state = state;
	}

	@Override
	public void run() {
		while (!stopped) {
			try {
				Message message = in.readMessage();
				Collection<Message> response = messageProcessor
						.process(message);

				for (Message responseMessage : response) {
					processResponseMessage(responseMessage);
				}
			} catch (UnrecognizedMessageTypeException e) {
				LOG.error("Could not recognize the message", e);
			} catch (MessageSendingException e) {
				LOG.error("Error sending response message", e);
			} catch (MessageReceievingException e) {
				stop();
				LOG.error("Error receiving message", e);
			}
		}

		try {
			socket.close();
		} catch (Exception e) {
			LOG.error("Error closing socket", e);
		}
	}

	public void sendMessage(Message message) throws MessageSendingException {
		out.writeMessage(message);
	}

	public void stop() {
		LOG.info("Stopping the connection: "
				+ socket.getInetAddress().getCanonicalHostName() + ":"
				+ socket.getPort());

		this.stopped = true;
	}

	public void reset() {
		this.stopped = false;
	}

	public boolean isStopped() {
		return this.stopped;
	}
	
	protected StateType getState() {
		return this.state;
	}

	protected void processResponseMessage(Message message)
			throws MessageSendingException {
		sendMessage(message);
	}

}
