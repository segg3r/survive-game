package by.segg3r.server;

import java.net.ServerSocket;
import java.net.Socket;

import by.segg3r.exception.ConnectionException;

public interface ConnectionService {

	ServerSocket createServerSocket(int port) throws ConnectionException;

	Connection createConnection(Socket clientSocket) throws ConnectionException;
	
}
