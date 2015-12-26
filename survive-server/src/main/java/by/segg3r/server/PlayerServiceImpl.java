package by.segg3r.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;
import by.segg3r.exception.AuthenticationException;

@Component
public class PlayerServiceImpl implements PlayerService {

	private static final String ADMIN = "admin";
	private static final int BORDER = 300;
	
	
	private static long idCounter = 1;

	private static synchronized long getNextId() {
		return idCounter++;
	}

	private Map<Long, GameObject> gameObjects = new HashMap<Long, GameObject>();

	@Override
	public GameObject authenticate(String login, String password) throws AuthenticationException {
		if (!(ADMIN.equals(login) && ADMIN.equals(password))) {
			throw new AuthenticationException("Wrong login/password combination");
		}
		
		ThreadLocalRandom random = ThreadLocalRandom.current();

		GameObject gameObject = new GameObject(getNextId());
		double x = random.nextDouble() * BORDER;
		double y = random.nextDouble() * BORDER;
		gameObject.setPosition(x, y);
		gameObject.setDestination(x, y);

		gameObjects.put(gameObject.getId(), gameObject);

		return gameObject;
	}

	@Override
	public void changePosition(long id, Position position) {
		GameObject gameObject = gameObjects.get(id);
		gameObject.setPosition(position.getX(), position.getY());
		gameObject.setDestination(position.getX(), position.getY());
	}

	@Override
	public List<GameObject> getOtherPlayers(long id) {
		return new ArrayList<GameObject>(gameObjects.values().stream()
				.filter(gameObject -> id != gameObject.getId())
				.collect(Collectors.toList()));
	}

	@Override
	public void removePlayer(long id) {
		gameObjects.remove(id);
	}

}
