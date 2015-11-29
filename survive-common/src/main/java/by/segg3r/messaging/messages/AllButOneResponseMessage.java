package by.segg3r.messaging.messages;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageTarget;

public class AllButOneResponseMessage extends Message {

	public AllButOneResponseMessage() {
		super(MessageTarget.ALL_BUT_ONE);
	}
	
}
