package by.segg3r.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.segg3r.listeners.PlayerCreationListener;
import by.segg3r.messaging.connection.listeners.ListenerType;
import by.segg3r.messaging.connection.listeners.Listeners;
import by.segg3r.server.ServerConnection;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
public class ListenersConfig {
	
	@SuppressWarnings("unchecked")
	@Bean(name = "listeners")
	public Listeners<ServerConnection> connectionEstablishedListeners(
			PlayerCreationListener playerCreationListener) {
		Listeners<ServerConnection> listeners = new Listeners<ServerConnection>();
		listeners.add(ListenerType.PLAYER_CONNECTED, playerCreationListener);
		return listeners;
	}
	
}
