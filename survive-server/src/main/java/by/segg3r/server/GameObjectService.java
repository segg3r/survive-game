package by.segg3r.server;

import java.util.List;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;

public interface GameObjectService {

	GameObject getNewGameObject();
	
	void changeObjectPosition(long id, Position position);
	
	List<GameObject> getOtherObjects(long id);
	
	void removeObject(long id);
	
}
