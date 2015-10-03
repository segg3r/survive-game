package by.segg3r.game.input;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;

public abstract class InputProcessor {

	private List<InputAction> actions = new ArrayList<InputAction>();
	
	public InputProcessor() { }
	
	public InputProcessor(InputAction... actions) {
		for (InputAction action : actions) {
			this.actions.add(action);
		}
	}
	
	public abstract boolean isTriggered(Input input);
	
	public void addInputAction(InputAction action) {
		this.actions.add(action);
	}
	
	public void process(Input input) {
		for (InputAction inputAction : actions) {
			inputAction.perform(input);
		}
	}
	
}
