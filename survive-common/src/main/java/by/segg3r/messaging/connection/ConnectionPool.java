package by.segg3r.messaging.connection;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageSendingException;


public interface ConnectionPool {

	void addConnection(Connection connection);
	void removeConnection(Connection connection);
	void sendAll(Message message) throws MessageSendingException;
	void sendAllButOne(Connection connection, Message message) throws MessageSendingException;
	
}
