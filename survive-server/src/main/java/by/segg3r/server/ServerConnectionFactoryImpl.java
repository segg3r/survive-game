package by.segg3r.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.exception.ConnectionException;
import by.segg3r.messaging.Connection;
import by.segg3r.messaging.ConnectionPool;
import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;

@Component
public class ServerConnectionFactoryImpl implements ServerConnectionFactory {

	@Autowired
	private MessageProcessor messageProcessor;
	@Autowired
	private ConnectionPool connectionPool;
	
	public ServerSocket createServerSocket(int port) throws ConnectionException {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			throw new ConnectionException("Error creating server socket", e);
		}
	}

	@Override
	public Connection createConnection(Socket clientSocket)
			throws ConnectionException {
		try {
			MessageInputStream in = new MessageInputStream(
					clientSocket.getInputStream());
			MessageOutputStream out = new MessageOutputStream(
					clientSocket.getOutputStream());
			Connection connection = new Connection(in, out, messageProcessor, connectionPool);
			new Thread(connection).start();
			return connection;
		} catch (Exception e) {
			throw new ConnectionException("Could not create connection", e);
		}
	}
}
