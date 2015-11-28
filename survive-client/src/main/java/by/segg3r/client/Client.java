package by.segg3r.client;

import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.exception.ConnectionException;

public class Client {

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

	public Connection start() throws ConnectionException {
		Socket socket = connectionFactory.createSocket(host, port);
		Connection result = connectionFactory.createConnection(socket);

		LOG.info("Successfully connected to server : "
				+ socket.getInetAddress().getCanonicalHostName() + ":"
				+ socket.getPort());
		return result;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
