package by.segg3r.messaging;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;

@SuppressWarnings("unchecked")
public class MessageProcessor {

	public static MessageProcessor withHandlers(
			Collection<MessageHandler<?>> handlers) {
		return new MessageProcessor(handlers);
	}

	public static MessageProcessor withHandlers(MessageHandler<?>... handlers) {
		return new MessageProcessor(Arrays.asList(handlers));
	}

	private Map<Class<?>, MessageHandler<?>> handlers = new HashMap<Class<?>, MessageHandler<?>>();

	public MessageProcessor(
			Collection<MessageHandler<? extends Message>> handlers) {
		super();
		handlers.forEach(handler -> {
			addHandler(handler.getMessageClass(), handler);
		});
	}

	private void addHandler(Class<? extends Message> messageClass,
			MessageHandler<? extends Message> handler) {
		handlers.put(messageClass, handler);
	}

	public <MessageType extends Message> Message process(MessageType message)
			throws UnrecognizedMessageTypeException {
		Class<?> messageClass = message.getClass();
		MessageHandler<MessageType> handler = (MessageHandler<MessageType>) handlers
				.get(messageClass);
		if (handler == null) {
			throw new UnrecognizedMessageTypeException(
					"Could not find handler for message " + message);
		}
		return handler.handle(message);
	}

}
