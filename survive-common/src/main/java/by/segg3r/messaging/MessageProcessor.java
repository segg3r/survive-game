package by.segg3r.messaging;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;

@SuppressWarnings("unchecked")
public class MessageProcessor<MessageSourceType> {

	public static <T> MessageProcessor<T> withHandlers(
			Collection<MessageHandler<T, ?>> handlers) {
		return new MessageProcessor<T>(handlers);
	}

	@SafeVarargs
	public static <T> MessageProcessor<T> withHandlers(
			MessageHandler<T, ?>... handlers) {
		return new MessageProcessor<T>(Arrays.asList(handlers));
	}

	private Map<Class<?>, MessageHandler<MessageSourceType, ?>> handlers =
			new HashMap<Class<?>, MessageHandler<MessageSourceType, ?>>();

	public MessageProcessor(
			Collection<MessageHandler<MessageSourceType, ? extends Message>> handlers) {
		super();
		handlers.forEach(handler -> {
			addHandler(handler.getMessageClass(), handler);
		});
	}

	private void addHandler(Class<? extends Message> messageClass,
			MessageHandler<MessageSourceType, ? extends Message> handler) {
		handlers.put(messageClass, handler);
	}

	public <MessageType extends Message> Collection<Message> process(
			MessageSourceType messageSource, MessageType message)
			throws UnrecognizedMessageTypeException, MessageHandlingException {
		Class<?> messageClass = message.getClass();
		MessageHandler<MessageSourceType, MessageType> handler =
				(MessageHandler<MessageSourceType, MessageType>) handlers.get(messageClass);
		if (handler == null) {
			throw new UnrecognizedMessageTypeException(
					"Could not find handler for message " + message);
		}
		return handler.handle(messageSource, message);
	}

}
