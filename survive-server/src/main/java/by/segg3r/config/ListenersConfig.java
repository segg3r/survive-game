package by.segg3r.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.segg3r.listeners.PlayerConnectionListener;
import by.segg3r.listeners.PlayerAuthenticationListener;
import by.segg3r.listeners.PlayerDisconnectedListener;
import by.segg3r.messaging.connection.listeners.ListenerType;
import by.segg3r.messaging.connection.listeners.Listeners;
import by.segg3r.server.ServerConnection;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
public class ListenersConfig {

	@SuppressWarnings("unchecked")
	@Bean(name = "listeners")
	public Listeners<ServerConnection> connectionEstablishedListeners(
			PlayerConnectionListener playerConnectionListener,
			PlayerAuthenticationListener playerCreationListener,
			PlayerDisconnectedListener playerDisconnectedListener) {
		Listeners<ServerConnection> listeners = new Listeners<ServerConnection>();
		listeners.add(ListenerType.PLAYER_CONNECTED, playerConnectionListener);
		listeners.add(ListenerType.PLAYER_AUTHENTICATED, playerCreationListener);
		listeners.add(ListenerType.PLAYER_DISCONNECTED,
				playerDisconnectedListener);
		return listeners;
	}

}
