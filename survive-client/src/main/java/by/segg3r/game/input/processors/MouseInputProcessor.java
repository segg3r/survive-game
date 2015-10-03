package by.segg3r.game.input.processors;

import org.newdawn.slick.Input;

import by.segg3r.game.input.InputAction;
import by.segg3r.game.input.InputProcessor;

public class MouseInputProcessor extends InputProcessor {

	private int button;
	
	public MouseInputProcessor(int button, InputAction... actions) {
		super(actions);
		this.button = button;
	}

	@Override
	public boolean isTriggered(Input input) {
		return input.isMousePressed(button);
	}
	
}
