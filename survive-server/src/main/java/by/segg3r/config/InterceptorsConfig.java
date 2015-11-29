package by.segg3r.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.segg3r.interceptors.ClientIdMessageInterceptor;
import by.segg3r.messaging.MessageInterceptor;
import by.segg3r.server.ServerConnection;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
public class InterceptorsConfig {

	@Bean(name = "messageInterceptors")
	public List<MessageInterceptor<ServerConnection>> messageInterceptors(
			ClientIdMessageInterceptor clientIdMessageInterceptor) {
		return Arrays.asList(clientIdMessageInterceptor);
	}

}
