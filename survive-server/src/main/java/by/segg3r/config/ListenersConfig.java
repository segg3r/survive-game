package by.segg3r.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.segg3r.listeners.PlayerCreationListener;
import by.segg3r.messaging.ConnectionListener;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
public class ListenersConfig {
	
	@Bean(name = "connectionEstablishedListeners")
	public List<ConnectionListener> connectionEstablishedListeners(
			PlayerCreationListener playerCreationListener) {
		return Arrays.asList(playerCreationListener);
	}
	
}
