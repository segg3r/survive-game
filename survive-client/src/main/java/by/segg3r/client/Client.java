package by.segg3r.client;

import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class Client implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Client.class);

	@Autowired
	private ClientConnectionFactory connectionFactory;

	private boolean stopped;
	private String host;
	private int port;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		Socket socket;

		try {
			socket = connectionFactory.createSocket(host, port);
			connectionFactory.createConnection(socket);
			
			LOG.info("Successfully connected to server : "
					+ socket.getInetAddress().getCanonicalHostName() + ":"
					+ socket.getPort());
		} catch (Exception e) {
			LOG.error("Error starting client", e);
		}
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
