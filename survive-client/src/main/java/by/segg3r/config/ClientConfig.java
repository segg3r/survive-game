package by.segg3r.config;

import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import by.segg3r.client.Client;
import by.segg3r.client.ClientMessageHandler;
import by.segg3r.game.SurviveGame;
import by.segg3r.game.SurviveGameContainer;
import by.segg3r.messaging.connection.Connection;
import by.segg3r.messaging.exception.ConnectionException;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
@PropertySource(value = "file:" + ClientConfig.RESOURCES_FOLDER
		+ "/client.properties")
public class ClientConfig {

	public static final String RESOURCES_FOLDER = "resources";
	public static final Locale DEFAULT_LOCALE = Locale.US;

	/*
	 * Game client application properties
	 */
	@Value("${window.width}")
	private int windowWidth;
	@Value("${window.height}")
	private int windowHeight;

	/*
	 * Server connection properties
	 */
	@Value("${server.host}")
	private String host;
	@Value("${server.port}")
	private int port;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		PropertySourcesPlaceholderConfigurer source = new PropertySourcesPlaceholderConfigurer();
		source.setLocation(new FileSystemResource(RESOURCES_FOLDER + "/client.properties"));
		source.setIgnoreResourceNotFound(true);

		return source;
	}

	@Bean
	public SurviveGameContainer appGameContainer(SurviveGame game) throws SlickException {
		SurviveGameContainer result = new SurviveGameContainer(game);
		result.setDisplayMode(windowWidth, windowHeight, false);
		return result;
	}

	@Bean
	public Client client() {
		return new Client(host, port);
	}

	@Bean
	@DependsOn(value = "messageProcessor")
	public Connection clientConnection(Client client)
			throws ConnectionException {
		return client.start();
	}
	
	@Bean(name = "messageQueue")
	public Queue<ClientMessageHandler<?>> messageQueue() {
		return new ConcurrentLinkedQueue<ClientMessageHandler<?>>();
	}

}
