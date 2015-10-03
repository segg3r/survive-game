package by.segg3r.game.input;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;
import org.springframework.stereotype.Component;

@Component
public class InputHandler {

	private List<InputProcessor> inputProcessors = new ArrayList<InputProcessor>();
	
	public void addInputProcessor(InputProcessor inputProcessor) {
		this.inputProcessors.add(inputProcessor);
	}
	
	public void handleInput(Input input) {
		for (InputProcessor inputProcessor : inputProcessors) {
			if (inputProcessor.isTriggered(input)) {
				inputProcessor.process(input);
			}
		}
	}

}
