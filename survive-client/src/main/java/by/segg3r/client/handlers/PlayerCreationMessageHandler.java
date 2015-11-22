package by.segg3r.client.handlers;

import org.newdawn.slick.SlickException;
import org.springframework.stereotype.Component;

import by.segg3r.client.VoidClientMessageHandler;
import by.segg3r.game.SurviveGame;
import by.segg3r.messages.PlayerCreationMessage;
import by.segg3r.messaging.exception.MessageHandlingException;

@Component
public class PlayerCreationMessageHandler extends
		VoidClientMessageHandler<PlayerCreationMessage> {

	public PlayerCreationMessageHandler() {
		super(PlayerCreationMessage.class);
	}

	@Override
	protected void handleVoidClientMessage(SurviveGame game)
			throws MessageHandlingException {
		try {
			game.createPlayerCharacter();
		} catch (SlickException e) {
			throw new MessageHandlingException(e);
		}
	}

}
