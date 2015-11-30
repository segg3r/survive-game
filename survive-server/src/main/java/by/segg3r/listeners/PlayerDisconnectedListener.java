package by.segg3r.listeners;

import org.springframework.stereotype.Component;

import by.segg3r.messages.server.ServerPlayerDisconnectedMessage;
import by.segg3r.messaging.connection.ConnectionPool;
import by.segg3r.server.GameObjectService;
import by.segg3r.server.ServerConnection;

@Component
public class PlayerDisconnectedListener extends ServerConnectionListener {

	@Override
	public void trigger(ServerConnection connection) throws Exception {
		ConnectionPool connectionPool = getConnectionPool();
		GameObjectService gameObjectService = getGameObjectService();
		long playerId = connection.getPlayer().getId();
		
		gameObjectService.removeObject(playerId);
		ServerPlayerDisconnectedMessage message = new ServerPlayerDisconnectedMessage(playerId);
		connectionPool.sendAllButOne(connection, message);
	}

}
