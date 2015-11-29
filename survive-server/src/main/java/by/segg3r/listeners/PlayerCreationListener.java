package by.segg3r.listeners;

import java.util.List;

import org.springframework.stereotype.Component;

import by.segg3r.data.GameObject;
import by.segg3r.messages.server.ServerOtherPlayersCreationMessage;
import by.segg3r.messages.server.ServerPlayerCreationMessage;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.connection.ConnectionPool;
import by.segg3r.server.GameObjectService;
import by.segg3r.server.ServerConnection;

@Component
public class PlayerCreationListener extends ServerConnectionListener {

	@Override
	public void trigger(ServerConnection connection) throws Exception {
		ConnectionPool connectionPool = getConnectionPool();
		GameObjectService gameObjectService = getGameObjectService();
		GameObject player = connection.getPlayer();

		Message playerCreationMessage = new ServerPlayerCreationMessage(player);
		connectionPool.sendAll(playerCreationMessage);

		List<GameObject> otherObjects = gameObjectService
				.getOtherObjects(player.getId());
		ServerOtherPlayersCreationMessage otherPlayersCreationMessage = new ServerOtherPlayersCreationMessage(
				otherObjects);
		connection.sendMessage(otherPlayersCreationMessage);
	}

}
