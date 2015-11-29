package by.segg3r.messages.server;

import java.io.Serializable;

import by.segg3r.data.GameObject;
import by.segg3r.messaging.Message;

public class ServerPlayerCreationMessage extends Message implements
		Serializable {

	private static final long serialVersionUID = 1661386432975138721L;

	private GameObject gameObject;

	public ServerPlayerCreationMessage(GameObject gameObject) {
		super();
		this.gameObject = gameObject;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

}
