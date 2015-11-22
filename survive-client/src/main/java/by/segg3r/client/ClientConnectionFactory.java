package by.segg3r.client;

import java.net.Socket;

import by.segg3r.messaging.exception.ConnectionException;

public interface ClientConnectionFactory {

	Socket createSocket(String host, int port) throws ConnectionException;

	ClientConnection createConnection(Socket socket) throws ConnectionException;

}
