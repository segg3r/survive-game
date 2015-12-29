package by.segg3r.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import by.segg3r.constants.FileSystem;
import by.segg3r.upgradeclient.UpgradeServerAPI;
import by.segg3r.upgradeclient.impl.UpgradeServerAPIImpl;

import com.google.gson.Gson;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
@PropertySource(value = "file:" + UpgradeClientConfig.UPGRADE_CLIENT_PROPERTIES_PATH)
public class UpgradeClientConfig {

	public static final String RESOURCES_FOLDER = "resources";
	public static final String UPGRADE_CLIENT_PROPERTIES = "upgrade-client.properties";
	public static final String UPGRADE_CLIENT_PROPERTIES_PATH = UpgradeClientConfig.RESOURCES_FOLDER
			+ FileSystem.FILE_SPLITTER + UpgradeClientConfig.UPGRADE_CLIENT_PROPERTIES;
	
	/*
	 * Upgrade client properties
	 */
	@Value("${upgrade-server.host}")
	private String upgradeServerHost;
	@Value("${upgrade-server.port}")
	private int upgradeServerPort;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		PropertySourcesPlaceholderConfigurer source = new PropertySourcesPlaceholderConfigurer();
		source.setLocation(new FileSystemResource(UpgradeClientConfig.UPGRADE_CLIENT_PROPERTIES_PATH));
		source.setIgnoreResourceNotFound(true);

		return source;
	}
	
	@Bean
	public UpgradeServerAPI upgradeServerAPI() {
		UpgradeServerAPIImpl upgradeServerAPI = new UpgradeServerAPIImpl();
		upgradeServerAPI.setUpgradeServerHost(upgradeServerHost);
		upgradeServerAPI.setUpgradeServerPort(upgradeServerPort);
		return upgradeServerAPI;
	}
	
	@Bean
	public HttpClient httpClient() {
		return HttpClientBuilder.create().build();
	}
	
	@Bean
	public Gson gson() {
		return new Gson();
	}
	
}
