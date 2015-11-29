package by.segg3r.interceptors;

import org.springframework.stereotype.Component;

import by.segg3r.data.GameObject;
import by.segg3r.messages.client.ClientMessage;
import by.segg3r.messaging.Message;
import by.segg3r.messaging.MessageInterceptor;
import by.segg3r.server.ServerConnection;

@Component
public class ClientIdMessageInterceptor implements MessageInterceptor<ServerConnection> {

	@Override
	public void intercept(Message message, ServerConnection connection) {
		if (message instanceof ClientMessage) {
			ClientMessage clientMessage = (ClientMessage) message;
			GameObject player = connection.getPlayer();
			clientMessage.setClientId(player.getId());
		}
	}

}
