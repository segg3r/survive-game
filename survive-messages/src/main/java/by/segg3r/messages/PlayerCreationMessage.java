package by.segg3r.messages;

import java.io.Serializable;

import by.segg3r.data.GameObject;
import by.segg3r.messaging.Message;

public class PlayerCreationMessage extends Message implements Serializable {

	private static final long serialVersionUID = 7304699834240947387L;

	private GameObject gameObject;

	public PlayerCreationMessage(GameObject gameObject) {
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
