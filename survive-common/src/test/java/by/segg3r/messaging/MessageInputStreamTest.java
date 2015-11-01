package by.segg3r.messaging;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;

import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.messaging.exception.MessageReceievingException;

public class MessageInputStreamTest {

	private static final Message EMPTY_MESSAGE = new Message() {
	};

	@Spy
	private MessageInputStream messageInputStream;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(messageInputStream);
	}

	@Test(description = "should read message from input stream")
	public void testReadMessagePositive() throws Exception {
		doReturn(EMPTY_MESSAGE).when(messageInputStream).readObjectFromStream();
		Message result = messageInputStream.readMessage();
		assertEquals(result, EMPTY_MESSAGE);
	}

	@Test(description = "should result in exception when not message is received", expectedExceptions = MessageReceievingException.class)
	public void testReadMessageNegative() throws Exception {
		Object object = new Object();
		doReturn(object).when(messageInputStream).readObjectFromStream();
		messageInputStream.readMessage();
	}

}
