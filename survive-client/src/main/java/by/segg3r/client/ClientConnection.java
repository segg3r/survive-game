package by.segg3r.client;

import java.net.Socket;

import by.segg3r.messaging.Connection;
import by.segg3r.messaging.MessageInputStream;
import by.segg3r.messaging.MessageOutputStream;
import by.segg3r.messaging.MessageProcessor;

public class ClientConnection extends Connection<ClientState> {

	public ClientConnection(Socket socket, MessageInputStream in,
			MessageOutputStream out, MessageProcessor messageProcessor,
			ClientState state) {
		super(socket, in, out, messageProcessor, state);
	}

}
