package by.segg3r.client.handlers;

import org.newdawn.slick.SlickException;
import org.springframework.stereotype.Component;

import by.segg3r.client.VoidClientMessageHandler;
import by.segg3r.data.Player;
import by.segg3r.game.SurviveGame;
import by.segg3r.messages.SinglePlayerCreationMessage;
import by.segg3r.messaging.exception.MessageHandlingException;

@Component
public class SinglePlayerCreationMessageHandler extends
		VoidClientMessageHandler<SinglePlayerCreationMessage> {

	public SinglePlayerCreationMessageHandler() {
		super(SinglePlayerCreationMessage.class);
	}

	@Override
	protected void handleVoidClientMessage(SurviveGame game)
			throws MessageHandlingException {
		try {
			SinglePlayerCreationMessage message = getMessage();
			Player player = message.getPlayer();
			
			game.createPlayerCharacter(player.getX(), player.getY());
		} catch (SlickException e) {
			throw new MessageHandlingException(e);
		}
	}

}
