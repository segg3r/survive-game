package by.segg3r.messaging;

import java.util.Collection;

public abstract class MessageHandler<MessageType extends Message> {

	private final Class<MessageType> messageClass;

	public MessageHandler(Class<MessageType> messageClass) {
		this.messageClass = messageClass;
	}

	public abstract Collection<Message> handle(MessageType message);

	public Class<MessageType> getMessageClass() {
		return messageClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((messageClass == null) ? 0 : messageClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MessageHandler<?> other = (MessageHandler<?>) obj;
		if (messageClass == null) {
			if (other.messageClass != null) {
				return false;
			}
		} else if (!messageClass.equals(other.messageClass)) {
			return false;
		}
		return true;
	}

}
