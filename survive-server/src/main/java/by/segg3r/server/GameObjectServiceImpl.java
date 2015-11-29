package by.segg3r.server;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import by.segg3r.data.GameObject;

@Component
public class GameObjectServiceImpl implements GameObjectService {

	private static long idCounter = 1;
	private static synchronized long getNextId() {
		return idCounter++;
	}
	
	@Override
	public GameObject getNewGameObject() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		
		GameObject result = new GameObject(getNextId());
		double x = random.nextDouble() * 300;
		double y = random.nextDouble() * 300;
		result.setPosition(x, y);
		result.setDestination(x, y);
		
		return result;
	}

}
