package by.segg3r;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.exception.ConnectionException;
import by.segg3r.messaging.Connection;
import by.segg3r.server.ServerConnectionService;

public class Server implements Runnable {

	private static final Logger LOG = LogManager.getLogger(Server.class);

	@Autowired
	private ServerConnectionService connectionService;

	private boolean stopped;
	private int port;
	private List<Connection> connections;

	public Server(int port) {
		this.port = port;
		this.connections = new LinkedList<Connection>();
	}

	@Override
	public void run() {
		ServerSocket serverSocket;
		Socket clientSocket;

		try {
			serverSocket = connectionService.createServerSocket(port);
			while (!stopped) {
				clientSocket = serverSocket.accept();
				Connection connection = connectionService.createConnection(clientSocket);
				connections.add(connection);
			}
		} catch (IOException | ConnectionException e) {
			LOG.error("Error starting server", e);
		}
	}

	public List<Connection> getConnections() {
		return connections;
	}

	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}

	public void setConnectionService(ServerConnectionService connectionService) {
		this.connectionService = connectionService;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
