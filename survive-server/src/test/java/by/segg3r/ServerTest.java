package by.segg3r;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.messaging.Connection;
import by.segg3r.messaging.ConnectionPool;
import by.segg3r.messaging.exception.ConnectionException;
import by.segg3r.server.ServerConnection;
import by.segg3r.server.ServerConnectionFactory;

public class ServerTest {

	private int port;

	@Mock
	private InetAddress inetAddress;
	@Mock
	private Socket clientSocket;
	@Mock
	private ServerSocket serverSocket;
	@Mock
	private ServerConnectionFactory connectionService;
	@Mock
	private ConnectionPool connectionPool;
	@Mock
	private ServerConnection serverConnection;
	
	private Server server;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeMethod
	public void init() throws ConnectionException {
		port = 11099;

		serverSocket = mock(ServerSocket.class);
		when(connectionService.createServerSocket(anyInt())).thenReturn(
				serverSocket);
		when(connectionService.createConnection(any(Socket.class)))
			.thenReturn(serverConnection);
		
		when(clientSocket.getInetAddress()).thenReturn(inetAddress);

		server = new Server(port);
		server.setConnectionService(connectionService);
		server.setConnectionPool(connectionPool);
	}
	
	@AfterMethod
	public void resetMocks() {
		reset(serverSocket, connectionService, connectionPool);
	}

	@Test(description = "should accept incoming connections")
	public void testAcceptIncomingConnections() throws Exception {
		when(serverSocket.accept()).thenAnswer(
				new SocketAnswer(clientSocket, clientSocket));

		server.run();

		verify(connectionPool, times(2)).addConnection(any(Connection.class));
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
				new SocketAnswer(clientSocket, clientSocket));

		server.setStopped(true);
		server.run();

		verify(connectionPool, never()).addConnection(any(Connection.class));
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
