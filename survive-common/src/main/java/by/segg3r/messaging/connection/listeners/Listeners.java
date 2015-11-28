package by.segg3r.messaging.connection.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.segg3r.messaging.connection.ConnectionListener;

public class Listeners<ConnectionType> {

	private Map<ListenerType, List<ConnectionListener<ConnectionType>>> listeners;
	
	public Listeners() {
		this.listeners = new HashMap<ListenerType, List<ConnectionListener<ConnectionType>>>();
	}

	@SuppressWarnings("unchecked")
	public void add(ListenerType listenerType, ConnectionListener<ConnectionType>... newListeners) {
		List<ConnectionListener<ConnectionType>> existingListeners = this.listeners.get(listenerType);
		if (existingListeners == null) {
			existingListeners = new ArrayList<ConnectionListener<ConnectionType>>();
			this.listeners.put(listenerType, existingListeners);
		}
		existingListeners.addAll(Arrays.asList(newListeners));
	}
	
	public void trigger(ListenerType listenerType, ConnectionType connection) throws Exception {
		List<ConnectionListener<ConnectionType>> existingListeners = this.listeners.get(listenerType);
		if (existingListeners != null) {
			for (ConnectionListener<ConnectionType> connectionListener : existingListeners) {
				connectionListener.trigger(connection);
			}
		}
	}
	
}
