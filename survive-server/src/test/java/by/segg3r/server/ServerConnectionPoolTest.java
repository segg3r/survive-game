package by.segg3r.server;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.util.Arrays;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.messaging.Connection;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.exception.MessageSendingException;

public class ServerConnectionPoolTest {

	@InjectMocks
	private ServerConnectionPoolImpl connectionPool;
	
	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(description = "should add connection to pool")
	public void testAddConnection() {
		Connection connection = mock(Connection.class);
		connectionPool.addConnection(connection);
		assertEquals(connectionPool.getConnections().size(), 1);
	}
	
	@Test(description = "should send message to all connected players")
	public void testSendAll() throws MessageSendingException {
		Connection connection = mock(Connection.class);
		connectionPool.getConnections().addAll(Arrays.asList(connection, connection));
		
		Message message = mock(Message.class);
		connectionPool.sendAll(message);
		
		verify(connection, times(2)).sendMessage(eq(message));
	}
	
}
