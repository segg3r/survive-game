package by.segg3r.game.actions;

import org.newdawn.slick.Input;

import by.segg3r.game.input.InputAction;
import by.segg3r.game.objects.ClientGameObject;

public class GameObjectMoveInputAction implements InputAction {
	
	private ClientGameObject gameObject;
	
	public GameObjectMoveInputAction(ClientGameObject gameObject) {
		super();
		this.gameObject = gameObject;
	}
	
	@Override
	public void perform(Input input) {
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		gameObject.setDestination(mouseX, mouseY);
	}

}
