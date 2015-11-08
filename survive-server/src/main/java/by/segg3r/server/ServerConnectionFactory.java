package by.segg3r.server;

import java.net.ServerSocket;
import java.net.Socket;

import by.segg3r.exception.ConnectionException;
import by.segg3r.messaging.Connection;

public interface ServerConnectionFactory {

	ServerSocket createServerSocket(int port) throws ConnectionException;

	Connection createConnection(Socket clientSocket) throws ConnectionException;
	
}
