package by.segg3r.server;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageInterceptor;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.messaging.connection.ConnectionPool;
import by.segg3r.messaging.connection.listeners.ListenerType;
import by.segg3r.messaging.connection.listeners.Listeners;
import by.segg3r.messaging.exception.MessageHandlingException;
import by.segg3r.messaging.exception.UnrecognizedMessageTypeException;
import by.segg3r.messaging.messages.AllButOneResponseMessage;
import by.segg3r.messaging.messages.AllPlayersResponseMessage;
import by.segg3r.messaging.messages.SinglePlayerResponseMessage;

public class ServerConnectionTest {

	private static final class StopMessage extends Message {
	};

	private static final StopMessage STOP_MESSAGE = new StopMessage();

	private MessageInterceptor<ServerConnection> messageInterceptor;
	private List<MessageInterceptor<ServerConnection>> messageInterceptors;
	@Mock
	private Listeners<ServerConnection> listeners;
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
	@Mock
	private ConnectionPool connectionPool;
	@InjectMocks
	private ServerConnection serverConnection;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("unchecked")
	@BeforeMethod
	public void setCommonMocks() throws UnrecognizedMessageTypeException,
			MessageHandlingException {
		serverConnection.reset();

		when(messageProcessor.process(eq(STOP_MESSAGE))).then(
				new Answer<Collection<Message>>() {
					@Override
					public Collection<Message> answer(
							InvocationOnMock invocation) throws Throwable {
						serverConnection.stop();
						return Collections.emptyList();
					}
				});

		when(socket.getInetAddress()).thenReturn(inetAddress);
		
		messageInterceptor = mock(MessageInterceptor.class);
		messageInterceptors = Arrays.asList(messageInterceptor, messageInterceptor);
		serverConnection.setMessageInterceptors(messageInterceptors);
	}

	@AfterMethod
	public void resetMocks() {
		reset(in, out, socket, inetAddress, messageProcessor, connectionPool, listeners);
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

		serverConnection.run();
		verify(out, times(2)).writeMessage(eq(responseMessage));
		verify(connectionPool, never()).sendAll(any(Message.class));
	}

	@Test(description = "should send collection of response messages to all players")
	public void testSendResponseToAll() throws Exception {
		Message simpleMessage = new Message() {
		};
		Message responseMessage = new AllPlayersResponseMessage() {
		};
		// 2 response messages in collection should be sent 2 times
		when(messageProcessor.process(eq(simpleMessage))).thenReturn(
				Arrays.asList(responseMessage, responseMessage));
		when(in.readMessage()).thenReturn(simpleMessage, STOP_MESSAGE);

		serverConnection.run();
		verify(out, never()).writeMessage(any(Message.class));
		verify(connectionPool, times(2)).sendAll(eq(responseMessage));
	}
	
	@Test(description = "should send collection of response messages to all but one players")
	public void testSendResponseToAllButOne() throws Exception {
		Message simpleMessage = new Message() {
		};
		Message responseMessage = new AllButOneResponseMessage() {
		};
		// 2 response messages in collection should be sent 2 times
		when(messageProcessor.process(eq(simpleMessage))).thenReturn(
				Arrays.asList(responseMessage, responseMessage));
		when(in.readMessage()).thenReturn(simpleMessage, STOP_MESSAGE);

		serverConnection.run();
		verify(out, never()).writeMessage(any(Message.class));
		verify(connectionPool, times(2)).sendAllButOne(eq(serverConnection), eq(responseMessage));
	}
	
	@Test(description = "should not send any response message to player if response collection is empty")
	public void testNotSendResponse() throws Exception {
		Message simpleMessage = new Message() {
		};
		when(messageProcessor.process(eq(simpleMessage))).thenReturn(
				Collections.emptyList());
		when(in.readMessage()).thenReturn(simpleMessage, STOP_MESSAGE);

		serverConnection.run();
		verify(out, never()).writeMessage(any());
	}
	
	@Test(description = "should trigger all message interceptors before processing message")
	public void testInterceptorsTriggered() throws Exception {
		Message simpleMessage = new Message() {
		};
		when(messageProcessor.process(eq(simpleMessage))).thenReturn(
				Collections.emptyList());
		when(in.readMessage()).thenReturn(simpleMessage, STOP_MESSAGE);

		serverConnection.run();
		
		verify(messageInterceptor, times(2)).intercept(eq(simpleMessage), eq(serverConnection));
	}

	@Test(description = "connection should remove itself from connection pool when stopped")
	public void testConnectionRemovesItselfFromConnectionPool() {
		serverConnection.stop();
		verify(connectionPool, times(1)).removeConnection(eq(serverConnection));
	}
	
	@Test(description = "should trigger player disconnected listeners when stopped")
	public void testConnectionTriggersPlayerDisconnectedListeners() throws Exception {
		serverConnection.stop();
		verify(listeners, times(1)).trigger(eq(ListenerType.PLAYER_DISCONNECTED), eq(serverConnection));
	}

}
