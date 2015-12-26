package by.segg3r.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.connection.ConnectionPool;
import by.segg3r.messaging.connection.listeners.Listeners;
import by.segg3r.messaging.exception.ConnectionException;

@Component
public class ServerConnectionFactoryImpl implements ServerConnectionFactory {

	@Autowired
	private MessageProcessor<ServerConnection> messageProcessor;
	@Autowired
	private ConnectionPool connectionPool;
	@Autowired
	private PlayerService gameObjectService;
	@Value("#{listeners}")
	private Listeners<ServerConnection> listeners;

	public ServerSocket createServerSocket(int port) throws ConnectionException {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			throw new ConnectionException("Error creating server socket", e);
		}
	}

	@Override
	public ServerConnection createConnection(Socket clientSocket)
			throws ConnectionException {
		try {
			MessageOutputStream out = new MessageOutputStream(
					clientSocket.getOutputStream());
			MessageInputStream in = new MessageInputStream(
					clientSocket.getInputStream());

			ServerConnection result = new ServerConnection(clientSocket, in,
					out, messageProcessor, connectionPool, listeners);
			new Thread(result).start();
			return result;
		} catch (Exception e) {
			throw new ConnectionException("Could not create connection", e);
		}
	}
}
