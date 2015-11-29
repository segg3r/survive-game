package by.segg3r.server;

import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageHandler;

public abstract class ServerMessageHandler<MessageType extends Message> extends
		MessageHandler<MessageType> {

	@Autowired
	private GameObjectService gameObjectService;

	public ServerMessageHandler(Class<MessageType> messageClass) {
		super(messageClass);
	}

	public GameObjectService getGameObjectService() {
		return gameObjectService;
	}

}
