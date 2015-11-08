package by.segg3r.messaging.messages;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageTarget;

public abstract class SinglePlayerResponseMessage extends Message {

	public SinglePlayerResponseMessage() {
		super(MessageTarget.SINGLE);
	}

}
