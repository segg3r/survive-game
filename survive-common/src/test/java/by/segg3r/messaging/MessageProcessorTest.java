package by.segg3r.messaging;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;

public class MessageProcessorTest {

	private static final class TestMessage extends Message {
	}

	private static final class TestResponseMessage extends Message {
	}

	private static final class UnrecognizedTestMessage extends Message {
	}

	private static final TestMessage MESSAGE = new TestMessage();
	private static final TestResponseMessage RESPONSE_MESSAGE = new TestResponseMessage();
	private static final UnrecognizedTestMessage UNRECOGNIZED_MESSAGE = new UnrecognizedTestMessage();

	private static final class TestMessageHandler extends
			MessageHandler<TestMessage> {
		public TestMessageHandler() {
			super(TestMessage.class);
		}

		@Override
		public Message handle(TestMessage message) {
			return RESPONSE_MESSAGE;
		}
	}

	private MessageProcessor messageProcessor;

	@BeforeClass
	public void init() {
		messageProcessor = MessageProcessor
				.withHandlers(new TestMessageHandler());
	}

	@Test(description = "should return response according to message type")
	public void testProcessPositive() throws UnrecognizedMessageTypeException {
		assertEquals(messageProcessor.process(MESSAGE), RESPONSE_MESSAGE);
	}

	@Test(description = "should throw exception if no handler found", expectedExceptions = UnrecognizedMessageTypeException.class)
	public void testProcessNegative() throws UnrecognizedMessageTypeException {
		messageProcessor.process(UNRECOGNIZED_MESSAGE);
	}

}
