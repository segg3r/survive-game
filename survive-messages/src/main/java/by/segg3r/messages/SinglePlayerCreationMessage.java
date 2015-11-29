package by.segg3r.messages;

import java.io.Serializable;

import by.segg3r.data.Player;
import by.segg3r.messaging.Message;

public class SinglePlayerCreationMessage extends Message implements
		Serializable {

	private static final long serialVersionUID = 7304699834240947387L;

	private Player player;

	public SinglePlayerCreationMessage(Player player) {
		super();
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
