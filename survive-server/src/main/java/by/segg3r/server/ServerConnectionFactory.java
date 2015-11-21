package by.segg3r.server;

import java.net.ServerSocket;
import java.net.Socket;

import by.segg3r.messaging.Connection;
import by.segg3r.messaging.exception.ConnectionException;

public interface ServerConnectionFactory {

	ServerSocket createServerSocket(int port) throws ConnectionException;

	Connection createConnection(Socket clientSocket) throws ConnectionException;
	
}
