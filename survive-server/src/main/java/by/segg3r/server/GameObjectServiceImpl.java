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

@Component
public class GameObjectServiceImpl implements GameObjectService {

	private static long idCounter = 1;

	private static synchronized long getNextId() {
		return idCounter++;
	}

	private Map<Long, GameObject> gameObjects = new HashMap<Long, GameObject>();

	@Override
	public GameObject getNewGameObject() {
		ThreadLocalRandom random = ThreadLocalRandom.current();

		GameObject gameObject = new GameObject(getNextId());
		double x = random.nextDouble() * 300;
		double y = random.nextDouble() * 300;
		gameObject.setPosition(x, y);
		gameObject.setDestination(x, y);

		gameObjects.put(gameObject.getId(), gameObject);

		return gameObject;
	}

	@Override
	public void changeObjectPosition(long id, Position position) {
		GameObject gameObject = gameObjects.get(id);
		gameObject.setPosition(position.getX(), position.getY());
	}

	@Override
	public List<GameObject> getOtherObjects(long id) {
		return new ArrayList<GameObject>(gameObjects.values().stream()
				.filter(gameObject -> id != gameObject.getId())
				.collect(Collectors.toList()));
	}

}
