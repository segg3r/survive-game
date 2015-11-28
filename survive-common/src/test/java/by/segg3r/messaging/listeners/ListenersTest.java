package by.segg3r.messaging.listeners;

import static org.mockito.Mockito.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.connection.ConnectionListener;
import by.segg3r.messaging.connection.listeners.ListenerType;
import by.segg3r.messaging.connection.listeners.Listeners;

public class ListenersTest {

	private Listeners<Connection> listeners;

	@BeforeMethod
	public void initMocks() {
		listeners = new Listeners<Connection>();
	}

	@SuppressWarnings("unchecked")
	@Test(description = "should trigger by type")
	public void shouldTriggerByType() throws Exception {
		ConnectionListener<Connection> playerConnectedListener = mock(ConnectionListener.class);
		ConnectionListener<Connection> playerDisconnectedListener = mock(ConnectionListener.class);

		listeners.add(ListenerType.PLAYER_CONNECTED, playerConnectedListener,
				playerConnectedListener);
		listeners.add(ListenerType.PLAYER_DISCONNECTED,
				playerDisconnectedListener, playerDisconnectedListener);

		Connection connection = mock(Connection.class);
		listeners.trigger(ListenerType.PLAYER_CONNECTED, connection);

		verify(playerConnectedListener, times(2)).trigger(eq(connection));
		verify(playerDisconnectedListener, never()).trigger(
				any(Connection.class));
	}

}
