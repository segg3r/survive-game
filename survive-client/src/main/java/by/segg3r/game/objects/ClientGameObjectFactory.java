package by.segg3r.game.objects;

import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.data.GameObject;
import by.segg3r.data.Position;
import by.segg3r.game.objects.prefabs.ImageHolder;
import by.segg3r.game.objects.prefabs.Prefab;

@Component
public class ClientGameObjectFactory {

	@Autowired
	private ImageHolder imageHolder;

	public ClientGameObjectFactory() {
		super();
	}

	public <T extends ClientGameObject> T instantiate(Prefab<T, ?> prefab,
			GameObject gameObject) throws SlickException {
		T result = prefab.instantiate(gameObject, imageHolder);
		Position position = gameObject.getPosition();
		Position destination = gameObject.getDestination();
		result.setPosition(position.getX(), position.getY());
		result.setDestination(destination.getX(), destination.getY());
		return result;
	}

	public void setImageHolder(ImageHolder imageHolder) {
		this.imageHolder = imageHolder;
	}

}
