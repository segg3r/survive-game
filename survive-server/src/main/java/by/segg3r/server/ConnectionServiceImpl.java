package by.segg3r.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Component;

import by.segg3r.exception.ConnectionException;

@Component
public class ConnectionServiceImpl implements ConnectionService {

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
		return new Connection(clientSocket);
	}
}
