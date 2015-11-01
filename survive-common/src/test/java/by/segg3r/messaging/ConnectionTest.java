package by.segg3r.messaging;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;

public class ConnectionTest {
	
	private static final class StopMessage extends Message {};
	private static final StopMessage STOP_MESSAGE = new StopMessage();
	
	@Mock
	private MessageInputStream in;
	@Mock
	private MessageOutputStream out;
	@Mock
	private MessageProcessor messageProcessor;
	@InjectMocks
	private Connection connection;
	
	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeMethod
	public void setCommonMocks() throws UnrecognizedMessageTypeException {
		connection.setStopped(false);
		
		when(messageProcessor.process(eq(STOP_MESSAGE))).then(new Answer<Message>() {
			@Override
			public Message answer(InvocationOnMock invocation) throws Throwable {
				connection.setStopped(true);
				return null;
			}
		});
	}
	
	@AfterMethod
	public void resetMocks() {
		reset(in, out, messageProcessor);
	}
	
	@Test(description = "should process messages while not stopped")
	public void testProcessingWhileNotStopped()
			throws Exception {
		Message simpleMessage = new Message() {};
		when(messageProcessor.process(eq(simpleMessage)))
			.thenReturn(null);
		when(in.readMessage()).thenReturn(simpleMessage, simpleMessage, STOP_MESSAGE);
		
		connection.run();
		verify(messageProcessor, times(3)).process(any());
	}
	
	@Test(description = "should send response message to player")
	public void testSendResponse() throws Exception {
		Message simpleMessage = new Message() {};
		Message responseMessage = new Message() {};
		when(messageProcessor.process(eq(simpleMessage)))
			.thenReturn(responseMessage);
		when(in.readMessage()).thenReturn(simpleMessage, STOP_MESSAGE);
	
		connection.run();
		verify(out, times(1)).writeMessage(eq(responseMessage));
	}

	@Test(description = "should not send response message to player if there is no such")
	public void testNotSendResponse() throws Exception {
		Message simpleMessage = new Message() {};
		when(messageProcessor.process(eq(simpleMessage)))
			.thenReturn(null);
		when(in.readMessage()).thenReturn(simpleMessage, STOP_MESSAGE);
	
		connection.run();
		verify(out, never()).writeMessage(any());
	}
	
}
