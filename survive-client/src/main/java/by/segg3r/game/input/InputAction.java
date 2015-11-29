package by.segg3r.game.input;

import org.newdawn.slick.Input;

import by.segg3r.messaging.exception.MessageSendingException;

public interface InputAction {

	void perform(Input input) throws MessageSendingException;
	
}
