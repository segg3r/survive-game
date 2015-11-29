package by.segg3r.messaging;

import by.segg3r.messaging.connection.Connection;

public interface MessageInterceptor<ConnectionType extends Connection> {

	void intercept(Message message, ConnectionType connection);
	
}
