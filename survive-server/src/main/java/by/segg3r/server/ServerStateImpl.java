package by.segg3r.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.messaging.ConnectionPool;

@Component
public class ServerStateImpl implements ServerState {

	@Autowired
	private ConnectionPool connectionPool;

	@Override
	public ConnectionPool getConnectionPool() {
		return this.connectionPool;
	}
	
	
}
