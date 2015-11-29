package by.segg3r.game.objects;

import org.newdawn.slick.SlickException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.game.objects.prefabs.ImageHolder;
import by.segg3r.game.objects.prefabs.Prefab;

@Component
public class GameObjectFactory {

	@Autowired
	private ImageHolder imageHolder;

	public GameObjectFactory() {
		super();
	}

	public <T extends GameObject> T instantiate(Prefab<T, ?> prefab, double x,
			double y) throws SlickException {
		T gameObject = prefab.instantiate(imageHolder);
		gameObject.setPosition(x, y);
		gameObject.setDestination(x, y);
		return gameObject;
	}

	public void setImageHolder(ImageHolder imageHolder) {
		this.imageHolder = imageHolder;
	}

}
