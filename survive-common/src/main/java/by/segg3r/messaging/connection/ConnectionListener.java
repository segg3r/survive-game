package by.segg3r.messaging.connection;

public interface ConnectionListener<ConnectionType> {

	void trigger(ConnectionType connection) throws Exception;
	
}
