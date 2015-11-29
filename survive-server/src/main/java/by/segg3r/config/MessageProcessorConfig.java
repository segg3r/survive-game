package by.segg3r.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.segg3r.messaging.MessageProcessor;
import by.segg3r.server.handlers.ClientPlayerMovementMessageHandler;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
public class MessageProcessorConfig {

	@Bean(name = "messageProcessor")
	public MessageProcessor messageProcessor(
			ClientPlayerMovementMessageHandler clientPlayerMovementMessageHandler) {
		return MessageProcessor.withHandlers(clientPlayerMovementMessageHandler);
	}

}
