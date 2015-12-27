package by.segg3r.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import by.segg3r.Server;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
@PropertySource(value = "file:" + ServerConfig.RESOURCES_FOLDER
		+ "/server.properties")
public class ServerConfig {

	public static final String RESOURCES_FOLDER = "resources";

	@Value("${server.port}")
	private int serverPort;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		PropertySourcesPlaceholderConfigurer source = new PropertySourcesPlaceholderConfigurer();
		source.setLocation(new FileSystemResource(RESOURCES_FOLDER + "/server.properties"));
		source.setIgnoreResourceNotFound(true);

		return source;
	}

	@Bean
	public Server server() {
		return new Server(serverPort);
	}


}
