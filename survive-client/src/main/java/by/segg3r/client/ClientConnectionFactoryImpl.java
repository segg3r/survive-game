package by.segg3r.client;

import java.io.IOException;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.exception.ConnectionException;

@Component
public class ClientConnectionFactoryImpl implements ClientConnectionFactory {

	@Autowired
	private MessageProcessor messageProcessor;

	@Override
	public Socket createSocket(String host, int port)
			throws ConnectionException {
		try {
			return new Socket(host, port);
		} catch (IOException e) {
			throw new ConnectionException("Error connection to server", e);
		}
	}

	@Override
	public Connection createConnection(Socket socket)
			throws ConnectionException {
		try {
			MessageOutputStream out = new MessageOutputStream(
					socket.getOutputStream());
			MessageInputStream in = new MessageInputStream(
					socket.getInputStream());

			Connection connection = new Connection(socket, in, out,
					messageProcessor);
			return connection;
		} catch (Exception e) {
			throw new ConnectionException(
					"Could not create connection to server", e);
		}
	}

}
