package by.segg3r;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.exception.ConnectionException;
import by.segg3r.server.ConnectionService;

public class ServerTest {

	private int port;

	private ServerSocket serverSocket;
	private ConnectionService connectionService;

	private Server server;

	@BeforeMethod
	public void init() throws ConnectionException {
		port = 11099;

		serverSocket = mock(ServerSocket.class);
		connectionService = mock(ConnectionService.class);
		when(connectionService.createServerSocket(anyInt())).thenReturn(
				serverSocket);

		server = new Server(port);
		server.setConnectionService(connectionService);
	}

	@Test(description = "should accept incoming connections")
	public void testAcceptIncomingConnections() throws Exception {
		when(serverSocket.accept()).thenAnswer(
				new SocketAnswer(mock(Socket.class), mock(Socket.class)));

		server.run();

		assertEquals(server.getConnections().size(), 2);
	}
	
	@Test(description = "should create server socket on specified port")
	public void testCreatingServerSocket() throws Exception {
		when(serverSocket.accept()).thenAnswer(
				new SocketAnswer());

		server.run();

		verify(connectionService, times(1)).createServerSocket(eq(port));
	}

	@Test(description = "should not accept any connections if stopped")
	public void testAcceptingConnectionsWhenStopped() throws Exception {
		when(serverSocket.accept()).thenAnswer(
				new SocketAnswer(mock(Socket.class), mock(Socket.class)));

		server.setStopped(true);
		server.run();

		assertEquals(server.getConnections().size(), 0);
	}
	
	private static final class SocketAnswer implements Answer<Socket> {

		private int currentSocketIndex;
		private List<Socket> sockets;

		public SocketAnswer(Socket... sockets) {
			this.sockets = Arrays.asList(sockets);
		}

		@Override
		public Socket answer(InvocationOnMock invocation) throws Throwable {
			if (currentSocketIndex < sockets.size()) {
				Socket result = sockets.get(currentSocketIndex);
				currentSocketIndex++;
				return result;
			}

			throw new IOException();
		}

	}

}
