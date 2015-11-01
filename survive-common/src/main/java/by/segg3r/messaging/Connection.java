package by.segg3r.messaging;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Connection implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Connection.class);
	
	private boolean stopped;

	private MessageInputStream in;
	private MessageOutputStream out;
	private MessageProcessor messageProcessor;

	public Connection(MessageInputStream in, MessageOutputStream out,
			MessageProcessor messageProcessor) {
		super();
		this.in = in;
		this.out = out;
		this.messageProcessor = messageProcessor;
	}

	@Override
	public void run() {
		while (!stopped) {
			try {
				Message message = in.readMessage();
				Message response = messageProcessor.process(message);
				if (response != null) {
					out.writeMessage(response);
				}
			} catch (Exception e) {
				LOG.error("Could not read message", e);
			}
		}
	}
	
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
