package by.segg3r.game.actions;

import org.newdawn.slick.Input;

import by.segg3r.data.Position;
import by.segg3r.game.input.InputAction;
import by.segg3r.messages.client.ClientPlayerMovementMessage;
import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.exception.MessageSendingException;

public class MovementAction implements InputAction {

	private Connection connection;

	public MovementAction(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void perform(Input input) throws MessageSendingException {
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		Position destination = new Position(mouseX, mouseY);
		ClientPlayerMovementMessage message = new ClientPlayerMovementMessage(
				destination);
		connection.sendMessage(message);
	}

}
