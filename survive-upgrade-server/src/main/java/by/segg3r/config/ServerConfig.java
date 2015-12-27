package by.segg3r.config;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@PropertySource(value = "file:" + ServerConfig.RESOURCES_FOLDER
		+ "/upgrade-server.properties")
public class ServerConfig {

	public static final String RESOURCES_FOLDER = "resources";

	@Value("${upgrade-server.port}")
	private int upgradeServerPort;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		PropertySourcesPlaceholderConfigurer source = new PropertySourcesPlaceholderConfigurer();
		source.setLocation(new FileSystemResource(RESOURCES_FOLDER
				+ "/upgrade-server.properties"));
		source.setIgnoreResourceNotFound(true);

		return source;
	}

	@Bean
	public Server server() {
		return new Server(upgradeServerPort);
	}

	@Bean
	public ServletContextHandler httpApplicationServletContext() {
		final ServletHolder servletHolder = new ServletHolder(new CXFServlet());
		final ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(servletHolder, "/*");
		context.addEventListener(new ContextLoaderListener());

		context.setInitParameter("contextClass",
				AnnotationConfigWebApplicationContext.class.getName());
		context.setInitParameter("contextConfigLocation",
				HttpApplicationConfig.class.getName());

		return context;
	}

}
