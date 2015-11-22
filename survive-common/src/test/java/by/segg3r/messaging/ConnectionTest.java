package by.segg3r.messaging;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.messaging.exception.MessageReceievingException;
import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;
import by.segg3r.messaging.messages.SinglePlayerResponseMessage;

public class ConnectionTest {

	private static final class StopMessage extends Message {
	};

	private static final StopMessage STOP_MESSAGE = new StopMessage();

	@Mock
	private InetAddress inetAddress;
	@Mock
	private Socket socket;
	@Mock
	private MessageInputStream in;
	@Mock
	private MessageOutputStream out;
	@Mock
	private MessageProcessor messageProcessor;
	@InjectMocks
	private Connection<?> connection;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeMethod
	public void setCommonMocks() throws UnrecognizedMessageTypeException {
		connection.reset();

		when(messageProcessor.process(eq(STOP_MESSAGE))).then(
				new Answer<Collection<Message>>() {
					@Override
					public Collection<Message> answer(
							InvocationOnMock invocation) throws Throwable {
						connection.stop();
						return Collections.emptyList();
					}
				});
		when(socket.getInetAddress()).thenReturn(inetAddress);
	}

	@AfterMethod
	public void resetMocks() {
		reset(in, out, inetAddress, socket, messageProcessor);
	}

	@Test(description = "should process messages while not stopped")
	public void testProcessingWhileNotStopped() throws Exception {
		Message simpleMessage = new Message() {
		};
		when(messageProcessor.process(eq(simpleMessage))).thenReturn(
				Collections.emptyList());
		when(in.readMessage()).thenReturn(simpleMessage, simpleMessage,
				STOP_MESSAGE);

		connection.run();
		verify(messageProcessor, times(2)).process(eq(simpleMessage));
	}

	@Test(description = "should send collection of response messages to player")
	public void testSendResponseToPlayer() throws Exception {
		Message simpleMessage = new Message() {
		};
		Message responseMessage = new SinglePlayerResponseMessage() {
		};
		// 2 response messages in collection should be sent 2 times
		when(messageProcessor.process(eq(simpleMessage))).thenReturn(
				Arrays.asList(responseMessage, responseMessage));
		when(in.readMessage()).thenReturn(simpleMessage, STOP_MESSAGE);

		connection.run();
		verify(out, times(2)).writeMessage(eq(responseMessage));
	}

	@Test(description = "should not send any response message to player if response collection is empty")
	public void testNotSendResponse() throws Exception {
		Message simpleMessage = new Message() {
		};
		when(messageProcessor.process(eq(simpleMessage))).thenReturn(
				Collections.emptyList());
		when(in.readMessage()).thenReturn(simpleMessage, STOP_MESSAGE);

		connection.run();
		verify(out, never()).writeMessage(any());
	}

	@Test(description = "should stop connection if message receiving exception is thrown")
	public void testStopWhenMessageReceivingExceptionIsThrown()
			throws Exception {
		Connection<?> connectionSpy = spy(connection);
		doCallRealMethod().when(connectionSpy).stop();
		when(in.readMessage()).thenThrow(new MessageReceievingException());

		connectionSpy.run();

		verify(connectionSpy, times(1)).stop();
	}

	@Test(description = "should close socket when stopped")
	public void testCloseSocketWhenStopped() throws Exception {
		connection.stop();

		connection.run();
		verify(socket, times(1)).close();
	}

	@Test(description = "stop and reset methods should trigger connection state change")
	public void testStopAndReset() {
		connection.stop();
		assertTrue(connection.isStopped());
		connection.reset();
		assertFalse(connection.isStopped());
	}

}
