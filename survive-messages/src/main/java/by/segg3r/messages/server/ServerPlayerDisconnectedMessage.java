package by.segg3r.messages.server;

import java.io.Serializable;

import by.segg3r.messaging.Message;

public class ServerPlayerDisconnectedMessage extends Message implements
		Serializable {

	private static final long serialVersionUID = 5034360874055472717L;

	private long playerId;

	public ServerPlayerDisconnectedMessage(long playerId) {
		super();
		this.playerId = playerId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

}
