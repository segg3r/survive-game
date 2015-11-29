package by.segg3r.client.handlers;

import java.util.List;

import org.newdawn.slick.SlickException;
import org.springframework.stereotype.Component;

import by.segg3r.client.VoidClientMessageHandler;
import by.segg3r.data.GameObject;
import by.segg3r.game.SurviveGame;
import by.segg3r.messages.server.ServerOtherPlayersCreationMessage;
import by.segg3r.messaging.exception.MessageHandlingException;

@Component
public class ServerOtherPlayersCreationMessageHandler extends
		VoidClientMessageHandler<ServerOtherPlayersCreationMessage> {

	public ServerOtherPlayersCreationMessageHandler() {
		super(ServerOtherPlayersCreationMessage.class);
	}

	@Override
	protected void handleVoidClientMessage(SurviveGame game)
			throws MessageHandlingException {
		ServerOtherPlayersCreationMessage message = getMessage();
		List<GameObject> otherGameObjects = message.getGameObjects();
		for (GameObject gameObject : otherGameObjects) {
			try {
				game.createPlayerCharacter(gameObject);
			} catch (SlickException e) {
				throw new MessageHandlingException(e);
			}
		}
	}

}
