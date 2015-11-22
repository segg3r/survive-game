package by.segg3r.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.exception.ConnectionException;

@Component
public class ServerConnectionFactoryImpl implements ServerConnectionFactory {

	@Autowired
	private MessageProcessor messageProcessor;
	@Autowired
	private ServerState serverState;
	
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

			ServerConnection connection = new ServerConnection(clientSocket, in, out,
					messageProcessor, serverState);
			new Thread(connection).start();
			return connection;
		} catch (Exception e) {
			throw new ConnectionException("Could not create connection", e);
		}
	}
}
