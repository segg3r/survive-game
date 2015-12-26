package by.segg3r.listeners;

import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.messaging.connection.ConnectionListener;
import by.segg3r.messaging.connection.ConnectionPool;
import by.segg3r.server.PlayerService;
import by.segg3r.server.ServerConnection;

public abstract class ServerConnectionListener implements
		ConnectionListener<ServerConnection> {

	@Autowired
	private ConnectionPool connectionPool;
	@Autowired
	private PlayerService gameObjectService;

	public ServerConnectionListener() {
	}

	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public PlayerService getGameObjectService() {
		return gameObjectService;
	}

}
