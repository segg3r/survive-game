package by.segg3r.messaging;

public abstract class Message {

	private final MessageTarget target;

	public Message() {
		this(MessageTarget.SINGLE);
	}
	
	public Message(MessageTarget target) {
		super();
		this.target = target;
	}

	public MessageTarget getTarget() {
		return target;
	}

}
