package by.segg3r.server;

import by.segg3r.messaging.ConnectionPool;
import by.segg3r.state.State;

public interface ServerState extends State {

	ConnectionPool getConnectionPool();
	
}
