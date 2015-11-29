package by.segg3r.server;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageSendingException;

public class ServerConnectionPoolTest {

	@InjectMocks
	private ServerConnectionPoolImpl connectionPool;
	
	@BeforeClass
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeMethod
	public void cleanConnections() {
		connectionPool.getConnections().clear();
	}
	
	@Test(description = "should add connection to pool")
	public void testAddConnection() {
		ServerConnection connection = mock(ServerConnection.class);
		connectionPool.addConnection(connection);
		assertEquals(connectionPool.getConnections().size(), 1);
	}
	
	@Test(description = "should remove connection from pool")
	public void testRemoveConnection() {
		ServerConnection connection = mock(ServerConnection.class);
		connectionPool.getConnections().add(connection);
		
		connectionPool.removeConnection(connection);
		assertTrue(connectionPool.getConnections().isEmpty());
	}
	
	@Test(description = "should send message to all connected players")
	public void testSendAll() throws MessageSendingException {
		ServerConnection connection = mock(ServerConnection.class);
		connectionPool.getConnections().addAll(Arrays.asList(connection, connection));
		
		Message message = mock(Message.class);
		connectionPool.sendAll(message);
		
		verify(connection, times(2)).sendMessage(eq(message));
	}
	
	@Test(description = "should send message to all but one")
	public void testSendAllButOne() throws MessageSendingException {
		ServerConnection except = mock(ServerConnection.class);
		ServerConnection connection = mock(ServerConnection.class);
		connectionPool.getConnections().addAll(Arrays.asList(except, connection));
		
		Message message = mock(Message.class);
		connectionPool.sendAllButOne(except, message);
		
		verify(except, never()).sendMessage(any(Message.class));
		verify(connection, times(1)).sendMessage(eq(message));
	}
	
}
