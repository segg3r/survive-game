package by.segg3r.messaging.messages;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageTarget;

public class AllPlayersResponseMessage extends Message {

	public AllPlayersResponseMessage() {
		super(MessageTarget.ALL);
	}
	
}
