package by.segg3r.messaging;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.messaging.exception.MessageSendingException;

public class MessageOutputStreamTest {

	private static final Message EMPTY_MESSAGE = new Message() {
	};

	@Spy
	private MessageOutputStream messageOutputStream;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(messageOutputStream);
	}

	@Test(description = "should send message to output stream")
	public void testWriteMessagePositive() throws Exception {
		doNothing().when(messageOutputStream).writeObjectToStream(
				eq(EMPTY_MESSAGE));
		messageOutputStream.writeMessage(EMPTY_MESSAGE);
		verify(messageOutputStream, times(1)).writeObjectToStream(
				eq(EMPTY_MESSAGE));
	}

	@Test(description = "should throw message sending exception if sending is failed", expectedExceptions = MessageSendingException.class)
	public void testWriteMessageNegative() throws Exception {
		Exception thrownException = new Exception();
		doThrow(thrownException).when(messageOutputStream).writeObjectToStream(
				eq(EMPTY_MESSAGE));
		messageOutputStream.writeMessage(EMPTY_MESSAGE);
	}

}
