package by.segg3r.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.segg3r.client.handlers.SinglePlayerCreationMessageHandler;
import by.segg3r.messaging.MessageProcessor;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
public class MessageProcessorConfig {

	@Bean(name = "messageProcessor")
	public MessageProcessor messageProcessor(
			SinglePlayerCreationMessageHandler playerCreationMessageHandler) {
		return MessageProcessor.withHandlers(playerCreationMessageHandler);
	}

}
