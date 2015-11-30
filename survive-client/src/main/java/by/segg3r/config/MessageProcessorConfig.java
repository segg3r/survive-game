package by.segg3r.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import by.segg3r.client.handlers.ServerOtherPlayersCreationMessageHandler;
import by.segg3r.client.handlers.ServerPlayerCreationMessageHandler;
import by.segg3r.client.handlers.ServerPlayerDisconnectedMessageHandler;
import by.segg3r.client.handlers.ServerPlayerMovementMessageHandler;
import by.segg3r.messaging.MessageProcessor;

@Configuration
@ComponentScan(basePackages = "by.segg3r")
public class MessageProcessorConfig {

	@Bean(name = "messageProcessor")
	public MessageProcessor messageProcessor(
			ServerPlayerCreationMessageHandler playerCreationMessageHandler,
			ServerPlayerMovementMessageHandler serverPlayerMovementMessageHandler,
			ServerOtherPlayersCreationMessageHandler serverOtherPlayersCreationMessageHandler,
			ServerPlayerDisconnectedMessageHandler serverPlayerDisconnectedMessageHandler) {
		return MessageProcessor.withHandlers(playerCreationMessageHandler,
				serverPlayerMovementMessageHandler,
				serverOtherPlayersCreationMessageHandler,
				serverPlayerDisconnectedMessageHandler);
	}

}
