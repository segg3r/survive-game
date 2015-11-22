package by.segg3r;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.messaging.Connection;
import by.segg3r.messaging.ConnectionPool;
import by.segg3r.messaging.exception.ConnectionException;
import by.segg3r.server.ServerConnectionFactory;

public class Server implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Server.class);

	@Autowired
	private ServerConnectionFactory connectionFactory;
	@Autowired
	private ConnectionPool connectionPool;

	private boolean stopped;
	private int port;

	public Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		ServerSocket serverSocket;
		Socket clientSocket;

		try {
			serverSocket = connectionFactory.createServerSocket(port);
			LOG.info("Initialized server socket on port " + this.port);
			
			while (!stopped) {
				clientSocket = serverSocket.accept();
				Connection connection = connectionFactory
						.createConnection(clientSocket);
				connectionPool.addConnection(connection);

				LOG.info("New client connection: "
						+ clientSocket.getInetAddress().getCanonicalHostName()
						+ ":" + clientSocket.getPort());
			}
		} catch (IOException | ConnectionException e) {
			LOG.error("Error starting server", e);
		}
	}

	public void setConnectionService(ServerConnectionFactory connectionService) {
		this.connectionFactory = connectionService;
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
