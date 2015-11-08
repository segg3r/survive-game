package by.segg3r;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.exception.ConnectionException;
import by.segg3r.messaging.Connection;
import by.segg3r.messaging.ConnectionPool;
import by.segg3r.server.ServerConnectionFactory;

public class Server implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Server.class);

	@Autowired
	private ServerConnectionFactory connectionService;
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
			serverSocket = connectionService.createServerSocket(port);
			while (!stopped) {
				clientSocket = serverSocket.accept();
				Connection connection = connectionService
						.createConnection(clientSocket);
				connectionPool.addConnection(connection);
			}
		} catch (IOException | ConnectionException e) {
			LOG.error("Error starting server", e);
		}
	}

	public void setConnectionService(ServerConnectionFactory connectionService) {
		this.connectionService = connectionService;
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
