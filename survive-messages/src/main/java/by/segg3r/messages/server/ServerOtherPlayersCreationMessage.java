package by.segg3r.messages.server;

import java.io.Serializable;
import java.util.List;

import by.segg3r.data.GameObject;
import by.segg3r.messaging.Message;

public class ServerOtherPlayersCreationMessage extends Message implements
		Serializable {

	private static final long serialVersionUID = -5295601800854589962L;

	private List<GameObject> gameObjects;

	public ServerOtherPlayersCreationMessage(List<GameObject> gameObjects) {
		super();
		this.gameObjects = gameObjects;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(List<GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

}
