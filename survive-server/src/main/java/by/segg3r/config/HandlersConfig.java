package by.segg3r.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.segg3r.handlers.ClientAuthenticationMessageHandler;
import by.segg3r.handlers.ClientPlayerMovementMessageHandler;
import by.segg3r.messaging.MessageProcessor;
import by.segg3r.server.ServerConnection;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
public class HandlersConfig {

	@Bean(name = "messageProcessor")
	public MessageProcessor<ServerConnection> messageProcessor(
			ClientPlayerMovementMessageHandler clientPlayerMovementMessageHandler,
			ClientAuthenticationMessageHandler clientAuthenticationMessageHandler) {
		return MessageProcessor.withHandlers(
				clientPlayerMovementMessageHandler,
				clientAuthenticationMessageHandler);
	}

}
