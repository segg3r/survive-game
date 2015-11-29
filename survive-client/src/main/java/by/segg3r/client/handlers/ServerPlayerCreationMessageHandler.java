package by.segg3r.client.handlers;

import org.newdawn.slick.SlickException;
import org.springframework.stereotype.Component;

import by.segg3r.client.VoidClientMessageHandler;
import by.segg3r.data.GameObject;
import by.segg3r.game.SurviveGame;
import by.segg3r.messages.server.ServerPlayerCreationMessage;
import by.segg3r.messaging.exception.MessageHandlingException;

@Component
public class ServerPlayerCreationMessageHandler extends
		VoidClientMessageHandler<ServerPlayerCreationMessage> {

	public ServerPlayerCreationMessageHandler() {
		super(ServerPlayerCreationMessage.class);
	}

	@Override
	protected void handleVoidClientMessage(SurviveGame game)
			throws MessageHandlingException {
		try {
			ServerPlayerCreationMessage message = getMessage();
			GameObject gameObject = message.getGameObject();	
			
			game.createPlayerCharacter(gameObject);
		} catch (SlickException e) {
			throw new MessageHandlingException(e);
		}
	}

}
