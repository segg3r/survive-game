package by.segg3r.server;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

import by.segg3r.messaging.Connection;
import by.segg3r.messaging.ConnectionPool;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageSendingException;

@Component
public class ServerConnectionPoolImpl implements ConnectionPool {

	private Collection<Connection> connections = new LinkedList<Connection>();

	@Override
	public void addConnection(Connection connection) {
		connections.add(connection);
	}

	@Override
	public void sendAll(Message message) throws MessageSendingException {
		for (Connection connection : connections) {
			connection.sendMessage(message);
		}
	}
	
	public Collection<Connection> getConnections() {
		return connections;
	}
	
}
