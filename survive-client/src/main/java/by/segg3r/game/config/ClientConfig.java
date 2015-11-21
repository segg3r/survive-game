package by.segg3r.game.config;

import java.util.Collections;
import java.util.Locale;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import by.segg3r.client.Client;
import by.segg3r.game.Game;
import by.segg3r.messaging.MessageProcessor;

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
		source.setLocation(new FileSystemResource("resources/client.properties"));
		source.setIgnoreResourceNotFound(true);

		return source;
	}

	@Bean
	public AppGameContainer appGameContainer(Game game) throws SlickException {
		AppGameContainer appGameContainer = new AppGameContainer(game);
		appGameContainer.setDisplayMode(windowWidth, windowHeight, false);
		return appGameContainer;
	}

	@Bean
	public Client client() {
		return new Client(host, port);
	}
	
	@Bean
	public MessageProcessor messageProcessor() {
		return MessageProcessor.withHandlers(Collections.emptyList());
	}

}
